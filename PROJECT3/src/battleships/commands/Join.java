package battleships.commands;


import battleships.client.BattleshipsPlayer;

public class Join extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		command = "JOIN "+command;
			player.getSender().setMessage(command);
	}

}
