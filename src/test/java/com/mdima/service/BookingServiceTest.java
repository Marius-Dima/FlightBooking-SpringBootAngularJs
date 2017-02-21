package com.mdima.service;

import com.mdima.AbstractTest;
import com.mdima.model.FlightBooking;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.NoResultException;
import javax.transaction.Transactional;
import java.util.Collection;

import static org.junit.Assert.*;

@Transactional
public class BookingServiceTest extends AbstractTest {

    @Autowired
    private BookingService bookingService;

    @Before
    public void setUp() {
        bookingService.evictCache();
    }

    @Test
    public void testFindAll() {
        final Collection<FlightBooking> flightBookings = bookingService.findAll();

        assertNotNull(flightBookings);
        assertEquals("Expected size failed", 5, flightBookings.size());
    }

    @Test
    public void testFindOne() {
        final FlightBooking flightBooking = bookingService.findOne(195L);

        assertNotNull(flightBooking);
    }

    @Test
    public void testFindOneNotFound() {
        Long max = Long.MAX_VALUE;
        final FlightBooking notFound = bookingService.findOne(max);

        assertNull(notFound);
    }

    @Test
    public void testSave() {
        final FlightBooking flightBooking = new FlightBooking("Paris", 2, 135);
        final FlightBooking saved = bookingService.save(flightBooking);

        assertNotNull(saved);
        assertNotNull(saved.getId());
        assertNotNull(saved.getDestination());

        final Collection<FlightBooking> flightBookings = bookingService.findAll();
        assertEquals("Expected size failed:", 6, flightBookings.size());
    }

    @Test
    public void testUpdate() {
        final FlightBooking flightBooking = bookingService.findOne(195L);
        assertNotNull(flightBooking);

        final String updatedDestination = flightBooking.getDestination() + " updated";
        flightBooking.setDestination(updatedDestination);

        final FlightBooking updatedFlightBooking = bookingService.update(flightBooking);

        assertNotNull(updatedFlightBooking);
        assertEquals("Expected updated entity attribute unchanged: ", 195L, updatedFlightBooking.getId());
        assertEquals("Expected updated entity text attribute match: ", updatedDestination, updatedFlightBooking.getDestination());
    }

    @Test(expected = NoResultException.class)
    public void testUpdateNotFound() {
        final Long max = Long.MAX_VALUE;
        final FlightBooking flightBooking = new FlightBooking("Paris", 3, 200);
        flightBooking.setId(max);
        bookingService.update(flightBooking);
    }

    @Test
    public void testDelete() {
        final Long id = 195L;
        final FlightBooking existingBooking = bookingService.findOne(id);
        assertNotNull(existingBooking);

        bookingService.delete(id);

        final FlightBooking deletedBooking = bookingService.findOne(id);
        assertNull(deletedBooking);
    }

}
