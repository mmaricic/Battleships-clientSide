package battleships.common;

/**
 *
 * @author POOP
 */
public class Coordinate {
	private int row;
	private int column;

	public Coordinate(int _row, int _column) {
		row = _row;
		column = _column;
	}

	static public Coordinate makeCoordinate(String coordinates) {
		int _row = Integer.parseInt(coordinates.substring(0, 2));
		int _column = Integer.parseInt(coordinates.substring(2, 4));
		return new Coordinate(_row, _column);
	}

	
	public int getRow() {
		return row;
	}

	
	public int getColumn() {
		return column;
	}

	
	public String toString() {
		return String.format("%02d", row) + String.format("%02d", column);
	}
}