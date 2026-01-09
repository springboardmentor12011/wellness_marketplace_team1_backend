package com.wellness.backend.controller;

import com.wellness.backend.integration.openfda.OpenFdaClient;
import com.wellness.backend.integration.who.WhoApiClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/health-data")
public class HealthDataController {

    private final OpenFdaClient fdaClient;
    private final WhoApiClient whoApiClient;

    public HealthDataController(OpenFdaClient fdaClient,
                                WhoApiClient whoApiClient) {
        this.fdaClient = fdaClient;
        this.whoApiClient = whoApiClient;
    }

    @GetMapping("/fda")
    public ResponseEntity<?> fda(@RequestParam String symptom) {
        return ResponseEntity.ok(
                fdaClient.searchDrug(symptom)
        );
    }

    @GetMapping("/who")
    public ResponseEntity<String> who(@RequestParam String topic) {
        return ResponseEntity.ok(
                whoApiClient.getHealthGuidelines(topic)
        );
    }
}
