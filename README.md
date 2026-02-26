# Quiz App

This project is a fully functional quiz web application that lets users take quizzes based on a topic of their choice and select the number of questions they want.

## Features

* User account login and registration
* Quizzes generated using the **Open Trivia DB API**
* A **MySQL database** that stores user accounts and all quizzes they have taken, along with their scores
* A quiz results page that shows which questions were answered correctly or incorrectly, including the user's answer and the correct answer

## Prerequisites

* Java 17 or newer is required (the project is built with Spring Boot 3 / Maven 4).

On Ubuntu/Debian you can install Java 17 and Maven 4 it with:

```bash
sudo apt update
sudo apt install -y openjdk-17-jdk
mvn install
```

## How to Run

1. **Open the project** in your own Codespace.

2. Make sure **Prerequisites** are met

2. Run the shell script using the command:

   ```bash
   ./run.sh
   ```

3. In the Codespace **Ports** tab, open the link associated with **port 8081**.

## How to Use

You may either create your own account or use the provided test account.

### **Test Account**

* **Username:** test
* **Password:** 1234

## Future Feature Updates

* Admin account login and admin dashboard to manage users' quizzes and remove the ability for users to delete their own quizzes
* Admin option to reset user passwords if they forget them
