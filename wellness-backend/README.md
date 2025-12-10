# Wellness Marketplace - Backend

Spring Boot backend for the Wellness Marketplace project.

## Features
- User registration & login (JWT)
- Role-based users (PRACTITIONER, PATIENT, etc.)
- Practitioner profile entity
- MySQL persistence (via Spring Data JPA)
- BCrypt password hashing

## Quick start

### Prerequisites
- Java 17
- Maven
- MySQL (or use RDS/container)
- (Optional) Postman for API testing

### Setup
1. Create a MySQL database:
   ```sql
   CREATE DATABASE wellness_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
