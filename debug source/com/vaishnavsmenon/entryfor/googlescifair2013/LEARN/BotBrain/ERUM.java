package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.DictEntry;

public class ERUM { // External Resources Utility Module
	
	public static ArrayList<DictEntry> wordsToList(String[] words){
		ArrayList<DictEntry> meaning = new ArrayList<DictEntry>();
		for(String s : words){
			if(OLib.getDictEntry(s) != null){
				meaning.add(OLib.getDictEntry(s));
			} else if(s.equals("and")) {meaning.add(new DictEntry("and", "and"));}
			else {meaning.add(new DictEntry(s, "?"));}
		}
		return meaning;
	}
	
	public static ArrayList<ArrayList<DictEntry>> formSentences(ArrayList<DictEntry> meaning){
		ArrayList<ArrayList<DictEntry>> sentences = new ArrayList<ArrayList<DictEntry>>();
		ArrayList<DictEntry> temp = new ArrayList<DictEntry>();
		for (int i = 0; i < meaning.size(); i++){
			DictEntry e = meaning.get(i);
			if((e.getKey() == e.getMeaning()) && (e.getKey().equals("and"))){
				sentences.add(temp);
				temp = null;
				temp = new ArrayList<DictEntry>();
			} 
			else if((i+1) == meaning.size()){
				temp.add(e);
				sentences.add(temp);
				temp = null;
			}
			else{
				temp.add(e);
			}
		}
		return sentences;
	}
	
	public static boolean isObject(DictEntry e){
		if(e.getMeaning().startsWith("obj")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String getObjectProp(DictEntry s){
		return s.getMeaning().substring(4);
	}
	
	public static boolean isEvent(DictEntry e){
		if(e.getMeaning().startsWith("event")){
			return true;
		}
		else{
			return false;
		}
	}
	
	public static String getEventProp(DictEntry s){
		return s.getMeaning().substring(6);
	}
}
