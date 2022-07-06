package gamegame;

import processing.core.PApplet;
import java.util.Random;

public class Red extends Enemy{
	
	private String[] dirs = {"Left", "Up", "Right", "Down"};

	public Red(PApplet sketch, int x, int y, Map map, String direction, int color1, int color2, int color3) {
		super(sketch, x, y, map, direction, color1, color2, color3);
	}
	
	@Override
	public boolean isValidMove(int x, int y, String direction) {
		Random rand = new Random();
		int r = rand.nextInt(4);
		
		if (direction == "Left") {
			if (isLegalSquare(x-32, y)) {
				return true;
			}else {
				this.direction = dirs[r];
				return false;
			}
		}else if (direction == "Up") {
			if (isLegalSquare(x, y-32)) {
				return true;
			}else {
				this.direction = dirs[r];
				return false;
			}
		}else if (direction == "Right") {
			if (isLegalSquare(x+32, y)) {
				return true;
			}else {
				this.direction = dirs[r];
				return false;
			}
		}else if (direction == "Down") {
			if (isLegalSquare(x, y+32)) {
				return true;
			}else {
				this.direction = dirs[r];
				return false;
			}
		}
		return false;
	}
	
	@Override
	public void moveEnemy(int x, int y, String direction) {
		// givent AI determine and make move
		// same as isvalidmove
		switch (direction) {
		case ("Left"):
			this.resetSquare(this.map, this.x , this.y);
			this.x -= 32;
		break;
		case ("Up"):
			this.resetSquare(this.map, this.x , this.y);
			this.y -= 32;
		break;
		case ("Right"):
			this.resetSquare(this.map, this.x , this.y);
			this.x += 32;
		break;
		case ("Down"):
			this.resetSquare(this.map, this.x , this.y);
			this.y += 32;
		break;

		}
	}
}

