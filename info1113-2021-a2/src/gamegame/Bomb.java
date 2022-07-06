package gamegame;


import java.util.ArrayList;

import processing.core.*;
import processing.data.*;

public class Bomb {
	
	int x;
	int y;
	int h;
	int w;
	int timerFrames;
	int timerAfter;
	Map map;
	boolean exploded;
	ArrayList<Square> myDanger;
	
	public Bomb(PApplet sketch, int x, int y, Map map) {
		this.x = x;
		this.y = y;
		this.w = 32;
		this.h = 32;
		this.map = map;
		this.timerFrames = 120;
		this.timerAfter = 30;
		this.exploded = false;
		this.myDanger = null;
	}
	
	public void displayDropped(PApplet sketch) {
		sketch.fill(204, 102, 0);
		sketch.rect(this.x, this.y, this.h, this.w);
	}
	
	public void explode(PApplet sketch) {
		//TODO -> dont go passed walls!
		sketch.fill(204, 102, 0);
		int i = 0;
		while (i < 3) {
			if (isLegalSquare(this.x + (i*this.w), this.y)) {
				sketch.rect(this.x + (i*this.w), this.y, this.w, this.h);
			}
			if (isLegalSquare(this.x - (i*this.w), this.y)) {
				sketch.rect(this.x - (i*this.w), this.y, this.w, this.h);
			}
			if (isLegalSquare(this.x , this.y + (i*this.h))) {
				sketch.rect(this.x, this.y + (i*this.h), this.w, this.h);
			}
			if (isLegalSquare(this.x , this.y - (i*this.h))) {
				sketch.rect(this.x, this.y - (i*this.h), this.w, this.h);
			}
			i++;
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
			return true;
		default:
			return true;
		}

	}
	
	public void cleanUp(PApplet sketch) {
		for (Square cur : myDanger) {
			resetSquare(this.map, cur.getX(), cur.getY(), sketch);
		}
	}
	
	public void resetSquare(Map map, int x, int y, PApplet sketch) {
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
			// make empty
			toReset.BWallNot = true;
			sketch.fill(0, 255, 0);
			sketch.rect(toReset.getX(), toReset.getY(), 32, 32);
		}
	}
	
	public ArrayList<Square> getDangerSquares(){
		// TODO dont detect sqares passed walls!
		
		ArrayList<Square> danger = new ArrayList<Square>();
		int i = 0;
		while (i < 3) {
			if (isLegalSquare(this.x + (i*this.w), this.y)) {
				danger.add(new Square("Ex " + (this.x + (i*this.w)) + " " + this.y));
			}
			if (isLegalSquare(this.x - (i*this.w), this.y)) {
				danger.add(new Square("Ex " + (this.x - (i*this.w)) + " " + this.y));
			}
			if (isLegalSquare(this.x , this.y + (i*this.h))) {
				danger.add(new Square("Ex " + this.x + " " + (this.y + (this.h*i))));
			}
			if (isLegalSquare(this.x , this.y - (i*this.h))) {
				danger.add(new Square("Ex " + this.x + " " + (this.y - (this.h*i))));
			}
			i++;
		}
		return danger;
	}
}
