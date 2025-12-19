package com.infosys.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.*;
import com.infosys.Service.PractitionerProfileService;
import com.infosys.entity.PractitionerProfile;

@RestController
@RequestMapping("/api/practitioners")
public class PractitionerProfileController {

    private final PractitionerProfileService service;

    public PractitionerProfileController(PractitionerProfileService service) {
        this.service = service;
    }

    @PostMapping("/createPrac")
    public Object createProfile(@RequestBody PractitionerProfile profile) {
        return service.createProfile(profile);
    }


    @GetMapping("getPracProfile/{id}")
    public PractitionerProfile getProfile(@PathVariable Long id) {
        return service.getProfileById(id);
    }
    @GetMapping("getAllPrac")
    public List<PractitionerProfile> getAllProfiles() {
        return service.getAllProfiles();
    }
 // Controller: PractitionerProfileController.java
    @PutMapping("/ratePractitioner/{id}")
    public PractitionerProfile ratePractitioner(
            @PathVariable Long id,
            @RequestBody Map<String, Object> body) {
        Double rating = Double.valueOf(body.get("rating").toString());
        return service.updateRating(id, rating);
    }



}
