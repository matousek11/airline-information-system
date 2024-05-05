package org.skywise.skywise.controllers;

import org.skywise.skywise.models.Plane;
import org.skywise.skywise.repositories.FlightRepository;
import org.skywise.skywise.repositories.PlaneRepository;
import org.skywise.skywise.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/plane")
public class PlaneController {
    private final PlaneRepository planeRepository;
    private final FlightRepository flightRepository;
    private final RepairRepository repairRepository;

    @Autowired
    public PlaneController(PlaneRepository planeRepository, FlightRepository flightRepository, RepairRepository repairRepository) {
        this.planeRepository = planeRepository;
        this.flightRepository = flightRepository;
        this.repairRepository = repairRepository;
    }

    @GetMapping("/create")
    public String createPlane() {
        return "plane/create";
    }

    @PostMapping("/create")
    public String createPlane(@RequestParam(required = false) String type, @RequestParam(required = false) String registration) {
        Plane plane = new Plane();
        plane.setType(type);
        plane.setRegistration(registration);
        planeRepository.save(plane);
        return "redirect:/plane/";
    }

    @GetMapping("/")
    public String planesList(Model model) {
        List<Plane> planes = planeRepository.findAll();
        model.addAttribute("planes", planes);
        return "plane/planes";
    }

    @GetMapping("/update/{planeID}")
    public String updatePlane(Model model, @PathVariable Long planeID) {
        Optional<Plane> planeOptional = planeRepository.findById(planeID);
        if (planeOptional.isEmpty()) {
            return "redirect:/plane/";
        }

        model.addAttribute("plane", planeOptional.get());
        return "plane/update";
    }

    @PostMapping("/update/{planeID}")
    public String updatePlane(@PathVariable Long planeID, @RequestParam(required = false) String type, @RequestParam(required = false) String registration) {
        Optional<Plane> planeOptional = planeRepository.findById(planeID);
        if (planeOptional.isPresent()) {
            Plane plane = planeOptional.get();
            plane.setType(type);
            plane.setRegistration(registration);
            planeRepository.save(plane);
        }

        return "redirect:/plane/";
    }

    @GetMapping("/detail/{repairID}")
    public String details(@PathVariable long repairID, Model model) {
        Optional<Plane> optionalPlane = planeRepository.findById(repairID);
        if (optionalPlane.isEmpty()) {
            throw new IllegalArgumentException("Repair does not exist");
        }

        Plane plane = optionalPlane.get();
        model.addAttribute("plane", plane);
        model.addAttribute("flights", flightRepository.findAllByPlane(plane));
        model.addAttribute("repairs", repairRepository.findAllByPlane(plane));
        return "plane/details";
    }

    @GetMapping("/delete/{planeID}")
    public String deletePlane(@PathVariable Long planeID) {
        planeRepository.deleteById(planeID);
        return "redirect:/plane/";
    }
}
