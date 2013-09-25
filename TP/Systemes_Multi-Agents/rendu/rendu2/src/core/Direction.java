package core;

public class Direction {
	
	private int x;
	private int y;
	
	public Direction(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public Direction() {
		this(0,0);
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

	public void getRandomDirection() {
		
		this.x = (int)(Math.random()*3)-1;
		this.y = (int)(Math.random()*3)-1;
		
		while(this.x == 0 && this.y == 0 ) {
			this.x = (int)(Math.random()*3)-1;
			this.y = (int)(Math.random()*3)-1;
		}
	}

	public void getDifferentRandomDirection() {
		int previousX = this.x;
		int previousY = this.y;
		
		this.x = (int)(Math.random()*3)-1;
		this.y = (int)(Math.random()*3)-1;
		
		while (this.x == previousX && this.y == previousY ) {
			this.x = (int)(Math.random()*3)-1;
			this.y = (int)(Math.random()*3)-1;
		}
	}
}
