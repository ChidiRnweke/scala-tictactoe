export declare enum Tile {
    X = "X",
    O = "O",
    Empty = " "
}
export declare enum RowPosition {
    Top = "Top",
    Middle = "Middle",
    Bottom = "Bottom"
}
export declare enum ColPosition {
    Left = "Left",
    Center = "Center",
    Right = "Right"
}
export declare enum Outcome {
    Ongoing = "Ongoing",
    Win = "Win",
    Draw = "Draw"
}
export interface Row {
    left: Tile;
    center: Tile;
    right: Tile;
}
export interface Board {
    bottomRow: Row;
    middleRow: Row;
    topRow: Row;
}
export interface Turn {
    currentPlayer: Tile.X | Tile.O;
    gameBoard: Board;
}
export interface MoveResponse {
    success: boolean;
    result: string | TurnInfo;
}
export interface TurnInfo {
    newTurn: Turn;
    outcome: Outcome;
}
