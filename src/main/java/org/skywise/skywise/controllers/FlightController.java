package org.skywise.skywise.controllers;

import groovyjarjarcommonscli.MissingArgumentException;
import org.skywise.skywise.models.Flight;
import org.skywise.skywise.models.Plane;
import org.skywise.skywise.models.Repair;
import org.skywise.skywise.models.User;
import org.skywise.skywise.repositories.FlightRepository;
import org.skywise.skywise.repositories.PlaneRepository;
import org.skywise.skywise.repositories.RepairRepository;
import org.skywise.skywise.repositories.UserRepository;
import org.skywise.skywise.services.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/flight")
public class FlightController {
    private final FlightRepository flightRepository;
    private final UserRepository userRepository;
    private final PlaneRepository planeRepository;
    private final RepairRepository repairRepository;
    private final RouteService routeService;

    @Autowired
    public FlightController(
            FlightRepository flightRepository,
            UserRepository userRepository,
            PlaneRepository planeRepository,
            RepairRepository repairRepository) {
        this.flightRepository = flightRepository;
        this.userRepository = userRepository;
        this.planeRepository = planeRepository;
        this.routeService = new RouteService();
        this.repairRepository = repairRepository;
    }

    @GetMapping("/create")
    public String createFlight(Model model) {
        model.addAttribute("pilots", userRepository.findAllByRole("PILOT"));
        model.addAttribute("copilots", userRepository.findAllByRole("PILOT"));
        model.addAttribute("flightAttendants", userRepository.findAllByRole("FLIGHT ATTENDANT"));
        model.addAttribute("planes", planeRepository.findAll());
        return "flight/create";
    }

    @PostMapping("/create")
    public String createFlight(
            @RequestParam() long pilotID,
            @RequestParam() long copilotID,
            @RequestParam() long flightAttendantID,
            @RequestParam() long planeID,
            @RequestParam() String fromICAO,
            @RequestParam() String toICAO,
            @RequestParam() String fromTime,
            @RequestParam() String toTime,
            @RequestParam() String fromGate,
            @RequestParam() String toGate,
            @RequestParam(required = false) String route
    ) throws MissingArgumentException {
        Optional<User> optionalPilot = userRepository.findById(pilotID);
        Optional<User> optionalCopilot = userRepository.findById(copilotID);
        Optional<User> optionalFlightAttendant = userRepository.findById(flightAttendantID);
        Optional<Plane> optionalPlane = planeRepository.findById(planeID);

        if (
                optionalPilot.isEmpty() ||
                optionalCopilot.isEmpty() ||
                optionalFlightAttendant.isEmpty() ||
                optionalPlane.isEmpty()
        ) {
            throw new MissingArgumentException("Pilot, copilot, flight attendant or plane is not valid.");
        }

        long fromTimestamp = getTimestamp(fromTime);
        long toTimestamp = getTimestamp(toTime);


        Flight flight = new Flight();
        flight.setPilot(optionalPilot.get());
        flight.setCopilot(optionalCopilot.get());
        flight.setFlightAttendant(optionalFlightAttendant.get());
        flight.setPlane(optionalPlane.get());
        flight.setFromICAO(fromICAO);
        flight.setToICAO(toICAO);
        flight.setFromTime(fromTimestamp);
        flight.setToTime(toTimestamp);
        flight.setFromGate(fromGate);
        flight.setToGate(toGate);
        flight.setRoute("sadasd");
        flightRepository.save(flight);

        return "redirect:/flight/";
    }

    @GetMapping("/update/{flightID}")
    public String updateFlight(Model model, @PathVariable long flightID) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightID);

        if (optionalFlight.isEmpty()) {
            throw new IllegalArgumentException("Flight with ID " + flightID + " doesn't exists.");
        }

        model.addAttribute("flight", optionalFlight.get());
        model.addAttribute("pilots", userRepository.findAllByRole("PILOT"));
        model.addAttribute("copilots", userRepository.findAllByRole("PILOT"));
        model.addAttribute("flightAttendants", userRepository.findAllByRole("FLIGHT ATTENDANT"));
        model.addAttribute("planes", planeRepository.findAll());
        return "flight/update";
    }

    @PostMapping("/update/{flightID}")
    public String updateFlight(
            @PathVariable long flightID,
            @RequestParam() long pilotID,
            @RequestParam() long copilotID,
            @RequestParam() long flightAttendantID,
            @RequestParam() long planeID,
            @RequestParam() String fromICAO,
            @RequestParam() String toICAO,
            @RequestParam() String fromTime,
            @RequestParam() String toTime,
            @RequestParam() String fromGate,
            @RequestParam() String toGate,
            @RequestParam(required = false) String route
    ) throws MissingArgumentException {
        Optional<Flight> optionalFlight = flightRepository.findById(flightID);
        Optional<User> optionalPilot = userRepository.findById(pilotID);
        Optional<User> optionalCopilot = userRepository.findById(copilotID);
        Optional<User> optionalFlightAttendant = userRepository.findById(flightAttendantID);
        Optional<Plane> optionalPlane = planeRepository.findById(planeID);

        if (
                optionalFlight.isEmpty() ||
                optionalPilot.isEmpty() ||
                optionalCopilot.isEmpty() ||
                optionalFlightAttendant.isEmpty() ||
                optionalPlane.isEmpty()
        ) {
            throw new MissingArgumentException("Flight, pilot, copilot, flight attendant or plane is not valid.");
        }

        long fromTimestamp = getTimestamp(fromTime);
        long toTimestamp = getTimestamp(toTime);

        Flight flight = optionalFlight.get();
        flight.setPilot(optionalPilot.get());
        flight.setCopilot(optionalCopilot.get());
        flight.setFlightAttendant(optionalFlightAttendant.get());
        flight.setPlane(optionalPlane.get());
        flight.setFromICAO(fromICAO);
        flight.setToICAO(toICAO);
        flight.setFromTime(fromTimestamp);
        flight.setToTime(toTimestamp);
        flight.setFromGate(fromGate);
        flight.setToGate(toGate);
        flight.setRoute(route);
        flightRepository.save(flight);

        return "redirect:/flight/";
    }

    @GetMapping("/")
    public String flightsList(Model model) {
        model.addAttribute("flights", flightRepository.findAll());
        return "flight/flights";
    }

    @GetMapping("/detail/{flightID}")
    public String flightDetails(Model model, @PathVariable long flightID) {
        Optional<Flight> optionalFlight = flightRepository.findById(flightID);
        if (optionalFlight.isEmpty()) {
            throw new IllegalArgumentException("Flight with ID " + flightID + " doesn't exists.");
        }
        Flight flight = optionalFlight.get();

        String fromMetar = routeService.getWeather(flight.getFromICAO());
        String toMetar = routeService.getWeather(flight.getToICAO());

        ArrayList<Repair> repairs = repairRepository.findAllByPlane(flight.getPlane());

        model.addAttribute("repairs", repairs);
        model.addAttribute("flight", flight);
        model.addAttribute("fromMetar", fromMetar);
        model.addAttribute("toMetar", toMetar);
        return "flight/details";
    }

    @GetMapping("/delete/{flightID}")
    public String deleteFlight(@PathVariable Long flightID) {
        flightRepository.deleteById(flightID);
        return "redirect:/flight/";
    }

    private long getTimestamp(String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        LocalDateTime dateTime = LocalDateTime.parse(time, formatter);
        return dateTime.toInstant(ZoneOffset.UTC).getEpochSecond();
    }
}
