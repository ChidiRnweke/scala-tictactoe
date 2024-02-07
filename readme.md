# TicTacToe web Component

Introducing the TicTacToe game component: a unique blend of TypeScript and Scala.js. This custom web component encapsulates the core game logic, implemented in Scala.js. This core interfaces web components written interface for seamless integration into any web project. By including a single bundled file, you can embed this classic game into your site.

## How to Use

Incorporating the TicTacToe game into your webpage is straightforward. Follow these two simple steps:

## Installation

To integrate the TicTacToe game into your project simply add the jsDeliver link:

```html
<script src=https://cdn.jsdelivr.net/npm/scala-tictactoe></script>
```

### Step 2: Add the Game Element

Place the TicTacToe game element in your HTML at the desired location:

```html
<tictactoe-game></tictactoe-game>
``` 
and you're good to go!

## Customization
The game's appearance can be tailored to fit your site's aesthetics through CSS custom variables. Below are the variables you can override:

* --background-alt: Background color of the game element.
* --foreground-color: Color of the text and symbols (X and O).
* --secondary-color: Color used for secondary text, such as instructions or information.
* --accent-color: Color for borders and highlights.
* --error-color: Color used to indicate errors or invalid moves.
* --subtle-color: Hover background color for buttons.
* --background-color: Button active state background color.

Customize these properties within your CSS to match your design scheme.

This project makes heavy use of the shadow DOM so the things above are the only way to style the *inside* of the component. You can style the outside out the component as normal.

## Features
Custom Web Component: Simplifies integration into any webpage.
Fully Customizable: Styled through CSS variables for easy theming.


## License
This project is licensed under the MIT License. Feel free to use, modify, and distribute it in accordance with the license terms.