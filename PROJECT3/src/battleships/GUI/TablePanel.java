package battleships.GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import battleships.client.CurrentPlayer;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class TablePanel extends JPanel {

	private JPanel tablePane;
	private JScrollPane tableScrollPane;
	private JPanel namePane;
	private JTable table;
	private int row, col;
	private CurrentPlayer player;
	private JLabel nameLabel;
	private JLabel imageLabel;

	/**
	 * Create the panel.
	 * @wbp.parser.constructor
	 */
	public TablePanel(int row, int col, CurrentPlayer player) {
		this.row = row;
		this.col = col;
		this.player = player;
		this.setLayout(new BorderLayout(0,0));
		add(getTablePane(), BorderLayout.CENTER);
		add(getNamePane(), BorderLayout.NORTH);
		
	}


	
	public TablePanel(int row, int col) {
		this.row = row;
		this.col = col;
		this.player = player;
		add(getScrollTablePane());
	}



	private JPanel getTablePane(){
		if (tablePane == null) {
			tablePane = new JPanel();
			tablePane.setOpaque(false);
			tablePane.add(getScrollTablePane());
			
		}
		return tablePane;
	}
	private JScrollPane getScrollTablePane() {
		if (tableScrollPane == null) {
			tableScrollPane = new JScrollPane();
			tableScrollPane.setViewportView(getTable());
			table.setPreferredScrollableViewportSize(table.getPreferredSize());
		}
		return tableScrollPane;
	}


	private JTable getTable() {
		if (table == null) {
			table = new JTable(row, col + 1) {

				@Override
				public Dimension getPreferredScrollableViewportSize() {
					int height = row * 30;
					int width = col * 30 + 20;
					return new Dimension(width, height);
				}

				@Override
				public Dimension getPreferredSize() {
					int height = row * 30;
					int width = col * 30 + 20;
					return new Dimension(width, height);
				}

			};
	
			
			table.setForeground(new Color(128, 0, 0));
			table.setModel(new GraphicTableModel(row, col + 1));
			table.getTableHeader().setResizingAllowed(false);
			table.getTableHeader().setReorderingAllowed(false);
			table.setFont(new Font("Stencil", Font.BOLD, 20));
			table.getTableHeader().setFont(new Font("Stencil", Font.BOLD, 20));
			setTable(table);
		}
		return table;
	}
	
	
	
	private JPanel getNamePane() {
		if(namePane == null){
			namePane = new JPanel();
			namePane.setOpaque(false);
		namePane.setLayout(new GridLayout(0, 2, 0, 0));
		if(player.getImage() != null)
			namePane.add(getMyImage());
			namePane.add(getMyName());
		}
		return namePane;
	}

	
	private JLabel getMyImage() {
		if(imageLabel == null){
		ImageIcon image = new ImageIcon(player.getImage());
		imageLabel = new JLabel(image, SwingConstants.CENTER);
		imageLabel.setOpaque(false);
		}

		imageLabel.setPreferredSize(new Dimension(64,64));
		imageLabel.setMinimumSize(new Dimension(64,64));

		return imageLabel;
	}
	
	

	private JLabel getMyName() {
		if(nameLabel == null){
			nameLabel = new JLabel();
			nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
			nameLabel.setFont(new Font("Times New Roman", Font.BOLD, 20));
			nameLabel.setText(player.getName()+"  ");
		}
		return nameLabel;
	}

	
	
	public void setTable(JTable table){
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setRowHeight(30);
		table.getTableHeader().setBackground(new Color(192, 192, 192));
		table.getTableHeader().setForeground(new Color(128, 0, 0));
		table.getTableHeader().setFont(new Font("Stencil", Font.BOLD, 20));
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(0).setMaxWidth(20);
		table.setBackground(new Color(184,236,249));
		table.getColumnModel().getColumn(0).setCellRenderer(new ColumnColorRenderer());
		table.getTableHeader().setFont(new Font("Stencil", Font.PLAIN, 12));

	}
	
	public JTable getMyTable(){
		return table;
	}
	
	
	public int getRowNumber(){
		return row;
	}
	
	
	public int getColumnNumber(){
		return col;
	}
	
	
	public String getName(){
		return player.getName();
	}
	
}

