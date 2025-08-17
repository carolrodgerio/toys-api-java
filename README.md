# Projeto API de Brinquedos - FIAP TDS

## Descrição do Projeto

Este projeto é uma API RESTful desenvolvida com o framework **Spring Boot** para gerenciamento de brinquedos infantis (até 12 anos). A API implementa operações CRUD (Create, Read, Update, Delete) com persistência em um banco de dados **Oracle**, utilizando **Spring Data JPA**, **Lombok** para simplificação de código e **HATEOAS** para fornecer links hipermedia (nível 3 de maturidade REST). Os testes foram realizados via **Postman** em `http://localhost:8080/brinquedos`, e o projeto foi implantado em uma plataforma online.

### Objetivos
- Criar uma API para gerenciar brinquedos com as colunas: `Id`, `Nome`, `Tipo`, `Classificacao`, `Tamanho`, `Preco`.
- Implementar endpoints REST com operações CRUD completas.
- Usar Lombok para reduzir boilerplate na entidade.
- Aplicar HATEOAS para retornos com links navegáveis.
- Persistir dados no banco Oracle (tabela `TDS_TB_Brinquedos`) via JPA.
- Documentar testes com prints e realizar deploy.

### Tecnologias Utilizadas
- **Spring Boot**: Framework principal para a API.
- **Maven**: Gerenciamento de dependências.
- **Java 17**: Linguagem de programação.
- **Spring Data JPA**: Persistência com Oracle.
- **Lombok**: Anotações para simplificar a entidade (`@Data`).
- **HATEOAS**: Links hipermedia nas respostas.
- **Oracle SQL Developer**: Banco de dados (tabela `TDS_TB_Brinquedos`).
- **Postman**: Testes dos endpoints.
- **IntelliJ IDEA**: IDE utilizada.
- **Render**: Plataforma de deploy.

### Configuração Inicial
O projeto foi inicializado via **Spring Initializr** com as dependências:
- Spring Web
- Spring Data JPA
- Lombok
- Spring HATEOAS
- Oracle JDBC Driver

A configuração do banco foi feita em `application.properties`:
```
spring.datasource.url=jdbc:oracle:thin:@//oracle.fiap.com.br:1521/ORCL
spring.datasource.username=
spring.datasource.password=
spring.datasource.driver-class-name=oracle.jdbc.OracleDriver
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=true
```

**Evidência**:

![Print do Spring Initializr com dependências](https://github.com/lucasthalless/toys-api-java/blob/main/assets/spring-initializr.png)


## Estrutura do Projeto
A arquitetura segue o padrão MVC com as camadas:
- **Model**: Classe `Brinquedo` anotada com `@Entity` e `@Data` (Lombok) para mapear a tabela `TDS_TB_Brinquedos`.
- **Repository**: Interface `BrinquedoRepository` extendendo `JpaRepository` para operações CRUD automáticas.
- **Controller**: Classe `BrinquedoController` com endpoints REST e HATEOAS.

Optamos por usar diretamente o `JpaRepository` no controller para simplificar o código, mantendo todas as funcionalidades de persistência e commit exigidas. O `JpaRepository` é uma interface do Spring Data JPA que fornece métodos prontos para CRUD, abstraindo a interação com o banco Oracle.

## Funcionalidades e Endpoints

A API opera em `http://localhost:8080/brinquedos` (local) ou na URL de deploy. Abaixo, cada operação CRUD é descrita com exemplos de uso no Postman.

### 1. GET /brinquedos (Listar Todos)
- **Descrição**: Retorna todos os brinquedos cadastrados com links HATEOAS ("self" e "brinquedos").
- **Exemplo de Resposta**:
  ```json
  [
    {
      "id": 1,
      "nome": "Boneca",
      "tipo": "Boneco",
      "classificacao": "3+",
      "tamanho": "Pequeno",
      "preco": 29.99,
      "links": [
        { "rel": "self", "href": "http://localhost:8080/brinquedos/1" },
        { "rel": "brinquedos", "href": "http://localhost:8080/brinquedos" }
      ]
    }
  ]
  ```
- **Evidência**:

  ![Print do GET /brinquedos no Chrome](https://github.com/lucasthalless/toys-api-java/blob/main/assets/get.png)
  ![Print do banco ORACLE com a persistência dos dados](https://github.com/lucasthalless/toys-api-java/blob/main/assets/select-all-database.png)


### 2. GET /brinquedos/{id} (Buscar por ID)
- **Descrição**: Retorna um brinquedo específico com links HATEOAS.
- **Exemplo de Resposta**:
  ```json
  {
    "id": 1,
    "nome": "Boneca",
    "tipo": "Boneco",
    "classificacao": "3+",
    "tamanho": "Pequeno",
    "preco": 29.99,
    "links": [
      { "rel": "self", "href": "http://localhost:8080/brinquedos/1" },
      { "rel": "brinquedos", "href": "http://localhost:8080/brinquedos" }
    ]
  }
  ```
- **Evidência**:
  ![Print do GET /brinquedos/1 no Postman](https://github.com/lucasthalless/toys-api-java/blob/main/assets/get-by-id.png)


### 3. POST /brinquedos (Criar)
- **Descrição**: Cria um novo brinquedo, adicionando à lista temporária (se aplicável) e persistindo no banco.
- **Exemplo de Requisição**:
  ```json
  {
    "nome": "Boneca",
    "tipo": "Boneco",
    "classificacao": "3+",
    "tamanho": "Pequeno",
    "preco": 29.99
  }
  ```
- **Exemplo de Resposta** (status 201 Created):
  ```json
  {
    "id": 1,
    "nome": "Boneca",
    "tipo": "Boneco",
    "classificacao": "3+",
    "tamanho": "Pequeno",
    "preco": 29.99,
    "links": [
      { "rel": "self", "href": "http://localhost:8080/brinquedos/1" }
    ]
  }
  ```
- **Evidência**:
  ![Print do POST /brinquedos no Postman](https://github.com/lucasthalless/toys-api-java/blob/main/assets/post.png)


### 4. PUT /brinquedos/{id} (Atualizar Completo)
- **Descrição**: Atualiza todos os campos de um brinquedo existente.
- **Exemplo de Requisição**:
  ```json
  {
    "nome": "Boneca Nova",
    "tipo": "Boneco",
    "classificacao": "5+",
    "tamanho": "Médio",
    "preco": 39.99
  }
  ```
- **Exemplo de Resposta**:
  ```json
  {
    "id": 1,
    "nome": "Boneca Nova",
    "tipo": "Boneco",
    "classificacao": "5+",
    "tamanho": "Médio",
    "preco": 39.99,
    "links": [
      { "rel": "self", "href": "http://localhost:8080/brinquedos/1" }
    ]
  }
  ```
- **Evidência**:
  ![Print do PUT /brinquedos/1 no Postman](https://github.com/lucasthalless/toys-api-java/blob/main/assets/put.png)


### 5. PATCH /brinquedos/{id} (Atualizar Parcial)
- **Descrição**: Atualiza apenas os campos fornecidos de um brinquedo.
- **Exemplo de Requisição**:
  ```json
  {
    "preco": 49.99
  }
  ```
- **Exemplo de Resposta**:
  ```json
  {
    "id": 1,
    "nome": "Boneca",
    "tipo": "Boneco",
    "classificacao": "3+",
    "tamanho": "Pequeno",
    "preco": 49.99,
    "links": [
      { "rel": "self", "href": "http://localhost:8080/brinquedos/1" }
    ]
  }
  ```
- **Evidência**:
  ![Print do PATCH /brinquedos/1 no Postman](https://github.com/lucasthalless/toys-api-java/blob/main/assets/patch.png)


### 6. DELETE /brinquedos/{id} (Excluir)
- **Descrição**: Exclui um brinquedo por ID, com commit no banco.
- **Exemplo de Resposta**: Status 204 No Content (sem corpo).
- **Evidência**:
  ![Print do DELETE /brinquedos/1 no Postman](https://github.com/lucasthalless/toys-api-java/blob/main/assets/delete.png)
  ![Print do banco atualizado](https://github.com/lucasthalless/toys-api-java/blob/main/assets/updated-database.png)


## Uso de Lombok
- A anotação `@Data` foi usada na classe `Brinquedo` para gerar automaticamente getters, setters, `toString`, `equals`, e `hashCode`, reduzindo código boilerplate e mantendo a entidade limpa.

## Uso de HATEOAS
- Implementamos HATEOAS (nível 3 de maturidade) usando `EntityModel` e `WebMvcLinkBuilder`. Cada resposta inclui links "self" (para o recurso atual) e "brinquedos" (para a coleção), permitindo navegação dinâmica pela API.

## Persistência no Banco Oracle
- A tabela `TDS_TB_Brinquedos` foi criada no Oracle SQL Developer com as colunas especificadas.
- A persistência foi configurada via `application.properties` e usa Spring Data JPA para commits no banco.

## Deploy
O projeto foi implantado na plataforma **Render**, utilizando Dockerfile e variaveis de ambiente para conectar ao banco. A URL de acesso é:
- **[Link do Deploy](https://toys-api-livh.onrender.com/brinquedos)**

## Instruções de Uso
1. Clone o repositório: `git clone https://github.com/lucasthalless/toys-api-java`.
2. Configure o Oracle com a tabela `TDS_TB_BRINQUEDOS`.
4. Rode o projeto: `./mvnw spring-boot:run` ou via IntelliJ.
5. Teste os endpoints em `http://localhost:8080/brinquedos` com Postman/Insomnia.

## IDE utilizado paraelaboração do projeto
- IntelliJ
