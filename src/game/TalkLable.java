package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
/**
 * 
 * @author lsy
 * @date 2017年1月11日
 * 注释:对话框面板
 */
public class TalkLable extends JPanel{
	private JTextArea text1;
	private JTextArea  text2;
	private Image ima;
	public TalkLable(JLayeredPane jlp) {
		text1 = new JTextArea(1,10);
		text2 = new JTextArea(8, 30);
		jlp.add(this, JLayeredPane.PALETTE_LAYER);
		jlp.add(text1, JLayeredPane.PALETTE_LAYER);
		jlp.add(text2, JLayeredPane.PALETTE_LAYER);
		add(text1);
		add(text2);
		setLayout(null);
		setSize(500, 298);
		text1.setBounds(20,180,100,19);
		text2.setBounds(20,210,460,70);
		text1.setOpaque(false);
		text2.setOpaque(false);
		setOpaque(false);
		text1.setEditable(false);
		text2.setEditable(false);
		text2.setLineWrap(true);
		text1.setFont(new Font("宋体", Font.BOLD, 11));
		text2.setFont(new Font("宋体", Font.BOLD, 11));
		text1.setForeground(Color.WHITE);
		text2.setForeground(Color.WHITE);
		ima = new ImageIcon("config/images/show/对话框.png").getImage();
		setVisible(false);
	}
	public void setText(String s1,String s2){
		text1.setText(s1);
		text2.setText("    "+s2);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(ima, 0, 0, null);
	}
}
