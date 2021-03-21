1) Application start-up
    1.1) with hsqldb in-memory db
    mvn spring-boot:run -Dspring-boot.run.profiles=dev
    1.2) with Docker MySql DB (temporary not working)
            due to error: java.net.ConnectException: Connection refused (Connection refused) - Caused by: com.mysql.cj.exceptions.CJCommunicationsException: Communications link failure (Docker network)

2) Endpoints:
    2.1) get hash code by phone number:
    GET http://127.0.0.1:8080/api/phones/380000000001/hashes
    Example of response: 65b05960fa318a5c7034d2aeafadb5c55542b69987ffd00aaf0a179183136a50
    2.2) get phone number by hash code:
    GET http://127.0.0.1:8080/api/phones/hashes/65b05960fa318a5c7034d2aeafadb5c55542b69987ffd00aaf0a179183136a50
    Example of response: 380000000001

    Basic Authentification:
    -   user: user
    -   password: resu

3) Docker compose
    - docker compose up (is running, but app doesn't start due to "Caused by: java.net.ConnectException: Connection refused (Connection refused)" - app doesn't see MySql DB)