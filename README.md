# web-automation-starter-java

This project is the Selenium Web automation test framework.

Features Overview:
* Created for the page object design pattern
* Automatic WebDriver download and setup according to OS and selected browser type
* Automatic dependency management, project setup, and configuration using Gradle
* Automatic initialization and closing of the browser
* Automatic screenshots on fail (feature available for the manual call) 
* TestNG test suites - XML files as combinations of tests
* Ready to run locally (single test class or test suits) or on Jenkins.

Supported Operating Systems:
* macOS
* Linux
* Windows

Supported Browser Configurations:
* Chrome
* Chrome(headless)
* Firefox
* Opera
* Edge (macOS and Windows only)
* Safari (macOS only)
* Internet Explorer (Windows only)
* Selenium Hub/Grid
* BrowserStack

## Getting Started

These instructions will get you a copy of the project on your local machine for test development and test running purposes.

### Prerequisites

To run the project you need the following:
* Git client
* Java 8+ 
* Java IDE. IntelliJ IDEA is strongly recommended as the project wasn't tested on other IDEs. You can obtain a copy of IntelliJ IDEA Community Edition(free and open source) here: https://www.jetbrains.com/idea/download

### Installing

A step by step examples that tell you how to get a test development env installed on your machine

The project can be installed using the command line:

```
git clone https://github.com/shenderov/web-automation-starter-java.git
```

or directly from IntelliJ IDEA: 

```
File -> New -> Project from Version Control...
URL: https://github.com/shenderov/web-automation-starter-java.git
Directory: <desired directory for the project>
```

## Running the tests

You have a few options to run the test.

Running a single test class using IntelliJ IDEA:
```
Run -> Edit Configurations...
Add (+ button) -> TestNG
Test kind: Class
Class: <A fully-qualified test class name. e.g. me.shenderov.automation.tests.IndexPageTest>
VM options: <options(please, refer to the parameters section)>
Run -> Run...
```

Running a single test class using Gradle.

In the project root directory (either using the terminal in IntelliJ IDEA or any other command line tool run the following

macOS/Linux:
```
./gradlew clean test --tests <A fully-qualified test class name. e.g. me.shenderov.automation.tests.IndexPageTest> <parameters(please, refer to the parameters section)>
```
Windows:
```
gradlew clean test --tests <A fully-qualified test class name. e.g. me.shenderov.automation.tests.IndexPageTest> <parameters(please, refer to the parameters section)>
```
Running a test suite using Gradle.

In the project root directory (either using terminal in IntelliJ IDEA or any other command line tool run the following

macOS/Linux:
```
./gradlew clean test -PsuiteFile=<suite file>.xml <parameters(please, refer to the parameters section)>
```
Windows:
```
gradlew clean test -PsuiteFile=<suite file>.xml <parameters(please, refer to the parameters section)>
```
Running all tests configured in Gradle(default test suite).

In the project root directory (either using terminal in IntelliJ IDEA or any other command line tool run the following

macOS/Linux:
```
./gradlew clean test <parameters(please, refer to the parameters section)>
```
Windows:
```
gradlew clean test <parameters(please, refer to the parameters section)>
```
### Parameters
This project can be parameterized using the following parameters:

| Parameter             | Description                                                                                       | Default Value                       |
| --------------------- | ------------------------------------------------------------------------------------------------- | ----------------------------------- |
| driverType            | Browser Configuration                                                                             | chrome                              |
| baseUrl               | Url to open at the test initialization.                                                           | https://www.seleniumeasy.com/test/  |
| suiteFile             | TestNG test suite to run                                                                          | all-classes.xml                     |
| browserstackUsername  | BrowserStack username. Required for `driverType=browserstack`. Otherwise will be ignored.         | No default value                    |
| browserstackKey       | BrowserStack access key. Required for `driverType=browserstack`. Otherwise will be ignored.       | No default value                    |
| hubUrl                | Selenium Hub/Grid URL. Required for `driverType=hub`. Otherwise will be ignored.                  | No default value                    |
| capabilitiesFilename  | Required for driverType browserstack and hub. Optional for other types (still in development)     | No default value                    |
| jobName               | Jenkins job name to use in Test listener                                                          | No default value                    |
| buildNumber           | Jenkins build number to use in Test listener                                                      | No default value                    |

To use parameters in VM options add -D before the parameter name (e.g. -DdriverType=chrome):

`-DdriverType=chrome -DbaseUrl=https://www.w3schools.com/`

To use parameters in Gradle command line command add -P before the parameter name (e.g. -PdriverType=chrome)

`-PbaseUrl=https://www.w3schools.com/`

### Test Results

Test results can be found in the following places:
* Run toolbar on IntelliJ IDEA (if test class run using the IDE)
* In the terminal output (it run using Gradle command line command)
* In `build/reports/tests/test` directory (TestNG report)
* In `build/reports/tests-results/test` directory (TestNG XML report)
* Screenshots location: `build/images`

## Built With

* [Java](https://www.oracle.com/java/) - The programming language
* [TestNG](https://testng.org/doc/) - Java testing framework
* [Selenium WebDriver](https://www.selenium.dev/) - Web testing framework
* [Gradle](https://gradle.org/) - Build tool

## Authors

* **Konstantin Shenderov** - *Initial work*

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
