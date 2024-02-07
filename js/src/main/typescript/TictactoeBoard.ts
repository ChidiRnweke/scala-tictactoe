import { Game, GameType } from "./game-logic.js";
import { ColPosition, MoveResponse, Outcome, RowPosition, Tile, Turn, TurnInfo } from "./bridge.js"

const html = /*html*/`
<style>
:host {
    margin: auto;
    background-color: var(--background-alt);
    color: var(--foreground-color);
    display: inline-grid;
}

p {
    text-align: center;
    color: var(--secondary-color)
}

a {
    text-align: center;
    display: inline;
    margin: 1rem;
    font-size: 1.2rem;
}

h1 {
    text-align: center;
    padding-top:  1rem;
    display:block;
    }

.status {
    text-align: center;
    margin: 1rem;
    font-size: 1.5rem;
    color: var(--foreground-color);
}

.status.error {
    color: var(--error-color);
}

button {
    background-color: var(--background-alt);
    border: 2px solid var(--accent-color);
    color: var(--foreground-color);
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.15); 
    font-size: clamp(0.5rem, 5vw, 5rem);
    width: clamp(1rem, 10vw, 8rem);
    height: clamp(1rem, 10vw, 8rem);
    cursor: pointer;
    transition: background-color 0.3s ease;
    border-radius: 5px;
    display: block;
    justify-self: center;

}

button:hover {
    background-color: var(--subtle-color);
    color: var(--background-color);
    transform: translateY(-3px); 
}

button:active {
    box-shadow: inset 0 0 10px rgba(0, 0, 0, 0.2);
}

.game-board {
    display: grid;
    grid-template-columns: repeat(3, 1fr);
    grid-template-rows: repeat(3, 1fr);
    justify-content: center;
    gap: 1rem;
    padding: 0.5rem 2rem;
    padding-bottom: 0rem;
    grid-template-areas: 
        "top-left top-center top-right"
        "middle-left middle-center middle-right"
        "bottom-left bottom-center bottom-right";
    margin: 1rem auto;
}

#tile-0-0 { grid-area: bottom-left; }
#tile-0-1 { grid-area: bottom-center; }
#tile-0-2 { grid-area: bottom-right; }
#tile-1-0 { grid-area: middle-left; }
#tile-1-1 { grid-area: middle-center; }
#tile-1-2 { grid-area: middle-right; }
#tile-2-0 { grid-area: top-left; }
#tile-2-1 { grid-area: top-center; }
#tile-2-2 { grid-area: top-right; }

</style>
<h1 class="status"></h1>
<p><strong>Tictactoe, brought to you by scala.js</strong></p>
<div class="game-board"></div>
<a href="https://github.com/ChidiRnweke/scala-tictactoe">source</a>
`

function numToCol(position: number): ColPosition {
    if (position == 0) {
        return ColPosition.Left
    }
    else if (position == 1) {
        return ColPosition.Center
    }
    else {
        return ColPosition.Right
    }
}

function numToRow(position: number): RowPosition {
    if (position == 0) {
        return RowPosition.Bottom
    }
    else if (position == 1) {
        return RowPosition.Middle
    }
    else {
        return RowPosition.Top
    }
}

function tileRepr(tile: Tile): string {
    switch (tile) {
        case Tile.X: return "λ";
        case Tile.O: return "Φ";
        case Tile.Empty: return "";
    }
}


class TictactoeGame extends HTMLElement {
    game: GameType;
    turn: Turn;

    public constructor() {
        super()
        this.attachShadow({ mode: 'open' });
        this.game = Game;
        this.turn = this.game.startGame;
        this.initialize();
    }

    private playerMove(row: RowPosition, col: ColPosition) {
        const moveAttempt = this.game.playerMove(this.turn, this.turn.currentPlayer, row, col);
        console.log(row, col);
        if (moveAttempt.success) {
            this.handleSuccessfulMove(moveAttempt);
        } else {
            const errorMsg = moveAttempt.result as string
            this.renderStatus(errorMsg, true);
        }
    }

    private handleSuccessfulMove(moveAttempt: MoveResponse): void {
        const move = moveAttempt.result as TurnInfo;
        this.turn = move.newTurn;
        this.renderBoard();
        const title = this.shadowRoot!.querySelector('h1')
        switch (move.outcome) {
            case Outcome.Ongoing:
                break;
            case Outcome.Win:
                this.renderStatus(`Well done player ${this.turn.currentPlayer}! You win. Press to restart.`, false);
                setTimeout(() => this!.addEventListener('click', () => { this.restartGame() }, { once: true }), 100);
                break;
            case Outcome.Draw:
                this.renderStatus("Well played, it's a draw. Press to restart.", false);
                setTimeout(() => this!.addEventListener('click', () => { this.restartGame() }, { once: true }), 100);
                break;
        }
    }

    private restartGame() {
        this.turn = Game.startGame;
        console.log(this.turn);
        this.renderBoard();
    }

    private initialize() {
        this.shadowRoot!.innerHTML = html
        this.addButtons();
        this.renderBoard();
    }

    private addButtons() {
        const boardContainer = this.shadowRoot!.querySelector('.game-board');
        for (let i = 0; i < 3; i++) {
            for (let j = 0; j < 3; j++) {
                let tile = document.createElement('button');
                const row = numToRow(2 - i);
                const col = numToCol(j);
                tile.id = `tile-${2 - i}-${j}`;
                tile.addEventListener('click', () => { console.log(row, col); console.log(2 - i, j); this.playerMove(row, col) });
                boardContainer!.appendChild(tile);
            }
        }
    }

    private renderBoard() {
        this.renderStatus(`Player ${this.turn.currentPlayer}'s turn.`, false);
        const board = this.turn.gameBoard;
        const rows = [board.bottomRow, board.middleRow, board.topRow]
        rows.forEach((row, rowNum) => {
            const cols = [row.left, row.center, row.right]
            cols.forEach((tile, colNum) => {
                const btn = this.shadowRoot!.querySelector(`#tile-${rowNum}-${colNum}`)
                btn!.textContent = tileRepr(tile);
            })
        })
    }

    private renderStatus(message: string, error: boolean): void {
        const status = this.shadowRoot?.querySelector<HTMLParagraphElement>('.status');
        if (error) {
            status!.classList.add("error");
        } else {
            status!.classList.remove("error");
        }
        status!.innerText = message
    }
}

window.customElements.define("tictactoe-game", TictactoeGame)