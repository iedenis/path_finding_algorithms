
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class IDAStar {

	// private static int threshold;
	private static boolean goalFound = false;
	static int minCost = Integer.MAX_VALUE;
	//_path is for printing the path to the goal
	private static Stack<Vertex> _path;
	private static int goal;

	public IDAStar() {
		_path = new Stack<Vertex>();
		try {
			Utilities.writer = new PrintWriter(ex1.out_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Wrong path to output file!");
		}
	}

	public String search(Vertex spCoordinates, Vector<Vertex> goalCoordinates) {
		Utilities.startTime = System.currentTimeMillis();
		String s = "";
		spCoordinates.minDistance = 0;
		int threshold = Utilities.heuristic_function(spCoordinates, goalCoordinates);
		while (!goalFound) {
			minCost = iterativeSearch(spCoordinates, 0, threshold, "");

			if (goalFound) {
				_path.push(spCoordinates);
				while (!_path.isEmpty()) {
					Vertex u = _path.pop();
					if (!_path.isEmpty()) {
						Vertex v = _path.peek();
						Iterator<Vertex> it = _path.iterator();
						it.next();
						if (!it.hasNext()) {
							ex1.os.println(v.pathFromParentVertex(u));
						} else {
							ex1.os.print(v.pathFromParentVertex(u) + "-");
						}
					}
				}
				ex1.os.println(goal);
				ex1.os.println("Num: " + Utilities.numberOfNodes);
				ex1.os.println("Cost: " + minCost);
				ex1.os.println("Time: " + (Utilities.endTime - Utilities.startTime));
				ex1.os.close();

				return s += minCost;
			} else if (threshold > 37)
				return "Not found";
			else
			threshold = minCost;
		}

		return s;
	}

	private static int iterativeSearch(Vertex v, int cost, int threshold, String previousDirection) {
		Utilities.numberOfNodes =0;
		Vector<Edge> edges;
		int f = cost + Utilities.heuristic_function(v, CAreaMap.getGoalCoordinates());
		if (f > threshold)
			return f;

		int min = Integer.MAX_VALUE;
		if (CAreaMap.getChildren(v) != null) {
			edges = new Vector<Edge>(CAreaMap.getChildren(v));

			Iterator<Edge> iter = edges.iterator();

			while (iter.hasNext()) {
				Edge e = iter.next();
				Vertex u = e.getVertex();
				int weight = e.getWeight();
				String currentDirection = v.pathFromParentVertex(u);
				weight += (previousDirection.equals(currentDirection) ? -1 : 0);
				int distanceThroughU = weight + v.minDistance;
				if (distanceThroughU < u.minDistance)
					u.minDistance = distanceThroughU;

				minCost = iterativeSearch(u, cost + weight, threshold, currentDirection);
				Utilities.numberOfNodes++;
				if (Utilities.isGoal(u, CAreaMap.getGoalCoordinates()) || goalFound) {
					Utilities.endTime = System.currentTimeMillis();
					_path.push(u);
					if (u._name.equals(CAreaMap.getGoalCoordinates().elementAt(0)._name)) {
						goal = 1;
					} else
						goal = 2;

					if (!goalFound) {
						goalFound = true;
						return u.minDistance;
					}
					return minCost;
				}
				if (minCost < min)
					min = minCost;
				if (min == Integer.MAX_VALUE)
					return minCost;
			}
		}
		return minCost;
	}
}
