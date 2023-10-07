import Tictactoe.*
import scala.io.StdIn.readLine

@main def main(): Unit =
    println("Welcome to Tic Tac Toe")
    val firstRound = Turn()

    val rowMapping = Map(
      "0" -> RowPosition.Bottom,
      "1" -> RowPosition.Middle,
      "2" -> RowPosition.Top
    )

    val colMapping = Map(
      "0" -> ColPosition.Left,
      "1" -> ColPosition.Center,
      "2" -> ColPosition.Right
    )

    gameLoop(firstRound, rowMapping, colMapping)

def gameLoop(
    gameRound: Turn,
    rowMapping: Map[String, RowPosition],
    colMapping: Map[String, ColPosition]
): Unit =
    val currentPlayer = gameRound.currentPlayer

    println(s"Current player: $currentPlayer")
    println(gameRound.gameBoard)

    val rowPrompt = s"Choose your row player: $currentPlayer. 0 1 2"
    val colPrompt = s"Choose your column player: $currentPlayer. 0 1 2"

    val row = getTargetPosition(rowPrompt, rowMapping)
    val col = getTargetPosition(colPrompt, colMapping)

    val attemptedMove = gameRound.playerMove(row, col)

    attemptedMove match

        case Right((updatedRound, Outcome.Ongoing)) =>
            gameLoop(updatedRound, rowMapping, colMapping)

        case Right((_, Outcome.Win)) =>
            println(s"Player $currentPlayer wins!")

        case Right((_, Outcome.Draw)) =>
            println("The match ended in a draw!")

        case Left(error) =>
            println(error)
            gameLoop(gameRound, rowMapping, colMapping)

def getTargetPosition[T](prompt: String, mapping: Map[String, T]): T =
    println(prompt)
    val userInput = readLine()

    mapping.get(userInput) match
        case Some(position) => position
        case None =>
            println(s"Invalid input: $userInput, try again.")
            getTargetPosition(prompt, mapping)
