package com.mdima.service;

import com.mdima.model.FlightBooking;

import java.util.Collection;

public interface BookingService {

    Collection<FlightBooking> findByPriceLessThan(double price);

    Collection<FlightBooking> findAll();

    FlightBooking findOne(Long id);

    FlightBooking save(FlightBooking flightBooking);

    FlightBooking update(FlightBooking flightBooking);

    Collection<FlightBooking> saveAll(Collection<FlightBooking> flightBookings);

    void delete(Long id);

    void evictCache();
}
