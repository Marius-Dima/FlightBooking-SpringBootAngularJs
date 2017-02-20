package com.mdima.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsondoc.core.annotation.ApiObject;
import org.jsondoc.core.annotation.ApiObjectField;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Data
@Entity
@NoArgsConstructor
@ApiObject
public class FlightBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @ApiObjectField(description = "The flight booking Id")
    private long id;

    @ApiObjectField(description = "The flight booking destination")
    private String destination;

    @ApiObjectField(description = "The number of tickets booked for the flight")
    private int numberOfTickets;

    @ApiObjectField(description = "The price of a ticket")
    private double price;

    public FlightBooking(String destination, int numberOfTickets, double price) {
        this.destination = destination;
        this.numberOfTickets = numberOfTickets;
        this.price = price;
    }

    public void udpateBooking(FlightBooking booking){
        this.destination = booking.destination;
        this.numberOfTickets = booking.numberOfTickets;
        this.price = booking.price;
    }

    public double getTotalPrice(){
        return price * numberOfTickets;
    }
}
