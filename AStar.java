
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;

public class AStar {
	private Hashtable<String, Vertex> closedList;
	private Hashtable<String, Vertex> openedList;
	private static Hashtable<Vertex, Vertex> pred;
	private static int numberOfNodes = 0;
	private PrintWriter writer;
	private Queue<Vertex> q;

	public AStar() {
		Utilities.heuristicTable = new Hashtable<Integer, Vertex>();
		closedList = new Hashtable<String, Vertex>();
		openedList = new Hashtable<String, Vertex>();
		pred = new Hashtable<Vertex, Vertex>();
		q = new PriorityQueue<Vertex>();
		try {
			writer = new PrintWriter(ex1.out_file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			System.out.println("Wrong path to output file! Please check the path in ex1 class");
		}

	}

	
	public void search(Vertex spCoordinates, Vector<Vertex> goalCoordinates) {
		Utilities.startTime=System.currentTimeMillis();

		Vector<Edge> edges;
		Vertex vertex = spCoordinates;
		vertex.minDistance = 0;
		vertex.setEvaluation(0);

		int heuristicValue = Utilities.heuristic_function(spCoordinates, goalCoordinates);
		vertex.setEvaluation(heuristicValue);
		numberOfNodes++;
		q.add(vertex);
		openedList.put(vertex._name.toString(), vertex);

		while (!q.isEmpty()) {
			Vertex u = q.poll();
		
			if (Utilities.isGoal(u, goalCoordinates)) {
				ex1.os.println(getStringPath(spCoordinates, u));
				Utilities.endTime=System.currentTimeMillis();
				if (u._name.equals(goalCoordinates.elementAt(0)._name)) {
					ex1.os.println(1);

				} else {
					ex1.os.println(2);
				}
				ex1.os.println("Num: " + numberOfNodes);
				ex1.os.println("Cost: " + (u.minDistance));
				ex1.os.println("Time: "+(Utilities.endTime-Utilities.startTime));
				ex1.os.close();

				return;
			}
			openedList.remove(u._name.toString());
			closedList.put(u._name.toString(), u);
			if (CAreaMap.getChildren(u) != null) {
				edges = new Vector<Edge>(CAreaMap.getChildren(u));
				Iterator<Edge> iter = edges.iterator();

				while (iter.hasNext()) {
					Edge e = iter.next();
					Vertex v = e.getVertex();
					int weight = e.getWeight();
					if (u.getPreviousDirection() == v.pathFromParentVertex(u)) {
						if (e.getWeight() == 5 || e.getWeight() == 2)
							weight -= 1;
					}
					;

					if ((!openedList.containsKey(v._name.toString()))
							&& (!closedList.containsKey(v._name.toString()))) {
						if (pred.contains(v))
							pred.put(v, u);
						else
							pred.put(v, u);
						int distanceThroughU = weight + u.minDistance;
						if (distanceThroughU < v.minDistance) {
							openedList.remove(v._name.toString());// check
							v.minDistance = distanceThroughU;
							q.remove(v);// check
							openedList.put(v._name.toString(), v);
						}
						v.setEvaluation(Utilities.heuristic_function(v, goalCoordinates));
						v.setPreviousDirection(u);

						
						q.add(v);
						numberOfNodes++;
					}

				}
			}
			closedList.put(u._name.toString(), u);
			openedList.remove(u._name.toString());
		}
		writer.println("no path");
		writer.close();
		return;
	}

	public static String getStringPath(Vertex s, Vertex v) {
		String str = "";
		if (!v._name.equals(s._name)) {
			if (pred.get(v) == null) {
				str = null;
			} else {
				str = str + getStringPath(s, pred.get(v));
				if (pred.get(v).equals(s))
					str += v.pathFromParentVertex(pred.get(v));
				else
					str = str + "-" + v.pathFromParentVertex(pred.get(v));
			}
		}
		return str;
	}

}
