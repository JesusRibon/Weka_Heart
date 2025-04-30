package com.example.weka_heart.service;

import com.example.weka_heart.entities.PatientPrediction;
import com.example.weka_heart.entities.PredictionRequest;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instances;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private Classifier model;
    private Instances datasetStructure;
    private List<PatientPrediction> predictionResults = new ArrayList<>();
    private int currentId = 1;

    // Usa tu propia API key válida aquí
    private String apiKey = "Bearer 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";

    public PredictionService() {
        try {
            // Cargar modelo desde archivo .model
            InputStream modelStream = getClass().getResourceAsStream("/models/weka_entrenados_F.model");
            ObjectInputStream ois = new ObjectInputStream(modelStream);
            model = (Classifier) ois.readObject();
            ois.close();

            // Cargar estructura del dataset desde .arff
            InputStream datasetStream = getClass().getResourceAsStream("/DataSet/heart-statlog.arff");
            datasetStructure = new Instances(new InputStreamReader(datasetStream));
            datasetStructure.setClassIndex(datasetStructure.numAttributes() - 1);

        } catch (Exception e) {
            throw new RuntimeException("Error al cargar el modelo o dataset: " + e.getMessage(), e);
        }
    }

    public String predict(PredictionRequest request) {
        try {
            DenseInstance instance = new DenseInstance(datasetStructure.numAttributes());
            instance.setDataset(datasetStructure);

            instance.setValue(0, request.getAge());
            instance.setValue(1, request.getSex());
            instance.setValue(2, request.getChestPainType());
            instance.setValue(3, request.getRestingBloodPressure());
            instance.setValue(4, request.getSerumCholesterol());
            instance.setValue(5, request.getFastingBloodSugar());
            instance.setValue(6, request.getRestingECG());
            instance.setValue(7, request.getMaximumHeartRate());
            instance.setValue(8, request.getExerciseInducedAngina());
            instance.setValue(9, request.getOldpeak());
            instance.setValue(10, request.getSlope());
            instance.setValue(11, request.getNumberOfMajorVessels());
            instance.setValue(12, request.getThalassemia());

            double result = model.classifyInstance(instance);
            int predictionClass = (int) Math.round(result);

            String prediction;
            String advice;

            if (predictionClass == 1) {
                prediction = "Predicción: Tiene enfermedad cardíaca.";
                advice = getAdviceFromAI(true);
            } else {
                prediction = "Predicción: No tiene enfermedad cardíaca.";
                advice = getAdviceFromAI(false);
            }

            PatientPrediction predictionResult = new PatientPrediction(
                    currentId++,
                    request.getNombre(),
                    prediction,
                    advice,
                    request
            );
            predictionResults.add(predictionResult);

            return prediction + (advice != null ? "\n\nConsejos de la IA:\n" + advice : "");

        } catch (Exception e) {
            throw new RuntimeException("Error en la predicción: " + e.getMessage(), e);
        }
    }

    public List<PatientPrediction> getAllPredictions() {
        return predictionResults;
    }

    private String getAdviceFromAI(boolean hasDisease) {
        try {
            OkHttpClient client = new OkHttpClient();

            MediaType mediaType = MediaType.parse("application/json");
            String prompt;

            if (hasDisease) {
                prompt = "Imagina que eres un médico cardiólogo experto. Una persona ha sido diagnosticada con enfermedad cardíaca. Proporcióname una lista de consejos médicos claros y detallados para mejorar su salud.";
            } else {
                prompt = "Imagina que eres un médico cardiólogo experto. Una persona no tiene enfermedad cardíaca. Proporcióname una lista de consejos médicos claros y detallados para mantener un corazón sano.";
            }

            JSONObject json = new JSONObject();
            json.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            JSONObject message = new JSONObject();
            message.put("role", "user");
            message.put("content", prompt);
            messages.put(message);

            json.put("messages", messages);

            RequestBody body = RequestBody.create(mediaType, json.toString());

            Request request = new Request.Builder()
                    .url("https://openrouter.ai/api/v1/chat/completions")
                    .post(body)
                    .addHeader("Authorization", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Error en la respuesta de OpenRouter: " + response);
            }

            String responseBody = response.body().string();
            JSONObject jsonObject = new JSONObject(responseBody);
            JSONArray choicesArray = jsonObject.getJSONArray("choices");
            JSONObject firstChoice = choicesArray.getJSONObject(0);
            JSONObject messageObject = firstChoice.getJSONObject("message");

            return messageObject.getString("content");

        } catch (Exception e) {
            throw new RuntimeException("Error al obtener consejo de la IA: " + e.getMessage(), e);
        }
    }
}
