package bgu.spl.mics.application.passiveObjects;
import java.util.HashMap;
import java.util.Iterator;


/**
 * Passive data-object representing the store inventory.
 * It holds a collection of {@link BookInventoryInfo} for all the
 * books in the store.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add ONLY private fields and methods to this class as you see fit.
 */
public class Inventory {

	//fields
	static Inventory Inventory = new Inventory();
	//widsh collection to choose for bookinfos?
	private HashMap<String, BookInventoryInfo> inventoryBooks = new HashMap<>();
	
	
	
	//constructor
	private Inventory() {
	}
	
	
	/**
     * Retrieves the single instance of this class.
     */
	public static Inventory getInstance() {
		return Inventory;
	}
	
	/**
     * Initializes the store inventory. This method adds all the items given to the store
     * inventory.
     * <p>
     * @param inventory 	Data structure containing all data necessary for initialization
     * 						of the inventory.
     */
	public void load (BookInventoryInfo[ ] inventory ) {
		for (int i = 0; i < inventory.length; i++) {
			inventoryBooks.put(inventory[i].getBookTitle(), inventory[i])
		}
	}
	
	/**
     * Attempts to take one book from the store.
     * <p>
     * @param book 		Name of the book to take from the store
     * @return 	an {@link Enum} with options NOT_IN_STOCK and SUCCESSFULLY_TAKEN.
     * 			The first should not change the state of the inventory while the 
     * 			second should reduce by one the number of books of the desired type.
     */
	public OrderResult take (String book) {
		//synchronize the search, because if taken i need to update ==>read and write;
		synchronized (inventoryBooks) { //this happens on inventoryBooks hashmap
			if (inventoryBooks.containsKey(book)) { //check if contains the book
				BookInventoryInfo bookObj = inventoryBooks.get(book);
				
			
			if (bookObj.getAmountInInventory()>0) { //if the amount is >0
				bookObj.decreaseAmountInInventory(bookObj);
				return OrderResult.SUCCESSFULLY_TAKEN;
		}
		return OrderResult.NOT_IN_STOCK;
		}
		}
		throw new IllegalArgumentException("book does not exist");
	}
	
	
	
	/**
     * Checks if a certain book is available in the inventory.
     * <p>
     * @param book 		Name of the book.
     * @return the price of the book if it is available, -1 otherwise.
     */
	public int checkAvailabiltyAndGetPrice(String book) {
		synchronized (inventoryBooks) {
			if (inventoryBooks.containsKey(book)) {                  //check if contains the book
				BookInventoryInfo bookObj = inventoryBooks.get(book);
				
				if (bookObj.getAmountInInventory()>0) {              //if the amount is >0
					return bookObj.getPrice();
				}
			}
		return -1;
		}
	}	
	
	/**
     * 
     * <p>
     * Prints to a file name @filename a serialized object HashMap<String,Integer> which is a 
     * Map of all the books in the inventory. The keys of the Map (type {@link String})
     * should be the titles of the books while the values (type {@link Integer}) should be
     * their respective available amount in the inventory. 
     * This method is called by the main method in order to generate the output.
     */
	public void printInventoryToFile(String filename){         //this method is uncomplete, needs to figure out what is bookstore runner
		//and how to iterate over an hashmap automatically;
		HashMap<String, BookInventoryInfo> fileHash = new HashMap<>();
		for (int i = 0; i < inventoryBooks.size(); i++) {
			fileHash
		}
	}
}

















