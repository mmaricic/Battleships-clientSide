package battleships.commands.addressCommands;

import java.net.InetAddress;
import java.net.UnknownHostException;

public abstract class GetAddress {
	public abstract InetAddress getServerIP() throws UnknownHostException;
}
