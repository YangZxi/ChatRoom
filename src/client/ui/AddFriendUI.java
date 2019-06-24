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

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.util.Locale;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import client.model.User;
import client.overrideClass.JBorder;
import client.overrideClass.RJTextField;
import client.service.UserManager;

import javax.swing.JTextField;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 〈一句话功能简述〉
 * 〈这是添加好友的弹出界面〉
 *
 * @author OuYoung
 * @create 2019/06/24
 * @since 1.0.0
 */


public class AddFriendUI extends JPanel {

	private JPanel contentPane;
	private JTextField textField;
	private JButton addFriend_btn;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame j = new JFrame();
					j.setBounds(100,100,700,250);
					j.setVisible(true);
					AddFriendUI a = new AddFriendUI();
					j.getContentPane().add(a);
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
	public AddFriendUI() {
		setBounds(100, 100, 700, 200);
//		setBackground(Color.WHITE);
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(25, 107, 550, 50);
		panel.setBackground(Color.WHITE);
		panel.setBorder(new JBorder(55,55));
		add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(79, 2, 400, 46);
		textField.setBackground(Color.WHITE);
		textField.setBorder(null);
		panel.add(textField);
		textField.setFont(new Font("微软雅黑", Font.PLAIN, 22));
		textField.setColumns(10);
		
		addFriend_btn = new JButton("查找");
		addFriend_btn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				addFriend_btn.setText("请稍等");
				findUser();
			}
			@Override
			public void mouseReleased(MouseEvent e) {
			
			}
		});
		addFriend_btn.setForeground(Color.WHITE);
		addFriend_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		addFriend_btn.setBackground(new Color(105, 155, 178));
		addFriend_btn.setFocusable(false);
		addFriend_btn.setBounds(592, 115, 70, 35);
		add(addFriend_btn);
	}
	
	public void findUser() {
		String id = textField.getText().trim();
		UserManager um = new UserManager();
		User user = um.getUser(id);
		System.out.println(user.getId());
		System.out.println(user.getName());
		System.out.println(user.getSex());
	}
}

