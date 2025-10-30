package Entity;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import Boilerplate.MainApplication;
import Item.*;
import acm.graphics.GImage;

public class Player extends Entity{
	public GImage sprite = getImage();
	private ArrayList<GImage> spriteUp, spriteDown, spriteLeft, spriteRight;
	private int x = 0, y = 0, dx = 0, dy = 0, stepsTaken = 0;
	private int HP=3;
	private boolean upPressed = false, downPressed = false, leftPressed = false, rightPressed = false;
	private Inventory inventory;
	private String playerDirection;
	public enum dir   {UP,DOWN,LEFT,RIGHT}
	MainApplication program;
	
	public Player(int x, int y, MainApplication program)   {
		super(x, y);
		inventory = new Inventory(program);
		addImages();
		this.program = program;
	}
	
	public int getHP()	{return HP;}
	
	public void setHP(int HP) {this.HP = HP;}
	
	public int getX()   {return x;}
	
	public int getY()   {return y;}
	
	public int getDX()   {return dx;}
	
	public int getDY()   {return dy;}
		
	public void setX(int a)   { x=x+a;}
	
	public String getDirection()   {return playerDirection;}
		
	public Inventory getInventory() {return inventory;}
	
	public void addImages() {
		spriteUp = new ArrayList<GImage>(2);
		spriteDown = new ArrayList<GImage>(2);
		spriteLeft = new ArrayList<GImage>(2);
		spriteRight = new ArrayList<GImage>(2);
			
		for (int i = 1; i <= 2; ++i)   {
			spriteUp.add(new GImage("res/player/PCU" + i + ".png"));
			spriteDown.add(new GImage("res/player/PCD" + i + ".png"));
			spriteLeft.add(new GImage("res/player/PCL" + i + ".png"));
			spriteRight.add(new GImage("res/player/PCR" + i + ".png"));
		}
	}
	
	private boolean keyUp(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_UP)   {return true;}
		else {return false;}	
	}
	
	private boolean keyDown(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_DOWN)   {return true;}
		else {return false;}	
	}
	
	private boolean keyLeft(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_A || e.getKeyCode() == KeyEvent.VK_LEFT)   {return true;}
		else {return false;}
	}
	
	private boolean keyRight(KeyEvent e)   {
		if (e.getKeyCode() == KeyEvent.VK_D || e.getKeyCode() == KeyEvent.VK_RIGHT)   {return true;}
		else {return false;}
	}
	
	public void keyPressed(KeyEvent e) {
		
		if (keyUp(e) || keyDown(e) || keyLeft(e) || keyRight(e))   {
			if (keyUp(e)) {upPressed = true;}
			if (keyDown(e)) {downPressed = true;}
			if (keyLeft(e)) {leftPressed = true;}
			if (keyRight(e)) {rightPressed = true;}

			if (upPressed) {
				if(leftPressed || rightPressed)   {dy = -2;}
				else {dy = -4;}
				move(Player.dir.UP);
				playerDirection = "Up";
			}
			if (downPressed) {
				if(leftPressed || rightPressed)   {dy = 2;}
				else   {dy = 4;}
				move(Player.dir.DOWN);
				playerDirection = "Down";
			}
			if (leftPressed) {
				if(upPressed || downPressed)   {dx = -2;}
				else   {dx = -4;}
				move(Player.dir.LEFT);
				playerDirection = "Left";
			}
			if (rightPressed) {
				if(upPressed || downPressed)   {dx = 2;}
				else   {dx = 4;}
				move(Player.dir.RIGHT);
				playerDirection = "Right";
			}
			x = (int) sprite.getX();
			y = (int) sprite.getY();
		}
	}
	
	public void keyReleased(KeyEvent e) {
		if (keyUp(e)) {upPressed = false;}
		if (keyDown(e)) {downPressed = false;}
		if (keyLeft(e)) {leftPressed = false;}
		if (keyRight(e)) {rightPressed = false;}
		
		if (upPressed)   {dy = -4;}
		else if (downPressed)   {dy = 4;}
		else   {dy = 0;}
		
		if (leftPressed)   {dx = -4;}
		else if (rightPressed)   {dx = 4;}
		else   {dx = 0;}
	}
	
	public void grabItem(Item item)   {
		getInventory().addItem(item);
	}
		
	private void move(dir direction)   {
		int legTracker;
		if (stepsTaken == 8)   {stepsTaken = 0;}
		if (stepsTaken < 4)   {legTracker = 0;}
		else   {legTracker = 1;}
		if(direction == dir.UP)   {loadImage(spriteUp.get(legTracker).getImage());}
		if(direction == dir.DOWN)   {loadImage(spriteDown.get(legTracker).getImage());}
		if(direction == dir.LEFT)   {loadImage(spriteLeft.get(legTracker).getImage());}
		if(direction == dir.RIGHT)   {loadImage(spriteRight.get(legTracker).getImage());}
		if(sprite.getBounds().getX() + dx < 0) {dx = 0;}
		else if(sprite.getBounds().getX() + sprite.getWidth() + dx > program.getWidth()) {dx = 0;}
		if(sprite.getBounds().getY() + dy < 0) {dy = 0;}
		else if(sprite.getBounds().getY() + sprite.getHeight() + dy > program.getHeight()) {dy = 0;}

		sprite.move(dx, dy);
		this.x = (int) (sprite.getX() + dx);
		this.y = (int) (sprite.getY() + dy);
		stepsTaken = stepsTaken + 1;
	}

	

}

