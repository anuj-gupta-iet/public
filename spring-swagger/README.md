# Getting Started

### Adding swagger dependencies

* Add these 2 dependencies in pom.xml
springfox-swagger2
springfox-swagger-ui

* swagger url - http://localhost:8080/swagger-ui.html

### Issues
* proviously getting this error while hitting swagger url - No mapping for GET /swagger-ui.html
* in order to solve this, changed spring boot version from 2.7.1 to 1.5.6.RELEASE
* and used springfox-swagger2 and springfox-swagger-ui version as 2.6.1  
