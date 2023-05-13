package assignment1;

public class Building {
	
	//Author : Jacob Snow
	//Basic Building implementation. I used this A LOTTTT for debugging
	//I really didn't think I would get done on time.
	private Pair leftPart;
	private Pair rightPart;
	
	public Building() {}
		
	public Building(Pair leftPart, Pair rightPart) {
		this.leftPart = leftPart;
		this.rightPart = rightPart;
	}
	
	public Pair getLeft() {
		return leftPart;
	}
	
	public Pair rightPart() {
		return rightPart;
	}
	
	public int getHeight() {
		return leftPart.getSecond();
	}
	
	public int getBeginning() {
		return leftPart.getFirst();
	}
	
	public int getEnd() {
		return rightPart.getFirst();
	}
	
	public void setHight(int h) {
		rightPart.setY(h);
	}
	
	public void setLeft(Pair p) {
		leftPart.setCoordinates(p.getFirst(), p.getSecond());;
	}
	
	public void setRight(Pair p) {
		rightPart.setCoordinates(p.getFirst(), p.getSecond());
	}
	
	public void printBuilding() {
		System.out.println("[(" + leftPart.getFirst() + " , " +  
	leftPart.getSecond() + "), ("+ rightPart.getFirst() + ", " 
				+ rightPart.getSecond() + ")]");
	}
 }
