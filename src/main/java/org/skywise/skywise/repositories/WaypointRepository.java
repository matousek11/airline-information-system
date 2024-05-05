package org.skywise.skywise.repositories;

import org.skywise.skywise.models.Waypoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WaypointRepository extends JpaRepository<Waypoint, Long> {
}
