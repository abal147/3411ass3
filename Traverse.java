import java.util.ArrayList;
import java.util.PriorityQueue;

public class Traverse {

	/**
	 * @param args
	 */

	public String traverseTo(char[][] map, int startRow, int startCol, int endRow, int endCol) {
		boolean finished = false;
		PriorityQueue<Node> queue = new PriorityQueue<Node>();

		Node current = new Node(startRow, startCol, mDistance(startRow, startCol, endRow, endCol), "");
		// System.out.println("Initial: " + current.getRow() + " " +
		// current.getCol() + " " + current.getHeuristic() + " " +
		// current.lastMove());
		queue.add(current);
		ArrayList<Integer> visited = new ArrayList<Integer>(100);

		while (!queue.isEmpty() && !finished) {
			current = queue.poll();
			if (!visited.contains(1000 * current.getRow() + current.getCol())) {
				visited.add(1000 * current.getRow() + current.getCol());

				// System.out.println("Exploring: " + current.getRow() + " " +
				// current.getCol() + " " + current.getHeuristic() + " " +
				// current.lastMove() + " " + (current.getPath().length() +
				// current.getHeuristic()));
				if (current != null) {
					if (current.getRow() == endRow && current.getCol() == endCol) {
						finished = true;
					} else {
						int row = current.getRow();
						int col = current.getCol();

						char value = map[row - 1][col];
						if (!(value == Agent.WATER) && !(value == Agent.UNKNOWN)
								&& !(current.lastMove() == 'd')) {
							String nextAction = moveNeeded(value);
							nextAction.concat("u");
							Node up = new Node(row - 1, col, mDistance(row - 1, col, endRow, endCol),
									current.getPath().concat(nextAction));
							queue.add(up);
						}
						value = map[row][col - 1];
						if (!(value == Agent.WATER) && !(value == Agent.UNKNOWN)
								&& !(current.lastMove() == 'l')) {
							String nextAction = moveNeeded(value);
							nextAction.concat("r");
							Node right = new Node(row - 1, col, mDistance(row - 1, col, endRow, endCol),
									current.getPath().concat(nextAction));
							queue.add(right);
						}
						value = map[row][col + 1];
						if (!(value == Agent.WATER) && !(value == Agent.UNKNOWN)
								&& !(current.lastMove() == 'r')) {
							String nextAction = moveNeeded(value);
							nextAction.concat("l");
							Node left = new Node(row - 1, col, mDistance(row - 1, col, endRow, endCol),
									current.getPath().concat(nextAction));
							queue.add(left);
						}
						value = map[row + 1][col];
						if (!(value == Agent.WATER) && !(value == Agent.UNKNOWN)
								&& !(current.lastMove() == 'u')) {
							String nextAction = moveNeeded(value);
							nextAction.concat("d");
							Node down = new Node(row - 1, col, mDistance(row - 1, col, endRow, endCol),
									current.getPath().concat(nextAction));
							queue.add(down);
						}
						// System.out.println();
					}
				}
			}
		}
		if (finished == true) {
			System.out.println("Path: " + current.getPath());
			return current.getPath();
		}
		System.out.println("Couldn't find it mate");
		return null;
	}

	private String moveNeeded(char value) {
		String actionNeeded = "";

		if (value == Agent.DOOR) {
			actionNeeded = "o";
		} else if (value == Agent.TREE) {
			actionNeeded = "c";
		} else if (value == Agent.WALL) {
			actionNeeded = "b";
		}

		return actionNeeded;
	}

	public int mDistance(int x1, int y1, int x2, int y2) {
		return Math.abs(x1 - x2) + Math.abs(y1 - y2);
	}

}
