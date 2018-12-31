package com.hit.view;
/////////////////////////
//FormPanel Class
//Handles page counters
/////////////////////////
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {
		

		private static final long serialVersionUID = 1L;
		private JLabel pFault;
		private JLabel pRep;
		private JTextField pFaultVal;
		private JTextField pRepVal;
				
		public FormPanel()
		{
			//create labels and text fields
			Font fontText = new Font("SansSerif", Font.BOLD, 20);
			pFault=new JLabel("Page faults        ");
			pRep=new JLabel  ("Page replaced   ");
			pFaultVal=new JTextField();
			pRepVal=new JTextField();
			
			pFaultVal.setFont(fontText);
			pRepVal.setFont(fontText);
			
			//set graphic properties
			pFaultVal.setHorizontalAlignment(JTextField.CENTER);
			pRepVal.setHorizontalAlignment(JTextField.CENTER);
			pRepVal.setPreferredSize(new Dimension(50, 30));
			pFaultVal.setPreferredSize(new Dimension(50, 30));
			
			//set border
			Border innerBorder=BorderFactory.createTitledBorder("Pages Information");
			Border outerBorder=BorderFactory.createEmptyBorder(5,5, 5, 5);
			setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));
	
			setPreferredSize(new Dimension(80,100));
			JPanel pageFaultPannel    = new JPanel();
	
			//add labels and text fields
			pageFaultPannel.add(pFault);
			pageFaultPannel.add(pFaultVal);
			pageFaultPannel.setLayout(new BoxLayout(pageFaultPannel, BoxLayout.X_AXIS));
			JPanel pageReplacedPannel = new JPanel();
			pageReplacedPannel.setLayout(new BoxLayout(pageReplacedPannel, BoxLayout.X_AXIS));
			pageReplacedPannel.add(pRep);
			pageReplacedPannel.add(pRepVal);
			add(pageFaultPannel);
			add(pageReplacedPannel);
			
		}
		
		//set value of page fault text field
		public void setPageFault(Integer numOfPF)
		{
			pFaultVal.setText(numOfPF.toString());
		}
		//set value of page replacement text field
		public void setPageReplacement(Integer numOfPR)
		{
			pRepVal.setText(numOfPR.toString());
		}

		//clear text fields
		public void clear() {
			pFaultVal.setText("");
			pRepVal.setText("");
		}

}
