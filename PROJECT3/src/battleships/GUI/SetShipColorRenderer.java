package battleships.GUI;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class SetShipColorRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int col) {
		this.setOpaque(true);
		
		setHorizontalAlignment(JLabel.CENTER);
		setVerticalAlignment(JLabel.CENTER);
		
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

		GraphicTableModel tableModel = (GraphicTableModel) table.getModel();

		l.setText(l.getText());
		if (tableModel.getValueAt(row, col).equals(" ") && col != 0) {
			if (tableModel.puttingShips())
				l.setBackground(new Color(0, 153, 76));
			else
				l.setBackground(new Color(184, 236, 249));

		} 
		
		else if (tableModel.getValueAt(row, col).equals("  ") && col != 0)
			l.setBackground(new Color(232, 21, 21));
		
		else if (col == 0)
			l.setForeground(getForeground());
		
		else
			l.setBackground(new Color(184,236,249));
		return l;

	}
}
