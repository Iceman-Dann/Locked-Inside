package Item;
import acm.graphics.GRect;

public class Door {
	private GRect doorBound;
	private RoomType roomType;
	private MapType mapType;
	private boolean locked;
	
	public Door(double x, double y, double width, double height, boolean locked, MapType map){
		doorBound = new GRect(x,y,width,height);
		doorBound.setVisible(false);
		this.locked = locked;
		this.mapType = map;
	}
	
	public void setRoomType(RoomType roomType) {this.roomType=roomType;}
	
	public void unlock() {locked = false;}
	
	public RoomType getRoomType() {return roomType;}
	
	public MapType getMapType() {return mapType;}
	
	public boolean isLocked() {return locked;}
	
	public GRect getRect() {return doorBound;}
	
}
