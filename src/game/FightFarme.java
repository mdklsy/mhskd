package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import model.Monster;
import model.Person;
import model.fight.AttackREG;
import model.fight.DefenceREG;
import model.fight.skill.PersonSkill;
import model.fight.skill.Skills;
import tool.MusicPlayer;
import tool.Utils;
/**
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:战斗窗口
 */
public class FightFarme extends JFrame{
	private static Logger log = Logger.getLogger(FightFarme.class); 
	
	private KaiShi ks;
	private Person person;     //人物战斗替身
	private Person zhenperson; //真身
	private Monster mon;      //怪物战斗替身
	private Monster zhenmon;	//真身
	private Image ima;
	private Image moupos;  //显示鼠标点击位置的图片
	private int tx;      //鼠标点击的x坐标
	private int ty;		//鼠标点击的Y坐标  （左下角为原点）
	private int sx;     //鼠标指向的x坐标
	private int sy;		//鼠标指向的Y坐标
	private Vector<PersonSkill> QWEskill; 
	private ShowPk showpk;
	private JPanel winpanel;
	private JPanel losepanel;
	private QWEkeyListener qwekeyListener;
	private PersonPKMove personPKMove;
	private UpdaSXY updaSXY;
	private JLayeredPane jlp;

	
	public FightFarme(KaiShi ks,Person person,Person zhenperson,Monster mon,Monster zhenmon) {
		
		jlp = new JLayeredPane();
		this.ks = ks;
		this.person = person;
		this.zhenperson = zhenperson;
		this.mon = mon;
		this.zhenmon = zhenmon;
		QWEskill = new Vector<PersonSkill>();
		QWEskill.add(null);
		QWEskill.add(null);
		QWEskill.add(null);
		QWEskill.add(null);
		tx = 200;
		ty = 300;
		JPanel wel = new FightWelcome(this);
		person.setPk_x(200);
		person.setPk_y(300);
		person.setTarget_x(200);
		person.setTarget_x(300);
		mon.setPk_x(600);
		mon.setPk_y(300);
		ima = Utils.getImage("/fight/fightmap.jpg");
		add(wel);
		setLayout(null);
		setSize(800, 630);
		setVisible(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(Utils.getImage("show/鼠标指针.png"), new Point(4, 4), "手"));
		
	}
	
	
	
	public KaiShi getKs() {
		return ks;
	}

	public Person getPerson() {
		return person;
	}

	public Monster getMon() {
		return mon;
	}

	public Vector<PersonSkill> getQWEskill() {
		return QWEskill;
	}

	public void gogogo() {
		setLayeredPane(jlp);
		showpk = new ShowPk(this);
		showpk.setLocation(0, 0);;
		mon.start();
		this.setQwekeyListener(new QWEkeyListener(this));
		this.addKeyListener(this.getQwekeyListener());
		this.setPersonPKMove(new PersonPKMove(this));
		showpk.addMouseListener(this.getPersonPKMove());
		this.setUpdaSXY(new UpdaSXY(this));
		showpk.addMouseMotionListener(this.getUpdaSXY());
		Thread updataperonpk = new UpdataPersonPK(this);
		Thread updatapkmap = new UpdataPkMap(this);
		this.requestFocus();
		updataperonpk.start();
		updatapkmap.start();
	}

	public int getTx() {
		return tx;
	}

	public int getTy() {
		return ty;
	}

	public void setTx(int tx) {
		this.tx = tx;
	}

	public void setTy(int ty) {
		this.ty = ty;
	}



	public Image getIma() {
		return ima;
	}



	public ShowPk getShowpk() {
		return showpk;
	}



	public Image getMoupos() {
		return moupos;
	}



	public void setMoupos(Image moupos) {
		this.moupos = moupos;
	}



	public void setSx(int sx) {
		this.sx = sx;
	}



	public void setSy(int sy) {
		this.sy = sy;
	}



	public int getSx() {
		return sx;
	}



	public int getSy() {
		return sy;
	}



	public JPanel getWinpanel() {
		return winpanel;
	}



	public JPanel getLosepanel() {
		return losepanel;
	}



	public void setWinpanel(JPanel winpanel) {
		this.winpanel = winpanel;
	}



	public void setLosepanel(JPanel losepanel) {
		this.losepanel = losepanel;
	}



	public QWEkeyListener getQwekeyListener() {
		return qwekeyListener;
	}



	public void setQwekeyListener(QWEkeyListener qwekeyListener) {
		this.qwekeyListener = qwekeyListener;
	}



	public PersonPKMove getPersonPKMove() {
		return personPKMove;
	}



	public void setPersonPKMove(PersonPKMove personPKMove) {
		this.personPKMove = personPKMove;
	}



	public UpdaSXY getUpdaSXY() {
		return updaSXY;
	}



	public void setUpdaSXY(UpdaSXY updaSXY) {
		this.updaSXY = updaSXY;
	}



	public JLayeredPane getJlp() {
		return jlp;
	}



	public Monster getZhenmon() {
		return zhenmon;
	}



	public Person getZhenperson() {
		return zhenperson;
	}
	
	 
	
	
}

class FightWelcome extends JPanel{
	private FightFarme ff;
	private Vector<PersonSkill> skills;
	private Vector<PersonSkill> QWEskills;
	private JPanel skillPanel;
	private JLabel jl1;
	private JLabel jl2;
	private JLabel jl3;
	public FightWelcome(FightFarme ff) {
		this.ff = ff;
		skills = new Vector<PersonSkill>();
		skills.addAll(ff.getPerson().getPerSkills());
		QWEskills = ff.getQWEskill();
		setLayout(null);
		skillPanel = new JPanel();
		skillPanel.setBounds(0, 0, 700, 300);
		JButton jbt = new JButton("进入战斗");
		jbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean isCangogogo  = false;
				for (PersonSkill ps : QWEskills) {
					if(ps!=null){
						isCangogogo = true;
						break;
					}
				}
				if(isCangogogo){
					FightWelcome.this.setVisible(false);
					ff.gogogo();
				}else{
					JOptionPane.showMessageDialog(FightWelcome.this,"请至少选一个技能，不然你拿头打？");
				}
			}
		});
		jbt.setBounds(700, 0, 100, 300);
		 jl1  = new JLabel();
		jl1.setBounds(0, 300, 200, 200);
		 jl2  = new JLabel();
		jl2.setBounds(200, 300, 200, 200);
		 jl3  = new JLabel();
		jl3.setBounds(400, 300, 200, 200);
		jl1.setFont(new Font("宋体", Font.BOLD, 40));
		jl2.setFont(new Font("宋体", Font.BOLD, 40));
		jl3.setFont(new Font("宋体", Font.BOLD, 40));
		JLabel jll1  = new JLabel("Q");
		jll1.setFont(new Font("宋体", Font.BOLD, 20));
		jll1.setBounds(0, 500, 100, 100);
		JLabel jll2  = new JLabel("W");
		jll2.setFont(new Font("宋体", Font.BOLD, 20));
		jll2.setBounds(200, 500, 100, 100);
		JLabel jll3  = new JLabel("E");
		jll3.setFont(new Font("宋体", Font.BOLD, 20));
		jll3.setBounds(400, 500, 100, 100);
		JButton bt1 = new JButton("移除");
		bt1.setBounds(100, 500, 100, 100);
		JButton bt2 = new JButton("移除");
		bt2.setBounds(300, 500, 100, 100);
		JButton bt3 = new JButton("移除");
		bt3.setBounds(500, 500, 100, 100);
		bt1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jl1.getText().length()!=0){
					QWEskills.set(0, null);
					jl1.setText("");
				}
			}
		});
		bt2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jl2.getText().length()!=0){
					QWEskills.set(1, null);
					jl2.setText("");
				}
			}
		});
		bt3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(jl3.getText().length()!=0){
					QWEskills.set(2, null);
					jl3.setText("");
				}
			}
		});
		for (int i = 0; i < skills.size(); i++) {
			JLabel jl = new JLabel(skills.get(i).getName());
			jl.setSize(100,100);
			JButton jb = new JButton("添加");
			jb.setSize(100,100);
			jb.setName(i+"");
			jb.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					FightWelcome.this.addSkill(Integer.valueOf(jb.getName()));
				}
			});
			skillPanel.add(jl);
			skillPanel.add(jb);
		}
		add(jbt);
		add(skillPanel);
		add(jl1);
		add(jl2);
		add(jl3);
		add(jll1);
		add(jll2);
		add(jll3);
		add(bt1);
		add(bt2);
		add(bt3);
		setBounds(0, 0, 800, 600);
		setVisible(true);
		
	}
	public void addSkill(int index){
		PersonSkill s = skills.get(index);
		if(QWEskills.indexOf(s)<0){
			if(QWEskills.get(0)==null){
				QWEskills.set(0, s);
				jl1.setText(s.getName());
			}else if(QWEskills.get(1)==null){
				QWEskills.set(1, s);
				jl2.setText(s.getName());
			}else if(QWEskills.get(2)==null){
				QWEskills.set(2, s);
				jl3.setText(s.getName());
			}else{
				QWEskills.set(0, s);
				jl1.setText(s.getName());
			}
		}
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(Utils.getImage("欢迎.jpg"), 0, 0, null);
	}
}


//人物移动线程
class UpdataPersonPK extends Thread {
	private FightFarme ff;
	public UpdataPersonPK(FightFarme ff) {
		this.ff = ff;
	}
	public void run() {
		while (ff.getMon().isLive()&&ff.getPerson().isLive()) {
			ff.getPerson().updataFX(new Point(ff.getTx(),ff.getTy()));
			ff.getPerson().pkMove(ff.getMon());
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

//刷新画面线程
class UpdataPkMap extends Thread {
	private FightFarme ff;
	private static Logger log = Logger.getLogger(UpdataPkMap.class); 
	public UpdataPkMap(FightFarme ff) {
		this.ff = ff;
	}
	@Override
	public void run() {
		while (ff.getMon().isLive()&&ff.getPerson().isLive()) {
			ff.getShowpk().updataJls();
			ff.getShowpk().repaint();
			try {
				sleep(20);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				log.error(e.getMessage());
			}
			
		}
		ff.getShowpk().updataJls();
		ff.getShowpk().repaint();
		if(ff.getPerson().isLive()){
			log.info("战斗胜利");
			ff.setWinpanel(new WinPanel(ff));
			ff.getWinpanel().setLocation(0, 0);
		}else{
			log.info("战斗失败");
			ff.setLosepanel(new LosePanel(ff));
			ff.getLosepanel().setLocation(0, 0);
		}
		
		ff.removeKeyListener(ff.getQwekeyListener());
		ff.getShowpk().removeMouseListener(ff.getPersonPKMove());
		ff.getShowpk().removeMouseMotionListener(ff.getUpdaSXY());
	}
}

class QWEkeyListener implements KeyListener{
	private static Logger log = Logger.getLogger(QWEkeyListener.class);
	private FightFarme ff;
	
	public QWEkeyListener(FightFarme ff) {
		this.ff = ff;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case 81:
			QWEpk(0);
			break;
		case 87:
			QWEpk(1);
			break;
		case 69:
			QWEpk(2);
			break;

		default:
			break;
		}
		
	}

	private void QWEpk(int index) {
		ff.getPerson().updataSkillFX(new Point(ff.getSx(), ff.getSy()));
		log.info("按下了q键");
		MusicPlayer.getPlayer().playOther("attack/p_att1.au");
		if(ff.getQWEskill().get(index)!=null&&ff.getQWEskill().get(index).isCanUse()){
			PersonSkill s = ff.getQWEskill().get(index);
			log.info("ppppp*********猪脚释放了" + s.getName() + "技能");
			AttackREG att = s.execute();
			DefenceREG de = ff.getMon().getDefenceREG();
			boolean b = att.getRec().isIntersect(de.getRec()); // 技能是否命中
			if (b) {
				log.info("ppppp*********猪脚释放的" + s.getName() + "技能成功命中敌人");
				int avd = de.getAvd();
				double ran = Math.random() * 100;
				if (ran > avd) { // 没有被回避
					int num = 0;
					if (att.getAttackType() == 0) { // 物理伤害
						num = (int) (att.getAttackPower() - de.getDad() + 0.5);
					} else { // 魔法伤害
						num = (int) (att.getAttackPower() - de.getDap() + 0.5);
					}
					num = num>0?num:0;
					log.info("ppppp*********" + s.getName() + "的伤害类型是" + att.getAttackType() + "技能伤害"
							+ att.getAttackPower() + "被防御后的实际伤害值" + num);
					log.info("ppppp*********敌人原本血量" + ff.getMon().getHp());
					ff.getMon().setHp(ff.getMon().getHp() - num);
					log.info("ppppp*********敌人被攻击后血量" + ff.getMon().getHp());
				}
			} else {
				log.info("ppppp*********猪脚释放的" + s.getName() + "技能没有命中敌人");
			}
			log.info("ppppp*********猪脚技能释放完毕");
		}
	}

	public void keyReleased(KeyEvent e) {
	}
	public void keyTyped(KeyEvent e) {
		
	}
	
}


class PersonPKMove extends MouseAdapter{
	private static Logger log = Logger.getLogger(PersonPKMove.class); 
	private FightFarme ff;
	private Timer timer;
	
	public PersonPKMove(FightFarme ff) {
		this.ff = ff;
		timer = new Timer();
	}
	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == MouseEvent.BUTTON3) {
            log.info("疯狂操作");
			ff.setTx(e.getX());
			ff.setTy(600-e.getY());
			ff.setMoupos(Utils.getImage("show/鼠标位置.png"));
			timer.schedule(new TimerTask() {
				@Override
				public void run() {
					ff.setMoupos(null);
				}
			}, 1000);
		}
	}
	
	
}

class ShowPk extends JPanel{
	private FightFarme ff;
	private JLayeredPane jlp;
	private JLabel skillcdq;
	private JLabel skillcdw;
	private JLabel skillcde;
	private JLabel skillcdr;
	private Person person;
	private Monster mon;
	private Vector<PersonSkill> skills;
	private JLabel [] jls;
	private Point [] points;
	private Image noskill;
	private Image skillkuang;
	private Image hpt;
	private JLabel hph;
	private JLabel hph2;
	private JLabel hphtext;
	private JLabel hphtext2;
	
	public ShowPk(FightFarme ff) {
		skills = ff.getQWEskill();
		person = ff.getPerson();
		mon = ff.getMon();
		noskill = Utils.getImage("skill/无技能.png");
		hpt = Utils.getImage("fight/血条2.png");
		skillkuang = Utils.getImage("skill/技能框.png");
		jls = new JLabel [] {skillcdq,skillcdw,skillcde,skillcdr};
		points = new Point[] {new Point(10,553),new Point(55,553),new Point(99,553),new Point(143,553)};
		this.ff = ff;
		this.jlp = ff.getJlp();
		hph = new JLabel();
		hph2 = new JLabel();
		hphtext = new JLabel("人:"+person.getHp()+"/"+person.getZhp());
		hphtext2 = new JLabel("怪:"+mon.getHp()+"/"+mon.getZhp());
		hph.setBackground(Color.red);
		hph2.setBackground(Color.red);
		jlp.add(hph,JLayeredPane.DEFAULT_LAYER);
		jlp.add(hph2,JLayeredPane.DEFAULT_LAYER);
		jlp.add(hphtext,JLayeredPane.DEFAULT_LAYER);
		jlp.add(hphtext2,JLayeredPane.DEFAULT_LAYER);
		hph.setBounds(8, 6, 321, 10);
		hph2.setBounds(463, 6, 321, 10);
		hphtext.setBounds(279, 22, 50, 20);
		hphtext2.setBounds(734, 22, 50, 20);
		hph.setOpaque(true);
		hph2.setOpaque(true);
		hphtext.setOpaque(true);
		hphtext2.setOpaque(true);
		add(hph);
		add(hph2);
		add(hphtext);
		add(hphtext2);
		setLayout(null);
		jlp.add(this,JLayeredPane.DEFAULT_LAYER);
		
		for (int i =0;i<skills.size();i++) {
			double d = skills.get(i)!=null?skills.get(i).getAfterusems()*1.0/skills.get(i).getCdms():0;
			jls[i] = new JLabel();
			jls[i].setBackground(new Color(254, 105, 22, 155));
			jlp.add(jls[i],JLayeredPane.DEFAULT_LAYER);
			jls[i].setSize(38,(int)((1-d)*38+0.5));
			add(jls[i]);
			jls[i].setLocation(points[i].x,(int)(points[i].y+38*d+0.5));
			jls[i].setOpaque(true);
		}
		setSize(800,600);
		setVisible(true);
	}
	
	public void updataJls(){
		hphtext.setText("人:"+person.getHp()+"/"+person.getZhp());
		hphtext2.setText("怪:"+mon.getHp()+"/"+mon.getZhp());
		hph.setSize((int)(321*person.getHp()*1.0/person.getZhp()+0.5), 10);
		hph2.setSize((int)(321*mon.getHp()*1.0/mon.getZhp()+0.5), 10);
		for (int i = 0; i < 4; i++) {
			double d = skills.get(i)!=null?skills.get(i).getAfterusems()*1.0/skills.get(i).getCdms():1;
			if(d<0.1){
				d = 1;
			}
			jls[i].setBounds(points[i].x,(int)(points[i].y+38*d+0.5),38,(int)((1-d)*38+0.5));
		}
	}
	
	//技能相对位置  5 2***50 2 **  94 2 ***  138 2     技能框大小 181 44  技能大小38 38 
	//血条大小328 18 内部血条位置 3 3  //确定按钮大小 124 36 y坐标400
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ff.getIma(), 0, 0, null);
		g.drawImage(hpt, 5, 3, null);
		g.drawImage(hpt, 460, 3, null);
		if(ff.getMoupos()!=null){
			g.drawImage(ff.getMoupos(), ff.getTx()-18, 600-ff.getTy()-20, null);
		}
		g.drawImage(ff.getPerson().getPersonImage(), ff.getPerson().getPk_x()-25, 600-ff.getPerson().getPk_y()-25, null);
		g.drawImage(ff.getMon().getImage(), ff.getMon().getPk_x()-25, 600-ff.getMon().getPk_y()-25, null);
		for (int i = 0; i < 4; i++) {
			if(skills.get(i)!=null){
				g.drawImage(skills.get(i).getIcon(), points[i].x, points[i].y, null);
			}else{
				g.drawImage(noskill, points[i].x, points[i].y, null);
			}
		}
		g.drawImage(skillkuang, 5, 551, null);
	}
}

class WinPanel extends JPanel {
	private Image ima;
	private FightFarme ff;
	private JLayeredPane jlp;
	private JButton jbt;
	public WinPanel(FightFarme ff) {
		this.ff = ff;
		this.jlp = ff.getJlp();
		ima = Utils.getImage("fight/战斗胜利.png");
		jbt = new JButton(new ImageIcon("config/images/fight/战斗确定.jpg"));
		jlp.add(this,JLayeredPane.PALETTE_LAYER);
		jlp.add(jbt, JLayeredPane.PALETTE_LAYER);
		add(jbt);
		jbt.setBounds(338, 400, 124, 36);
		jbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Utils.killMonster(ff.getKs().getGameMap().getMap(), ff.getZhenmon());
				ff.getKs().setVisible(true);
				ff.getZhenperson().resetFX();
				ff.dispose();
				ff.getKs().setFf(null);
				MusicPlayer.getPlayer().closeBGM();
				MusicPlayer.getPlayer().playBGM(ff.getKs().getGameMap().getMap().getMapName());
			}
		});
		setSize(800, 600);
		setOpaque(false);
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ima, 0, 0, null);
	}
	
} 

class LosePanel extends JPanel {
	private Image ima;
	private FightFarme ff;
	private JLayeredPane jlp;
	private JButton jbt;
	public LosePanel(FightFarme ff) {
		this.ff = ff;
		this.jlp = ff.getJlp();
		ima = Utils.getImage("fight/战斗失败.png");
		jbt = new JButton(new ImageIcon("config/images/fight/战斗确定.jpg"));
		jlp.add(this,  JLayeredPane.PALETTE_LAYER);
		jlp.add(jbt, JLayeredPane.PALETTE_LAYER);
		add(jbt);
		jbt.setBounds(338, 400, 124, 36);
		jbt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ff.getKs().setVisible(true);
				ff.getZhenperson().resetFX();
				ff.dispose();
				ff.getKs().setFf(null);
				MusicPlayer.getPlayer().closeBGM();
				MusicPlayer.getPlayer().playBGM(ff.getKs().getGameMap().getMap().getMapName());
			}
		});
		setSize(800, 600);
		setOpaque(false);
		setVisible(true);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ima, 0, 0, null);
	}
	
} 
class UpdaSXY implements MouseMotionListener{
	private FightFarme ff;
	public UpdaSXY(FightFarme ff) {
		this.ff = ff;
	}
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		ff.setSx(e.getX());
		ff.setSy(600-e.getY());
	}
	
}