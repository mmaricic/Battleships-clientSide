package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class NoVictory extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		player.getControler().setNV();
	}

}
