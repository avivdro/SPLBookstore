package bgu.spl.mics.application.Messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.Customer;


public class BookOrderEvent implements Event{
    private String bookName;
    private Customer customer;
    private int timeTick;

    public BookOrderEvent(String bookName,Customer customer, int tick) {
        this.bookName=bookName;
        this.customer=customer;
        this.timeTick=tick;
    }

    public String getBookName() {
        return this.bookName;
    }

    public Customer getCustomer(){
        return this.customer;
    }

    public int getOrderTickTime() {

        return this.timeTick;

    }


}