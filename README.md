# POOB vs. Zombies
This project is entirely based on the original *Plants vs. Zombies* game developed by PopCap Games, utilizing assets from the entire game series, including those from EA Games. The project was developed over the course of one month as part of the third term of the **POOB** (Programación Orientada a Objetos) class at the **Escuela Colombiana de Ingeniería Julio Garavito** during the 2024 - 02 cycle.

The game offers three modes:

1. **Versus Mode**: Inspired by the PS3 and Xbox 360 versions of the game, where the player controlling the plants must first strategically place all their plants, and then the player controlling the zombies attempts to defeat them.
   
2. **Survival Mode**: The standard mode where the player selects a garden, sets the time, chooses resources, and battles through waves of zombies.

3. **Trials Mode**: A self-playing mode where the game is played autonomously using a predefined machine setup. The available machine types are: *Strategic* and *Original*.

This game replicates core mechanics from the original *Plants vs. Zombies* while adding some unique features and modes.

### Content
- **src Directory**: Contains all the main components of the game, including their respective `.java` files. It includes the following subdirectories:
1. **domain**: Manages the game logic, as well as the classes representing game entities, the environment, and data persistence.
2. **presentation**: Handles user interaction and the game interface. This package imports the domain package to provide an appropriate representation of the game.
3. **resources**: Stores all game assets, including images, fonts, PNG sequences, sound effects, and background music.
4. **test**: Contains unit tests for the domain package, using JUnit 5.8.1 for testing purposes.

- **lib Directory**: Contains the necessary JUnit 5.8.1 `.jar` files required to run the tests located in the `src` directory.

- **bin Directory**: Follows the same structure of the `src` directory and contains all the compiled `.class` files from the game.

- **POOBvsZombies.asta**: An AstahUML file that includes the project’s package diagrams, class diagrams, and sequence diagrams.


#### Instructions
To run this program, follow these steps:

1. Download the Source Code:
- Download the `src` folder along with all its resources.

2. Compile and Run:
- You can either compile and run the program via the console or use an IDE of your choice.

3. JDK Requirement:
- Ensure that JDK 23 is installed on your system.

4. Running Unit Tests:
- To run the unit tests located in the `src/test` folder, you must download all the `.jar` files in the lib folder.
- These files are required to run JUnit **5.8.1** tests.

## Built With
* [IntelliJ](https://www.jetbrains.com/es-es/idea/)
* [Astah Community Edition](https://astah.net/)

## Authors
* **LePeanutButter** - [LePeanutButter](https://github.com/LePeanutButter)
* **Lanapequin** - [Lanapequin](https://github.com/Lanapequin)