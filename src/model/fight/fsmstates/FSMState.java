package model.fight.fsmstates;

import model.Monster;

/**
 * 
 * @author lsy
 * @date 2017年1月6日
 * 注释:状态机状态接口
 */
public interface FSMState {
	void enter();//进入状态的动作
	void exit(); //离开状态的动作
	void update(); //状态执行时的方法
	void updataCloneObj(Monster mon); //更换状态里的mon及timer对象，克隆mon对象时要注意调用此方法
}
