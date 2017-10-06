package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class FireAccepted extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		player.getControler().setStatus("Fire accepted!");
	}
}
