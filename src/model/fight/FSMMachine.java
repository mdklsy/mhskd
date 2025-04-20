package model.fight;

import java.util.ArrayList;

import model.fight.fsmstates.FSMState;
/**
 * 
 * @author lsy
 * @date 2017年1月6日
 * 注释:状态机
 */
public class FSMMachine {
	private ArrayList<FSMState> states;  //状态机所拥有的状态
	private FSMState currentState;       //当前状态
	private FSMState defaultState;		 //默认状态
	
	public FSMMachine(ArrayList<FSMState> states,int defaultStateIndex) {
		this.states = states;
		currentState = states.get(defaultStateIndex);
		defaultState = states.get(defaultStateIndex);
	}
	
	public void execute(){   //状态机运行
		currentState.enter();
		currentState.update();
	}
	
	public void updataStates(int index){   //切换到指定状态
		if(index<states.size()){
			currentState.exit();
			currentState = states.get(index);
			execute();
		}
	}
	
	public void resetState(){   //恢复默认设置
		currentState.exit();
		currentState = defaultState;
		execute();
	}

	public ArrayList<FSMState> getStates() {
		return states;
	}

	public void setStates(ArrayList<FSMState> states) {
		this.states = states;
	}
	
	public int getDefaultIndex(){
		return states.indexOf(defaultState);
	}
	
}
