package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class FireRejected extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		player.getControler().setStatus("Fire rejected! You were too late!");

	}

}
