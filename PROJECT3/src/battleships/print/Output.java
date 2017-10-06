package battleships.print;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import battleships.client.BattleshipsPlayer;
import battleships.common.Coordinate;
import battleships.common.Ship;
import battleships.common.Table;

public class Output { // svrha ove klase je da bih lakse posle zamenila ispis za
						// GUI

	private Output() {
	}

	private static Output instance = null;

	static public Output makeObject() {
		if (instance == null)
			instance = new Output();
		return instance;
	}
 
	
	static public void IPAddressDemand() {
		System.out.print("Insert Server IP address: ");
	}

	
	public void demandPassword() {
		System.out.println("***Protected Server! Insert JOIN name/password***");
	}

	
	public void printWaitingMenu(BattleshipsPlayer player) {
		System.out.println("***Waiting for players...\nGame options:\n-Game state(STATE)\n-Exit game(QUIT)***");
	}

	
	public void accessDenied() {
		System.out.println("***Wrong password. Try again.***");
	}

	
	public void duplicateName() {
		System.out.println("***Name taken! Try with new name.***");
	}

	
	public void waitingPlayers(String command) {
		System.out.println("***Waiting for players***");
	}

	
	public void deployShipsState(String command) {
		// System.out.println(command);
		System.out.println("***Deploy ships. Remained time: " + command.split(" ")[1] + " seconds.***");
	}

	
	public void stateRound(String command) {
		System.out.println(
				"***Round " + command.split(" ")[1] + ". Remained time: " + command.split(" ")[2] + " seconds.***");
	}

	
	public void stateUpdate() {
		System.out.println("***Game is currently calculating round results. Please wait.***");
	}

	
	public void gameOver(BattleshipsPlayer player) {
		System.out.println("***GAME OVER\nYou can stay in game and watch or quit(type QUIT)***");
	}

	
	public void victory() {
		System.out.println("***Congratulations! You won!***");
	}

	
	public void gameWon(String command) {
		System.out.println("***Game over. Game won player " + command.split(" ")[1] + "***");
	}

	
	public void noVictory() {
		System.out.println("***Game over. There is no victory.***");
	}

	
	public void fireRejected() {
		System.out.println("***You're move is rejected! You cannot fire when it's not time to fire!***");
	}

	
	public void fireAccepted() {
		System.out.println("***Fire accepted***");
	}

	
	public void layoutRejected() {
		System.out.println("***Your ship layout is rejected. You entered invalid ship schedule.***");
	}

	
	public void error(String command) {
		System.out.println("***Invalid command. Command: " + command + "***");
	}

	
	public void disconnect() {
		System.out.println("***You have been disconected from server.***");
	}

	
	static public void welcomeMenu() {
		System.out.println("You are connected to server.\nYour options:\n1. JOIN name[/password]\n2. QUIT");
	}

	
	private void printLines(int num) {
		System.out.println();
		System.out.print("   ");

		for (int i = 0; i < num; i++)
			System.out.print("-");
		System.out.println();
	}

	
	public void printMyTable(BattleshipsPlayer player) {
		Table table = player.getTable(player.getName());
		Ship ship;
		System.out.print("   ");

		for (int i = 0; i < player.getColumnNum(); i++)
			System.out.print(String.format("%3d", i));
		printLines(player.getColumnNum() * 3 + 1);

		for (int i = 0; i < player.getColumnNum(); i++) {
			System.out.print(String.format("%2d", i) + " |");
			for (int j = 0; j < player.getRowNum(); j++) {
				Coordinate crdnt = new Coordinate(i, j);
				ship = table.getShipOnCoordinate(crdnt);

				if (ship != null) {
					if (ship.isOperable(crdnt))
						System.out.print(" *|");
					else
						System.out.print(" x|");
				} else
					System.out.print("  |");
			}

			printLines(player.getColumnNum() * 3 + 1);
		}
		System.out.println("     my table\n");
	}

	
	/*public void oponentsTables(BattleshipsPlayer player) {
		Iterator<Entry<String, Table>> iter = player.myTables().entrySet().iterator();

		while (iter.hasNext()) {
			Map.Entry<String, Table> pair = (Map.Entry<String, Table>) iter.next();
			if (pair.getKey().equals(player.getName()))
				continue;
			Table table = pair.getValue();
			Coordinate hitCrdnt = null;
			Coordinate missCrdnt = null;

			System.out.print("   ");
			for (int i = 0; i < player.getColumnNum(); i++)
				System.out.print(String.format("%3d", i));
			printLines(player.getColumnNum() * 3 + 1);

			Iterator<Coordinate> hittedIter = table.getHittedSpots().iterator();
			Iterator<Coordinate> missedIter = table.getMissedSpots().iterator();

			for (int j = 0; j < player.getRowNum(); j++) {
				System.out.print(String.format("%2d", j) + " |");

				for (int i = 0; i < player.getColumnNum(); i++) {
					if (hitCrdnt == null || (hitCrdnt.getRow() < j || (hitCrdnt.getRow() >= j && hitCrdnt.getColumn() > i)))
						if (hittedIter.hasNext()){
							hitCrdnt = (Coordinate) hittedIter.next();
						}
					if (missCrdnt == null || (missCrdnt.getRow() < j || (missCrdnt.getRow() >= j && missCrdnt.getColumn() > i)))
						if (missedIter.hasNext()){
							missCrdnt = (Coordinate) missedIter.next();
						}
					if (hitCrdnt != null && hitCrdnt.getColumn() == j && hitCrdnt.getRow() == i)
						System.out.print(" x|");
					else if (missCrdnt != null && missCrdnt.getColumn() == j && missCrdnt.getRow() == i)
						System.out.print(" o|");
					else
						System.out.print("  |");

				}
				printLines(player.getColumnNum() * 3 + 1);
			}
		}
	}*/

	
	public void newRoundMenu(String command, BattleshipsPlayer player) {
		String[] helpString = command.split(" ");
		System.out.print("Round " + helpString[1]);
		helpString = helpString[2].substring(1, helpString[2].length() - 1).split(";");
		String temp = helpString[0];

		for (int i = 1; i < helpString.length; i++)
			temp = temp + ", " + helpString[i];

		System.out.println(", players: " + temp + "\nOptions:");
		System.out.println("1.FIRE " + player.getID() + " [{oponents_name}C1;{oponents_name}C2;...{oponents_name}Ck]");
		System.out.println("2. STATE\n3. QUIT");
		System.out.println("Be careful, you can only fire once!");
	}

	
	public void printGameSpecification(String specification, BattleshipsPlayer player) {
		String[] helpString = specification.split(";");
		System.out.println("Dimension of table is: " + helpString[0].split("=")[1]);
		System.out.println(
				"Number in brackets represent number of segments\nSecond nubmer represent number of available ships that size");

		for (int i = 1; i < helpString.length; i++)
			System.out.println(helpString[i]);
		System.out
				.println("Insert command: SHIP_LAYOUT " + player.getID() + " S(x1)=[C1,C2..Ck];...S(xn)=[C1,C2..Cj]\n");
	}

}
