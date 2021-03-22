Aplication description:
1) Application start-up:
    1.1) with hsqldb in-memory db:
        For that it is necessary to leave bean "dataSource" with EmbeddedDatabaseBuilder implementation in SpringJdbcConfig class
        (set @Profile("dev", for other implementations @Profile("!dev")
        Command:
        mvn spring-boot:run -Dspring-boot.run.profiles=dev
    1.2) with Docker MySql DB (temporary not working - "ECONNREFUSED localhost:8080" error)
        For that it is necessary to do next:
         - leave bean "dataSource" with DataSourceBuilder.create().build() implementation and "transactionManager" bean in SpringJdbcConfig class;
         - build .jar file
           mvn clean package -Pdev -Dmaven.test.skip=true
         - copy generated jar file hashing.jar from directory /target into directory /docker/Docker
         - generate DB dump scripts if file /docker/Docker/setup.sql is not exist
           Manual run "main" method in test/class GenerateInitDataSql
         - create "app" and "db1" images and containers in Docker
           Command:
           docker-compose up
2) Unit tests (works only with "dataSource" bean with EmbeddedDatabaseBuilder implementation)
   Command:
   mvn clean test -Pdev -DprofileIdEnabled=true
    ● шаги для запуска тестов
3) API/Endpoints description:
    3.1) get hash code by phone number:
    GET http://127.0.0.1:8080/api/phones/380000000001/hashes
    , where 380000000001 is a path variable of
    Example of responses:
        - "happy path":  65b05960fa318a5c7034d2aeafadb5c55542b69987ffd00aaf0a179183136a50
        - validation response:
            {
                "code": 400,
                "message": "targetphone.number.not.validCell phone number+380000000001 is invalid"
            }
        - unauthorized: Status: 401 Unauthorized in Response Header
    3.2) get phone number by hash code:
    GET http://127.0.0.1:8080/api/phones/hashes/65b05960fa318a5c7034d2aeafadb5c55542b69987ffd00aaf0a179183136a50
    , where 65b05960fa318a5c7034d2aeafadb5c55542b69987ffd00aaf0a179183136a50 is has code
    Example of responses:
        - success: 380000000001
        - unsuccess:
            {
                "code": 500,
                "message": "Phone not found by hash "
            }
        - unauthorized: Status: 401 Unauthorized in Response Header

    Basic Authentification is present for both eddpoint above:
    -   user: user
    -   password: resu
4) Implementation features
    4.1) All configuration are located in application.properties
    By default:
        hash.algorithm=SHA-256
        hash.salt=AAA111