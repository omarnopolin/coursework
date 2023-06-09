### Implementation:
- Front-End - HTML/CSS/JavaScript
- Back-End - Java 14, Maven, SQL

### Technologies:
- Spring Framework, Spring Boot, Spring Security
- PostgreSQL

### Usage:

1. ```git clone https://github.com/omarnopolin/coursework.git```

2. PostgreSQL database:

    2.1. You need to install PostgreSQL.

    2.2. Create user and databases: 
        ``` psql postgres; 
            CREATE USER root WITH PASSWORD 'qwerty';
            CREATE DATABASE [your login];
            CREATE DATABASE cashflow;
            exit;```

3. apply maven wrapper, if necessary: ```mvn -N io.takari:maven:wrapper```

4. write postgresql username and password in hibernate config file if its different from 2.3. (/src/main/resources/hibernate.cfg.xml)

5. run app: ```./mvnw spring-boot:run```

6. open in browser website: http://localhost:8080/