<<<<<<< HEAD
public class Node implements Comparable<Node> {
=======

public class Node implements Comparable<Node>{
>>>>>>> 5ff179775fd555bf29020cc29928b68666cdea17
	private int currRow;
	private int currCol;
	private String path;
	private int heuristic;
<<<<<<< HEAD

	/**
	 * @param args
	 */
	public Node(int currRow, int currCol, int heuristic, String path) {
=======
	/**
	 * @param args
	 */
	public Node (int currRow, int currCol, int heuristic, String path) {
>>>>>>> 5ff179775fd555bf29020cc29928b68666cdea17
		this.currCol = currCol;
		this.currRow = currRow;
		this.heuristic = heuristic;
		this.path = path;
	}
<<<<<<< HEAD

	public int getRow() {
		return currRow;
	}

	public int getCol() {
		return currCol;
	}

	public String getPath() {
		return path;
	}

	public int getHeuristic() {
		return heuristic;
	}

	public char lastMove() {
		if (path.length() == 0) {
			return ' ';
		} else {
			return path.charAt(path.length() - 1);
		}
	}

	public String getMovePath() {
		String movePath = path.replaceAll("o", "");
		movePath = movePath.replaceAll("c", "");
		movePath = movePath.replaceAll("b", "");
		return movePath;
	}

	@Override
	public int compareTo(Node obj) {
		return (getMovePath().length() + heuristic)
				- (obj.getMovePath().length() + obj.getHeuristic());
	}
=======
	
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


>>>>>>> 5ff179775fd555bf29020cc29928b68666cdea17
}
