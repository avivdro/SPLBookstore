package bgu.spl.mics.application.services;

import java.util.List;

import bgu.spl.mics.MicroService;
import bgu.spl.mics.application.passiveObjects.Customer;

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
public class APIService extends MicroService{
	
	private List<orderSchedule> orderSchedule;
	Customer customer;
	String name;
	
	public APIService(String name,Customer customer, List orderSchedule) {
		super(name);
		this.name = name;
		this.customer = customer;
		this.orderSchedule
		
		
		
	}

	@Override
	protected void initialize() {
		// TODO Implement this
		
	}

}
