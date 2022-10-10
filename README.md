# OMCTT


- Java 17
- JUnit 5
- Maven 3.8.6


## Building

1.1. **Configure MySQL DB** To configure db (Docker):

```
docker compose up -d
```

1.2. **Configure MySQL DB** To configure db :

```
Port: 3308
host: localhost
username: root
password: root
```
2. **Create DB** :
```
Create omc data base on MySQL
```

3. **Initial data** :
```
data.sql load data into data base
```

4. **Package** To package application:
```
./mvnw clean package
```

5. **Run** To run application:

```
./mvnw spring-boot:run
```

6. **URL** To open application:

```
http://localhost:9001/index
```

7. **Login** To login :

```
The password for all users is 123456.
```




