package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes;

public class StringNode extends ObjectNode{
	
	String Str;

	public StringNode(int iuid, String _t, String string) {
		super(iuid, _t);
		Str = string;
	}

	public boolean isStringNode(){
		return true;
	}
	
	public void addEvent(String K, String V){
	}
	
	public void removeEvent(String K){
	}
	
	public String getEvent(String K){
		return Str;
	}
	
	public void replaceEvent(String K_old, String K_new, String Value){
	}
	
	public void setEvent(String K, String V){
	}
	
	public boolean containsEvent(String K){
		return false;
	}
	public boolean containsEventWithValue(String V){
		return false;
	}
	
	public void addObj(String K, String V){
		obj.put(K,V);
	}
	
	public void removeObj(String K){
	}
	
	public String getObj(String K){
		return Str;
	}
	
	public void replaceObj(String K_old, String K_new, String Value){
	}
	
	public void setObj(String K, String V){
	}
	
	public boolean containsObj(String K){
		return  true;
	}
	public boolean containsObjWithValue(String V){
		return true;
	}
	
}
