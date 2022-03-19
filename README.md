# merchante-automation challenge

The framework mainly uses Spring, TestNG, RestAssured and Selenium in addition to other dependencies.
- RestAssured (for API testing)
- Selenium (for desktop automation) - In a future is possible use this driver for appium mobile and browsertack


### Requirements:
Java 8, Maven, Git, TestNG.


### Project Download:
```sh
$ git clone <URL>
```

### Tests:
```sh
.../src/test/java/
```

### Properties:
Here we can indicate the different hosts, endpoints, DBs, etc., to which we will point our tests.
The properties are in:
```sh
...src/main/resources/properties
```

### Execution:
-The "enviroment" to which we want to point the tests must be indicated, according to the following example, the properties defined in properties.environments.qa will be raised
-The "groups" that we want to include in the execution must be indicated, according to the following example, only those tests that have the group "regression" specified in the @Test annotation will be executed.

API Example:
```sh
$ mvn clean test -Dgroups=regression -Denvironment=prod
```
Frontend Example:
```sh
$ mvn clean test -Dgroups=regression -Denvironment=prod -Dbrowser=chrome
```

### Execution report:
The result of the execution can be obtained from the report generated in:
```sh
.../reports/merchante-automation-report.html
```
