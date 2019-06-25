/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: AddFriendUI
 * Author:   OuYoung
 * Date:     2019/06/24 14:18
 * Description: 这是添加好友的弹出界面
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           版本号             描述
 */
package client.ui;

import javax.swing.*;
import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.border.EmptyBorder;

import client.service.Message;
import com.sun.awt.AWTUtilities;

import client.model.User;
import client.util.overrideClass.JBorder;
import client.service.UserManager;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

/**
 * 〈一句话功能简述〉 〈这是添加好友的弹出界面〉
 *
 * @author OuYoung
 * @create 2019/06/24
 * @since 1.0.0
 */

public class AddFriendUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JButton findFriend_btn;
	private JLabel find_friend_lbl;
	private JLabel find_group_lbl;

	private int findType = 0;
	private String headIcon;
	private String user_id;
	private String user_name;

	private int mouseAtX = 0; // 鼠标x轴
	private int mouseAtY = 0; // 鼠标y轴
	private JPanel mainPanel;
	private JPanel findBefore_panel;
	private JPanel findAfter_panel;
	private JPanel find_result;
	private JLabel headIcon_lbl;
	private JLabel name_lbl;
	private JLabel add_lbl;
	private JButton reback_btn;
	private JLabel tip_lbl;
	private Message message;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFriendUI a = new AddFriendUI(null);
					a.setType(JFrame.Type.UTILITY);
					a.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AddFriendUI(Message message) {
		this.message = message;
		this.init();
		// 圆角
		AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 20.0D, 20.0D));
		this.validate();
	}

	public void init() {
		this.setType(JFrame.Type.UTILITY);
		this.setUndecorated(true);
		// 窗口拖动
		addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				// 获取点击鼠标时的坐标
				mouseAtX = e.getPoint().x;
				mouseAtY = e.getPoint().y;
			}
		});
		addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				setLocation((e.getXOnScreen() - mouseAtX), (e.getYOnScreen() - mouseAtY));// 设置拖拽后，窗口的位置
			}
		});

		setBounds(100, 100, 700, 170);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(242, 242, 242));
		contentPane.setBorder(new JBorder(25, 25));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		mainPanel = new JPanel();
		mainPanel.setBounds(1, 0, 698, 160);
		mainPanel.setBackground(new Color(242, 242, 242));
		mainPanel.setLayout(null);
		contentPane.add(mainPanel);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/topBg.jpg")));
		lblNewLabel.setBounds(0, 0, 700, 5);
		mainPanel.add(lblNewLabel);

		JButton close_btn = new JButton("");
		close_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				dispose();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				close_btn.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/close_1.png")));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				close_btn.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/close_2.png")));
			}
		});
		close_btn.setBackground(Color.WHITE);
		close_btn.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/close_2.png")));
		close_btn.setBounds(675, 10, 16, 16);
		close_btn.setBorder(null);
		close_btn.setFocusable(false);
		mainPanel.add(close_btn);

		mainPanel.add(this.getFindBefore_panel());
		findBefore_panel.setVisible(true);
		mainPanel.add(this.getFindAfter_panel());
		findAfter_panel.setVisible(false);

	}

	public JPanel getFindBefore_panel() {
		findBefore_panel = new JPanel();
		findBefore_panel.setBounds(0, 30, 698, 130);
		findBefore_panel.setBorder(new JBorder(25, 25,new Color(242, 242, 242)));
//		mainPanel.add(findBefore_panel);
		findBefore_panel.setLayout(null);

		find_friend_lbl = new JLabel("找朋友");
		find_friend_lbl.setBounds(240, 0, 60, 35);
		findBefore_panel.add(find_friend_lbl);
		find_friend_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (findType == 0) {
					return;
				} else {
					findType = 0;
					find_friend_lbl.setForeground(new Color(78, 129, 151));
					find_group_lbl.setForeground(Color.BLACK);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				find_friend_lbl.setForeground(new Color(78, 129, 151));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (findType == 0) {
					find_friend_lbl.setForeground(new Color(78, 129, 151));
				} else {
					find_friend_lbl.setForeground(Color.BLACK);
				}
			}
		});
		find_friend_lbl.setFont(new Font("微软雅黑", Font.BOLD, 20));
		find_friend_lbl.setForeground(new Color(78, 129, 151));

		find_group_lbl = new JLabel("找群组");
		find_group_lbl.setBounds(395, 0, 60, 35);
		findBefore_panel.add(find_group_lbl);
		find_group_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				super.mouseClicked(e);
				if (findType == 1) {
					return;
				} else {
					findType = 1;
					find_group_lbl.setForeground(new Color(78, 129, 151));
					find_friend_lbl.setForeground(Color.BLACK);
				}
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				super.mouseEntered(e);
				find_group_lbl.setForeground(new Color(78, 129, 151));
			}

			@Override
			public void mouseExited(MouseEvent e) {
				super.mouseExited(e);
				if (findType == 1) {
					find_group_lbl.setForeground(new Color(78, 129, 151));
				} else {
					find_group_lbl.setForeground(Color.BLACK);
				}
			}
		});
		find_group_lbl.setFont(new Font("微软雅黑", Font.BOLD, 20));

		JPanel panel = new JPanel();
		panel.setBounds(25, 46, 550, 50);
		findBefore_panel.add(panel);
		panel.setBackground(new Color(242, 242, 242));
		panel.setBorder(new JBorder(55, 55));
		panel.setLayout(null);

		JLabel find_png = new JLabel();
		find_png.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/find.png")));
		find_png.setBounds(20, 9, 32, 32);
		panel.add(find_png);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                	if (textField.getText().equals("")) {
    					tip_lbl.setVisible(true);
    					return;
    				}
    				findUser(findType);
    				textField.setText("");
    				findBefore_panel.setVisible(false);
    				findAfter_panel.setVisible(true);
    				findAfter_panel.updateUI();
                }
            }
        });
		textField.setBounds(65, 2, 450, 46);
		textField.setBackground(new Color(242, 242, 242));
		textField.setBorder(null);
		panel.add(textField);
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		textField.setColumns(10);

		findFriend_btn = new JButton("查 找");
		findFriend_btn.setBounds(600, 54, 72, 35);
		findBefore_panel.add(findFriend_btn);
		findFriend_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
//				findFriend_btn.setText("请稍等");
				if (textField.getText().equals("")) {
					tip_lbl.setVisible(true);
					return;
				}
				findUser(findType);
				textField.setText("");
				findBefore_panel.setVisible(false);
				findAfter_panel.setVisible(true);
				findAfter_panel.updateUI();
			}

			@Override
			public void mouseReleased(MouseEvent e) {

			}
		});
		findFriend_btn.setForeground(Color.WHITE);
		findFriend_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		findFriend_btn.setBackground(new Color(105, 155, 178));
		findFriend_btn.setFocusable(false);
		
		tip_lbl = new JLabel("请输入好友账号或者群号");
		tip_lbl.setForeground(new Color(216, 30, 6));
		tip_lbl.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/caution.png")));
		tip_lbl.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		tip_lbl.setBounds(98, 99, 189, 18);
		tip_lbl.setVisible(false);
		findBefore_panel.add(tip_lbl);

		return findBefore_panel;
	}

	public JPanel getFindAfter_panel() {
		findAfter_panel = new JPanel();
		findAfter_panel.setBounds(0, 30, 698, 130);
		findAfter_panel.setBorder(new JBorder(25, 25,new Color(242, 242, 242)));
//		mainPanel.add(findAfter_panel);
		findAfter_panel.setLayout(null);
		
		find_result = new JPanel();
		find_result.setBounds(44, 35, 615, 65);
		find_result.setBorder(new JBorder(15,15));
		findAfter_panel.add(find_result);
		find_result.setLayout(null);
		
		headIcon_lbl = new JLabel("头像");
		headIcon_lbl.setBounds(15, 7, 50, 50);
		find_result.add(headIcon_lbl);
		
		name_lbl = new JLabel("测试");
		name_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				name_lbl.setText("<HTML><U>" + user_name + "</U></HTML>");
			}
			@Override
			public void mouseExited(MouseEvent e) {
				name_lbl.setText(user_name);
			}
		});
		name_lbl.setFont(new Font("微软雅黑", Font.PLAIN, 19));
		name_lbl.setBounds(75, 17, 220, 30);
		find_result.add(name_lbl);
		
		add_lbl = new JLabel("");
		add_lbl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String id = user_id;
				message.addFriendOrGroupSend(id,findType);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				add_lbl.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/add_1.png")));
			}
			@Override
			public void mouseExited(MouseEvent e) {
				add_lbl.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/add_2.png")));
			}
		});
		add_lbl.setIcon(new ImageIcon(AddFriendUI.class.getResource("/client/images/add_2.png")));
		add_lbl.setBounds(560, 15, 32, 32);
		find_result.add(add_lbl);
		
		reback_btn = new JButton("返回");
		reback_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				findAfter_panel.setVisible(false);
				findBefore_panel.setVisible(true);
			}
		});
		reback_btn.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		reback_btn.setBounds(615, 105, 65, 25);
		reback_btn.setFocusable(false);
		reback_btn.setBorder(null);
		reback_btn.setForeground(Color.WHITE);
		reback_btn.setBackground(new Color(105, 155, 178));
		findAfter_panel.add(reback_btn);

		return findAfter_panel;
	}

	public void findUser(int type) {
		String id = textField.getText().trim();
		UserManager um = new UserManager();
		if (type == 0) {
			User user = um.getUser(id);
			this.user_id = String.valueOf(user.getId());
			this.user_name = user.getName();
			this.name_lbl.setText(user_name);
		}else if (type == 1) {
			String group_name = um.getGroupName(id);
			this.user_id = id;
			this.user_name = group_name;
			this.name_lbl.setText(user_name);
		}
		
	}
}
