package com.hit.view;
/////////////////////////
//Toolbar Class
//Handles buttons
/////////////////////////

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;


public class Toolbar extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private JButton playBtn;
	private JButton playAllBtn;
	private JButton resetBtn;
	private Dispatcher dispatcher;
	
	//initialize toolbar
	public Toolbar(Dispatcher dispatcher)
	{
		this.dispatcher=dispatcher;
		
		setBorder(BorderFactory.createRaisedBevelBorder());
		//create the buttons
		playBtn=new JButton("Play");
		playAllBtn=new JButton("Play All");
		resetBtn=new JButton("Reset");
		
		//add action listener for the buttons
		playBtn.addActionListener(this);
		playAllBtn.addActionListener(this);
		resetBtn.addActionListener(this);

		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));	
		add(playBtn);
		add(playAllBtn);
		add(resetBtn);
	}

	//action listener for the buttons
	//activate the dispatcher functions according to the selected button
	@Override
	public void actionPerformed(ActionEvent e) {

	JButton clicked=(JButton) e.getSource();
	
		if(clicked==playBtn)
		{
			dispatcher.play();
		}
		else if(clicked==playAllBtn)
		{
			dispatcher.playAll();
		}
		else
		{
			dispatcher.resetRam();
		}
	
	}

	public void setPlayButtonEnable(boolean b) {
		playBtn.setEnabled(b);		
	}
	public void setPlayAllButtonEnable(boolean b) {
		playAllBtn.setEnabled(b);		
	}

}
