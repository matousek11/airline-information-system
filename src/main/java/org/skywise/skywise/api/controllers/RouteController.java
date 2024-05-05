package org.skywise.skywise.api.controllers;

import org.skywise.skywise.services.RouteService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RouteController {
    private final RouteService routeService;

    public RouteController() {
        this.routeService = new RouteService();
    }

    @GetMapping("/test")
    public String test() {
        //return routeService.generateRoutePlan().toString();
        return routeService.getWeather("LKTB");
        //return routeService.getAirportByICAO("LKPR");
    }
}
