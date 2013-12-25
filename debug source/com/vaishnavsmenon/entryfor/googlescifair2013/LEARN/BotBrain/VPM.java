package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.util.ArrayList;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.DictEntry;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.ObjectNode;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.StringNode;

public class VPM { // Vocabulary Processing Module
		
	public static void ProcessVox(String[] words){
		
		LogUtils.log("Vox Called");
		
		/*
		 * This method processes the vocabulary to give meaning to the words given,
		 * and take necessary action.
		 * 
		 */
		
		/*
		 * The processing will take a tiered process,
		 * I: Break the meaning sentences separated by 'and', DONE
		 * II: Try to get the "Meaning" for these sets DONE
		 * III: If Words are missing, try to make sense without these words. DONE
		 * IV: If that is not possible, get the meaning  <-- (LEARNING)
		 */
		
		ArrayList<ArrayList<DictEntry>> sentences = ERUM.formSentences(ERUM.wordsToList(words));
		
		ArrayList<String> message = new ArrayList<String>();
		
		for(ArrayList<DictEntry> sentence : sentences){
			/*
			 * Here we must find the meaning in each sentence.
			 * To do this, we must find the 'object' each sentence.
			 * Then the identifier.
			 */
			if(!message.isEmpty()){
				message.add("and");
			}
			
			DictEntry objectEntry = null;
			ObjectNode object = null;
			for(DictEntry e:sentence){
				LogUtils.log("Dict entry: "+e.getKey());
				 if(e.getMeaning() != null){
					 if(e.getMeaning().startsWith("*") && e.getMeaning().endsWith("*")){
						 	object = new StringNode(OLib.latestNode().IUID+1, e.getKey(), e.getMeaning().substring(1, e.getMeaning().length() - 1));
							objectEntry = e;
							LogUtils.log("Obj not null and is string");
							break;
					}else if(OLib.getObject(e.getMeaning()) != null){
						LogUtils.log("Object not null");
						object = OLib.getObject(e.getMeaning());
						objectEntry = e;
						break;
					} else {
						LogUtils.log("Object Null");
					}
				}
				
			}
			if(object == null){
				LogUtils.log("Object Null");
				for(DictEntry e : sentence){
					if(ERUM.isObject(e)||ERUM.isEvent(e)){
						for(DictEntry ex : sentence){
							if(ex.getMeaning() == "?"){
								LogUtils.output("Whats " + ex.getKey());
							}
						}
					}
				}
			}
			else{
				for(DictEntry e:sentence){
					if(ERUM.isObject(e) && object.containsObj(ERUM.getObjectProp(e))){
						message.add(objectEntry.getKey());
						message.add(object.getObj(ERUM.getObjectProp(e)));
					}
					if(ERUM.isEvent(e) && object.containsObj(ERUM.getEventProp(e))){
						message.add(objectEntry.getKey());
						message.add(object.getEvent(ERUM.getEventProp(e)));
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
		log(msg);
		
	}

	public static void log(Object s){
		LogUtils.log(s);
	}
	
}
