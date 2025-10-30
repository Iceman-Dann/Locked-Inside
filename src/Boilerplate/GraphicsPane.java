package Boilerplate;

/* File: GraphicsPane.java
 * -----------------------
 * Like you did with your own graphics programs, simply
 * extend from GraphicsPane and implement
 * as little or as much of the mouse listeners that you need
 * for your own programs.  Notice however that in this situation
 * There is no access to the GraphicsProgram window.
 * Make sure to distinguish between your constructor
 * and using showContents and hideContents
 */

import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public abstract class GraphicsPane implements Interfaceable {
	public boolean leftPressed, rightPressed;
	public boolean[] keys;
	private int mouseX, mouseY; 
	
	public GraphicsPane()  {
		
	}
	
	
	// The getters
	public boolean isLeftPressed() {
		return leftPressed;
	}
		
	public boolean isRightPressed() {
		return rightPressed;
	}
		
	public int getMouseX() {
		return mouseX; 
	}
		
	public int getMouseY() {
		return mouseY; 
			
	}
	
	@Override
	public abstract void showContents();

	@Override
	public abstract void hideContents();

	@Override
	public void mousePressed(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = true;
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = true; 
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(e.getButton() == MouseEvent.BUTTON1) {
			leftPressed = false;
		}
		else if(e.getButton() == MouseEvent.BUTTON3) {
			rightPressed = false; 
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;

	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}

}
