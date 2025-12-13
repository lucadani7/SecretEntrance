# 🎄 Secret Entrance

![Java](https://img.shields.io/badge/Language-Java_21-orange)
![Build](https://img.shields.io/badge/Build-Maven-blue)
![Status](https://img.shields.io/badge/Status-Completed-success)
![Javadoc](lucadani7.github.io/SecretEntrance/)

## 📖 About the Project
This serves as a solution to the "Elf Secret Entrance" puzzle. The goal is to simulate a safe's rotary dial mechanism to recover a lost password needed by the Elves for Christmas.

The application processes a list of rotation instructions (e.g., `R50`, `L10`) and calculates how many times the dial lands exactly on the target position (`0`).

## ⚙️ Technical Logic
The safe dial consists of **100 positions** (0 to 99). The core logic handles:
* **Circular Arithmetic:** Moving past 99 wraps around to 0.
* **Negative Wrap-around:** Moving left past 0 wraps around to 99.
* **Directional Input:** parsing 'R' (Clockwise) and 'L' (Counter-Clockwise).

## 🚀 How to Run

### Prerequisites
* Java JDK 21+
* Maven 3.6+

### Running the application
1.  Clone the repository:
    ```bash
    git clone https://github.com/lucadani7/SecretEntrance.git
    ```
2.  Navigate to the project folder:
    ```bash
    cd SecretEntrance
    ```
3.  Compile and run using Maven:
    ```bash
    mvn clean compile exec:java -Dexec.mainClass="com.lucadani.SecretEntrance"
    ```
    *(Or simply run the `main` method in IntelliJ IDEA)*

## 🧪 Testing
The project includes a robust JUnit 5 test suite covering:
* Standard rotations.
* Full 360-degree spins.
* Edge cases (exactly on 0).
* Negative wrap-around logic.

Run the tests with:
```bash
mvn test
