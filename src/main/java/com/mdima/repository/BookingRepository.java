package com.mdima.repository;

import com.mdima.model.FlightBooking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<FlightBooking, Long> {
    List<FlightBooking> findByPriceLessThan(double price);
}
