package battleships.commands;

import battleships.client.BattleshipsPlayer;

public class StateDeployShips extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		player.timeLeft = Integer.parseInt(command.split(" ")[1]);
		if(!player.getControler().demanded)
			player.getControler().setStatus("Deploy ships. Remained time: " + command.split(" ")[1] + " seconds");
		else
			player.getControler().demanded = false;
	}

}
