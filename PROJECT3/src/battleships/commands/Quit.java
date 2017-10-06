package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class Quit extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		player.getSender().setMessage("QUIT "+player.getID());
	}

}
