package org.skywise.skywise.repositories;

import org.skywise.skywise.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.fromTime > ?1")
    ArrayList<Flight> findFlightsDepartingInFuture(long currentTimestamp);
}
