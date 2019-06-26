/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: CreateGroupUI
 * Author:   OuYoung
 * Date:     2019/06/26 20:58
 * Description: 创建群聊的UI
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           版本号             描述
 */
package client.ui;

import client.service.Message;
import client.service.UserManager;
import client.util.overrideClass.JBorder;

import javax.swing.*;
import javax.swing.border.EmptyBorder;

import com.sun.awt.AWTUtilities;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;

/**
 * 〈一句话功能简述〉 〈创建群聊的UI〉
 *
 * @author OuYoung
 * @create 2019/06/26
 * @since 1.0.0
 */
public class CreateGroupUI extends JFrame {

	private int mouseAtX = 0; // 鼠标x轴
	private int mouseAtY = 0; // 鼠标y轴

	private String user_id;
	private Message message;

	private JPanel contentPane;
	private JTextField textField;
	private JLabel tip_lbl;

	private UserManager userManager;

	public static void main(String[] args) {
		CreateGroupUI c = new CreateGroupUI();
		c.setVisible(true);
	}

	public CreateGroupUI() {
		this.init();
		// 圆角
		AWTUtilities.setWindowShape(this,
				new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 20.0D, 20.0D));
		this.validate();
	}

	public CreateGroupUI(String user_id, Message message) {
		this.user_id = user_id;
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

		setBounds(100, 100, 400, 250);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setBackground(new Color(242, 242, 242));
		contentPane.setBorder(new JBorder(25, 25));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel bg_lbl = new JLabel("New label");
		bg_lbl.setIcon(new ImageIcon(CreateGroupUI.class.getResource("/client/images/topBg.jpg")));
		bg_lbl.setBounds(0, 0, 400, 5);
		contentPane.add(bg_lbl);

		JPanel panel = new JPanel();
		panel.setBounds(10, 10, 380, 230);
		contentPane.add(panel);
		panel.setLayout(null);

		JButton close_btn = new JButton("");
		close_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				tip_lbl.setVisible(false);
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
		close_btn.setIcon(new ImageIcon(CreateGroupUI.class.getResource("/client/images/close_2.png")));
		close_btn.setFocusable(false);
		close_btn.setBorder(null);
		close_btn.setBackground(Color.WHITE);
		close_btn.setBounds(364, 0, 16, 16);
		panel.add(close_btn);

		JLabel titel = new JLabel("创建群聊");
		titel.setFont(new Font("微软雅黑", Font.BOLD, 18));
		titel.setBounds(155, 10, 72, 18);
		panel.add(titel);

		JLabel name = new JLabel("群名称");
		name.setFont(new Font("微软雅黑", Font.PLAIN, 20));
		name.setBounds(53, 71, 60, 24);
		panel.add(name);

		JPanel panel_1 = new JPanel();
		panel_1.setBounds(123, 59, 202, 48);
		panel.add(panel_1);
		panel_1.setBorder(new JBorder(50, 50));
		panel_1.setLayout(null);

		textField = new JTextField();
		textField.setBounds(16, 5, 170, 38);
		textField.setBackground(new Color(240, 240, 240));
		textField.setBorder(null);
		panel_1.add(textField);
		textField.setColumns(10);

		JButton create_btn = new JButton("创建");
		create_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				createGroupFunction();
			}
		});
		create_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		create_btn.setBounds(301, 190, 70, 30);
		create_btn.setForeground(Color.WHITE);
		create_btn.setBackground(new Color(78, 129, 151));
		panel.add(create_btn);

		tip_lbl = new JLabel("群创建成功，您的群号为：535251");
		tip_lbl.setForeground(new Color(216, 30, 6));
		tip_lbl.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		tip_lbl.setBounds(53, 143, 251, 18);
		tip_lbl.setVisible(false);
		panel.add(tip_lbl);

	}

	public void createGroupFunction() {
		if (userManager == null) {
			userManager = new UserManager();
		}
		boolean flag = userManager.createGroup(textField.getText().trim(),user_id);
		if (flag == true) {
			tip_lbl.setVisible(true);
			message.getClientUI().addFriendToList("535251",1);
		}
	}

}