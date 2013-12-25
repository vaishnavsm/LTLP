package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;

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
		}catch(Exception e){LogUtils.log("IGNORE MODULE NOT STARTED!");}
	}
	
	public static void saveIGNMOD(){
		PrintWriter pw;
		try{
			pw = new PrintWriter(filename);
			for(String s : ignorelist){
				pw.write(s);
			}
			pw.flush();
			pw.close();
		}catch(Exception E){
			LogUtils.log("IGNORE MODULE NOT SAVED!!");
		}
	}
	
	public boolean canIgnore(String s){
		boolean ret = false;
		for(String st : ignorelist){
			if(st.equals(s) || st == s){
				ret = true;
			}
		}
		return ret;
	}
	
}
