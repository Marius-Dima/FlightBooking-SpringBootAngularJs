package com.mdima.controller;

import com.mdima.AbstractControllerTest;
import com.mdima.model.FlightBooking;
import com.mdima.service.BookingService;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;

import static org.junit.Assert.*;

@Transactional
public class BookingControler extends AbstractControllerTest {

    @Autowired
    private BookingService bookingService;

    @Before
    public void setUp() {
        super.setUp();
        bookingService.evictCache();
    }

    @Test
    public void testGetBookings() throws Exception {
        final String uri = "/api/bookings";

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri).accept(MediaType.APPLICATION_JSON)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        assertEquals(200, status);
        assertTrue(!content.trim().isEmpty());
    }

    @Test
    public void testGetBooking() throws Exception {
        Long id = 195L;
        final String uri = "/api/bookings/{id}";

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        assertEquals(200, status);
        assertTrue(!content.trim().isEmpty());
    }

    @Test
    public void testGetBookingNotFound() throws Exception {
        Long id = Long.MAX_VALUE;
        final String uri = "/api/bookings/{id}";

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id).accept(MediaType.APPLICATION_JSON)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        assertEquals(404, status);
        assertTrue(content.trim().isEmpty());
    }

    @Test
    public void testCreateBooking() throws Exception {
        String uri = "/api/bookings";
        final FlightBooking flightBooking = new FlightBooking("Paris", 2, 135);

        final String requestJson = super.mapToJson(flightBooking);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(requestJson)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        assertEquals(201, status);
        assertTrue(!content.trim().isEmpty());
    }

    @Test
    public void testUpdateBooking() throws Exception {
        String uri = "/api/bookings/{id}";
        Long id = 195L;

        final FlightBooking flightBooking = bookingService.findOne(195L);
        assertNotNull(flightBooking);
        final String updatedDestination = flightBooking.getDestination() + " updated";
        flightBooking.setDestination(updatedDestination);

        final String requestJson = super.mapToJson(flightBooking);

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.put(uri, id)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
                .content(requestJson)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        assertEquals(200, status);
        assertTrue(!content.trim().isEmpty());
    }


    @Test
    public void testDeleteBooking() throws Exception {
        final String uri = "/api/bookings/{id}";
        final Long id = 195L;

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.delete(uri, id)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        assertEquals(200, status);
        assertTrue(!content.trim().isEmpty());

        final FlightBooking deletedBooking = bookingService.findOne(id);
        assertNull(deletedBooking);
    }

}
