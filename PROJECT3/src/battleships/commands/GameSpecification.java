package battleships.commands;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import battleships.client.BattleshipsPlayer;
import battleships.client.CurrentPlayer;

public class GameSpecification extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException, NumberFormatException {

			String message = "CONFIRM_DEPLOY " + player.getID() + " " + command;
			player.getSender().setMessage(message);
		
		String[] separatedFields = command.split(";");
		String [] helpString = separatedFields[0].split("=");
		int [] shipLayoutArray = new int [(separatedFields.length-1)*2];
		for(int i = 0; i < shipLayoutArray.length/2; i++){
			String [] tempArray = separatedFields[i+1].split("=");
			shipLayoutArray[i*2] = Integer.parseInt(tempArray[0].substring(tempArray[0].indexOf('(')+1, tempArray[0].indexOf(')')));
			shipLayoutArray[i*2+1] = Integer.parseInt(tempArray[1]);
		}
		int row = Integer.parseInt(helpString[1]);
		int col = Integer.parseInt(helpString[1]);
		player.setTableDim(row, col);
		
		player.makeMyTable();
		player.getControler().startShipLayout(row, col, shipLayoutArray, new CurrentPlayer(player.getName(), player.getImage()));
	}

}
