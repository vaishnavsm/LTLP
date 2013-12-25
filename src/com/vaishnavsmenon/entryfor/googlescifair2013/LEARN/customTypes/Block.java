package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Block {
	public Texture tex;
	public int magneticField, temprature;
	public ArrayList<String> properties;
	public int blockType = 0;
	
	public Block(Block b){
		this.tex = b.tex;
		this.magneticField = b.magneticField;
		this.temprature = b.temprature;
		this.properties = new ArrayList<String>();
		for(String s : b.properties){
			this.properties.add(s);
		}
		this.blockType = b.blockType;
		b = null;
	}
	
	public Block(String texture, int blockT, int mag, int temp, String[] props){
		tex = new Texture(System.getProperty("user.dir")+"/imgs/"+texture);
		magneticField = mag;
		blockType = blockT;
		temprature = temp;
		this.properties = new ArrayList<String>();
		for(String str : props){
			properties.add(str);
		}
	}
	
	public Block(String texture, int blockT, String[] props){
		tex = new Texture(System.getProperty("user.dir")+"/imgs/"+texture);
		blockType = blockT;
		magneticField = 0;
		temprature = 0;
		this.properties = new ArrayList<String>();
		for(String str : props){
			properties.add(str);
		}
	}
	
	public Texture getTexture(){
		return tex;
	}
	
	public void render(SpriteBatch batch, int x, int y){
		batch.draw(tex, x, y);
	}
}
