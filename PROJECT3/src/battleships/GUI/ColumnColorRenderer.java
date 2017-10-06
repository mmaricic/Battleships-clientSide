package battleships.GUI;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;

public class ColumnColorRenderer extends DefaultTableCellRenderer {
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
	
		JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
		
		GraphicTableModel tableModel = (GraphicTableModel) table.getModel();
		
		l.setForeground(new Color(128,0,0));
		
		if (tableModel.isSelectable() == true && col!=0) {
			l.setBackground(new Color(0, 153,76));
		}
		else if(tableModel.isSelectable() == true){
			l.setBackground(new Color(192,192,192));
			l.setFont(new Font("Stencil", Font.BOLD, 12));
		}
		
		return l;
	}
}
