package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Map.Entry;
import java.util.Scanner;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.ObjectNode;

public class ERCD {//The External Resources Compiler / Decompiler

	static ArrayList<String> ER;
	
	public static void saveER(){
		ArrayList<String> tempFile = new ArrayList<String>();
		for(ObjectNode o : OLib.oLib){
			
			tempFile.add("{"+o.tag+"}\n");
			
			if(!o.obj.isEmpty()){
			tempFile.add("\t(obj)\n");
			for(Entry<String, String> e : o.obj.entrySet()){
				tempFile.add("\t\t["+e.getKey()+"]\n");
				tempFile.add("\t\t\t<"+e.getValue()+">\n");
			}}
			
			if(!o.event.isEmpty()){
			tempFile.add("\t(event)\n");
			for(Entry<String, String> e : o.event.entrySet()){
				tempFile.add("\t\t["+e.getKey()+"]\n");
				tempFile.add("\t\t\t<"+e.getValue()+">\n");
			}
			}
		}
		
		String[] file = new String[tempFile.size()];
		file = tempFile.toArray(file);
		
		try{
			PrintWriter writer = new PrintWriter("OBJECTS.lelb");
			for(String s : file){
				writer.write(s);
			}
			writer.write("&EOF&");
			writer.flush();
			writer.close();
		} catch(Exception e){
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	public static void loadER(){
		ER = new ArrayList<String>();
		loadERFile();
		
		compileERFile();
//		DEBUG();
		
	}
	
	private static void compileERFile() {
	
		int objeventstate = -1;
		
		for(int i = 0; i<ER.size(); i++){
			if(ER.get(i).startsWith("{")){
				OLib.addNode(ER.get(i).substring(1,ER.get(i).length() - 1));
				objeventstate = -1;
			}
			else if(ER.get(i).startsWith("(")){
				if(ER.get(i).equals("(obj)")){
					objeventstate = 0;
				}else if(ER.get(i).equals("(event)")){
					objeventstate = 1;
				}
			}
			else if((ER.get(i).startsWith("[")) && (ER.get(i+1).startsWith("<"))){
				if(objeventstate == 0){OLib.latestNode().obj.put(ER.get(i).substring(1,ER.get(i).length() - 1), ER.get(i+1).substring(1,ER.get(i+1).length() - 1));}
				else if(objeventstate == 1){OLib.latestNode().event.put(ER.get(i).substring(1,ER.get(i).length() - 1), ER.get(i+1).substring(1,ER.get(i+1).length() - 1));}
				i+=1;
			}
		}
		
	}
	
	@SuppressWarnings("unused")
	private static void DEBUG(){
		int nn = OLib.numnodes;
		for(int __I = 0; __I < nn; __I++){
			ObjectNode tempNode = OLib.oLib.get(__I);
			log(tempNode.tag+"   --->");
			for(Entry<String,String> e : tempNode.obj.entrySet()){
				log(e.getKey()+" => "+e.getValue());
			}
			log("");
			for(Entry<String,String> e : tempNode.event.entrySet()){
				log(e.getKey()+" => "+e.getValue());
			}
		}
	}
	
	private static void log(String x){
		System.out.println(x);
	}

	private static void loadERFile(){
		try{
			Scanner scanner = new Scanner(new File(System.getProperty("user.dir")+"/OBJECTS.lelb"));
			while(scanner.hasNext()){
				ER.add(scanner.nextLine());
			}
		}catch(Exception e){
			e.printStackTrace();
		return;
		}
		
		for(int _counter = 0; _counter < ER.size(); _counter++){
			ER.set(_counter, ER.get(_counter).replace('\n', ' ').trim().toLowerCase().replaceAll("\\s", ""));
		}
	}
	
}
