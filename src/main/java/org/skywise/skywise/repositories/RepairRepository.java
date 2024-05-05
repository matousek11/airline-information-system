package org.skywise.skywise.repositories;

import org.skywise.skywise.models.Plane;
import org.skywise.skywise.models.Repair;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface RepairRepository extends JpaRepository<Repair, Long> {
    ArrayList<Repair> findAllByPlane(Plane plane);
}
