package com.mdima.service;

import com.mdima.model.FlightBooking;
import com.mdima.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Service;

import javax.persistence.NoResultException;
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
    public FlightBooking update(FlightBooking flightBooking) {
        final FlightBooking existingBooking = findOne(flightBooking.getId());

        if (existingBooking == null)
            throw new NoResultException("Requested entity was not found");

        existingBooking.udpateBooking(flightBooking);

        return bookingRepository.save(existingBooking);
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
    @CacheEvict(value = "bookings", allEntries = true)
    public void evictCache() {

    }
}
