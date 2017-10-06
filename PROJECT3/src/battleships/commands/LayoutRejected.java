package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class LayoutRejected extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		player.getControler().setStatus("Your layout is rejected!");

	}

}
