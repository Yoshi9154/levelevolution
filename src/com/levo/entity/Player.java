package com.levo.entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import com.levo.game.Game;
import com.levo.physics.AABB;
import com.levo.physics.Collision;
import com.levo.physics.Vec2;

public class Player extends Entity {
	public static final double JUMP_VEL = -5;
	public static final double SPEED = 5;

	private AABB aabb;
	private Vec2 vel;
	private boolean canJump;
	
	// Initialize with position
	public Player(Vec2 pos) {
		aabb = new AABB(pos, 10, 10); 
		vel = new Vec2(0, 0);
		canJump = true;
	}
	
	public void draw(Graphics2D g) {
		aabb.draw(g, Color.GREEN);
	}
	
	public void update() {
		aabb.addVec(vel);
		vel.add(Game.GRAVITY);
	}
	
	public void updateWithControls(boolean[] keyDown) {
		if (keyDown[KeyEvent.VK_SPACE]) {
			if (canJump && vel.y == 0) {
				vel.add(new Vec2(0, -5));
				canJump = false;
			}
		} 
		vel.x = 0;
		if (keyDown[KeyEvent.VK_A] || keyDown[KeyEvent.VK_LEFT]) {
			if(canJump)
				vel.x = -SPEED;
			else
				vel.x = -SPEED / 4;
		} 
		if (keyDown[KeyEvent.VK_D] || keyDown[KeyEvent.VK_RIGHT]) {
			if(canJump)
				vel.x = SPEED;
			else
				vel.x = SPEED / 4;
		}
	}
	
	public void handleCollisions(List<Block> blocks) {
		for (Block b : blocks) {
			// Check if block is colliding with player
			if (b.getAABB().isColliding(aabb)) {
				Collision c = b.getAABB().getCollision(aabb);
				
				if (c.getNormal().y == -1) {
					vel.y = 0;
					canJump = true;
				} if (c.getNormal().y == 1) {
					vel.y = .1;
				}
				
				
				if (c.getDepth() >= 0) {
					aabb.addVec(c.getNormal().scaled(c.getDepth()));
				}
			}
		}
	}
}
