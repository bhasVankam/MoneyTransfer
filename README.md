
## Overview

Simple RESTful API for money transfer.

## Technologies  used:

<ul>
  <li>Spark Java</li>
  <li>JOOQ</li>
  <li>H2 as in-memory database</li>
  <li>Junit5 and Rest Assured</li>
</ul>

## Endpoints
* `POST` api/revoult/account - Register an Account
  - example request body
  ```json
    {
    	"balance": 100,
    	"name": "Nike"
    }
  ```
* `GET` api/revoult/account/{id}; -  Returns an account with given Account Id.

* `POST` api/revoult/account/transfer - Transfer amount from one account to another
  - example request body
  ```json
    {
    	  "receiver_id": "fe42084d-62c7-4687-b293-a0002e0e6552",
          "sender_id": "411123d7-4561-4b73-9b13-dce4cf68a9c2",
          "amount": 10
    }
  ```
* `GET` api/revoult/account/{id}/transfer  - Returns all transfers made by given sender account Id

## Running

Running application as stand alone

```java -jar ./target/money-transfer-1.0-SNAPSHOT.jar``` - Running the executable jar on given port

```java -Dserver.port=8080 -jar ./target/money-transfer-1.0-SNAPSHOT.jar``` (For default port)
