# Grader - A Grade Tracker

This Application is designed to Easily Track Your Grades in School and Calculate the Average And Overall Grades of given Grades.

## Features

This Features are _Still in Progress_. **Not FINAL**

- Save Grades for each subject
- Get the average of the given subject Grade
- Export the Data in either a JSON or CSV Format

## Database Schema

```mermaid
---
title: Grader Database
---
erDiagram
    Grade{
        INT id PK
        VARCHAR(255) exam
        FLOAT grade
        FLOAT weight
        DATE date
        INT fk_subject FK
    }
    Subject{
        int id PK
        VARCHAR(255) subject
        INT fk_class FK
    }
    Teacher{
        INT id PK
        VARCHAR(255) lastname
        VARCHAR(255) firstname
    }
    School{
        INT id PK
        VARCHAR(255) name
        VARCHAR(255) address
    }
    Class{
        INT id PK
        INT fk_school FK
        INt fk_teacher FK
    }

    Grade}o--o{Subject : contains
    Subject||--||Class : has
    Class||--||Teacher : has
    Class||--||School : in
```
