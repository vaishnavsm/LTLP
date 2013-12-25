package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.DictEntry;

public class IOM { // Input / Output Module

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
				String token = nextToken(_temp);
				len = token.length();
				_temp = _temp.substring(len).trim();
				while(!token.equals("[/o]")){
					tokens.add(token);
					token = nextToken(_temp);
					len = token.length();
					_temp = _temp.substring(len).trim();
				}
				tokens.add(token);
			}
		}
	
		int words = 0;
		float wordsX = 0.0f;
		for(String s : tokens){
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
					while(!tokens.get(totIdx+x).equals("[/o]")){
						toks++;
						x++;
					}
					if(tokens.get(totIdx+x).equals("[/o]")){toks++;}
					x = tokens.size() + 10;
				}
			}
			
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
			if(s.startsWith("{") && s.endsWith("}")){
				tokStr = s.substring(2,(s.length() - 1)).trim();
				break;
			}
		}
		if(!tokStr.isEmpty()){
			while(!tokStr.startsWith("|")){
				int x = nextToken(tokStr).length();
				
				String p = nextToken(tokStr);
				
				if(p.startsWith("<") && p.endsWith(">")){				
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
			if(s.startsWith("{") && s.endsWith("}")){
				tokStr = s.substring(2,(s.length() - 1)).trim();
				break;
			}
		}
		if(!tokStr.isEmpty()){
			while(!tokStr.startsWith("|")){
				int x = nextToken(tokStr).length();
				
				String p = nextToken(tokStr);
				
				if(p.startsWith("(") && p.endsWith(")")){				
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
	}
	
	public static String[] readVocabFile(String filename){
		
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
