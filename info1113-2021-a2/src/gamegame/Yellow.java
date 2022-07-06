package gamegame;

import processing.core.PApplet;

public class Yellow extends Enemy{

	public Yellow(PApplet sketch, int x, int y, Map map, String direction, int color1, int color2, int color3) {
		super(sketch, x, y, map, direction, color1, color2, color3);
	}
	
	@Override
	public boolean isValidMove(int x, int y, String direction) {
		// if left -> call up
		// if up -> call right
		// if right -> call down
		// if down -> call left
		
		if (direction == "Left") {
			if (isLegalSquare(x-32, y)) {
				return true;
			}else {
				this.direction = "Up";
				return false;
			}
		}else if (direction == "Up") {
			if (isLegalSquare(x, y-32)) {
				return true;
			}else {
				this.direction = "Right";
			}
		}else if (direction == "Right") {
			if (isLegalSquare(x+32, y)) {
				return true;
			}else {
				this.direction = "Down";
			}
		}else if (direction == "Down") {
			if (isLegalSquare(x, y+32)) {
				return true;
			}else {
				this.direction = "Left";
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
