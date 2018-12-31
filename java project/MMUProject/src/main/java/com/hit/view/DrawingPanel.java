package com.hit.view;
/////////////////////////
//DrawingPanel Class
//Simulate hard disk and ram graphics
/////////////////////////
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class DrawingPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	
	//hold list of all pages ids
	private ArrayList<Integer> pagesList;
	//boolean image of pages list.values are true if in hard disk, false if in ram
	private ArrayList<Boolean> pageOnDiskList=new ArrayList<Boolean>();
	
	public DrawingPanel (){
		setPreferredSize(new Dimension(300,450));
	}
	//main drawing function
	public void paintComponent(Graphics g)
	{ 
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		setBackground(new Color(153,255,255));
		//get the matrices of the panel
		int w = getWidth();
		int h = getHeight();
		
		//draw rectangles for hard disk and ram
		g2.setColor(new Color(204,255,204));
		g2.fillRect(0, h/2, w, h);
		Font f = new Font("Dialog", Font.BOLD, 16);
		g2.setFont(f);
		g2.setColor(Color.BLACK);
		//draw rectangles labels
		g2.drawString("HardDisk", 0, 20);
		g2.drawString("Ram", 0, 20+h/2);
		//draw the pages for hard disk and ram 
		drawHardDiskPages (g2,30,30);
		drawRamPages (g2,30,h/2+30);
	}
	
	//draw the hard disk pages
	private void drawHardDiskPages(Graphics2D g2, int x, int y) {
			
		int locx=x;
		int locy=y;
		
		for (Integer i=0; i<pagesList.size(); i++) {	
			
			if (pageOnDiskList.get(i)) {
				//red - updated on hard disk
				g2.setColor(new Color(222,38,38));
			}
			else
			{	//orange - page on ram
				g2.setColor(new Color(255,128,0));
			}
		
			g2.fillRect(locx, locy, 30, 40);
			//draw the index on the page
			locx+=35;
			g2.setColor(Color.BLACK);
			g2.drawString(pagesList.get(i).toString(), locx-25,locy+20);
			
			//limit the number of pages on a line
			if((i+1)%12==0)
			{
				locy+=60;
				locx=x;
			}
		}
		
	}
	//draw the ram pages
	private void drawRamPages(Graphics2D g2, int x, int y) {
			
		int locx=x;
		int locy=y;
			
		for (Integer i=0; i<pagesList.size(); i++) {
			//if the page is on ram
			if (!pageOnDiskList.get(i)) {
			
				g2.setColor(Color.YELLOW);
				g2.fillRect(locx, locy, 30, 40);
				locx+=35;
				g2.setColor(Color.BLACK);
				g2.drawString(pagesList.get(i).toString(), locx-25,locy+20);
				
				//limit the number of pages on a line
				if((i+1)%12==0)
				{
					locy+=60;
					locx=x;
				}
			}	
		}

	}
	//init pageOnDiskList
	public void setDiskPages(ArrayList<Integer> pagesList) {
		this.pagesList=pagesList;
		for (int i=0; i<pagesList.size();i++) {
			pageOnDiskList.add(true);
		}	
	}
	
	//functions to handle the pageOnDiskList defines which page is on hard disk or ram
	public void handlePageFault(int pageNum) {
		int index=pagesList.indexOf(pageNum);
		pageOnDiskList.set(index, false);
		//repaint the panel
		repaint();
	}
	public void handlePageReplacement(int pageToHD, int pageToRam) {
		int index=pagesList.indexOf(pageToHD);
		pageOnDiskList.set(index, true);
		
		index=pagesList.indexOf(pageToRam);
		pageOnDiskList.set(index, false);
		//repaint the panel
		repaint();

	}
	//return to initial state
	public void resetRam() {
		for (int i=0; i<pagesList.size();i++) {
			pageOnDiskList.set(i, true);
		}	
		//repaint the panel
		repaint();
	}

}

