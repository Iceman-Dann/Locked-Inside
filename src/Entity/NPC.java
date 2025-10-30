package Entity;
import Boilerplate.MainApplication;
import acm.graphics.GImage;

public class NPC extends Entity{
	public GImage sprite = getImage();
	private boolean dead = false;
	MainApplication program;
	
	public NPC(int x, int y, MainApplication program)   {
		super(x, y);
		this.program = program;
		this.sprite = new GImage("res/NPC/NPC2.png");
	}
	
	public void setDead(boolean dead)   {
		this.dead = dead;
		if(dead == true)   {program.remove(sprite);}
	}
	
	public GImage getImage()   {return sprite;}
	
	public boolean isDead()   {return dead;}
	
}
