package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.VirtualWorld;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.BotBrain.Bot;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.MessageWatcher;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Block;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;


public class MainViewFrame implements ApplicationListener, InputProcessor{

	public SpriteBatch batch;
	ArrayList<Block> blocks;
	static ArrayList<ArrayList<Block>> blockList;
	BotVF bot;
	OrthographicCamera cam;
	public static int scrHeight, scrWidth;

	int stage[][] = 
		{		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
		 		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		 		{0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
		 		{0,0,1,1,1,1,0,0,0,0,0,4,0,0,0,0},
		 		{0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0},
		 		{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}, 
		 		{0,0,0,0,0,0,0,0,6,0,0,0,2,0,0,0},
		 		{0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0},
		 		{0,0,5,3,0,0,0,0,0,0,2,2,2,0,0,0},
		 		{0,0,3,0,3,0,0,0,0,0,2,2,2,0,0,0}, 
		 		{0,0,3,3,3,0,0,0,0,0,2,0,0,0,0,0},
		 		{0,0,3,3,3,0,0,0,0,0,2,0,0,0,0,0},
		 		{0,0,0,3,0,0,0,0,0,0,0,0,0,0,0,0},
		 		{0,0,0,0,0,0,0,0,0,0,0,0,0,5,0,0}, 
		 		{0,0,0,6,0,0,0,0,0,0,0,0,0,0,0,0},
		 		{0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0}	};

	int stageDead[][] =  
		{	{0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0},
			{0,0,4,4,4,4,4,0,6,0,4,4,4,4,4,0},
			{0,0,4,0,0,4,0,0,6,0,4,0,0,4,0,0},
			{0,0,0,4,4,0,0,0,6,0,0,4,4,0,0,0},
			{4,4,0,0,0,0,0,0,6,0,0,0,0,0,0,0},
			{4,4,0,4,4,4,0,0,6,0,4,4,4,4,4,0},
			{0,0,0,0,0,0,0,0,6,0,4,0,4,0,4,0},
			{4,4,0,4,4,4,0,0,6,0,4,0,4,0,4,0},
			{4,4,0,0,0,0,0,0,6,0,4,0,0,0,4,0},
			{0,0,0,0,0,0,0,0,6,0,0,0,0,0,0,0},
			{6,6,6,6,6,6,6,6,6,0,4,4,0,0,0,0},
			{0,0,0,0,0,5,5,0,6,0,0,4,4,4,0,0},
			{0,0,0,5,5,0,0,0,6,0,0,4,0,0,4,0},
			{0,0,5,4,0,0,0,0,6,0,0,4,4,4,0,0},
			{0,0,0,5,5,0,0,0,6,0,4,4,0,0,0,0},
			{0,0,0,0,0,5,5,0,6,0,0,0,0,0,0,0}	};
	
	@Override
	public void create() {
		
		MessageWatcher.init();
		
		batch = new SpriteBatch();
		blocks = new ArrayList<Block>();
		blockList = new ArrayList<ArrayList<Block>>();
		
		scrHeight = Gdx.graphics.getDesktopDisplayMode().height;
		scrWidth = Gdx.graphics.getDesktopDisplayMode().width;
		
		String[] props = {Integer.toString(0),"grey"};
		Block x = new Block("basefloor.png", 0,props);
		props = null;
		blocks.add(0,x);
		String[] props1 = {Integer.toString(0),"red"};
		x = new Block("redfloor.png", 1,props1);
		props1 = null;
		blocks.add(1,x);
		String[] props2 = {Integer.toString(0),"green"};
		x = new Block("greenfloor.png", 2,props2);
		props2 = null;
		blocks.add(2,x);
		String[] props3 = {Integer.toString(0),"blue"};
		x = new Block("bluefloor.png", 3,props3);
		props3 = null;
		blocks.add(3,x);	
		String[] props4 = {Integer.toString(0),"red"};
		x = new Block("fire.png", 4,props4);
		props4 = null;
		blocks.add(4,x);
		String[] props5 = {Integer.toString(10),"grey"};
		x = new Block("magnet.png", 5,props5);
		props5 = null;
		blocks.add(5,x);
		String[] props6 = {Integer.toString(1),"silver"};
		x = new Block("spikes.png", 6,props6);
		props6 = null;
		blocks.add(6,x);
		Gdx.input.setInputProcessor(this);
		
		for(int i = 0; i < 16; i++){
			blockList.add(i,new ArrayList<Block>());
			for(int j = 0; j < 16; j++){
				blockList.get(i).add(new Block(blocks.get(stage[i][j])));
			}
		}
		
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				if(blockList.get(i).get(j).blockType == 4){
					blockList.get(i).get(j).temprature = 4;
					int[][] coords = {{i, j+1},{i-1, j},{i, j-1}, {i+1, j},
							{i, j+2},{i-1, j+1},{i-2, j},{i-1, j-1},{i, j-2},{i+1, j-1},{i+2 , j},{i+1, j+1},
							{i, j+3},{i-1, j+2},{i-2, j+1},	{i-3, j},{i-2, j+1},{i-1, j-2},{i, j-3},{i+1, j-2},{i+2, j-1},{i+3, j},{i+2, j+1},{i+1, j+2}};
					for (int idx = 0; idx < 4; idx++){
						if(coords[idx][0] >= 0 && (coords[idx][1]) >= 0 && (coords[idx][1]) < 16 && (coords[idx][0] < 16) ){blockList.get(coords[idx][0]).get(coords[idx][1]).temprature = 3;}
					}
					for (int idx = 4; idx < 12; idx++){
						
						if(coords[idx][0] >= 0 && (coords[idx][1]) >= 0 && (coords[idx][1]) < 16 && (coords[idx][0] < 16) ){blockList.get(coords[idx][0]).get(coords[idx][1]).temprature = 2;}
					}
					for (int idx = 12; idx < 24; idx++){
						if(coords[idx][0] >= 0 && (coords[idx][1]) >= 0 && (coords[idx][1]) < 16 && (coords[idx][0] < 16) ){blockList.get(coords[idx][0]).get(coords[idx][1]).temprature = 1;}
					}
				}
				else if(blockList.get(i).get(j).blockType == 5){
					blockList.get(i).get(j).magneticField = 4;
					int[][] coords = {{i, j+1},{i-1, j},{i, j-1}, {i+1, j},
							{i, j+2},{i-1, j+1},{i-2, j},{i-1, j-1},{i, j-2},{i+1, j-1},{i+2 , j},{i+1, j+1},
							{i, j+3},{i-1, j+2},{i-2, j+1},	{i-3, j},{i-2, j+1},{i-1, j-2},{i, j-3},{i+1, j-2},{i+2, j-1},{i+3, j},{i+2, j+1},{i+1, j+2}};
					for (int idx = 0; idx < 4; idx++){
						if(coords[idx][0] >= 0 && (coords[idx][1]) >= 0 && (coords[idx][1]) < 16 && (coords[idx][0] < 16) ){blockList.get(coords[idx][0]).get(coords[idx][1]).magneticField = 3;}
					}
					for (int idx = 4; idx < 12; idx++){
						if(coords[idx][0] >= 0 && (coords[idx][1]) >= 0 && (coords[idx][1]) < 16 && (coords[idx][0] < 16) ){blockList.get(coords[idx][0]).get(coords[idx][1]).magneticField = 2;}
					}
					for (int idx = 12; idx < 24; idx++){
						if(coords[idx][0] >= 0 && (coords[idx][1]) >= 0 && (coords[idx][1]) < 16 && (coords[idx][0] < 16) ){blockList.get(coords[idx][0]).get(coords[idx][1]).magneticField = 1;}
					}
				}
			}
		}
		BotVF.initBot(8,8, blockList);
		MessageWatcher.update(1);
	}
	@Override
	public void dispose() {
		for(ArrayList<Block> a : blockList){
			for(Block b : a){
				b.tex.dispose();
			}
		}
		for(Block b : blocks){
			b.getTexture().dispose();
		}
		BotVF.tex.dispose();
	}

	@Override
	public void pause() {
	}

	@Override
	public void render() {		
		if(BotVF.isAlive){
		Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		batch.begin();
		for(int i = 0; i < 16; i++){
			for(int j = 0; j < 16; j++){
				if(stage[i][j] > 3 && stage[i][j] < 7){
					blocks.get(0).render(batch, i*32, j*32);
				}
				//blocks.get(stage[i][j]).render(batch, i*32, j*32);
				blockList.get(i).get(j).render(batch, i*32, j*32);
			}
		}
		BotVF.render(batch);
		batch.end();
		}
		else
		{
			Gdx.gl.glClearColor(0f, 0f, 0f, 0f);
			Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
			
			batch.begin();
			for(int i = 0; i < 16; i++){
				for(int j = 0; j < 16; j++){
					if(stageDead[i][j] > 3 && stageDead[i][j] < 7){
						blocks.get(0).render(batch, i*32, j*32);
					}
					blocks.get(stageDead[i][j]).render(batch, i*32, j*32);
				}
			}
			batch.end();
		}
	}

	@Override
	public void resize(int k, int arg1) {
	}

	@Override
	public void resume() {
	}
	@Override
	public boolean keyDown(int k) {
		
		return false;
	}
	@Override
	public boolean keyTyped(char k) {
		
		return false;
	}
	@Override
	public boolean keyUp(int k) {
		
		if(Bot.corelMode){
			cyc(k);
		}else{
			fakeCyc(k);
		}
		
		return true;
	}
	public static void fakeCyc(int k) {
		
		if(BotVF.isAlive){	switch(k){
	 	case (Keys.UP):
			BotVF.moveForward(blockList, true);
			break;
		case (Keys.W):
			BotVF.moveForward(blockList, true);
			break;
		case (Keys.DOWN):
			BotVF.moveBackward(blockList, true);
			break;
		case (Keys.S):
			BotVF.moveBackward(blockList, true);
			break;
		case (Keys.LEFT):
			BotVF.turnLeft();
			break;
		case (Keys.A):
			BotVF.turnLeft();
			break;
		case (Keys.RIGHT):
			BotVF.turnRight();
			break;
		case (Keys.D):
			BotVF.turnRight();
			break;
		case (Keys.Q):
			Bot.running = false;
			break;
		case (Keys.F4):
			Bot.running = false;
			break;
		case (Keys.F5):
			Bot.running = false;
			break;
		
		default:
			break;	
		}
		BotVF.condCheck(blockList);
		}
		
	}
	public static void cyc(int k){

		MessageWatcher.update(1);
		
		BotVF.condCheck(blockList);
		BotVF.sendInfo(blockList);
		try{
			Thread.sleep(100);
		}catch(Exception e){
			log("ERROR IN SLEEPING!");
		}
		
		Message m = null;
		m = MessageWatcher.getNext(1);
		
		String msg = "go_ahead";
		
		if(m != null && m.message != null && !m.message.isEmpty()){
//			LogUtils.log("in if");
		msg = m.message.get(0);
		}
		if(msg.equals("go_ahead")){
			BotVF.go_ahead = true;
		if(BotVF.isAlive){	switch(k){
	 	case (Keys.UP):
			BotVF.moveForward(blockList, true);
			break;
		case (Keys.W):
			BotVF.moveForward(blockList, true);
			break;
		case (Keys.DOWN):
			BotVF.moveBackward(blockList, true);
			break;
		case (Keys.S):
			BotVF.moveBackward(blockList, true);
			break;
		case (Keys.LEFT):
			BotVF.turnLeft();
			break;
		case (Keys.A):
			BotVF.turnLeft();
			break;
		case (Keys.RIGHT):
			BotVF.turnRight();
			break;
		case (Keys.D):
			BotVF.turnRight();
			break;
		case (Keys.Q):
			Bot.running = false;
			break;
		case (Keys.F4):
			Bot.running = false;
			break;
		case (Keys.F5):
			Bot.running = false;
			break;
		
		default:
			break;	
		}
		return;
	}
		}
		else if(msg.equals("go_back")){
			BotVF.go_ahead = false;
			if(BotVF.lastMove){
				BotVF.moveBackward(blockList, false);BotVF.moveBackward(blockList, false);
			}else{
				BotVF.moveForward(blockList, false);BotVF.moveForward(blockList, false);
			}
			return;}
	}
	@Override
	public boolean mouseMoved(int k, int arg1) {
		return false;
	}
	@Override
	public boolean scrolled(int k) {
		return false;
	}
	@Override
	public boolean touchDown(int k, int arg1, int arg2, int arg3) {
		return false;
	}
	@Override
	public boolean touchDragged(int k, int arg1, int arg2) {
		return false;
	}
	@Override
	public boolean touchUp(int k, int arg1, int arg2, int arg3) {
		return false;
	}
	public static void log(Object o){
		LogUtils.log(o);
	}
}
