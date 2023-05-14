import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/*
 * AUTHOR : Jacob Snow
 * Specification: Find an optimal path through a field of truffles represented by a 2-D array
 * Imagine there is a field of objects that you may only traverse once. From top to bottom,
 * you must maximize the numer of objects obtained. Once you get an object say from row one,
 * you may only move down-left, down-right, or down. The following field yields 9 truffles.
 * [1	2	3]
 * [2	4	2]
 * [2	1	1]
 */

public class TrufflePath {
	public static void main(String[] args) throws FileNotFoundException {
		
		//Parses file input using scanner, tab characters, and substring methods
		String fileInput = args[0];

		File file = new File(fileInput);
		Scanner reader = new Scanner(file);
		String input = "";
		boolean onChar = true;
		List<List<Integer>> map = new ArrayList<List<Integer>>();

		while (reader.hasNextLine()) {
			input = reader.nextLine();
			List<Integer> row = new ArrayList<Integer>();
			onChar = true;
			while (input.length() > 0) {
				if (onChar) {
					if (input.contains("\t")) {
						row.add(Integer.parseInt(input.substring(0, input.indexOf("\t"))));
						input = input.substring(input.indexOf("\t"));
					} else {
						row.add(Integer.parseInt(input));
						input = "";
					}
					onChar = false;
				} else {
					input = input.substring(1);
					onChar = true;
				}
			}
			map.add(row);
		}
		reader.close();

		// Now we have an arrayList of arrayLists of type integer containing the map.
		// Now we get to implement the graph. Details regarding the implementation
		// are featured in the analysis. This will run in O(n) time
		// Specifically, each index in the map will be set to a vertex, and the vertex
		// will contain edges equal to the possible truffle outcome for going along the
		// edge.

		int lastNode = map.size() * map.get(0).size() + 1;

		// Allocates a graph object and adds edges from the starting point to all
		// available spots with truffles in the first row of the map
		Graph truffleMap = new Graph(map.size() * map.get(0).size() + 2);
		for (int i = 1; i < map.get(0).size() + 1; i++) {
			truffleMap.addEdge(0, i, map.get(0).get(i - 1));
		}

		// Assigns edges for the rest of the spots in the map, then
		// assigns edges from the last row of nodes to the exit node.
		int k = 1;
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(i).size(); j++) {
				if (k > lastNode - map.get(i).size() - 1) {
					truffleMap.addEdge(k, lastNode, 0);
					k++;
				} else {
					truffleMap.addEdge(k, k + map.get(i).size(), map.get(i + 1).get(j));
					if(map.get(i).size() < 2) {
						k++;
					}
					if (j == 0 && map.get(i).size() > 1) {
						truffleMap.addEdge(k, k + map.get(i).size() + 1, map.get(i + 1).get(j + 1));
						k++;
					} else if (j == map.get(0).size() - 1) {
						if (map.get(i).size() > 1) {
							truffleMap.addEdge(k, k + map.get(i).size() - 1, map.get(i + 1).get(j - 1));
							k++;
						}
					} else {
						if (map.get(i).size() > 1) {
							truffleMap.addEdge(k, k + map.get(i).size() - 1, map.get(i + 1).get(j - 1));
							truffleMap.addEdge(k, k + map.get(i).size() + 1, map.get(i + 1).get(j + 1));
							k++;
						}
					}
				}
			}
		}

		// Great! Now we have a formal, graphed, truffleMap. Now we will use
		// Maximum edge traversal via Topological Sort!
		int[] bestPath = truffleMap.bestPath(0);
		int[][] collectionMap = new int[map.size()][map.get(0).size()];

		int l = 1;
		for (int i = 0; i < map.size(); i++) {
			for (int j = 0; j < map.get(0).size(); j++) {
				collectionMap[i][j] = bestPath[l];
				l++;
			}
		}

		// Okay now we have a two-dimensional array representing the truffle outcome
		// for each possible spot we may land on. We now traverse this backwards
		// greedily

		int[][] choices = new int[map.size()][2];
		int[] values = new int[map.size()];
		for (int i = 0; i < choices.length; i++) {
			choices[i][0] = i;
		}

		int maxSeen = 0;
		int beg = 1;
		int end = 0;

		//Finds the largest part of the last row and gives us the
		//range of where to start
		for (int i = 0; i < collectionMap[0].length; i++) {
			if (collectionMap[collectionMap.length - 1][i] > maxSeen) {
				maxSeen = collectionMap[collectionMap.length - 1][i];
				choices[collectionMap.length - 1][1] = i;
				values[collectionMap.length - 1] = map.get(map.size() - 1).get(i);
				if (i == 0) {
					beg = 0;
					end = 1;
				} else if (i == collectionMap.length - 2) {
					beg = collectionMap.length - 3;
					end = collectionMap.length - 2;
				} else {
					beg = i - 1;
					end = i + 1;
				}
			}
		}
		
		//Traverses the collection map backwards, starting with the second
		//to last row (because these rows aren't connected to the final node)
		int[] choice;
		for (int i = collectionMap.length - 2; i > -1; i--) {
			choice = bestChoice(map.get(i), collectionMap[i], beg, end);
			choices[i][1] = choice[1];
			values[i] = choice[0];
			if (choice[1] == 0) {
				beg = 0;
				end = 1;
			} else if (choice[1] == collectionMap.length - 2) {
				beg = collectionMap.length - 3;
				end = collectionMap.length - 2;
			} else {
				beg = choice[1] - 1;
				end = choice[1] + 1;
			}
		}
		
		//Prints output
		for (int i = 0; i < values.length; i++) {
			System.out.println(arrToStringPlusOne(choices[i]) + " - " + values[i]);
		}
		System.out.println("Total Truffles : " + bestPath[bestPath.length - 1]);
	}

	// Prints the map represented by a list of lists of integers
	public static void printMap(List<List<Integer>> map) {
		for (int i = 0; i < map.size(); i++) {
			System.out.print(map.get(i));
		}
	}

	//finds the best choice and in a row and its index given a beginning and ending index
	public static int[] bestChoice(List<Integer> mapSegment, int[] arr, int beg, int end) {
		int[] choice = new int[2];
		int maxSoFar = 0;
		if(mapSegment.size() < 2) {
			beg = 0;
			end = 0;
		} else if (mapSegment.size() < 3) {
			beg = 0; 
			end = 1;
		}
		for (int i = beg; i <= end; i++) {
			if (arr[i] > maxSoFar) {
				maxSoFar = arr[i];
				choice[0] = mapSegment.get(i);
				choice[1] = i;
			}
		}
		return choice;
	}

	//returns string representation of an array and adds 1 for output requirements
	public static String arrToStringPlusOne(int[] arr) {
		String res = "[";
		for (int i = 0; i < arr.length; i++) {
			if (i != arr.length - 1) {
				res += (arr[i] + 1) + ",";
			} else {
				res += (arr[i] + 1) + "]";
			}
		}
		return res;
	}
}
