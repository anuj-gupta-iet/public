* in ProdProfileConfig and DevProfileConfig we have used @Profile annotation to activate profile

* we can do overriding of properties based on profile
e.g. src\main\resources\application.yml properties will be used if we run application with default profile (see properties values from endpoint - http://localhost:8080/getProperty/application.property1)
but src\main\resources\application-profile1.yml  is overriding only application.property2 , so when we run application with profile1 then it will display application.property1=value1 and application.property2=overriddenvalue22
