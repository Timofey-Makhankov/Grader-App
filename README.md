# Grader - A Grade Tracker

This Application is designed to Easily Track Your Grades in School and Calculate the Average And Overall Grades of given Grades. (Designed for Swiss School Systems ATM)

[![ko-fi](https://ko-fi.com/img/githubbutton_sm.svg)](https://ko-fi.com/Y8Y5X6KL1)

## Application Download

At the Moment you can't install the Application through Google Play Store or F-Droid. But it will be available in the fututre

## Features

Manage grades through the hierarchy (Schools -> Divisions -> Modules -> Exams)

![Home Screen of the application](/doc/img/Screenshot_20240508_221724_Grader.png)

Calculate your Average Grade with a Grade Calculator

![Calculator Screen](/doc/img/Screenshot_20240508_224000_Grader.png)

For a Detailed look of all the Features the App has to Offer, see the [Docs](https://grader.timofey-makhankov.ch/)

## Installation Guide for Dev

> It is Recommended to use an actual Android Device instead of the AVD, since it
> is more realistic and easier to setup without extra configurations

### Clone Project

[Link to Android Studio Download Page](https://developer.android.com/studio)

This Project uses the latest Version of Android Studio. This Project Includes a multitude of dependancies:

- Dagger-Hilt

- Room Database

- Splash Screen API

- Jetpack Compose

- MVVM (**M**odel **V**iew **V**iew**M**odel)

- Compose Navigation

- Material 3

To get Started, first clone the repository

```bash
git clone https://github.com/Timofey-Makhankov/Grader-App.git
```

open the Project in Android Studio

After cloning the Project, Open the Project in Android Studio and let Gradle install all the Dependancies. It may also require to install the necessary Java SDK and AGP (Android Gradle Plugin). Android Studio should install it, when opening the Project, otherwise start the install manually.

After Gradle is finished, You can either export the Project to an APK File or Connect an Android Device with debugging support enabled and run the Project to the device
