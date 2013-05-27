
public class Node implements Comparable<Node>{
	private int currRow;
	private int currCol;
	private String path;
	private int heuristic;
	/**
	 * @param args
	 */
	public Node (int currRow, int currCol, int heuristic, String path) {
		this.currCol = currCol;
		this.currRow = currRow;
		this.heuristic = heuristic;
		this.path = path;
	}
	
	public int getRow () {
		return currRow;
	}
	
	public int getCol () {
		return currCol;
	}
	
	public String getPath() {
		return path;
	}
	public int getHeuristic() {
		return heuristic;
	}
	
	public char lastMove () {
		if (path.length() == 0) {
			return ' ';
		} else {
			return path.charAt(path.length()-1);
		}
	}
	@Override
	public int compareTo(Node obj) {
		return (path.length() + heuristic) - (obj.getPath().length() + obj.getHeuristic());
	}


}
