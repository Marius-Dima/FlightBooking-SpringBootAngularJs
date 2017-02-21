package com.mdima.controller;

import com.mdima.model.FlightBooking;
import com.mdima.service.BookingService;
import org.jsondoc.core.annotation.Api;
import org.jsondoc.core.annotation.ApiBodyObject;
import org.jsondoc.core.annotation.ApiMethod;
import org.jsondoc.core.annotation.ApiPathParam;
import org.jsondoc.core.pojo.ApiStage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(value = "api/")
@Api(name = "Flight Booking API"
        , description = "Provides a list of operations that manage flight bookings",
        stage = ApiStage.GA)
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping(value = "bookings")
    @ApiMethod(description = "Display all available flight bookings")
    public ResponseEntity<Collection<FlightBooking>> findAll() {
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

    @GetMapping(value = "bookings/{id}")
    @ApiMethod(description = "Display all available flight bookings")
    public ResponseEntity<FlightBooking> findOne(@PathVariable("id") Long id) {
        final FlightBooking flightBooking = bookingService.findOne(id);

        if (flightBooking == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(flightBooking, HttpStatus.OK);
    }

    @GetMapping(value = "bookings/affordable/{price}")
    @ApiMethod(description = "Get all flight bookings with ticket price less than the provided value")
    public ResponseEntity<Collection<FlightBooking>> getAffordableFlights(@ApiPathParam(name = "price", description = "Affordable price should be less than this value") @PathVariable double price) {
        return new ResponseEntity<>(bookingService.findByPriceLessThan(price), HttpStatus.OK);
    }

    @PostMapping(value = "bookings")
    @ApiMethod(description = "Create a new flight booking")
    public ResponseEntity<Collection<FlightBooking>> create(@ApiBodyObject(clazz = FlightBooking.class) @RequestBody FlightBooking hotelBooking) {
        bookingService.save(hotelBooking);
        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.CREATED);
    }

    @PutMapping(value = "bookings/{id}")
    @ApiMethod(description = "Update the flight booking which has the provided Id")
    public ResponseEntity<Collection<FlightBooking>> update(@ApiPathParam(name = "id", description = "Flight Booking id to be updated") @PathVariable("id") long id, @ApiBodyObject(clazz = FlightBooking.class) @RequestBody FlightBooking flightBooking) {
        final FlightBooking updatedFlightBooking = bookingService.update(flightBooking);

        if (updatedFlightBooking == null)
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);

        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

    @DeleteMapping(value = "bookings/{id}")
    @ApiMethod(description = "Remove the flight booking which has the provided Id")
    public ResponseEntity<Collection<FlightBooking>> remove(@ApiPathParam(name = "id", description = "Flight Booking id to be deleted") @PathVariable long id) {
        final FlightBooking currentBooking = bookingService.findOne(id);

        if (currentBooking == null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        bookingService.delete(id);

        return new ResponseEntity<>(bookingService.findAll(), HttpStatus.OK);
    }

}
