
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

public class DFID {
	private Stack<Vertex> stack;
	private int depth;
	private static int maxDepth = 1;
	private boolean goalFound = false;
	private int numberOfNodes;
	private Vector<Edge> edges;
	private Hashtable<String, Vertex> openedList;
	private static Stack<String> path;

	public DFID() {
		try {
			Utilities.writer = new PrintWriter(ex1.out_file);
		} catch (FileNotFoundException e) {
			System.out.println("Wrong path to output file! Please check the path in ex1 class");
			e.printStackTrace();
		}
	};

	public void search(Vertex spCoordinates, Vector<Vertex> goalCoordinates) {
		Utilities.startTime = System.currentTimeMillis();
		Utilities.endTime = Utilities.startTime;
		depth = 0;
		// String s = "";
		numberOfNodes = CAreaMap.getTableSize() * CAreaMap.getTableSize();
		while (maxDepth < numberOfNodes && !goalFound) {
			depthLimitedSearch(CAreaMap.getSpCoordinates(), CAreaMap.getGoalCoordinates(), maxDepth);
			maxDepth++;
		}
		if (goalFound) {
			return;
		}

		else
			ex1.os.println("not found");
		return;

	}

	private void depthLimitedSearch(Vertex spCoordinates, Vector<Vertex> goalCoordinates, int maxDepth) {
		path = new Stack<String>();
		Utilities.numberOfNodes = 0;
		stack = new Stack<Vertex>();
		Utilities.pred = new Hashtable<String, Vertex>();
		openedList = new Hashtable<String, Vertex>();
		Vertex vertex = spCoordinates;
		stack.push(vertex);
		depth = 0;
		while (!stack.isEmpty() && depth < maxDepth) {
			Vertex u = stack.pop();

			u.setEvaluation(0);
			openedList.put(u._name.toString(), u);
			edges = new Vector<Edge>(CAreaMap.getChildren(u));
			u.adjacencies = edges;
			Iterator<Edge> iterator = edges.iterator();

			while (iterator.hasNext()) {
				Edge e = iterator.next();
				Vertex v = e.getVertex();
				if (!(stack.contains(v)) && (!openedList.containsKey(v._name.toString()))) {
					Utilities.pred.put(v._name.toString(), u);

					openedList.put(v._name.toString(), v);
					if (Utilities.isGoal(v, goalCoordinates)) {
						Utilities.endTime = System.currentTimeMillis();
						// Utilities.pred.put(u._name.toString(), v);
						v.adjacencies.add(new Edge(u, 2));
						ex1.os.println(getPath(spCoordinates, v));

						if (v.equals(goalCoordinates.elementAt(0))) {
							ex1.os.println(1);
							goalFound = true;

						} else {
							ex1.os.println(2);
							goalFound = true;

						}
						ex1.os.println("Num: " + Utilities.numberOfNodes);
						ex1.os.println("Cost: " + Utilities.cost);
						ex1.os.println("Time: " + (Utilities.endTime - Utilities.startTime));

						return;
					}
					if (!stack.contains(v) || !openedList.containsKey(v._name.toString())) {
						v.setEvaluation(0);
						v.setPreviousDirection(u);
						Utilities.numberOfNodes++;
						stack.push(v);
					}

				}

			}
			depth++;
		}
	}

	public static String getPath(Vertex s, Vertex goal) {
		int weight = 0;
		//System.out.println(Utilities.pred.get(goal._name.toString()));
		Vertex parent;
		String str = "";

		while ((Utilities.pred.get(goal._name.toString()) != null)
				&& (!Utilities.pred.get(goal._name.toString()).equals(s._name.toString()))) {

			parent = Utilities.pred.get(goal._name.toString());
			weight = goal.getWeigthBetween(parent);

			if (parent.getPreviousDirection() == goal.pathFromParentVertex(parent)) {
				if (weight == 5 || weight == 2)
					weight -= 1;
			}
			path.push(goal.pathFromParentVertex(parent));
			Utilities.cost += weight;
			goal = parent;
		}
		while (!path.isEmpty()) {

			str += path.pop();
			if (!path.isEmpty())
				str += "-";
		}
		return str;
	}
}
