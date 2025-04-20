package model.fight.fsmstates;

import java.util.Timer;

import model.Monster;
import model.Person;
/**
 * 
 * @author L
 *怪物死亡状态
 */
public class Die implements FSMState{
	private Monster monster;
	private Timer timer;
	
	public Die(Monster monster) {
		this.monster = monster;
	}
	
	@Override
	public void enter() {
	}

	@Override
	public void exit() {
	}

	@Override
	public void update() {
		if(monster.getHp()<1){
			monster.setLive(false);
		}
		
	}

	@Override
	public void updataCloneObj(Monster mon) {
		this.monster = mon;
		timer  = new Timer();
		
	}


}
