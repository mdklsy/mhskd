package game;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import model.Bao;
import model.Items;
import model.Person;
/***
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:人物属性面板
 */
public class PersonEqpt extends JPanel{
	private HashMap<String,Items> eqpt;
	private Image ima;
	private ArrayList<Point> zb;
	private HashMap<Integer,String> eqpts;
	private Bao bao;
	private Person person;
	private JTextArea text; 
	
	public PersonEqpt(HashMap<String,Items> eqpt,JLayeredPane jlp,Person person) {
		zbInit();
		this.person = person;
		this.eqpt = eqpt;
		text = new JTextArea();
		ima = new ImageIcon("config/images/show/人物属性.png").getImage();
		jlp.add(this, JLayeredPane.PALETTE_LAYER);
		jlp.add(text, JLayeredPane.PALETTE_LAYER);
		add(text);
		setLayout(null);
		text.setBounds(165,27,60,115);
		text.setOpaque(false);
		text.setEditable(false);
		text.setFont(new Font("宋体", Font.BOLD, 10));
		text.setForeground(Color.WHITE);
		upDataText();
		setBounds(470,320,239,166);
		setVisible(false);
		addMouseListener(new MouseListener() {
			Dimension ds = new Dimension(25, 25);

			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					Rectangle rt = null;
					for (int i = 0; i < zb.size(); i++) {
						rt = new Rectangle(zb.get(i), ds);
						if (rt.contains(e.getX(), e.getY())) {
							Items item = eqpt.get(eqpts.get(i));
							eqpt.put(eqpts.get(i), null);
							person.upDataAttr();
							upDataText();
							if(bao.getItemSum()<bao.getBaoSize()){
								bao.addItem(item, 0);
							}
						}
					}
				}
			}
		});
	}
	
	public void upDataText() {
		StringBuffer s = new StringBuffer();
		s.append("等级："+person.getLv());
		s.append("\n经验："+person.getExp());
		s.append("\n血量："+person.getHp());
		s.append("\n体力："+person.getPhy());
		s.append("\n攻击："+person.getAd());
		s.append("\n法术："+person.getAp());
		s.append("\n物防："+person.getDad());
		s.append("\n法防："+person.getDap());
		s.append("\n回避："+person.getAvd());
		System.out.println(s);
		text.setText(s.toString());
	}

	private void zbInit() {
		zb = new ArrayList<Point>();
		zb.add(new Point(17, 24));
		zb.add(new Point(17, 58));
		zb.add(new Point(17, 92));
		zb.add(new Point(17, 127));
		zb.add(new Point(123, 24));
		zb.add(new Point(123, 58));
		zb.add(new Point(123, 92));
		zb.add(new Point(123, 127));
		eqpts = new HashMap<Integer,String>();
		eqpts.put(0, "helmet");
		eqpts.put(1, "coat");
		eqpts.put(2, "pants");
		eqpts.put(3, "shoes");
		eqpts.put(4, "weapon");
		eqpts.put(5, "Addition1");
		eqpts.put(6, "Addition2");
		eqpts.put(7, "Addition3");
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ima, 0, 0, null);
		if(eqpt.get("helmet")!=null){
			g.drawImage(eqpt.get("helmet").getIma(), zb.get(0).x, zb.get(0).y, null);
		}
		if(eqpt.get("coat")!=null){
			g.drawImage(eqpt.get("coat").getIma(), zb.get(1).x, zb.get(1).y, null);
		}
		if(eqpt.get("pants")!=null){
			g.drawImage(eqpt.get("pants").getIma(), zb.get(2).x, zb.get(2).y, null);
		}
		if(eqpt.get("shoes")!=null){
			g.drawImage(eqpt.get("shoes").getIma(), zb.get(3).x, zb.get(3).y, null);
		}
		if(eqpt.get("weapon")!=null){
			g.drawImage(eqpt.get("weapon").getIma(), zb.get(4).x, zb.get(4).y, null);
		}
		if(eqpt.get("Addition1")!=null){
			g.drawImage(eqpt.get("Addition1").getIma(), zb.get(5).x, zb.get(5).y, null);
		}
		if(eqpt.get("Addition2")!=null){
			g.drawImage(eqpt.get("Addition2").getIma(), zb.get(6).x, zb.get(6).y, null);
		}
		if(eqpt.get("Addition3")!=null){
			g.drawImage(eqpt.get("Addition3").getIma(), zb.get(7).x, zb.get(7).y, null);
		}
	}

	public void setBao(Bao bao) {
		this.bao = bao;
	}
	
}
