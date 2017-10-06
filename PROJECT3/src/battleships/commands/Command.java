package battleships.commands;

import battleships.client.BattleshipsPlayer;
import battleships.print.Output;

public abstract class Command {
	static protected Output output = Output.makeObject();

	public abstract void execute(BattleshipsPlayer player, String command) throws InterruptedException;

}
