/*********************************************
/*  Agent.java 
/*  Sample Agent for Text-Based Adventure Game
/*  COMP3411 Artificial Intelligence
/*  UNSW Session 1, 2013
 */

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Agent {
	private final int initialx = 80;
	private final int initialy = 80;
	private final int EAST = 0;
	private final int NORTH = 1;
	private final int WEST = 2;
	private final int SOUTH = 3;

	// MOVES
	private final char LEFT = 'l';
	private final char RIGHT = 'r';
	private final char FORWARD = 'f';
	private final char CHOP = 'c';
	private final char OPEN = 'o';
	private final char BLAST = 'b';

	// Environment stuff
	public static final char TREE = 'T';
	public static final char DOOR = '-';
	public static final char WALL = '*';
	public static final char WATER = '~';
	public static final char AXE = 'a';
	public static final char KEY = 'k';
	public static final char DYNAMITE = 'd';
	public static final char GOLD = 'g';
	public static final char UNKNOWN = 'x';
	public static final char EMPTY = ' ';

	private char[][] map;
	private int CurrCol;
	private int CurrRow;
	private int orientation;
	private boolean hasAxe;
	private boolean hasKey;
	private boolean hasGold;
	private int numDynamite;
	private ArrayList<Item> itemLocations;
	
	private boolean lastSeenRight;
	private int exploreStartRow;
	private int exploreStartCol;
	private boolean moved;

	private boolean canSeeKey;
	private boolean canSeeAxe;
	private boolean canSeeGold;

	private final int LOOKAHEAD = 0;
	private final int LOOKLEFT = 1;
	private final int LOOKBACK = 2;
	private final int LOOKRIGHT = 3;

	private int turnCount;

	public Agent() {
		map = new char[160][160];
		CurrCol = 80;
		CurrRow = 80;
		hasAxe = false;
		hasKey = false;
		hasGold = false;
		numDynamite = 0;
		orientation = SOUTH;
		canSeeKey = false;
		canSeeAxe = false;
		canSeeGold = false;
		lastSeenRight = false;
		exploreStartRow = -1;
		exploreStartCol = -1;
		moved = false;
		
		itemLocations = new ArrayList<Item>();

		turnCount = -1;

		for (int i = 0; i < 160; i++) {
			for (int j = 0; j < 160; j++) {
				map[i][j] = UNKNOWN;
			}
		}
	}

	public char get_action(char view[][]) {
		Traverse search = new Traverse();

		// search.traverseTo(map, CurrRow, CurrCol, 84, 80);

		for (int i = 0; i < 5; i++) {
			System.out.print("x");
			for (int j = 0; j < 5; j++) {
				System.out.print(view[i][j]);
			}
			System.out.println("x");
		}
		
		if (hasGold) {
			String temp = search.traverseTo(map, CurrRow, CurrCol, initialx, initialy);
			if (temp != null);
		}
		

		// REPLACE THIS CODE WITH AI TO CHOOSE ACTION

		// Record the current position into the map.
		mapPosition(view);
		for (int i = 70; i < 90; i++) {
			for (int j = 70; j < 90; j++) {
				System.out.print(map[i][j]);
			}
			System.out.println();
		}
		turnCount++;
		System.out.println("Turn: " + turnCount);
		System.out.println("Current Position: " + CurrRow + " " + CurrCol + " Orientation "
				+ orientation + " Ahead: \"" + lookAhead() + "\"");
		
		if (lookAhead() == GOLD || lookAhead() == DYNAMITE ||
				lookAhead() == AXE || lookAhead() == KEY) {
			updateAgent(FORWARD);
			return FORWARD;
		}
		if (hasAxe && lookAhead() == TREE) {
			updateAgent(CHOP);
			return CHOP;
		}
		if (hasKey && lookAhead() == DOOR) {
			updateAgent(OPEN);
			return OPEN;
		}
		
		if (turnCount < 3000) {
			char action = explore ();
			updateAgent(action);
			return action;	
		}
		

//		 if (hasGold) {
//		 traverseTo (initialx, initialy);
//		 }
//		
//		 //If we have an axe and see a tree chop it.


//		
//		 if (lookAhead() == WATER) {
//		 if (Math.random() * 2 > 1) {
//		 updateAgent(LEFT);
//		 return LEFT;
//		 } else {
//		 updateAgent(RIGHT);
//		 return RIGHT;
//		 }
//		 } else {
//		 if (Math.random() * 2 > 1) {
//		 updateAgent(FORWARD);
//		 return FORWARD;
//		 } else if (Math.random() * 2 > 1) {
//		 updateAgent(RIGHT);
//		 return RIGHT;
//		 } else {
//		 updateAgent(LEFT);
//		 return LEFT;
//		 }
//		 }

		int ch = 0;

		System.out.print("Enter Action(s): ");
		String path = "";
//		for (Item itemLocation : itemLocations) {
//			String currentPath = search.traverseTo(map, CurrRow, CurrCol, itemLocation.getX(),
//					itemLocation.getY());
//			if ((pathPossible(currentPath))) {
//				path = currentPath;
//			}
//		}
		if (path.isEmpty()) {
			// randomTraverse
		} else {
			// TraversePath
		}
		try {
			while (ch != -1) {
				// read character from keyboard
				ch = System.in.read();

				switch (ch) { // if character is a valid action, return it
				case 'F':
				case 'L':
				case 'R':
				case 'C':
				case 'O':
				case 'B':
				case 'f':
				case 'l':
				case 'r':
				case 'c':
				case 'o':
				case 'b':
					updateAgent((char) ch);
					return ((char) ch);
				}
			}
		} catch (IOException e) {
			System.out.println("IO error:" + e);
		}

		return 0;
	}

	private char explore() {
		char ahead = look (LOOKAHEAD);
		char right = look (LOOKRIGHT);
		char left = look (LOOKLEFT);
		
		if (exploreStartRow == CurrRow && exploreStartCol == CurrCol) {
			if (moved) {
				System.out.println("FINISHED");
				exploreStartRow = -1;
				exploreStartCol = -1;
				return LEFT;	
			} else {
				System.out.println("NOT MOVED YO");
			}
			
		}
		
		switch (left) {
		case DYNAMITE:
		case AXE:
		case GOLD:
		case KEY:
			System.out.println("SEARCH ABORTED");
			exploreStartRow = -1;
			exploreStartCol = -1;
			return LEFT;
		case TREE:
			if (hasAxe) {
				System.out.println("SEARCH ABORTED");
				exploreStartRow = -1;
				exploreStartCol = -1;
				return LEFT;	
			}
		case DOOR:
			if (hasKey) {
				System.out.println("SEARCH ABORTED");
				exploreStartRow = -1;
				exploreStartCol = -1;
				return LEFT;
			}
		}
		
		switch (right) {
		case DYNAMITE:
		case AXE:
		case GOLD:
		case KEY:
			System.out.println("SEARCH ABORTED");
			exploreStartRow = -1;
			exploreStartCol = -1;
			return RIGHT;
		case TREE:
			if (hasAxe) {
				System.out.println("SEARCH ABORTED");
				exploreStartRow = -1;
				exploreStartCol = -1;
				return RIGHT;	
			}
		case DOOR:
			if (hasKey) {
				System.out.println("SEARCH ABORTED");
				exploreStartRow = -1;
				exploreStartCol = -1;
				return RIGHT;
			}
		}
		if (ahead == EMPTY && right == EMPTY && !lastSeenRight) {
			moved = true;
			return FORWARD;
		}

		if (right != EMPTY) {
			lastSeenRight = true;
			if (ahead == EMPTY) {
				moved = true;
				return FORWARD;
			}
		}
		if (exploreStartRow == -1 && (right != EMPTY || ahead != EMPTY)) {
			exploreStartRow = CurrRow;
			exploreStartCol = CurrCol;
			moved = false;
			System.out.println("STARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\nSTARTING SEARCH\n");
			System.out.println("at " + CurrRow + " " + CurrCol);
		}
		if (lastSeenRight && right == EMPTY) {
			lastSeenRight = false;
			return RIGHT;
		}

		return LEFT;
	}

	private boolean pathPossible(String path) {
		boolean possible = true;
		if (path.isEmpty()) {
			possible = false;
		}
		if (path.contains("o") && !hasKey) {
			possible = false;
		}
		if (path.contains("c") && !hasAxe) {
			possible = false;
		}
		if (path.length() - path.replaceAll("d", "").length() > numDynamite) {
			possible = false;
		}
		return possible;
	}

	/**
	 * Checks if the agent can reach the item given. from the given coordinates.
	 * Choices include gold, axe, key.
	 * 
	 * @param item
	 * @return
	 */
	private boolean canReach(int x, int y, char item) {
		if (x < 0 || y < 0 || x > 160 || y > 160) {
			return false;
		}
		if (map[x][y] == item) {
			return true;
		} else if (map[x][y] == TREE || map[x][y] == WATER || map[x][y] == DOOR || map[x][y] == WALL
				|| map[x][y] == UNKNOWN) {
			return false;
		} else {
			return canReach(x + 1, y, item) || canReach(x - 1, y, item) || canReach(x, y + 1, item)
					|| canReach(x, y - 1, item);
		}
	}

	/**
	 * Updates the agent state in line with the action. Should be used before
	 * returning an action.
	 * 
	 * @param action
	 */
	private void updateAgent(char action) {
		System.out.println("Updating action: " + action);
		if (action == FORWARD) {
			char ahead = lookAhead();
			if (ahead == GOLD) {
				hasGold = true;
				Item newLocation = new Item(ahead, CurrRow, CurrCol);
				itemLocations.remove(newLocation);
			} else if (ahead == AXE) {
				hasAxe = true;
				Item newLocation = new Item(ahead, CurrRow, CurrCol);
				itemLocations.remove(newLocation);
			} else if (ahead == KEY) {
				hasKey = true;
				Item newLocation = new Item(ahead, CurrRow, CurrCol);
				itemLocations.remove(newLocation);
			} else if (ahead == DYNAMITE) {
				numDynamite++;
				Item newLocation = new Item(ahead, CurrRow, CurrCol);
				itemLocations.remove(newLocation);
			}
			if (ahead == WATER) {
				System.out.println("SHITSHITSHIT");
			}

			moveForwardUpdate();

		} else if (action == RIGHT) {
			orientation--;
			if (orientation < 0) {
				orientation = SOUTH;
			}

		} else if (action == LEFT) {
			orientation++;
			orientation = orientation % 4;

		} else if (action == CHOP) {
			// replace whatever is ahead in the map with ' ';
			if (lookAhead() == TREE) {
				clearCellAhead();
				moveForwardUpdate();
			}

		} else if (action == OPEN) {
			if (lookAhead() == DOOR) {
				clearCellAhead();
				moveForwardUpdate();
			}

		} else if (action == BLAST) {
			numDynamite--;
			clearCellAhead();
			// moveForwardUpdate();
		}
	}

	/**
	 * Removes the obstacle in the cell ahead. Called when using
	 * axes/keys/dynamite.
	 */
	public void clearCellAhead() {
		if (orientation == EAST) {
			map[CurrCol + 1][CurrRow] = EMPTY;
		} else if (orientation == NORTH) {
			map[CurrCol][CurrRow + 1] = EMPTY;
		} else if (orientation == WEST) {
			map[CurrCol - 1][CurrRow] = EMPTY;
		} else if (orientation == SOUTH) {
			map[CurrCol][CurrRow - 1] = EMPTY;
		}
	}

	/**
	 * Updates the currX and currY variables in line with a move forward.
	 */
	public void moveForwardUpdate() {
		System.out.println("Moving forward...");
		char ahead = lookAhead();
		if (ahead == EMPTY || ahead == AXE || ahead == DYNAMITE || ahead == KEY) {
			if (orientation == EAST) {
				CurrCol++;
			} else if (orientation == NORTH) {
				CurrRow--;
			} else if (orientation == WEST) {
				CurrCol--;
			} else if (orientation == SOUTH) {
				CurrRow++;
			}
		}
	}

	/**
	 * Commands the agent to traverse to the given coordinates.
	 * 
	 * @param x
	 * @param y
	 */
	private void traverseTo(int x, int y) {
		// PLZ HELP THOMAS
	}

	private char lookAhead() {
		if (orientation == EAST) {
			return map[CurrRow][CurrCol + 1];
		} else if (orientation == NORTH) {
			return map[CurrRow - 1][CurrCol];
		} else if (orientation == WEST) {
			return map[CurrRow][CurrCol - 1];
		} else {
			return map[CurrRow + 1][CurrCol];
		}
	}

	private char look(int direction) {
		direction = (direction + orientation) % 4;
		if (direction == EAST) {
			return map[CurrRow][CurrCol + 1];
		} else if (direction == NORTH) {
			return map[CurrRow - 1][CurrCol];
		} else if (direction == WEST) {
			return map[CurrRow][CurrCol - 1];
		} else {
			return map[CurrRow + 1][CurrCol];
		}
	}

	/**
	 * Maps the current view into the map. The map has SOUTH as being down and
	 * EAST as being to the right. Replaces the character with a space.
	 * 
	 * @param view
	 *           the 5x5 char array.
	 */
	private void mapPosition(char[][] view) {

		for (int i = -2; i <= 2; i++) {
			int mapRow = i + CurrRow;

			for (int j = -2; j <= 2; j++) {
				char value;
				int mapCol = j + CurrCol;

				if (i == 0 && j == 0) {
					value = 'o';
				} else {

					int row = i + 2;
					int col = j + 2;

					if (orientation == EAST) {
						int temp = col;
						col = row;
						row = 4 - temp;
					} else if (orientation == SOUTH) {
						row = 4 - row;
						col = 4 - col;
					} else if (orientation == WEST) {
						int temp = row;
						row = col;
						col = 4 - temp;
					}
					// System.out.println("Row: " + row + " Col: " + col);
					// System.out.println("MapX " + mapRow + " MapY " + mapCol);
					value = view[row][col];
				}
				if (value == GOLD || value == AXE || value == KEY || value == DYNAMITE) {
					Item newLocation = new Item(value, mapRow, mapCol);
					if (!itemLocations.contains(newLocation)) {
						itemLocations.add(newLocation);
					}
				}
				map[mapRow][mapCol] = value;
			}
		}
	}

	void print_view(char view[][]) {
		int i, j;

		System.out.println("\n+-----+");
		for (i = 0; i < 5; i++) {
			System.out.print("|");
			for (j = 0; j < 5; j++) {
				if ((i == 2) && (j == 2)) {
					System.out.print('^');
				} else {
					System.out.print(view[i][j]);
				}
			}
			System.out.println("|");
		}
		System.out.println("+-----+");
	}

	public static void main(String[] args) {
		InputStream in = null;
		OutputStream out = null;
		Socket socket = null;
		Agent agent = new Agent();
		char view[][] = new char[5][5];
		char action = 'F';
		int port;
		int ch;
		int i, j;

		if (args.length < 2) {
			System.out.println("Usage: java Agent -p <port>\n");
			System.exit(-1);
		}

		port = Integer.parseInt(args[1]);

		try { // open socket to Game Engine
			socket = new Socket("localhost", port);
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			System.out.println("Could not bind to port: " + port);
			System.exit(-1);
		}

		try { // scan 5-by-5 wintow around current location
			while (true) {
				for (i = 0; i < 5; i++) {
					for (j = 0; j < 5; j++) {
						if (!((i == 2) && (j == 2))) {
							ch = in.read();
							if (ch == -1) {
								System.exit(-1);
							}
							view[i][j] = (char) ch;
						}
					}
				}
				agent.print_view(view); // COMMENT THIS OUT BEFORE SUBMISSION
				action = agent.get_action(view);
				out.write(action);
			}
		} catch (IOException e) {
			System.out.println("Lost connection to port: " + port);
			System.exit(-1);
		} finally {
			try {
				socket.close();
			} catch (IOException e) {
			}
		}
	}
}
