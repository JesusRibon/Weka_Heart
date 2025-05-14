package com.example.weka_heart.controller;

import com.example.weka_heart.entities.PatientPrediction;
import com.example.weka_heart.entities.PredictionRequest;
import com.example.weka_heart.service.PredictionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/prediction")
@CrossOrigin("*") // Permite solicitudes desde cualquier origen (útil para desarrollo con Angular, React, etc.)
public class PredictionController {

    @Autowired
    private PredictionService predictionService;

    /**
     * Recibe un cuerpo con los datos del paciente y retorna el resultado de la predicción y recomendaciones.
     */
    @PostMapping
    public String predict(@RequestBody PredictionRequest request) {
        return predictionService.predict(request);
    }

    /**
     * Retorna la lista de todas las predicciones realizadas hasta ahora.
     */
    @GetMapping("/patients")
    public List<PatientPrediction> getAllPredictions() {
        return predictionService.getAllPredictions();
    }
}
