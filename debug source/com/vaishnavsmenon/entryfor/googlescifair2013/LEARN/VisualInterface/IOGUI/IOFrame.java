package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.IOGUI;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain.Bot;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class IOFrame extends JFrame{
	
	private static final long serialVersionUID = -681607203789878682L;
	
	JButton x;
	JTextField ibox;
	static JTextArea outList;
	
	public IOFrame(){
		
		this.setSize(450 , 500);
		this.setTitle("LEARN: Input/Output");
		this.setVisible(true);
		this.setResizable(false);
		this.addKeyListener(xKeyListener);
		this.addWindowListener(xStateListener);
		x = new JButton();
		x.setText("Send");
		x.setSize(100, 40);
		x.setBounds(0, 435, 100, 40);
		x.setVisible(true);
		x.addActionListener(xListener);
		x.addKeyListener(xKeyListener);
		
		ibox = new JTextField();
		ibox.setSize(350, 40);
		ibox.setBounds(100, 435, 350, 40);
		ibox.setVisible(true);
		ibox.addKeyListener(xKeyListener);
		
		outList = new JTextArea();
		outList.setBackground(Color.WHITE);
		outList.setSize(450, 432);
		outList.setBounds(0, 0, 450, 432);
		outList.setVisible(true);
		outList.setEditable(false);
		outList.addKeyListener(xKeyListener);
		
		this.add(x);
		this.add(outList);
		this.add(ibox);
		
		this.update(this.getGraphics());
		
		while(Bot.running){
			x.setSize(100, 40);
			x.setBounds(0, 435, 100, 40);
			
			ibox.setSize(350, 40);
			ibox.setBounds(100, 435, 350, 40);
			
			outList.setSize(450, 432);
			outList.setBounds(0, 0, 450, 432);
			this.repaint();
		}
		
		
	}
	
	public ActionListener xListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent event) {
			if(!(ibox.getText().toLowerCase().trim().equals("i quit".trim().toLowerCase())) && !ibox.getText().isEmpty()){
			outList.setText(outList.getText()+"\n User: "+ibox.getText());
			String input = ibox.getText();
			ibox.setText("");
			Message m = new Message("user_input",2,0);
			m.addMessage(input.toLowerCase());
			MessageBuffer.addToBuffer(m);
			
			} else if(!ibox.getText().isEmpty()){
				Bot.saveAndStop();
			}
		}
		
	};
	
	public KeyListener xKeyListener = new KeyListener(){

		@Override
		public void keyPressed(KeyEvent e) {		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyCode() == 10){
				if(!(ibox.getText().toLowerCase().trim().equals("quit".trim())) && !ibox.getText().isEmpty()){
					outList.setText(outList.getText()+"\n User: "+ibox.getText());
					String input = ibox.getText();
					ibox.setText("");
					Message m = new Message("user_input",2,0);
					m.addMessage(input.toLowerCase());
					MessageBuffer.addToBuffer(m);
					LogUtils.log("PUTTING MESSAGE to 0");
					} else if(!ibox.getText().isEmpty()){
						Bot.saveAndStop();
					}
				}
				else if(e.getKeyCode() == 123 || e.getKeyCode() == 116 || e.getKeyCode() == 115){ 
				Bot.saveAndStop();
		}
			}
		@Override
		public void keyTyped(KeyEvent e) {
		}
	};
	
	WindowListener xStateListener = new WindowListener(){
		@Override
		public void windowActivated(WindowEvent arg0) {		}

		@Override
		public void windowClosed(WindowEvent e) {		}

		@Override
		public void windowClosing(WindowEvent e) {	Bot.saveAndStop();	}

		@Override
		public void windowDeactivated(WindowEvent arg0) {		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {		}

		@Override
		public void windowIconified(WindowEvent arg0) {	}

		@Override
		public void windowOpened(WindowEvent arg0) {		}
	};
	
	public static void output(String s){
		if(!s.isEmpty()){
			outList.setText(outList.getText()+"\n Bot: "+s+"\n");
		}
	}
	
	
	
}
