package Boilerplate;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Timer;
import Entity.*;
import Item.*;
import GamePanes.*;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;

public class MainApplication extends GraphicsProgram {
	
	public static final int WINDOW_WIDTH = 800;
	public static final int WINDOW_HEIGHT = 640;
	public static final String MUSIC_FOLDER = "sounds";

	public double scaledX(double x) {
		return x * getWidth() / WINDOW_WIDTH;
	}

	public double scaledY(double y) {
		return y * getHeight() / WINDOW_HEIGHT;
	}

	public double scaledW(double w) {
		return w * getWidth() / WINDOW_WIDTH;
	}

	public double scaledH(double h) {
		return h * getHeight() / WINDOW_HEIGHT;
	}

	private GraphicsPane curScreen;
	private MenuPane menu;
	private NewGamePane newGame;
	private OptionPane options;
	private Credits credits;
	private BedRoomGamePane bedroom;
	private OfficeGamePane office;
	private GoodEndPane goodEnd;
	private BadEndPane badEnd;
	private TrueEndPane trueEnd;
	
	public Player player;
	public Monster monster;
	public NPC NPC;
	public Timer monsterTimer;
	public boolean fromBedtoLiving = false;
	private ArrayList<Item> items = new ArrayList <Item>();
	public static MusicBox music = new MusicBox();
	
	//pause and resume
	private GImage pauseImg;
	public GButton pauseButton;
	public boolean paused = false;
	public GButton resume = new GButton("", 296, 180, 208, 95);
	public GButton quit = new GButton("", 296, 420, 208, 95);
	private GImage quitImg;
	private GImage resumeImg;
	public GLabel lockedDoor = null, wrongItem = null, keyUsed = null;
	
	//player health gui
	private GParagraph healthPoints; 
	private static final int heartRootX = 75, heartRootY = 610, heartWidth = 30;
	ArrayList <GImage> playerHearts = new ArrayList <GImage>(); 	
	
	//doors
	public ArrayList <Door> doors = new ArrayList <Door>();
	//doors in bedroom map
	public Door inLivingMap, inLeftBed, inRightBed, outLeftBed, outRightBed, inRoom2, leftToRoom2, outOffice, secretDoor;
	//doors in living room map
	public Door inBedMap, inBath, outBath, winning;
	
	//item in livingroom
	public Item itemKnife;
	
	public Font customFont;
	//---------------------------------------------------------------------------
	
	/* GUI
	 * -----------------------------
	 * setGUI()
	 * addGUI()
	 * removeGUI()
	 * updatePlayerHeartsGUI(int hp)
	 */
	public void setGUI() {
		pauseImg = new GImage("res/texture/pause.png", 768, 0);
		pauseImg.setSize(32, 32);
		pauseImg.setVisible(true);
		pauseButton = new GButton("", 768, 0, 32, 32);
		try {
			resumeImg = new GImage("res/texture/Resume.png",296,180);
		} catch (Exception e) {
			resumeImg = null;
		}
		try {
			quitImg = new GImage("res/texture/Quit.png",296, 420);
		} catch (Exception e) {
			quitImg = null;
		}
		healthPoints = new GParagraph("HP:", 45, 625);
		healthPoints.setColor(Color.white);
		healthPoints.setFont(this.customFont);
	}
	public void addGUI() {
		this.add(pauseImg);
		this.add(pauseButton);
		this.add(healthPoints);
		updatePlayerHeartsGUI(this.player.getHP());
	}
	public void removeGUI() {
		this.remove(pauseImg);
		this.remove(pauseButton);
		this.remove(healthPoints);
		updatePlayerHeartsGUI(0);
	}
	public void updatePlayerHeartsGUI(int hp) {
		int heartLen = playerHearts.size();
		int dif = hp - heartLen;
		if (dif > 0) {
			for (int i = 0; i < dif; i++) {
				GImage heart = new GImage("res/texture/HP.png", heartRootX + ((heartLen + i) * heartWidth), heartRootY);
				heart.setSize(25, 20);
				heart.setVisible(true);
				playerHearts.add(heart);
				this.add(heart);
			}
		}
		else if (dif < 0) {
			dif = dif * -1; // Absolute value
			for (int i = 0; i < dif; i++) {
				int end = playerHearts.size() - 1;
				GImage heart = playerHearts.get(end);
				this.remove(heart);
				playerHearts.remove(end);
			}
		}
	}
	
	/* Doors
	 * --------------------------------------------
	 * setDoors()
	 * Doors(Boolean addOrRemove, MapType map)
	 * unlockDoor(Door door, KeyEvent e)
	 * openDoor(Door door, KeyEvent e)
	 * openDoor(Door door, KeyEvent e, int x, int y)
	 */
	public void setDoors() {
		inBedMap = new Door(64,150,64,20, true, MapType.LIVINGR);
		inBedMap.setRoomType(RoomType.BEDROOMS);
		inBath = new Door(672,380,32,20, false, MapType.LIVINGR);
		outBath = new Door(672,283,32,10, false, MapType.LIVINGR);
		winning = new Door(480,540,65,10, true, MapType.LIVINGR);
		winning.setRoomType(RoomType.OUT);
		doors.add(inBedMap); doors.add(inBath); doors.add(outBath); doors.add(winning);
		inLivingMap = new Door(704,510,64,4, false, MapType.BEDR);
		inLeftBed = new Door(128,340,32,20, true, MapType.BEDR);
		inRightBed = new Door(672,340,32,20, true, MapType.BEDR);
		inRightBed.setRoomType(RoomType.BEDROOMR);
		outLeftBed = new Door(128,252,32,5, false, MapType.BEDR);
		outRightBed = new Door(672,252,32,5, false, MapType.BEDR);
		inRoom2 = new Door(20,20,64,20, true, MapType.BEDR);
		inRoom2.setRoomType(RoomType.OFFICE);
		inRoom2.getRect().setVisible(false);
		leftToRoom2 = new Door(20,100,64,40, true, MapType.BEDR);
		leftToRoom2.setRoomType(RoomType.OFFICE);
		outOffice = new Door(400,100,64,20, false, MapType.OFFICE);
		outOffice.setRoomType(RoomType.BEDROOMS);
		secretDoor = new Door(20,20,64,40, false, MapType.BEDR);
		secretDoor.setRoomType(RoomType.BEDROOMR);
		secretDoor.getRect().setVisible(false);
		doors.add(inLivingMap); doors.add(inLeftBed); doors.add(inRightBed); doors.add(outLeftBed); doors.add(outRightBed); doors.add(inRoom2); doors.add(leftToRoom2); doors.add(outOffice); doors.add(secretDoor);
	}
	public void Doors(Boolean addOrRemove, MapType map) {
		for (int i = 0; i < this.doors.size(); ++i)   {
			Door door = this.doors.get(i);
			if(door.getMapType()==map) {
				if(addOrRemove) {
					this.add(door.getRect());
					if(door == leftToRoom2) {
						door.getRect().sendToFront();
					}
				}else {
					this.remove(door.getRect());
				}
			}
		}
	}
	public void unlockDoor(Door door, KeyEvent e) {
		if(getSelectedItem()!=null) {
			if(this.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_E){
				if(getSelectedItem().getRoomType()==door.getRoomType()) {
					if(wrongItem!=null) {wrongItem.setVisible(false);} 
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					keyUsed = new GLabel("Unlocked the door!", 200, 560);
					keyUsed.setColor(Color.white);
					keyUsed.setFont(this.customFont);
					this.add(keyUsed);
					door.unlock();
					this.player.getInventory().deleteItem(this,getSelectedItem());
					this.remove(getSelectedItem().getInvSprite());
					this.label5sec(keyUsed);
				}else {
					if(lockedDoor!=null) {lockedDoor.setVisible(false);}
					wrongItem = new GLabel("Wrong item!", 200, 580);
					wrongItem.setColor(Color.white);
					wrongItem.setFont(this.customFont);
					this.add(wrongItem);
					this.label5sec(wrongItem);
				}	
			}
		}
	}
	public void openDoor(Door door, KeyEvent e) {
		if(this.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			if (!door.isLocked()) {
				if(door == inBedMap) {
					this.switchToBedRoom();
				}else if (door == winning) {
					if(this.NPC.isDead()) {
						this.switchToTrueEnd();
					}else {
						this.switchToGoodEnd();
					}
				}else if (door == inLivingMap) {
					this.fromBedtoLiving = true;
					this.switchToNewGame();
				}else if(door == inRightBed) {
					this.remove(this.player.getImage());
					this.add(this.player.getImage(), 672,219);
				}else if(door == inRoom2 || door == leftToRoom2 || door == secretDoor) {
					this.switchToOffice();
				}else if(door == outOffice) {
					this.switchToBedRoom();
				}
			}else {
				if(wrongItem!=null) {wrongItem.setVisible(false);}
				lockedDoor = new GLabel("Door is locked!", 200, 540);
				lockedDoor.setColor(Color.white);
				lockedDoor.setFont(this.customFont);
				this.add(lockedDoor);
				this.label5sec(lockedDoor);
			}
		}
	}
	public void openDoor(Door door, KeyEvent e, int x, int y) {
		if(this.player.sprite.getBounds().intersects(door.getRect().getBounds()) && e.getKeyCode()==KeyEvent.VK_ENTER) {
			this.remove(this.player.getImage());
			this.add(this.player.getImage(), x,y);
		}
	}
	
	/* Items
	 * ---------------------------
	 * setItems()
	 * Items(Boolean addOrRemove, MapType map)
	 * getSelectedItem()
	 * setSelectedItem(KeyEvent e)
	 * numItems()
	 * itemAt(int i)
	 * pickUpItem(KeyEvent e)
	 * grab(Item item)
	 */
	public void setItems() {
		itemKnife = new Item("Knife",new GImage ("res/inventory/Small Knife.png"), ItemType.WEAPON, MapType.LIVINGR);
		itemKnife.setX(734);
		itemKnife.setY(259);
		itemKnife.setDescription("Knife to kill");
		items.add(itemKnife);
		Item bedroomKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, MapType.LIVINGR);
		bedroomKey.setX(200);
		bedroomKey.setY(100);
		bedroomKey.setRoomType(RoomType.BEDROOMS);
		bedroomKey.setDescription("Key to bedrooms");
		items.add(bedroomKey);
		Item winningKey = new Item("Key",new GImage("res/inventory/Small Key.png"), ItemType.KEY, MapType.BEDR);
		winningKey.setX(422);
		winningKey.setY(217);
		winningKey.setRoomType(RoomType.OUT);
		winningKey.setDescription("Key to outside");
		items.add(winningKey);
		Item itemKey2 = new Item("Key", new GImage("res/inventory/Small Key.png"), ItemType.KEY, MapType.BEDR);
		itemKey2.setX(116);
		itemKey2.setY(184);
		itemKey2.setRoomType(RoomType.BEDROOMR);
		itemKey2.setDescription("Key to bedroom");
		items.add(itemKey2);
		Item room2Key = new Item("Key", new GImage("res/inventory/Small Key.png"), ItemType.KEY, MapType.LIVINGR);
		room2Key.setX(300);
		room2Key.setY(200);
		room2Key.setRoomType(RoomType.OFFICE);
		room2Key.setDescription("Key to Office");
		items.add(room2Key);
		Item gun = new Item("Gun", new GImage("res/inventory/gun.png"), ItemType.WEAPON, MapType.BEDR);
		gun.setX(500);
		gun.setY(300);
		gun.setDescription("Gun to shoot");
		items.add(gun);
		Item officeGun = new Item("Gun", new GImage("res/inventory/gun.png"), ItemType.GUN, MapType.OFFICE);
		officeGun.setX(500);
		officeGun.setY(400);
		officeGun.setDescription("Gun to shoot the monster");
		items.add(officeGun);
	}
	public void Items(Boolean addOrRemove, MapType map) {
		for (int i = 0; i < this.numItems(); i++)   {
			if (this.itemAt(i).getMapType()==map && !this.itemAt(i).isPickedUp()) {
				if(addOrRemove) {
					this.add(this.itemAt(i).getImage(), this.itemAt(i).getX(), this.itemAt(i).getY());
				}else {
					this.remove(this.itemAt(i).getImage());
				}
			}
		}
	}
	public Item getSelectedItem() {return this.player.getInventory().getSelectedItem();}
	public void setSelectedItem(KeyEvent e) {
		Inventory playerInv = this.player.getInventory();
		if(e.getKeyCode()==KeyEvent.VK_1) {
			if (playerInv.setSelectedItem(0)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_2) {
			if (playerInv.setSelectedItem(1)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_3) {
			if (playerInv.setSelectedItem(2)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_4) {
			if (playerInv.setSelectedItem(3)) {
				playerInv.drawSelectedItem();
			}
		}
		else if(e.getKeyCode()==KeyEvent.VK_5) {
			if (playerInv.setSelectedItem(4)) {
				playerInv.drawSelectedItem();
			}
		}
	}
	public int numItems() {return items.size();}
	public Item itemAt(int i) {return items.get(i);}
	public void pickUpItem(KeyEvent e) {
		for (int i=0; i<this.numItems(); i++) {
			Item item = this.itemAt(i);
			if(!item.isPickedUp() && this.player.sprite.getBounds().intersects(item.getImage().getBounds())&& e.getKeyCode()==KeyEvent.VK_E) {
				this.grab(this.itemAt(i));
			}
		}
	}
	public void grab(Item item)   {
		this.player.grabItem(item);
		item.setPickedUp(true);
		this.remove(item.getImage());
		this.add(item.getInvSprite());
	}
	
	/* Pause and Resume
	 * ----------------
	 * pauseMenu(GObject obj)
	 * pause()
	 * resume()
	 */
	public void pauseMenu(GObject obj) {
		if (obj == this.pauseButton) {
			this.monsterTimer.stop();
			this.pause();
		}
		if (obj == this.resume) {
			this.resume();
			this.monsterTimer.setInitialDelay(0);
			this.monsterTimer.restart();
		}	
		if (obj == this.quit) {System.exit(0);}
	}
	public void pause() {
		if (resumeImg != null) this.add(resumeImg);
		if (quitImg != null) this.add(quitImg);
		this.add(resume);
		this.add(quit);
		paused = true;
	}
	public void resume() {
		if (resumeImg != null) this.remove(resumeImg);
		this.remove(resume);
		this.remove(quit);
		if (quitImg != null) this.remove(quitImg);
		paused = false;
	}
	
	/* Labels
	 * -----------------------
	 * removeLabels()
	 * label5sec(GLabel label)
	 */
	public void removeLabels() {
		if(keyUsed!=null) {this.remove(keyUsed);}
		if(wrongItem!=null) {this.remove(wrongItem);}
		if(lockedDoor!=null) {this.remove(lockedDoor);}
	}
	public void label5sec(GLabel label) {
		//label disappears in 5 sec
		int delay = 5000;
	    ActionListener taskPerformer = new ActionListener() {
	    	public void actionPerformed(ActionEvent evt) {
	        label.setVisible(false);;
	    	}
	    };
	    javax.swing.Timer tick=new javax.swing.Timer(delay,taskPerformer);
	    tick.setRepeats(false);
	    tick.start();
	}
	
	/* Player, monster, NPC
	 * addPlayer(int x, int y)
	 * addMonster(int x, int y)
	 * addNPC(int x, int y)
	 */
	public void addPlayer(int x, int y)   {
		this.add(player.getImage(), x, y);
		player.setX(x);
		player.setY(y);
	}
	public void addMonster(int x, int y)   {
		this.add(monster.getImage(), x, y);
		monster.setX(x);
		monster.setY(y);
	}
	public void addNPC(int x, int y)   {
		this.add(NPC.getImage(), x, y);
		NPC.setX(x);
		NPC.setY(y);
	}
	
	//collision detect
	public boolean checkCollision(ArrayList<GRect> walls) {
		Iterator<GRect> iterate = walls.iterator();
		while(iterate.hasNext()) {
			GRect temp = iterate.next();
			if(this.player.sprite.getBounds().intersects(temp.getBounds())) {
				
				if(this.player.getDX() > 0)   {
					if(this.player.getDY() > 0)   {this.player.sprite.move(-2, -2);}
					if(this.player.getDY() < 0)   {this.player.sprite.move(-2, 2);}
					if(this.player.getDY() == 0)   {this.player.sprite.move(-4, 0);}
				}
				if(this.player.getDX() < 0)   {
					if(this.player.getDY() > 0)   {this.player.sprite.move(2, -2);}
					if(this.player.getDY() < 0)   {this.player.sprite.move(2, 2);}
					if(this.player.getDY() == 0)   {this.player.sprite.move(4, 0);}
				}
				if(this.player.getDY() < 0)   {
					if(this.player.getDX() > 0)   {this.player.sprite.move(-2, 2);}
					if(this.player.getDX() < 0)   {this.player.sprite.move(2, 2);}
					if(this.player.getDX() == 0)   {this.player.sprite.move(0, 4);}
				}
				if(this.player.getDY() > 0)   {
					if(this.player.getDX() > 0)   {this.player.sprite.move(-2, -2);}
					if(this.player.getDX() < 0)   {this.player.sprite.move(2, -2);}
					if(this.player.getDX() == 0)   {this.player.sprite.move(0, -4);}
				}
				return true;
			}
		}
		return false;
	}
	
	//music
	public static MusicBox getMusic() {return music;}
	
	/* Method: setupInteractions
	 * -------------------------
	 * must be called before switching to another
	 * pane to make sure that interactivity
	 * is setup and ready to go.
	 */
	protected void setupInteractions() {
		requestFocus();
		addKeyListeners();
		addMouseListeners();
	}
	
	/* switchToScreen(newGraphicsPane)
	 * -------------------------------
	 * will simply switch from making one pane that was currently
	 * active, to making another one that is the active class.
	 */
	protected void switchToScreen(GraphicsPane newScreen) {
		if(curScreen != null) {
			curScreen.hideContents();
		}
		newScreen.showContents();
		curScreen = newScreen;
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(curScreen != null) {curScreen.mousePressed(e);}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {if(curScreen != null) {curScreen.mouseReleased(e);}}
	
	@Override
	public void mouseClicked(MouseEvent e) {if(curScreen != null) {curScreen.mouseClicked(e);}}
	
	@Override
	public void mouseDragged(MouseEvent e) {if(curScreen != null) {curScreen.mouseDragged(e);}}
	
	@Override
	public void mouseMoved(MouseEvent e) {if(curScreen != null) {curScreen.mouseMoved(e);}}
	
	@Override
	public void keyPressed(KeyEvent e) {
		if(curScreen != null) {
			if(!paused) {curScreen.keyPressed(e);}
		}
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		if(curScreen != null) {curScreen.keyReleased(e);}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {
		if(curScreen != null) {curScreen.keyTyped(e);}
	}
	

	public void actionPerformed(ActionEvent e) {
		monster.move(this.player);
		if(monster.touchPlayer())   {
			if (this.player.getHP() <= 0)   {
				monsterTimer.stop();
			}else {
				monsterTimer.setInitialDelay(2000);
				monsterTimer.restart();
			}
			this.player.setHP(this.player.getHP() - 1);
			if(this.player.getHP()==0) {this.switchToBadEnd();}
			this.updatePlayerHeartsGUI(this.player.getHP());
		}
	}

	public void init() {
		setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
	}

	public void run() {
		System.out.println("Let's make something awesome!");
		File Audio = new File("res/music/TestAudio.wav");
		music.PLAY(Audio);
		menu = new MenuPane(this);
		newGame = new NewGamePane(this);
		options = new OptionPane(this);
		credits = new Credits(this);
		bedroom = new BedRoomGamePane(this);
		office = new OfficeGamePane(this);
		goodEnd = new GoodEndPane(this);
		badEnd = new BadEndPane(this);
		trueEnd = new TrueEndPane(this);
		try {
		     customFont = Font.createFont(Font.TRUETYPE_FONT, new File("res/prstartk.ttf")).deriveFont(10f);
		     GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		     ge.registerFont(customFont);
		} catch (IOException|FontFormatException e) {
		     e.printStackTrace();
		}
		player = new Player(0, 0, this);
		monster = new Monster(0, 0, MonsterType.TALL, this);
		NPC = new NPC(540, 450, this);
		monsterTimer = new Timer(100, this);
		setGUI();
		setDoors();
		setItems();
		setupInteractions();
		// Add resize listener
		// getWindow().addComponentListener(new ComponentAdapter() {
		// 	@Override
		// 	public void componentResized(ComponentEvent e) {
		// 		if (curScreen != null) {
		// 			curScreen.showContents();
		// 		}
		// 	}
		// });
		switchToMenu();
	}

	public void switchToMenu() {switchToScreen(menu);}
	
	public void switchToNewGame() {switchToScreen(newGame);}
		
	public void switchToOptions() {switchToScreen(options);}
	
	public void switchToCredits() {switchToScreen(credits);}

	public void switchToBedRoom() {switchToScreen(bedroom);}
	
	public void switchToGoodEnd() {switchToScreen(goodEnd);}
	
	public void switchToBadEnd() {switchToScreen(badEnd);}
	
	public void switchToTrueEnd() {switchToScreen(trueEnd);}

	public void switchToOffice() {switchToScreen(office);}

	public static void main(String[] args) {new MainApplication().start();}

}
