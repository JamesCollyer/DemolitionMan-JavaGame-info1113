package gamegame;

import processing.core.*;
import processing.data.*;

public class Player {
	
	private PApplet sketch;
	// Positional indicators
	int initX;
	int initY;
	int x;
	int y;
	int w;
	int h;
	Map map;
	
	// Constructor
	public Player(PApplet sketch, int initX, int initY, Map map) {
		this.sketch = sketch;
		this.initX = initX;
		this.initY = initY;
		this.x = initX;
		this.y = initY;
		this.w = 32;
		this.h = 32;
		this.map = map;
	}
	
	public void resetPlayer() {
		this.resetSquare(this.map, this.x, this.y);
		this.x = this.initX;
		this.y = this.initY;
	}
	
	public void update(boolean left, boolean up, boolean right, boolean down) {
		if (isValidMove(this.x, this.y, left, up, right, down)) {
			this.movePlayer(left, up, right, down);
		}
	}
	
	public void display(boolean moved) {
		// Color / animation
		sketch.fill(150);
		
		// Draw player in position
		if (moved) {
			// Draw player on new position, old position handled in movePlayer
			sketch.rect((float)this.x, (float)this.y, (float)this.w, (float)this.h);
		}
	}
	
	public void movePlayer(boolean left, boolean up, boolean right, boolean down) {
		if (left) {
			this.resetSquare(this.map, this.x , this.y);
			this.x -= 32;
		}else if (up) {
			this.resetSquare(this.map, this.x, this.y);
			this.y -= 32;
		}else if (right) {
			this.resetSquare(this.map, this.x, this.y);
			this.x += 32;
		}else if (down) {
			this.resetSquare(this.map, this.x, this.y);
			this.y += 32;
		}
	}
	
	public boolean isValidMove(int x, int y, boolean left, boolean up, boolean right, boolean down) {
		if (left) {
			return isLegalSquare(x - 32, y);
		}else if (up) {
			return isLegalSquare(x, y - 32);
		}else if (right) {
			return isLegalSquare(x + 32, y);
		}else if (down) {
			return isLegalSquare(x, y + 32);
		}else {
			// Note no move made and that is legal
			return true;
		}
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
			if (toCheck.BWallNot) {
				return true;
			}
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
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}

