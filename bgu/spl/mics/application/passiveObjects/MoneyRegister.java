package bgu.spl.mics.application.passiveObjects;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import bgu.spl.mics.application.BookStoreRunner;

/**
 * Passive object representing the store finance management. 
 * It should hold a list of receipts issued by the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class MoneyRegister {
	
	private AtomicInteger totalEarnings;
	List<OrderReceipt> receiptList;
	private static MoneyRegister singleInstance = null;

	private MoneyRegister(){               //private constructor
		totalEarnings=new AtomicInteger(0);
		receiptList = new LinkedList<>();
	}
	
	/**
     * Retrieves the single instance of this class.
     */
	public static MoneyRegister getInstance()
    {
        // To ensure only one instance is created
        if (singleInstance == null)
        {
            singleInstance = new MoneyRegister();
        }
        return singleInstance;
    }
	/**
     * Saves an order receipt in the money register.
     * <p>   
     * @param r		The receipt to save in the money register.
     */
	public void file (OrderReceipt r) {
		receiptList.add(r);
	}
	
	/**
     * Retrieves the current total earnings of the store.  
     */
	public int getTotalEarnings() {
		return totalEarnings.get();
	}
	
	/**
     * Charges the credit card of the customer a certain amount of money.
     * <p>
     * @param amount 	amount to charge
     */
	public void chargeCreditCard(Customer c, int amount) {
		c.setAvailableCreditsAmount(amount);
		totalEarnings.getAndAdd(amount);
	}
	
	/**
     * Prints to a file named @filename a serialized object List<OrderReceipt> which holds all the order receipts 
     * currently in the MoneyRegister
     * This method is called by the main method in order to generate the output.
     */
	public void printOrderReceipts(String filename) {
		BookStoreRunner.writeObjectToFileName(filename, receiptList);
	}
}
