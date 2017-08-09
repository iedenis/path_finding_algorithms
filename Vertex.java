
import java.util.*;
/*
 * This class represents Vertex
 * Each vertex has member a vector of edges (vertices that it connected with them)
 */
public class Vertex implements Comparable<Vertex> {

	public Point _name;
	// public final String name;
	public Vector<Edge> adjacencies;
	public int minDistance = Integer.MAX_VALUE;
	public Vertex previous;
	private int _evaluation = Integer.MAX_VALUE;
	private String previousDirection;

	public Vertex(String argName) {
		_name = new Point(argName);
		adjacencies = new Vector<Edge>();
		previousDirection = "";
	}

	public void setEvaluation(int evaluation) {
		this._evaluation = evaluation;
	}

	public int getEvaluation() {
		return _evaluation;
	}

	public Vertex(Point p) {
		_name = p;
		adjacencies = new Vector<Edge>();
	}

	public String toString() {
		String s = this._name.toString();
		if (adjacencies == null)
			return s;
		for (Iterator<Edge> iterator = adjacencies.iterator(); iterator.hasNext();) {
			Edge edge = (Edge) iterator.next();
			s += edge.toString() + "--->";
		}

		return s;
	}

	public void setPreviousDirection(Vertex v) {
		this.previousDirection = this.pathFromParentVertex(v);
	}

	public String getPreviousDirection() {
		return this.previousDirection;
	}

	public String pathFromParentVertex(Vertex parent) {
		String s = "";
		if (this._name.equals(parent._name))
			return "";
		if ((this._name.get_y() == parent._name.get_y()) && (this._name.get_x() < parent._name.get_x()))
			s = "L";
		if ((this._name.get_y() < parent._name.get_y()) && (this._name.get_x() < parent._name.get_x()))
			s = "LU";
		if ((this._name.get_y() < parent._name.get_y()) && (this._name.get_x() == parent._name.get_x()))
			s = "U";
		if ((this._name.get_y() < parent._name.get_y()) && (this._name.get_x() > parent._name.get_x()))
			s = "RU";
		if ((this._name.get_y() == parent._name.get_y()) && (this._name.get_x() > parent._name.get_x()))
			s = "R";
		if ((this._name.get_y() > parent._name.get_y()) && (this._name.get_x() > parent._name.get_x()))
			s = "RD";
		if ((this._name.get_y() > parent._name.get_y()) && (this._name.get_x() == parent._name.get_x()))
			s = "D";
		if ((this._name.get_y() > parent._name.get_y()) && (this._name.get_x() < parent._name.get_x()))
			s = "LD";
		return s;
	}

	public int getWeigthBetween(Vertex parent) {
		int weight = 0;
		Iterator<Edge> it = parent.adjacencies.iterator();
		while (it.hasNext()) {
			Edge e = (Edge) it.next();
			if (e.getVertex()._name.equals(this._name))
				weight = e.getWeight();
		}
		return weight;
	}

	public boolean equals(Vertex other) {
		return ((this._evaluation + this.minDistance) == (other._evaluation + other.minDistance));
	}

	@Override
	public int compareTo(Vertex other) {
		return Integer.valueOf(minDistance + _evaluation).compareTo(other.minDistance + other._evaluation);
		//return Integer.compare(minDistance + _evaluation, other.minDistance + other._evaluation);
	}
}
