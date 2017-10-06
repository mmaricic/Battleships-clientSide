package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class GameWon extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		player.getControler().setGW(command.split(" ")[1]);
	}

}
