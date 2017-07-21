package com.levo.gamestate;

import java.awt.Graphics2D;
import java.awt.event.MouseEvent;

public class GameState {
	
	private boolean done;
	private GameState nextState;
	
	public GameState() {
		done = false;
		nextState = null;
	}

	public void draw(Graphics2D g) {
		System.out.println("BAD");
		
	}
	
	public void update() {
		
	}

	// Returning anything non null will cause that gamestate to be pushed on the gamestate stack
	public GameState nextState() {
		return nextState;
	}
	
	protected void setNextState(GameState nextState) {
		this.nextState = nextState;
	}
	
	public boolean isDone() {
		return done;
	}
	
	protected void exit() {
		done = true;
	}
	
	public void dispose() {};

	public void keyPressed(int k) {}

	public void keyReleased(int k) {}
	
	public void mouseClicked(MouseEvent e) {}

	public void mouseEntered(MouseEvent e) {}

	public void mouseExited(MouseEvent e) {}

	public void mousePressed(MouseEvent e) {}
	
	public void mouseReleased(MouseEvent e) {}

	public void mouseDragged(MouseEvent e) {}

	public void mouseMoved(MouseEvent e) {}
	
}