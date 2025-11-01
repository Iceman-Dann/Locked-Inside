package GamePanes;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import Item.*;
import Boilerplate.*;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.graphics.GRectangle;
import acm.graphics.GLabel;

public class OfficeGamePane extends GraphicsPane implements ActionListener {
	private MainApplication program;
	private GRect backgroundBlack;
	private GImage officeMap;
	private int playerX = 50, playerY = 100;
	private ArrayList <GRect> walls = new ArrayList <GRect>();

	public OfficeGamePane(MainApplication app) {
		this.program = app;
		backgroundBlack = new GRect(0, 0, 1500, 1500);
		backgroundBlack.setFilled(true);
		backgroundBlack.setFillColor(java.awt.Color.BLACK);
		backgroundBlack.setLineWidth(0); // Make border fully invisible
		officeMap = new GImage("res/room2.png");
		officeMap.setSize(777, (int)(800 / 3.5)); // Width 777px (3 left, 20 right), height 1/3.5 of width
		setWalls();
	}

	@Override
	public void showContents() {
		//1. background image
		program.add(backgroundBlack);
		program.add(officeMap, 33, 68); // Moved down 20px and right 10px from previous (23+10=33, 48+20=68)
		//2. walls
		for (int i=0; i<walls.size(); i++) {program.add(walls.get(i));}
		//3. items on the map
		program.Items(true, MapType.OFFICE);
		//4. doors (none in Office)
		//5. player and monster
		program.addPlayer(playerX, playerY);
		program.player.getInventory();
		program.addMonster(playerX + 20, playerY + 50);
		program.monsterTimer.setInitialDelay(3000);
		program.monsterTimer.start();
		//6. inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//7. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems(); i++) {
			program.add(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//8. GUI
		program.addGUI();
	}

	@Override
	public void hideContents() {
		//1. walls
		for(int i=0; i<walls.size(); i++) {program.remove(walls.get(i));}
		//2. background image
		program.remove(backgroundBlack);
		program.remove(officeMap);
		//3. doors
		//4. player and monster
		program.remove(program.player.getImage());
		program.remove(program.monster.getImage());
		program.monsterTimer.stop();
		//5. inventory hot bar image
		program.remove(Inventory.INVENTORY_IMG);
		//6. items on the map
		program.Items(false, MapType.OFFICE);
		//7. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//7.5 labels of wrongItem, lockedDoor, keyUsed
		program.removeLabels();
		//9. GUI
		program.removeGUI();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		program.pauseMenu(obj);

		//to select item on the inventory hot bar
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {playerInv.drawSelectedItem();}

		//shoot bullet if gun is selected
		if(program.getSelectedItem() != null && program.getSelectedItem().getItemType() == ItemType.GUN) {
			shootBullet(e.getX(), e.getY());
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);

		program.checkCollision(walls);

		//pick up item with E
		program.pickUpItem(e);

		//to select item with 12345 key
		program.setSelectedItem(e);

		//shoot monster with gun
		if(program.getSelectedItem() != null && program.getSelectedItem().getItemType() == ItemType.GUN && program.player.sprite.getBounds().intersects(program.monster.getImage().getBounds()) && e.getKeyCode()==KeyEvent.VK_E) {
			program.monsterTimer.stop();
			program.switchToTrueEnd();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}

	@Override
	public void actionPerformed(ActionEvent e) {program.actionPerformed(e);}

	public void setWalls() {
		// Invisible walls for collision detection only
		GRect topWall = new GRect(0, 70, 800, 20);
		GRect bottomWall = new GRect(0, 320, 800, 20);
		GRect leftWall = new GRect(0, 70, 20, 250);
		GRect rightWall = new GRect(700, 70, 20, 250);

		walls.add(topWall); walls.add(bottomWall); walls.add(leftWall); walls.add(rightWall);

		// Walls are not filled, so they are invisible
		for (int i = 0; i < 4; ++i)   {walls.get(i).setFilled(false);}
	}

	public void shootBullet(int targetX, int targetY) {
		// Create a bullet at player position
		GRect bullet = new GRect(program.player.getX() + 10, program.player.getY() + 10, 5, 5);
		bullet.setFilled(true);
		bullet.setFillColor(java.awt.Color.YELLOW);
		program.add(bullet);

		// Calculate direction
		final double dx = targetX - (program.player.getX() + 10);
		final double dy = targetY - (program.player.getY() + 10);
		final double distance = Math.sqrt(dx * dx + dy * dy);
		final double normalizedDx = distance > 0 ? dx / distance : 0;
		final double normalizedDy = distance > 0 ? dy / distance : 0;

		// Move bullet in a loop until it hits something or goes off screen
		javax.swing.Timer bulletTimer = new javax.swing.Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				bullet.move(normalizedDx * 5, normalizedDy * 5);
				// Check if bullet hits monster
				if (bullet.getBounds().intersects(program.monster.getImage().getBounds())) {
					program.remove(bullet);
					((javax.swing.Timer)e.getSource()).stop();
					program.monsterTimer.stop();
					program.switchToTrueEnd();
				}
				// Remove bullet if it goes off screen
				else if (bullet.getX() < 0 || bullet.getX() > 800 || bullet.getY() < 0 || bullet.getY() > 640) {
					program.remove(bullet);
					((javax.swing.Timer)e.getSource()).stop();
				}
			}
		});
		bulletTimer.start();
	}
}
