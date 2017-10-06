package battleships.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;

public class WelcomeScreen extends JFrame {
	private JPanel mainPanel;
	private JLabel waitingLabel;
	private JButton quitButton;
	private BackgroundPanel contentPane;

	private GUIControler controler;
	
	
	/**
	 * Create the frame.
	 */
	public WelcomeScreen(GUIControler controler) {
		setTitle("Battleships");
		this.controler = controler;
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controler.shutDown();
			}
		});
		
		setBounds(100, 100, 600, 470);
		
		contentPane = new BackgroundPanel(WelcomeScreen.class.getResourceAsStream("/images/welcome.jpg"));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		contentPane.add(getMainPanel(), BorderLayout.CENTER);
		
		
		setLocationRelativeTo(null);
	}
	
	
	private JPanel getMainPanel() {
		if (mainPanel == null) {
			mainPanel = new JPanel();
			mainPanel.setOpaque(false);
			mainPanel.setLayout(null);
			mainPanel.add(getWaitingLabel());
			mainPanel.add(getWaitingGif());
			mainPanel.add(getQuitButton());
		}
		return mainPanel;
	}
	
	
	private JLabel getWaitingLabel() {
		if (waitingLabel == null) {
			waitingLabel = new JLabel("Waiting for other players...");
			waitingLabel.setForeground(new Color(128, 0, 0));
			waitingLabel.setBounds(0, 43, 584, 40);
			waitingLabel.setFont(new Font("Stencil", Font.PLAIN, 36));
			waitingLabel.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return waitingLabel;
	}
	
	
	private JButton getQuitButton() {
		if (quitButton == null) {
			quitButton = new JButton("QUIT");
			quitButton.setForeground(new Color(128, 0, 0));
			quitButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controler.quitGame();
				}
			});
			quitButton.setBounds(238, 267, 110, 40);
			quitButton.setFont(new Font("Stencil", Font.PLAIN, 22));
		}
		return quitButton;
	}
	
	
	private JLabel getWaitingGif(){
		 ImageIcon loading = new ImageIcon(WelcomeScreen.class.getResource("/images/loading.gif"));
		 JLabel label = new JLabel("", loading, JLabel.CENTER);
		 label.setBounds(0, 143, 584, 73);
		 return label; 
	}
}
