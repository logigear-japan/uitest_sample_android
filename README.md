# uitest_sample_android [![CircleCI](https://circleci.com/gh/logigear-japan/uitest_sample_android.svg?style=svg)](https://circleci.com/gh/logigear-japan/uitest_sample_android) 

## What's this?
This is an ui test code sample of Espresso. The app is really a basic/template sample that is of Android Studio generated by selecting "Basic Acticity".

## Where is the Test code?
### UI Test(Instrumented Test)
The code is under [`app/src/androidTest/java/com/example/uitestsample`](https://github.com/ryoyakawai/uitest_sample_android/tree/master/app/src/androidTest/java/com/example/uitestsample)

### Unit Test
The code is under [`app/src/test/java/com/example/uitestsample`](https://github.com/ryoyakawai/uitest_sample_android/tree/master/app/src/test/java/com/example/uitestsample)


## How to run the Test?
### Run Test on GUI
To run the test, [Android Studio](https://developer.android.com/studio/?gclid=CjwKCAiA2fjjBRAjEiwAuewS_Qic_WfLbq49nTdKWgEEP3SPan7JaZQ3tbHTrlhQ56m__S-lL7GmwhoCINAQAvD_BwE) must be installed, and also more than one [virtual devices](https://developer.android.com/studio/run/managing-avds) must be run in your computer.
Then open UI Test code on Android Studio, then click the 'Play Button' locaeted next to line number.

In short:

0. Clone this repository
1. Install Android Studio
2. Setup Virtual Device and Run it
3. Open the UI Test code
4. Click 'Play Button'

### Run Test on CLI
At No.3 in the list above, CLI can be the alternatice way to run the Test.

#### Instrumented Test
```bash
$ ./gradlew connectedAndroidTest
```
#### Unit Test
```bash
$ ./gradlew test
```

However, this way does not always easy for all of developers. So, to get more information about to run on CLI in detail please refer to this page depending on your interest.
https://developer.android.com/studio/test/command-line

### Run both Instrumented Test and Unit test, and get report
```bash
./gradlew jacocoDebugTestReport
```

Report will be stored in `./app/build/reports/` directory.

# Lisence
Apache License Version 2.0
