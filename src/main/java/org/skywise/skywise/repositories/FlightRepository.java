package org.skywise.skywise.repositories;

import org.skywise.skywise.models.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {
}
