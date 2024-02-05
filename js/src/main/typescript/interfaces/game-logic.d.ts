import { ColPosition, MoveResponse, RowPosition, Tile, Turn } from "./bridge";

export declare class Game {
    playerMove(turn: Turn,
        player: Tile.X | Tile.O,
        targetRow: RowPosition,
        targetCol: ColPosition): MoveResponse
    startGame: Turn
}