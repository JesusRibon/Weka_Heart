package com.example.weka_heart.entities;

public class PredictionRequest {

    private String nombre;
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

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public int getSex() { return sex; }
    public void setSex(int sex) { this.sex = sex; }

    public int getChestPainType() { return chestPainType; }
    public void setChestPainType(int chestPainType) { this.chestPainType = chestPainType; }

    public int getRestingBloodPressure() { return restingBloodPressure; }
    public void setRestingBloodPressure(int restingBloodPressure) { this.restingBloodPressure = restingBloodPressure; }

    public int getSerumCholesterol() { return serumCholesterol; }
    public void setSerumCholesterol(int serumCholesterol) { this.serumCholesterol = serumCholesterol; }

    public int getFastingBloodSugar() { return fastingBloodSugar; }
    public void setFastingBloodSugar(int fastingBloodSugar) { this.fastingBloodSugar = fastingBloodSugar; }

    public int getRestingECG() { return restingECG; }
    public void setRestingECG(int restingECG) { this.restingECG = restingECG; }

    public int getMaximumHeartRate() { return maximumHeartRate; }
    public void setMaximumHeartRate(int maximumHeartRate) { this.maximumHeartRate = maximumHeartRate; }

    public int getExerciseInducedAngina() { return exerciseInducedAngina; }
    public void setExerciseInducedAngina(int exerciseInducedAngina) { this.exerciseInducedAngina = exerciseInducedAngina; }

    public double getOldpeak() { return oldpeak; }
    public void setOldpeak(double oldpeak) { this.oldpeak = oldpeak; }

    public int getSlope() { return slope; }
    public void setSlope(int slope) { this.slope = slope; }

    public int getNumberOfMajorVessels() { return numberOfMajorVessels; }
    public void setNumberOfMajorVessels(int numberOfMajorVessels) { this.numberOfMajorVessels = numberOfMajorVessels; }

    public int getThalassemia() { return thalassemia; }
    public void setThalassemia(int thalassemia) { this.thalassemia = thalassemia; }
}
