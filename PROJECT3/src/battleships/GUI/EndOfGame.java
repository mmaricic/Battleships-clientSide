package battleships.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EndOfGame extends JDialog {

	private final BackgroundPanel contentPanel = new BackgroundPanel(EndOfGame.class.getResourceAsStream("/images/end.jpg"));

	
	
	
	
	/**
	 * Create the dialog.
	 */
	public EndOfGame(String string, GUIControler controler) {
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				controler.shutDown();
			}
		});
		
		setBounds(100, 100, 500, 330);
		setResizable(false);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		setVisible(false);
		
		contentPanel.setLayout(new MigLayout("", "[grow,center]", "[grow][grow][grow][grow][grow][grow]"));
		{
			JLabel lblNewLabel = new JLabel(string);
			lblNewLabel.setForeground(new Color(128, 0, 0));
			lblNewLabel.setFont(new Font("Stencil", Font.PLAIN, 28));
			contentPanel.add(lblNewLabel, "cell 0 1");
		}
		{
			JButton btnNewButton = new JButton("Login Menu");
			btnNewButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controler.quitGame();
				}
			});
			btnNewButton.setForeground(new Color(139, 0, 0));
			btnNewButton.setFont(new Font("Stencil", Font.PLAIN, 24));
			contentPanel.add(btnNewButton, "flowx,cell 0 3");
		}
		{
			JButton btnNewButton_1 = new JButton("EXIT");
			btnNewButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					controler.shutDown();
				}
			});
			btnNewButton_1.setForeground(new Color(128, 0, 0));
			btnNewButton_1.setFont(new Font("Stencil", Font.PLAIN, 24));
			contentPanel.add(btnNewButton_1, "cell 0 3");
		}
		setLocationRelativeTo(null);
	}
}
