export { ColPosition, MoveResponse, Outcome, RowPosition, Tile, Turn, TurnInfo } from "./bridge";


export interface GameType {
    playerMove: (turn: Turn, player: Tile.X | Tile.O, targetRow: RowPosition, targetCol: ColPosition) => MoveResponse,
    startGame: Turn

}

export const Game: GameType;
