import scala.scalajs.js.annotation._
import TictactoeBridge._
import typings.scalaTictactoe.{distInterfaceMod as JSInterface}
import Tictactoe._

@JSExportTopLevel("Game")
object Game:
    @JSExport
    def playerMove(
        turn: JSInterface.Turn,
        player: JSInterface.Tile.X | JSInterface.Tile.O,
        targetRow: JSInterface.RowPosition,
        targetCol: JSInterface.ColPosition
    ): JSInterface.MoveResponse =
        val row = RowPositionBridge.toScala(targetRow)
        val col = ColPositionBridge.toScala(targetCol)
        val currentTurn = TurnBridge.toScala(player, turn)
        val attemptedMove = currentTurn.playerMove(row, col)
        MoveResponseBridge.tojS(attemptedMove)
    @JSExport
    def startGame: JSInterface.Turn =
        TurnBridge.toJS(Turn())
