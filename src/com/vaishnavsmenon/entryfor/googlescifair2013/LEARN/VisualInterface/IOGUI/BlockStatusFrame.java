package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.IOGUI;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;

public class BlockStatusFrame extends JFrame {

	private static final long serialVersionUID = -7281638760958159695L;
	
	
	static JTextArea hbox;
	
	public BlockStatusFrame(){
		super("LTLP: Status");
		this.setSize(100, 50);
		this.setVisible(true);
		this.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		this.setAlwaysOnTop(true);
		this.setLocation(675,100);
		
		JPanel panel = new JPanel();
		
		JLabel health = new JLabel("Health: ");
		health.setBounds(0,0,96,50);
		health.setVisible(true);
		panel.add(health);
		
		hbox = new JTextArea();
		hbox.setBounds(100, 0, 100, 50);
		hbox.setEditable(false);
		hbox.setText(Integer.toString(100));
		hbox.setVisible(true);
		panel.add(hbox);
		
		this.getContentPane().add(panel);
	}
	
	public static void setHealth(int health){
		hbox.setText(Integer.toString(health));
	}
	
}
