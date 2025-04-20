package model.fight.fsmstates;

import model.Monster;

/**
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:怪物停止状态
 */
public class Stop implements FSMState{

	@Override
	public void enter() {
		System.out.println("enter stop");
		
	}

	@Override
	public void exit() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update() {
			System.out.println("stop");
	}


	@Override
	public void updataCloneObj(Monster mon) {
		// TODO Auto-generated method stub
		
	}

}
