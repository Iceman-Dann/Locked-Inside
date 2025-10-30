package GamePanes;
import Boilerplate.*;
import Item.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class NewGamePane extends GraphicsPane implements ActionListener {

	private MainApplication program; 
	private GImage background = new GImage("res/livingroom.png"); 
	private int playerX = 482, playerY = 510;
	private ArrayList <GRect> walls = new ArrayList <GRect>(); 
	private GLabel goal = null;	
	
	public NewGamePane(MainApplication app) {
		this.program = app;
		setWalls();
	}
		
	@Override
	public void showContents() {
		//1. walls
		for (int i=0; i<walls.size(); i++) {
			walls.get(i).setLocation(walls.get(i).getX(), walls.get(i).getY());
			walls.get(i).setSize(walls.get(i).getWidth(), walls.get(i).getHeight());
			program.add(walls.get(i));
		}
		//2. background image
		background.setLocation(0, 0);
		background.setSize(800, 640);
		program.add(background);
		//3. doors
		program.Doors(true,MapType.LIVINGR);
		//4. player and monster and npc and Goal Label
		if (program.fromBedtoLiving) {
program.addPlayer(85, 172);
			program.addMonster(35, 102);
			if(program.NPC.isDead() == false)   {program.addNPC(540, 450);}
			else  {walls.remove(walls.size() - 1);}
			program.fromBedtoLiving=false;
			program.monsterTimer.setInitialDelay(1000);
		} else {
			program.addPlayer(playerX, playerY);
			program.addMonster(playerX + 20, playerY + 50);
			program.addNPC(540, 450);
			program.monsterTimer.setInitialDelay(3000);
			goal = new GLabel("Find a way to get out of the house!", 450, 435);
			goal.setColor(Color.white);
			goal.setFont(program.customFont.deriveFont(9f));
			program.label5sec(goal);
			program.add(goal);
		}
		program.monsterTimer.start();
		program.player.getInventory();
		//5. Inventory hot bar image
		program.add(Inventory.INVENTORY_IMG, Inventory.INVENTORY_X, Inventory.INVENTORY_Y);
		//6. items on the map
		program.Items(true, MapType.LIVINGR);
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
		for(int i = 0; i < walls.size(); i++) {program.remove(walls.get(i));}
		//2. background
		program.remove(background);
		//3. doors
		program.Doors(false,MapType.LIVINGR);	
		//4. player and monster and npc
		program.remove(program.player.getImage());
		program.remove(program.monster.getImage());
		program.remove(program.NPC.getImage());
		program.monsterTimer.stop();
		//5. Inventory hot bar image
		program.remove(Inventory.INVENTORY_IMG);
		//6. items on the map
		program.Items(false, MapType.LIVINGR);
		//7. items on the inventory hot bar
		for (int i=0; i<program.player.getInventory().numInvItems();i++) {
			program.remove(program.player.getInventory().itemAt(i).getInvSprite());
		}
		//7.5 labels of wrongItem, lockedDoor, keyUsed
		program.removeLabels();
		program.remove(goal);
		//8. GUI
		program.removeGUI();		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		program.pauseMenu(obj);
		
		//click item in hot bar to select
		Inventory playerInv = program.player.getInventory();
		if(playerInv.setSelectedItem(obj)) {playerInv.drawSelectedItem();}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {
		program.player.keyPressed(e);
		
		program.checkCollision(walls);
		
		//unlock the bedroom map door with key
		program.unlockDoor(program.inBedMap,e);

		//get into the bedroom map
		program.openDoor(program.inBedMap,e);
		
		//get into the bathroom door
		program.openDoor(program.inBath, e, 672,250);
		
		//get out of bathroom
		program.openDoor(program.outBath,e,672,400);
		
		//unlocked the house door with key
		program.unlockDoor(program.winning, e);

		//get outta house
		program.openDoor(program.winning,e);
		
		//pick up item by E
		program.pickUpItem(e);
		
		//to select item with 12345 key
		program.setSelectedItem(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e) {program.player.keyReleased(e);}
	
	public void actionPerformed(ActionEvent e) {program.actionPerformed(e);}
	
	public void setWalls() {
		GRect wall1 = new GRect(0,0,800,64);
		GRect wall2 = new GRect(0,64,160,96);
		GRect wall3 = new GRect(0,160, 32,480);
		GRect wall4 = new GRect(32,224,128,128);
		GRect wall5 = new GRect(160,256,288,128);
		GRect wall6 = new GRect(32,512,448,128);
		GRect wall7 = new GRect(480,544,64,96);
		GRect wall8 = new GRect(544,512,256,128);
		GRect wall9 = new GRect(576,448,224,64);
		GRect wall10 = new GRect(768,64,32,384);
		GRect wall11 = new GRect(576,64,192,128);
		GRect wall12 = new GRect(576,192,64,96);
		GRect wall13 = new GRect(576,288,192,96);
		
		walls.add(wall1); walls.add(wall2); walls.add(wall3); walls.add(wall4); walls.add(wall5); walls.add(wall6); walls.add(wall7);
		walls.add(wall8); walls.add(wall9); walls.add(wall10); walls.add(wall11); walls.add(wall12); walls.add(wall13);
		
		for (int i = 0; i < 13; ++i)   {walls.get(i).setFilled(true);}
		
		GRect npc = new GRect(540, 450, 32, 32);
		npc.setVisible(false);
		walls.add(npc);
	}
}
