package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class MessageWatcher {
	
	static ArrayList<Message> mess1;
	static ArrayList<Message> mess2;
	static ArrayList<Message> mess3;
	
	public static void init(){
		mess1 = new ArrayList<Message>();
		mess2 = new ArrayList<Message>();
		mess3 = new ArrayList<Message>();
	}
	
	public static Message getNext(int caller){
		if(caller == 1 && !mess1.isEmpty()){
			Message x = mess1.get(0);
			mess1.remove(0);
			return x;
		}
		else if(caller == 2 && !mess2.isEmpty()){
			Message x = mess2.get(0);
			mess2.remove(0);
			return x;
		}
		else if(caller == 3 && !mess3.isEmpty()){
			Message x = mess3.get(0);
			mess3.remove(0);
			return x;
		}
		return null;
	}
	
	public static boolean hasNext(int caller){
		if(caller == 1 && !mess1.isEmpty()){
			return true;
		}
		else if(caller == 2 && !mess2.isEmpty()){
			return true;
		}
		else if(caller == 3 && !mess3.isEmpty()){
			return true;
		}
		return false;
	}
	
	public static void update(int caller){
		if(!MessageBuffer.isEmpty()){
			if(caller == 1){
				Message m;
				m = MessageBuffer.nextMessage(1);
				if(m != null){
					mess1.add(m);
				}
			} else if(caller == 2){
				Message m;
				m = MessageBuffer.nextMessage(2);
				if(m != null){
					mess2.add(m);
				}
			}
			else if(caller == 3){
				Message m;
				m = MessageBuffer.nextMessage(3);
				if(m != null){
					mess3.add(m);
				}
				
			}
		}
	}
}
