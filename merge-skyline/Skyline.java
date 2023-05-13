package assignment1;

import java.util.*;

public class Skyline {
	
	/* Author : Jacob Snow
	 * Class : CSCI 405
	 * Programming Assignment 1
	 * 
	 */

	public static void main(String[] args) {
		List<int[]> buildings = new ArrayList<int[]>();
		Scanner sc = new Scanner(System.in);
		System.out.println("Please enter a list of buildings in the form <l h r><l h r>...");

		String input = sc.nextLine();

		//This statement separates the input into arrays that we can
		//actually work with. Each array is of size 3 and contains the elements
		//l, h, and r.
		boolean flag = true;
		while (flag) {
			int[] g = new int[3];
			String subset = input.substring(input.indexOf("<") + 1, input.indexOf(">"));
			int l = Integer.parseInt(subset.substring(0, subset.indexOf(" ")));
			String subsetOfSubset = subset.substring(subset.indexOf(" ") + 1);
			int h = Integer.parseInt(subsetOfSubset.substring(0, subsetOfSubset.indexOf(" ")));
			String finalSubset = subsetOfSubset.substring(subsetOfSubset.indexOf(" ") + 1);
			int r = Integer.parseInt(finalSubset);
			g[0] = l;
			g[1] = h;
			g[2] = r;
			buildings.add(g);
			input = input.substring(input.indexOf(">") + 1);
			if (input.length() < 1) {
				flag = false;
			}
		}
		
		//Outputs the skyline
		List<Pair> skyline = findSkyline(buildings);
		for (Pair building : skyline) {
			System.out.print("<" + building.getFirst() + " " + building.getSecond()+ ">");
		}

		sc.close();

	}

	//Separates array into building objects and makes its call to skylineSort
	public static List<Pair> findSkyline(List<int[]> arrList) {
		List<Building> buildings = new ArrayList<Building>();
		// For each building in our list, we split it up into pairs
		// This will run in O(n) time
		for (int[] building : arrList) {
			buildings.add(new Building(makePair(building[0], building[1]), makePair(building[2], 0)));
		}
		
		int low = 0;
		int high = buildings.size() - 1;

		return skylineSort(buildings, low, high);

	}

	//skylineSort takes in a parameter of building objects, the low index, and
	//the high index.
	//skylineSort makes recursive calls to itself in order to divide the problem
	//into n/2 sub problems. Once it is broken down to its smallest size, it
	//creates a new skyline result list made of the critical points of the buildings
	//and then makes calls to mergeSkyline.
	public static List<Pair> skylineSort(List<Building> buildings, int low, int high) {
		if (high - low < 1) {
			//In the base case, we initialize our skyline pair and separate
			//the building into 2 (x, y) pairs, consisting of (l, h) and (r, 0)
			List<Pair> res = new ArrayList<Pair>();
			
			Pair curBuilding = new Pair();
			curBuilding.setX(buildings.get(low).getLeft().getFirst());
			curBuilding.setY(buildings.get(low).getHeight());
			res.add(curBuilding);
			
			curBuilding = new Pair();
			curBuilding.setX(buildings.get(low).rightPart().getFirst());
			curBuilding.setY(0);
			res.add(curBuilding);
			
			return res;
		} else {
			int mid = (high + low) / 2;
			
			List<Pair> A = skylineSort(buildings, low, mid);
			List<Pair> B = skylineSort(buildings, mid + 1, high);
			
			List<Pair> res = mergeSkyline(A, B);
			
			return res;
		}
	}
	
	//This takes the current list of results and a building and makes sure
	//it can be added to the list. If the buildings are stacked, then it will just
	//change the y-coordinate to the higher of the two.
	
	public static void addBuilding(List<Pair> A, Pair curBuilding) {
		int size = A.size();
		//We're at the same x, but we went higher, so we'll update the height
		if(size > 0 && A.get(size - 1).getFirst() == curBuilding.getFirst()) {
			A.get(size - 1).setY(Math.max(A.get(size - 1).getSecond(), curBuilding.getSecond()));
			return;
		}
		
		//we just looked at that pair so we can just return
		if(size > 0 && A.get(size - 1).getSecond() == curBuilding.getSecond()) {
			return;
		}
		
		//The pair is special
		A.add(curBuilding);
	}
	
	//mergeSkyline takes in two lists of pairs and merge them skyline-style.
	//***************************************************************************//
	//This means taking two (x, y) pairs, and adding the closest x coordinate
	//first to the skyline. Then, adding its height to the skyline
	//if it is greater than the current height.
	//If the two (x, y) pairs have the same x, we'll take the greater y.
	//If the algorithm has reached the end, it means that there are
	//still pairs in either A or B that do not have equal x coordinates, 
	//and we should thus add the rest of the pairs to the skyline.
	
	public static List<Pair> mergeSkyline(List<Pair> A, List<Pair> B) {
		Pair curBuilding;
		List<Pair> skyline = new ArrayList<Pair>();
		int height1 = 0;
		int height2 = 0;
		int i = 0;
		int j = 0;
		
		while (i < A.size() && j < B.size()) {
			if (A.get(i).getFirst() < B.get(j).getFirst()) {
				//If x1 < x2, we should take the pair for x1 first
				height1 = A.get(i).getSecond();
				curBuilding = new Pair();
				curBuilding.setX(A.get(i).getFirst());
				curBuilding.setY(Math.max(height1, height2));
				A.remove(i);
				addBuilding(skyline, curBuilding);
			} else if(A.get(i).getFirst() > B.get(j).getFirst()){
				//If x1 > x2, and we should take the pair of x2 first
				height2 = B.get(j).getSecond();
				
				curBuilding = new Pair();
				curBuilding.setX(B.get(j).getFirst());
				curBuilding.setY(Math.max(height1, height2));
				B.remove(j);
				addBuilding(skyline, curBuilding);
			} else {
				//If x1 == x2 i.e. If they're stacked
				height1 = A.get(i).getSecond();
				height2 = B.get(j).getSecond();
				curBuilding = new Pair();
				curBuilding.setX(A.get(i).getFirst());
				curBuilding.setY(Math.max(height1, height2));

				addBuilding(skyline, curBuilding);
				A.remove(i);
				B.remove(j);
			}
		}
		
		while (i < A.size()) {
			skyline.add(A.get(i));
			i++;
		}

		while (j < B.size()) {
			skyline.add(B.get(j));
			j++;
		}
		
		return skyline;

	}
	
	//Makes a Pair object
	public static Pair makePair(int x, int y) {
		Pair p = new Pair(x, y);
		return p;
	}

}
