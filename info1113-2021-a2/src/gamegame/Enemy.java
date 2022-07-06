package gamegame;


import processing.core.*;
import processing.data.*;

public class Enemy {
	
	private PApplet sketch;
	// Positional indicators
	int initX;
	int initY;
	String initDirection;
	int x;
	int y;
	int w;
	int h;
	Map map;
	String direction;
	int color1, color2, color3;
	
	// Constructor
	public Enemy(PApplet sketch, int initX, int initY, Map map, String initDirection, int color1, int color2, int color3) {
		this.sketch = sketch;
		// For reset
		this.initX = initX;
		this.initY = initY;
		this.initDirection = initDirection;
		// For movement
		this.x = initX;
		this.y = initY;
		this.w = 32;
		this.h = 32;
		this.map = map;
		this.direction = initDirection;
		
		this.color1 = color1;
		this.color2 = color2;
		this.color3 = color3;
	}
	
	public void update() {
		while (true) {
			if (isValidMove(this.x, this.y, this.direction)) {
				this.moveEnemy(this.x, this.y, this.direction);
				break;
			}
		}
	}
	
	public void display(boolean moved) {
		// Color / animation
		sketch.fill(this.color1, this.color2, this.color3);
		
		// Draw player in position
		if (moved) {
			// Draw player on new position, old position handled in movePlayer
			sketch.rect((float)this.x, (float)this.y, (float)this.w, (float)this.h);
		}
	}
	
	
	
	
	public void moveEnemy(int x, int y, String direction) {
		// givent AI determine and make move
		// same as isvalidmove
	}
	
	public void resetEnemy() {
		this.resetSquare(this.map, this.x , this.y);
		this.x = initX;
		this.y = initY;
		this.direction = this.initDirection;
	}
	
	public boolean isValidMove(int x, int y, String direction) {
		// given AI specification determine move/square
		// Calls isLegalSquare after determining the square
		return true;
	}
	
	public boolean isLegalSquare(int x, int y) {
		// Check boundaries
		if (x < 0 || y < 0 || x > 479 || y > 479) {
			return false;
		}
		
		// Check walls
		String tmp = "" + x + y;
		int hashInt = Integer.parseInt(tmp);
		Square toCheck = this.map.getHashMap().get(hashInt);
		// If no square allow move
		if (toCheck == null) {
			return true;
		}
		switch (toCheck.getType()) {
		case "Wall":
			return false;
		case "BWall":
			return false;
		default:
			return true;
		}

	}
	
	public void resetSquare(Map map, int x, int y) {
		String tmp = "" + x + y;
		int hashInt = Integer.parseInt(tmp);
		Square toReset = this.map.getHashMap().get(hashInt);
		if (toReset == null) {
			return;
		}
		
		switch (toReset.getType()) {
		case "Goal":
			sketch.fill(100, 0, 0);
			sketch.rect(toReset.getX(), toReset.getY(), 32, 32);
			break;
		case ("Empty"):
			sketch.fill(0, 255, 0);
			sketch.rect(toReset.getX(), toReset.getY(), 32, 32);
			break;
		case ("Wall"):
			sketch.fill(0, 0, 0);
			sketch.rect(toReset.getX(), toReset.getY(), 32, 32);
			break;
		case ("BWall"):
			if (toReset.BWallNot) {
				sketch.fill(0, 255, 0);
				sketch.rect(toReset.getX(), toReset.getY(), 32, 32);
			}else {
				sketch.fill(60);
				sketch.rect(toReset.getX(), toReset.getY(), 32, 32);
			}
		}
	}
}

