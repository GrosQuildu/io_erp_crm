# ERP & CRM project

System is composed of two frontend desktop applications (for crm and erp) and backend server common for both apps.

### Functionalities:
#### General
* crm, erp and admin employees

#### ERP
* Companies / customers management
* Orders management
* Proformas generation
* Warehouse
* Delivery costs based on weights
* Reports

#### CRM
* Companys / customers / contacts management
* Tasks
* Meetings
* Emails communication


### Entity Relationship Diagram
Whole database
![Whole database](/project/db_schemas/schema.png)

ERP entities
![erp database](/project/db_schemas/schema_erp.png)

CRM entities
![crm database](/project/db_schemas/schema_crm.png)


### API specification
[Backend api swagger specification - yaml](/project/api/erp_crm_api.yaml)

[Backend api swagger specification - live](http://80.211.144.146/swagger-ui/)


## Technology
#### Backend
* Spring Boot
* Sprint Data REST
* Spring Data JPA
* Sprint REST Assured
* Maven
* Swagger / Springfox
* Postgresql

REST API was created in swagger (openAPI) format. Swagger made it possible to partially define the system without use of specific technology. Moreover, defined api at the beginning allowed developers to work on client applications and server parallely. Springfox (part of swagger project) is plugin for spring to document api inside source code.

Server is a Spring Boot application. Main adventage is that it's self-contained (contains embedded tomcat servlet, runs from single jar file) which make it easy to ship, deploy and possibly containerize. Another adventage is consistency with frontend applications (also made in java). Rest of the features (Data JPA / ORM, auto CRUD, unit tests framework) can be found in other technologies/frameworks.

PostgreSQL was chosen mainly because it's open source and efficient.

#### Frontend
