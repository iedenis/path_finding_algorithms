
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CAreaMap {
	private static String in_file = null;
	private static int algorithm_type;
	private static int table_size;
	// private static int [][]wallCoordinates;
	private static Vertex sp_coordinates; // coordinates of the starting point
	private static Vector<Vertex> goalCoordinates;
	private static char[][] inputMatrix;
	private static int[][] areaMap;
	private static LinkedList<Vertex> list;
	private final static int wall = Integer.MIN_VALUE;

	static void help() {
		System.out.println("Input file not found! Please check the path to the file in ex1 class");
	}

	public CAreaMap() {
		try {
			if (System.getProperty("os.name").equals("Linux"))
				in_file = System.getProperty("user.dir") + "/input.txt";
			else if (System.getProperty("os.name").equals("Windows"))
				in_file = System.getProperty("user.dir") + "\\input.txt";
			else
				System.out.println("Unknown operating system");
			// in_file = System.getProperty("user.dir") + "/src/" + "input.txt";
			FileReader fr = new FileReader(in_file);
			BufferedReader br = new BufferedReader(fr);
			try {
				StringBuilder sb = new StringBuilder();
				String line = br.readLine();
				if (line == null)
					throw new IOException("The file is empty or didn't find the file");
				else {
					goalCoordinates = new Vector<Vertex>();
					sb.append(line);
					sb.append(System.getProperty("line.separator"));

					algorithm_type = Integer.parseInt(line);
					line = br.readLine();
					table_size = Integer.parseInt(line);
					line = br.readLine();
					sp_coordinates = new Vertex(new Point(line));
					// System.out.println(sp_coordinates);
					line = br.readLine();
					String firstGoal = line.substring(0, 5);
					goalCoordinates.add(new Vertex(new Point(firstGoal)));
					String secondGoal = line.substring(6);
					goalCoordinates.add(new Vertex(new Point(secondGoal)));
					inputMatrix = new char[table_size][table_size];
					areaMap = new int[table_size][table_size];
				}
				for (int i = 0; i < table_size; i++) {
					sb.append(line);
					sb.append(System.getProperty("line.separator"));
					line = br.readLine();
					inputMatrix[i] = line.toCharArray();
				}

				for (int i = 0; i < areaMap[0].length; i++) {
					for (int j = 0; j < areaMap.length; j++) {
						switch (inputMatrix[i][j]) {
						case 'A':
							areaMap[i][j] = 2;
							break;
						case 'B':
							areaMap[i][j] = 5;
							break;
						case 'X':
							areaMap[i][j] = wall;
							break;
						case 'S':
							areaMap[i][j] = 0;
							break;
						case 'G':
							areaMap[i][j] = 2;
							break;
						default:
							System.out.println("ERROR");
						}
					}
				}
				ini(areaMap);
				// printListOfAdjacencies(list);

			} finally {
				br.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			help();
		}
	}

	public int getAlgorithmType() {
		return algorithm_type;
	}

	public static int getTableSize() {
		return table_size;
	}

	// Starting point coordinates
	public static Vertex getSpCoordinates() {
		return sp_coordinates;
	}

	public static Vector<Vertex> getGoalCoordinates() {
		return goalCoordinates;
	}

	public static void printVectorOfVertices(Vector<Vertex> v) {
		for (Iterator<Vertex> iterator = v.iterator(); iterator.hasNext();) {
			Vertex vertex = (Vertex) iterator.next();
			vertex.toString();
		}
	}

	public static void ini(int[][] map) {
		list = new LinkedList<Vertex>();
		for (int i = 0; i < map[0].length; i++) {
			Vertex v = null;
			for (int j = 0; j < map.length; j++) {
				v = new Vertex(new Point(j, i));
				if (areaMap[i][j] > -1) {
					if ((j < table_size - 1) && areaMap[i][j + 1] > 0) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j + 1, i)), areaMap[i][j + 1]));
					}
					if ((j < table_size - 1) && (i < table_size - 1) && areaMap[i + 1][j + 1] != wall
							&& areaMap[i + 1][j] != wall && areaMap[i][j + 1] != wall) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j + 1, i + 1)), areaMap[i + 1][j + 1]));
					}
					if ((i > 0) && (j < table_size - 1) && areaMap[i - 1][j + 1] != wall && areaMap[i - 1][j] != wall) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j + 1, i - 1)), areaMap[i - 1][j + 1]));
					}
					if ((j > 0) && (i < table_size - 1) && areaMap[i + 1][j - 1] != wall && areaMap[i + 1][j] != wall
							&& areaMap[i][j - 1] != wall) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j - 1, i + 1)), areaMap[i + 1][j - 1]));
					}
					if ((j > 0) && areaMap[i][j - 1] >= 0) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j - 1, i)), areaMap[i][j - 1]));
					}
					if ((j > 0) && (i > 0) && areaMap[i - 1][j - 1] != wall && areaMap[i][j - 1] != wall) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j - 1, i - 1)), areaMap[i - 1][j - 1]));
					}
					if ((i > 0) && areaMap[i - 1][j] >= 0) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j, i - 1)), areaMap[i - 1][j]));
					}
					if ((i < table_size - 1) && areaMap[i + 1][j] >= 0) {
						v.adjacencies.add(new Edge(new Vertex(new Point(j, i + 1)), areaMap[i + 1][j]));
					}
					list.add(v);
				}
			}

		}
	}

	public static LinkedList<Vertex> ls() {
		return list;
	}

	public static Vector<Edge> getChildren(Vertex v) {
		for (Iterator<Vertex> iterator = list.iterator(); iterator.hasNext();) {
			Vertex vertex = (Vertex) iterator.next();
			if (v._name.equals(vertex._name))
				return vertex.adjacencies;
		}
		return null;
	}

	public static void printListOfAdjacencies(LinkedList<Vertex> list) {
		for (Iterator<Vertex> iterator = list.iterator(); iterator.hasNext();) {
			Vertex vertex = (Vertex) iterator.next();
			System.out.println(vertex._name + "==>" + vertex);
		}
	}

}
