package Item;
import java.awt.Color;
import java.util.ArrayList;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GRect;

public class Inventory {
	public ArrayList<Item> invItems = new ArrayList<Item>(5);
	boolean active = false;
	public static final int INVENTORY_X = 200;
	public static final int INVENTORY_Y = 576;
	public static final GImage INVENTORY_IMG = new GImage("res/inventory/HotBar.png");
	private int listSpacing=0;
	private Item selectedItem = null;
	private GRect redBox = null;
	private GLabel description = null;
	MainApplication program;
	
	
	public Inventory(MainApplication program) {
		redBox = new GRect(0, 0, 32, 32);
		description = new GLabel("", 200, 600);
		setHighlightVisible(false);
		this.program = program;
	}
	
	public int numInvItems() {return invItems.size();}
	
	public int getListSpacing() {return listSpacing;}
	
	public Item getSelectedItem() {return selectedItem;}

	public void setListSpacing(int listSpacing) {this.listSpacing = listSpacing;}

	public void setSelectedItem(Item item) {this.selectedItem = item;}
	
	public boolean setSelectedItem(int idx) {
		int maxItems = numInvItems();
		if (idx < 0 || idx >= maxItems) { return false; }
	
		Item selected = invItems.get(idx);
		setSelectedItem(selected);
		return true;
	}
	
	public boolean setSelectedItem(GObject obj) {
		for (int i=0; i<numInvItems(); i++) {
			Item detected = invItems.get(i);
			if (obj == detected.getInvSprite()) {
				setSelectedItem(detected);
				return true;
			}
		}
		return false;
	}
	
	public void drawSelectedItem() {
		setHighlightVisible(true);
		Item selectedItem = program.player.getInventory().getSelectedItem();
		if(selectedItem!=null) {
			GImage selectedItemSprite = selectedItem.getInvSprite();
			redBox.setLocation(selectedItemSprite.getX(), selectedItemSprite.getY());
			redBox.setColor(Color.red);
			program.add(redBox);
			
			
			description.setLabel(selectedItem.getDescription());
			description.setColor(Color.white);
			description.setFont(program.customFont);
			program.add(description);
		}
		
	}
	
	public void setHighlightVisible(boolean state) {
		if (redBox != null) {	
			redBox.setVisible(state);
			description.setVisible(state);
		}
	}
	
	public void deleteItem(MainApplication program, Item item) {
		Inventory inv= program.player.getInventory();
		inv.invItems.remove(item);
		inv.updateItemGui();
		setHighlightVisible(false);
	}
	
	public void addItem(Item item)   {
		invItems.add(item);
		updateItemGui();
	}
	
	
	public void updateItemGui() {
		for(int i = 0; i < invItems.size(); ++i)   {
			invItems.get(i).getInvSprite().setLocation(200 + (32 * i), INVENTORY_Y + 32);
			invItems.get(i).setY(INVENTORY_Y);
		}
	}
	
	public Item itemAt(int i) {return invItems.get(i);}
	
}
