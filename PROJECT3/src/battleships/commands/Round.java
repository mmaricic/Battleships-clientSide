package battleships.commands;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import battleships.client.BattleshipsPlayer;
import battleships.client.CurrentPlayer;
import battleships.common.Table;

public class Round extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException, NumberFormatException {
		String[] helpString = command.split(" ");
		int check = Integer.parseInt(helpString[1]);
		int time = Integer.parseInt(helpString[2]);
		System.out.println(helpString[3]);
		helpString = (helpString[3].substring(1, helpString[3].length() - 1)).split(";");
		
		if (check == 1) {
			boolean rem;
			CurrentPlayer pl = null;
			
			for (String name : helpString) {
				if (!name.equals(player.getName()))
					player.insertTable(name);
			}
			
			rem = true;
			Iterator<CurrentPlayer> it = player.getAllPlayers().iterator();
			while (it.hasNext()) {
				pl = it.next();
				int i = 0;
				for (; i < helpString.length; i++)
					if (pl.getName().equals(helpString[i]))
						break;

				if (pl != null && i == helpString.length) {
					System.out.println("UKLANJA " + pl.getName());
					it.remove();
				}
			}
			player.getControler().startGame(player.getAllPlayers());
			player.getControler().setStartingNumOfFires(player.getTable(player.getName()).numOfOperableSeg());

		} 
		else {
			Iterator<Map.Entry<String, Table>> iter = player.activePlayers().entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry<String, Table> pair = (Map.Entry<String, Table>) iter.next();
				int i = 0;
				
				while (i < helpString.length && !helpString[i].equals(pair.getKey()))
					i++;
				if (i == helpString.length) {
				
					for (Iterator<CurrentPlayer> it = player.getAllPlayers().iterator(); it.hasNext();) {
						CurrentPlayer pl = it.next();
						if (pl.getName().equals(pair.getKey())) {
							it.remove();
							break;
						}
					}
					iter.remove();
				}
			}
			
			player.getControler().updateActivePlayers(check, player.getAllPlayers());
		}
		
		System.out.println("RUNDA "+check);
		player.getControler().setTimer(time);
		player.getControler().setStatus("New round");
	}
}
