package battleships.commands.addressCommands;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GetAddressCommandLine extends GetAddress {
	String[] address;
	static GetAddressCommandLine instance = null;

	private GetAddressCommandLine(String[] address) {
		this.address = address;
	}

	@Override
	public InetAddress getServerIP() throws UnknownHostException {
		return InetAddress.getByName(address[0]);
	}

	public static GetAddressCommandLine makeObject(String[] address) {
		if (instance == null)
			instance = new GetAddressCommandLine(address);
		return instance;
	}
}
