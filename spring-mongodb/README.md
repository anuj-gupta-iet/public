* comamnd to run mongodb server on docker
* Anuj@Anuj-PC MINGW64 /f/mongodb-server
* $ docker run -p 2717:27017 --name mymongo mongo:4.4.6

* Anuj@Anuj-PC MINGW64 /f/mongodb-server
* $ docker ps -a
* CONTAINER ID        IMAGE               COMMAND                  CREATED             STATUS              PORTS                     NAMES
* c48ec064008b        mongo:4.4.6         "docker-entrypoint.s…"   28 minutes ago      Up 28 minutes       0.0.0.0:2717->27017/tcp   mymongo

* 
* http://localhost:8080/addBook - {
    "id": 2,
    "name": "Anuj",
    "author": "java"
}

* connection string - mongodb://localhost:2717/