# Battleships - Documentation

## Introduction

> This document contains the relevant design and implementation aspects of DAW project.

The project development is divided into 2 phases. The main objective of each phase is described below:

* **Phase 1**: Development of the Web API for the Battleships game - **backend**;
* **Phase 2**: Development of the Web UI for the Battleships game - **frontend**.

## Authors

- 48089 [André Páscoa](https://github.com/devandrepascoa)
- 48280 [André Jesus](https://github.com/andre-j3sus)
- 48287 [Nyckollas Brandão](https://github.com/Nyckoka)

Professor: Eng. Pedro Félix

@ISEL<br>
Bachelor in Computer Science and Computer Engineering<br>
Web Application Development - LEIC51D - Group 03<br>
Winter Semester of 2022/2023

## Table of Contents

* [Introduction](#introduction);
* [Authors](#authors);
* [Table of Contents](#table-of-contents);
* [Modeling the Database](#modeling-the-database):
    * [Conceptual Model](#conceptual-model);
    * [Physical Model](#physical-model),
* [Software Organization](#software-organization):
    * [Project structure](#project-structure);
    * [Project dependencies](#project-dependencies);
    * [Open-API Specification](#open-api-specification);
    * [Use-Case Scenario](#use-case-scenario);
    * [Connection Management](#connection-management);
    * [Data Access](#data-access);
    * [Error Handling](#error-handling);
    * [Testing](#testing);
* [Conclusions](#conclusions);
    * [Critical Evaluation](#critical-evaluation).

---

## Modeling the Database

### Conceptual Model

The following diagram holds the Entity-Relationship model for the information managed by the system.

<p align="center">
    <img src="battleships-db/battleships-db.svg" alt="Entity Relationship Diagram"/>
</p>

The conceptual model is stored in the [`docs/battleships-db`](./battleships-db) folder.

We highlight the following aspects:

* ...

The conceptual model has the following restrictions:

* ...

### Physical Model

The physical model of the database is available in [createSchema.sql](../code/jvm/src/main/sql/createSchema.sql).

To implement and manage the database **PostgreSQL** was used.

The [`src/main/sql`](../code/jvm/src/main/sql) folder contains all SQL scripts developed:

* [createSchema.sql](../code/jvm/src/main/sql/createSchema.sql) - creates the database schema;
* [cleanData.sql](../code/jvm/src/main/sql/cleanData.sql) - clears the database tables;
* [insertData.sql](../code/jvm/src/main/sql/insertData.sql) - adds data to the database.

We highlight the following aspects of this model:

* All unique identifiers are `SERIAL`;
* ...

---

## Software Organization

### Project Structure

The project is organized as follows:

* ...

---

### Project Dependencies

...

---

### Open-API Specification

The Open-API Specification is available [here](battleships-api-spec.yaml).

In our Open-API specification, we highlight the following aspects:

* The requests are split into 3 routers: ...;
* ...

---

### Use-Case Scenario

The application is launched in
the [AppLaunch](https://github.com/isel-leic-ls/2122-2-LEIC41D-G03/blob/main/src/main/kotlin/pt/isel/ls/sports/AppLaunch.kt)
file.
This file contains the main function, where the database and server instances are created, starting the server.

The server is represented by
the [AppServer](https://github.com/isel-leic-ls/2122-2-LEIC41D-G03/blob/main/src/main/kotlin/pt/isel/ls/sports/AppServer.kt)
class. It is implemented with a `Http4kServer`, and creates an instance of the application services, using the received
database, and an instance of
the Web API, using the services instance.

The application services are represented by
the [AppServices](https://github.com/isel-leic-ls/2122-2-LEIC41D-G03/blob/main/src/main/kotlin/pt/isel/ls/sports/services/AppServices.kt)
and implements all the operations, making parameters validations, and calling the database methods.

The Web API is implemented in
the [AppWebApi](https://github.com/isel-leic-ls/2122-2-LEIC41D-G03/blob/main/src/main/kotlin/pt/isel/ls/sports/api/AppWebApi.kt)
class.
This class implements all the routes of the API, implementing all the requests and calling the corresponding services
method.

Both the services, the Web API and the database are divided into several sections.
The services and Web API have one section per endpoint group (users, routes, activities, sports).
The database has one section per entity (users, routes, activities, sports, tokens).

When a request arrives, it is received and handled by the Web API module.

This module calls the associated method of the services module, which does all sorts of validation.
If everything is OK, then database methods may be called.

In the end, the server will respond with the corresponding HTTP Response documented in
the [Open-API Specification](https://github.com/isel-leic-ls/2122-2-LEIC41D-G03/blob/574f00333ae71cebfd74759226725bfb69bee372/docs/sports-api-spec.yml)
.

---

### Connection Management

...

---

### Data Access

The data access implementation is available in the [here](../code/jvm/src/main/kotlin/pt/isel/daw/battleships/database).

...

---

### Error Handling

For error handling purposes, ...

---

### Testing

...

---
---

## Conclusions

### Critical Evaluation

...
