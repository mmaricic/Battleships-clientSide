package battleships.client;

import battleships.communication.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import battleships.GUI.DeployShipsScreen;
import battleships.GUI.GUIControler;
import battleships.GUI.LoginScreen;
import battleships.GUI.WelcomeScreen;
import battleships.commands.*;
import battleships.commands.Error;
//import battleships.commands.addressCommands.*;
import battleships.common.*;

/**
 *
 * @author POOP
 */
public class BattleshipsPlayer {
	private Client client;
	boolean messageRead = true;
	private Receiver reciever;
	private Sender sender;
	private InetAddress address;
	private int port;
	private BufferedImage image = null;
	private String name;
	private String message;
	private String ID;
	private String[] splitedMessage;
	private boolean enable = true;
	private int tableDimRow;
	private int tableDimCol;
	public Semaphore outputSemaphore = new Semaphore();
	private ArrayList<CurrentPlayer> allPlayers = new ArrayList<CurrentPlayer>();
	
	private GUIControler controler; 

	// static private HashMap<Integer, GetAddress> AddressMap = new
	// HashMap<Integer, GetAddress>();
	public static HashMap<String, Command> CommandsMap = new HashMap<String, Command>();
	private HashMap<String, Table> GameTables = new HashMap<String, Table>();
	public int timeLeft;

	public HashMap<String, Table> activePlayers() {
		return GameTables;
	}
	
	public Client getClient() {
		return client;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTableDim(int row, int col) {
		tableDimRow = row;
		tableDimCol = col;
	}

	public int getRowNum() {
		return tableDimRow;
	}

	public int getColumnNum() {
		return tableDimCol;
	}

	public BattleshipsPlayer insertTable(String name) {
		GameTables.put(name, new Table(tableDimRow, tableDimCol));
		return this;
	}

	public Table getTable(String name) {
		return GameTables.get(name);
	}

	public void disable() {
		enable = false;
	}

	public boolean enabled() {
		return enable;
	}

	public void setID(String id) {
		ID = id;
	}

	public String getID() {
		return ID;
	}

	private void initMaps(String[] args) {
		// AddressMap.put(0, GetAddressFromPlayer.makeObject());
		// AddressMap.put(1, GetAddressCommandLine.makeObject(args));
		CommandsMap.put(CommunicationCommands.PASSWORD_MESSAGE, new PasswordRequired());
		CommandsMap.put(CommunicationCommands.ACCESS_DENIED_MESSAGE, new AccessDenied());
		CommandsMap.put(CommunicationCommands.NAME_MESSAGE, new DuplicateName());
		CommandsMap.put(CommunicationCommands.WELCOME_MESSAGE, new Welcome());
		CommandsMap.put(CommunicationCommands.QUIT_REQUEST, new Quit());
		CommandsMap.put(CommunicationCommands.FORCE_DISCONNECT_MESSAGE, new ForceDisconnect());
		CommandsMap.put(CommunicationCommands.WFP_STATE_MESSAGE, new WaitingForPlayers());
		CommandsMap.put(CommunicationCommands.DS_STATE_MESSAGE, new StateDeployShips());
		CommandsMap.put(CommunicationCommands.R_STATE_MESSAGE, new StateRound());
		CommandsMap.put(CommunicationCommands.UPDATE_STATE_MESSAGE, new StateUpdate());
		CommandsMap.put(CommunicationCommands.DEPLOY_SHIPS_MESSAGE, new GameSpecification());
		CommandsMap.put(CommunicationCommands.LAYOUT_ACCEPTED_MESSAGE, new LayoutAccepted());
		CommandsMap.put(CommunicationCommands.LAYOUT_REJECTED_MESSAGE, new LayoutRejected());
		CommandsMap.put(CommunicationCommands.ROUND_MESSAGE, new Round());
		CommandsMap.put(CommunicationCommands.FIRE_ACCEPTED_MESSAGE, new FireAccepted());
		CommandsMap.put(CommunicationCommands.FIRE_REJECTED_MESSAGE, new FireRejected());
		CommandsMap.put(CommunicationCommands.UPDATE_MESSAGE, new Update());
		CommandsMap.put(CommunicationCommands.VICTORY_MESSAGE, new Victory());
		CommandsMap.put(CommunicationCommands.NO_VICTORY_MESSAGE, new NoVictory());
		CommandsMap.put(CommunicationCommands.GAME_OVER_MESSAGE, new GameOver());
		CommandsMap.put(CommunicationCommands.GAME_WON_MESSAGE, new GameWon());
		CommandsMap.put(CommunicationCommands.ERROR_MESSAGE, new Error());
		CommandsMap.put(CommunicationCommands.JOIN_MESSAGE, new Join());
		CommandsMap.put(CommunicationCommands.IMAGE_MESSAGE, new OponentsImage());

	}

	void receiveAndExecute() throws InterruptedException, NumberFormatException {
		synchronized(this){
		message = reciever.message;
		messageRead = true;
		notifyAll();
		}
		String[] splitedMessage = message.split(" ");
		synchronized (CommandsMap) {
			if (CommandsMap.get(splitedMessage[0]) != null)
				CommandsMap.get(splitedMessage[0]).execute(this, message);
			else if (message.substring(0, 2).equals("D="))
				CommandsMap.get("D").execute(this, message);
		}

	}

	public BattleshipsPlayer(String[] args, GUIControler controler) throws SocketException, IOException, InterruptedException {
		this.controler = controler;
		initMaps(args);
		//synchronized (outputSemaphore) {
		//address = AddressMap.get(args.length).getServerIP();
	//}
		address = InetAddress.getByName(args[0]);
		port = Integer.parseInt(args[1]);
		client = new Client(address, port);
		reciever = new Receiver(this, client);
		sender = new Sender(this, client);

		sendJoin();
	}

	
	public Sender getSender() {
		return sender;
	}

	
	public void setImage(String fileName) {
		try {
			image = ImageIO.read(new File(fileName));
		} catch (IOException e) {
			Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, e);
		}

	}

	
	public BufferedImage getImage(){
		return image;
	}
	
	public String getImageAsString() {
		if (image != null) {
			StringBuilder str = new StringBuilder("[");
			for (int i = 0; i < 64; i++)
				for (int j = 0; j < 64; j++) {
					int rgb = image.getRGB(i, j);
					str.append(((rgb >> 16) & 0xff) + "," + ((rgb >> 8) & 0xff) + "," + (rgb & 0xff));
					str.append((i == 63 && j == 63) ? "]" : ",");

				}
			return str.toString();
		} else
			return null;
	}
	

	public void executeExit() {
		sender.interrupt();
		reciever.interrupt();
	}

	public GUIControler getControler() {
		return controler;
	}

	public void sendJoin() throws InterruptedException, NumberFormatException {
		synchronized (CommandsMap) {
			String command = controler.getJoinInfo();
			CommandsMap.get(CommunicationCommands.JOIN_MESSAGE).execute(this, command);
		}
		
	}

	public void makeMyTable() {
		insertTable(name);
	}

	public void setMyShip(Coordinate coordinate, int numOfSeg, char or) {
		Table myTable = getTable(name);
		Ship newShip = new Ship(numOfSeg);
		newShip.orient(or);
		if(myTable.setShip(newShip, coordinate)){
			System.out.println("brod je postavljen\nkoordinate: ");
			int [] coloringFields= new int [numOfSeg*2];
			for(int i = 0; i <numOfSeg; i++){
			coloringFields[2*i] = newShip.getSegmentsX(i);
			coloringFields[2*i+1] = newShip.getSegmentsY(i);
			System.out.println(coloringFields[2*i] + " "+coloringFields[2*i+1]+" ");
			}
			System.out.println(myTable);
			controler.colorMyFields(coloringFields, true);
		}else
			System.out.println("Brod nije postavljen " + newShip);
	}

	public void removeMyShip(Coordinate coordinate) {
		Ship ship = getTable(name).getShipOnCoordinate(coordinate);
		if (ship != null){
			int numOfSeg = ship.size();
			int [] coloringFields= new int [numOfSeg*2];
			for(int i = 0; i <numOfSeg; i++){
			coloringFields[2*i] = ship.getSegmentsX(i);
			coloringFields[2*i+1] = ship.getSegmentsY(i);
			}
			controler.colorMyFields(coloringFields, false);
		}
		
	}

	public void deployReady() {
		if(getTable(name).numOfOperableSeg() != 0)
			sender.setMessage("SHIP_LAYOUT "+ID+" "+getTable(name));
		
	}

	public void demandState() {
		sender.setMessage("STATE "+ID);
	}

	public void insertPlayer(String name, BufferedImage bufferedImage) {
		allPlayers.add(new CurrentPlayer(name, bufferedImage));
	}

	public ArrayList<CurrentPlayer> getAllPlayers() {
		return allPlayers;
	}

	public int operableSegments() {
		return GameTables.get(name).numOfOperableSeg();
	}

	public void fire(String str) {
		sender.setMessage(str);
	}


}
