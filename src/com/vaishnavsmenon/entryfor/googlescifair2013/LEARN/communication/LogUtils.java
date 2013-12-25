package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication;

import javax.swing.text.BadLocationException;

import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.IOGUI.IOFrame;

public class LogUtils {
public static void output(String s){
	try {
		IOFrame.output(s);
	} catch(BadLocationException e) {
		e.printStackTrace();
	}
}
public static void log(Object o){
	System.out.println(o);
}
}
