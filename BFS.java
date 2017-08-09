
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

public class BFS {
	private Hashtable<String, Vertex> closedList;
	
	//Holding the parent to return from the goal to start
	private static Hashtable<String, Vertex> pred;
	private Hashtable<String, Vertex> openedList;
	private Vector<Edge> edges;
	private int size = CAreaMap.getTableSize();
	private static Stack<String> path;

	public BFS() {
		closedList = new Hashtable<String, Vertex>();
		openedList = new Hashtable<String, Vertex>();
		pred = new Hashtable<String, Vertex>();
		path = new Stack<String>();
		try {
			Utilities.writer = new PrintWriter(ex1.out_file);
		} catch (FileNotFoundException e) {
			System.out.println("Wrong path to output file! Please check the path in ex1 class");
			e.printStackTrace();
		}
	};

	public void search(Vertex spCoordinates, Vector<Vertex> goalCoordinates) {
		Utilities.startTime=System.currentTimeMillis();
		Utilities.endTime=Utilities.startTime;
		Queue<Vertex> q = new ArrayBlockingQueue<Vertex>(size * size);
		Vertex vertex = spCoordinates;
		q.add(vertex);
		closedList.put(vertex._name.toString(), vertex);
		openedList.put(vertex._name.toString(), vertex);

		while (!q.isEmpty()) {
			Vertex u = q.poll();
			openedList.remove(u._name.toString());
			//openedList.remove(u._name.toString(), u);
			if (CAreaMap.getChildren(u) != null) {
				edges = new Vector<Edge>(CAreaMap.getChildren(u));
				u.adjacencies = edges;
				Iterator<Edge> iter = edges.iterator();
				while (iter.hasNext()) {
					Vertex v = iter.next().getVertex();
					if ((!openedList.containsKey(v._name.toString()))
							&& (!closedList.containsKey(v._name.toString()))) {
						
						if (pred!=null||Utilities.pred.contains(v) || pred.contains(u._name.toString())) {
							pred.put(v._name.toString(), u);

						} else {
							pred.put(v._name.toString(), u);
						}
						if (Utilities.isGoal(v, goalCoordinates)) {
							Utilities.endTime=System.currentTimeMillis();
							v.adjacencies.add(new Edge(u, 2));
							if (v._name.equals(goalCoordinates.elementAt(0))) {
								ex1.os.println(getPath(spCoordinates, v));
								ex1.os.println(1);
							} else {
								ex1.os.println(getPath(spCoordinates, v));
								ex1.os.println(2);
							}
							
							ex1.os.println("Num: " + Utilities.numberOfNodes);
							ex1.os.println("Cost: "+Utilities.cost);
							ex1.os.println("Time: "+(Utilities.endTime-Utilities.startTime));
							ex1.os.close();

							return;
						}
						v.setEvaluation(0);
						v.setPreviousDirection(u);
						q.add(v);
						Utilities.numberOfNodes++;
						openedList.put(v._name.toString(), v);
					}
				}
			}
			closedList.put(u._name.toString(), u);
			openedList.remove(u._name.toString());
		}

		return;
	}

	public static String getPath(Vertex s, Vertex goal) {
		int weight = 0;
		Vertex parent;
		String str = "";
		while ((pred.get(goal._name.toString()) != null)
				&& (!pred.get(goal._name.toString()).equals(s._name.toString()))) {

			parent = pred.get(goal._name.toString());
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
