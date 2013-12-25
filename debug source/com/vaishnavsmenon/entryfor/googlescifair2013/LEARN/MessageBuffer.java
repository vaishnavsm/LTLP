package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

//Message codes:
// 0 - Bot Thinking Module (botbrain)
// 1 - Virtual Environment
// 2 - IO Frame
// 3 - Bot In Virtual Environment

public class MessageBuffer {

private static ArrayList<Message> buffer;

public static void initMessageBuffer(){
	buffer = new ArrayList<Message>();
}

public static void addToBuffer(Message m){
	LogUtils.log("adding message: "+m.title);
	buffer.add(m);
}

public static boolean isEmpty(){
	return buffer.isEmpty();
}

public static Message nextMessage(int caller){
	LogUtils.log("\nIn nextMesage\nCaller: "+caller+"\n");
	int i = 0;
	for(Message m : buffer){
		LogUtils.log("Sender: "+m.getSender()+"\nReceiver: "+buffer.get(0).getReciever());
	if(!buffer.isEmpty() && ((m.getSender() != caller) || (m.getReciever() == caller ))){
		LogUtils.log("\nnextMessage worked!\n");
		Message ret = m;
		buffer.remove(i);
		return ret;
	}
	i+=1;
	}
	return null;
}
}
