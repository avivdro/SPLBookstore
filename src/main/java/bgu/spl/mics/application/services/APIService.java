package bgu.spl.mics.application.services;


import java.util.Arrays;

import bgu.spl.mics.application.Messages.BookOrderEvent;
import bgu.spl.mics.application.Messages.DeliveryEvent;
import bgu.spl.mics.application.Messages.TerminateBroadcast;
import bgu.spl.mics.application.Messages.TickBroadcast;
import bgu.spl.mics.application.passiveObjects.*;
import java.util.List;

import bgu.spl.mics.Future;
import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Customer;
import bgu.spl.mics.application.passiveObjects.OrderSchedule;

/**
 * APIService is in charge of the connection between a client and the store.
 * It informs the store about desired purchases using {@link BookOrderEvent}.
 * This class may not hold references for objects which it is not responsible for:
 * {@link ResourcesHolder}, {@link MoneyRegister}, {@link Inventory}.
 *
 * You can add private fields and public methods to this class.
 * You MAY change constructor signatures and even add new public constructors.
 */


/**orderSchedule: List – contains the orders that the client needs to make
 *  (every order has a corresponding time tick to send the OrderBookEvent).
 *   The list is not guaranteed to be sorted. The APIService will send the
 *   OrderBookEvent on the tick specified on the schedule (each order
 *   contains one book only, that is, orders on the same tick are supposed
 *    to be processed by different SellingService (in case there is more than one).
 */
public class APIService extends MicroService {
    private int time;
    private OrderSchedule[] orderSchedules;
    private int currentIndex = 0;
    private Customer customer;

    public APIService(String name, Customer customer1, OrderSchedule[] orderSchedule) {
        super(name);
        customer = customer1;
        Arrays.sort(orderSchedule);
        orderSchedules = new OrderSchedule[orderSchedule.length];
        for (int i = 0; i < orderSchedule.length; i++) {
            orderSchedules[i] = orderSchedule[i];
        }
    }

    @Override
    protected void initialize() {
        subscribeBroadcast(TerminateBroadcast.class, finallCall -> {  //termination, not sure why
            this.terminate();
        });
        subscribeBroadcast(TickBroadcast.class, tickIncoming -> {  //subscribe a new broadcast
            this.time = tickIncoming.getCurrentTick();             //get the current tick from tickBroadcast(message)
            while (currentIndex <= orderSchedules.length - 1 && time == orderSchedules[currentIndex].getTick()) { //if there are orders left, and in the order list of specific client it has an order in this tick
                Future<OrderReceipt> futuro = sendEvent(new BookOrderEvent(customer, time, orderSchedules[currentIndex].getBookTitle())); //make future order recipt and send event of a new book order
                OrderReceipt futuroReciept = futuro.get();  //when future will resolse, use the reciept
                if (!(futuroReciept instanceof NullReciept)) {  //if receipt isnt null
                    sendEvent(new DeliveryEvent(customer.getAddress(), customer.getDistance())); //send delivery event
                    customer.getCustomerReceiptList().add(futuro.get());  //add the receipt to the customer
                }
                currentIndex++; //one book is delivered
            }
        });
    }

}