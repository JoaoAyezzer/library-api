# Library API

## Overview
Este é uma aplicação Spring Boot para gerenciar um sistema de biblioteca. Ela inclui funcionalidades para gerenciar livros, usuários e empréstimos de livros.
## Prerequisitos
- Java 21
- Gradle
- PostgreSQL

## Setup

### Clone do repositório
```sh
git clonehttps://github.com/JoaoAyezzer/library-api.git
cd library-api
```

### Configurando banco de dados
1. Caso não tenha postgres instalado em sua maquina siga os passos abaixo:
    ```sh
   vim docker-compose.yaml
    ```
   Copie e cole o conteudo do docker-compose abaixo:
    ```code
    services:
       dcs-postgres:
          image: postgres:15.3-alpine
          container_name: dcs-postgres
          restart: always
          environment:
             POSTGRES_PASSWORD: 1234
             POSTGRES_USER: postgres
          ports:
             - 5432:5432
          volumes:
             - ./data/postgres:/var/lib/postgresql/data
   
   ```

2. altere  `application-dev.yml` arquivo localizado em `src/main/resources/` com suas configurações:
    ```properties
    spring:
       datasource:
       password: '1234'
       username: postgres
       url: jdbc:postgresql://localhost:5432/library
    jpa:
       hibernate:
          ddl-auto: update
    ```
3. Ative o perfil de dev em `application.yml` arquivo localizado em `src/main/resources/`
    ```properties
    spring:
       profiles:
          active: dev
    ```
### Build and Run
1. Build da aplicação usando Gradle:
    ```sh
    ./gradlew build
    ```

2. Run da aplicação usando Gradle:
    ```sh
    ./gradlew bootRun
    ```

### Documentação da API
The API documentation is available at:
```
http://localhost:8080/docs
```

## Executar Testes
Para executar os testes, use o seguinte comando:
```sh
./gradlew test
```

## Dependencias
- Spring Boot Starter Web
- Spring Boot Starter Data JPA
- Spring Boot Starter Validation
- SpringDoc OpenAPI
- Lombok
- PostgreSQL Driver
- JUnit 5
