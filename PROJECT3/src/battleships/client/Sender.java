package battleships.client;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import battleships.communication.Client;

public class Sender extends Thread {
	String message = "";
	private BattleshipsPlayer player;
	private Client client;

	public Sender(BattleshipsPlayer bp, Client client) {
		player = bp;
		this.client = client;
		start();
	}

	public void run() {
		try {
			while (!interrupted()) {
				synchronized (this) {
					while (message.isEmpty())
						wait();

					client.send(message);
					message = "";
					notify();
				}

			}

		} catch (IOException ex) {
			Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException e) {
		}
	}

	public synchronized void setMessage(String command) {
		try {
			while (!message.isEmpty())
				wait();
			message = command;
			notify();
		} catch (InterruptedException e) {
		}
	}
}
