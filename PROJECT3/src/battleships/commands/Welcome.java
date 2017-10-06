package battleships.commands;

import battleships.GUI.GUIControler;
import battleships.client.BattleshipsPlayer;

public class Welcome extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		StringBuilder str = new StringBuilder();
		str.append("IMAGE ");
		String [] helpString = command.split(" ");
		player.setID(helpString[1]);
		str.append(helpString[1]+" ");
		player.setName(player.getControler().getUsername());
		String image = player.getImageAsString();
		
		if(image != null){
		str.append(player.getImageAsString());
		player.getSender().setMessage(str.toString());
		}
		
		player.getControler().setWaitingMenu();
	}
}
