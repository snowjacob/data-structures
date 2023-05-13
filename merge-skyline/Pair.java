package assignment1;

public class Pair {
	
	//Author : Jacob Snow
	//Basic Pair object implementation
	
	public Pair() {}
	
	private int y;
	private int x;
	
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public int getFirst() {
		return x;
	}
	
	public int getSecond() {
		return y;
	}
	
	public void printPair() {
		System.out.println("<" + x + ", " + y + ">");
	}
	
	public void setCoordinates(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}

}
