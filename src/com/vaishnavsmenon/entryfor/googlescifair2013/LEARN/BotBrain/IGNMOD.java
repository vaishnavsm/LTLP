package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class IGNMOD {
	public static ArrayList<String> ignorelist;
	public static final String filename = "IGNORE.lemo";
	public static void initIGNMOD(){
		Scanner s;
		ignorelist = new ArrayList<String>();
		try{
			s = new Scanner(new File(System.getProperty("user.dir")+"/"+filename));
			while(s.hasNext()){
				ignorelist.add(s.next().trim().replace(" ", "").toLowerCase());
			}
			s.close();
		}catch(Exception e){System.out.println("IGNORE MODULE NOT SAVED!!");}
	}
	
	public static void saveIGNMOD(){
		PrintWriter pw;
		try{
			pw = new PrintWriter(filename);
			for(String s : ignorelist){
				pw.write(s+"\n");
			}
			pw.flush();
			pw.close();
		}catch(Exception E){
			System.out.println("  MODULE NOT SAVED!!");
		}
	}
	
	public static boolean canIgnore(String s){
		if(ignorelist.contains(s)){
			return true;
		}
		return false;
	}

	public static void add(String key) {
		if(!ignorelist.contains(key)){
			ignorelist.add(key);
		}
	}
	
}
