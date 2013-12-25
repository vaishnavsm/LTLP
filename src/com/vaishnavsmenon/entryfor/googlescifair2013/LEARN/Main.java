package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain.Bot;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain.MessageWatcher;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.IOGUI.BlockStatusFrame;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.IOGUI.IOFrame;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.VirtualWorld.MainViewFrame;

public class Main {

	public static LwjglApplication app;
	public static IOFrame ioframe;
	public static BlockStatusFrame frm;
	static Thread learnThr, IOFrm, VisInface;
	
//Launcher
	public static void main(String args[]) throws InterruptedException{
		
		learnThr = new Thread(new Runnable()
		{
			@Override
			public void run() {	
				Bot.init();
				
				while( Bot.running ){
				if(MessageWatcher.hasNext()){
					Bot.cycle();
				}
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {	e.printStackTrace();	}
				}
				
				Bot.saveAndStop();
			}
		}	
		);
		
		Thread.sleep(1000);

		VisInface = new Thread(new Runnable()
		{
			@Override
			public void run() {
				LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
				cfg.title = "LTLP: Virtual Environment";
				cfg.useGL20 = false;
				cfg.width = 512;
				cfg.height = 512;
				cfg.x = 675;
				cfg.y = 100;
				app = new LwjglApplication(new MainViewFrame(), cfg);
			}
			
		}
		);
		
		Thread.sleep(1000);
		
		IOFrm = new Thread(new Runnable()
		{
			@Override
			public void run() {
				ioframe = new IOFrame();
				ioframe.setVisible(true);
				
				frm = new BlockStatusFrame();
				frm.setVisible(true);
			}
			
		}
		); 
		
		learnThr.start();
		VisInface.start();
		IOFrm.start();
	}

	public static void stopThreads() {
		
		app.exit();
		
		try{
		Thread.sleep(3000);
		} catch(Exception e){ e.printStackTrace(); 	}
		
		ioframe.dispose();
		frm.dispose();
		try{
			Thread.sleep(500);
			} catch(Exception e){e.printStackTrace(); }
		Thread.yield();
		
		System.exit(0);
		
	}
}
