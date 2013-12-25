package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.DictEntry;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.ObjectNode;

public class OLib {
	
	public static ArrayList<ObjectNode> oLib;
	public static ArrayList<DictEntry> vocab;
	public static int numnodes = 0;
	
	public static void initOLib(){
		oLib = new ArrayList<ObjectNode>();
		vocab = new ArrayList<DictEntry>();
	}
	
	public static ObjectNode getObject(String _tag){
		for(ObjectNode on : oLib){
			if(on.tag.equals(_tag)){return on;}
		}
		return null;
	}
	
	public static DictEntry getDictEntry(String key){
		for(DictEntry de : vocab){
			if(de.getKey().equals(key)){return de;}
		}
		
		for(DictEntry de : vocab){
			for(String s : de.subWords){
				if(s.equals(key)){return de;}
			}
		}
		
		return null;
	}
	
	public static void addNode(String x){
		oLib.add(new ObjectNode(numnodes, x));
		numnodes++;
	}
	
	public static void addNode(ObjectNode node){
		oLib.add(node);
		numnodes++;
	}
	
	public static ObjectNode latestNode(){
		return oLib.get(numnodes - 1);
	}
	
	public static boolean vocabContainsEntryWithKey(String s){
		for(DictEntry e : vocab){
			if(e.getKey().equals(s)){
				return true;
			}
		}
		return false;
	}
	
	public static boolean vocabContainsEntryWithValue(String s){
		for(DictEntry e : vocab){
			if(e.getMeaning().equals(s)){
				return true;
			}
		}
		return false;
	}

	public static DictEntry getDictEntryWithMeaning(String obj) {
		for(DictEntry e : vocab){
			if(e.getMeaning().equals(obj)){
				return e;
			}
		}
		return null;
	}
}
