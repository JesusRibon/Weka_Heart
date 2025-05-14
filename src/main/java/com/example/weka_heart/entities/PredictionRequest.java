package com.example.weka_heart.entities;

public class PredictionRequest {
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

    // Getters y setters
    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getHistologicType() { return histologicType; }
    public void setHistologicType(String histologicType) { this.histologicType = histologicType; }

    public String getDegreeOfDifferentiation() { return degreeOfDifferentiation; }
    public void setDegreeOfDifferentiation(String degreeOfDifferentiation) { this.degreeOfDifferentiation = degreeOfDifferentiation; }

    public String getBone() { return bone; }
    public void setBone(String bone) { this.bone = bone; }

    public String getBoneMarrow() { return boneMarrow; }
    public void setBoneMarrow(String boneMarrow) { this.boneMarrow = boneMarrow; }

    public String getLung() { return lung; }
    public void setLung(String lung) { this.lung = lung; }

    public String getPleura() { return pleura; }
    public void setPleura(String pleura) { this.pleura = pleura; }

    public String getPeritoneum() { return peritoneum; }
    public void setPeritoneum(String peritoneum) { this.peritoneum = peritoneum; }

    public String getLiver() { return liver; }
    public void setLiver(String liver) { this.liver = liver; }

    public String getBrain() { return brain; }
    public void setBrain(String brain) { this.brain = brain; }

    public String getSkin() { return skin; }
    public void setSkin(String skin) { this.skin = skin; }

    public String getNeck() { return neck; }
    public void setNeck(String neck) { this.neck = neck; }

    public String getSupraclavicular() { return supraclavicular; }
    public void setSupraclavicular(String supraclavicular) { this.supraclavicular = supraclavicular; }

    public String getAxillar() { return axillar; }
    public void setAxillar(String axillar) { this.axillar = axillar; }

    public String getMediastinum() { return mediastinum; }
    public void setMediastinum(String mediastinum) { this.mediastinum = mediastinum; }

    public String getAbdominal() { return abdominal; }
    public void setAbdominal(String abdominal) { this.abdominal = abdominal; }
}
