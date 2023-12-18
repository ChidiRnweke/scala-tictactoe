import Tictactoe.*
import cats.effect.{IO, IOApp}
import cats.data.EitherT
import cats.syntax.all._

object Main extends IOApp.Simple:
    val welcomeMsg = IO.println("Welcome to Tic Tac Toe")
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
    val run = welcomeMsg >> gameLoop(firstRound, rowMapping, colMapping)

def gameLoop(
    currentTurn: Turn,
    rowMapping: Map[String, RowPosition],
    colMapping: Map[String, ColPosition]
): IO[Unit] =
    val currentPlayer = currentTurn.currentPlayer
    val rowMsg = s"Choose your row player: $currentPlayer. 0 1 2"
    val colMsg = s"Choose your column player: $currentPlayer. 0 1 2"
    def nextTurn(next: Turn): IO[Unit] = gameLoop(next, rowMapping, colMapping)
    def restartTurn(err: String) = IO.println(err) >> nextTurn(currentTurn)

    def nextTurnOrEnd(matchTurn: Tuple2[Turn, Outcome]): IO[Unit] =
        matchTurn match
            case (next, Outcome.Ongoing) => nextTurn(next)
            case (_, Outcome.Win)  => IO.println(s"Player $currentPlayer wins!")
            case (_, Outcome.Draw) => IO.println("The match ended in a draw!")

    val attemptedMove = EitherT(
      for
          _ <- IO.println(s"Current player: $currentPlayer")
          _ <- IO.println(currentTurn.gameBoard)
          row <- IO.println(rowMsg) >> getTargetPosition(rowMapping)
          col <- IO.println(colMsg) >> getTargetPosition(colMapping)
      yield (currentTurn.playerMove(row, col))
    )
    attemptedMove.foldF(restartTurn(_), nextTurnOrEnd(_))

def getTargetPosition[T](mapping: Map[String, T]): IO[T] =
    def askAgain(invalidInput: String): IO[T] =
        IO.println(s"Invalid input: $invalidInput, try again.") >> askUser
    def askUser: IO[T] = for
        input <- IO.readLine
        position <- mapping.get(input).fold(askAgain(input))(IO.pure(_))
    yield position

    askUser
