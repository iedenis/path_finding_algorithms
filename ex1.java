
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class ex1 {
	public static String out_file = System.getProperty("user.dir") + "/src" + "/output.txt";
	public static FileWriter fw=null;
	static PrintWriter os;
	static CAreaMap map = new CAreaMap();
	
	
	public static void main(String[] args) {
		if(System.getProperty("os.name").equals("Linux"))
			out_file=System.getProperty("user.dir") +"/output.txt";
		else if(System.getProperty("os.name")=="Windows")out_file=System.getProperty("user.dir")+"\\output.txt";
		else
			System.out.println("Unknown operating system");
				
		try {
			fw=new FileWriter(out_file);
		} catch (IOException e) {
			System.out.println("The path to output file not found. You can change it above");
			e.printStackTrace();
		}
		os=new PrintWriter(fw);
		switch (map.getAlgorithmType()) {
		case 1:
			BFS bfs = new BFS();
			bfs.search(CAreaMap.getSpCoordinates(), CAreaMap.getGoalCoordinates());
			break;
		case 2:
			DFID dfid = new DFID();
			dfid.search(CAreaMap.getSpCoordinates(), CAreaMap.getGoalCoordinates());
			break;
		case 3:
			AStar AStar = new AStar();
			AStar.search(CAreaMap.getSpCoordinates(), CAreaMap.getGoalCoordinates());
			break;
		case 4:
			IDAStar IdaStar = new IDAStar();
			IdaStar.search(CAreaMap.getSpCoordinates(), CAreaMap.getGoalCoordinates());
			break;
		default:
			System.out.println("Wrong number of algorithm");
		}
		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
