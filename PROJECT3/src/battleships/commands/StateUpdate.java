package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class StateUpdate extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		player.getControler().setStatus("Game is currently calculating round results. Please wait.");
	}

}
