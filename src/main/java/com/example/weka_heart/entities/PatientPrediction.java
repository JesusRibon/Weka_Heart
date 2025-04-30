package com.example.weka_heart.entities;

public class PatientPrediction {

    private int id;
    private String nombre;
    private String resultado;
    private String advice; // <<--- NUEVO CAMPO

    private int age;
    private int sex;
    private int chestPainType;
    private int restingBloodPressure;
    private int serumCholesterol;
    private int fastingBloodSugar;
    private int restingECG;
    private int maximumHeartRate;
    private int exerciseInducedAngina;
    private double oldpeak;
    private int slope;
    private int numberOfMajorVessels;
    private int thalassemia;

    public PatientPrediction(int id, String nombre, String resultado, String advice, PredictionRequest request) {
        this.id = id;
        this.nombre = nombre;
        this.resultado = resultado;
        this.advice = advice;

        this.age = request.getAge();
        this.sex = request.getSex();
        this.chestPainType = request.getChestPainType();
        this.restingBloodPressure = request.getRestingBloodPressure();
        this.serumCholesterol = request.getSerumCholesterol();
        this.fastingBloodSugar = request.getFastingBloodSugar();
        this.restingECG = request.getRestingECG();
        this.maximumHeartRate = request.getMaximumHeartRate();
        this.exerciseInducedAngina = request.getExerciseInducedAngina();
        this.oldpeak = request.getOldpeak();
        this.slope = request.getSlope();
        this.numberOfMajorVessels = request.getNumberOfMajorVessels();
        this.thalassemia = request.getThalassemia();
    }

    // Getters
    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public String getResultado() { return resultado; }
    public String getAdvice() { return advice; } // <<--- NUEVO GETTER

    public int getAge() { return age; }
    public int getSex() { return sex; }
    public int getChestPainType() { return chestPainType; }
    public int getRestingBloodPressure() { return restingBloodPressure; }
    public int getSerumCholesterol() { return serumCholesterol; }
    public int getFastingBloodSugar() { return fastingBloodSugar; }
    public int getRestingECG() { return restingECG; }
    public int getMaximumHeartRate() { return maximumHeartRate; }
    public int getExerciseInducedAngina() { return exerciseInducedAngina; }
    public double getOldpeak() { return oldpeak; }
    public int getSlope() { return slope; }
    public int getNumberOfMajorVessels() { return numberOfMajorVessels; }
    public int getThalassemia() { return thalassemia; }
}
