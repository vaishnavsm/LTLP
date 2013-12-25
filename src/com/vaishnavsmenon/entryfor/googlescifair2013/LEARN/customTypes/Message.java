package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes;

import java.util.ArrayList;

public class Message {
	public ArrayList<String> message;
	private int Sender;
	private int Reciever = -1;
	public String title;
	
	
	public Message(String t, int s){
		this.message = new ArrayList<String>();
		Sender = s;
		title = t;
	}
	public Message(String t, int s, int r){
		this.message = new ArrayList<String>();
		Sender = s;
		Reciever = r;
		title = t;
	}
	
	public int getSender(){
		return Sender;
	}
	public int getReciever(){		
		return Reciever;
	}
	
	public void addMessage(String t){
		message.add(t);
	}
}
