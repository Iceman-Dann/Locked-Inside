package Item;
import acm.graphics.GImage;
import acm.graphics.GRect;

public class Item {
	
	private int x, y, count, width = 32, height = 32;
	private String name, desc;
	private boolean used, pickedUp;
	private GImage sprite, invSprite;
	private ItemType type;
	private RoomType roomType;
	private MapType mapType;

	public Item (String name, GImage sprite, ItemType type, MapType map) {
		this.name = name;
		this.sprite = sprite;
		setInvSprite(new GImage("res/inventory/" + name + ".png", x, y));
		this.type = type;
		count = 1;
		this.mapType = map;
	}
	
	public void setRoomType(RoomType roomType) {this.roomType=roomType;}
	
	public RoomType getRoomType() {return roomType;}
		
	public MapType getMapType() {return mapType;}
	
	public ItemType getItemType() {return type;}
	
	public int getX()   {return x;}
	
	public int getY()   {return y;}
	
	public int getCount()   {return count;}
	
	public String getName()   {return name;}
	
	public GImage getImage()   {return sprite;}
	
	public void setImage(GImage image)   {sprite = image;}
	
	public String getDescription()   {return desc;}
	
	public GRect getBounds()   {return new GRect(x, y, width, height);}
	
	public boolean isUsed()   {return used;}
	
	public boolean isPickedUp()   {return pickedUp;}
	
	public void setX(int x)   {this.x = x;}
	
	public void setY(int y)   {this.y = y;}
	
	public void setCount(int count)   {this.count = count;}
	
	public void setName(String name)   {this.name = name;}
	
	public void setDescription(String desc)   {this.desc = desc;}
	
	public void setUsed(boolean used)   {this.used = used;}
	
	public void setPickedUp(boolean pickedUp)   {this.pickedUp = pickedUp;}

	public GImage getInvSprite() {return invSprite;}

	public void setInvSprite(GImage invSprite) {this.invSprite = invSprite;}
}