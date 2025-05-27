package com.example.weka_heart.service;

import com.example.weka_heart.entities.PatientPrediction;
import com.example.weka_heart.entities.PredictionRequest;
import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import weka.classifiers.Classifier;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.Attribute;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class PredictionService {

    private static final Logger logger = LoggerFactory.getLogger(PredictionService.class);

    private Classifier model;
    private Instances datasetStructure;
    private List<PatientPrediction> predictionResults = new ArrayList<>();
    private int currentId = 1;

    private String apiKey = "Bearer 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";

    public PredictionService() {
        try {
            loadModel();
            loadDatasetStructure();
        } catch (Exception e) {
            logger.error("Error al cargar modelo o dataset", e);
            throw new RuntimeException("Error al cargar modelo o dataset: " + e.getMessage(), e);
        }
    }

    private void loadModel() throws Exception {
        try (InputStream modelStream = getClass().getResourceAsStream("/models/cancerModelo.model");
             ObjectInputStream ois = new ObjectInputStream(modelStream)) {
            model = (Classifier) ois.readObject();
            logger.info("Modelo cargado exitosamente");
        }
    }

    private void loadDatasetStructure() throws Exception {
        try (InputStream datasetStream = getClass().getResourceAsStream("/DataSet/primary.tumor.arff");
             InputStreamReader reader = new InputStreamReader(datasetStream)) {
            datasetStructure = new Instances(reader);
            datasetStructure.setClassIndex(datasetStructure.numAttributes() - 1);
            logger.info("Estructura del dataset cargada exitosamente");
            printNominalAttributes();
        }
    }

    private void printNominalAttributes() {
        logger.info("Atributos nominales del dataset:");
        for (int i = 0; i < datasetStructure.numAttributes(); i++) {
            Attribute attr = datasetStructure.attribute(i);
            if (attr.isNominal()) {
                logger.info("Atributo {}: {} valores", attr.name(), attr.numValues());
                for (int j = 0; j < attr.numValues(); j++) {
                    logger.info("  - {}", attr.value(j));
                }
            }
        }
    }

    public String predict(PredictionRequest request) {
        try {
            DenseInstance instance = new DenseInstance(datasetStructure.numAttributes());
            instance.setDataset(datasetStructure);

            setNominalAttributes(instance, request);

            double result = model.classifyInstance(instance);
            String predictedClass = datasetStructure.classAttribute().value((int) result);

            if (predictedClass == null || predictedClass.isEmpty()) {
                throw new IllegalArgumentException("La clase predicha es nula o vacía");
            }

            String prediction = "Predicción: " + translateClass(predictedClass);
            String advice = getAdviceFromAI(predictedClass);

            PatientPrediction predictionResult = new PatientPrediction(
                    currentId++,
                    prediction,
                    advice,
                    request
            );
            predictionResults.add(predictionResult);

            logger.info("Predicción realizada para paciente con ID {}: {}", currentId, prediction);
            logger.info("Consejo de IA: {}", advice != null ? advice : "No se obtuvo");

            return prediction + (advice != null ? "\n\nConsejos de la IA:\n" + advice : "");

        } catch (Exception e) {
            logger.error("Error en la predicción", e);
            throw new RuntimeException("Error en la predicción: " + e.getMessage(), e);
        }
    }

    private void setNominalAttributes(DenseInstance instance, PredictionRequest request) {
        // Age - map to the exact categories expected by the model
        String ageValue;
        int age = Integer.parseInt(request.getAge()); // Convertir String a int
        if (age < 30) {
            ageValue = "<30";
        } else if (age <= 59) {
            ageValue = "30-59";
        } else {
            ageValue = ">=60";
        }
        instance.setValue(datasetStructure.attribute("age"), datasetStructure.attribute("age").indexOfValue(ageValue));

        // Sex - use exact values from ARFF
        String sexValue = request.getSex().equals("1") ? "male" : "female";
        Attribute sexAttr = datasetStructure.attribute("sex");
        int sexIndex = sexAttr.indexOfValue(sexValue);
        if (sexIndex == -1) {
            logger.error("Valores permitidos para 'sex':");
            for (int i = 0; i < sexAttr.numValues(); i++) {
                logger.error("  - {}", sexAttr.value(i));
            }
            throw new IllegalArgumentException("Valor inválido para 'sex': " + sexValue);
        }
        instance.setValue(sexAttr, sexIndex);

        // Binary attributes - use exact names and values from ARFF
        setBinaryAttributeValue(instance, "bone", request.getBone());
        setBinaryAttributeValue(instance, "bone-marrow", request.getBoneMarrow());
        setBinaryAttributeValue(instance, "lung", request.getLung());
        setBinaryAttributeValue(instance, "pleura", request.getPleura());
        setBinaryAttributeValue(instance, "peritoneum", request.getPeritoneum());
        setBinaryAttributeValue(instance, "liver", request.getLiver());
        setBinaryAttributeValue(instance, "brain", request.getBrain());
        setBinaryAttributeValue(instance, "skin", request.getSkin());
        setBinaryAttributeValue(instance, "neck", request.getNeck());
        setBinaryAttributeValue(instance, "supraclavicular", request.getSupraclavicular());
        setBinaryAttributeValue(instance, "axillar", request.getAxillar());
        setBinaryAttributeValue(instance, "mediastinum", request.getMediastinum());
        setBinaryAttributeValue(instance, "abdominal", request.getAbdominal());
    }

    private void setBinaryAttributeValue(DenseInstance instance, String attributeName, String attributeValue) {
        String value = attributeValue.equals("1") ? "yes" : "no";
        Attribute attr = datasetStructure.attribute(attributeName);
        if (attr == null) {
            throw new IllegalArgumentException("Atributo '" + attributeName + "' no encontrado en el dataset");
        }
        int valueIndex = attr.indexOfValue(value);
        if (valueIndex == -1) {
            logger.error("Valores permitidos para '{}':", attributeName);
            for (int i = 0; i < attr.numValues(); i++) {
                logger.error("  - {}", attr.value(i));
            }
            throw new IllegalArgumentException("Valor inválido para '" + attributeName + "': " + value);
        }
        instance.setValue(attr, valueIndex);
    }

    private String translateClass(String predictedClass) {
        switch (predictedClass) {
            case "lung": return "Tumor pulmonar";
            case "head and neck": return "Tumor de cabeza y cuello";
            case "esophagus": return "Tumor esofágico";
            case "thyroid": return "Tumor tiroideo";
            case "stomach": return "Tumor gástrico";
            case "duoden and sm.int": return "Tumor de duodeno e intestino delgado";
            case "colon": return "Tumor de colon";
            case "rectum": return "Tumor rectal";
            case "anus": return "Tumor anal";
            case "salivary glands": return "Tumor de glándulas salivales";
            case "pancreas": return "Tumor pancreático";
            case "gallbladder": return "Tumor de vesícula biliar";
            case "liver": return "Tumor hepático";
            case "kidney": return "Tumor renal";
            case "bladder": return "Tumor de vejiga";
            case "testis": return "Tumor testicular";
            case "prostate": return "Tumor de próstata";
            case "ovary": return "Tumor ovárico";
            case "corpus uteri": return "Tumor de cuerpo uterino";
            case "cervix uteri": return "Tumor de cuello uterino";
            case "vagina": return "Tumor vaginal";
            case "breast": return "Tumor de mama";
            default: return "Tipo de tumor: " + predictedClass;
        }
    }

    public List<PatientPrediction> getAllPredictions() {
        return new ArrayList<>(predictionResults);
    }

    private String getAdviceFromAI(String predictedClass) {
        try {
            OkHttpClient client = new OkHttpClient();

            String translatedClass = translateClass(predictedClass);
            String prompt = "Actúas como un médico oncólogo. Un paciente ha sido diagnosticado con " +
                    translatedClass + ". Proporciona recomendaciones médicas específicas para este tipo de tumor.";

            JSONObject json = new JSONObject();
            json.put("model", "gpt-3.5-turbo");

            JSONArray messages = new JSONArray();
            messages.put(new JSONObject()
                    .put("role", "user")
                    .put("content", prompt)
            );
            json.put("messages", messages);

            RequestBody body = RequestBody.create(MediaType.parse("application/json"), json.toString());

            Request request = new Request.Builder()
                    .url("https://openrouter.ai/api/v1/chat/completions")
                    .post(body)
                    .addHeader("Authorization", apiKey)
                    .addHeader("Content-Type", "application/json")
                    .build();

            Response response = client.newCall(request).execute();

            if (!response.isSuccessful()) {
                throw new RuntimeException("Error al contactar OpenRouter: " + response);
            }

            ResponseBody responseBody = response.body();
            if (responseBody == null) {
                throw new RuntimeException("Respuesta de OpenRouter vacía.");
            }

            String responseString = responseBody.string();
            JSONObject jsonObject = new JSONObject(responseString);
            JSONArray choicesArray = jsonObject.optJSONArray("choices");

            if (choicesArray == null || choicesArray.isEmpty()) {
                throw new RuntimeException("No se encontraron opciones de respuesta de IA.");
            }

            JSONObject firstChoice = choicesArray.getJSONObject(0);
            JSONObject messageObject = firstChoice.optJSONObject("message");

            return messageObject != null ? messageObject.optString("content", "Sin contenido") : "Respuesta sin mensaje";

        } catch (Exception e) {
            logger.error("Error al obtener consejo de IA para clase {}", predictedClass, e);
            return "No se pudo obtener una recomendación de la IA en este momento.";
        }
    }
}