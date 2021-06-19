package bgu.spl.mics.application.passiveObjects;

import bgu.spl.mics.Future;

import java.util.Arrays;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

/**
 * Passive object representing the resource manager.
 * You must not alter any of the given public methods of this class.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private methods and fields to this class.
 */
public class ResourcesHolder {

    /**
     * Retrieves the single instance of this class.
     */

    private ConcurrentLinkedQueue<DeliveryVehicle> vehicleQueue;
    private ConcurrentLinkedQueue<Future<DeliveryVehicle>> vehicleFutureToResolve;

    private static ResourcesHolder singleInstance = null;
    private Semaphore keyForResource;


    private ResourcesHolder() {

    }


    public static ResourcesHolder getInstance() {
        if (singleInstance == null){
            singleInstance = new ResourcesHolder();
        }
        return singleInstance;
    }

    /**
     * Tries to acquire a vehicle and gives a future object which will
     * resolve to a vehicle.
     * <p>
     * @return 	{@link Future<DeliveryVehicle>} object which will resolve to a
     * 			{@link DeliveryVehicle} when completed.
     */
    public Future<DeliveryVehicle> acquireVehicle() {
        Future<DeliveryVehicle> futureVehicle= new Future(); //future object
        if(keyForResource.tryAcquire()) {                    //if is able to acquire (belongs to future class)
            futureVehicle.resolve((DeliveryVehicle) vehicleQueue.poll()); // resolving request ;casting because resolve works with Generic
        }
        else {
            vehicleFutureToResolve.add(futureVehicle);    //adds to queue
        }
        return futureVehicle;                   //return future

    }

    /**
     * Releases a specified vehicle, opening it again for the possibility of
     * acquisition.
     * <p>
     * @param vehicle	{@link DeliveryVehicle} to be released.
     */
    public void releaseVehicle(DeliveryVehicle vehicle) {
        synchronized (vehicleQueue) {
            vehicleQueue.add(vehicle);
        }
        keyForResource.release();  //released one key
        assignWaitersToVehicle();
        //assign a vehicle to the released one

    }

    public void assignWaitersToVehicle() { //every time a vehicle is released
        synchronized (vehicleFutureToResolve) { //waiters for vehicle synchronize
            if(vehicleFutureToResolve!=null) { // Isn't empty
                if(keyForResource.tryAcquire()) { // can acquire
                    DeliveryVehicle vehicle = vehicleQueue.poll(); //last released vehicle
                    if(vehicle!=null) {
                        vehicleFutureToResolve.poll().resolve(vehicle); //resolve one future that's waiting to this vehicle
                    }
                }
            }
        }
    }

    /**
     * Receives a collection of vehicles and stores them.
     * <p>
     * @param vehicles	Array of {@link DeliveryVehicle} instances to store.
     */
    public void load(DeliveryVehicle[] vehicles) {
        //copy the array to the list(happends one time?
        vehicleQueue.addAll(Arrays.asList(vehicles));
        keyForResource = new Semaphore(vehicles.length);
    }

}