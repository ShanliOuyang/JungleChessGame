# 🐘 JungleChessGame (斗兽棋)

[![Java](https://img.shields.io/badge/Java-8%2B-orange)](https://www.java.com/)
[![Swing](https://img.shields.io/badge/GUI-Swing-blue)](https://docs.oracle.com/javase/tutorial/uiswing/)

A classic **Jungle Chess** (Dou Shou Qi / 斗兽棋) board game implemented in Java Swing. This project was created as a freshman-year course project, featuring a full graphical user interface, two game modes, save/load functionality, game replay, and more.

---

## 🎮 Game Overview

**Jungle Chess** (also known as *Dou Shou Qi* or *Animal Chess*) is a traditional two-player Chinese board game played on a **9×7** grid. Each player controls **8 animal pieces** of different ranks, aiming to move a piece into the opponent's den or capture all opponent pieces.

### Animal Hierarchy (Rank)

| Rank | Animal   |
|------|----------|
| 8    | Elephant |
| 7    | Lion     |
| 6    | Tiger    |
| 5    | Leopard  |
| 4    | Wolf     |
| 3    | Dog      |
| 2    | Cat      |
| 1    | Rat      |

---

## ✨ Features

### Core Gameplay
- **Two Game Modes**: Player vs. Player (PvP) and Player vs. Computer (PvE / AI mode)
- **Full Rule Implementation**: Complete jungle chess rules including animal hierarchy, special interactions, traps, river mechanics, and den capture
- **Move Highlighting**: Valid destination cells are automatically highlighted when a piece is selected
- **Turn Indicator**: Displays whose turn it is (Blue or Red)
- **Round Counter**: Tracks the number of turns played
- **Score Tracking**: Records the number of wins for both Blue and Red sides

### Game Rules Implemented
- **Rank-based Capture**: A piece can capture an opponent's piece of equal or lower rank
- **Special Counter**: The **Rat** (rank 1) can defeat the **Elephant** (rank 8), and the Elephant **cannot** capture the Rat
- **Trap Mechanic**: Any piece standing on a trap tile can be captured by **any** opponent piece, regardless of rank
- **River Mechanic**:
  - Only the **Rat** can enter water tiles
  - The **Lion** and **Tiger** can jump across rivers horizontally or vertically (landing on the opposite bank)
  - Rat in the river blocks Lion/Tiger from jumping over it
- **Den Protection**: A player cannot move their own piece into their own den
- **Win Condition**: Capture the opponent's den **or** eliminate all 8 opponent pieces

### Board Features
- **Special Terrain Types**: Water (river), Grass (land), Traps, and Dens (caves)
- **Trap Detection**: Pieces automatically get trap status when standing on trap tiles

### User Interface
- **Login System**: Enter a player ID to create a personalized save folder
- **Save/Load Game**: Save the current board state to a file and load it later, including piece positions, current turn, and captured pieces
- **Undo (Regret)**: Undo the last move (supports multiple undo steps)
- **Game Replay**: Automatically replay all moves from the start at 500ms intervals
- **Reset Game**: Start a new round at any time
- **Exit Game**: Quit the current game session

### Themes & Sound
- **Two Visual Themes**: Cool (blue-tone) and Hot (red-tone) with matching backgrounds and button styles
- **Background Music**: Two background music tracks corresponding to each theme, playing in continuous loop
- **Sound Effects**: Distinct audio feedback for picking up a piece, placing a piece, capturing an opponent, and winning
- **Music Toggle**: Mute/unmute background music with a single button click

### Technical Features
- **exe4j Support**: Project configured for Windows `.exe` packaging
- **Input Validation**: Sanity checks during file load (duplicate pieces, illegal pieces, illegal water placement)

---

## 🏗️ Architecture

The project follows the **MVC (Model-View-Controller)** design pattern:

```
src/
├── Main.java                          # Program entry point
├── controller/
│   └── GameController.java            # Core game logic controller
├── listener/
│   └── GameListener.java              # Interface for click events
├── model/
│   ├── Cell.java                      # Single board cell container
│   ├── ChessPiece.java                # Piece with rank, owner, capture logic
│   ├── Chessboard.java                # 9×7 board with move/capture validation
│   ├── ChessboardPoint.java           # Immutable (row, col) coordinate
│   ├── Constant.java                  # Board dimension constants
│   ├── PlayerColor.java               # Player color enum (BLUE/RED)
│   └── Step.java                      # Move record for undo and replay
└── view/
    ├── CellComponent.java             # GUI component for each cell
    ├── ChessGameFrame.java            # Main game window (buttons, labels, theme)
    ├── ChessboardComponent.java       # Board rendering component
    ├── GridTypes.java                 # Terrain type enum (WATER/GRASS/TRAP/CAVE)
    ├── LoadFrame.java                 # Load game dialog
    ├── LoginFrame.java                # Login/registration window
    ├── SaveFrame.java                 # Save game dialog
    └── animals/                       # Animal piece GUI components
        ├── AnimalChessComponent.java  # Base animal component
        ├── ElephantChessComponent.java
        ├── LionChessComponent.java
        ├── TigerChessComponent.java
        ├── LeopardChessComponent.java
        ├── WolfChessComponent.java
        ├── DogChessComponent.java
        ├── CatChessComponent.java
        └── RatChessComponent.java
```

### Model Layer
- **`Chessboard`**: The core data structure — a `Cell[9][7]` 2D array representing the board. Contains all move validation logic including river jump rules for Lion/Tiger, Rat-specific water entry, and den access restrictions.
- **`ChessPiece`**: Represents a single animal piece with `owner` (Blue/Red), `name` (animal type), `rank` (1-8 power level), `trap` status, and `inriver` status. Contains the `canCapture()` method implementing all combat rules.
- **`Step`**: Immutable record of a single move (source, destination, player color, captured piece if any). Used for undo and replay features.
- **`ChessboardPoint`**: An immutable `(row, col)` coordinate pair with proper `equals()` and `hashCode()` implementations.

### Controller Layer
- **`GameController`**: The central controller connecting model and view. Handles:
  - Player click events (selecting pieces, choosing destinations)
  - Move execution and capture execution
  - Win condition checking
  - AI move generation and execution
  - Save/Load file I/O operations
  - Undo mechanism via step replay
  - Game replay playback
  - Sound effect triggering
  - Trap status updates after each move

### View Layer
- **`ChessGameFrame`**: The main `JFrame` window housing the board, score labels, control buttons, theme switching, and background music management.
- **`ChessboardComponent`**: Renders the 9×7 grid with terrain types and pieces.
- **`AnimalChessComponent`** and subclasses: Each animal has its own GUI component displaying animal-specific artwork.
- **`LoginFrame`**: Entry screen with ID input and game mode selection (Normal / AI).
- **`LoadFrame` / `SaveFrame`**: Dialogs for loading and saving game states.

---

## 🧠 AI Algorithm

The computer opponent uses a **simple random selection algorithm**:

1. Collect all pieces belonging to the AI that have at least one legal move
2. Randomly select one piece from this collection
3. Randomly select one legal destination from that piece's valid moves
4. Execute the move (or capture if the destination contains an opponent piece)

The AI operates on a separate thread with a 100ms delay to make the move visible to the human player.

---

## 🔑 Key Algorithms

### Move Validation (`Chessboard.isValidMove`)
The move validation implements the complete jungle chess rule set:
- **General movement**: A piece can move to any adjacent cell (Manhattan distance = 1)
- **Water restriction**: Non-Rat pieces cannot enter river cells
- **Rat water ability**: Rat can enter and move through water cells
- **Lion/Tiger river jump**: Can jump across rivers (4 cells forward or 3 cells sideways) if no Rat blocks the path on intermediate water cells
- **Den restriction**: A player cannot move their own piece into their own den
- **Capture integration**: If the destination has an opponent's piece at distance 1, validates capture separately

### Capture Validation (`ChessPiece.canCapture`)
- **Trap rule**: If the target is on a trap (`trap == 1`), any piece can capture it
- **Rat vs Elephant**: Rat (rank 1) can capture Elephant (rank 8), but Elephant cannot capture Rat
- **Rank comparison**: A piece can capture any opponent piece with equal or lower rank
- **Water restriction**: A piece in water cannot capture

### Pathfinding for Lion/Tiger Jump
When a Lion or Tiger attempts a river jump:
- **Vertical jump** (from row 2 to row 6, or row 6 to row 2): Checks 3 intermediate water cells for blocking Rats
- **Horizontal jump** (from col 0→3, 3→0, 3→6, 6→3 along rows 3-5): Checks 2 intermediate water cells for blocking Rats
- Jump is blocked if any Rat occupies intermediate water cells

### Undo Algorithm
The undo feature:
1. Removes the last step from the step history
2. Resets the board to initial state
3. Replays all remaining steps sequentially (both moves and captures)
4. Restores the correct turn, score, and board state

### Game Replay Algorithm
The replay feature:
1. Resets the board
2. Uses a background thread (`Thread.sleep(500)`) to replay each step at 500ms intervals
3. Updates the board GUI after each step for smooth animation

### Save/Load System
The save file format (`.txt`):
- **Lines 1-9**: Board state with `rank + color code` per cell (`1a` = Blue Rat, `8b` = Red Elephant, `-` = empty)
- **Line 10**: Current player (`1` = Blue, `2` = Red)
- **Line 11**: Blue's captured pieces (rank codes)
- **Line 12**: Red's captured pieces (rank codes)

**Validation during load**: Checks for duplicate pieces, invalid piece codes, and illegal water placement (non-Rat pieces in river).

---

## 🚀 Getting Started

### Prerequisites
- **Java JDK 8** or higher
- Any Java IDE (IntelliJ IDEA recommended) or command-line tools

### Running the Game

#### Option 1: Using an IDE (Recommended)
1. Clone or download this repository
2. Open the project in IntelliJ IDEA (or your preferred IDE)
3. Ensure all resource paths point to the correct `Related Photos/` and `Related BGM/` directories
4. Run `src/Main.java`

#### Option 2: Command Line
```bash
# Compile
javac -d out src/**/*.java

# Run
java -cp out Main
```

#### Option 3: exe4j (Windows)
The project includes a `JungleGame.exe4j` configuration file for building a standalone Windows `.exe` using the exe4j tool.

### Resource Paths
The game expects the following directory structure relative to the project root:
```
Jungle/
├── Related BGM/       # Audio files (.wav)
├── Related Load/      # Save files (auto-created per user ID)
└── Related Photos/    # Image assets
```

> **Note**: Resource paths use `..\\Jungle\\` prefix, which expects the project folder to be named `Jungle`. Adjust paths if your folder name differs.

---

## 📁 Project Structure

```
JungleChessGame/
├── README.md
├── Jungle.iml                          # IntelliJ module file
├── JungleGame.exe4j                    # exe4j packaging config
├── META-INF/
│   └── MANIFEST.MF                     # JAR manifest (Main class)
├── Related BGM/                        # Sound effects & background music
│   ├── BGMCOOL.wav / BGMHOT.wav        # Theme background music
│   ├── EAT.wav / GET.wav / PUT.wav     # Action sound effects
│   └── WIN.wav                         # Victory fanfare
├── Related Load/                       # Save game data
│   └── <player_id>/                    # Per-player save directories
│       └── <slot>.txt                  # Save slot files
├── Related Photos/                     # Image assets
│   ├── ANIMAL/ / ANIMALBLUE/ / ANIMALRED/  # Piece images
│   ├── BACKPIC/                        # Background images
│   ├── BUTTONPIC/                      # Button images
│   └── CELLPIC/                        # Cell terrain images
├── src/                                # Source code
│   ├── Main.java
│   ├── controller/
│   ├── listener/
│   ├── model/
│   └── view/
└── out/                                # Compiled .class files
```

---

## 🎯 Game Rules Summary

| Rule | Description |
|------|-------------|
| **Board** | 9 rows × 7 columns |
| **Pieces per player** | 8 (Elephant, Lion, Tiger, Leopard, Wolf, Dog, Cat, Rat) |
| **Movement** | One step to adjacent cell (orthogonal only) |
| **Capture** | Higher or equal rank captures lower or equal rank |
| **Rat vs Elephant** | Rat can capture Elephant; Elephant **cannot** capture Rat |
| **Traps** | Pieces on trap tiles can be captured by any opposing piece |
| **River** | Only Rat can enter water; Lion/Tiger can jump across if unobstructed |
| **Den** | Pieces cannot enter their own den; entering opponent's den wins |
| **Victory** | Enter opponent's den **or** capture all 8 opponent pieces |

---

## 📝 Development Notes

This project was developed as a **freshman-year course project** and represents an early exploration of:
- Object-oriented programming in Java
- MVC architecture pattern
- Java Swing GUI development
- Event-driven programming (mouse listeners)
- File I/O for game state persistence
- Multithreading (AI thread, replay thread)
- Audio playback with Java Sound API
- Enum-based terrain and constant management

---

## 📄 License

This project is available for educational purposes. Feel free to use, modify, and learn from it.

---

## 🙏 Acknowledgments

- Inspired by the traditional Chinese board game **斗兽棋** (Jungle Chess / Dou Shou Qi)
- Built with Java Swing and the Java Sound API
