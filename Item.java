public class Item {
	public Item(char itemChar, int x, int y) {
		this.itemChar = itemChar;
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		if (getClass() == obj.getClass()) {
			Item other = (Item) obj;
			return ((getX() == other.getX()) && (getY() == other.getY()));
		}
		return false;
	}

	private char itemChar;
	private int x;
	private int y;
}
