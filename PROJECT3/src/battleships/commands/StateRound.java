package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class StateRound extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		player.getControler().setStatus("Round " + command.split(" ")[1] + ". Remained time: " + command.split(" ")[2] + " seconds.");

	}

}
