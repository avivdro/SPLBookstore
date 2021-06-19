package bgu.spl.mics.application.Messages;


import bgu.spl.mics.Event;

public class canBuyThisBookEvent implements Event {
    private String bookName;
    private int availableAmount;

    public canBuyThisBookEvent(String name, int customersAmmount) {
        this.bookName=name;
        this.availableAmount=customersAmmount;
    }

    public String getBookName() {
        return bookName;
    }

    public int getAvailableCreditAmount() {
        return availableAmount;
    }
}