# TEnmo Documentation

### Overview of the Application

TEnmo is an online payment service for transferring "TE bucks" between friends. This application allows users to register, log in, view their account balance, send and request transfers of TE bucks, view transfer history, and manage pending transfer requests. The application consists of a backend RESTful API server and a frontend client built with React.

### Running the Application Locally

To run the TEnmo application locally, you need to set up and run both the backend server and the frontend client. Follow the steps below to get everything up and running.

#### Prerequisites

- Java 11 or higher
- PostgreSQL
- Node.js and npm

#### Backend Server

1. **Clone the Repository:**

   ```sh
   git clone https://github.com/cameronmbacon/TEnmo.git
   cd TEnmo
   ```

2. **Set Up PostgreSQL Database:**

   - Create a new PostgreSQL database called `tenmo`.
   - Run the `database/tenmo.sql` script in your PostgreSQL client to set up the database schema.

3. **Configure the Application:**

   Update the `src/main/resources/application.properties` file with your database connection details:

   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/tenmo
   spring.datasource.username=YOUR_DB_USERNAME
   spring.datasource.password=YOUR_DB_PASSWORD
   ```

4. **Build and Run the Server:**

   ```sh
   ./mvnw spring-boot:run
   ```

   The server should now be running on `http://localhost:8080`.

#### Frontend Client

1. **Navigate to the Client Directory:**

   ```sh
   cd client
   ```

2. **Install Dependencies:**

   ```sh
   npm install
   ```

3. **Start the Client:**

   ```sh
   npm start
   ```

   The client should now be running on `http://localhost:3000`.

### Using the Application

Once both the client and server are running, you can access the application by navigating to `http://localhost:3000` in your web browser. From there, you can register a new user, log in, and start using the features of the application.

By following these steps, you should be able to set up and run the TEnmo application locally, both the backend server and the frontend client. If you encounter any issues, ensure that all dependencies are correctly installed and that the database is properly configured.

---------------------------------------------------------------

# API Documentation

## Overview

The following section describes the available API endpoints for the TEnmo application. For each endpoint, the path, expected input, and expected output are provided.

## Endpoints

### UserController

#### Base URL

`/tenmo/users`

---

#### List All Users

**Endpoint:** `GET /tenmo/users`

**Description:** Retrieves a list of all registered users.

**Request:**

- Method: `GET`
- URL: `/tenmo/users`

**Response:**

- Status: `200 OK`
- Body: Array of `User` objects
  ```json
  [
      {
          "id": 1,
          "username": "user1"
      },
      {
          "id": 2,
          "username": "user2"
      }
  ]
  ```

---

#### Get User by ID

**Endpoint:** `GET /tenmo/users/id/{id}`

**Description:** Retrieves a user by their user ID.

**Request:**

- Method: `GET`
- URL: `/tenmo/users/id/{id}`

**Path Parameters:**

- `id` (integer): The ID of the user to retrieve.

**Response:**

- Status: `200 OK`
- Body: `User` object
  ```json
  {
      "id": 1,
      "username": "user1"
  }
  ```

---

#### Get User by Username

**Endpoint:** `GET /tenmo/users/username/{username}`

**Description:** Retrieves a user by their username.

**Request:**

- Method: `GET`
- URL: `/tenmo/users/username/{username}`

**Path Parameters:**

- `username` (string): The username of the user to retrieve.

**Response:**

- Status: `200 OK`
- Body: `User` object
  ```json
  {
      "id": 1,
      "username": "user1"
  }
  ```

---

### AccountController

#### Base URL

`/tenmo/accounts`

---

#### Get Account by Account ID

**Endpoint:** `GET /tenmo/accounts/accountId/{id}`

**Description:** Retrieves an account by its account ID.

**Request:**

- Method: `GET`
- URL: `/tenmo/accounts/accountId/{id}`

**Path Parameters:**

- `id` (integer): The ID of the account to retrieve.

**Response:**

- Status: `200 OK`
- Body: `Account` object
  ```json
  {
      "id": 1,
      "userId": 1,
      "balance": 1000.00
  }
  ```

---

#### Get Account by User ID

**Endpoint:** `GET /tenmo/accounts/userId/{id}`

**Description:** Retrieves an account by the user ID of the account owner.

**Request:**

- Method: `GET`
- URL: `/tenmo/accounts/userId/{id}`

**Path Parameters:**

- `id` (integer): The ID of the user whose account to retrieve.

**Response:**

- Status: `200 OK`
- Body: `Account` object
  ```json
  {
      "id": 1,
      "userId": 1,
      "balance": 1000.00
  }
  ```

---

#### Get Account Balance by User ID

**Endpoint:** `GET /tenmo/accounts/userId/{userId}/balance`

**Description:** Retrieves the balance of an account by the user ID of the account owner.

**Request:**

- Method: `GET`
- URL: `/tenmo/accounts/userId/{userId}/balance`

**Path Parameters:**

- `userId` (integer): The ID of the user whose account balance to retrieve.

**Response:**

- Status: `200 OK`
- Body: Balance as a `BigDecimal`
  ```json
  1000.00
  ```

#### Base URL

`/tenmo/transfers`

---

#### Create Send Transfer

**URL:** `/tenmo/transfers/send`

**Method:** `POST`

**Description:** Creates a new send transfer from one account to another.

**Request Body:**

```json
{
  "transferId": 0,
  "transferTypeId": 2,
  "transferStatusId": 0,
  "accountFromId": 1,
  "accountToId": 2,
  "amount": 100.00
}
```

**Response:**

- **201 Created**

  ```json
  {
    "transferId": 1,
    "transferTypeId": 2,
    "transferStatusId": 2,
    "accountFromId": 1,
    "accountToId": 2,
    "amount": 100.00
  }
  ```

**Error Responses:**

- **400 Bad Request:** Invalid input data.
- **401 Unauthorized:** User is not authenticated.
- **403 Forbidden:** User cannot transfer to the same account.

---

#### Create Request Transfer

**URL:** `/tenmo/transfers/request`

**Method:** `POST`

**Description:** Creates a new transfer request from one account to another.

**Request Body:**

```json
{
  "transferId": 0,
  "transferTypeId": 1,
  "transferStatusId": 0,
  "accountFromId": 1,
  "accountToId": 2,
  "amount": 50.00
}
```

**Response:**

- **201 Created**

  ```json
  {
    "transferId": 1,
    "transferTypeId": 1,
    "transferStatusId": 1,
    "accountFromId": 1,
    "accountToId": 2,
    "amount": 50.00
  }
  ```

**Error Responses:**

- **400 Bad Request:** Invalid input data.
- **401 Unauthorized:** User is not authenticated.
- **403 Forbidden:** User cannot request transfer to the same account.

---

#### Accept Request Transfer

**URL:** `/tenmo/transfers/request/{transferId}/accept`

**Method:** `PUT`

**Description:** Accepts a transfer request.

**Path Variables:**

- **transferId**: ID of the transfer to accept.

**Response:**

- **202 Accepted**

  ```json
  {
    "transferId": 1,
    "transferTypeId": 1,
    "transferStatusId": 2,
    "accountFromId": 1,
    "accountToId": 2,
    "amount": 50.00
  }
  ```

**Error Responses:**

- **400 Bad Request:** Invalid transfer ID.
- **401 Unauthorized:** User is not authenticated.
- **404 Not Found:** Transfer not found.
- **403 Forbidden:** User cannot accept transfer.

---

#### Reject Request Transfer

**URL:** `/tenmo/transfers/request/{transferId}/reject`

**Method:** `PUT`

**Description:** Rejects a transfer request.

**Path Variables:**

- **transferId**: ID of the transfer to reject.

**Response:**

- **202 Accepted**

  ```json
  {
    "transferId": 1,
    "transferTypeId": 1,
    "transferStatusId": 3,
    "accountFromId": 1,
    "accountToId": 2,
    "amount": 50.00
  }
  ```

**Error Responses:**

- **400 Bad Request:** Invalid transfer ID.
- **401 Unauthorized:** User is not authenticated.
- **404 Not Found:** Transfer not found.
- **403 Forbidden:** User cannot reject transfer.

---

#### Get Transfers for User

**URL:** `/tenmo/transfers/{userId}`

**Method:** `GET`

**Description:** Retrieves all transfers for a user.

**Path Variables:**

- **userId**: ID of the user.

**Response:**

- **200 OK**

  ```json
  [
    {
      "transferId": 1,
      "transferTypeId": 2,
      "transferStatusId": 2,
      "accountFromId": 1,
      "accountToId": 2,
      "amount": 100.00
    },
    {
      "transferId": 2,
      "transferTypeId": 1,
      "transferStatusId": 1,
      "accountFromId": 2,
      "accountToId": 1,
      "amount": 50.00
    }
  ]
  ```

**Error Responses:**

- **400 Bad Request:** Invalid user ID.
- **401 Unauthorized:** User is not authenticated.
- **404 Not Found:** User not found.

---

#### Get Transfer Details

**URL:** `/tenmo/transfers/{transferId}/details`

**Method:** `GET`

**Description:** Retrieves details of a specific transfer.

**Path Variables:**

- **transferId**: ID of the transfer.

**Response:**

- **200 OK**

  ```json
  {
    "transferId": 1,
    "transferTypeId": 2,
    "transferStatusId": 2,
    "accountFromId": 1,
    "accountToId": 2,
    "amount": 100.00
  }
  ```

**Error Responses:**

- **400 Bad Request:** Invalid transfer ID.
- **401 Unauthorized:** User is not authenticated.
- **404 Not Found:** Transfer not found.

---

### TransferDto

**Description:** Data Transfer Object for transferring money between accounts.

**Fields:**

- `transferId` (int): Unique ID of the transfer.
- `transferTypeId` (int): ID representing the type of the transfer.
- `transferStatusId` (int): ID representing the status of the transfer.
- `accountFromId` (int): ID of the account sending the money.
- `accountToId` (int): ID of the account receiving the money.
- `amount` (BigDecimal): Amount of money being transferred.

**Example:**

```json
{
  "transferId": 1,
  "transferTypeId": 2,
  "transferStatusId": 2,
  "accountFromId": 1,
  "accountToId": 2,
  "amount": 100.00
}
```
