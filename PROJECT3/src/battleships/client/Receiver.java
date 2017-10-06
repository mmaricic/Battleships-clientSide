package battleships.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import battleships.communication.Client;

public class Receiver extends Thread {
	String message;
	private BattleshipsPlayer player;
	private Client client;

	public Receiver(BattleshipsPlayer bp, Client client) {
		player = bp;
		this.client = client;
		start();
	}

	public void run() {
		try {
			while (!interrupted()) {
				synchronized (player) {
					while (!player.messageRead)
						player.wait();
					message = client.receive();
					System.out.println(message);
					player.receiveAndExecute();
				}
			}
		} catch (InterruptedException e) {
		} catch (IOException ex) {
			Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
		}

	}
}
