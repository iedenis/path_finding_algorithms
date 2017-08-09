/*
 * The class represents edge between two vertices. On the left side is the current vertex(this)
 * and on the other side connected vertex with weight
 */
public class Edge {
	private final Vertex _vertex;
	private  int _weight;

	public Edge(Vertex v, int w) {
		_vertex = v;
		_weight = w;
	}
	@Override
	public String toString(){
		return this._vertex._name+"["+this._weight+"]";
	}
	public Vertex getVertex(){
		return this._vertex;
	}
	public int getWeight(){
		return _weight;
	}
	public void setWeight(int w){
		this._weight=w;
	}
}
