# Tic Tac Toe in Scala

Welcome to a simple implementation of the classic game, Tic Tac Toe, written in Scala 3.

## Overview

This project uses a functional object-oriented style. The emphasis is on immutability and minimizing side effects, which aligns well with functional programming principles. Side effects are allowed for printing and getting input from the user, but they are kept away from the logic. The "architecture" ensures a clear separation of business logic from I/O operations, making the core logic easily testable.

**Project Structure:**
- `Tictactoe.scala`: Contains the core game logic.
- `main.scala`: Deals primarily with I/O operations to interact with the player.

It's worth noting that only `Tictactoe.scala` has been unit-tested (using mUnit), as it encapsulates the business logic of the game.

## Getting Started

### Prerequisites

- Scala Version: 3.3.0
- sbt (for building, running, and testing)

### How to Play

1. Clone the repository.
2. Navigate to the project directory and run the game using sbt:
    ```
    sbt run
    ```
3. Follow the on-screen prompts to play the game:
    - You'll be asked to choose a row (0, 1, or 2) and then a column (0, 1, or 2).
    - Players take turns choosing positions on the board.
    - The game ends when one player gets three in a row, column, or diagonal, or when all positions on the board are filled, resulting in a draw.

## Tests

The game comes equipped with unit tests to ensure its logic and functionality are accurate. To run the tests, use the following command:

```
sbt test
```

May the best player win!

---