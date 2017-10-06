package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class ForceDisconnect extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
	
		player.getControler().setForceDisconnectFrame();
	}

}
