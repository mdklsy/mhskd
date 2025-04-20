package game;

import java.awt.Graphics;
import java.awt.Image;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import tool.*;
import model.*;
import model.fight.skill.PersonSkill;
/**
 * 
 * @author LSY
 * @date Dec 7, 2016
 * 游戏地图面板
 */
public class GameMap extends JPanel{
	private Map map;
	private Person person;
	private Properties pro;
	public GameMap (JLayeredPane jlp){
		pro = new Properties();
		try {
			pro.load(new FileInputStream("config/message/a.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String name = pro.getProperty("name", "");
		person = new Person(name);
		person.getPerSkills().add(new PersonSkill(1, person, "skill/personskill1.png", "普攻",2,2000,0,50,50));
		map = Utils.getMap("新手村",person);
		setLayout(null);
		jlp.add(this, JLayeredPane.DEFAULT_LAYER);
		setSize(800, 600);
	
	}
	
	

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Image image = null;
		Image imageNpc = null;
		int pylx = (((person.getPerson_init_x()+25)%50)==0)?(50):(person.getPerson_init_x()+25)%50;
		int pyly = (((person.getPerson_init_y()+25)%50)==0)?(50):(person.getPerson_init_y()+25)%50;
		for (int i = person.getI()-8; i <= person.getI()+8; i++) {
			for (int j = person.getJ()-6; j <= person.getJ()+6; j++) {
				if (i<0||i>39||j<0||j>19) {
					image = new ImageIcon("config/images/map/石头.png").getImage();
					g.drawImage(image, (person.getPersonX()+(i-person.getI())*50-pylx), person.getPersonY()+(j-person.getJ())*50-pyly, null);
				}else {
					image = Utils.getMapImage(map,j,i);
					imageNpc = Utils.getNpcImage(map, j, i);
					g.drawImage(image, (person.getPersonX()+(i-person.getI())*50-pylx), person.getPersonY()+(j-person.getJ())*50-pyly, null);
					g.drawImage(imageNpc, (person.getPersonX()+(i-person.getI())*50-pylx), person.getPersonY()+(j-person.getJ())*50-pyly, null);
				}
			}
			
		}
		g.drawImage(person.getPersonImage(),375,275,null);
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}



	public Map getMap() {
		return map;
	}



	public void setMap(Map map) {
		this.map = map;
	}


	
	
	
}
