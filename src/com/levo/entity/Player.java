package com.levo.entity;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.List;

import com.levo.game.Game;
import com.levo.physics.AABB;
import com.levo.physics.Collision;
import com.levo.physics.Vec2;

//to be removed
import java.awt.Color;

public final class Player extends Mobs {
        // The accelleration of the player
        public static final double NORMAL_ACCEL = 0.3;
        // The rate at which the player will slow down
        public static final double NORMAL_DEACCEL = 0.2;
        // The Y-Velocity the player accelerates by when jumping
	public static final double JUMP_ACCEL = 5.5;
        
	// Maximum speed in the Y direction that the player can achieve
	public static final double MAX_Y_VEL = 5;
        // Maximum speed in the X direction that the player can achieve
        public static final double MAX_X_VEL = 7;
        
        // Factor by which the NORMAL_ACCEL is multiplied when sprinting
        public static final double SPRINT_FACTOR = 3;
	// Wall jump movement cooldown to make sure player moves away from wall before going back to it
	public static final int WALL_JUMP_COOLDOWN = 15;
	// Multiplier on gravity to be used during a wall slide to reduce slide speed
	public static final double WALL_SLIDE_GRAVITY = .2;
	
	public static final int WIDTH = 15;
	public static final int HEIGHT = 20;
        

	// Array with an index for every key code. keyDown[any key code] is true when that key is being pressed
	private boolean[] keyDown;
        //Forces the player to move away from the wall
        private int wallJumpCooldown;
	
	// Initialize with starting position. 
	// Also passes a reference to keyDown array so when keyDown is updated in GameState.java, 
	// the player keyDown array is effected
	public Player(Vec2 pos, boolean[] keyDown) {
		super(pos, WIDTH, HEIGHT, ID.PLAYER,5); 
		this.keyDown = keyDown;
	}
	
	// Draws the player currently as a rectangle with a purple color
        @Override
	public void draw(Graphics2D g) {
		this.getAABB().draw(g, Color.MAGENTA);
	}
	
        public void deaccel() {
        // Function that handles deacceleration to prevent the player from moving infinitely
            if (this.getVel().x != 0) {
                //Deacceleration depends on the circumstances
                double rateDeaccel;
                
                if (this.isOnGround()) {
                    rateDeaccel = NORMAL_DEACCEL;
                }
                else {
                    // If flying, the deacceleration is halved
                    rateDeaccel = 0.5*NORMAL_DEACCEL;
                }
                
                //Apply deacceleration
                if (this.getVel().x > 0) {
                    this.getVel().x -= rateDeaccel;
                } else {
                    this.getVel().x += rateDeaccel;
                }
            }
            // Deacceleration due to gravity
            if ((this.isOnLeftWall() || this.isOnRightWall()) && this.getVel().y <= 0) {
                //Gravity is lessened by a factor of WALL_SLIDE_GRAVITY, to create a sliding effect
                System.out.println("gravity");
                this.getVel().add(Game.GRAVITY.scaled(WALL_SLIDE_GRAVITY));
            } else {
                System.out.println("gravity 1");
                this.getVel().add(Game.GRAVITY);
            }
            
            //Round to two decimal places
            double var = Math.round((this.getVel().x*100));
            this.getVel().x = var / 100;
        }
        
        
        public void jump() {
        // Function that handles jumping
            if (keyDown[KeyEvent.VK_SPACE]) {
		if (this.isOnGround()) {
                    // A regular jump is possible and will occur
                    this.getVel().y -= JUMP_ACCEL;
		} 
                if (this.isOnLeftWall() && wallJumpCooldown <= 0) {
                    // A wall jump has happened on a wall to the left
                    this.getVel().x += SPRINT_FACTOR*Math.abs(this.getVel().x);
                    this.getVel().y -= JUMP_ACCEL;
                    wallJumpCooldown = WALL_JUMP_COOLDOWN; // A cooldown is necessary so the player moves away from the wall
		} 
                else if (this.isOnRightWall() && wallJumpCooldown <= 0) {
                    // A wall jump has happened on a wall to the right
                    this.getVel().x -= SPRINT_FACTOR*Math.abs(this.getVel().x);
                    this.getVel().y -= JUMP_ACCEL;
                    wallJumpCooldown = WALL_JUMP_COOLDOWN;
		} else {
                    wallJumpCooldown--;
                }
                
                
            } 
        }
        
        public void walk() {
            // Check player movement keys and set speed accordingly
            if (keyDown[KeyEvent.VK_A] || keyDown[KeyEvent.VK_LEFT]) {
                this.getVel().x -= NORMAL_ACCEL;
            }
            if (keyDown[KeyEvent.VK_D] || keyDown[KeyEvent.VK_RIGHT]) {
                this.getVel().x += NORMAL_ACCEL;
            }
        }  
        
        
        public void sprint () {
                // Multiply the rate of acceleration by SPRINT_FACTOR is shift is held down
                if (this.isOnGround()) {
                if ((keyDown[KeyEvent.VK_A] || keyDown[KeyEvent.VK_LEFT]) && keyDown[KeyEvent.VK_SHIFT]) {
                    this.getVel().x -= NORMAL_ACCEL * SPRINT_FACTOR;
                }
                if ((keyDown[KeyEvent.VK_D] || keyDown[KeyEvent.VK_RIGHT]) && keyDown[KeyEvent.VK_SHIFT]) {
                    this.getVel().x += NORMAL_ACCEL * SPRINT_FACTOR;
                }
            }
        }
        
        @Override
	public void update() {
		this.getAABB().addVec(this.getVel()); // Move position by vel vector
                
                
                if (MAX_X_VEL > Math.abs(this.getVel().x)) {
                    this.walk();
                    this.sprint();
                }
                this.jump();
                this.deaccel();
                System.out.println("vel "+this.getVel());

	}
	
	@Override
        public void attack() {
            // TODO
            // Check entity type
            // do damage
        }
	
}
