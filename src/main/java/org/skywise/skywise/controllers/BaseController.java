package org.skywise.skywise.controllers;

import org.skywise.skywise.repositories.FlightRepository;
import org.skywise.skywise.repositories.PlaneRepository;
import org.skywise.skywise.repositories.RepairRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BaseController {
    private final FlightRepository flightRepository;
    private final RepairRepository repairRepository;
    private final PlaneRepository planeRepository;

    @Autowired
    public BaseController(
            FlightRepository flightRepository,
            RepairRepository repairRepository,
            PlaneRepository planeRepository
    ) {
        this.flightRepository = flightRepository;
        this.repairRepository = repairRepository;
        this.planeRepository = planeRepository;
    }


    @GetMapping("/overview")
    public String overview(Model model) {
        long currentTimestamp = System.currentTimeMillis() / 1000;
        model.addAttribute("flights", flightRepository.findFlightsDepartingInFuture(currentTimestamp));
        model.addAttribute("repairs", repairRepository.findAll());
        model.addAttribute("planes", planeRepository.findAll());
        return "overview";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/403")
    public String forbidden() {
        return "403";
    }
}
