package com.mdima.controller;

import com.mdima.AbstractControllerTest;
import com.mdima.model.FlightBooking;
import com.mdima.service.BookingService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@Transactional
public class BookingControllerMockTest extends AbstractControllerTest {

    @Mock
    private BookingService bookingService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        final BookingController bookingController = new BookingController(bookingService);
        setUp(bookingController);
    }

    @After
    public void tearDown() {
        this.bookingService = null;
    }

    private Collection<FlightBooking> getEntityStubList() {
        final Collection<FlightBooking> data = new ArrayList<>();
        data.add(getEntityStubData());

        return data;
    }

    private FlightBooking getEntityStubData() {
        return new FlightBooking("Paris", 2, 110);
    }

    @Test
    public void testFindAll() throws Exception {
        final Collection<FlightBooking> stubList = getEntityStubList();
        when(bookingService.findAll()).thenReturn(stubList);

        final String uri = "/api/bookings";

        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        verify(bookingService, times(1)).findAll();

        assertEquals(200, status);
        assertTrue(!content.trim().isEmpty());
    }

    @Test
    public void testGetBooking() throws Exception {
        final Long id = 1L;
        final FlightBooking flightBooking = getEntityStubData();
        when(bookingService.findOne(id)).thenReturn(flightBooking);

        final String uri = "/api/bookings/{id}";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();

        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        verify(bookingService, times(1)).findOne(id);

        assertEquals(200, status);
        assertTrue(!content.trim().isEmpty());
    }

    @Test
    public void testBookingNotFound() throws Exception {
        final Long id = Long.MAX_VALUE;
        when(bookingService.findOne(id)).thenReturn(null);

        final String uri = "/api/bookings/{id}";
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.get(uri, id)
                .accept(MediaType.APPLICATION_JSON)).andReturn();
        final String content = result.getResponse().getContentAsString();
        final int status = result.getResponse().getStatus();

        verify(bookingService, times(1)).findOne(id);

        assertEquals(404, status);
        assertTrue(content.trim().isEmpty());
    }

    @Test
    public void testCreateBooking() throws Exception {
        final FlightBooking flightBooking = getEntityStubData();
        when(bookingService.save(any(FlightBooking.class))).thenReturn(flightBooking);
        when(bookingService.findAll()).thenReturn(getEntityStubList());

        final String uri = "/api/bookings";
        final String request = super.mapToJson(flightBooking);
        final MvcResult result = mvc.perform(MockMvcRequestBuilders.post(uri).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON).content(request)).andReturn();

        final String content = result.getResponse().getContentAsString();
        int status = result.getResponse().getStatus();

        verify(bookingService, times(1)).save(any(FlightBooking.class));

        assertEquals(201, status);
        assertTrue(!content.trim().isEmpty());

        final FlightBooking[] flightBookings = mapFromJson(content, FlightBooking[].class);
        assertTrue(Arrays.asList(flightBookings).contains(flightBooking));
    }


}
