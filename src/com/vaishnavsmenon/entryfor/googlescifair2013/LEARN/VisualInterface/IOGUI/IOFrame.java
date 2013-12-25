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
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.BadLocationException;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain.Bot;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class IOFrame extends JFrame{
	
	private static final long serialVersionUID = -681607203789878682L;
	
	static JButton x;
	static JTextField ibox;
	static JTextArea outList;
	static JScrollPane pane;
	static JPanel panel;
	
	public IOFrame(){
		
		this.setSize(470 , 500);
		this.setTitle("LTLR: Input/Output");
		this.setResizable(false);
		this.addKeyListener(xKeyListener);
		this.setLocation(200,120);
		this.addWindowListener(xStateListener);
		
		panel = new JPanel();
		panel.setSize(470, 500);
		panel.setLayout(null);
		
		x = new JButton();
		x.setText("Send");
		x.setBounds(0, 435, 100, 40);
		x.addActionListener(xListener);
		x.addKeyListener(xKeyListener);
		x.setVisible(true);
		
		ibox = new JTextField();
		ibox.setBounds(100, 435, 350, 40);
		ibox.addKeyListener(xKeyListener);
		ibox.setVisible(true);
		
		outList = new JTextArea(1,29);
		outList.setBackground(Color.WHITE);
		outList.setBounds(0, 0, 450, 432);
		outList.setEditable(false);
		outList.addKeyListener(xKeyListener);
		outList.setVisible(true);
		
		pane = new JScrollPane(outList);
		pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		pane.setBounds(0,0,470,432);
		pane.getViewport().add(outList);
		pane.setVisible(true);
		
		this.getContentPane().add(panel);
		
		panel.add(x);
		panel.add(ibox);
		panel.add(pane);
		
		panel.setVisible(true);	
		
	}
	
	public ActionListener xListener = new ActionListener(){

		@Override
		public void actionPerformed(ActionEvent event) {
			if(!(ibox.getText().toLowerCase().trim().equals("i quit".trim().toLowerCase())) && !(ibox.getText().toLowerCase().trim().equals("clear".trim().toLowerCase())) &&!ibox.getText().isEmpty()){
			if(Bot.corelMode){
				outList.setText(outList.getText()+"\n User: "+ibox.getText()+"\n");
				String input = ibox.getText();
				ibox.setText("");
				Message m = new Message("user_input",2,0);
				m.addMessage(input.toLowerCase());
				MessageBuffer.addToBuffer(m);
			}
			} else if(!ibox.getText().isEmpty() && ibox.getText().toLowerCase().trim().equals("i quit".trim().toLowerCase())){
				Bot.saveAndStop();
			}
			else if(!ibox.getText().isEmpty()){
				ibox.setText("");outList.setText("");
			}
		}
		
	};
	
	public KeyListener xKeyListener = new KeyListener(){

		@Override
		public void keyPressed(KeyEvent e) {		}

		@Override
		public void keyReleased(KeyEvent e) {
			
			if(e.getKeyCode() == 10){
				if(Bot.corelMode){
				if(!(ibox.getText().toLowerCase().trim().equals("i quit".trim().toLowerCase())) && !(ibox.getText().toLowerCase().trim().equals("clear".trim().toLowerCase())) &&!ibox.getText().isEmpty()){
					outList.setText(outList.getText()+"\n User: "+ibox.getText()+"\n");
					String input = ibox.getText();
					ibox.setText("");
					Message m = new Message("user_input",2,0);
					m.addMessage(input.toLowerCase());
					MessageBuffer.addToBuffer(m);
					
					} else if(!ibox.getText().isEmpty() && ibox.getText().toLowerCase().trim().equals("i quit".trim().toLowerCase())){
						Bot.saveAndStop();
					}
					else if(!ibox.getText().isEmpty()){
						ibox.setText("");outList.setText("");
					}
			}
				else if(e.getKeyCode() == 123 || e.getKeyCode() == 116 || e.getKeyCode() == 115){ 
				Bot.saveAndStop();
				}
				else if(e.getKeyChar() == 'C'){
					ibox.setText("");outList.setText("");
				}
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
	
	public static void output(String s) throws BadLocationException{
		if(!s.isEmpty()){
			outList.setText(outList.getText()+"\n Bot: "+s+"\n");
			outList.scrollRectToVisible(outList.modelToView(outList.getDocument().getLength()));
		}
	}
	
	
	
}
