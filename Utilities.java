
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

/* 
 * Class for helping functions such as heuristic function etc.
 */
public class Utilities {
	public static Hashtable<Integer, Vertex> heuristicTable;
	public static int numberOfNodes = 0;
	public static PrintWriter writer;
	public static Hashtable<String, Vertex> pred;
	public static int cost = 0;
	public static long startTime;
	public static long endTime;

	public static boolean isGoal(Vertex u, Vector<Vertex> goalCoordinates) {
		if (u._name.equals(goalCoordinates.elementAt(0)._name) || u._name.equals(goalCoordinates.elementAt(1)._name))
			return true;
		else
			return false;
	}

	public static int heuristic_function(Vertex v, Vector<Vertex> goalCoordinates) {
		int dist_first_goal, dist_second_goal;
		dist_first_goal = Math.max(Math.abs(goalCoordinates.elementAt(0)._name.get_x() - v._name.get_x()),
				Math.abs(goalCoordinates.elementAt(0)._name.get_y() - v._name.get_y()));
		dist_second_goal = Math.max(Math.abs(goalCoordinates.elementAt(1)._name.get_x() - v._name.get_x()),
				Math.abs(goalCoordinates.elementAt(1)._name.get_y() - v._name.get_y()));
		if (dist_first_goal < dist_second_goal)
			return dist_first_goal;
		else if (dist_second_goal < dist_first_goal)
			return dist_second_goal;
		else {
			if ((Math.abs(goalCoordinates.elementAt(0)._name.get_x() - v._name.get_x())) < Math
					.abs(goalCoordinates.elementAt(1)._name.get_x() - v._name.get_x()))
				return dist_first_goal;
			else
				return dist_second_goal;
		}
	}

	public Vertex minHeuristicEvaluation(Vector<Edge> edges, Vector<Vertex> goalCoordinates) {
		int min = Integer.MAX_VALUE;
		for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
			Edge edge = (Edge) iterator.next();
			Vertex v = edge.getVertex();
			int evaluation = heuristic_function(v, goalCoordinates);
			heuristicTable.put(evaluation, v);
			if (evaluation < min) {
				min = evaluation;
			}
		}
		return heuristicTable.get(min);
	}

	public static int getWeightBetween(Vertex parent, Vertex child) {
		return 0;
	}

	// For BFS and DFID using only
	public static String getStringPath(Vertex s, Vertex v) {
		String str = "";
		if (!v._name.equals(s._name)) {
			if (pred.get(v) == null) {
				str = null;
			} else {
				str = str + getStringPath(s, pred.get(v));
				if (pred.get(v).equals(s)) {
					cost = 0;
					str = str + v.pathFromParentVertex(pred.get(v)) + "-";
				} else {

					for (Iterator<Edge> iterator = pred.get(v).adjacencies.iterator(); iterator.hasNext();) {
						Edge e = iterator.next();
						if (e.getVertex().equals(v)) {
							cost += e.getWeight();
							System.out.println(cost);
						}
					}
					for (Iterator<Edge> iterator = pred.get(v).adjacencies.iterator(); iterator.hasNext();) {
						Edge e = iterator.next();
						if (e.getVertex().equals(v)) {
							cost += e.getWeight();
							System.out.println(cost);
						}
					}
					str = str + v.pathFromParentVertex(pred.get(v)) + "-";
				}
			}
		}
		return str;
	}
}
