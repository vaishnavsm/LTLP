package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class MessageWatcher {
	
	static ArrayList<Message> mess;
	
	public static void init(){
		mess = new ArrayList<Message>();
	}
	
	public static Message getNext(){
		Message x = mess.get(0);
		mess.remove(0);
		return x;
	}
	
	public static boolean hasNext(){
		Message m;
		if(!MessageBuffer.isEmpty()){
			LogUtils.log("in hasNext()");
			m = MessageBuffer.nextMessage(0);
			if(m == null){
				LogUtils.log("message is null");
				return false;
			}
			else{
				LogUtils.log("message success!\nTitle: "+m.title);
				mess.add(m);
				return true;
			}
		} else{
			LogUtils.log("in hasNext()");
			LogUtils.log("no new message");
			return false;
		}
	}
}
