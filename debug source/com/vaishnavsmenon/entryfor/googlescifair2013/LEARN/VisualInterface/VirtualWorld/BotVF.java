package com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.VisualInterface.VirtualWorld;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.MessageBuffer;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.communication.LogUtils;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Block;
import com.vaishnavsmenon.entryfor.googlescifair2013.LEARN.customTypes.Message;

public class BotVF {
	static int x, y;
	static Texture tex;
	static int facing = 0;
	static int health = 100;
	static boolean isAlive = true, lastMove = false, go_ahead = true;
	/*
	 * FACING :-
	 *    0
	 *  1   3
	 *    2
	 */
	
	public static void initBot(int _x,int _y, ArrayList<ArrayList<Block>> _b){
		tex = new Texture(System.getProperty("user.dir")+"/imgs/bot.png");
		x = _x;
		y = _y;
		sendInfo(_b);
	}

/*	public static void cycle(){
		log("Cycle");
		if(!MessageWatcher.hasNext(3)){log("Is Empty");return;}
		Message m = MessageWatcher.getNext(3);
		if(m.title.equals("bot_command")){
			log("bot command");
			if(m.message.get(0).equals("go_back")){
				log("bot command: go back");
				go_ahead = false;
				if(lastMove){
					moveBackward(blockList, false);moveBackward(blockList, false);
				}else{
					moveForward(blockList, false);moveForward(blockList, false);
				}
			}
			else if(m.message.get(0).equals("go_ahead")){
				log("bot command: go ahead");
				go_ahead = true;
				log("Go Ahead!");
				MessageWatcher.update(3);
				if(!MessageWatcher.hasNext(3)){return;}
				m = MessageWatcher.getNext(3);
			}
		}
		if(go_ahead){
			if(m.title.equals("bot_move_command")){
				log("bot move command");
				int key = Integer.parseInt(m.message.get(0));
				log("key: "+key);
				if(key == Keys.UP || key == Keys.W){ moveForward(blockList, true); }
				else if(key == Keys.DOWN || key == Keys.S){ moveBackward(blockList, true); }
				else if(key == Keys.LEFT || key == Keys.A){ turnLeft(); }
				else if(key == Keys.RIGHT || key == Keys.D){ turnRight(); }
				
			}
		
		}
		BotVF.condCheck(blockList);
		BotVF.sendInfo(blockList);
		Thread.yield();
	}*/
	
	public static void moveForward(ArrayList<ArrayList<Block>> blockList, boolean lm){
		
		if(facing == 0 && (y+1)<16 ){
			y += 1;
			if(lm){lastMove = true;}
		} else if(facing == 1 && (x-1) >= 0){
			x -= 1;
			if(lm){lastMove = true;}
		} else if(facing == 2 && (y-1) >= 0){
			y -= 1;
			if(lm){lastMove = true;}
		} else if(facing == 3 && (x+1) < 16){
			x += 1;
			if(lm){lastMove = true;}
		}
	}
	public static void sendInfo(ArrayList<ArrayList<Block>> blockList) {
		Block b = blockList.get(x).get(y);
		if(b != null){
		Message temp = new Message("bot_status_update",1, 0);
		temp.addMessage("h:"+Integer.toString(health));
		temp.addMessage("p:"+Integer.toString(b.magneticField));
		temp.addMessage("p:"+Integer.toString(b.temprature));
		for(String s : b.properties){
			temp.addMessage("p:"+s);
		}
		MessageBuffer.addToBuffer(temp);
		}
		Thread.yield();
	}

	public static void moveBackward(ArrayList<ArrayList<Block>> blockList, boolean lm){
		if(facing == 0 && (y-1) >= 0){
			y -= 1;
			if(lm){lastMove = false;}
		} else if(facing == 1 && (x+1)<16){
			x += 1;
			if(lm){lastMove = false;}
		} else if(facing == 2 && (y+1)<16){
			y += 1;
			if(lm){lastMove = false;}
		} else if(facing == 3 && (x-1) >= 0){
			x -= 1;
			if(lm){lastMove = false;}
		}
	}
	public static void turnLeft(){
		facing = (facing+1) % 4;
	}
	public static void turnRight(){
		facing = (facing+3) % 4;
	}
	
	public static void render(SpriteBatch batch){
		batch.draw(tex, x*32, y*32, 15.5f, 15.5f, tex.getWidth(), tex.getHeight(), 1, 1, facing*90, 0, 0, tex.getWidth(), tex.getHeight(), false, false);			
	}
	
	public static void condCheck(ArrayList<ArrayList<Block>> blockList){
		
	Block b = blockList.get(x).get(y);
	if(b.temprature > 0){
		switch(b.temprature){
			case 1:
				health -= 3;
				break;
			case 2:
				health -= 9;
				break;
			case 3:
				health -= 21;
				break;
			case 4:
				health -= 35;
				break;
			default:
				health -= 100;
				break;
		}
	}
	else if(b.magneticField > 0){
		switch(b.magneticField){
				case 1:
					health -= 2;
					break;
				case 2:
					health -= 4;
					break;
				case 3:
					health -= 8;
					break;
				case 4:
					health -= 32;
					break;
				default:
					health -= 100;
					break;
		}
	}
	else if(b.blockType == 6){
		health -= 33;
	}
	healthMod();
	}
	
	public static void healthMod(){
		if(health > 0){
			health += 5;
			} else{
				health = 0;
				isAlive = false;
			}
	}
	
	public static void log(Object o){
		LogUtils.log(o);
	}
}
