package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class MessageBuffer {

private static ArrayList<Message> buffer;

public static void initMessageBuffer(){
	buffer = new ArrayList<Message>();
}

public static void addToBuffer(Message m){
	buffer.add(m);
}

public static boolean isEmpty(){
	return buffer.isEmpty();
}

public static Message nextMessage(int caller){
	int i = 0;
	for(Message m : buffer){
	if(!buffer.isEmpty() && ((m.getSender() != caller) || (m.getReciever() == caller ))){
		Message ret = m;
		buffer.remove(i);
		return ret;
	}
	i+=1;
	}
	return null;
}
}
