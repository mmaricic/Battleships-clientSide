package battleships.GUI;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ForceDisconnectFrame extends JFrame {

	private BackgroundPanel contentPane;
	private JLabel textLabel;
	private JButton btnOk;
	private GUIControler controler;

	/**
	 * Create the frame.
	 */
	public ForceDisconnectFrame(GUIControler controler) {
		this.controler = controler;
		
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent arg0) {
				controler.quitGame();
				setVisible(false);
			}
		});
		
		
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 700, 250);
		setResizable(false);
		contentPane = new BackgroundPanel(ForceDisconnectFrame.class.getResourceAsStream("/images/end.jpg"));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new MigLayout("", "[grow,center]", "[][grow][grow][grow]"));
		contentPane.add(getTextLabel(), "cell 0 1");
		contentPane.add(getBtnOk(), "cell 0 2");
		
		setLocationRelativeTo(null);
	}

	private JLabel getTextLabel() {
		if (textLabel == null) {
			textLabel = new JLabel("You have been disconected from the server");
			textLabel.setForeground(new Color(128, 0, 0));
			textLabel.setFont(new Font("Stencil", Font.PLAIN, 26));
		}
		return textLabel;
	}
	
	
	private JButton getBtnOk() {
		if (btnOk == null) {
			btnOk = new JButton("OK");
			btnOk.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controler.quitGame();
					setVisible(false);
				}
			});
			btnOk.setForeground(new Color(128, 0, 0));
			btnOk.setFont(new Font("Stencil", Font.PLAIN, 20));
		}
		return btnOk;
	}
}
