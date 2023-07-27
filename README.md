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
        BLOB id PK
        BLOB modul_id FK
        TEXT name
        TEXT description
        REAL grade
        REAL weight
        INTEGER date
        INTEGER is_selected
        INTEGER on_delete
    }

    Module{
        BLOB id PK
        BLOB division_id FK
        TEXT name
        TEXT description
        TEXT teacher_lastname
        TEXT teacher_firstname
        INTEGER is_selected
        INTEGER on_delete
        REAL grade
    }

    School{
        BLOB id PK
        TEXT name
        TEXT description
        TEXT address
        TEXT zip
        TEXT city
        INTEGER is_selected
        INTEGER on_delete
        REAL grade
    }

    Division{
        BLOB id PK
        BLOB school_id FK
        TEXT name
        TEXT description
        INTEGER school_year
        INTEGER is_selected
        INTEGER on_delete
        REAL grade
    }

    Module}o--||Division : "Zero to Many"
    Division}|--||School : "One to Many"
    Exam}|--||Module : "One to Many"
```

in SQLlite hat es kein uuid type, muss als string oder blob speichern
