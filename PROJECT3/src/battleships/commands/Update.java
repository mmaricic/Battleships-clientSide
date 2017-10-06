package battleships.commands;

import battleships.client.BattleshipsPlayer;
import battleships.common.*;

public class Update extends Command {

	@Override
	public void execute(BattleshipsPlayer player, String command) throws InterruptedException {
		String [] helpArray = command.split(" ");
		command = helpArray[1].substring(1, helpArray[1].length()-1);
		if(command.equals(""))
			return;
		helpArray = command.split(";");
		
		for(int i= 0; i < helpArray.length; i++){
			String [] temp = helpArray[i].split("}");
			temp[0] = temp[0].substring(1, temp[0].length());
			Table table = player.getTable(temp[0]);
			Coordinate crdnt = Coordinate.makeCoordinate(temp[1].substring(0, temp[1].length()-1));
			int row = Integer.parseInt(temp[1].substring(0, 2));
			int col = Integer.parseInt(temp[1].substring(2, 4));
			player.getControler().setShotField(temp[0], row, col, temp[1].charAt(temp[1].length()-1));
			if(temp[1].charAt(temp[1].length()-1) == 'H'){
				if(temp[0].equals(player.getName())){
					table.getShipOnCoordinate(crdnt).disableSegment(crdnt);
				}
				table.insertHittedSpot(crdnt);
			}else
				table.insertMissedSpot(crdnt);
		}
		
		Table table = player.getTable(player.getName());
		if(table.numOfOperableSeg() == 0)
			player.disable();
		
	}

}
