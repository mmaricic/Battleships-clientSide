package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class Victory extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		player.getControler().setV();
	}

}
