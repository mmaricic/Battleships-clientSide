package battleships.GUI;

import javax.swing.table.AbstractTableModel;

public class GraphicTableModel extends AbstractTableModel {
	private String[] columnHeader;
	private int rowNum;
	private String[][] table;
	private boolean selectable = true;
	private int colorCell;
	private boolean stateOfPuttingShips;
	private boolean update;

	public boolean isSelectable() {
		return selectable;
	}

	public GraphicTableModel(int rowNum, int colNum) {
		columnHeader = new String[colNum];
		this.rowNum = rowNum;
		fillColumHeader();
		table = new String[rowNum][colNum];
		for (int j = 0; j < rowNum; j++)
			for (int i = 0; i < colNum; i++)
				if (i == 0)
					table[j][i] = "" + j;
				else
					table[j][i] = "";
	}

	@Override
	public int getColumnCount() {
		return columnHeader.length;
	}

	@Override
	public int getRowCount() {
		return rowNum;
	}

	@Override
	public Object getValueAt(int row, int col) {
		return table[row][col];
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}

	@Override
	public String getColumnName(int arg0) {
		return columnHeader[arg0];
	}

	private void fillColumHeader() {
		columnHeader[0] = "";
		for (int i = 0; i < columnHeader.length - 1; i++)
			columnHeader[i + 1] = "" + i;
	}

	public void setPuttingShips(boolean set) {
		stateOfPuttingShips = set;
	}

	public boolean puttingShips() {
		return stateOfPuttingShips;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		table[rowIndex][columnIndex] = (String) aValue;
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return getValueAt(0, columnIndex).getClass();
	}

}
