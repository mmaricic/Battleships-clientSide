package battleships.GUI;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class MessagePanel extends JScrollPane {
	
	private JTextPane messagesTextPane;
	private StyledDocument textPaneStyleDoc;
	private Style textPaneStyle;
	private int count = 0;

	MessagePanel(JTextPane textPane){
		super(textPane);
		messagesTextPane = textPane;
		setTextPane();
		setOpaque(false);
		
		TitledBorder title = new TitledBorder(new LineBorder(new Color(128, 0, 0)), "Messages", TitledBorder.LEFT, TitledBorder.TOP, null, new Color(0, 0, 0));
		title.setTitleFont(new Font("Stencil", Font.PLAIN, 15));
		title.setTitleColor(new Color(128, 0, 0));
		
		setViewportBorder(title);
		setViewportView(messagesTextPane);
		setMinimumSize(new Dimension(getWidth(), 100));
		
		setPreferredSize(new Dimension(getWidth(), 100));
		setMaximumSize(new Dimension(getWidth(), 100));
	}

	private void setTextPane() {
		messagesTextPane.setFont(new Font("Times New Roman", Font.PLAIN, 13));
		messagesTextPane.setForeground(new Color(128, 0, 0));
		textPaneStyleDoc = messagesTextPane.getStyledDocument();
		textPaneStyle = messagesTextPane.addStyle("New style", null);

	}

	public void displayMessageFromServer(String message) {
		try {
			if (count++ % 2 == 0)
				StyleConstants.setForeground(textPaneStyle, Color.blue);
			else
				StyleConstants.setForeground(textPaneStyle, Color.red);

			textPaneStyleDoc.insertString(0, message + "\n", textPaneStyle);
			
		} catch (BadLocationException e) {
		}

	}
}
