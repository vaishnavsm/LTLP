package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.Main;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class Bot {
	
	public static boolean running = true;
	public final static boolean corelMode = true;

	public static void cycle(){
		if(corelMode){
		Message newMessage = null;
		newMessage = MessageWatcher.getNext();
		if(newMessage == null){return;}
		ArrayList<String> messages = newMessage.message;
		if(newMessage.title.toLowerCase().equals("user_input")){
			String[] words = messages.get(0).replaceAll("\\p{Punct}", "").toLowerCase().replace("  ", " ").split(" ");
			VPM.ProcessVox(words);
		} else if(newMessage.title.toLowerCase().equals("bot_status_update")){
			CRM.Corelate(newMessage);
		}
		}
	}

	public static void init() {
		MessageBuffer.initMessageBuffer();
		OLib.initOLib();
		IOM.compileVocab();
		CRM.initCoRel("corel.lemo");
		ERCD.loadER();
		MessageWatcher.init();
		IGNMOD.initIGNMOD();
	}
	
	public static void saveAndStop(){
		
		OLib.getObject("this").setObj("health", "100");
		ERCD.saveER();
		CRM.saveCoRel("corel.lemo");
		IOM.writeVocabFile("VOCAB.xlesc");
		IGNMOD.saveIGNMOD();
		
		running = false;
		
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {e.printStackTrace();	}
		
		Thread.yield();
		Main.stopThreads();
	}
	
}
