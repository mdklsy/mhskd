package game;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * 
 * @author LSY
 * @date Dec 7, 2016
 * 欢迎面板(输入用户名)
 */
public class Welcome extends JPanel {
	private JButton jbt;
	private JLabel jl1;
	private JLabel jl2;
	private JTextField jTextField;
	private Properties pro;

	public JButton getJbt() {
		return jbt;
	}


	public JLabel getJl1() {
		return jl1;
	}


	public JLabel getJl2() {
		return jl2;
	}


	public JTextField getjTextField() {
		return jTextField;
	}


	public Properties getPro() {
		return pro;
	}


	public Welcome(JLayeredPane jlp) {
		//super();
		welcomeInit(jlp);
		
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(new ImageIcon("config/images/欢迎.jpg").getImage(), 0, 0, null);
	}
	
	private void welcomeInit(JLayeredPane jlp) {
		jbt = new JButton("确定");
		jl1 = new JLabel("用户名:");
		jTextField = new JTextField(20);
		jl2 = new JLabel("");
		jl1.setFont(new Font("宋体",Font.BOLD, 16));
		jl2.setFont(new Font("宋体",Font.BOLD, 16));
		jTextField.setFont(new Font("宋体",Font.BOLD, 14));
		jl1.setForeground(Color.WHITE);
		jl2.setForeground(Color.WHITE);
		getUsername();
		setLayout(null);
		jlp.add(this, JLayeredPane.PALETTE_LAYER);
		jlp.add(jbt, JLayeredPane.PALETTE_LAYER);
		jlp.add(jl1, JLayeredPane.PALETTE_LAYER);
		jlp.add(jl2, JLayeredPane.PALETTE_LAYER);
		jlp.add(jTextField, JLayeredPane.PALETTE_LAYER);

		this.add(jbt);
		this.add(jl1);
		this.add(jl2);
		this.add(jTextField);
		setSize(800, 600);
		jl1.setBounds(250, 280, 60, 40);
		jTextField.setBounds(320, 280, 100, 25);
		jl2.setBounds(430, 280, 160, 40);
		jbt.setBounds(335, 360, 70, 30);
		setOpaque(false);
	}


	private void getUsername() {
		pro = new Properties();
		try {
			pro.load(new FileInputStream("config/message/a.properties"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		String name = pro.getProperty("name", "");
		jTextField.setText(name);
		jTextField.selectAll();
	}
}
