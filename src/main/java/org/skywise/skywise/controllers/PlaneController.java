package org.skywise.skywise.controllers;

import org.skywise.skywise.models.Plane;
import org.skywise.skywise.repositories.PlaneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/plane")
public class PlaneController {
    @Autowired
    private PlaneRepository planeRepository;

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

    @GetMapping("/delete/{planeID}")
    public String updatePlane(@PathVariable Long planeID) {
        planeRepository.deleteById(planeID);
        return "redirect:/plane/";
    }
}
