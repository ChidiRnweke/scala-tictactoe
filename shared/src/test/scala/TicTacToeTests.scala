import munit._
import Tictactoe.*

class TicTacToeTests extends FunSuite:

    test("Row is complete with identical tiles") {
        val completeRow = Row(Tile.X, Tile.X, Tile.X)
        assertEquals(completeRow.isComplete, true)
    }

    test("Row is not complete with only empty tiles") {
        val completeRow = Row(Tile.Empty, Tile.Empty, Tile.Empty)
        assertEquals(completeRow.isComplete, false)
    }

    test("Row is not complete with mixed tiles") {
        val incompleteRow = Row(Tile.X, Tile.O, Tile.X)
        assertEquals(incompleteRow.isComplete, false)
    }

    test("Row is full with all tiles occupied") {
        val fullRow = Row(Tile.X, Tile.O, Tile.X)
        assertEquals(fullRow.isFull, true)
    }

    test("Row is not full with any tile empty") {
        val notFullRow = Row(Tile.X, Tile.Empty, Tile.X)
        assertEquals(notFullRow.isFull, false)
    }

    test("Board is not full when empty") {
        val emptyBoard = Board()
        assertEquals(emptyBoard.isFull, false)
    }

    test("Board is full with all tiles occupied") {
        val fullBoard = Board(
          Row(Tile.X, Tile.O, Tile.X),
          Row(Tile.X, Tile.X, Tile.O),
          Row(Tile.O, Tile.X, Tile.O)
        )
        assertEquals(fullBoard.isFull, true)
    }

    test("Winning with a diagonal from top-left to bottom-right") {
        val diagonalCompleteBoard = Board(
          topRow = Row(left = Tile.X),
          middleRow = Row(center = Tile.X),
          bottomRow = Row(right = Tile.X)
        )
        val game = Turn(gameBoard = diagonalCompleteBoard)
        val outcome = game.playerMove(RowPosition.Bottom, ColPosition.Left)
        val (_, gameOutcome) = outcome.right.get
        assertEquals(gameOutcome, Outcome.Win)
    }

    test("Winning with a horizontal board") {
        val horizontal = Board(
          topRow = Row(right = Tile.X),
          middleRow = Row(right = Tile.X),
          bottomRow = Row(right = Tile.X)
        )
        val game = Turn(currentPlayer = Tile.X, gameBoard = horizontal)
        val outcome = game.playerMove(RowPosition.Bottom, ColPosition.Left)
        val (_, gameOutcome) = outcome.right.get
        assertEquals(gameOutcome, Outcome.Win)
    }

    test("Move to an already occupied tile should fail.") {
        val game = Turn(gameBoard = Board(topRow = Row(left = Tile.X)))
        val outcome = game.playerMove(RowPosition.Top, ColPosition.Left)
        assertEquals(outcome.isLeft, true)
    }

    test("Player switch after move") {
        val game = Turn(currentPlayer = Tile.X)
        val outcome = game.playerMove(RowPosition.Top, ColPosition.Left)
        val (newGame, _) = outcome.right.get
        assertEquals(newGame.currentPlayer, Tile.O)
    }

    test("Game ends in a draw") {
        val almostFullBoard = Board(
          topRow = Row(Tile.X, Tile.O, Tile.X),
          middleRow = Row(Tile.X, Tile.X, Tile.O),
          bottomRow = Row(Tile.O, Tile.X, Tile.Empty)
        )
        val game = Turn(currentPlayer = Tile.O, gameBoard = almostFullBoard)
        val outcome = game.playerMove(RowPosition.Bottom, ColPosition.Right)
        val (_, gameOutcome) = outcome.right.get
        assertEquals(gameOutcome, Outcome.Draw)
    }
