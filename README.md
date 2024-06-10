# Exam Online System Backend

## Introduction

This is the backend of the Exam Online System. It is a RESTful API that provides endpoints for the frontend to interact with the database.

The frontend of the system is built with VueJS and can be found [here](https://github.com/vanhung4499/exam-online-system-fe).

The system is designed to be used by schools or organizations to conduct online exams. It supports multiple types of questions, random exam generation, and exam statistics.

In the future, I plan to build a school management system that includes more features like attendance, timetable, and grade, file management.

## Technologies

- Java 17
- Spring Boot 3
- Spring Security
- Mybatis Plus
- MySQL
- JWT
- Redis
- Docker & Docker Compose

## Features

There are 3 roles in the system: `admin`, `teacher`, and `student`.

Main features include:

- Authentication:
  - [x] User can register, login, and logout
  - [x] Use JWT to authenticate user
  - [x] Implement captcha to prevent brute force attack

- User Management:
  - [x] Admin can create, update, delete, and get all user
  - [x] Teacher can add student to class
  - [x] Student can view their profile, update their profile and change password
  - [ ] Import user from excel

- Class Management:
  - [x] Admin can create, update, delete, and get all class
  - [x] Teacher can create class and become owner of the class 
  - [ ] Teacher can add, remove student to class
  - [ ] Import class members from excel

- Exam:
  - [x] Admin can create, update, delete, and get all exam
  - [x] Teacher can create exam and assign exam to class they own
  - [x] Student can view and take exam assigned to their class
  - [x] Create random exam from question bank
  - [ ] Import exam from excel

- Question:
  - [x] Support many types of question: single choice, multiple choice, true/false, short answer questions.
  - [x] Question bank implementation 
  - [x] Admin can create, update, delete, and get all question
  - [x] Teacher can create question and

- Record:
  - [x] Admin can get all record of exam results
  - [x] Teacher can get all record of their class
  - [x] Student can view their record of exam
  - [x] Admin and teacher can view the statistics of exam results like max, min, average score of a exam
  - [ ] Export record to excel

- Exam Mistake:
  - [x] Admin can get all error of exam
  - [x] Teacher can get all error of their class
  - [x] Student can view their error of exam and can retry the exam error

- Notification:
  - [x] Admin can create, update, delete, and get all notification
  - [x] Teacher can create notification and send notification to class they own
  - [x] Student can view notification sent to their class or from admin
  - [ ] Implement email notification
  - [ ] Implement push notification through Firebase Cloud Messaging

- Upload:
    - [ ] Implement file upload for question, exam, and user image profile
    - [ ] Implement media server to store file (AWS S3, Cloudinary, AliOSS, etc.)

## Running the application

> Make sure you have Java 17, Maven, Docker, and Docker Compose installed on your machine

1. Clone the repository
```bash
git clone https://github.com/vanhung4499/exam-online-system-be
```

2. Copy the `.env.example` file to `.env` and fill in the environment variables

```bash
cp .env.example .env
```

3. Run the mysql and redis server using docker-compose

```bash
docker-compose up -d
```

4. Import database schema to mysql server

```bash
mysql -u root -p exam < db/schema.sql
```

5. Run maven to install dependencies and build the project

```bash
mvn clean install
```

6. Run the application

```bash
mvn spring-boot:run
```

> You can run the application in IntelliJ IDEA by running the `ExamApplication` class

## Database Schema

You can find the database schema in the `db` folder

TODO: add the database schema image

## API Documentation

Todo: Add OpenAPI documentation

## Deploy

Todo: Add deployment instructions

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## References

- [Spring Boot](https://spring.io/projects/spring-boot)
- [Spring Security](https://spring.io/projects/spring-security)
- [Mybatis Plus](https://baomidou.com/)
- [MySQL](https://www.mysql.com/)
- [JWT](https://jwt.io/)
- [Redis](https://redis.io/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/)