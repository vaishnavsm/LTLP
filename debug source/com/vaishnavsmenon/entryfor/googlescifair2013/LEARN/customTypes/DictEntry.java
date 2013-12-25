package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes;

import java.util.ArrayList;

public class DictEntry {
	private String mainWord;
	private String mainMeaning;
	public ArrayList<String> subWords;
	public ArrayList<String> subMeanings;
	
	public DictEntry(String key, String tag){
		this.mainWord = key;
		this.mainMeaning = tag;
		subWords = new ArrayList<String>();
		subMeanings = new ArrayList<String>();
	}
	
	public boolean isEntry(String s){
		if(mainWord.equals(s)){
			return true;
		}
		else if(subWords.contains(s)){
			return true;
		}
		return false;
	}
	
	public String getMeaning(){
		return mainMeaning;
	}
	
	public ArrayList<String> getSubMeaningList(){
		return subMeanings;
	}
	
	public String getKey(){
		return mainWord;
	}
	public ArrayList<String> getSubKeyList(){
		return subWords;
	}
}
