package bgu.spl.mics.application.services;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.Messages.TerminateBroadcast;
import bgu.spl.mics.application.Messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.Inventory;
import bgu.spl.mics.application.passiveObjects.MoneyRegister;
import bgu.spl.mics.application.passiveObjects.ResourcesHolder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * TimeService is the global system timer There is only one instance of this micro-service.
 * It keeps track of the amount of ticks passed since initialization and notifies
 * all other micro-services about the current time tick using {@link //Tick Broadcast}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 * 
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */
public class TimeService extends MicroService{

    private int speed;
    private int duration;
    private int currentTick;
	public TimeService(int speed, int duration) {
		super("TimeService");
		this.speed = speed;
		this.duration = duration;
		this.currentTick = 0;
	}

	@Override
	protected void initialize() {
        subscribeBroadcast(TerminateBroadcast.class, finalCall-> this.terminate());
        Timer timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if(currentTick < duration) {
                        currentTick++;
                        TickBroadcast tick = new TickBroadcast (currentTick);
                        sendBroadcast(tick);
                    }
                    else{
                        sendBroadcast(new TerminateBroadcast());
                        timer.cancel();
                    }
                }
            }, 0, speed);
        }
	}