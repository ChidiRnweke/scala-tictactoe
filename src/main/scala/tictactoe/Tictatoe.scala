package Tictactoe

enum Tile:
    case X, O, Empty

    override def toString: String = this match
        case Tile.X     => "X"
        case Tile.O     => "O"
        case Tile.Empty => " "

enum RowPosition:
    case Top, Middle, Bottom

enum ColPosition:
    case Left, Center, Right

enum Outcome:
    case Ongoing, Win, Draw

case class Row(
    left: Tile = Tile.Empty,
    center: Tile = Tile.Empty,
    right: Tile = Tile.Empty
):

    val isComplete: Boolean = left == center &&
        center == right &&
        left != Tile.Empty

    val isFull: Boolean = left != Tile.Empty &&
        center != Tile.Empty &&
        right != Tile.Empty

    override def toString: String = s"|${left}|${center}|${right}|"

case class Board(
    bottomRow: Row = Row(),
    middleRow: Row = Row(),
    topRow: Row = Row()
):

    val isFull: Boolean = topRow.isFull && middleRow.isFull && bottomRow.isFull

    val completed: Boolean = diagonalOne.isComplete ||
        diagonalTwo.isComplete ||
        topRow.isComplete ||
        middleRow.isComplete ||
        bottomRow.isComplete ||
        LeftVertical.isComplete ||
        centerVertical.isComplete ||
        rightVertical.isComplete

    override def toString: String =
        s"""2 ${topRow.toString}
           |1 ${middleRow.toString}
           |0 ${bottomRow.toString}
           | 0  1  2""".stripMargin

    private lazy val diagonalOne: Row =
        Row(topRow.left, middleRow.center, bottomRow.right)

    private lazy val diagonalTwo: Row =
        Row(topRow.right, middleRow.center, bottomRow.left)

    private lazy val LeftVertical: Row =
        Row(topRow.left, middleRow.left, bottomRow.left)

    private lazy val centerVertical: Row =
        Row(topRow.center, middleRow.center, bottomRow.center)

    private lazy val rightVertical: Row =
        Row(topRow.right, middleRow.right, bottomRow.right)

case class Turn(currentPlayer: Tile = Tile.X, gameBoard: Board = Board()):

    def playerMove(
        targetRow: RowPosition,
        targetCol: ColPosition
    ): Either[String, (Turn, Outcome)] =
        val row = getRow(targetRow)
        val attemptedMove = updateRow(row, targetCol)

        attemptedMove match
            case Right(validRow) =>
                val newBoard = updateBoard(validRow, targetRow)
                val outcome = getOutcome(newBoard)
                Right(Turn(nextPlayer, newBoard), outcome)

            case Left(error) => Left(error)

    private def getOutcome(board: Board): Outcome =
        if (board.completed) Outcome.Win
        else if (board.isFull) Outcome.Draw
        else Outcome.Ongoing

    private def updateBoard(newRow: Row, targetRow: RowPosition): Board =
        targetRow match
            case RowPosition.Top    => gameBoard.copy(topRow = newRow)
            case RowPosition.Middle => gameBoard.copy(middleRow = newRow)
            case RowPosition.Bottom => gameBoard.copy(bottomRow = newRow)

    private def getRow(row: RowPosition): Row =
        row match
            case RowPosition.Top    => gameBoard.topRow
            case RowPosition.Middle => gameBoard.middleRow
            case RowPosition.Bottom => gameBoard.bottomRow

    private def updateRow(
        selectedRow: Row,
        targetCol: ColPosition
    ): Either[String, Row] =
        targetCol match
            case ColPosition.Left if selectedRow.left == Tile.Empty =>
                Right(selectedRow.copy(left = currentPlayer))

            case ColPosition.Center if selectedRow.center == Tile.Empty =>
                Right(selectedRow.copy(center = currentPlayer))

            case ColPosition.Right if selectedRow.right == Tile.Empty =>
                Right(selectedRow.copy(right = currentPlayer))

            case _ => Left("You can only set a value on an empty tile.")

    private val nextPlayer: Tile =
        if (currentPlayer == Tile.X) Tile.O else Tile.X
