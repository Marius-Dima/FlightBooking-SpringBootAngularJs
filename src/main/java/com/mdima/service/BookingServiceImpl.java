package com.mdima.service;

import com.mdima.model.FlightBooking;
import com.mdima.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingServiceImpl(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    @Override
    @CachePut(value = "bookings", key = "#price")
    public Collection<FlightBooking> findByPriceLessThan(double price) {
        return bookingRepository.findByPriceLessThan(price);
    }

    @Override
    @CachePut(value = "bookings")
    public Collection<FlightBooking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    @CachePut(value = "bookings", key = "#id")
    public FlightBooking findOne(Long id) {
        return bookingRepository.findOne(id);
    }

    @Override
    public FlightBooking save(FlightBooking flightBooking) {
        return bookingRepository.save(flightBooking);
    }

    @Override
    public Collection<FlightBooking> saveAll(Collection<FlightBooking> flightBookings) {
        return bookingRepository.save(flightBookings);
    }

    @Override
    @CacheEvict(value = "bookings", key = "#id")
    public void delete(Long id) {
        bookingRepository.delete(id);
    }

    @Override
    @CacheEvict(value = "channel", allEntries = true)
    public void evictCache() {

    }
}
