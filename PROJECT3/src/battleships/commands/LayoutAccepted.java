package battleships.commands;

import battleships.client.BattleshipsPlayer;
import battleships.common.*;

public class LayoutAccepted extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		player.getControler().layoutIsAccepted();
	}

}
