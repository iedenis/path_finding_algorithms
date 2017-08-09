/*
 * This class represents coordinates of a vertex
 */
class Point {
	private int _x, _y;

	public Point(int x, int y) {
		this._x = x;
		this._y = y;
	}

	public Point(String _point) {
		String[] split = _point.split("\\W+");
		this._x = (Integer.parseInt(split[1]));
		this._y = (Integer.parseInt(split[2]));
	}

	public boolean equals(Point other) {
		return _x == other._x && _y == other._y;
	}

	public int get_x() {
		return _x;
	}

	public void set_x(int _x) {
		this._x = _x;
	}

	public int get_y() {
		return _y;
	}

	public void set_y(int _y) {
		this._y = _y;
	}

	@Override
	public String toString() {
		return "(" + (this._x) + "," + (this._y) + ")";

	}

	public Point[] parse(String st) {
		return null;
	}
}
