# Toys API - Projeto de Containerização com Docker Compose

## Visão Geral

Este projeto consiste em uma API RESTful para gerenciamento de brinquedos, desenvolvida em **Java 17** com o framework **Spring Boot**.

Como parte do 1º Checkpoint da disciplina de **DevOps Tools & Cloud Computing** da FIAP, o projeto foi totalmente migrado de uma arquitetura tradicional para uma arquitetura de contêineres gerenciada pelo **Docker Compose**.

## Arquitetura da Solução

A aplicação é orquestrada pelo Docker Compose e consiste em dois serviços principais que operam de forma isolada, mas conectada:

* `app`: Um contêiner responsável por executar a aplicação Java/Spring Boot, construído a partir de um `Dockerfile` customizado.
* `db`: Um contêiner executando uma instância do banco de dados **PostgreSQL 13**, utilizando uma imagem oficial.

**Recursos Adicionais:**

* **Rede Dedicada (`toys-net`):** Uma rede do tipo *bridge* foi configurada para permitir a comunicação segura e eficiente entre a aplicação e o banco de dados.
* **Volume Persistente (`db-data`):** Um volume Docker foi implementado para garantir que os dados do PostgreSQL não sejam perdidos ao recriar o contêiner do banco.

---

## Pré-requisitos

Antes de começar, garanta que você tenha os seguintes softwares instalados:

* [Docker](https://www.docker.com/products/docker-desktop/)
* Docker Compose (geralmente já incluído no Docker Desktop)

---

## Processo de Deploy (Passo a Passo)

[cite_start]Siga as instruções abaixo para executar o projeto em seu ambiente local. [cite: 56]

**1. Clone o Repositório**
Abra seu terminal e clone este repositório para a sua máquina:
```bash
git clone https://github.com/carolrodgerio/toys-api-java
```

**2. Navegue até a Pasta do Projeto**
```bash
cd toys-api-java
```

**3. Execute o Docker Compose**
Este é o comando principal. Ele irá construir a imagem da aplicação (se ainda não existir), baixar a imagem do PostgreSQL e iniciar os dois contêineres.
```bash
docker compose up --build
```

Após a execução, os logs dos dois serviços serão exibidos no terminal. A API estará pronta para receber requisições quando a mensagem `Started BrinquedosApplication` aparecer.

A API estará acessível em: `http://localhost:8080`

---

## Comandos Essenciais do Docker Compose

Aqui estão os comandos mais úteis para gerenciar o ciclo de vida da aplicação.

* **Subir os serviços em background (modo detached):**
    ```bash
    docker compose up -d
    ```

* **Parar e remover todos os contêineres, redes e volumes:**
    ```bash
    docker compose down
    ```

* **Visualizar os logs da aplicação em tempo real:**
    ```bash
    docker compose logs -f app
    ```

* **Verificar o status dos contêineres em execução:**
    ```bash
    docker compose ps
    ```

---

## Troubleshooting Básico

Encontrou algum problema? [cite_start]Aqui estão algumas soluções para os erros mais comuns. [cite: 56]

* **Erro: `Port is already allocated` ou `Bind for 0.0.0.0:8080 failed`**
    * **Causa:** Outro serviço em sua máquina já está utilizando a porta 8080.
    * **Solução:** Pare o outro serviço ou altere a porta no arquivo `docker-compose.yml`. Mude a linha `ports: - "8080:8080"` para, por exemplo, `ports: - "8081:8080"` e acesse a API em `http://localhost:8081`.

* **Erro durante a etapa de `build`**
    * **Causa:** Pode ser um erro de compilação do Java, uma dependência faltando no `pom.xml` ou um comando incorreto no `Dockerfile`.
    * **Solução:** Leia atentamente a mensagem de erro no log do build para identificar a causa raiz e corrija o arquivo correspondente.

* **Aplicação inicia e para imediatamente**
    * **Causa:** Geralmente, a aplicação não conseguiu se conectar ao banco de dados.
    * **Solução:** Verifique os logs com `docker compose logs app`. Confirme se as variáveis de ambiente (`SPRING_DATASOURCE_URL`, `...USERNAME`, `...PASSWORD`) no `docker-compose.yml` estão corretas e se o `healthcheck` do banco de dados está funcionando.

---

**Alunos**

* Carolina Estevam Rodgerio - RM 554975
* Enrico Andrade D'Amico -  RM 557706
* Lucas Thalles dos Santos - RM 558886
