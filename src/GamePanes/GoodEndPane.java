package GamePanes;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class GoodEndPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GButton quit;
	private GImage background;
	
	public GoodEndPane(MainApplication app) {
		this.program = app;
		background = new GImage("res/texture/Good Ending.png");
		quit = new GButton("", 296, 460, 208, 95);
	}
	
	@Override
	public void showContents() {
		program.add(background);
		program.add(quit);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(quit);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == quit) {System.exit(0);}
	}
	
	@Override
	public void keyPressed(KeyEvent e) {}
	
	@Override
	public void keyReleased(KeyEvent e) {}
}
