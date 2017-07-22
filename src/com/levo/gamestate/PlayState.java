package com.levo.gamestate;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import com.levo.entity.Block;
import com.levo.entity.Camera;
import com.levo.entity.Entity;
import com.levo.entity.Player;
import com.levo.physics.Vec2;

public class PlayState extends GameState {

	private List<Entity> blocks;
	private Player p;
	private Camera cam;
	
	public PlayState() {
		super();
		
		blocks = new ArrayList<Entity>();
		blocks.add(new Block(new Vec2(50, 380), 100, 60, Color.GREEN));
		blocks.add(new Block(new Vec2(150, 360), 1000, 80, Color.BLUE));
		blocks.add(new Block(new Vec2(50, 250), 200, 40, Color.CYAN));
		blocks.add(new Block(new Vec2(420, 140), 80, 180, Color.RED));
		blocks.add(new Block(new Vec2(600, 140), 80, 220, Color.RED));
		
		p = new Player(new Vec2(60, 250), keyDown);
		cam = new Camera(p);
		
		System.out.println("PlayState initialized");
	}
	
	public void draw(Graphics2D g) {
		cam.draw(g, blocks);
	}

	public void update() {
		p.update();
		p.handleCollisions(blocks);
		cam.update();
	}
}
