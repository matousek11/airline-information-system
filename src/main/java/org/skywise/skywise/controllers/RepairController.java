package org.skywise.skywise.controllers;

import org.skywise.skywise.models.Plane;
import org.skywise.skywise.models.Repair;
import org.skywise.skywise.models.User;
import org.skywise.skywise.repositories.PlaneRepository;
import org.skywise.skywise.repositories.RepairRepository;
import org.skywise.skywise.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Optional;

@Controller
@RequestMapping("/repair")
public class RepairController {
    private final RepairRepository repairRepository;
    private final UserRepository userRepository;
    private final PlaneRepository planeRepository;

    @Autowired
    public RepairController(
            RepairRepository repairRepository,
            UserRepository userRepository,
            PlaneRepository planeRepository
    ) {
        this.repairRepository = repairRepository;
        this.userRepository = userRepository;
        this.planeRepository = planeRepository;
    }

    @GetMapping("/")
    public String repairsList(Model model) {
        model.addAttribute("repairs", repairRepository.findAll());
        return "repair/repairs";
    }

    @GetMapping("/create")
    public String createRepair(Model model) {
        ArrayList<User> technicians = userRepository.findAllByRole("TECHNICIAN");
        model.addAttribute("technicians", technicians);
        model.addAttribute("planes", planeRepository.findAll());
        return "repair/create";
    }

    @PostMapping("/create")
    public String createRepair(
            @RequestParam() long userID,
            @RequestParam() long planeID,
            @RequestParam() String repairName,
            @RequestParam() String repairDescription
    ) throws Exception {
        Repair repair = new Repair();
        Optional<User> optionalUser = userRepository.findById(userID);
        Optional<Plane> optionalPlane = planeRepository.findById(planeID);

        if (optionalUser.isEmpty() || optionalPlane.isEmpty()) {
            throw new Exception("User or plane does not exists");
        }

        repair.setUser(optionalUser.get());
        repair.setPlane(optionalPlane.get());
        repair.setRepairName(repairName);
        repair.setRepairDescription(repairDescription);
        repairRepository.save(repair);

        return "redirect:/repair/";
    }

    @GetMapping("/update/{repairID}")
    public String updateRepair(@PathVariable long repairID, Model model) {
        Optional<Repair> optionalRepair = repairRepository.findById(repairID);
        if (optionalRepair.isEmpty()) {
            throw new IllegalArgumentException("Repair does not exist");
        }

        Repair repair = optionalRepair.get();
        model.addAttribute("repair", repair);
        ArrayList<User> technicians = userRepository.findAllByRole("TECHNICIAN");
        model.addAttribute("technicians", technicians);
        model.addAttribute("planes", planeRepository.findAll());

        return "repair/update";
    }

    @PostMapping("/update/{repairID}")
    public String updateRepair(
            @PathVariable long repairID,
            @RequestParam() long userID,
            @RequestParam() long planeID,
            @RequestParam() String repairName,
            @RequestParam() String repairDescription
    ) throws Exception {
        Optional<Repair> optionalRepair = repairRepository.findById(repairID);
        Optional<User> optionalUser = userRepository.findById(userID);
        Optional<Plane> optionalPlane = planeRepository.findById(planeID);
        if (
                optionalRepair.isEmpty() ||
                optionalPlane.isEmpty() ||
                optionalUser.isEmpty()
        ) {
            throw new Exception("User, plane or repair does not exists");
        }

        Repair repair = optionalRepair.get();

        repair.setUser(optionalUser.get());
        repair.setPlane(optionalPlane.get());
        repair.setRepairName(repairName);
        repair.setRepairDescription(repairDescription);
        repairRepository.save(repair);

        return "redirect:/repair/";
    }

    @GetMapping("/delete/{repairID}")
    public String deleteUser(@PathVariable Long repairID) {
        repairRepository.deleteById(repairID);
        return "redirect:/repair/";
    }
}
