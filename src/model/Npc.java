package model;

import java.awt.Image;
import java.util.ArrayList;

/**
 * 
 * @author LSY
 * @date Dec 7, 2016 NPC对象
 */
public class Npc implements Npcs{
	private String name;
	private int npc_i;// npc在地图中的位置 i为行j为列
	private int npc_j;
	private Image npcImage;
	private ArrayList<String> bemissionTalk;
	private ArrayList<String> afmissionTalk;
	private MissionMoveTo mission;

	public Npc(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Image getNpcImage() {
		return npcImage;
	}

	public void setNpcImage(Image npcImage) {
		this.npcImage = npcImage;
	}

	public int getNpc_i() {
		return npc_i;
	}

	public void setNpc_i(int npc_i) {
		this.npc_i = npc_i;
	}

	public int getNpc_j() {
		return npc_j;
	}

	public void setNpc_j(int npc_j) {
		this.npc_j = npc_j;
	}

	public MissionMoveTo getMission() {
		return mission;
	}

	public void setMission(MissionMoveTo mission) {
		this.mission = mission;
	}

	public ArrayList<String> getBemissionTalk() {
		return bemissionTalk;
	}

	public void setBemissionTalk(ArrayList<String> bemissionTalk) {
		this.bemissionTalk = bemissionTalk;
	}

	public ArrayList<String> getAfmissionTalk() {
		return afmissionTalk;
	}

	public void setAfmissionTalk(ArrayList<String> afmissionTalk) {
		this.afmissionTalk = afmissionTalk;
	}

	@Override
	public int getI() {
		// TODO Auto-generated method stub
		return getNpc_i();
	}

	@Override
	public int getJ() {
		// TODO Auto-generated method stub
		return getNpc_j();
	}

	@Override
	public Image getImage() {
		// TODO Auto-generated method stub
		return getNpcImage();
	}

}
