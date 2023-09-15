# Spring Boot TechBazaar Application


## Installation

Before running this project, make sure you have the following prerequisites:

- Java Development Kit (JDK) 17 or higher.
- Maven build tool.
- MySQL database server.


1. Configure the database by editing the `src/main/resources/application.properties` file with your database connection details:

   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306//test
   spring.datasource.username=your-username
   spring.datasource.password=your-password
   ```

2. Create the database schema by running the SQL script provided in the `database` directory.

3. Install the dependencies using the following command:

   ```bash
   mvn clean install
   ```
4. Run the application using the following command:

   ```bash
   mvn spring-boot:run
   ```
5. Open your browser and visit http://localhost:8080