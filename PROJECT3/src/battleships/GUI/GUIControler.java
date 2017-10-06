package battleships.GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.BorderFactory;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.MatteBorder;

import battleships.client.BattleshipsPlayer;
import battleships.client.CurrentPlayer;
import battleships.common.Coordinate;

public class GUIControler {
	private LoginScreen login;
	private BattleshipsPlayer newPlayer;
	private WelcomeScreen welcome;
	private DeployShipsScreen deployShips;
	private TablePanel playerTable;
	private GameScreen game = null;
	private BufferedImage temp;
	private MessagePanel messagePanel;
	private ForceDisconnectFrame forceDiscFrame;
	public boolean demanded = false;

	public GUIControler(LoginScreen login) {
		this.login = login;
		forceDiscFrame = new ForceDisconnectFrame(this);
	}

	public void shutDown() {
		int quit = JOptionPane.showConfirmDialog(login, "Are you sure you want to exit the game?", "EXIT",
				JOptionPane.YES_NO_OPTION);

		if (quit == JOptionPane.YES_OPTION) {
			if (login != null)
				login.dispose();
			if (welcome != null)
				welcome.dispose();
			if (deployShips != null)
				deployShips.dispose();
			if (game != null)
				game.dispose();
			if (forceDiscFrame != null)
				forceDiscFrame.dispose();
			if (newPlayer != null)
				newPlayer.executeExit();
		}
		System.exit(0);
	}

	public void connect() {
		try {
			if (newPlayer == null) {
				String[] string = new String[] { login.getIP(), login.getPort() };
				newPlayer = new BattleshipsPlayer(string, this);
				if (!login.getImageText().equals(""))
					newPlayer.setImage(login.getImageText());
			} else
				newPlayer.sendJoin();

		} catch (SocketException ex) {
			Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (UnknownHostException ex) {
			Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(BattleshipsPlayer.class.getName()).log(Level.SEVERE, null, ex);
		} catch (NumberFormatException ex) {
			System.out.println("Wrong game specification!");
		} catch (InterruptedException e) {
		}
	}

	public String getJoinInfo() {
		return login.loginInfo();
	}

	public void setWaitingMenu() {

		if (welcome == null) {
			welcome = new WelcomeScreen(this);
		}
		welcome.setVisible(true);
		login.setVisible(false);
	}

	public void getImageFromSystem() {
		JFileChooser jfcOpen = new JFileChooser();
		jfcOpen.setCurrentDirectory(new File(System.getProperty("user.home")));
		int povratnaVrednost = jfcOpen.showOpenDialog(login.getContentPane());
		if (povratnaVrednost == JFileChooser.APPROVE_OPTION) {
			String fileName = jfcOpen.getSelectedFile().getAbsolutePath();
			login.setImageText(fileName);
		}

	}

	public void setMyTable(JTable table) {
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		table.getTableHeader().setBackground(new Color(192, 192, 192));
		table.getTableHeader().setForeground(new Color(128, 0, 0));
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setBackground(new Color(184, 236, 249));
		table.getColumnModel().getColumn(0).setCellRenderer(new ColumnColorRenderer());
		table.getTableHeader().setFont(new Font("Stencil", Font.PLAIN, 12));
		for (int i = 1; i < table.getColumnModel().getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(30);
			table.getColumnModel().getColumn(i).setMaxWidth(30);
			table.getColumnModel().getColumn(i).setCellRenderer(new SetShipColorRenderer());
		}

	}

	public void checkForEmptyFields() {
		if (login.getIP().isEmpty() || login.getPort().isEmpty() || login.getUsername().isEmpty()) {
			login.setMessageText("Fields \"IP\", \"Port\" and \"Username\" cannot be empty!");
			login.setEmptyBorder();
		} else
			connect();
	}

	public void quitGame() {
		login.resetFields();
		login.resetBorders();
		login.setMessageText("");
		if(newPlayer != null)
			newPlayer.getSender().setMessage("QUIT " + newPlayer.getID());
		newPlayer = null;
		if (login != null)
			login.setVisible(true);
		if (welcome != null)
			welcome.setVisible(false);
		else
			welcome = null;
		if (deployShips != null)
			deployShips.setVisible(false);
		else
			deployShips = null;
		if (game != null)
			game.setVisible(false);
		else
			game = null;
	}

	public void getNewPasword() {
		login.UpdateGetNewPassword();
	}

	public void getNewName() {
		login.UpdateGetNewName();
	}

	public void demandPassword() {
		login.UpdateGetPassword();
	}

	public String getUsername() {
		return login.getUsername();
	}

	public void startShipLayout(int row, int col, int[] shipLayoutArray, CurrentPlayer player) {
		if (deployShips == null) {
			player.setImage(temp);
			deployShips = new DeployShipsScreen(row, col, this, shipLayoutArray, player);
		}
		deployShips.setVisible(true);
		welcome.setVisible(false);

	}

	public void attemptToSetShip(int row, int col, int numOfSeg, char or) {
		newPlayer.setMyShip(new Coordinate(row, col), numOfSeg, or);

	}

	public void attemptToRemoveShip(int row, int col) {
		newPlayer.removeMyShip(new Coordinate(row, col));
	}

	public void setPlayerTable(TablePanel table) {
		playerTable = table;
	}

	public void colorMyFields(int[] coloringFields, boolean set) {
		GraphicTableModel tableModel = (GraphicTableModel) (playerTable.getMyTable().getModel());
		tableModel.setPuttingShips(set);
		for (int i = 0; i < coloringFields.length / 2; i++) {
			if (set)
				tableModel.setValueAt(" ", coloringFields[2 * i], coloringFields[2 * i + 1] + 1);
			else
				tableModel.setValueAt("", coloringFields[2 * i], coloringFields[2 * i + 1] + 1);
			tableModel.fireTableCellUpdated(coloringFields[2 * i], coloringFields[2 * i + 1] + 1);
		}
		deployShips.changeTextFields(coloringFields.length / 2, set);
	}

	public void sendDeploy() {
		newPlayer.deployReady();
	}

	public void layoutIsAccepted() {
		messagePanel.displayMessageFromServer("Your layout is accepted!");
		messagePanel.displayMessageFromServer("Please wait while the other players finnish deploy");
		deployShips.disableAll();
	}

	public int getTime() {
		demanded = true;
		newPlayer.demandState();
		return newPlayer.timeLeft;
	}

	public void startGame(ArrayList<CurrentPlayer> names) {
		if (game == null)
			game = new GameScreen(names, this);
		deployShips.setVisible(false);
		game.setVisible(true);

	}

	public TablePanel getMyTable() {
		return playerTable;
	}

	public void setTimer(int x) {
		game.setTimer(x);
	}

	public void updateActivePlayers(int round, ArrayList<CurrentPlayer> allPlayers) {
		game.updateActivePlayers(round, allPlayers);
	}

	public int getNumOfOperable() {
		return newPlayer.operableSegments();
	}

	public String getID() {
		return newPlayer.getID();
	}

	public void sendFire(String str) {
		newPlayer.fire(str);
	}

	public void setStartingNumOfFires(int numOfOperableSeg) {
		game.setShotsNumber(numOfOperableSeg);
	}

	public void updateImage(BufferedImage img) {
		temp = img;
	}

	public void setShotField(String player, int row, int col, char mark) {
		JTable table = game.getTable(player);
		GraphicTableModel tableModel = (GraphicTableModel) (table.getModel());
		if (mark == 'M')
			if (player.equals(playerTable.getName()))
				tableModel.setValueAt(tableModel.getValueAt(row, col+1), row, col + 1);
			else
				tableModel.setValueAt(" ", row, col + 1);
		else
			tableModel.setValueAt("  ", row, col + 1);

		tableModel.fireTableCellUpdated(row, col + 1);
	}

	public void setStatus(String string) {
		messagePanel.displayMessageFromServer(string);
	}

	public void setMessagePanel(MessagePanel mp) {
		messagePanel = mp;
	}

	public MessagePanel getMessagePanel() {
		return messagePanel;
	}

	public void setForceDisconnectFrame() {

		forceDiscFrame.setVisible(true);
	}

	public void setGO() {
		if (game != null)
			game.makeGO();
		else
			deployShips.makeGO();
	}

	public void setGW(String player) {
		if (game != null)
			game.makeGW(player);
		else
			deployShips.makeGW(player);
	}

	public void setNV() {
		if (game != null)
			game.makeNV();
		else
			deployShips.makeNV();
	}

	public void setV() {
		if (game != null)
			game.makeV();
		else
			deployShips.makeV();
	}

}
