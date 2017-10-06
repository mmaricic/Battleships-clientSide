package battleships.common;

import java.util.*;

public class Table {
	private int rowNum, columnNum;
	private ArrayList<Ship> ships = new ArrayList<>();
	private ArrayList<Coordinate> hittedSpots = new ArrayList<Coordinate>();
	private ArrayList<Coordinate> missedSpots = new ArrayList<Coordinate>();

	public ArrayList<Coordinate> getHittedSpots() {
		return hittedSpots;
	}

	public ArrayList<Coordinate> insertHittedSpot(Coordinate spot) {
		int i = 0;
		while (i < hittedSpots.size() && hittedSpots.get(i).toString().compareTo(spot.toString()) < 0)
			i++;
		if (i-- != hittedSpots.size() && i>=0)
			hittedSpots.add(i, spot);
		if (hittedSpots.size() == 0)
			hittedSpots.add(spot);
		if(i == 0)
			hittedSpots.add(0, spot);
		System.out.println("Hitted spot inserted.Size: " + hittedSpots.size());
		return hittedSpots;
	}

	public ArrayList<Coordinate> getMissedSpots() {
		return missedSpots;
	}

	public ArrayList<Coordinate> insertMissedSpot(Coordinate spot) {
		int i = 0;
		while (i < missedSpots.size() && missedSpots.get(i).toString().compareTo(spot.toString()) < 0)
			i++;
		if (i != missedSpots.size() && i != 0)
			missedSpots.add(i - 1, spot);
		if (missedSpots.size() == 0)
			missedSpots.add(spot);
		if(i == 0)
			missedSpots.add(0, spot);
		System.out.println("Missed spot inserted.Size: " + missedSpots.size());
		for(Coordinate c: missedSpots)
			System.out.println(c+" ");
		return missedSpots;
	}

	public Table(int row, int column) {
		rowNum = row;
		columnNum = column;
	}

	public void clear() {
		ships.clear();
	}

	public int getRowNum() {
		return rowNum;
	}

	public int getColumnNum() {
		return columnNum;
	}

	public int getShipsNum() {
		return ships.size();
	}

	public boolean setShip(Ship ship, Coordinate crdnt) {
		ship.set(crdnt);
		if (!ship.checkPlacement(columnNum, rowNum))
			return false;
		for (Ship iter : ships) {
			if (iter.isTouching(ship))
				return false;
		}

		ships.add(ship);
		return true;
	}

	public Ship getShipOnCoordinate(Coordinate crdnt) {
		for (Ship iter : ships) {
			if (iter.amOnCoordinate(crdnt))
				return iter;
		}
		return null;
	}

	public int numOfOperableSeg() {
		int num = 0;
		for (Ship iter : ships) {
			for (int i = 0; i < iter.segments.length; i++) {
				if (iter.isOperable(iter.segments[i].coordinate))
					num++;
			}
		}
		return num;
	}

	public boolean removeShip(Coordinate crdnt) {
		Ship ship = getShipOnCoordinate(crdnt);
		if (ship != null) {
			ships.remove(ship);
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuilder string = new StringBuilder();
		for (Ship iter : ships) 
			string.append(iter.toString()+";");
		string.deleteCharAt(string.length()-1);
		
		return string.toString();
	}
}
