package battleships.GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class LoginScreen extends JFrame {

	private JPanel contentPane;
	private JLabel IPlabel;
	private JLabel portLabel;
	private JLabel passwordLabel;
	private JLabel usernameLabel;
	private JLabel imageLabel;
	private JTextField textIP;
	private JTextField textPort;
	private JTextField textPassword;
	private JTextField textUsername;
	private JTextField textImage;
	private JButton buttonBrowse;
	private JButton buttonConnect;
	private JLabel messageLabel;
	private JPanel fieldsPanel;
	private JPanel messagePanel;
	private Border redBorder = BorderFactory.createLineBorder(Color.RED, 1);
	BufferedImage img = null;

	private GUIControler controler;
	private JLabel TitleLabel;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginScreen login = new LoginScreen();
					login.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LoginScreen() {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setTitle("Battleships");
		controler = new GUIControler(this);

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controler.shutDown();
			}
		});

		setBounds(100, 100, 700, 440);
		setResizable(false);

		contentPane = new BackgroundPanel(LoginScreen.class.getResourceAsStream("/images/login.jpg"));
		contentPane.setOpaque(true);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);

		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[] { 424, 0 };
		gbl_contentPane.rowHeights = new int[] { 223, 32, 0 };
		gbl_contentPane.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_contentPane.rowWeights = new double[] { 1.0, 1.0, Double.MIN_VALUE };
		contentPane.setLayout(gbl_contentPane);

		GridBagConstraints gbc_fieldsPanel = new GridBagConstraints();
		gbc_fieldsPanel.fill = GridBagConstraints.VERTICAL;
		gbc_fieldsPanel.insets = new Insets(0, 0, 5, 0);
		gbc_fieldsPanel.gridx = 0;
		gbc_fieldsPanel.gridy = 0;
		contentPane.add(getFieldsPanel(), gbc_fieldsPanel);

		GridBagConstraints gbc_messagePanel = new GridBagConstraints();
		gbc_messagePanel.fill = GridBagConstraints.BOTH;
		gbc_messagePanel.gridx = 0;
		gbc_messagePanel.gridy = 1;
		contentPane.add(getMessagePanel(), gbc_messagePanel);

		resetBorders();

		setLocationRelativeTo(null);

	}

	private JLabel getIPlabel() {
		if (IPlabel == null) {
			IPlabel = new JLabel("IP address:");
			IPlabel.setOpaque(true);
			IPlabel.setFont(new Font("Stencil", Font.PLAIN, 17));
			IPlabel.setForeground(new Color(128, 0, 0));
		}
		return IPlabel;
	}

	private JLabel getPortLabel() {
		if (portLabel == null) {
			portLabel = new JLabel("Port:");
			portLabel.setOpaque(true);
			portLabel.setForeground(new Color(128, 0, 0));
			portLabel.setFont(new Font("Stencil", Font.PLAIN, 17));
		}
		return portLabel;
	}

	private JLabel getPasswordLabel() {
		if (passwordLabel == null) {
			passwordLabel = new JLabel("Password:");
			passwordLabel.setOpaque(true);
			passwordLabel.setFont(new Font("Stencil", Font.PLAIN, 17));
			passwordLabel.setForeground(new Color(128, 0, 0));
		}
		return passwordLabel;
	}

	private JLabel getUsernameLabel() {
		if (usernameLabel == null) {
			usernameLabel = new JLabel("Username:");
			usernameLabel.setOpaque(true);
			usernameLabel.setForeground(new Color(128, 0, 0));
			usernameLabel.setFont(new Font("Stencil", Font.PLAIN, 17));
		}
		return usernameLabel;
	}

	private JLabel getImageLabel() {
		if (imageLabel == null) {
			imageLabel = new JLabel("Image:");
			imageLabel.setOpaque(true);
			imageLabel.setFont(new Font("Stencil", Font.PLAIN, 17));
			imageLabel.setForeground(new Color(128, 0, 0));
		}
		return imageLabel;
	}

	private JTextField getTextIP() {
		if (textIP == null) {
			textIP = new JTextField();
			textIP.setFont(new Font("Times New Roman", Font.BOLD, 17));
			textIP.setMinimumSize(new Dimension(100, 20));
			textIP.setColumns(15);
		}
		return textIP;
	}

	private JTextField getTextPort() {
		if (textPort == null) {
			textPort = new JTextField();
			textPort.setFont(new Font("Times New Roman", Font.BOLD, 17));
			textPort.setMinimumSize(new Dimension(100, 20));
			textPort.setColumns(15);
		}
		return textPort;
	}

	private JTextField getTextPassword() {
		if (textPassword == null) {
			textPassword = new JTextField();
			textPassword.setFont(new Font("Times New Roman", Font.BOLD, 17));
			textPassword.setMinimumSize(new Dimension(100, 20));
			textPassword.setColumns(15);
		}
		return textPassword;
	}

	private JTextField getTextUsername() {
		if (textUsername == null) {
			textUsername = new JTextField();
			textUsername.setFont(new Font("Times New Roman", Font.BOLD, 17));
			textUsername.setMinimumSize(new Dimension(100, 20));
			textUsername.setColumns(15);

		}
		return textUsername;
	}

	private JTextField getTextImage() {
		if (textImage == null) {
			textImage = new JTextField();
			textImage.setFont(new Font("Times New Roman", Font.BOLD, 17));
			textImage.setMinimumSize(new Dimension(100, 20));
			textImage.setColumns(15);
		}
		return textImage;
	}

	private JButton getButtonBrowse() {
		if (buttonBrowse == null) {
			buttonBrowse = new JButton("Browse");
			buttonBrowse.setForeground(new Color(128, 0, 0));
			buttonBrowse.setFont(new Font("Stencil", Font.PLAIN, 16));
			buttonBrowse.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controler.getImageFromSystem();
				}
			});
		}
		return buttonBrowse;
	}

	private JButton getButtonConnect() {
		if (buttonConnect == null) {
			buttonConnect = new JButton("CONNECT");
			buttonConnect.setForeground(new Color(128, 0, 0));
			buttonConnect.setPreferredSize(new Dimension(60, 30));
			buttonConnect.setFont(new Font("Stencil", Font.PLAIN, 20));
			buttonConnect.setVerticalAlignment(SwingConstants.CENTER);
			buttonConnect.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					controler.checkForEmptyFields();
				}
			});
		}
		return buttonConnect;
	}

	public String loginInfo() {
		StringBuilder str = new StringBuilder();
		str.append(textUsername.getText());
		if (!textPassword.getText().isEmpty())
			str.append("/" + textPassword.getText());
		return str.toString();
	}

	public String getIP() {
		return textIP.getText();
	}

	public String getPort() {
		return textPort.getText();
	}

	public String getUsername() {
		return textUsername.getText();
	}

	public void setImageText(String fileName) {
		textImage.setText(fileName);
	}

	private JLabel getMessageLabel() {
		if (messageLabel == null) {
			messageLabel = new JLabel("");
			messageLabel.setOpaque(true);
			messageLabel.setVerticalAlignment(SwingConstants.TOP);
			messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
			messageLabel.setFont(new Font("Stencil", Font.PLAIN, 16));
			messageLabel.setForeground(new Color(128, 0, 0));
		}
		return messageLabel;
	}

	private JPanel getFieldsPanel() {
		if (fieldsPanel == null) {
			fieldsPanel = new JPanel();
			fieldsPanel.setOpaque(false);
			fieldsPanel.setLayout(new MigLayout("", "[40px,grow][grow][grow,center][grow]",
					"[][25px,grow][20px,grow][20px,grow][20px,grow][20px,grow][20px,grow][10px][44.00px,grow]"));
			fieldsPanel.add(getTitleLabel(), "cell 2 0");
			fieldsPanel.add(getIPlabel(), "cell 1 2,alignx trailing");
			fieldsPanel.add(getTextIP(), "cell 2 2,growx");
			fieldsPanel.add(getPortLabel(), "cell 1 3,alignx trailing");
			fieldsPanel.add(getTextPort(), "cell 2 3,growx");
			fieldsPanel.add(getPasswordLabel(), "cell 1 4,alignx trailing");
			fieldsPanel.add(getTextPassword(), "cell 2 4,growx");
			fieldsPanel.add(getUsernameLabel(), "cell 1 5,alignx trailing");
			fieldsPanel.add(getTextUsername(), "cell 2 5,growx");
			fieldsPanel.add(getImageLabel(), "cell 1 6,alignx trailing");
			fieldsPanel.add(getTextImage(), "cell 2 6,growx");
			fieldsPanel.add(getButtonBrowse(), "cell 3 6,alignx left");
			fieldsPanel.add(getButtonConnect(), "cell 2 8,alignx center");
		}
		return fieldsPanel;
	}

	private JPanel getMessagePanel() {
		if (messagePanel == null) {
			messagePanel = new JPanel();
			messagePanel.setOpaque(false);
			messagePanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
			messagePanel.add(getMessageLabel());
		}
		return messagePanel;
	}

	public void setMessageText(String message) {
		messageLabel.setText(message);
	}

	public void setEmptyBorder() {
		resetBorders();

		if (textIP.getText().isEmpty())
			textIP.setBorder(redBorder);
		if (textPort.getText().isEmpty())
			textPort.setBorder(redBorder);
		if (textUsername.getText().isEmpty())
			textUsername.setBorder(redBorder);
	}

	void resetBorders() {
		Border border = BorderFactory.createLineBorder(Color.black, 1);
		textIP.setBorder(border);
		textPort.setBorder(border);
		textUsername.setBorder(border);
		textPassword.setBorder(border);
		textImage.setBorder(border);
	}

	public void UpdateGetNewName() {
		messageLabel.setText("Username already taken. Enter new Username.");
		textUsername.setText("");
		resetBorders();
		textUsername.setBorder(redBorder);

	}

	
	public void UpdateGetPassword() {
		messageLabel.setText("Server is protected. Please enter password");
		textPassword.setText("");
		resetBorders();
		textPassword.setBorder(redBorder);
	}

	
	public void UpdateGetNewPassword() {
		messageLabel.setText("Wrong password! Please enter the right password.");
		textPassword.setText("");
		resetBorders();
		textPassword.setBorder(redBorder);

	}

	
	public void resetFields() {
		textImage.setText("");
		textIP.setText("");
		textPassword.setText("");
		textPort.setText("");
		textUsername.setText("");
	}

	
	private JLabel getTitleLabel() {
		if (TitleLabel == null) {
			TitleLabel = new JLabel("Battleships");
			TitleLabel.setOpaque(false);
			TitleLabel.setForeground(new Color(128, 0, 0));
			TitleLabel.setFont(new Font("Stencil", Font.PLAIN, 50));
		}
		return TitleLabel;
	}

	
	public String getImageText() {
		return textImage.getText();
	}
}
