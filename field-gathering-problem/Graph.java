import java.util.LinkedList;
import java.util.Stack;

public class Graph {
	int numberVertices;
	LinkedList<Edge>[] adjacencyMatr;

	//Initializes graph object
	Graph(int numberVertices) {
		this.numberVertices = numberVertices;
		adjacencyMatr = new LinkedList[numberVertices];

		for (int i = 0; i < numberVertices; i++) {
			adjacencyMatr[i] = new LinkedList<>();
		}
	}

	//prints the graph's connections and weights
	public void printGraph() {
		for (int i = 0; i < numberVertices; i++) {
			LinkedList<Edge> edgeList = adjacencyMatr[i];
			for (int j = 0; j < edgeList.size(); j++) {
				System.out.println("vertex = " + i + ". connection = " + edgeList.get(j).destination + ". weight = "
						+ edgeList.get(j).weight);
			}
		}
	}

	//adds an edge to the graph
	public void addEdge(int source, int destination, int weight) {
		Edge edge = new Edge(source, destination, weight);
		adjacencyMatr[source].addFirst(edge);
	}

	//Topologically sorts the graph recursively. This will run in O(n) time.
	public void topologicalSort(int v, boolean[] visited, Stack<Integer> st) {
		visited[v] = true;
		for (int i = 0; i < adjacencyMatr[v].size(); i++) {
			Edge e = adjacencyMatr[v].get(i);
			if (visited[e.destination] == false) {
				topologicalSort(e.destination, visited, st);
			}
		}
		st.push(v);
	}

	//Finds the best path from the starting vertex. This will run in O(n) time
	public int[] bestPath(int s) {
		Stack<Integer> st = new Stack<Integer>();
		int distance[] = new int[numberVertices];
		boolean[] visited = new boolean[numberVertices];

		for (int i = 0; i < numberVertices; i++) {
			visited[i] = false;
		}

		for (int i = 0; i < numberVertices; i++) {
			if (visited[i] == false) {
				topologicalSort(i, visited, st);
			}
		}

		for (int i = 0; i < numberVertices; i++) {
			distance[i] = Integer.MIN_VALUE;
		}

		distance[s] = 0;
		
		while (st.isEmpty() == false) {
			int u = st.peek();
			st.pop();
			
			if (distance[u] != Integer.MIN_VALUE) {
				for (int i = 0; i < adjacencyMatr[u].size(); i++) {
					Edge e = adjacencyMatr[u].get(i);
					if (distance[e.destination] < distance[u] + e.weight) {
						distance[e.destination] = distance[u] + e.weight;
					}
				}
			}
		}
		
		return distance;
	}
	
}
