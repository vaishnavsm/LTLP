package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.Main;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class Bot {
	
	public static boolean running = true;

	public static void cycle(){
		LogUtils.log("Cycle");
		Message newMessage = null;
		newMessage = MessageWatcher.getNext();
		LogUtils.log("New Message");
		if(newMessage == null){return;}
		LogUtils.log("Message not null");
		ArrayList<String> messages = newMessage.message;
		if(newMessage.title.toLowerCase().equals("user_input")){
			String[] words = messages.get(0).replaceAll("\\p{Punct}", "").toLowerCase().replace("  ", " ").split(" ");
			VPM.ProcessVox(words);
		} else if(newMessage.title.toLowerCase().equals("bot_status_update")){
			CRM.Corelate(newMessage);
		}
	}

	public static void init() {
		LogUtils.log("Init");

		MessageBuffer.initMessageBuffer();
		LogUtils.log("message buffer");
		OLib.initOLib();
		LogUtils.log("olib");
		IOM.compileVocab();
		LogUtils.log("iom");
		CRM.initCoRel("corel.lemo");
		LogUtils.log("corel");
		ERCD.loadER();
		LogUtils.log("ercd");
		MessageWatcher.init();
		LogUtils.log("watcher");
		IGNMOD.initIGNMOD();
		LogUtils.log("ignmod");
		
		LogUtils.log("end");
	}
	
	public static void saveAndStop(){
		
		LogUtils.log("Called SAVEANDSTOP()");
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
