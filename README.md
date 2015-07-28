# Chess Replayer
Chess Replayer a Java SE program that replays a chess game. It takes an input file including the game recorded in a chess notation. Currently, only PGN (portable game notation) is supported.
A game can be replayed either manually by playing/taking back a single move, or automatically by playing moves one after another with an interval given by the turn time.

## Properties
Program properties are held in properties.xml file. These properties can be changed from settings menu. The properties include turn time, frame title, text displayed at the end of the game for 3 different cases (black wins, white wins, draw), and the paths of graphic files for various UI elements.

## User interface
The program has 5 main UI elements in 2 frames. Main frame includes a menu bar, a play panel that includes buttons to replay the game, a board panel including the chess board, and an info panel that shows game tags and move texts. The menu bar is at the top of the frame, the play panel is below that, the board panel is under the play panel to the left and the info panel is on the right of the board panel. The fifth UI element is settings frame, which is used to display/change properties.

## Modules
The program has a parser module, and a replayer module.

### Parser Module
Parser module is used to create a parse tree from the given input file, using a finite state machine. Then, a chess game and the necessary objects are created from the parse tree. Currently, the parser module includes the parser implementation for PGN. Parser implementations for other chess notations can be added by creating a parser class that implements the Parser interface for the notation.

### Replayer Module
Replayer module is used to replay a given game. It implements the MVC pattern. The model is implemented in model, move and piece packages; while view and control are implemented in view and control packages, respectively.
