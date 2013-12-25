package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes;

import java.util.TreeMap;

public class ObjectNode {
	

	public int IUID;
	public String tag;
	
	public TreeMap<String, String> obj;	
	public TreeMap<String, String> event;
	
	public ObjectNode(int iuid, String _t){
		IUID = iuid;
		tag = _t;
		obj = new TreeMap<String, String>();
		event = new TreeMap<String, String>();
	}
	
	public boolean isStringNode(){
		return false;
	}
	
	public void addEvent(String K, String V){
		event.put(K,V);
	}
	
	public void removeEvent(String K){
		event.remove(K);
	}
	
	public String getEvent(String K){
		return event.get(K);
	}
	
	public void replaceEvent(String K_old, String K_new, String Value){
		event.remove(K_old);
		event.put(K_new, Value);
	}
	
	public void setEvent(String K, String V){
		event.remove(K);
		event.put(K, V);
	}
	
	public boolean containsEvent(String K){
		return event.containsKey(K);
	}
	public boolean containsEventWithValue(String V){
		return event.containsValue(V);
	}
	
	public void addObj(String K, String V){
		obj.put(K,V);
	}
	
	public void removeObj(String K){
		obj.remove(K);
	}
	
	public String getObj(String K){
		String ret = obj.get(K);
		if(ret == null || ret.isEmpty()){
			return null;
		}
		return ret;
	}
	
	public void replaceObj(String K_old, String K_new, String Value){
		obj.remove(K_old);
		obj.put(K_new, Value);
	}
	
	public void setObj(String K, String V){
		obj.remove(K);
		obj.put(K, V);
	}
	
	public boolean containsObj(String K){
		return obj.containsKey(K);
	}
	public boolean containsObjWithValue(String V){
		return obj.containsValue(V);
	}
	
	
}
