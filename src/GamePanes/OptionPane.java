package GamePanes;
import java.awt.Color;
import java.awt.event.MouseEvent;
import Boilerplate.GButton;
import Boilerplate.GraphicsPane;
import Boilerplate.MainApplication;
import Boilerplate.MusicBox;
import acm.graphics.GImage;
import acm.graphics.GObject;
import acm.graphics.GRect;


public class OptionPane extends GraphicsPane {
	// you will use program to get access to all of the GraphicsProgram calls
	private MainApplication program; 
	
	private GImage background, mute;
	private GButton Credits, Back, MuteButton; 
	private GRect audioBar;
	private final int WIDTH = 200, HEIGHT = 88; 
	private int lastX;
	private GObject obj;
	private boolean muted = false;
	
	public OptionPane(MainApplication app) {
		super();
		this.program = app;
		program = app;
		double X = app.getWidth()/2 - WIDTH/2 -7;
		background = new GImage("res/texture/Options Menu.png", 0, 0);
		background.setSize(800, 640);
		mute = new GImage("res/texture/Unmuted.png", 718, 75);
		Credits = new GButton("", X, 418, WIDTH, HEIGHT);
		Back = new GButton("", X, 532, WIDTH, HEIGHT);
		MuteButton = new GButton("", 718, 75, 42, 41);
		audioBar = new GRect(710, 170, 32, 32);
		audioBar.setFilled(true);
		audioBar.setFillColor(Color.black);
	}
	
	private MusicBox getMusic()   {
		return MainApplication.getMusic();
	}
	
	private void setVolume(double d)   {
		for(int i = 0; i <= 8; ++i)   {
			if (d >= 50 + 82 * i && d <= 50 + 82 * (i + 1))   {
				getMusic();
				MusicBox.audioControl.setValue(-40.0f + (5.0f * i));
			}
		}
	}

	@Override
	public void showContents() {
		program.add(background);
		program.add(Credits);
		program.add(Back);
		program.add(audioBar);
		program.add(mute);
		program.add(MuteButton);
	}

	@Override
	public void hideContents() {
		program.remove(background);
		program.remove(Credits);
		program.remove(Back);
		program.remove(audioBar);
		program.remove(mute);
		program.remove(MuteButton);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		lastX = e.getX();
		obj = program.getElementAt(e.getX(), e.getY());
		if (obj == Back) {
			program.switchToMenu();
		}
		if (obj == Credits) {
			program.switchToCredits();
		}
		if (obj == MuteButton)   {
			if(muted == false)   {
				mute.setImage("res/texture/Muted.png");
				muted = true;
				getMusic();
				MusicBox.Mute();
			}
			else   {
				mute.setImage("res/texture/Unmuted.png");
				muted = false;
				getMusic();
				MusicBox.Unmute();
			}
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		if (obj == audioBar) {
			if (audioBar.getX() < 50)   {audioBar.setLocation(50, 170); return;}
			if (audioBar.getX() > 710)   {audioBar.setLocation(710, 170); return;}
				audioBar.move(e.getX() - lastX, 0);
				lastX = e.getX();
				setVolume(audioBar.getX());
		}
	}
}


