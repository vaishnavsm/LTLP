package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.DictEntry;

public class IOM { // Input / Output Module
	
/*	public static void writeVocabFile(ArrayList<TreeMap<String,String>> __RAWvocab, String filename){
		
		*
		 * This file Saves the arraylist given into the VOCAB.lesc file.
		 * It first extracts the Word -> Meaning TreeMap from the Arraylist,
		 *  makes another array list, which contains the file in a coded format
		 * from the give Key -> Value Style arrangement,
		 * then makes a String[] of the list, and writes it to the file
		 *
		
		TreeMap<String,String> vocab = __RAWvocab.get(0);
		__RAWvocab.clear();		__RAWvocab = null;
		
		ArrayList<String> file = new ArrayList<String>();
		
		for(Map.Entry<String, String> e : vocab.entrySet()){
			if(e.getKey().startsWith("+")){
			file.add("+\n");
			file.add("-"+e.getKey().substring(1)+" = "+e.getValue()+"\n");
			String extra = "";
			extra += "-[";
			for(String s : vocab.keySet()){
				if(vocab.get(s) == e.getValue() && s != e.getKey()){
					//Repeated Value (ex: for me, you, your, etc)
					extra += s;
					extra += ";";
				}
			}
			extra += "]\n";
			if(!extra.contains("-[]\n")){
				file.add(extra);
			}else {extra = "";}}
		} 
		
//		for(int ___c=0; ___c<file.size();___c++){   System.out.print(file.get(___c));		}
		
		String wFile[] = new String[file.size()];
		wFile = file.toArray(wFile);
//		for(int ___c=0; ___c<wFile.length;___c++){ System.out.print(wFile[___c]); }
		
		//Printing the file (wFile[])
		try{
		PrintWriter writer = new PrintWriter(filename);
		for(String line : wFile){
			writer.write(line);
		}
		writer.write("&&EOF&&");
		writer.flush();
		writer.close();
		}catch(Exception e){e.printStackTrace(); System.exit(0);}
		
	}*/
		
	public static int compileVocab(){
		
		if(OLib.vocab == null){
			return -1;
		}
		
		String rawVocab[] = readVocabFile("VOCAB.xlesc");
		if(rawVocab == null){System.err.println("VOCAB FILE NOT FOUND! EXITING!"); System.exit(0);}
		
		ArrayList<String> tokens = new ArrayList<String>(0);
		
		for(int _C = 0; _C < rawVocab.length; _C++){
			
		
			
			if(rawVocab[_C].trim().toLowerCase().startsWith("[o]")){	
				String _temp = rawVocab[_C].trim().toLowerCase();
				int len = 0;
				log("\nITER");
				String token = nextToken(_temp);
				len = token.length();
				_temp = _temp.substring(len).trim();
				while(!token.equals("[/o]")){
					log("\nITER");
					tokens.add(token);
					token = nextToken(_temp);
					len = token.length();
					_temp = _temp.substring(len).trim();
				}
				tokens.add(token);
			}
		}
		
		for (String p : tokens){
			System.out.println("|"+p+"|");
		}
	
		int words = 0;
		float wordsX = 0.0f;
		for(String s : tokens){
			log(s);
			if(s.equals("[o]")){ wordsX += 0.5f; }
			else if(s.equals("[/o]")){ wordsX += 0.5f; }
		}
		words = (int) (wordsX+0.5f);
		
		int totIdx = 0;
		for(int c = 0; c < words; c++){
			int toks = 0;
			for(int x = 0; x < tokens.size(); x++){
				if(tokens.get(totIdx+x).equals("[o]")){
					toks++;
					x+=1;
					log(tokens.get(totIdx+x));
					while(!tokens.get(totIdx+x).equals("[/o]")){
						log(tokens.get(totIdx+x));
						toks++;
						x++;
					}
					if(tokens.get(totIdx+x).equals("[/o]")){toks++;}
					log(tokens.get(totIdx+x));
					x = tokens.size() + 10;
				}
			}
			log(Integer.toString(toks));
			
			String mainKey, mainMeaning;
			
			mainMeaning = getMainMeaningFromTokens(arrListCurTokens(tokens, toks, totIdx));
			mainKey = getMainKeyFromTokens(arrListCurTokens(tokens, toks, totIdx));
			String subMeanings[] = getSubMeaningsFromTokens(arrListCurTokens(tokens, toks, totIdx));
			String subKeys[] = getSubKeysFromTokens(arrListCurTokens(tokens, toks, totIdx));
			
			DictEntry entry = new DictEntry(mainKey, mainMeaning);
			if(subMeanings != null) {for(String s:subMeanings){entry.subMeanings.add(s);}}
			if(subKeys != null) {for(String s:subKeys){entry.subWords.add(s);}}
			
			OLib.vocab.add(entry);
			totIdx += toks;
		}
		return 1;
	}
	
	public static String[] arrListCurTokens(ArrayList<String> tokens, int toks,
			int ofset) {
		ArrayList<String> temp = new ArrayList<String>();
		for(int x = 0; x < toks; x++){
			if(!(tokens.get(ofset+x).equals("[o]")) && !(tokens.get(ofset+x).equals("[/o]"))){
				temp.add(tokens.get(ofset+x));
			}
		}
		String[] ret = new String[temp.size()];
		ret = temp.toArray(ret);
		return ret;
	}

	public static String getMainMeaningFromTokens(String[] tokens){
		String ret = "";
		for(String s : tokens){
			if(s.trim().startsWith("<")&&s.trim().endsWith(">")){
				ret = s.substring(1, (s.length() - 1));
				break;
			}
		}
		return ret;
	}
	
	public static String getMainKeyFromTokens(String[] tokens){
		String ret = "";
		for(String s : tokens){
			if(s.trim().startsWith("(")&&s.trim().endsWith(")")){
				ret = s.substring(1, (s.length() - 1));
				break;
			}
		}
		return ret;
	}
	
	public static String[] getSubMeaningsFromTokens(String[] tokens){
		ArrayList<String> temp = new ArrayList<String>();
		String tokStr = "";
		for(String s : tokens){
			log("TRY: "+s);
			if(s.startsWith("{") && s.endsWith("}")){
				log("SUCCESS: "+s);
				tokStr = s.substring(2,(s.length() - 1)).trim();
				break;
			}
		}
		if(!tokStr.isEmpty()){
			while(!tokStr.startsWith("|")){
				int x = nextToken(tokStr).length();
				
				String p = nextToken(tokStr);
				
				if(p.startsWith("<") && p.endsWith(">")){				
					log(nextToken(tokStr).substring(1,x-1));
					temp.add(nextToken(tokStr).substring(1,x-1));
					tokStr = tokStr.substring(x);				
				}else{
					tokStr = tokStr.substring(x);
				}
			} 
		} else {
			return null;
		}
		
		String[] ret = new String[temp.size()];
		ret = temp.toArray(ret);
		return ret;
	}
	
	public static String[] getSubKeysFromTokens(String[] tokens){
		ArrayList<String> temp = new ArrayList<String>();
		String tokStr = "";
		for(String s : tokens){
			log("TRY: "+s);
			if(s.startsWith("{") && s.endsWith("}")){
				log("SUCCESS: "+s);
				tokStr = s.substring(2,(s.length() - 1)).trim();
				break;
			}
		}
		if(!tokStr.isEmpty()){
			while(!tokStr.startsWith("|")){
				int x = nextToken(tokStr).length();
				
				String p = nextToken(tokStr);
				
				if(p.startsWith("(") && p.endsWith(")")){				
					log(nextToken(tokStr).substring(1,x-1));
					temp.add(nextToken(tokStr).substring(1,x-1));
					tokStr = tokStr.substring(x);				
				}else{
					tokStr = tokStr.substring(x);
				}
			} 
		} else {
			return null;
		}
		
		String[] ret = new String[temp.size()];
		ret = temp.toArray(ret);
		return ret;
	}
	
	public static String nextToken(String s){
		log(s);
		String tok = "";
		s = s.trim().toLowerCase();
		char[] S = new char[s.length()];
		s.getChars(0, s.length(), S, 0);
		int curIdx = 0;
		boolean inTok = false;
		char curChar = S[curIdx];
		if(curChar == '[' || curChar == '(' || curChar == '{' || curChar == '<'){
			log(Character.toString(curChar));
			inTok = true;
			char tokCloser;
			if(curChar == '('){
				tokCloser = (char) (curChar+1);
			}else{
				tokCloser = (char) (curChar+2);
			}
			log("Closer: "+Character.toString(tokCloser));
			tok += curChar;
			curIdx++;
			curChar = S[curIdx];
			log("CURR BEFORE WHILE:"+Character.toString(curChar));
			while(inTok){
				if(curChar == tokCloser){
					inTok = false;
					curIdx = s.length() + 1;
					tok += curChar;
					log("TOKEN: |"+tok+"|");
					break;
				}else if(curIdx < S.length){
					tok += curChar;
					curIdx++;
					curChar = S[curIdx];
					log("CURR: "+Character.toString(curChar));
					log("TOKEN: "+tok);
				}else{
					inTok = false;
				}
			}
		}
		else{
			curIdx++;
		}
		return tok;
	}
	
	public static void log(String s){
		System.out.println(s);
	}
	
	public static ArrayList<TreeMap<String,String>> readCompileVocab(){
		
		/*
		 * This function returns two TreeMap objects in an ArrayList,
		 * One with the words as Keys and meanings as Values
		 * It Returns in such a way that the main word's key has a + in front.
		 * For example, +me means THIS, so does you and your. But the key +me, or the word "me"
		 * MUST be given privilege over you and your, so the robot itself will use "me" to refer
		 * itself
		 * 
		 * The other is the reverse, with the meaning as Key and the word as Value
		 * 
		 * It will be returned as, [0] = Word -> Meaning, [1] = Meaning -> Word
		 * 
		 * All DEBUG Codes have been //COMMENTED Out
		 */
		
		TreeMap<String,String> returnFile = new TreeMap<String,String>();
		String[] raw_file = readVocabFile("VOCAB.lesc");
		if(raw_file == null){System.err.println("VOCAB FILE NOT FOUND! EXITING!"); System.exit(0);}
		
		System.out.println("for loop entry");
		for(int __C__ = 0; __C__ < (raw_file.length-2);__C__++){
			System.out.println("CURR: "+raw_file[__C__]);
			if(raw_file[__C__].toLowerCase().trim().startsWith("+")){
				System.out.println("ENTERED IF");
				String[] split_extra;
				String[] split;
				while(__C__+1<raw_file.length&&!(raw_file[__C__+1].startsWith("+"))){
					__C__++;
					System.out.println("For iteration, "+__C__);
					if(!raw_file[__C__].startsWith("-")){
						__C__++;
						continue;
					}else{
						split = raw_file[__C__].toLowerCase().split(" = ");
						if(split[0].startsWith("-")) {split[0] = split[0].substring(1);}
						returnFile.put("+"+split[0], split[1]);
						System.out.println("+"+split[0]+" is "+ split[1]);
						
						if(__C__+1<raw_file.length&&(raw_file[__C__+1].startsWith("-[")) && (raw_file[__C__+1].endsWith("]"))){
							__C__ += 1;
							System.out.println("Got Extras");
							String pString = raw_file[__C__].toLowerCase().substring(2,raw_file[__C__].length()-1);
							split_extra = pString.split(";");
							for(int _ct_ = 0; _ct_<split_extra.length;_ct_++){
								returnFile.put(split_extra[_ct_], split[1]);
								System.out.println(split_extra[_ct_]+" = "+ split[1]);
							}
						}
						
						
						__C__++;
					}
				}
				System.out.println("EXIT IF");
			}
			
			
		}
		
		//Making [1]
		TreeMap<String,String> rev = new TreeMap<String,String>();
		
		for(Map.Entry<String,String> entry : returnFile.entrySet()){
			if(entry.getKey().startsWith("+")){
			rev.put(entry.getValue(), entry.getKey().substring(1));
			}
		}
		//Returner
		ArrayList<TreeMap<String,String>> returner = new ArrayList<TreeMap<String,String>>();
		returner.add(returnFile);
		returner.add(rev);
		
		return returner;
	}
	
	public static String[] readVocabFile(String filename){
		
		/*
		 * This function returns the VOCAB.lesc in a String array
		 */
	
		ArrayList<String> fileAL = new ArrayList<String>();
	
		try{
			Scanner scanner = new Scanner(new File(System.getProperty("user.dir")+"/"+filename));
			while(scanner.hasNext()){
				fileAL.add(scanner.nextLine());
			}
		}catch(Exception e){
			e.printStackTrace();
		return null;
		}
		
		for(int _counter = 0; _counter < fileAL.size(); _counter++){
			fileAL.set(_counter, fileAL.get(_counter).replace('\n', ' ').trim().toLowerCase());
		}
		
		String[] file = new String[fileAL.size()];
		for(int __counter = 0; __counter < fileAL.size(); __counter++){
			file[__counter] = fileAL.get(__counter);
		}
		return file;
	}

	public static void writeVocabFile(String filename){
		ArrayList<String> fileBuffer = new ArrayList<String>();
		for(DictEntry e : OLib.vocab){
			String entry = "[o] ";
			entry += ("("+e.getKey()+") ");
			String extraWords = "{|";
			if(!(e.subWords.isEmpty())){
				for(String s : e.subWords){
					extraWords += ("("+s+")");
				}
			}
			extraWords += "|}";
			if(!extraWords.equals("{||}")){
				entry += extraWords + " ";
			}
			
			entry += ("<"+e.getMeaning()+"> ");
			String extraMeanings = "{|";
			if(!(e.subMeanings.isEmpty())){
				for(String s : e.subMeanings){
					extraMeanings += ("<"+s+">");
				}
			}
			extraMeanings += "|}";
			if(!extraMeanings.equals("{||}")){
				entry += extraMeanings + " ";
			}
			entry += "[/o]\n";
			fileBuffer.add(entry);
		}
		
		try{
			PrintWriter writer = new PrintWriter(filename);
			for(String line : fileBuffer){
				writer.write(line);
			}
			writer.write("&&EOF&&");
			writer.flush();
			writer.close();
			}catch(Exception e){e.printStackTrace(); System.exit(0);}
	}
	
}
