* when we hit 'http://localhost:8080/hello' then it will be redirected to 'http://localhost:8080/my-login'
* we have created thymleaf template to display myLogin.html template on hitting url my-login
* in this template form is submitting to same url 'my-login' with post
* and then spring security will be called when post method is called on my-login  

* if invalid credentials is passed then this url will be hit by string security 'http://localhost:8080/my-login?error'
* so we can use that th:if="${param.error}" to display error message
