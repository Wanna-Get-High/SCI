package Mvc.Models;

public class Direction {
	
	private int x;
	private int y;
	
	public Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void reverseDirections() {
		this.reverseXDirection();
		this.reverseYDirection();
	}
	
	public void reverseXDirection() { this.x = -this.x; }
	
	public void reverseYDirection() { this.y = -this.y; }
	
	public int x() { return x; }
	
	public int y() { return y; }

	public void x(int x) { this.x = x; }
	
	public void y(int y) { this.y = y; }
}
