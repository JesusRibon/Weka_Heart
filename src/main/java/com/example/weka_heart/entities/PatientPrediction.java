package com.example.weka_heart.entities;

public class PatientPrediction {

    private int id;
    private String resultado;
    private String advice;

    private String age;
    private String sex;
    private String histologicType;
    private String degreeOfDifferentiation;
    private String bone;
    private String boneMarrow;
    private String lung;
    private String pleura;
    private String peritoneum;
    private String liver;
    private String brain;
    private String skin;
    private String neck;
    private String supraclavicular;
    private String axillar;
    private String mediastinum;
    private String abdominal;

    public PatientPrediction(int id, String resultado, String advice, PredictionRequest request) {
        this.id = id;
        this.resultado = resultado;
        this.advice = advice;

        this.age = request.getAge();
        this.sex = request.getSex();
        this.histologicType = request.getHistologicType();
        this.degreeOfDifferentiation = request.getDegreeOfDifferentiation();
        this.bone = request.getBone();
        this.boneMarrow = request.getBoneMarrow();
        this.lung = request.getLung();
        this.pleura = request.getPleura();
        this.peritoneum = request.getPeritoneum();
        this.liver = request.getLiver();
        this.brain = request.getBrain();
        this.skin = request.getSkin();
        this.neck = request.getNeck();
        this.supraclavicular = request.getSupraclavicular();
        this.axillar = request.getAxillar();
        this.mediastinum = request.getMediastinum();
        this.abdominal = request.getAbdominal();
    }

    // Getters
    public int getId() { return id; }
    public String getResultado() { return resultado; }
    public String getAdvice() { return advice; }

    public String getAge() { return age; }
    public String getSex() { return sex; }
    public String getHistologicType() { return histologicType; }
    public String getDegreeOfDifferentiation() { return degreeOfDifferentiation; }
    public String getBone() { return bone; }
    public String getBoneMarrow() { return boneMarrow; }
    public String getLung() { return lung; }
    public String getPleura() { return pleura; }
    public String getPeritoneum() { return peritoneum; }
    public String getLiver() { return liver; }
    public String getBrain() { return brain; }
    public String getSkin() { return skin; }
    public String getNeck() { return neck; }
    public String getSupraclavicular() { return supraclavicular; }
    public String getAxillar() { return axillar; }
    public String getMediastinum() { return mediastinum; }
    public String getAbdominal() { return abdominal; }
}
