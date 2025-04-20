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
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import model.Bao;
import model.Equipment;
import model.Items;
import model.Person;

/**
 * 
 * @author lsy
 * @date 2016年12月27日 注释:背包游戏面板类,负责在游戏窗口显示背包及其中的物品
 */
public class BeiBao extends JPanel {
	private Bao bao;
	private Image ima1;
	private Image ima2;
	private ArrayList<Point> zb;
	private ArrayList<Image> data;
	private JLabel jl;
	private BeiBaoMouse bbm;
	private BeiBaoMenu bbmenu;
	private PersonEqpt eqpt;

	public BeiBao(PersonEqpt eqpt,Person person, JLayeredPane jlp) {
		zbInit();
		this.eqpt = eqpt;
		this.bao = person.getBao();
		ima1 = new ImageIcon("config/images/items/sys/包.jpg").getImage();
		ima2 = new ImageIcon("config/images/items/sys/格子.jpg").getImage();
		jl = new JLabel();
		jl.setSize(70, 50);
		jl.setVisible(false);
		jl.setOpaque(true);
		setLayout(null);
		jlp.add(this, JLayeredPane.PALETTE_LAYER);
		jlp.add(jl, JLayeredPane.PALETTE_LAYER);
		bbmenu = new BeiBaoMenu(jlp, this, person,eqpt);
		add(bbmenu);
		add(jl);
		setSize(190, 200);
		setLocation(500, 100);
		bbm = new BeiBaoMouse(this);
		addMouseMotionListener(bbm);
		setVisible(false);
		addMouseListener(new MouseListener() {
			Dimension ds = new Dimension(26, 26);

			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					int size = data.size();
					Rectangle rt = null;
					for (int i = 0; i < size; i++) {
						rt = new Rectangle(zb.get(i), ds);
						if (rt.contains(e.getX(), e.getY())) {
							bbmenu.setLocation(e.getX() - 5, e.getY() - 5);
							bbmenu.setVisible(true);
							bbmenu.setSelectIndex(i);
							BeiBao.this.removeMouseMotionListener(bbm);
							jl.setVisible(false);
							break;
						}
					}
				}
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
				
			}
		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		data = bao.upDataImages();
		g.drawImage(ima1, 0, 0, null);
		g.drawImage(ima2, 45, 60, null);
		// 道具尺寸26*26
		for (int i = 0; i < data.size(); i++) {
			g.drawImage(data.get(i), zb.get(i).x, zb.get(i).y, null);
		}
	}

	public boolean addItem(Items item, int num) {
		return bao.addItem(item, num);
	}

	private void zbInit() {
		zb = new ArrayList<Point>();
		zb.add(new Point(47, 62));
		zb.add(new Point(77, 62));
		zb.add(new Point(107, 62));
		zb.add(new Point(47, 92));
		zb.add(new Point(77, 92));
		zb.add(new Point(107, 92));
		zb.add(new Point(47, 122));
		zb.add(new Point(77, 122));
		zb.add(new Point(107, 122));
	}

	public Bao getBao() {
		return bao;
	}

	public ArrayList<Point> getZb() {
		return zb;
	}

	public ArrayList<Image> getData() {
		return data;
	}

	public JLabel getJl() {
		return jl;
	}

	public BeiBaoMouse getBbm() {
		return bbm;
	}

}

// 鼠标在道具上移动显示道具信息
class BeiBaoMouse implements MouseMotionListener {
	private BeiBao bb;
	private Dimension ds;

	public BeiBaoMouse(BeiBao bb) {
		this.bb = bb;
		ds = new Dimension(26, 26);
	}

	// 移动
	public void mouseMoved(MouseEvent e) {
		int size = bb.getData().size();
		Rectangle rt = null;
		for (int i = 0; i < size; i++) {
			rt = new Rectangle(bb.getZb().get(i), ds);
			if (rt.contains(e.getX(), e.getY())) {
				Items itm = bb.getBao().getItem(i);
				String s = "<html>" + itm.getName() + "<br/>数量：" + itm.getAmount()+"<br/>价格：" + itm.getSalePrice() + "<html>";
				JLabel jl = bb.getJl();
				jl.setText(s);
				jl.setLocation(e.getX()+10, e.getY());
				jl.setVisible(true);
				break;
			} else {
				bb.getJl().setVisible(false);
			}
		}
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}
}

// 背包中右键道具菜单
class BeiBaoMenu extends JPanel {
	private JLabel jl1;
	private JLabel jl2;
	private int selectIndex;
	private PersonEqpt eqpt;

	public BeiBaoMenu(JLayeredPane jlp, BeiBao bb, Person person,PersonEqpt eqpt) {
		selectIndex = -1;
		this.eqpt = eqpt;
		jl1 = new JLabel("使用");
		jl2 = new JLabel("删除");
		jl1.setFont(new Font("宋体", Font.BOLD, 10));
		jl2.setFont(new Font("宋体", Font.BOLD, 10));
		jlp.add(this, JLayeredPane.PALETTE_LAYER);
		jlp.add(jl1, JLayeredPane.PALETTE_LAYER);
		jlp.add(jl2, JLayeredPane.PALETTE_LAYER);
		add(jl1);
		add(jl2);
		setLayout(null);
		jl1.setBounds(2, 2, 25, 10);
		jl2.setBounds(2, 12, 25, 10);
		jl1.setOpaque(true);
		jl2.setOpaque(true);
		setSize(29, 24);
		setVisible(false);
		addMouseListener(new MouseListener() {
			public void mouseReleased(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseClicked(MouseEvent e) {
			}

			// 离开
			public void mouseExited(MouseEvent e) {
				BeiBaoMenu.this.setVisible(false);
				bb.addMouseMotionListener(bb.getBbm());
			}

		});
		jl1.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			// 离开
			public void mouseExited(MouseEvent e) {
				jl1.setBackground(Color.white);
			}

			// 进入
			public void mouseEntered(MouseEvent e) {
				jl1.setBackground(Color.GREEN);
			}

			// 点击
			public void mouseReleased(MouseEvent e) {
				System.out.println("使用");
				if (selectIndex > -1) {
					Items item = bb.getBao().getItem(selectIndex);
					Equipment ite = (Equipment)item;
					int[] attr =  new int[]{ite.getAd(),ite.getDad(),ite.getAp(),ite.getDap(),ite.getPhy(),ite.getAvd()};
					HashMap<String, Items> eqpt = person.getPersonEqpt();
					switch (item.getItemType()) {
					case helmet:
						// 如果装备栏为空
						if (eqpt.get("helmet") == null) {
							Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
									item.getSalePrice(), item.getItemType(),attr);
							itemTemp.addAmount(1);
							int i = item.removeAmount(1);
							if (i > 0) {
								eqpt.put("helmet", itemTemp);
							} else if (i == 0) {
								eqpt.put("helmet", itemTemp);
								bb.getBao().removeItem(selectIndex);
							}
						} else {
							// 装备栏有装备但是不是同一件装备
							if (!eqpt.get("helmet").isOne(item.getId())) {
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								int i = item.removeAmount(1);
								Items oldItem = eqpt.get("helmet");
								System.out.println(oldItem.getAmount());
								if (i > 0) {
									eqpt.put("helmet", itemTemp);
								} else if (i == 0) {
									eqpt.put("helmet", itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize()) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						}
						break;
					case coat:
						if (eqpt.get("coat") == null) {
							Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
									item.getSalePrice(), item.getItemType(),attr);
							itemTemp.addAmount(1);
							int i = item.removeAmount(1);
							if (i > 0) {
								eqpt.put("coat", itemTemp);
							} else if (i == 0) {
								eqpt.put("coat", itemTemp);
								bb.getBao().removeItem(selectIndex);
							}
						} else {
							if (!eqpt.get("coat").isOne(item.getId())) {
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								Items oldItem = eqpt.get("coat");
								int i = item.removeAmount(1);
								if (i > 0) {
									eqpt.put("coat", itemTemp);
								} else if (i == 0) {
									eqpt.put("coat", itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize()) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						}
						break;
					case pants:
						if (eqpt.get("pants") == null) {
							Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
									item.getSalePrice(), item.getItemType(),attr);
							itemTemp.addAmount(1);
							int i = item.removeAmount(1);
							if (i > 0) {
								eqpt.put("pants", itemTemp);
							} else if (i == 0) {
								eqpt.put("pants", itemTemp);
								bb.getBao().removeItem(selectIndex);
							}
						} else {
							if (!eqpt.get("pants").isOne(item.getId())) {
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								Items oldItem = eqpt.get("pants");
								int i = item.removeAmount(1);
								if (i > 0) {
									eqpt.put("pants", itemTemp);
								} else if (i == 0) {
									eqpt.put("pants", itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize()) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						}
						break;
					case shoes:
						if (eqpt.get("shoes") == null) {
							Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
									item.getSalePrice(), item.getItemType(),attr);
							itemTemp.addAmount(1);
							int i = item.removeAmount(1);
							if (i > 0) {
								eqpt.put("shoes", itemTemp);
							} else if (i == 0) {
								eqpt.put("shoes", itemTemp);
								bb.getBao().removeItem(selectIndex);
							}
						} else {
							if (!eqpt.get("shoes").isOne(item.getId())) {
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								Items oldItem = eqpt.get("shoes");
								int i = item.removeAmount(1);
								if (i > 0) {
									eqpt.put("shoes", itemTemp);
								} else if (i == 0) {
									eqpt.put("shoes", itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize()) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						}
						break;
					case weapon:
						if (eqpt.get("weapon") == null) {
							Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
									item.getSalePrice(), item.getItemType(),attr);
							itemTemp.addAmount(1);
							int i = item.removeAmount(1);
							if (i > 0) {
								eqpt.put("weapon", itemTemp);
							} else if (i == 0) {
								eqpt.put("weapon", itemTemp);
								bb.getBao().removeItem(selectIndex);
							}
						} else {
							if (!eqpt.get("weapon").isOne(item.getId())) {
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								Items oldItem = eqpt.get("weapon");
								int i = item.removeAmount(1);
								if (i > 0) {
									eqpt.put("weapon", itemTemp);
								} else if (i == 0) {
									eqpt.put("weapon", itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize()) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						}
						break;
					case Addition:
						if (eqpt.get("Addition1") != null && eqpt.get("Addition2") != null
								&& eqpt.get("Addition3") != null) {
							if (!eqpt.get("Addition1").isOne(item.getId()) && !eqpt.get("Addition2").isOne(item.getId())
									&& eqpt.get("Addition3").isOne(item.getId())) {
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								int i = item.removeAmount(1);
								Items oldItem = eqpt.get("Addition1");
								if (i > 0) {
									eqpt.put("Addition1", itemTemp);
								} else if (i == 0) {
									eqpt.put("Addition1", itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize()) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						} else {
							if (eqpt.get("Addition1") == null) {
								int j = 1;
								if (eqpt.get("Addition2") != null && eqpt.get("Addition2").isOne(item.getId())) {
									j = 2;
								}
								if (eqpt.get("Addition3") != null && eqpt.get("Addition3").isOne(item.getId())) {
									j = 3;
								}
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								int i = item.removeAmount(1);
								Items oldItem = eqpt.get("Addition" + j);
								if (i > 0) {
									eqpt.put("Addition" + j, itemTemp);
								} else if (i == 0) {
									eqpt.put("Addition" + j, itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize() && j != 1) {
									bb.getBao().addItem(oldItem, 0);
								}
							} else if (eqpt.get("Addition2") == null) {
								int j = 2;
								if (eqpt.get("Addition1") != null && eqpt.get("Addition1").isOne(item.getId())) {
									j = 1;
								}
								if (eqpt.get("Addition3") != null && eqpt.get("Addition3").isOne(item.getId())) {
									j = 3;
								}
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								int i = item.removeAmount(1);
								Items oldItem = eqpt.get("Addition" + j);
								if (i > 0) {
									eqpt.put("Addition" + j, itemTemp);
								} else if (i == 0) {
									eqpt.put("Addition" + j, itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize() && j != 2) {
									bb.getBao().addItem(oldItem, 0);
								}
							} else {
								int j = 3;
								if (eqpt.get("Addition1") != null && eqpt.get("Addition1").isOne(item.getId())) {
									j = 1;
								}
								if (eqpt.get("Addition2") != null && eqpt.get("Addition2").isOne(item.getId())) {
									j = 2;
								}
								Items itemTemp = new Equipment(item.getId(), item.getName(), item.getIma(),
										item.getSalePrice(), item.getItemType(),attr);
								itemTemp.addAmount(1);
								int i = item.removeAmount(1);
								Items oldItem = eqpt.get("Addition" + j);
								if (i > 0) {
									eqpt.put("Addition" + j, itemTemp);
								} else if (i == 0) {
									eqpt.put("Addition" + j, itemTemp);
									bb.getBao().removeItem(selectIndex);
								}
								if (bb.getBao().getItemSum() < bb.getBao().getBaoSize() && j != 3) {
									bb.getBao().addItem(oldItem, 0);
								}
							}
						}
						break;

					default:
						break;
					}
				}
				person.upDataAttr();
				eqpt.upDataText();
				selectIndex = -1;
				BeiBaoMenu.this.setVisible(false);
				bb.addMouseMotionListener(bb.getBbm());
			}

		});

		jl2.addMouseListener(new MouseListener() {
			public void mouseClicked(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
			}

			// 离开
			public void mouseExited(MouseEvent e) {
				jl2.setBackground(Color.white);
			}

			// 进入
			public void mouseEntered(MouseEvent e) {
				jl2.setBackground(Color.GREEN);
			}

			// 点击
			public void mouseReleased(MouseEvent e) {
				System.out.println("删除");
				if (selectIndex > -1) {
					bb.getBao().removeItem(selectIndex);
					selectIndex = -1;
				}
				BeiBaoMenu.this.setVisible(false);
				bb.addMouseMotionListener(bb.getBbm());
			}
		});

	}

	public void setSelectIndex(int selectIndex) {
		this.selectIndex = selectIndex;
	}
}
