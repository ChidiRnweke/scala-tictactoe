package TictactoeBridge

import typings.scalaTictactoe.{distInterfaceMod as JSInterface}
import scala.scalajs.js

import Tictactoe._

object TileBridge:
    import JSInterface.{Tile as SJSTile}
    def toScala(jsTile: SJSTile): Tile =
        jsTile match
            case SJSTile.Empty => Tile.Empty
            case SJSTile.O     => Tile.O
            case SJSTile.X     => Tile.O

    def toJS(tile: Tictactoe.Tile): SJSTile =
        tile match
            case Tile.X     => SJSTile.X
            case Tile.O     => SJSTile.O
            case Tile.Empty => SJSTile.Empty

object RowPositionBridge:
    import JSInterface.{RowPosition as SJSRowPosition}

    def toScala(jsRowPos: SJSRowPosition): RowPosition =
        jsRowPos match
            case SJSRowPosition.Bottom => RowPosition.Bottom
            case SJSRowPosition.Middle => RowPosition.Middle
            case SJSRowPosition.Top    => RowPosition.Top

    def toJS(jsRowPos: Tictactoe.RowPosition): SJSRowPosition =
        jsRowPos match
            case RowPosition.Bottom => SJSRowPosition.Middle
            case RowPosition.Middle => SJSRowPosition.Middle
            case RowPosition.Top    => SJSRowPosition.Top

object ColPositionBridge:
    import JSInterface.{ColPosition as SJSColPosition}

    def toScala(js: SJSColPosition): ColPosition =
        js match
            case SJSColPosition.Left   => ColPosition.Left
            case SJSColPosition.Center => ColPosition.Center
            case SJSColPosition.Right  => ColPosition.Right

    def toJS(scala: Tictactoe.ColPosition): SJSColPosition =
        scala match
            case ColPosition.Left   => SJSColPosition.Left
            case ColPosition.Center => SJSColPosition.Center
            case ColPosition.Right  => SJSColPosition.Right

object OutcomeBridge:
    import JSInterface.{Outcome as SJSOutcome}

    def toJS(scala: Tictactoe.Outcome): SJSOutcome =
        scala match
            case Tictactoe.Outcome.Ongoing => SJSOutcome.Ongoing
            case Tictactoe.Outcome.Win     => SJSOutcome.Win
            case Tictactoe.Outcome.Draw    => SJSOutcome.Draw

object RowBridge:
    import JSInterface.{Row as SJSRow}

    def toScala(js: SJSRow): Row =
        val left = TileBridge.toScala(js.left)
        val center = TileBridge.toScala(js.center)
        val right = TileBridge.toScala(js.right)
        Row(left, center, right)

    def toJS(scala: Tictactoe.Row): SJSRow =
        val left = TileBridge.toJS(scala.left)
        val center = TileBridge.toJS(scala.center)
        val right = TileBridge.toJS(scala.right)
        SJSRow(left, center, right)

object BoardBridge:
    import JSInterface.{Board as JSBoard}

    def toScala(js: JSBoard): Board =
        val bottom = RowBridge.toScala(js.bottomRow)
        val middle = RowBridge.toScala(js.middleRow)
        val top = RowBridge.toScala(js.topRow)
        Board(bottom, middle, top)

    def toJS(scala: Tictactoe.Board): JSBoard =
        val bottom = RowBridge.toJS(scala.bottomRow)
        val middle = RowBridge.toJS(scala.middleRow)
        val top = RowBridge.toJS(scala.topRow)
        JSBoard(bottom, middle, top)

object TurnBridge:
    import JSInterface.{Turn as JSTurn}

    def toScala(
        jsPlayer: JSInterface.Tile.X | JSInterface.Tile.O,
        jsTurn: JSInterface.Turn
    ): Turn =
        val currentPlayer = TileBridge.toScala(jsPlayer)
        val board = BoardBridge.toScala(jsTurn.gameBoard)
        Turn(currentPlayer, board)

    def toJS(scala: Tictactoe.Turn): JSTurn =
        type XorO = JSInterface.Tile.X | JSInterface.Tile.O
        val player =
            (if scala.currentPlayer == Tile.X then JSInterface.Tile.X
             else JSInterface.Tile.O).asInstanceOf[XorO]
        val board = BoardBridge.toJS(scala.gameBoard)
        JSTurn(player, board)

object TurnInfoBridge:
    import JSInterface.{TurnInfo as JSTurnInfo}

    def toJS(scala: Tuple2[Turn, Outcome]): JSTurnInfo =
        val turn = TurnBridge.toJS(scala._1)
        val outcome = OutcomeBridge.toJS(scala._2)
        JSTurnInfo(turn, outcome)

object MoveResponseBridge:
    import JSInterface.{MoveResponse}

    def tojS(scala: Either[String, Tuple2[Turn, Outcome]]): MoveResponse =
        scala match
            case Left(err)  => MoveResponse(err, false)
            case Right(res) => MoveResponse(TurnInfoBridge.toJS(res), true)
