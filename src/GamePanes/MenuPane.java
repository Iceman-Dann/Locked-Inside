package GamePanes;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import acm.graphics.GImage;
import acm.graphics.GObject;

public class MenuPane extends GraphicsPane {

	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program;

	private GButton newGame;
	private GButton options;
	private GButton exitGame;
	private GImage background;
	private final int WIDTH = 200;
	private final int HEIGHT = 88;


	public MenuPane(MainApplication app) {
		super();
		program = app;
		background = new GImage("res/texture/Main Menu.png", program.scaledX(0), program.scaledY(0));
		background.setSize(program.scaledW(800), program.scaledH(640));
		newGame = new GButton("", program.scaledX(800/2 - WIDTH/2 -7), program.scaledY(305), program.scaledW(WIDTH), program.scaledH(HEIGHT));
		options = new GButton("", program.scaledX(800/2 - WIDTH/2 -7), program.scaledY(418), program.scaledW(WIDTH), program.scaledH(HEIGHT));
		exitGame = new GButton("", program.scaledX(800/2 - WIDTH/2 -7), program.scaledY(532), program.scaledW(WIDTH), program.scaledH(HEIGHT));
	}

	@Override
	public void showContents() {
		background.setLocation(program.scaledX(0), program.scaledY(0));
		background.setSize(program.scaledW(800), program.scaledH(640));
		newGame.setLocation(program.scaledX(800/2 - WIDTH/2 -7), program.scaledY(305));
		options.setLocation(program.scaledX(800/2 - WIDTH/2 -7), program.scaledY(418));
		exitGame.setLocation(program.scaledX(800/2 - WIDTH/2 -7), program.scaledY(532));
		program.add(background);
		program.add(newGame);
		program.add(options);
		program.add(exitGame);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(newGame);
		program.remove(options);
		program.remove(exitGame);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		GObject obj = program.getElementAt(e.getX(), e.getY());
		if (obj == newGame)   {program.switchToNewGame();}
		if (obj == options)   {program.switchToOptions();}
		if (obj == exitGame)   {System.exit(0);}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {}
}
