package gamegame;

public class Square {
	
	private String type;
	private String typeRaw;
	private int x;
	private int y;
	private String[] split;
	boolean BWallNot;
	
	public Square(String raw) {
		this.split = raw.split(" ");
		this.x = Integer.parseInt(split[1]);
		this.y = Integer.parseInt(split[2]);
		this.typeRaw = split[0];
		this.BWallNot = false;
		
		switch (typeRaw) {
		case "Goal":
			this.type = "Goal";
			break;
		case "Empty":
			this.type = "Empty";
			break;
		case "BWall":
			this.type = "BWall";
			break;
		case "Wall":
			this.type = "Wall";
			break;
		default:
			this.type = "Invalid";
			break;
		}
	}
	
	public String getType() {
		return this.type;
	}
	
	public int getX() {
		return this.x;
	}

	public int getY() {
		return this.y;
	}
}
