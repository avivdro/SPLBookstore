package bgu.spl.mics.application.Messages;

import bgu.spl.mics.Event;
import bgu.spl.mics.application.passiveObjects.OrderReceipt;


public class DeliveryEvent implements Event {
    private OrderReceipt receipt;
    private int distance;
    private String address;

    public DeliveryEvent(OrderReceipt receipt, int distance, String address) {
        this.receipt = receipt;
        this.distance = distance;
        this.address = address;

    }

    public int getDistance() {
        return distance;
    }

    public OrderReceipt getOrderReceipt() {

        return receipt;

    }

    public String getAddress() {
        return address;
    }
}
