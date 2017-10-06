package battleships.GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyleConstants;

import battleships.client.CurrentPlayer;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import net.miginfocom.swing.MigLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.event.ActionEvent;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.CardLayout;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameScreen extends JFrame {

	private BackgroundPanel contentPane;
	private JPanel gamePanel;
	private JPanel timerPanel;
	private JPanel playerPanel;
	private JMenuBar menuBar;
	private JMenuItem quitMenuItem;
	private GUIControler controler;
	private MessagePanel messagePane;
	private JTextPane messagesTextPane;
	private JPanel titlePanel;
	private JLabel titleLabel;
	private JPanel myPanel;
	private JPanel oponentsPanel;
	private JComboBox comboBox;
	private TablePanel[] playingTables;
	private TablePanel myTable;
	private ArrayList<CurrentPlayer> playersImages = new ArrayList<CurrentPlayer>();
	private int time;
	private Timer timer;
	private JLabel TimeLeftLabel;
	private JLabel timerLabel;
	private JLabel ShotsLabel;
	private int operable;
	private JButton fireButton;
	private String message;
	private TablePanel showing;
	private CardLayout cl;
	private EndOfGame gameOver;
	private EndOfGame gameWon;
	private EndOfGame noVictory;
	private EndOfGame victory;
	private JLabel lblNewLabel;

	/**
	 * Create the frame.
	 */
	public GameScreen(ArrayList<CurrentPlayer> names, GUIControler controler) {
		setTitle("Battleships");
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controler.shutDown();
			}
		});
		this.controler = controler;
		for (int i = 0; i < names.size(); i++)
			playersImages.add(new CurrentPlayer(names.get(i)));
		message = "FIRE " + controler.getID() + " [";
		playingTables = new TablePanel[names.size()];
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 750, 500);
		setJMenuBar(getMenuBar_2());
		contentPane = new BackgroundPanel(WelcomeScreen.class.getResourceAsStream("/images/game.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		myTable = controler.getMyTable();
		contentPane.add(getMessagePane(), BorderLayout.SOUTH);
		contentPane.add(getGamePanel(), BorderLayout.CENTER);
		contentPane.add(getTimerPanel(), BorderLayout.WEST);
		contentPane.add(getPlayerPanel(), BorderLayout.EAST);
		contentPane.add(getTitlePanel(), BorderLayout.NORTH);
		setSize(timerPanel.getWidth() + myTable.getMyTable().getModel().getColumnCount() * 90 + 300,
				(myTable.getMyTable().getModel().getRowCount() + 1) * 30 + 350);

		makeCountDown();

		setResizable(false);
		setLocationRelativeTo(null);
	}

	private void timerJob() {
		--time;
		if (time == 0) {
			messagePane.displayMessageFromServer("Server is processing data...");
			timer.cancel();
		}

		if (time >= 0)
			timerLabel.setText(time + " seconds");

	}

	private void makeCountDown() {
		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {
			public void run() {
				timerJob();
			}

		}, 0, 1000);
	}

	private Component getMessagePane() {
		if (messagePane == null) {
			messagePane = controler.getMessagePanel();
			messagePane.displayMessageFromServer(
					"Clik on the field to select it. Change the oponents using dropdown menu.Use left mouse click do undo the selection.");
			messagePane.displayMessageFromServer("Be careful! YOU CAN ONLY PRESS FIRE ONCE!");

		}
		return messagePane;
	}

	private JTextPane getTextPane() {
		if (messagesTextPane == null) {
			messagesTextPane = new JTextPane();
		}
		return messagesTextPane;
	}

	private JPanel getGamePanel() {
		if (gamePanel == null) {
			gamePanel = new JPanel();
			gamePanel.setOpaque(false);
			gamePanel.setLayout(new GridLayout(0, 2, 0, 0));
			gamePanel.add(getMyPanel());
			gamePanel.add(getOponentsPanel());
		}
		return gamePanel;
	}

	private JPanel getTimerPanel() {
		if (timerPanel == null) {
			timerPanel = new JPanel();
			timerPanel.setOpaque(false);
			timerPanel.setLayout(new MigLayout("", "[]", "[grow][][][grow]"));
			timerPanel.add(getTimeLeftLabel(), "cell 0 1");
			timerPanel.add(getTimerLabel(), "cell 0 2");
		}
		return timerPanel;
	}

	private JPanel getPlayerPanel() {
		if (playerPanel == null) {
			playerPanel = new JPanel();
			playerPanel.setOpaque(false);
			playerPanel.setLayout(new MigLayout("", "[27px]", "[grow][][][][][][][19px,grow]"));
			playerPanel.add(getLblNewLabel(), "cell 0 1");
			playerPanel.add(getComboBox(), "cell 0 2,alignx left,aligny top");
			playerPanel.add(getFireButton(), "cell 0 5");
			playerPanel.add(getShotsLabel(), "cell 0 6");
		}
		return playerPanel;
	}

	private JMenuBar getMenuBar_2() {
		if (menuBar == null) {
			menuBar = new JMenuBar();
			menuBar.setBackground(new Color(47, 79, 79));
			menuBar.add(Box.createHorizontalGlue());
			menuBar.add(getQuitMenuItem());
		}
		return menuBar;
	}

	private JMenuItem getQuitMenuItem() {
		if (quitMenuItem == null) {
			quitMenuItem = new JMenuItem("Quit game");
			quitMenuItem.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controler.quitGame();
				}
			});
			quitMenuItem.setFont(new Font("Stencil", Font.PLAIN, 14));
			quitMenuItem.setForeground(new Color(192, 192, 192));
			quitMenuItem.setBackground(new Color(47, 79, 79));
			quitMenuItem.setHorizontalAlignment(SwingConstants.RIGHT);
			quitMenuItem.setMaximumSize(new Dimension(40, 50));
		}
		return quitMenuItem;
	}

	private JPanel getTitlePanel() {
		if (titlePanel == null) {
			titlePanel = new JPanel();
			titlePanel.setOpaque(false);
			titlePanel.add(getTitleLabel());
		}
		return titlePanel;
	}

	private JLabel getTitleLabel() {
		if (titleLabel == null) {
			titleLabel = new JLabel("ROUND 1");
			titleLabel.setForeground(new Color(128, 0, 0));
			titleLabel.setFont(new Font("Stencil", Font.PLAIN, 50));
		}
		return titleLabel;
	}

	private JPanel getMyPanel() {
		if (myPanel == null) {
			myPanel = new JPanel();
			myPanel.setOpaque(false);
			myPanel.add(myTable);
		}
		return myPanel;
	}

	private JPanel getOponentsPanel() {
		if (oponentsPanel == null) {
			oponentsPanel = new JPanel();
			oponentsPanel.setOpaque(false);
			cl = new CardLayout(0, 0);
			oponentsPanel.setLayout(cl);
			for (int i = 0; i < playersImages.size(); i++)
				oponentsPanel.add(getOponentsTable(i), playersImages.get(i).getName());

		}
		return oponentsPanel;
	}

	private TablePanel getOponentsTable(int i) {
		playingTables[i] = new TablePanel(myTable.getRowNumber(), myTable.getColumnNumber(), playersImages.get(i));
		playingTables[i].setOpaque(false);
		JTable table = playingTables[i].getMyTable();
		for (int i1 = 1; i1 < table.getColumnModel().getColumnCount(); i1++) {
			table.getColumnModel().getColumn(i1).setPreferredWidth(30);
			table.getColumnModel().getColumn(i1).setMaxWidth(30);
			table.getColumnModel().getColumn(i1).setCellRenderer(new PlayingTableRenderer());
		}

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent evt) {

				int row = table.rowAtPoint(evt.getPoint());
				int col = table.columnAtPoint(evt.getPoint());
				if (col >= 0 && ((GraphicTableModel) table.getModel()).isSelectable()) {
					if (SwingUtilities.isLeftMouseButton(evt)) {
						((GraphicTableModel) (table.getModel())).setPuttingShips(true);
						if (operable > 0) {
							--operable;
							if (table.getValueAt(row, col).equals("")) {
								table.setValueAt("X", row, col);
								((GraphicTableModel) (table.getModel())).fireTableCellUpdated(row, col);
								appendField(row, col, playingTables[i].getName());
							}
						}
					} else {
						((GraphicTableModel) (table.getModel())).setPuttingShips(false);
						if (table.getValueAt(row, col).equals("X")) {
							++operable;
							table.setValueAt("", row, col);
							((GraphicTableModel) (table.getModel())).fireTableCellUpdated(row, col);
							removeField("{" + playingTables[i].getName() + "}" + String.format("%02d", row)
									+ String.format("%02d", col - 1) + ";");
						}
					}
				}
				setShotField();
			}

			private void removeField(String remove) {
				if (fireButton.isEnabled()) {
					String first = message.substring(0, message.indexOf(remove));
					String last = (message.indexOf(remove) + remove.length() < message.length())
							? message.substring(message.indexOf(remove) + remove.length(), message.length()) : "";
					message = first + last;
				}
			}

			private void appendField(int row, int col, String name) {
				if (fireButton.isEnabled())
					message += "{" + name + "}" + String.format("%02d", row) + String.format("%02d", col - 1) + ";";
			}
		});

		return playingTables[i];
	}

	private void setShotField() {
		ShotsLabel.setText("Shots left: " + operable);
	}

	private JComboBox getComboBox() {
		if (comboBox == null) {
			String[] names = new String[playersImages.size()];
			for (int i = 0; i < names.length; i++)
				names[i] = playersImages.get(i).getName();

			comboBox = new JComboBox(names);
			comboBox.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					String playerName = (String) comboBox.getSelectedItem();
					cl.show(oponentsPanel, playerName);

				}
			});
			comboBox.setSelectedIndex(0);
			comboBox.setForeground(new Color(128, 0, 0));
			comboBox.setFont(new Font("Stencil", Font.PLAIN, 16));
		}
		return comboBox;
	}

	public void setTimer(int x) {
		time = x;
	}

	private JLabel getTimeLeftLabel() {
		if (TimeLeftLabel == null) {
			TimeLeftLabel = new JLabel("Time left:");
			TimeLeftLabel.setForeground(new Color(128, 0, 0));
			TimeLeftLabel.setFont(new Font("Stencil", Font.PLAIN, 18));
		}
		return TimeLeftLabel;
	}

	private JLabel getTimerLabel() {
		if (timerLabel == null) {
			timerLabel = new JLabel("");
			timerLabel.setForeground(new Color(128, 0, 0));
			timerLabel.setFont(new Font("Stencil", Font.PLAIN, 18));
		}
		return timerLabel;
	}

	public void updateActivePlayers(int round, ArrayList<CurrentPlayer> allPlayers) {
		titleLabel.setText("ROUND " + round);
		fireButton.setEnabled(true);
		message = "FIRE " + controler.getID() + " [";
		boolean throwout = true;
		for (int j = 0; j < playersImages.size(); j++) {
			throwout = true;
			CurrentPlayer active = playersImages.get(j);
			System.out.println("Pretrazuje za " + active.getName());
			int i = 0;
			while (i < allPlayers.size() && throwout == true) {
				CurrentPlayer player = allPlayers.get(i);
				System.out.println("Uporeduje sa " + player.getName());
				if (active.getName().equals(player.getName()))
					throwout = false;
				i++;
			}
			if (throwout) {
				TablePanel temp = null;
				for (Component comp : oponentsPanel.getComponents()) {
					if (comp.isVisible() == true) {
						temp = (TablePanel) comp;
						break;
					}
				}
				if (temp.getName().equals(active.getName()))
					cl.next(oponentsPanel);
				messagePane.displayMessageFromServer("Player " + active.getName() + " lost all ships!");
				comboBox.removeItemAt(playersImages.indexOf(active));
				playersImages.remove(j);

			}
		}

		operable = controler.getNumOfOperable();
		makeCountDown();
		setShotField();
	}

	private JLabel getShotsLabel() {
		if (ShotsLabel == null) {
			ShotsLabel = new JLabel("Shots left:");
			ShotsLabel.setOpaque(true);
			ShotsLabel.setForeground(new Color(128, 0, 0));
			ShotsLabel.setFont(new Font("Stencil", Font.PLAIN, 18));
		}
		return ShotsLabel;
	}

	private JButton getFireButton() {
		if (fireButton == null) {
			fireButton = new JButton("FIRE!");
			fireButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					operable = 0;
					String str = message.toString();
					str = str.substring(0, str.length() - 1) + "]";
					controler.sendFire(str);
					fireButton.setEnabled(false);
					ShotsLabel.setText("Shots left: 0");
				}
			});
			fireButton.setFont(new Font("Stencil", Font.PLAIN, 15));
			fireButton.setForeground(new Color(128, 0, 0));
		}
		return fireButton;
	}

	public void setShotsNumber(int numOfOperableSeg) {
		operable = numOfOperableSeg;
		setShotField();
	}

	public JTable getTable(String player) {
		int i = 0;
		while (i < playingTables.length && !playingTables[i].getName().equals(player))
			i++;
		if (i < playingTables.length)
			return playingTables[i].getMyTable();

		return myTable.getMyTable();
	}

	public void makeGO() {
		gameOver = new EndOfGame("Game Over. You lost all of your ships.", controler);
		gameOver.setVisible(true);
	}

	public void makeGW(String player) {
		gameWon = new EndOfGame("Game over. Game won player " + player, controler);
		gameWon.setVisible(true);
	}

	public void makeNV() {
		noVictory = new EndOfGame("Game over. There is no victory.", controler);
		noVictory.setVisible(true);
	}

	public void makeV() {
		victory = new EndOfGame("Congratulations! You won!", controler);
		victory.setVisible(true);
	}

	private JLabel getLblNewLabel() {
		if (lblNewLabel == null) {
			lblNewLabel = new JLabel("Oponents:");
			lblNewLabel.setForeground(new Color(128, 0, 0));
			lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 18));
		}
		return lblNewLabel;
	}
}
