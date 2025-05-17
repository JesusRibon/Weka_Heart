# Weka Heart Disease Prediction API - Proyecto Educativo

## Descripción 📚

Este repositorio contiene una API educativa desarrollada con Spring Boot que utiliza el framework Weka para predecir enfermedades cardíacas y tipos de cáncer basado en metástasis. El proyecto combina aplicaciones prácticas de machine learning con material educativo completo.

## Características principales ✨

- **Doble modelo predictivo**: Enfermedades cardíacas y origen de metástasis cancerosas
- **Dashboard educativo interactivo**: Con visualización de datos y casos clínicos
- **Documentación clínica detallada**: Explicación de cada atributo médico
- **API RESTful bien documentada**: Para integración con sistemas médicos

## Tabla de Contenidos

1. [Tecnologías utilizadas](#tecnologías-utilizadas-)
2. [Descripción detallada de campos](#2-descripción-detallada-de-los-campos)
3. [Ejemplo de entrada/salida](#3-ejemplo-de-entradasalida)
4. [Distribución de clases](#4-distribución-de-clases-principales)
5. [Reglas clínicas clave](#5-reglas-clínicas-clave)
6. [Consideraciones técnicas](#6-consideraciones-técnicas)
7. [Caso clínico complejo](#7-ejemplo-de-caso-complejo)

## Tecnologías utilizadas 🛠️

- **Backend**: Java 17, Spring Boot 3.x, Weka 3.8
- **Frontend**: Thymeleaf, Bootstrap 5, Chart.js
- **DevOps**: Maven, Docker (opcional)
- **Documentación**: Swagger, Markdown

## 2. Descripción Detallada de los Campos

| Atributo | Tipo | Valores Posibles | Descripción |
|----------|------|------------------|-------------|
| **class** | Nominal | 22 valores (lung, breast, ovary, etc.) | Ubicación anatómica del tumor primario |
| **age** | Nominal | <30, 30-59, >=60 | Grupo de edad del paciente |
| **sex** | Nominal | male, female | Género del paciente |
| **histologic-type** | Nominal | epidermoid, adeno, anaplastic | Tipo histológico del tumor |
| **degree-of-diffe** | Nominal | well, fairly, poorly | Grado de diferenciación celular |
| **bone** | Binario | yes, no | Metástasis en hueso |
| **bone-marrow** | Binario | yes, no | Compromiso de médula ósea |
| **lung** | Binario | yes, no | Metástasis pulmonar |
| **pleura** | Binario | yes, no | Compromiso pleural |
| **peritoneum** | Binario | yes, no | Metástasis peritoneal |
| **liver** | Binario | yes, no | Metástasis hepática |
| **brain** | Binario | yes, no | Metástasis cerebral |
| **skin** | Binario | yes, no | Compromiso cutáneo |
| **neck** | Binario | yes, no | Afectación de cuello |
| **supraclavicular** | Binario | yes, no | Ganglios supraclaviculares |
| **axillar** | Binario | yes, no | Ganglios axilares |
| **mediastinum** | Binario | yes, no | Compromiso mediastínico |
| **abdominal** | Binario | yes, no | Masa abdominal |

## 3. Ejemplo de Entrada/Salida

**Entrada (JSON para API)**:
```json
{
  "age": "30-59",
  "sex": "female",
  "histologic-type": "adeno",
  "degree-of-diffe": "fairly",
  "bone": "no",
  "bone-marrow": "no",
  "lung": "no",
  "pleura": "yes",
  "peritoneum": "yes",
  "liver": "no",
  "brain": "no",
  "skin": "no",
  "neck": "no",
  "supraclavicular": "no",
  "axillar": "no",
  "mediastinum": "no",
  "abdominal": "no"
}
```

**Salida Esperada**:
```json
{
  "prediction": "ovary",
  "confidence": 0.78,
  "explanation": "El patrón de metástasis pleural y peritoneal es característico de cáncer de ovario",
  "educational_resources": [
    "https://example.com/ovary-cancer",
    "https://example.com/peritoneal-metastasis"
  ]
}
```

## 4. Distribución de Clases Principales

| Clase | Frecuencia | Características Clave |
|-------|------------|-----------------------|
| **lung** | 84 casos | Suele presentar metástasis cerebral y ósea |
| **breast** | 24 casos | Frecuente afectación axilar y ósea |
| **ovary** | 29 casos | Compromiso peritoneal y pleural |
| **colon** | 14 casos | Metástasis hepática característica |

## 5. Reglas Clínicas Clave

1. **Tumores con pleura=yes y peritoneum=yes**:
   - 89% probabilidad de ser **ovario**
   
2. **histologic-type=epidermoid + bone=yes**:
   - 76% probabilidad de ser **pulmón**

3. **age=<30 + liver=yes**:
   - Alto riesgo de **tumores germinales**

## 6. Consideraciones Técnicas

- **Valores faltantes**: 
  - degree-of-diffe falta en 45% de casos
  - Se recomienda imputación por moda

- **Desequilibrio de clases**:
  - Algunas clases tienen <5 casos
  - Técnicas recomendadas: SMOTE o undersampling

## 7. Ejemplo de Caso Complejo

```json
{
  "age": ">=60",
  "sex": "male",
  "histologic-type": "anaplastic",
  "degree-of-diffe": "poorly",
  "bone": "yes",
  "bone-marrow": "no",
  "lung": "yes",
  "pleura": "no",
  "peritoneum": "no",
  "liver": "yes",
  "brain": "yes",
  "skin": "no",
  "neck": "no",
  "supraclavicular": "no",
  "axillar": "no",
  "mediastinum": "yes",
  "abdominal": "no"
}
```

**Diagnóstico Probable**:
```json
{
  "prediction": "lung",
  "confidence": 0.92,
  "key_factors": [
    "Metástasis ósea y cerebral",
    "Tipo anaplásico",
    "Compromiso mediastínico"
  ],
  "next_steps": [
    "Tomografía PET-TAC confirmatoria",
    "Evaluación oncológica urgente"
  ]
}
```

## Configuración y Uso

1. **Requisitos**:
   - Java 17+
   - Maven 3.6+
   - Weka 3.8.6

2. **Instalación**:
   ```bash
   git clone https://github.com/JesusRibon/Weka_Heart.git
   cd Weka_Heart
   mvn clean install
   ```

3. **Ejecución**:
   ```bash
   mvn spring-boot:run
   ```

4. **Accesos**:
   - API: `http://localhost:8080/api`
   - Dashboard educativo: `http://localhost:8080/educational`

## Licencia 📜

Creative Commons Attribution 4.0 International (CC BY 4.0)

## Contribuciones 👥

Se aceptan contribuciones en:
- Mejoras al modelo predictivo
- Nuevos casos de estudio clínicos
- Traducciones de la documentación
- Mejoras en la visualización de datos

**¡Este proyecto es ideal para estudiantes de medicina e informática médica!**
