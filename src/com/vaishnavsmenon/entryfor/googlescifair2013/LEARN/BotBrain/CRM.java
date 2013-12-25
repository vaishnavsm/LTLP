package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.CorelEntry;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.ObjectNode;

public class CRM {

	static ArrayList<String> file;
	static ArrayList<CorelEntry> corel;
	static ArrayList<Integer> dHealth;
	static int oldHealth = 100;
	
	public static void giveUpdate(Message m){
		ArrayList<String> content = new ArrayList<String>();
		int health = 100;
		for(String s : m.message){
			if(s.startsWith("h:")){
				health = Integer.parseInt(s.substring(2));
			}
			else{ content.add(s.substring(2)); }
		}
		String blk = "";
		for(ObjectNode o : OLib.oLib){
			boolean checkr =	true;
			if(o.tag.startsWith("block")){
				int i = 0;
				for(String s : content){
					if(o.containsEvent("p"+i) && o.getEvent("p"+i).equals(s)){
						checkr = checkr && true;
					}
					else if(o.containsObj("p"+i) && o.getObj("p"+i).equals(s)){
						checkr = checkr && true;
					}
					else{
						checkr = false;
						break;
					}
					i+=1;
				}
			if(checkr){
				if(o.containsObj("name")){
				blk = o.getObj("name");
				} else {
					blk = o.tag;
				}
				break;
			}
			}
			
		}
		if(OLib.getObject("this").containsObj("health")){
			oldHealth = Integer.parseInt(OLib.getObject("this").getObj("health"));
			OLib.getObject("this").setObj("health", Integer.toString(health));
		}else{
		OLib.getObject("this").addObj("health", Integer.toString(health));
		}
		
		boolean exists = false;
		
		for(ObjectNode o : OLib.oLib){
			if(o.tag.startsWith("block")){			
			boolean e = true;
			int i = 0;
			for(String s : content){
				if((o.getObj("p"+i) != null) && o.getObj("p"+i).equals(s) ){
					e = e && true;
					i+= 1;
				}else{
					e = false;
					i+= 1;
				}
			}
			exists = exists || e;
			}
			}
				
		if(!exists){
			OLib.addNode("block"+(OLib.numnodes+1));
			int i = 0;
			OLib.latestNode().addObj("name", OLib.latestNode().tag);
			for(String s : content){
				OLib.latestNode().addObj("p"+i, s.toLowerCase().trim());
				i+=1;
			}
			ERCD.saveER();
		} 
		
		if(OLib.getObject("lastblock").containsObj("name")){OLib.getObject("lastblock").setObj("name", OLib.getObject("curblock").getObj("name"));}
		else{OLib.getObject("lastblock").addObj("name",  OLib.getObject("curblock").getObj("name"));}
		
		for(Entry<String, String> e : OLib.getObject("curblock").obj.entrySet()){
			if(OLib.getObject("lastblock").containsObj(e.getKey())){OLib.getObject("lastblock").setObj(e.getKey(), e.getValue());}
			else{OLib.getObject("lastblock").addObj(e.getKey(), e.getValue());}
		}
		if(!blk.isEmpty()){
		if(OLib.getObject("curblock").containsObj("name")){OLib.getObject("curblock").setObj("name", blk);}
		else{OLib.getObject("curblock").addObj("name", blk);}
		}
		int i = 0;
		for(String s : content){
			if(OLib.getObject("curblock").containsObjWithValue(s)){OLib.getObject("curblock").setObj("p"+i, s);}
			else{OLib.getObject("curblock").addObj("p"+i, s);}
			i+=1;
		}
		if(OLib.vocabContainsEntryWithValue(OLib.getObject("curblock").getObj("name"))){
			OLib.getObject("this").setEvent("place", OLib.getDictEntryWithMeaning(OLib.getObject("curblock").getObj("name")).getKey());
		}
		else{
			OLib.getObject("this").setEvent("place", OLib.getObject("curblock").getObj("name"));
		}
		ERCD.saveER();
		
		int dHealthc = health - oldHealth;
		dHealth.remove(0);
		dHealth.add(dHealthc);
		oldHealth = health;
		if (dHealthc < dHealth.get(0)){
			ArrayList<Integer> delta = new ArrayList<Integer>();
			ArrayList<String> deltaObj = new ArrayList<String>();
			for(Entry<String, String> e : OLib.getObject("curblock").obj.entrySet()){
				String ed = "0";
				if(OLib.getObject("lastblock").containsObj(e.getKey())){
					ed = OLib.getObject("lastblock").getObj(e.getKey());
				}
				int ped = 10000;
				int ped2 = 10000;
				try{
					ped = Integer.parseInt(ed);
					ped2 = Integer.parseInt(e.getValue());
				}catch(Exception excep){ ped = 10000; ped2 = 10000;}
				if(ped != 10000 && ped2 != 10000){
					delta.add(ped2 - ped);
					deltaObj.add(e.getKey());
				}
			}
			for(int ix = 0; ix < delta.size(); ix++){
					if(delta.get(ix) > 0 && !(CRM.containsEntry(deltaObj.get(ix) , '+', "health", '-'))){
						corel.add(new CorelEntry(deltaObj.get(ix) , '+', "health", '-'));
					}
					else if(delta.get(ix) < 0 && !(corel.contains(new CorelEntry(deltaObj.get(ix) , '-', "health", '-')))){
						corel.add(new CorelEntry(deltaObj.get(ix) , '-', "health", '-'));
					}
			}
			
		}
		if(dHealthc > dHealth.get(0)){
			ArrayList<Integer> delta2 = new ArrayList<Integer>();
			ArrayList<String> delta2Obj = new ArrayList<String>();
			for(Entry<String, String> e : OLib.getObject("curblock").obj.entrySet()){
				String ed = "0";
				if(OLib.getObject("lastblock").containsObj(e.getKey())){
					ed = OLib.getObject("lastblock").getObj(e.getKey());
				}
				int ped = 10000;
				int ped2 = 10000;
				try{
					ped = Integer.parseInt(ed);
					ped2 = Integer.parseInt(e.getValue());
				}catch(Exception excep){ ped = 10000; ped2 = 10000;}
				if(ped != 10000 && ped2 != 10000){
					delta2.add(ped2 - ped);
					delta2Obj.add(e.getKey());
				}
			}
			for(int ix = 0; ix < delta2.size(); ix++){
					if(delta2.get(ix) > 0 && !(CRM.containsEntry(delta2Obj.get(ix) , '+', "health", '+'))){
						corel.add(new CorelEntry(delta2Obj.get(ix) , '+', "health", '+'));
					}
					else if(delta2.get(ix) < 0 && !(CRM.containsEntry(delta2Obj.get(ix) , '-', "health", '+'))){
						corel.add(new CorelEntry(delta2Obj.get(ix) , '-', "health", '+'));
					}
			}
			
		}
		
		CRM.saveCoRel("corel.lemo");
		
	}
	
	public static boolean containsEntry(String s, char c, String s2,
			char d) {
		for(CorelEntry e : corel){
			if(e.s1.equals(s) && e.s1op == c && e.s2.equals(s2) && e.s2op == d){
				return true;
			}
		}
		return false;
	}

	public static void saveCoRel(String filename){
		ArrayList<String> file = new ArrayList<String>();
		for(CorelEntry e : corel){
			file.add("("+e.s1+") {"+e.s1op+"} ["+e.s2+"] <"+e.s2op+">");
		}
		try {
			PrintWriter writer = new PrintWriter(new File(System.getProperty("user.dir")+"/"+filename));
			for(String s:file){
				writer.write(s+"\n");
			}
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e1) { e1.printStackTrace();
		}
	}

	public static void initCoRel(String filename) {
		Scanner scn;
		dHealth =  new ArrayList<Integer>();
		dHealth.add(0);
		dHealth.add(0);
		try {
			scn = new Scanner(new File(System.getProperty("user.dir")+"/"+filename));
		
		file = new ArrayList<String>();
		while(scn.hasNext()){
			file.add(scn.nextLine());
		}
		} catch (FileNotFoundException e) { e.printStackTrace();}
		corel = new ArrayList<CorelEntry>();
		for(String s : file){
			CorelEntry t;
			String a = "", b = "";
			char c = '\0',d = '\0';
			String tok =  nextToken(s);
			while((!tok.isEmpty())){
				s = s.substring(tok.length()).trim();
				if(tok.startsWith("(" )&& tok.endsWith(")")){
					a = tok.substring(1,tok.length()-1);
				}
				else if(tok.startsWith("[" )&& tok.endsWith("]")){
					b = tok.substring(1,tok.length()-1);
				}
				else if(tok.startsWith("{" )&& tok.endsWith("}")){
					c = (char) tok.substring(1,tok.length()-1).toCharArray()[0];
				}
				else if(tok.startsWith("<" )&& tok.endsWith(">")){
					d = (char) tok.substring(1,tok.length()-1).toCharArray()[0];
				}
				tok =  nextToken(s);
//				s = s.substring(tok.length()).trim();
			}
			t = new CorelEntry(a,c,b,d);
			corel.add(t);
		}
	}
	
	public static String nextToken(String s){
		try{
		String tok = "";
		s = s.trim().toLowerCase();
		char[] S = new char[s.length()];
		s.getChars(0, s.length(), S, 0);
		int curIdx = 0;
		boolean inTok = false;
		char curChar = S[curIdx];
		if(curChar == '[' || curChar == '(' || curChar == '{' || curChar == '<'){
			inTok = true;
			char tokCloser;
			if(curChar == '('){
				tokCloser = (char) (curChar+1);
			}else{
				tokCloser = (char) (curChar+2);
			}
			tok += curChar;
			curIdx++;
			curChar = S[curIdx];
			while(inTok){
				if(curChar == tokCloser){
					inTok = false;
					curIdx = s.length() + 1;
					tok += curChar;
					break;
				}else if(curIdx < S.length){
					tok += curChar;
					curIdx++;
					curChar = S[curIdx];
				}else{
					inTok = false;
				}
			}
		}
		else{
			curIdx++;
		}
		return tok;
		}catch(Exception e){
			return "";
		}
	}
	
	public static void Corelate(Message m){
		giveUpdate(m);
		boolean corelDone = false;
		for(Entry<String, String> s : OLib.getObject("curblock").obj.entrySet()){
			int[] dVals = {10000, 10000};
			try{
			dVals[0] = Integer.parseInt(s.getValue());
			dVals[1] = Integer.parseInt(OLib.getObject("lastblock").obj.get(s.getKey()));
			}catch(Exception e){
				dVals[0] = 10000;
				dVals[1] = 10000;
			}
			int deltan = dVals[0] - dVals[1];
			if(deltan == 0) {continue;}
			char op = (deltan>0?'+' : '-');
			CorelEntry entry = null;
			for(CorelEntry e : corel){
				if(e.s1.equals(s.getKey()) && e.s1op == op){
					entry = e;
					break;
				}
			}
			if(entry == null){corelDone = false; continue; }
			else if(entry.s2.equals("health") && entry.s2op == '-'){
				corelDone = true;
				Message mes = new Message("bot_command", 0, 1);
				mes.message.add("go_back");
				MessageBuffer.addToBuffer(mes);
				break;
			} 
			else if(entry.s2.equals("health") && entry.s2op == '+'){
				corelDone = true;
				Message mes = new Message("bot_command", 0, 1);
				mes.message.add("go_ahead");
				MessageBuffer.addToBuffer(mes);
				break;
			} 
			else{
				corelDone = false;
			}
		}
		if(!corelDone){
			Message mes = new Message("bot_command", 0, 1);
			mes.message.add("go_ahead");
			MessageBuffer.addToBuffer(mes);
		}
		
	}
}
