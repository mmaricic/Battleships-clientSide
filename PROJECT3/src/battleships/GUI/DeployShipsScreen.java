package battleships.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import battleships.client.CurrentPlayer;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTable;
import java.awt.GridLayout;
import java.awt.TextArea;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import net.miginfocom.swing.MigLayout;
import javax.swing.JTextField;
import javax.swing.JTextPane;

import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JRadioButton;
import java.awt.Font;

public class DeployShipsScreen extends JFrame {

	private JPanel contentPane;
	private JScrollPane tablePane;
	private MessagePanel messagePane;
	private JTextPane messagesTextPane;
	private StyledDocument textPaneStyleDoc;
	private Style textPaneStyle;
	private int count = 0;
	private JTable table;
	private int row, col;
	private int[] pairs;
	private JTextField[] numOfShips;
	private GUIShip[] ships;
	private GUIControler controler;
	private JPanel shipPanel;
	private JLabel numShipsLabel;
	private TablePanel panel;
	private int selSeg = -1, selNum = -1, time = -100;
	private JRadioButton rdbtnH;
	private JRadioButton rdbtnV;
	private ButtonGroup orientation = new ButtonGroup();
	private JLabel orientationLabel;
	private JButton doneButton;
	private JPanel headPanel;
	private JLabel Heading;
	private JPanel eastPanel;
	private JLabel timerLabel;
	private Timer timer;
	private JLabel lblTimeLeft;
	private CurrentPlayer player;
	private EndOfGame gameOver;
	private EndOfGame gameWon;
	private EndOfGame noVictory;
	private EndOfGame victory;

	/**
	 * Create the frame.
	 */
	public DeployShipsScreen(int row, int col, GUIControler controler, int[] pairs, CurrentPlayer player) {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controler.shutDown();
			}
		});

		setTitle("Battleships");
		this.controler = controler;
		this.row = row;
		this.col = col;
		this.pairs = pairs;
		this.player = player;
		ships = new GUIShip[pairs.length / 2];
		numOfShips = new JTextField[pairs.length / 2];
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 650, 400);
		contentPane = new BackgroundPanel(GameScreen.class.getResourceAsStream("/images/deploy.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		contentPane.setLayout(new BorderLayout(0, 0));

		contentPane.add(getHeadPanel(), BorderLayout.NORTH);
		contentPane.add(getMessagePane(), BorderLayout.SOUTH);
		contentPane.add(getShipPanel(), BorderLayout.WEST);
		contentPane.add(getEastPanel(), BorderLayout.EAST);
		contentPane.add(getPanel(), BorderLayout.CENTER);

		setResizable(false);

		timer = new Timer();

		timer.scheduleAtFixedRate(new TimerTask() {

			public void run() {
				timerJob();

			}

		}, 0, 1000);

		displayMessageFromServer(
				"Select the ship, choose orientation, and place it on the wanted fields. Use left mouse click to undo the placement.");

		setLocationRelativeTo(null);
	}

	private void timerJob() {
		if (time < 0)
			time = controler.getTime();
		else
			--time;

		if(time == 2){
			if (doneButton.isEnabled()) {
				controler.sendDeploy();
				doneButton.setEnabled(false);
			}
		}
		
		if (time == 1) {
			timer.cancel();
		}
		if (time > 0)
			timerLabel.setText((time - 1) + " seconds");

	}

	private GUIShip getGUIShip(int i, int num) {
		GUIShip ship = new GUIShip(i);
		ship.addActionListener(new ButtonListener(ship, i, num));
		return ship;
	}

	private class ButtonListener implements ActionListener {
		private JButton button;
		private int seg, num;

		public ButtonListener(JButton button, int seg, int num) {
			this.button = button;
			this.num = num;
			this.seg = seg;
		}

		public void actionPerformed(ActionEvent ev) {
			if (button.isEnabled()) {
				for (int i = 0; i < ships.length; i++)
					ships[i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
				button.setBorder(BorderFactory.createLineBorder(new Color(0, 204, 204), 2));
			}
			selSeg = seg;
			selNum = num;
		}
	}

	private MessagePanel getMessagePane() {
		if (messagePane == null) {
			messagePane = new MessagePanel(getTextPane());
		}
		controler.setMessagePanel(messagePane);
		return messagePane;
	}

	private JTextPane getTextPane() {
		if (messagesTextPane == null) {
			messagesTextPane = new JTextPane();
		}
		return messagesTextPane;
	}

	public void displayMessageFromServer(String message) {
		messagePane.displayMessageFromServer(message);
	}

	private JPanel getShipPanel() {
		if (shipPanel == null) {
			shipPanel = new JPanel();
			shipPanel.setOpaque(false);
			shipPanel.setLayout(new MigLayout("", "[10px][5px][100px]",
					"[][][][10px][][10px][][10px][][10px][][][][][][][200px][][]"));
			shipPanel.add(getNumShipsLabel(), "cell 0 1,growx");
			int i = 0;
			for (; i < pairs.length / 2; i++) {
				ships[i] = getGUIShip(pairs[i * 2], i);
				numOfShips[i] = getTextField(pairs[i * 2 + 1]);
				shipPanel.add(numOfShips[i], "cell 0 " + (i * 2 + 2) + ",growx");
				shipPanel.add(ships[i], "cell 2 " + (i * 2 + 2));
			}
			shipPanel.add(getOrientationLabel(), "cell 0 " + (i * 2 + 4) + ",growx");
			shipPanel.add(getRdbtnH(), "cell 0 " + (i * 2 + 5));
			shipPanel.add(getRdbtnV(), "cell 0 " + (i * 2 + 6));
			orientation.add(rdbtnH);
			orientation.add(rdbtnV);
			rdbtnH.setSelected(true);

		}
		return shipPanel;
	}

	private JButton getDoneButton() {
		if (doneButton == null) {
			doneButton = new JButton("DONE");
			doneButton.setFont(new Font("Stencil", Font.PLAIN, 16));
			doneButton.setForeground(new Color(128, 0, 0));
			doneButton.setHorizontalAlignment(SwingConstants.LEADING);

			doneButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controler.sendDeploy();
					doneButton.setEnabled(false);
				}
			});
		}
		return doneButton;
	}

	private JLabel getOrientationLabel() {
		if (orientationLabel == null) {
			orientationLabel = new JLabel("Ship orientation:");
			orientationLabel.setFont(new Font("Stencil", Font.PLAIN, 14));
			orientationLabel.setForeground(new Color(128, 0, 0));
		}
		return orientationLabel;
	}

	private JLabel getNumShipsLabel() {

		if (numShipsLabel == null) {
			numShipsLabel = new JLabel();
			numShipsLabel.setFont(new Font("Stencil", Font.PLAIN, 14));
			numShipsLabel.setForeground(new Color(128, 0, 0));

			numShipsLabel.setText("Ships left:");
		}
		return numShipsLabel;
	}

	private JTextField getTextField(int i) {
		JTextField textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setColumns(2);
		textField.setPreferredSize(new Dimension(25, 25));
		textField.setFont(new Font("Stencil", Font.PLAIN, 14));
		textField.setForeground(new Color(128, 0, 0));
		textField.setText("" + i);
		textField.setEditable(false);
		return textField;
	}

	private TablePanel getPanel() {
		if (panel == null) {
			panel = new TablePanel(row, col, player);
			table = panel.getMyTable();
			panel.setOpaque(false);

			for (int i = 1; i < table.getColumnModel().getColumnCount(); i++) {
				table.getColumnModel().getColumn(i).setPreferredWidth(30);
				table.getColumnModel().getColumn(i).setMaxWidth(30);
				table.getColumnModel().getColumn(i).setMinWidth(30);
				table.getColumnModel().getColumn(i).setCellRenderer(new SetShipColorRenderer());
			}

			setSize(shipPanel.getWidth() + table.getModel().getColumnCount() * 65 + 340,
					(table.getModel().getRowCount() + 1) * 44 + 350);

			controler.setPlayerTable(panel);

			table.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent evt) {

					int row = table.rowAtPoint(evt.getPoint());
					int col = table.columnAtPoint(evt.getPoint());
					if (col >= 0 && ((GraphicTableModel) table.getModel()).isSelectable()) {
						if (SwingUtilities.isLeftMouseButton(evt)) {
							char or = rdbtnH.isSelected() ? 'h' : 'v';
							if (selNum != -1 && ships[selNum].isEnabled())
								controler.attemptToSetShip(row, col - 1, selSeg, or);
						} else
							controler.attemptToRemoveShip(row, col - 1);
					}
				}
			});
		}
		return panel;
	}

	private JRadioButton getRdbtnH() {
		if (rdbtnH == null) {
			rdbtnH = new JRadioButton("Horizontal");
			rdbtnH.setOpaque(false);
			rdbtnH.setFont(new Font("Stencil", Font.PLAIN, 14));
			rdbtnH.setForeground(new Color(128, 0, 0));

		}
		return rdbtnH;
	}

	private JRadioButton getRdbtnV() {
		if (rdbtnV == null) {
			rdbtnV = new JRadioButton("Vertical");
			rdbtnV.setOpaque(false);
			rdbtnV.setFont(new Font("Stencil", Font.PLAIN, 14));
			rdbtnV.setForeground(new Color(128, 0, 0));

		}
		return rdbtnV;
	}

	public void changeTextFields(int segNum, boolean set) {
		if (set) {
			if (ships[selNum].isEnabled()) {
				numOfShips[selNum].setText((Integer.parseInt(numOfShips[selNum].getText()) - 1) + "");
				if (numOfShips[selNum].getText().equals("0")) {
					ships[selNum].setEnabled(false);
					ships[selNum].setBorder(BorderFactory.createLineBorder(Color.black, 1));
				}

			}
		} else {
			for (int i = 0; i < ships.length; i++)
				if (ships[i].getSegNum() == segNum) {
					numOfShips[i].setText((Integer.parseInt(numOfShips[selNum].getText()) + 1) + "");
					if (numOfShips[selNum].getText().equals(" "))
						ships[selNum].setEnabled(true);
					break;
				}
		}
	}

	public void disableAll() {
		doneButton.setEnabled(false);
		for (int i = 0; i < ships.length; i++) {
			ships[i].setEnabled(false);
			ships[i].setBorder(BorderFactory.createLineBorder(Color.black, 1));
			numOfShips[i].setText("");
		}
		selNum = selSeg = -1;
	}

	private JPanel getHeadPanel() {
		if (headPanel == null) {
			headPanel = new JPanel();
			headPanel.setOpaque(false);
			headPanel.setLayout(new GridLayout(2, 1, 0, 0));
			headPanel.add(getHeading());
		}
		return headPanel;
	}

	private JLabel getHeading() {
		if (Heading == null) {
			Heading = new JLabel("DEPLOY SHIPS\r\n");
			Heading.setVerticalAlignment(SwingConstants.BOTTOM);
			Heading.setHorizontalAlignment(SwingConstants.CENTER);
			Heading.setFont(new Font("Stencil", Font.PLAIN, 50));
			Heading.setForeground(new Color(128, 0, 0));
		}
		return Heading;
	}

	private JPanel getEastPanel() {
		if (eastPanel == null) {
			eastPanel = new JPanel();
			eastPanel.setOpaque(false);
			eastPanel.setLayout(new MigLayout("", "[]", "[][10px][10px][grow][][grow]"));
			eastPanel.add(getLblTimeLeft(), "cell 0 1");
			eastPanel.add(getTimerLabel(), "cell 0 2");
			eastPanel.add(getDoneButton(), "cell 0 4");
		}
		return eastPanel;
	}

	private JLabel getTimerLabel() {
		if (timerLabel == null) {
			timerLabel = new JLabel("xx seconds");
			timerLabel.setFont(new Font("Stencil", Font.PLAIN, 18));
			timerLabel.setForeground(new Color(128, 0, 0));
		}
		return timerLabel;
	}

	private JLabel getLblTimeLeft() {
		if (lblTimeLeft == null) {
			lblTimeLeft = new JLabel("Time left:");
			lblTimeLeft.setFont(new Font("Stencil", Font.PLAIN, 18));
			lblTimeLeft.setForeground(new Color(128, 0, 0));
		}
		return lblTimeLeft;
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
}
