package gamegame;

import java.util.ArrayList;
import java.util.HashMap;

import processing.core.*;
import processing.data.*;

public class Map {
	
	private ArrayList<Square> mapArray;
	
	// Coords are xy -> 3232 -> 6464
	private HashMap<Integer, Square> mapHash;
	private String intermediate;
	private String[] split;
	private Integer hashInt;
	
	public Map(PApplet sketch, ArrayList<String> mapRaw) {
		// Convert to array of squares
		// Also displays the map
		this.mapArray = new ArrayList<Square>();
		this.mapHash = new HashMap<Integer, Square>();
		
		for (String cur : mapRaw) {
			// Add to array to be inited later
			this.mapArray.add(new Square(cur));
			
			// Add to HashMap to deal with replacement
			this.split = cur.split(" ");
			this.intermediate = split[1] + split[2];
			this.hashInt = Integer.parseInt(intermediate);
			this.mapHash.put(hashInt, new Square(cur));
			
		}
		
	}
	
	public int goalX() {
		for (Square cur : this.mapArray) {
			if (cur.getType() == "Goal") {
				return cur.getX();
			}
		}
		return 0;
	}
	
	public int goalY() {
		for (Square cur : this.mapArray) {
			if (cur.getType() == "Goal") {
				return cur.getY();
			}
		}
		return 0;
	}
	
	public void display(PApplet sketch) {
		// Init the squares in map
		for (Square cur : this.mapArray) {
			switch (cur.getType()) {
			case "Goal":
				sketch.fill(100, 0, 0);
				sketch.rect(cur.getX(), cur.getY(), 32, 32);
				break;
			case ("Empty"):
				sketch.fill(0, 255, 0);
				sketch.rect(cur.getX(), cur.getY(), 32, 32);
				break;
			case ("Wall"):
				sketch.fill(0, 0, 0);
				sketch.rect(cur.getX(), cur.getY(), 32, 32);
				break;
			case ("BWall"):
				sketch.fill(60);
				sketch.rect(cur.getX(), cur.getY(), 32, 32);
			}
			cur.BWallNot = false;
		}
	}
	
	public HashMap<Integer, Square> getHashMap() {
		return this.mapHash;
	}

}
