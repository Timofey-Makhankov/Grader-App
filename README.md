# Grader - A Grade Tracker

This Application is designed to Easily Track Your Grades in School and Calculate the Average And Overall Grades of given Grades.

## Features

This Features are _Still in Progress_. **Not FINAL**

- Save Grades for each subject
- Get the average of the given subject Grade
- Export the Data in either a JSON or CSV Format
- rounding to full or .5 grade
- easily manage grades

## Database Schema

```mermaid
---
title: Grader Database
---
erDiagram
    Exam{
        UUID id PK
        VARCHAR(255) name
        VARCHAR(255) description
        FLOAT grade
        FLOAT weight
        DATE date
        UUID modul_id FK
    }

    Module{
        UUID id PK
        VARCHAR(255) name
        VARCHAR(255) description
        INT division_id FK
    }

    School{
        UUID id PK
        VARCHAR(255) name
        VARCHAR(255) address
        VARCHAR(20) zip
        VARCHAR(255) city
    }

    Division{
        UUID id PK
        VARCHAR(255) name
        VARCHAR(255) teacher_lastname
        VARCHAR(255) teacher_firstname
        INT school_id FK
    }

    Module}o--||Division : "Zero to Many"
    Division}|--||School : "One to Many"
    Exam}|--||Module : "One to Many"
```

in SQLlite hat es kein uuid type, muss als string oder blob speichern
