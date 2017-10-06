package battleships.commands.addressCommands;

import java.net.InetAddress;
import java.net.UnknownHostException;
import battleships.client.Citaj;
import battleships.print.*;

public class GetAddressFromPlayer extends GetAddress {
static GetAddressFromPlayer instance = null;
	private GetAddressFromPlayer() {}
	
	@Override
	public InetAddress getServerIP() throws UnknownHostException {
		Output.IPAddressDemand();
		String IP = Citaj.Line();
		return InetAddress.getByName(IP);
	}

	public static GetAddressFromPlayer makeObject() {
		if (instance == null)
			instance = new GetAddressFromPlayer();
		return instance;
	}
}
