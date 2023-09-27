# Library-Management-System

Technology Stack:
    Backend: Java (with Spring Boot)
    Frontend: HTML, CSS(Bootstrap), JavaScript
    Database: PostgreSQL
    Security: Spring Security
    Database Access: Hibernate
    User Interface Templating: Thymeleaf

To set up And run the app on your local machine, follow these steps:
    1. Clone the repository onto your local machine.
    2. Create a new PostgreSQL database schema. Go to the application.yml file and in the datasource URL add a URL to your schema.
    3. In the application.yml file write your datasource username and password.
    4. In the application.yml file write your mail host, your username(email), and the application password provided. For guidance, watch this video: https://www.youtube.com/watch?v=ugIUObNHZdo.
    5. In the application.yml file configure the server port
    6. Use your preferred IDE (IntelliJ, Eclipse, etc.) to open and run the project or build and run it using Maven. Maven command to run the app - mvn spring-boot:run.
    7. After running the application, you will be prompted to log in or register. After you complete the registration, log in using the username and password you used during registration.
    8. Add your own patrons, books, genres, authors, loans.

Description:
  This project aims to create a Library Management System for Librarians. The application manages patrons, books, authors, genres, and book loans. A book can have multiple authors and belong to multiple genres. Tables can be sorted by certain fields by clicking on 
  corresponding table heads. The application also implements pagination. To notify patrons about the book loans that haven't been turned in on time, you can click the notify button on the patron's page and an email will be sent to the chosen patron. Patrons have 
  membership statuses, which indicate how long they can keep the loaned books.
