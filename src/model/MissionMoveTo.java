package model;

import java.awt.Point;
import java.util.ArrayList;
/**
 * 
 * @author lsy
 * @date 2016年12月22日
 * 注释:游戏中任务的模型类，可以作为npc的属性 
 * MOVETO类型任务（类似于这样的>>>  去村头拔个草，去村尾砍棵树，去给小爷沏杯茶！！！）
 */
public class MissionMoveTo {
	//任务ID
	private int id;
	//任务的启动坐标和完成坐标，启动坐标为null时从角色坐标开始
	private Point startPosition;
	private Point endPosition;
	//任务是否达到启动和完成条件
	private boolean isStart;
	private boolean isEnd;
	//任务前，任务进行中，任务完成后的对话列表（可以为null）
	private ArrayList<String> beforeTalk;
	private ArrayList<String> inTalk;
	private ArrayList<String> afterTalk;
	//任务的监控线程，监控任务状态及做出相应操作
	private Thread missionControl;
	
	public MissionMoveTo(){
		
	}
	
	
	
	public MissionMoveTo(int id,Point startPosition, Point endPosition,  ArrayList<String> beforeTalk,  ArrayList<String> inTalk,  ArrayList<String> afterTalk,
			Thread missionControl) {
		super();
		this.id = id;
		this.startPosition = startPosition;
		this.endPosition = endPosition;
		this.beforeTalk = beforeTalk;
		this.inTalk = inTalk;
		this.afterTalk = afterTalk;
		this.missionControl = missionControl;
		this.isStart = false;
		this.isEnd = false;
	}
	


	public Point getStartPosition() {
		return startPosition;
	}
	public void setStartPosition(Point startPosition) {
		this.startPosition = startPosition;
	}
	public Point getEndPosition() {
		return endPosition;
	}
	public void setEndPosition(Point endPosition) {
		this.endPosition = endPosition;
	}
	public boolean isStart() {
		return isStart;
	}
	public void setStart(boolean isStart) {
		this.isStart = isStart;
	}
	public boolean isEnd() {
		return isEnd;
	}
	public void setEnd(boolean isEnd) {
		this.isEnd = isEnd;
	}
	public Thread getMissionControl() {
		return missionControl;
	}
	public void setMissionControl(Thread missionControl) {
		this.missionControl = missionControl;
	}

	public ArrayList<String> getBeforeTalk() {
		return beforeTalk;
	}
	public void setBeforeTalk(ArrayList<String> beforeTalk) {
		this.beforeTalk = beforeTalk;
	}

	public ArrayList<String> getInTalk() {
		return inTalk;
	}

	public void setInTalk(ArrayList<String> inTalk) {
		this.inTalk = inTalk;
	}



	public ArrayList<String> getAfterTalk() {
		return afterTalk;
	}



	public void setAfterTalk(ArrayList<String> afterTalk) {
		this.afterTalk = afterTalk;
	}



	public int getId() {
		return id;
	}



	public void setId(int id) {
		this.id = id;
	}
	
	
}
