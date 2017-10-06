package battleships.common;

import java.awt.Component;

public class Ship {
	private char orientation = 'h';
	Segment[] segments;

	public Ship(int segmentNum) {
		segments = new Segment[segmentNum];
	}

	
	public void orient(char o) {
		orientation = o;
	}

	
	public void set(Coordinate coord) {
		segments[0] = new Segment(coord);
		for (int i = 1; i < segments.length; i++) {
			if (orientation == 'h')
				segments[i] = new Segment(new Coordinate(coord.getRow(), coord.getColumn() + i));
			else
				segments[i] = new Segment(new Coordinate(coord.getRow() + i, coord.getColumn()));
		}
		
	}

	
	static public Ship makeShip(String ship) {
		int segNum = Integer.parseInt(ship.substring(2, 3));
		ship = ship.substring(6, ship.length() - 1);
		String[] coordinates = ship.split(",");
		Ship temp = new Ship(segNum);
		if (coordinates[0].charAt(3) == coordinates[coordinates.length - 1].charAt(3))
			temp.orient('v');
		temp.set(Coordinate.makeCoordinate(coordinates[0]));
		return temp;
	}

	
	public String toString() {
		int i = 0;
		StringBuilder word = new StringBuilder("S(");
		word.append(segments.length + ")=[");
		for (; i < segments.length - 1; i++)
			word.append(segments[i].coordinate + ",");
		word.append(segments[i].coordinate + "]");
		return word.toString();
	}

	
	public boolean amOnCoordinate(Coordinate crdnt) {
		int i = 0;
		while (i < segments.length && !segments[i].coordinate.toString().equals(crdnt.toString()))
			i++;
		if (i == segments.length)
			return false;
		return true;
	}

	
	public boolean isTouching(Ship newShip) {
		int oneFirst = segments[0].coordinate.getRow() - 1;
		int oneLast = segments[segments.length - 1].coordinate.getRow() + 1;
		int twoFirst = newShip.segments[0].coordinate.getRow();
		int twoLast = newShip.segments[newShip.segments.length - 1].coordinate.getRow();
		if (check(oneFirst, oneLast, twoFirst, twoLast)) {
			oneFirst = segments[0].coordinate.getColumn() - 1;
			oneLast = segments[segments.length - 1].coordinate.getColumn() + 1;
			twoFirst = newShip.segments[0].coordinate.getColumn();
			twoLast = newShip.segments[newShip.segments.length - 1].coordinate.getColumn();
			if (check(oneFirst, oneLast, twoFirst, twoLast))
				return true;
		}
		return false;
	}

	private boolean check(int of, int ol, int tf, int tl) {
		return ((of <= tf && tf <= ol) || (of <= tl && tl <= ol) || (tf <= of && ol <= tl));
	}

	
	public boolean isOperable(Coordinate crdnt) {
		if (!amOnCoordinate(crdnt))
			return false;
		int i = 1;
		Segment temp = segments[0];
		while (i < segments.length && temp.coordinate != crdnt)
			temp = segments[i++];
		return temp.state;
	}

	
	public void disableSegment(Coordinate crdnt) {
		if (!this.amOnCoordinate(crdnt))
			return;
		for (int i = 0; i < segments.length; i++)
			if (segments[i].coordinate.toString().equals(crdnt.toString())) {
				segments[i].state = false;
				return;
			}
	}
	
	
	public Coordinate getFirstCoordinate(){
		return segments[0].coordinate;
	}

	
	class Segment {
		Coordinate coordinate;
		boolean state = true;

		Segment(Coordinate c) {
			coordinate = c;
		}
	}


	public boolean checkPlacement(int columnNum, int rowNum) {
		for(int i = 0; i < segments.length; i++){
			if(segments[i].coordinate.getRow() < 0 || segments[i].coordinate.getColumn() < 0
					|| segments[i].coordinate.getRow() >= columnNum || segments[i].coordinate.getColumn() >= rowNum)
				return false;
		}
		return true;
	}


	public int getSegmentsX(int i) {
		return segments[i].coordinate.getRow();
	}
	
	public int getSegmentsY(int i){
		return segments[i].coordinate.getColumn();
	}


	public int size() {
		return segments.length;
	}
}