package GamePanes;
import java.awt.event.MouseEvent;
import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GObject;


public class SavePane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	private GImage img; 
	private GButton Back; 
	private GButton save1; 
	private GButton save2;
	private GButton save3; 
	private final int WIDTH = 200; 
	private final int HEIGHT = 88; 

	public SavePane(MainApplication app) {
		super();
		this.program = app;
		program = app;
		double X = app.getWidth()/2 - WIDTH/2 -7;
		img = new GImage("res/texture/Load Game Menu.png", 0, 0);
		img.setSize(800, 640);	
		Back = new GButton("", X, 532, WIDTH, HEIGHT);
		save1 = new GButton("", 40, 50, WIDTH, 450);
		save2 = new GButton("", 300, 50, WIDTH, 450); 
		save3 = new GButton("", 560, 50, WIDTH, 450);	
		
	}

	@Override
	public void showContents() {
		program.add(img);
		program.add(Back);
		program.add(save1);
		program.add(save2);
		program.add(save3);
		
	}

	@Override
	public void hideContents() {
		program.remove(img);
		program.remove(Back);
		program.remove(save1);
		program.remove(save2);
		program.remove(save3);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Back) {program.switchToMenu();}
		if (obj == save1) {program.switchToNewGame();}
		if (obj ==  save2) {program.switchToNewGame();}
		if (obj == save3) {program.switchToNewGame();}
	}
}

