## try akayerov добавление приложения в докер
$ java -jar target/spring-boot-web.jar

  access http://localhost:8090

//dockerize

// create a docker image
$ sudo docker build -t spring-boot:1.0 .
// run it
$ sudo docker run -d -p 8090:8090 -t spring-boot:1.0

  access http://localhost:8080
```