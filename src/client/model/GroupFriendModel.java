/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: GroupFriendModel
 * Author:   OuYoung
 * Date:     2019/06/23 9:37
 * Description: 群聊天界面的好友列表Model
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           版本号             描述
 */
package client.model;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 〈一句话功能简述〉
 * 〈群聊天界面的好友列表Model〉
 *
 * @author OuYoung
 * @create 2019/06/23
 * @since 1.0.0
 */
public class GroupFriendModel extends JPanel {
	
	private String user_id;
	private String user_name;
	private JLabel groupFriend_name;

	public JLabel getGroupFriend_name() {
		return groupFriend_name;
	}

	public void setGroupFriend_name(JLabel groupFriend_name) {
		this.groupFriend_name = groupFriend_name;
	}

	public GroupFriendModel() {
		this.init();
	}

	public GroupFriendModel(String user_name) {
		this.user_name = user_name;
		this.init();
	}

	public GroupFriendModel(String user_id,String user_name) {
		this.user_id = user_id;
		this.user_name = user_name;
		this.init();
	}
	
	public void init() {
		this.setLayout(null);
		this.setBounds(0,0,160,25);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 设置被选中的背景色
//				personPanel.setBackground(new Color(191, 191, 191));
				// 设置当前的背景
				setBackground(new Color(191, 191, 191));
			}
			@Override
			public void mouseEntered(MouseEvent e) {
//				// 改变被标记的好友背景色
//				if (PERSONPANEL_HOVER != null) {
//					PERSONPANEL_HOVER.setBackground(new Color(240, 240, 240));
//				}
//				// 设置当前的背景
				setBackground(new Color(191, 191, 191));
//				// 存进标记
//				PERSONPANEL_HOVER = personPanel;
			}
			@Override
			public void mouseExited(MouseEvent e) {
				setBackground(new Color(240, 240, 240));
			}
		});
		
		groupFriend_name = new JLabel("这里显示群成员的名称");
		groupFriend_name.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		groupFriend_name.setBounds(2, 1, 144, 23);
		add(groupFriend_name);
	}
	
	public static void main(String[] args) {
		JFrame j = new JFrame();
		j.setBounds(100,100,250,200);
		j.setVisible(true);
		GroupFriendModel g = new GroupFriendModel();
		g.setVisible(true);
		j.add(g);
	}
}