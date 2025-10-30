package GamePanes;
import java.awt.event.MouseEvent;

import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class Credits extends GraphicsPane {
	
	private MainApplication program; 
	
	private GImage credits;
	private GButton back;
	private final int WIDTH = 200;
	private final int HEIGHT = 88; 
	
	public Credits(MainApplication app) {
		super();
		this.program = app;
		program = app;
		double X = app.getWidth()/2 - WIDTH/2 -7;
		credits = new GImage("res/texture/Credits.png", 0, 0);
		credits.setSize(800, 640);	
		back = new GButton("", X, 532, WIDTH, HEIGHT);
	}

	@Override
	public void showContents() {
		program.add(credits);
		program.add(back);
		
	}

	@Override
	public void hideContents() {
		program.remove(credits);
		program.remove(back);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == back) {
			program.switchToOptions();
		}
	}

}
