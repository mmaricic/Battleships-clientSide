package battleships.commands;

import battleships.GUI.GUIControler;
import battleships.client.BattleshipsPlayer;

public class DuplicateName extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException{
		synchronized (player.outputSemaphore) {
				player.getControler().getNewName();
		}
	}

}
