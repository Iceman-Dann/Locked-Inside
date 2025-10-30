// Main application entry point for the web version of Nightfall Manor
// This is a port from the Java ACM Graphics version to JavaScript/HTML5 Canvas

const canvas = document.getElementById('gameCanvas');
const ctx = canvas.getContext('2d');

// Game constants
const CANVAS_WIDTH = 800;
const CANVAS_HEIGHT = 640;

// Game state
let currentPane = null;
let keys = {};
let mouse = { x: 0, y: 0, pressed: false };

// Enums (equivalent to Java enums)
const ItemType = {
    WEAPON: 'weapon',
    KEY: 'key',
    HEALING: 'healing',
    GUN: 'gun'
};

const MapType = {
    LIVINGR: 'living room',
    BEDR: 'bed room',
    OFFICE: 'office'
};

const RoomType = {
    BEDROOMS: 'bedroom map',
    BEDROOML: 'bedroom left',
    BEDROOMR: 'bedroom right',
    OFFICE: 'office',
    OUT: 'Out of the house'
};

// Door class (port from Java)
class Door {
    constructor(x, y, width, height, locked, map) {
        this.doorBound = { x, y, width, height, visible: false };
        this.roomType = null;
        this.mapType = map;
        this.locked = locked;
    }

    setRoomType(roomType) { this.roomType = roomType; }
    unlock() { this.locked = false; }
    getRoomType() { return this.roomType; }
    getMapType() { return this.mapType; }
    isLocked() { return this.locked; }
    getRect() { return this.doorBound; }
}

// Entity class (port from Java)
class Entity {
    constructor(x, y) {
        this.sprite = { x, y, width: 32, height: 32, image: null }; // Placeholder for image
        this.x = x;
        this.y = y;
        this.visible = true;
    }

    getX() { return this.x; }
    getY() { return this.y; }
    setX(x) {
        this.x = x;
        this.sprite.x = x;
    }
    setY(y) {
        this.y = y;
        this.sprite.y = y;
    }
    getImage() { return this.sprite; }
    isVisible() { return this.visible; }
    getBounds() { return { x: this.x, y: this.y, width: this.sprite.width, height: this.sprite.height }; }
    setVisible(visible) { this.visible = visible; }
    loadImage(image) { this.sprite.image = image; }
}

// Player class (port from Java, extends Entity)
class Player extends Entity {
    constructor(x, y) {
        super(x, y);
        this.spriteUp = [];
        this.spriteDown = [];
        this.spriteLeft = [];
        this.spriteRight = [];
        this.dx = 0;
        this.dy = 0;
        this.stepsTaken = 0;
        this.HP = 3;
        this.upPressed = false;
        this.downPressed = false;
        this.leftPressed = false;
        this.rightPressed = false;
        this.inventory = new Inventory();
        this.playerDirection = null;
        this.addImages();
    }

    getHP() { return this.HP; }
    setHP(HP) { this.HP = HP; }
    getDX() { return this.dx; }
    getDY() { return this.dy; }
    setX(a) { this.x += a; }
    getDirection() { return this.playerDirection; }
    getInventory() { return this.inventory; }

    addImages() {
        // Placeholder for sprite arrays
        for (let i = 1; i <= 2; ++i) {
            this.spriteUp.push({ image: null });
            this.spriteDown.push({ image: null });
            this.spriteLeft.push({ image: null });
            this.spriteRight.push({ image: null });
        }
    }

    keyPressed(e) {
        const code = e.code;
        if (['KeyW', 'ArrowUp', 'KeyS', 'ArrowDown', 'KeyA', 'ArrowLeft', 'KeyD', 'ArrowRight'].includes(code)) {
            if (['KeyW', 'ArrowUp'].includes(code)) this.upPressed = true;
            if (['KeyS', 'ArrowDown'].includes(code)) this.downPressed = true;
            if (['KeyA', 'ArrowLeft'].includes(code)) this.leftPressed = true;
            if (['KeyD', 'ArrowRight'].includes(code)) this.rightPressed = true;

            if (this.upPressed) {
                if (this.leftPressed || this.rightPressed) this.dy = -2;
                else this.dy = -4;
                this.move(Player.dir.UP);
                this.playerDirection = "Up";
            }
            if (this.downPressed) {
                if (this.leftPressed || this.rightPressed) this.dy = 2;
                else this.dy = 4;
                this.move(Player.dir.DOWN);
                this.playerDirection = "Down";
            }
            if (this.leftPressed) {
                if (this.upPressed || this.downPressed) this.dx = -2;
                else this.dx = -4;
                this.move(Player.dir.LEFT);
                this.playerDirection = "Left";
            }
            if (this.rightPressed) {
                if (this.upPressed || this.downPressed) this.dx = 2;
                else this.dx = 4;
                this.move(Player.dir.RIGHT);
                this.playerDirection = "Right";
            }
            this.x = this.sprite.x;
            this.y = this.sprite.y;
        }
    }

    keyReleased(e) {
        const code = e.code;
        if (['KeyW', 'ArrowUp'].includes(code)) this.upPressed = false;
        if (['KeyS', 'ArrowDown'].includes(code)) this.downPressed = false;
        if (['KeyA', 'ArrowLeft'].includes(code)) this.leftPressed = false;
        if (['KeyD', 'ArrowRight'].includes(code)) this.rightPressed = false;

        if (this.upPressed) this.dy = -4;
        else if (this.downPressed) this.dy = 4;
        else this.dy = 0;

        if (this.leftPressed) this.dx = -4;
        else if (this.rightPressed) this.dx = 4;
        else this.dx = 0;
    }

    grabItem(item) {
        this.inventory.addItem(item);
    }

    move(direction) {
        let legTracker;
        if (this.stepsTaken == 8) this.stepsTaken = 0;
        if (this.stepsTaken < 4) legTracker = 0;
        else legTracker = 1;
        if (direction == Player.dir.UP) this.loadImage(this.spriteUp[legTracker].image);
        if (direction == Player.dir.DOWN) this.loadImage(this.spriteDown[legTracker].image);
        if (direction == Player.dir.LEFT) this.loadImage(this.spriteLeft[legTracker].image);
        if (direction == Player.dir.RIGHT) this.loadImage(this.spriteRight[legTracker].image);
        // Boundary checks would be added here
        this.sprite.x += this.dx;
        this.sprite.y += this.dy;
        this.x = this.sprite.x + this.dx;
        this.y = this.sprite.y + this.dy;
        this.stepsTaken++;
    }

    static dir = { UP: 0, DOWN: 1, LEFT: 2, RIGHT: 3 };
}

// Monster class (port from Java, extends Entity)
class Monster extends Entity {
    constructor(x, y, type) {
        super(x, y);
        this.spriteUp = [];
        this.spriteDown = [];
        this.spriteLeft = [];
        this.spriteRight = [];
        this.dx = 0;
        this.dy = 0;
        this.stepsTaken = 0;
        this.player = null;
        this.addImages();
        this.loadImage(this.spriteDown[0].image);
    }

    addImages() {
        for (let i = 1; i <= 3; ++i) {
            this.spriteUp.push({ image: null });
            this.spriteDown.push({ image: null });
            this.spriteLeft.push({ image: null });
            this.spriteRight.push({ image: null });
        }
    }

    move(direction) {
        let legTracker;
        if (this.stepsTaken == 8) this.stepsTaken = 0;
        if (this.stepsTaken < 4) legTracker = 0;
        else legTracker = 1;
        if (direction == Monster.dir.UP) {
            this.loadImage(this.spriteUp[legTracker].image);
            this.dx = 0; this.dy = -2;
        }
        if (direction == Monster.dir.DOWN) {
            this.loadImage(this.spriteDown[legTracker].image);
            this.dx = 0; this.dy = 2;
        }
        if (direction == Monster.dir.LEFT) {
            this.loadImage(this.spriteLeft[legTracker].image);
            this.dx = -2; this.dy = 0;
        }
        if (direction == Monster.dir.RIGHT) {
            this.loadImage(this.spriteRight[legTracker].image);
            this.dx = 2; this.dy = 0;
        }
        this.moveInternal(this.dx, this.dy);
        this.stepsTaken++;
    }

    touchPlayer() {
        if (!this.player) return false;
        const smallerBounds = {
            x: this.sprite.x + 8,
            y: this.sprite.y + 16,
            width: this.sprite.width - 16,
            height: this.sprite.height - 32
        };
        return this.player.getBounds().x < smallerBounds.x + smallerBounds.width &&
               this.player.getBounds().x + this.player.getBounds().width > smallerBounds.x &&
               this.player.getBounds().y < smallerBounds.y + smallerBounds.height &&
               this.player.getBounds().y + this.player.getBounds().height > smallerBounds.y;
    }

    moveInternal(x, y) {
        this.sprite.x += this.dx;
        this.sprite.y += this.dy;
        this.x = this.sprite.x + this.dx;
        this.y = this.sprite.y + this.dy + 16;
    }

    move(player) {
        this.player = player;
        if (player.getX() < this.x) this.move(Monster.dir.LEFT);
        if (player.getX() > this.x) this.move(Monster.dir.RIGHT);
        if (player.getY() < this.y) this.move(Monster.dir.UP);
        if (player.getY() > this.y) this.move(Monster.dir.DOWN);
    }

    static dir = { UP: 0, DOWN: 1, LEFT: 2, RIGHT: 3 };
}

// GButton class (port from Java)
class GButton {
    constructor(label, x, y, width, height, col = 'white') {
        this.rect = { x, y, width, height, fillColor: col, filled: false };
        this.message = { text: label, x: x + width / 2, y: y + height / 2 + 10, color: 'white' };
        this.sizeLabelFont();
    }

    sizeLabelFont() {
        // Simplified font sizing
        this.message.font = '16px Courier New';
    }

    setFillColor(col) { this.rect.fillColor = col; }
    setColor(col) { this.message.color = col; }

    render(ctx) {
        if (this.rect.filled) {
            ctx.fillStyle = this.rect.fillColor;
            ctx.fillRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
        } else {
            ctx.strokeStyle = this.rect.fillColor;
            ctx.strokeRect(this.rect.x, this.rect.y, this.rect.width, this.rect.height);
        }
        ctx.fillStyle = this.message.color;
        ctx.font = this.message.font;
        ctx.textAlign = 'center';
        ctx.fillText(this.message.text, this.message.x, this.message.y);
    }
}

// GParagraph class (port from Java)
class GParagraph {
    constructor(label, x, y) {
        this.startX = x;
        this.startY = y;
        this.allText = label;
        this.labels = [];
        this.labelHeight = 20;
        this.processLabels();
    }

    setColor(c) {
        this.labels.forEach(l => l.color = c);
    }

    setText(text) {
        this.allText = text;
        this.processLabels();
    }

    setFont(font) {
        this.labels.forEach(l => l.font = font);
        this.processLabels();
    }

    processLabels() {
        const textLines = this.allText.split('\n');
        this.setupLabels(textLines.length);
        this.assignLabels(textLines);
    }

    assignLabels(textLines) {
        for (let i = 0; i < this.labels.length; i++) {
            this.labels[i].text = i < textLines.length ? textLines[i] : '';
        }
    }

    setupLabels(numLabels) {
        this.labels = [];
        for (let i = 0; i < numLabels; i++) {
            this.labels.push({
                text: '',
                x: this.startX,
                y: this.startY + i * this.labelHeight,
                font: '16px Courier New',
                color: 'white'
            });
        }
    }

    render(ctx) {
        this.labels.forEach(label => {
            ctx.fillStyle = label.color;
            ctx.font = label.font;
            ctx.textAlign = 'left';
            ctx.fillText(label.text, label.x, label.y);
        });
    }
}

// GraphicsPane interface equivalent (abstract class)
class GraphicsPane {
    showContents() {}
    hideContents() {}
    mousePressed(e) {}
    keyPressed(e) {}
    keyReleased(e) {}
}

// Item class (port from Java)
class Item {
    constructor(name, sprite, type, map) {
        this.x = 0;
        this.y = 0;
        this.count = 1;
        this.width = 32;
        this.height = 32;
        this.name = name;
        this.desc = '';
        this.used = false;
        this.pickedUp = false;
        this.sprite = sprite;
        this.invSprite = null;
        this.type = type;
        this.roomType = null;
        this.mapType = map;
    }

    setRoomType(roomType) { this.roomType = roomType; }
    getRoomType() { return this.roomType; }
    getMapType() { return this.mapType; }
    getItemType() { return this.type; }
    getX() { return this.x; }
    getY() { return this.y; }
    getCount() { return this.count; }
    getName() { return this.name; }
    getImage() { return this.sprite; }
    setImage(image) { this.sprite = image; }
    getDescription() { return this.desc; }
    getBounds() { return { x: this.x, y: this.y, width: this.width, height: this.height }; }
    isUsed() { return this.used; }
    isPickedUp() { return this.pickedUp; }
    setX(x) { this.x = x; }
    setY(y) { this.y = y; }
    setCount(count) { this.count = count; }
    setName(name) { this.name = name; }
    setDescription(desc) { this.desc = desc; }
    setUsed(used) { this.used = used; }
    setPickedUp(pickedUp) { this.pickedUp = pickedUp; }
    getInvSprite() { return this.invSprite; }
    setInvSprite(invSprite) { this.invSprite = invSprite; }
}

// Inventory class (port from Java)
class Inventory {
    constructor() {
        this.invItems = [];
        this.active = false;
        this.INVENTORY_X = 200;
        this.INVENTORY_Y = 576;
        this.listSpacing = 0;
        this.selectedItem = null;
        this.redBox = { x: 0, y: 0, width: 32, height: 32, visible: false };
        this.description = { text: '', x: 200, y: 600, visible: false };
    }

    static INVENTORY_IMG = { x: 200, y: 576, width: 400, height: 64 }; // Placeholder

    numInvItems() { return this.invItems.length; }
    getListSpacing() { return this.listSpacing; }
    getSelectedItem() { return this.selectedItem; }
    setListSpacing(listSpacing) { this.listSpacing = listSpacing; }
    setSelectedItem(item) { this.selectedItem = item; }

    setSelectedItemByIndex(idx) {
        if (idx < 0 || idx >= this.numInvItems()) return false;
        this.setSelectedItem(this.invItems[idx]);
        return true;
    }

    setSelectedItem(obj) {
        for (let i = 0; i < this.numInvItems(); i++) {
            const detected = this.invItems[i];
            if (obj == detected.getInvSprite()) {
                this.setSelectedItem(detected);
                return true;
            }
        }
        return false;
    }

    drawSelectedItem(ctx) {
        this.setHighlightVisible(true);
        if (this.selectedItem) {
            if (this.selectedItem.invSprite) {
                this.redBox.x = this.selectedItem.invSprite.x;
                this.redBox.y = this.selectedItem.invSprite.y;
                ctx.strokeStyle = 'red';
                ctx.lineWidth = 2;
                ctx.strokeRect(this.redBox.x, this.redBox.y, this.redBox.width, this.redBox.height);
            }
            this.description.text = this.selectedItem.getDescription();
            ctx.fillStyle = 'white';
            ctx.font = '16px Courier New';
            ctx.fillText(this.description.text, this.description.x, this.description.y);
        }
    }

    setHighlightVisible(state) {
        this.redBox.visible = state;
        this.description.visible = state;
    }

    deleteItem(item) {
        const index = this.invItems.indexOf(item);
        if (index > -1) this.invItems.splice(index, 1);
        this.updateItemGui();
        this.setHighlightVisible(false);
    }

    addItem(item) {
        this.invItems.push(item);
        this.updateItemGui();
    }

    updateItemGui() {
        for (let i = 0; i < this.invItems.length; ++i) {
            if (this.invItems[i].invSprite) {
                this.invItems[i].invSprite.x = 200 + (32 * i);
                this.invItems[i].invSprite.y = this.INVENTORY_Y + 32;
            }
            this.invItems[i].setY(this.INVENTORY_Y);
        }
    }

    itemAt(i) { return this.invItems[i]; }
}

// Input handling
window.addEventListener('keydown', (e) => {
    keys[e.code] = true;
    if (currentPane && currentPane.keyPressed) currentPane.keyPressed(e);
});

window.addEventListener('keyup', (e) => {
    keys[e.code] = false;
    if (currentPane && currentPane.keyReleased) currentPane.keyReleased(e);
});

window.addEventListener('mousedown', (e) => {
    mouse.pressed = true;
    mouse.x = e.offsetX;
    mouse.y = e.offsetY;
    if (currentPane && currentPane.mousePressed) currentPane.mousePressed(e);
});

window.addEventListener('mouseup', (e) => {
    mouse.pressed = false;
});

window.addEventListener('mousemove', (e) => {
    mouse.x = e.offsetX;
    mouse.y = e.offsetY;
});

// Game loop
function gameLoop() {
    update();
    render();
    requestAnimationFrame(gameLoop);
}

function update() {
    if (currentPane && currentPane.update) {
        currentPane.update();
    }
}

function render() {
    ctx.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
    if (currentPane && currentPane.render) {
        currentPane.render(ctx);
    }
}

// MenuPane class (extends GraphicsPane)
class MenuPane extends GraphicsPane {
    constructor() {
        super();
        this.title = "Nightfall Manor";
        this.background = { image: null, x: 0, y: 0, width: 800, height: 640 }; // Placeholder
        this.newGame = new GButton("New Game", 800/2 - 200/2 - 7, 305, 200, 88);
        this.loadGame = new GButton("Load Game", 800/2 - 200/2 - 7, 350, 200, 88);
        this.options = new GButton("Options", 800/2 - 200/2 - 7, 395, 200, 88);
        this.credits = new GButton("Credits", 800/2 - 200/2 - 7, 440, 200, 88);
        this.exitGame = new GButton("Quit", 800/2 - 200/2 - 7, 485, 200, 88);
    }

    showContents() {
        // Load background, etc.
    }

    hideContents() {
        // Remove elements
    }

    mousePressed(e) {
        const obj = getElementAt(e.offsetX, e.offsetY);
        if (obj == this.newGame) currentPane = new NewGamePane();
        if (obj == this.loadGame) {} // TODO
        if (obj == this.options) {} // TODO
        if (obj == this.credits) {} // TODO
        if (obj == this.exitGame) {} // Exit
    }

    keyPressed(e) {
        // No keyboard navigation
    }

    render(ctx) {
        // Render background, title, and buttons
        ctx.fillStyle = 'black';
        ctx.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        ctx.fillStyle = 'white';
        ctx.font = '48px Courier New';
        ctx.textAlign = 'center';
        ctx.fillText(this.title, CANVAS_WIDTH / 2, 100);
        this.newGame.render(ctx);
        this.loadGame.render(ctx);
        this.options.render(ctx);
        this.credits.render(ctx);
        this.exitGame.render(ctx);
    }
}

// NewGamePane class (extends GraphicsPane)
class NewGamePane extends GraphicsPane {
    constructor() {
        super();
        this.background = { image: null, x: 0, y: 0, width: 800, height: 640 };
        this.playerX = 482;
        this.playerY = 510;
        this.walls = [];
        this.goal = null;
        this.setWalls();
    }

    showContents() {
        // Add walls, background, doors, player, monster, npc, inventory, items, gui
    }

    hideContents() {
        // Remove elements
    }

    mousePressed(e) {
        const obj = getElementAt(e.offsetX, e.offsetY);
        // Handle inventory selection, pause menu
    }

    keyPressed(e) {
        // Handle movement, collisions, doors, items
    }

    keyReleased(e) {}

    actionPerformed(e) {}

    setWalls() {
        // Define walls as in Java
        this.walls = [
            { x: 0, y: 0, width: 800, height: 64, filled: true },
            // Add all walls
        ];
    }

    render(ctx) {
        // Render background, walls, player, etc.
        ctx.fillStyle = '#2c2c2c';
        ctx.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        // Render walls, etc.
    }
}

// BedRoomGamePane class (extends GraphicsPane)
class BedRoomGamePane extends GraphicsPane {
    constructor() {
        super();
        this.background = { image: null, x: 10, y: -20, width: 800, height: 600 };
        this.playerX = 722;
        this.playerY = 474;
        this.walls = [];
        this.setWalls();
    }

    showContents() {
        // Add walls, background, doors, player, monster, npc, inventory, items, gui
    }

    hideContents() {
        // Remove elements
    }

    mousePressed(e) {
        const obj = getElementAt(e.offsetX, e.offsetY);
        // Handle inventory, choices
    }

    keyPressed(e) {
        // Handle movement, collisions, doors, items, npc interaction
    }

    keyReleased(e) {}

    actionPerformed(e) {}

    setWalls() {
        // Define walls
        this.walls = [
            { x: 0, y: 0, width: 800, height: 96, filled: true },
            // Add all walls
        ];
    }

    render(ctx) {
        // Render background, walls, player, inventory, etc.
        ctx.fillStyle = '#2c2c2c';
        ctx.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        // Render elements
    }
}

// OfficeGamePane class (extends GraphicsPane)
class OfficeGamePane extends GraphicsPane {
    constructor() {
        super();
        this.background = { image: null, x: 0, y: 0, width: 800, height: 640 };
        this.playerX = 50;
        this.playerY = 100;
        this.walls = [];
        this.setWalls();
    }

    showContents() {
        // Add background, walls, items, player, monster, inventory, gui
    }

    hideContents() {
        // Remove elements
    }

    mousePressed(e) {
        const obj = getElementAt(e.offsetX, e.offsetY);
        // Handle inventory selection, shooting
    }

    keyPressed(e) {
        // Handle movement, collisions, doors, items, shooting
    }

    keyReleased(e) {}

    actionPerformed(e) {}

    setWalls() {
        // Define walls as in Java
        this.walls = [
            { x: 0, y: 70, width: 800, height: 20, filled: true },
            { x: 0, y: 320, width: 800, height: 20, filled: true },
            { x: 0, y: 70, width: 20, height: 250, filled: true },
            { x: 700, y: 70, width: 20, height: 250, filled: true }
        ];
    }

    render(ctx) {
        // Render background, walls, player, inventory, etc.
        ctx.fillStyle = '#2c2c2c';
        ctx.fillRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        // Render walls
        ctx.fillStyle = 'gray';
        this.walls.forEach(wall => {
            if (wall.filled) ctx.fillRect(wall.x, wall.y, wall.width, wall.height);
        });
        // Render player, etc.
    }
}

// Helper function to get element at position (simplified)
function getElementAt(x, y) {
    // Placeholder: implement collision detection for buttons, items, etc.
    return null;
}

// Initialize the game when the page loads
function init() {
    currentPane = new MenuPane();
    gameLoop();
}

window.onload = init;

