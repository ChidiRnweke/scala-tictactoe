# Tic Tac Toe in Scala

Welcome to a simple implementation of the classic game, Tic Tac Toe, written in Scala 3.

## Overview

This is a simple Tic Tac Toe game built with Scala 3, exploring cross-compilation for JVM, JavaScript, and Native platforms. The project aims to demonstrate a functional programming approach, particularly using the IO monad for managing side effects. It's aim is also to explore cross compiling to both native and JS. In particular, using Scala to write libraries that are consumed by JS.


**Project Structure:**
- `shared/src/main/scala/Tictactoe.scala`: Contains the core game logic.
- `jvm-native/`: All the code for both the JVM and native.
- `js/src/main/scala/`: Code to bridge scala types with typescript.
- `js/src/main/typescript`: Typescript code for the frontend.


# Getting Started

## Prerequisites
* Scala 3.3.0
* sbt for building and running the project
  
## How to Run

Depending on your target platform, do one of the following:

* For JVM: clone repo `sbt tictactoeJVM/run` after cloning the repo.
* For JavaScript: Web component available through [JSDelivr](https://www.jsdelivr.com/package/npm/scala-tictactoe). You can build it from scratch by running `sh jsBuild.sh`, this is mostly handy if you're changing something on the Scala side of the project. If it's just styling then `npx rollup -c` is enough.
* For Native: `sbt tictactoeNative/run` or run the Linux binaries from `tictactoe-out`.

## Platform Details
* JVM and Native: These versions focus on basic console-based interaction. The Native build supports direct execution on Linux.
* JavaScript: Integrates Scala.js for the logic, providing a TypeScript interface and a web component for browser use. It's published on npm as [scala-tictactoe](https://www.npmjs.com/package/scala-tictactoe).

## Tests

It's worth noting that only `shared` has been unit-tested, as it encapsulates the logic of the game. To run it should do `sbt tictactoeJVM/test`. Testing the core logic with native also works but compile times are longer. Testing the core with scala.js doesn't work, it would require extra setups. Considering the semantics of the 3 platforms are the same it should be similar.


## Note
This project is experimental, focusing on cross-compiling Scala code. The structure might need refinement, but it serves as a solid proof of concept.

May the best player win!

---