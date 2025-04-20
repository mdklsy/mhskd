package model.fight.fsmstates;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

import model.Monster;
/**
 * 
 * @author L
 *	怪物一级生存状态   等级越高越凶残
 */
public class Live1 implements FSMState{
	private static Logger log = Logger.getLogger(Live1.class); 
	private Monster monster;
	private Timer timer;
	
	public Live1(Monster monster) {
		this.monster = monster;
	}
	
	@Override
	public void enter() {
		log.info("enter Live1");
		// TODO Auto-generated method stub
	}
	@Override
	public void exit() {
		log.info("exit Live1");
		timer.cancel();
	}
	@Override
	public void update() {
		timer.scheduleAtFixedRate(new Montask(monster),100,20);
	}
	@Override
	public void updataCloneObj(Monster mon) {
		this.monster = mon;
		timer = new Timer();
	}
	public void setTimer(Timer timer) {
		this.timer = timer;
	}
	
}
class Montask extends TimerTask {
	private Monster mon;
	
	public Montask(Monster mon) {
		this.mon = mon;
	}
	public void run() {
		mon.move();
		if(mon.isNearPerson(72)){
			mon.useSkill(0);
		}
		if(mon.getHp()<1){
			mon.getFsmmachine().updataStates(1);
		}
	}
} 
