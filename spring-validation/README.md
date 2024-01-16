Getting Started

### sample Payload
* url (post) - http://localhost:8080/save
* {
	"id": "1",
    "name": "Anujaaa",
    "email": "a@a.com",
    "phoneNumber" : 7503931534
} 

### Group Validations sample Payload
* suppose below payload is passed to all these endpoint
* {
  "userName" : "",
  "priviledgedUserType" : "",
  "adminUserType" : ""
}
* endpoint - http://localhost:8080/saveNormalUser
* response - [
    {
        "fieldName": "userName",
        "errorMessage": "must not be blank"
    }
]
* endpoint - http://localhost:8080/savePriviledgedUser
* response - [
    {
        "fieldName": "userName",
        "errorMessage": "must not be blank"
    },
    {
        "fieldName": "priviledgedUserType",
        "errorMessage": "must not be blank"
    }
]
* endpoint - http://localhost:8080/saveAdminUser
* response - [
    {
        "fieldName": "userName",
        "errorMessage": "must not be blank"
    },
    {
        "fieldName": "adminUserType",
        "errorMessage": "must not be blank"
    }
]