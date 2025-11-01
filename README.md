# Locked Inside

## Table of Contents
1. [Overview](#overview)
2. [Features](#features)
3. [Folder Structure](#folder-structure)
4. [Requirements](#requirements)
5. [Quick Start](#quick-start)
6. [Installation & Setup](#installation--setup)
7. [Compilation](#compilation)
8. [Running the Game](#running-the-game)
9. [Web Version](#web-version)
10. [Gameplay](#gameplay)
11. [Controls](#controls)
12. [Development Notes](#development-notes)
13. [Credits](#credits)
14. [License](#license)

---

## Overview
**Locked Inside** is a 2D interactive horror game developed in Java using the ACM graphics library.
Players are trapped in a haunted mansion, exploring rooms, solving puzzles, collecting items, and interacting with NPCs while avoiding monsters. The game features multiple endings based on player choices and actions, creating a tense and immersive horror experience.

---

## Features
- **2D Top-Down Exploration:** Navigate through interconnected rooms, hallways, and hidden areas in a haunted mansion.
- **Inventory System:** Collect and manage items such as candles, keys, knives, guns, and more to solve puzzles and unlock doors.
- **Interactive NPCs:** Engage with non-player characters for hints, story progression, and quests.
- **Monsters and Combat:** Encounter various monsters with unique behaviors; use items like guns or knives to defend yourself.
- **Multiple Endings:** Achieve different outcomes (Good, Bad, True, or special endings) depending on your decisions, items collected, and interactions.
- **Sound & Music:** Atmospheric background music and sound effects enhance the horror ambiance.
- **Pause and Options Menus:** Control game state, audio settings, and access instructions.
- **Save/Load Functionality:** Save your progress and resume later.
- **Web Port:** A basic web version using HTML5 Canvas and JavaScript for broader accessibility.

---

## Folder Structure
```
Locked-Inside/
├── acm.jar                 # ACM Graphics Library (required for Java version)
├── bin/                    # Compiled Java classes (generated after compilation)
├── res/                    # Game assets (images, sprites, audio)
├── src/                    # Source code
│   ├── Boilerplate/        # Core utilities (MainApplication, GButton, etc.)
│   ├── Entity/             # Player and Monster classes
│   ├── Item/               # Item, Inventory, Door classes
│   └── GamePanes/          # Game screens (Menu, Rooms, Endings)
├── web/                    # Web version (HTML, JS, CSS)
├── README.md               # This file
├── TODO.md                 # Development tasks
└── implementation_plan.md  # Implementation details
```

---

## Requirements
- **Java JDK 17 or higher** (for compiling and running the Java version)
- **ACM Library** (`acm.jar` included in the project; provides graphics and utility classes)
- **Web Browser** (for the web version, supports modern browsers with Canvas API)
- Terminal or PowerShell on Windows, or Terminal on macOS/Linux

---

## Quick Start
1. **Clone the repository:**
   ```
   git clone https://github.com/Iceman-Dann/Locked-Inside.git
   cd Locked-Inside
   ```

2. **Compile and run (Windows):**
   ```
   javac -cp acm.jar -d bin -sourcepath src src\Boilerplate\MainApplication.java src\Boilerplate\GButton.java src\Boilerplate\GParagraph.java src\Entity\Entity.java src\Entity\Player.java src\Entity\Monster.java src\Item\Item.java src\Item\Inventory.java src\Item\Door.java src\GamePanes\MenuPane.java src\GamePanes\NewGamePane.java src\GamePanes\BedRoomGamePane.java src\GamePanes\OfficeGamePane.java src\GamePanes\BadEndPane.java src\GamePanes\Credits.java src\GamePanes\GoodEndPane.java src\GamePanes\OptionPane.java src\GamePanes\SavePane.java src\GamePanes\TrueEndPane.java
   java -cp "bin;acm.jar" Boilerplate.MainApplication
   ```

3. **Compile and run (Linux/macOS):**
   ```
   javac -cp acm.jar -d bin -sourcepath src src/Boilerplate/MainApplication.java src/Boilerplate/GButton.java src/Boilerplate/GParagraph.java src/Entity/Entity.java src/Entity/Player.java src\Entity\Monster.java src\Item\Item.java src\Item\Inventory.java src\Item\Door.java src\GamePanes/MenuPane.java src\GamePanes\NewGamePane.java src\GamePanes\BedRoomGamePane.java src\GamePanes\OfficeGamePane.java src\GamePanes\BadEndPane.java src\GamePanes\Credits.java src\GamePanes\GoodEndPane.java src\GamePanes\OptionPane.java src\GamePanes\SavePane.java src\GamePanes\TrueEndPane.java
   java -cp "bin:acm.jar" Boilerplate.MainApplication
   ```

4. **Web version:** Open `web/index.html` in your browser.

That's it! The game will launch with the main menu.

---

## Installation & Setup
1. Download or clone the repository from GitHub: `https://github.com/Iceman-Dann/Locked-Inside.git`.
2. Ensure all `res` folders and their contents remain in their original structure (assets are critical for the game).
3. Place `acm.jar` in the project root folder if not already present.
4. For the web version, no additional setup is needed beyond opening `web/index.html` in a browser.

---

## Compilation

Compile the Java source code from the project root folder. Open PowerShell (Windows) or Terminal (Mac/Linux) and run the appropriate command below. This generates class files in the `bin` directory.

### Windows
```
javac -cp acm.jar -d bin -sourcepath src src\Boilerplate\MainApplication.java src\Boilerplate\GButton.java src\Boilerplate\GParagraph.java src\Entity\Entity.java src\Entity\Player.java src\Entity\Monster.java src\Item\Item.java src\Item\Inventory.java src\Item\Door.java src\GamePanes\MenuPane.java src\GamePanes\NewGamePane.java src\GamePanes\BedRoomGamePane.java src\GamePanes\OfficeGamePane.java src\GamePanes\BadEndPane.java src\GamePanes\Credits.java src\GamePanes\GoodEndPane.java src\GamePanes\OptionPane.java src\GamePanes\SavePane.java src\GamePanes\TrueEndPane.java
```

### Linux/macOS
```
javac -cp acm.jar -d bin -sourcepath src src/Boilerplate/MainApplication.java src/Boilerplate/GButton.java src/Boilerplate/GParagraph.java src/Entity/Entity.java src\Entity/Player.java src\Entity\Monster.java src\Item\Item.java src\Item\Inventory.java src\Item\Door.java src\GamePanes/MenuPane.java src\GamePanes/NewGamePane.java src\GamePanes/BedRoomGamePane.java src\GamePanes/OfficeGamePane.java src\GamePanes/BadEndPane.java src\GamePanes\Credits.java src\GamePanes\GoodEndPane.java src\GamePanes\OptionPane.java src\GamePanes\SavePane.java src\GamePanes/TrueEndPane.java
```

**Note:** The main class is `Boilerplate.MainApplication`. Ensure all source files are listed to avoid compilation errors. If you encounter issues, verify that `acm.jar` is in the classpath and all dependencies are included.

---

## Running the Game

### Java Version
After compilation, run the game with the following commands. The game window will open, starting with the main menu.

#### Windows
```
java -cp "bin;acm.jar" Boilerplate.MainApplication
```

#### Linux/macOS
```
java -cp "bin:acm.jar" Boilerplate.MainApplication
```

**Troubleshooting:** If the game doesn't start, ensure the `bin` folder exists and contains compiled classes, and that `acm.jar` is accessible.

### Web Version
1. Open `web/index.html` in a modern web browser (e.g., Chrome, Firefox).
2. The game runs directly in the browser using HTML5 Canvas.
3. This version is a simplified port and may not include all features of the Java version.

---

## Gameplay
- **Exploration:** Move through the mansion's rooms, including the living room, bedrooms, office, and secret areas. Use doors to transition between rooms.
- **Puzzles and Items:** Collect items from the floor or by interacting with objects. Use keys to unlock doors, weapons to fight monsters, and other items for specific puzzles.
- **NPC Interactions:** Talk to NPCs for story clues, quests, or to progress the narrative. Some interactions may affect endings.
- **Monsters:** Avoid or confront monsters. Use weapons like the gun or knife to defend yourself. Monsters have different behaviors (e.g., patrolling, chasing).
- **Endings:** The game has multiple endings:
  - **Good Ending:** Escape safely with key items and minimal harm.
  - **Bad Ending:** Fail to escape or make poor choices.
  - **True Ending:** Discover secrets and achieve a special outcome.
  - Other variations based on actions (e.g., fighting monsters, helping NPCs).
- **Health and Inventory:** Monitor your health (displayed as HP). Manage inventory by picking up and using items.
- **Save/Load:** Use the save feature to preserve progress.

**Objective:** Escape the mansion by solving puzzles, collecting items, and making strategic decisions while surviving monster encounters.

---

## Controls

### Java Version
- **Movement:** Arrow keys or WASD to move the player.
- **Interact:** Mouse click to interact with items, doors, NPCs, or objects.
- **Inventory:** Click on inventory items to select and use them.
- **Pause Menu:** Click the pause button (top-right) or press P to pause/resume.
- **Audio Options:** Access via the options menu to toggle music/sound.
- **Save/Load:** Use the save pane to save progress.

### Web Version
- **Movement:** Arrow keys or WASD.
- **Interact:** Mouse click.
- **Other Controls:** Similar to Java version, adapted for browser input.

---

## Development Notes
- **ACM Graphics Library:** The game uses ACM classes like `GImage`, `GLabel`, `GRect`, `GraphicsProgram`, etc. All graphics are handled through this library.
- **Assets:** All images, sprites, and audio must remain in the `res` folder structure. Images are PNG format, audio is WAV.
- **Code Structure:** Modular design with separate packages for entities, items, game panes, and boilerplate utilities.
- **Web Port:** The `web` folder contains a JavaScript port using Canvas for basic gameplay. It's a prototype and lacks full features.
- **Known Issues/TODO:** Refer to `TODO.md` for pending tasks, such as wall padding adjustments, player positioning, and item placement.
- **Extensibility:** Add new rooms by creating subclasses of `GamePane`, new items via the `Item` class, and monsters via `Monster`.
- **Testing:** Run the game after compilation to test navigation, item pickup, combat, and endings. Use the pause menu for debugging.

---

## Credits
- **Developer:** Iceman-Dann (and team contributors).
- **Assets:** Custom sprites and audio created for the project.
- **Libraries:** ACM Java Graphics Library (Stanford University).
- **Inspiration:** Classic horror games like Resident Evil and point-and-click adventures.

---

## License
This project is developed for educational and non-commercial purposes. Redistribution, commercial use, or modification without permission is prohibited. All assets and code are original or licensed under compatible terms.

---

For issues, contributions, or questions, please open an issue on the GitHub repository: [https://github.com/Iceman-Dann/Locked-Inside](https://github.com/Iceman-Dann/Locked-Inside).
