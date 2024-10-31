# Candidate Management Application

This project is a candidate management application with an Angular frontend and a Spring Boot backend, using PostgreSQL as the database. It allows users to add, update, delete, and search for candidates, with an option to export data in CSV format.

## Table of Contents
- [Project Structure](#project-structure)
- [Prerequisites](#prerequisites)
- [Installation](#installation)
- [Database Setup](#database-setup)
- [Running the Application](#running-the-application)
- [Usage](#usage)
- [Technologies Used](#technologies-used)
- [Contributing](#contributing)
- [License](#license)

## Project Structure
- **Frontend**: [Angular application](https://github.com/NikKnez/camel-csv-frontend)
- **Backend**: [Spring Boot application](https://github.com/NikKnez/CamelCSVExport)
- **Database**: PostgreSQL for storing candidate data

## Prerequisites
Make sure you have the following installed:
- [Node.js and npm](https://nodejs.org/)
- [Angular CLI](https://angular.io/cli)
- [PostgreSQL](https://www.postgresql.org/)
- [Java 23+](https://www.oracle.com/java/technologies/downloads/#java23) (for Spring Boot backend)

## Installation

### Frontend (Angular)
1. Clone the frontend repository:
   ```bash
   git clone https://github.com/NikKnez/camel-csv-frontend.git
   ```

2. Navigate to the project directory:
    ```bash
    cd camel-csv-frontend
   ```

3. Install dependencies:
    ```bash
    npm install
    npm install bootstrap
   ```

### Backend  (Spring Boot)
1. Clone the backend repository:
   ```bash
   git clone https://github.com/NikKnez/CamelCSVExport.git
   ```
   
2. Open the project in your preferred IDE and let it sync dependencies.

### Database Setup (PostgreSQL)
1. Start PostgreSQL and create the required database and table by running the following SQL commands:
   ```sql
   CREATE DATABASE candidates_db;
   CREATE TABLE candidates (
   id SERIAL PRIMARY KEY,
   first_name VARCHAR(50),
   last_name VARCHAR(50),
   jmbg VARCHAR(13),
   year_of_birth INT,
   email VARCHAR(100),
   phone VARCHAR(20),
   notes TEXT,
   employed_after_competition BOOLEAN,
   data_update TIMESTAMP
   );
   ```
2. Update the application.properties file in the backend project with your PostgreSQL username, password, and database URL:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/candidates_db
   spring.datasource.username=YOUR_USERNAME
   spring.datasource.password=YOUR_PASSWORD
   ```
   
## Running the Application
### Frontend
To start the Angular frontend:
   ```bash
   ng serve
   ```
Navigate to http://localhost:4200 in your browser to view the application.

### Backend
To start the Spring Boot backend:

1. Navigate to the backend project root.
2. Run the application:
   ```bash
    ./mvnw spring-boot:run
   ```
The backend will be available at http://localhost:8080.

## Usage
* Use the frontend interface to manage candidate data.
* Search, add, update, delete, and export candidate information.
* Use the CSV export feature to download a list of candidates based on specific criteria.

## Technologies Used
* Frontend: Angular, Bootstrap
* Backend: Spring Boot, Apache Camel
* Database: PostgreSQL

## Contributing
Contributions are welcome! Please fork the repository and create a pull request.

## Licence
This project is licensed under the MIT License - see the [LICENSE](https://github.com/NikKnez/CamelCSVExport?tab=MIT-1-ov-file) file for details.

**Developed by [NikKnez](https://github.com/NikKnez)**