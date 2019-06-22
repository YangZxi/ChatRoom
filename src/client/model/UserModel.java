package client.model;

import client.ui.ChatShowInputUI;
import client.ui.ClientUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class UserModel extends JPanel {

	private HashMap<UserModel, ChatShowInputUI> personList = null;

	private String id = null;
	private String name = null;
	private JLabel personName;
	private JLabel onlineStatus;	// 在线状态
	private ClientUI clientUI;
	private ChatShowInputUI csUI = null;
	private JLabel red_dot;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JFrame jf = new JFrame();
					UserModel frame = new UserModel();
					jf.getContentPane().add(frame);
					jf.setBounds(100,100,500,300);
					jf.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public JLabel getOnlineStatus() {
		return onlineStatus;
	}

	public JLabel getRed_dot() {
		return red_dot;
	}

	public HashMap<UserModel, ChatShowInputUI> getPersonList() {
		return personList;
	}

	public void setPersonList(HashMap<UserModel, ChatShowInputUI> personList) {
		this.personList = personList;
	}

	/**
	 * Create the frame.
	 */
	public UserModel() {
		init();
	}

	public UserModel(String id, String user_name, ClientUI clientUI, ChatShowInputUI csUI) {
		init();
		this.id = id;
		this.name = user_name;
		this.personName.setText(user_name);
		this.clientUI = clientUI;
		this.csUI = csUI;
		this.setVisible(true);
	}
	
	public void init() {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				// 设置被选中的背景色
//				personPanel.setBackground(new Color(191, 191, 191));
				// 设置当前的背景
				setBackground(new Color(191, 191, 191));
				// 隐藏小红点
				red_dot.setVisible(false);
				// 显示消息的显示和输入界面
				System.out.println("显示" + csUI.getStr_Name());
				clientUI.getBottomPanel().remove(clientUI.getBottomRightPanel());
				if (clientUI.getChatShowInputUI() != null) {
					clientUI.getChatShowInputUI().setVisible(false);
					clientUI.getBottomPanel().remove(clientUI.getChatShowInputUI());
				}
				clientUI.setChatShowInputUI(csUI);
				clientUI.getBottomPanel().add(csUI);
				clientUI.getChatShowInputUI().setVisible(true);
				clientUI.getBottomPanel().updateUI();
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
	
		this.setBounds(0, 0, 310, 70);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
		this.setLayout(null);
		// 好友头像
		JLabel personIcon = new JLabel("头像");
		personIcon.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		personIcon.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(179, 179, 179)));
		personIcon.setBounds(15, 10, 50, 50);
		this.add(personIcon);
		// 好友名称
		personName = new JLabel("新加入好友新加入好友");
		personName.setFont(new Font("微软雅黑", Font.PLAIN, 18));
		personName.setBounds(79, 10, 185, 30);
		this.add(personName);
		// 上下线标识
		onlineStatus = new JLabel("离线");
		onlineStatus.setFont(new Font("微软雅黑", Font.PLAIN, 16));
		onlineStatus.setBounds(255, 42, 34, 18);
		add(onlineStatus);
		
		red_dot = new JLabel("");
		red_dot.setIcon(new ImageIcon(UserModel.class.getResource("/client/images/red_dot.png")));
		red_dot.setBounds(268, 10, 16, 16);
		red_dot.setVisible(false);
		add(red_dot);
	}
}
