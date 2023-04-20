* actuator endpoint to monitor togglz, all features - http://localhost:8080/actuator/togglz
* to monitor individual features - http://localhost:8080/actuator/togglz/FEATURE_ONE
* to change status of a feature - POST - http://localhost:8080/actuator/togglz/FEATURE_ONE 
* body {
    "name": "FEATURE_ONE",
    "enabled": false
}

* toggle-console - http://localhost:8080/togglz-console/index, for that put this config togglz.console.secured=false