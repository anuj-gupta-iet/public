* first hit this endpoint to generate jwt token POST - 'http://localhost:8080/hello'
* payload structure - {
    "username": "anuj",
    "password": "anuj"
}
* then hit secured endpoints 'http://localhost:8080/admin', 'http://localhost:8080/user' with Authorization header and token