# Bank Sampah API Service

## Overview

This project is a Spring Boot-based backend service for a Bank Sampah (Waste Bank) system. It
includes user authentication, profile management, point tracking, and Bank Sampah data retrieval.

## Features

- **User Authentication** with JWT (JSON Web Token).
- **User Profile Management** (Retrieve & Update).
- **User Points System** (Retrieve, Update, Reset).
- **Bank Sampah Management** (Retrieve List).
- **Account Deletion** (User can delete their own account).
- **Secure API Access** using Spring Security

## Tech Stack

- **Spring Boot** (Java)
- **PostgreSQL**
- **MongoDB**
- **Spring Data MongoDB**
- **Lombok** (for reducing boilerplate code)

## API Endpoints

### Admin

 Method   | Endpoint                              | Description                                    |
|----------|---------------------------------------|------------------------------------------------|
| `GET`    | `api/admin/dashboard`                 | Retrieve dashboard data with system statistics |
| `PUT`    | `api/admin/points`                    | Add points to a specific trash                 |
| `PUT`    | `api/admin/reset`                     | Reset user points based on request             |
| `POST`   | `api/admin/trash`                     | Add a new trash record                         |
| `DELETE` | `api/admin/trash/{trashId}`           | Delete a trash record by ID                    |
| `PUT`    | `api/admin/trash/{trashId}`           | Update specific fields of a trash record       |
| `GET`    | `api/admin/users`                     | Retrieve a list of all users                   |
| `POST`   | `api/admin/banksampah`                | Add a new `BankSampah`                         |
| `DELETE` | `api/admin/banksampah/(bankSampahId}` | Delete a `BankSampah` by ID                    |
| `PUT`    | `api/admin/banksampah/{bankSampahId}` | Update specific fields of a `BankSampah`       |

### Authentication

 Method | Endpoint            | Description                                          |
|--------|---------------------|------------------------------------------------------|
| `POST` | `api/auth/register` | Register a new user account                          |
| `POST` | `api/auth/login`    | Authenticate user and generate an access token       |
| `POST` | `api/auth/refresh`  | Refresh the access token using a valid refresh token |

### User

 Method   | Endpoint              | Description                                         |
|----------|-----------------------|-----------------------------------------------------|
| `GET`    | `api/user/profile`    | Retrieve the authenticated user's profile details   |
| `PUT`    | `api/user/profile`    | Update the authenticated user's profile information |
| `GET`    | `api/user/points`     | Retrieve the total points of the authenticated user |
| `GET`    | `api/user/banksampah` | Retrieve a list of available `BankSampah` locations |
| `DELETE` | `api/user/profile`    | Delete the authenticated user's account             |

## Setup Instructions

### Prerequisites

- **Java 17+**
- **MongoDB 6+**
- **Docker (optional)**

### Running Locally

1. Clone the repository:
   ```sh
   git clone https://github.com/your-username/bank-sampah-api.git
   cd bank-sampah-api

2. Configure application.properties
   ```properties
   spring.data.mongodb.uri=your_mongo_uri
   spring.data.mongodb.database=your_mongo_database

3. Run the application:
   ```shell
   mvn spring-boot:run

4. Access APIs at:
    - http://localhost:8080/api/admin/dashboard
    - http://localhost:8080/api/auth/register
    - http://localhost:8080/api/user/profile

## Contributors

Developed by:

- ***Steven Arycena Fatich***
- ***Rafael Deandra Pradipta***
- ***Lila Prabandaru***
- ***Muhammad Akbar Susanto***