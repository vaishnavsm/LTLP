package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.DictEntry;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.ObjectNode;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.StringNode;

public class VPM { // Vocabulary Processing Module
		
	static boolean question = false;
	static ObjectNode qBlock = null;
	static String qWord = "";
	
	public static void initVPM(){
	}
	
	public static void ProcessVox(String[] words){
		ArrayList<ArrayList<DictEntry>> sentences = ERUM.formSentences(ERUM.wordsToList(words));
		
		ArrayList<String> message = new ArrayList<String>();
		
		if(!question){
			qBlock = null;
			qWord = "";
		for(ArrayList<DictEntry> sentence : sentences){
			if(!message.isEmpty()){
				message.add("and");
			}
			
			DictEntry objectEntry = null;
			ObjectNode object = null;
			for(DictEntry e:sentence){
				 if(e.getMeaning() != null){
					 if(e.getMeaning().startsWith("*") && e.getMeaning().endsWith("*")){
						 	object = new StringNode(OLib.latestNode().IUID+1, e.getKey(), e.getMeaning().substring(1, e.getMeaning().length() - 1));
							objectEntry = e;
							break;
					}else if(OLib.getObject(e.getMeaning()) != null){
						object = OLib.getObject(e.getMeaning());
						objectEntry = e;
						break;
					} else {
					}
				}
				
			}
			boolean respondable = false;
			if(object == null){
				for(DictEntry e : sentence){
					if(ERUM.isObject(e)||ERUM.isEvent(e)){
						for(DictEntry ex : sentence){
							if(ex.getMeaning().equals("?") && !IGNMOD.canIgnore(ex.getKey())){
								LogUtils.output("What " +	ex.getKey().trim().toLowerCase());
								qWord = ex.getKey().trim().toLowerCase();
								question = true;
								break;
							}
						}
					}
				}
			}
			else{
				respondable = true;
				for(DictEntry e:sentence){
					if(ERUM.isObject(e) && object.containsObj(ERUM.getObjectProp(e))){
						message.add(objectEntry.getKey());
						if(!e.getKey().equals("what") && !e.getKey().equals("who")){
							message.add(e.getKey());
						}
						message.add(object.getObj(ERUM.getObjectProp(e)));
					}
					if(ERUM.isEvent(e) && object.containsEvent(ERUM.getEventProp(e))){
						message.add(object.getEvent(ERUM.getEventProp(e)));
					}
				}
			}
			if(message.isEmpty()){respondable = false;}
			if(respondable){
				for(DictEntry en : sentence){
					if(en.getMeaning() == "?"){
						IGNMOD.add(en.getKey());
					}
				}
			} else {
				for(DictEntry ex : sentence){
					if(ex.getMeaning().equals("?") && !IGNMOD.canIgnore(ex.getKey()) && !question){
						LogUtils.output("What " + ex.getKey());
						qWord = ex.getKey();
						qBlock = object;
						question = true;
						break;
					}
				}
			}
		}
		
		String msg = "";
		for(String s : message){
			msg += s;
			msg += " ";
		}
		LogUtils.output(msg);
		
	}
		else{
			//guess it is answer to a question
			for(ArrayList<DictEntry> sentence : sentences){
				for(int i = 0; i < sentence.size(); i++){
					DictEntry e = sentence.get(i);
					if(IGNMOD.canIgnore(e.getKey()) || e.getKey().equals(qWord) || (qBlock!=null && (e.getKey().equals(qBlock.tag) || e.getKey().equals(qBlock.getObj("name"))))){
						sentence.remove(i);
					}
				}
				for(DictEntry ex : sentence){
				if(qBlock != null){
					if(qBlock.containsObj(ex.getKey())){
						OLib.vocab.add(new DictEntry(qWord, "obj."+ex.getKey()));
						LogUtils.output("ok");
					}
					else if(qBlock.containsEvent(ex.getKey())){
						OLib.vocab.add(new DictEntry(qWord, "event."+ex.getKey()));
						LogUtils.output("ok");
					}
					else{
						if(qBlock.containsObjWithValue(ex.getKey())){
							if(qBlock.containsMulObjsWithValue(ex.getKey()) == 0){
							OLib.vocab.add(new DictEntry(qWord, "obj."+qBlock.getObjWithValue(ex.getKey())));
							LogUtils.output("ok");
							} else {
								LogUtils.output("Not able to find "+ex.getKey()+". Too many Values!");
								LogUtils.output("not ok");
							}
						}
						else if(qBlock.containsEventWithValue(ex.getKey())){
							if(qBlock.containsMulEventsWithValue(ex.getKey()) == 0){
							OLib.vocab.add(new DictEntry(qWord, "event."+qBlock.getEventWithValue(ex.getKey())));
							LogUtils.output("ok");
							} else {
								LogUtils.output("Not able to find "+ex.getKey()+". Too many Values!");
								LogUtils.output("not ok");
							}
						}
					}
				}else{
					DictEntry en = null;
					if(OLib.vocabContainsEntryWithKey(ex.getKey())){
						en = OLib.getDictEntry(ex.getKey());
						if(en.getMeaning().contains("block")){
							if(OLib.getObject(en.getMeaning()) != null){
								ObjectNode o = OLib.getObject(en.getMeaning());
								OLib.vocab.add(new DictEntry(qWord, o.getObj("name")));
							}
						}
					}
				}
				}
			}
			question = false;
		}
	}

}
