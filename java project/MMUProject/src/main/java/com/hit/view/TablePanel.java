package com.hit.view;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
/////////////////////////
//TablePanel Class
//Handles pages table
/////////////////////////
public class TablePanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable pageTable;
	private DefaultTableModel pageTableModel;
	private DefaultTableCellRenderer renderer;

	public TablePanel()
	{
		//set the table borders
		Border outerBorder=BorderFactory.createTitledBorder("Ram Table");
		Border innerBorder=BorderFactory.createEmptyBorder(5,5,5,5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
		
		//set table panel size
		setPreferredSize(new Dimension(400,80));
		pageTableModel=new DefaultTableModel() {
		private static final long serialVersionUID = 1L;
		@Override
			//disable cells editing
		    public boolean isCellEditable(int row, int column) {
		       //all cells false
		       return false;
		    }
		};
		//set the table properties
		pageTable=new JTable(pageTableModel);
		setLayout(new BorderLayout());
		add(new JScrollPane(pageTable),BorderLayout.LINE_START);
		
		DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
		renderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);
		pageTable.setDefaultRenderer(String.class, renderer);
		
		Font fontText = new Font("SansSerif", Font.BOLD, 15);
		pageTable.setFont(fontText);
		
	}
	
	public void setRowNum(int numOfRows) {
		pageTableModel.setNumRows(numOfRows);
	}

	//add page to the table = add new column
	public void addPage(Integer pageNum) {
		pageTableModel.addColumn(pageNum);
		pageTable.getColumnModel().getColumn(pageTableModel.getColumnCount() - 1).setCellRenderer(renderer);
		
		//handle the columns title
		String[] columnsHeaders = new String[pageTableModel.getColumnCount()];
		for (int i=0; i < pageTableModel.getColumnCount(); i++){
			columnsHeaders[i] = pageTableModel.getColumnName(i);
		}
		columnsHeaders [pageTableModel.getColumnCount() - 1] =pageNum.toString();
		pageTableModel.setColumnIdentifiers(columnsHeaders);
			
	}
	
	//replace page title
	public void replacePages(int pageToHD, int pageToRam) {
		String[] columnsHeaders = new String[pageTableModel.getColumnCount()];
		//replace the column title of the "old" page with the "new" page
		for (int j=0; j < pageTableModel.getColumnCount(); j++){
			columnsHeaders[j] = pageTableModel.getColumnName(j);
		 
			if (columnsHeaders[j].equals(Integer.toString(pageToHD)))
			{
				columnsHeaders [j]=Integer.toString(pageToRam);
				for(int i=0;i<pageTableModel.getRowCount();i++)
				{
					pageTableModel.setValueAt(" ", i, j);
				}
			}
		}	
		//set the updated column titles
		pageTableModel.setColumnIdentifiers(columnsHeaders);
					
	}

	//replace page data
	public void setPageData(int pageNum, int[] intDataValues) {
		String[] columnsHeaders = new String[pageTableModel.getColumnCount()];
		//replace the column data of the "old" page with the "new" page data
		for (int j=0; j < pageTableModel.getColumnCount(); j++){
			columnsHeaders[j] = pageTableModel.getColumnName(j);
		 
			if (columnsHeaders[j].equals(Integer.toString(pageNum)))
			{
				for(int i=0;i<pageTableModel.getRowCount();i++)
				{
					pageTableModel.setValueAt(intDataValues[i], i, j);
				}
			}
		
		}
	}
	//clears the table
	public void clear() {

		pageTableModel.setColumnCount(0);
	}
}
