package gamegame;

import java.util.ArrayList;

import processing.core.*;
import processing.data.*;

public class Game extends PApplet {
	
	boolean left, right, up, down, moved, released;
	private ArrayList<Player> players = new ArrayList<Player>();
	private ArrayList<Map> maps = new ArrayList<Map>();
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	private ArrayList<Bomb> bombs = new ArrayList<Bomb>();
	int frames;
	int lives;
	int level;
	int maxLevel;
	boolean space;
	ArrayList<Square> dangerSquares;
	
	public void initMapsArray() {
		// Init maps in maps array
		// Test map, should be read from file
		// -------------------------------------
		ArrayList<String> map1 = new ArrayList<String>();
		ArrayList<String> map2 = new ArrayList<String>();
		
		int i = 0;
		int j;
		while (i < 15) {
			j = 0;
			while (j < 15) {
				if (i == 10 && j == 10) {
					map1.add("Goal " + (32*j) + " " + (32*i));
					j++;
					continue;
				}
				map1.add("Empty " + (32*j) + " " + (32*i));
				j++;
			}
			i++;
		}
		
		i = 0;
		while (i < 15) {
			j = 0;
			while (j < 15) {
				if (i == 11 && j == 11) {
					map2.add("Goal " + (32*j) + " " + (32*i));
					j++;
					continue;
				}
				if (i == 0 || j == 0) {
					map2.add("BWall " + (32*j) + " " + (32*i));
				}else {
					map2.add("Empty " + (32*j) + " " + (32*i));
				}
				j++;
			}
			i++;
		}
		
		maps.add(new Map(this, map1));
		maps.add(new Map(this, map2));
		maxLevel = 2;
	}
	
	public void initEnemies() {
		// TODO - init correct coords and to correct map
		// Init all enemies to correct map and add spawn method
		enemies.add(new Yellow(this, (5*32), (5*32), maps.get(level), "Up", 255, 204, 0));
		enemies.add(new Red(this, (8*32), (8*32), maps.get(level + 1), "Down", 255, 0, 0));
	}
	
	public void initPlayers() {
		// TODO - init at given coords
		// Init one player to each map
		for (Map cur : maps) {
			players.add(new Player(this, 64, 64, cur));
		}
		//change indexing of player to level index and use lives index as end of game
	}
	
	public void settings() {
		this.size(480, 480);
	}
	
	public void setup() {
		// Init lives
		lives = 3;
		// Init level
		level = 0;
		// Init movement
		left = false;
		right = false;
		up = false;
		down = false;
		moved = false;
		released = true;
		// Init frames
		frames = 0;
		// Init maps array
		initMapsArray();
		// Init players array
		initPlayers();
		// Init enemies array
		initEnemies();
		// Init map1
		maps.get(level).display(this);
	}
	
	public void draw(){
		// Win condition -> next level logic
		if (players.get(level).x == maps.get(level).goalX() && players.get(level).y == maps.get(level).goalY()) {
			nextLevel();
			maps.get(level).display(this);
		}
		
		// Bomb Guy
		if (lives == 0) {
			// you lose logic
			// TODO
		}else {
			// Drop bomb logic
			if (space && released) {
				space = false;
				bombs.add(new Bomb(this, players.get(level).getX(), players.get(level).getY(), maps.get(level)));
				bombs.get(bombs.size() - 1).displayDropped(this);
			}
			
			// Remove exploded bomb after 30 frames logic
			for (Bomb cur : bombs) {
				// Explode bomb logic after 120 frames
				if (!cur.exploded && cur.map == maps.get(level)) {
					cur.timerFrames -= 1;
					cur.displayDropped(this);
					
					if (cur.timerFrames == 0 && cur.map == maps.get(level)) {
						cur.explode(this);
						cur.exploded = true;
					}
				}
				
				if (cur.exploded && cur.map == maps.get(level)) {
					
					// Check contact with other objects
					dangerSquares = cur.getDangerSquares();
					cur.myDanger = dangerSquares;
					for (Square sqr : dangerSquares) {
						// Handles player contact -> reset level
						if (players.get(level).getX() == sqr.getX() && players.get(level).getY() == sqr.getY()) {
							// This resets enemies
							for (Enemy toReset : enemies) {
								if (toReset.map == maps.get(level)) {
									toReset.resetEnemy();
								}
							}
							// This resets player
							players.get(level).resetPlayer();
							lives -= 1;
							
							maps.get(level).display(this);
							break;	
						}
						// Handle enemies contact
						for (Enemy en : enemies) {
							if (en.map == maps.get(level) && en.x == sqr.getX() && en.y == sqr.getY()) {
								//TODO
							}
						}
					}
				}
			}
			// handle clean up logic
			int i = 0;
				while (i < bombs.size()) {
					if (bombs.get(i).exploded) {
						bombs.get(i).timerAfter -= 1;
						if (bombs.get(i).timerAfter == 0) {
							bombs.get(i).cleanUp(this);
							bombs.remove(i);
						}
					}
					i ++;
			}
			
			// Move player logic
			players.get(level).update(left, up, right, down);
			players.get(level).display(moved);
			
			for (Enemy cur : enemies) {
				if (cur.x == players.get(level).x && cur.y == players.get(level).y && cur.map == maps.get(level)) {
					// Reset the level logic:
					
					// This resets enemies
					for (Enemy toReset : enemies) {
						if (toReset.map == maps.get(level)) {
							toReset.resetEnemy();
						}
					}
					// This resets player
					players.get(level).resetPlayer();
					lives -= 1;
					
					// reset map
					maps.get(level).display(this);
					break;
				}	
			}	
		}
		// Enemies
		frames += 1;
		if (frames == 60) {
			frames = 0;
			for (Enemy cur : enemies) {
				if (cur.map == maps.get(level)) {
					cur.update();
					cur.display(true);
				}
			}
		}
}
	
	public void nextLevel() {
		level += 1;
		if (level == maxLevel) {
			//TODO
			// win screen
		}
	}
	
	public void keyPressed() {
		if (!this.released) {
			return;
		}
		
		switch (keyCode) {
		case 37:
			left = true;
			released = false;
			break;
		case 38:
			up = true;
			released = false;
			break;
		case 39:
			right = true;
			released = false;
			break;
		case 40:
			down = true;
			released = false;
			break;
		case 32:
			space = true;
			released = false;
		default:
			/*
			fill(0);
			textSize(50);
			text(keyCode, 100, 50);
			*/
		}
		
		if (left || up || right || down) {
			moved = true;
		}
		
		if (lives == 0) {
			// You lose, but for simplicity just pass
		}else {
			players.get(level).update(left, up, right, down);
			players.get(level).display(moved);
		}
		// Make sure only 1 move is made
		left = false;
		up = false;
		right = false;
		down = false;
	}
	
	public void keyReleased() {
		if (keyCode == 37 || keyCode == 38 || keyCode == 39 || keyCode == 40 || keyCode == 32) {
			released = true;
		}else {
			released = false;
		}
	}
		
	public static void main(String[] args) {
		String[] processingArgs = {"Game"};
		Game mySketch = new Game();
		PApplet.runSketch(processingArgs, mySketch);

	}

}
