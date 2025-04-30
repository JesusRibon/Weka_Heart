package com.example.weka_heart.controller;

import com.example.weka_heart.entities.PatientPrediction;
import com.example.weka_heart.entities.PredictionRequest;
import com.example.weka_heart.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prediction")
@CrossOrigin("*")
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    @PostMapping
    public String predict(@RequestBody PredictionRequest request) {
        return predictionService.predict(request);
    }

    @GetMapping("/patients")
    public List<PatientPrediction> getAllPredictions() {
        return predictionService.getAllPredictions();
    }
}
