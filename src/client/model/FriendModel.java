/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: ClientUI
 * Author:   OuYoung
 * Date:     2019/06/22 22:19
 * Description: 客户端聊天界面的主视图
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           版本号             描述
 */
package client.model;

/**
 * 〈一句话功能简述〉
 * 〈客户端聊天界面的主视图〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */

import client.ui.ChatShowInputUI;
import client.ui.ClientUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

public class FriendModel extends JPanel {

    private HashMap<FriendModel, ChatShowInputUI> personList = null;

    private String id = null;
    private String name = null;
    private JLabel personName;
    private String type;
    private JLabel onlineStatus;    // 在线状态
    private ClientUI clientUI;
    private ChatShowInputUI csUI = null;
    private JLabel red_dot;

    private JLabel headIcon;


    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    JFrame jf = new JFrame();
                    FriendModel frame = new FriendModel("111111", "不是木易杨", 1, null, null);
                    jf.getContentPane().add(frame);
                    jf.setBounds(100, 100, 500, 300);
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

    public HashMap<FriendModel, ChatShowInputUI> getPersonList() {
        return personList;
    }

    public void setPersonList(HashMap<FriendModel, ChatShowInputUI> personList) {
        this.personList = personList;
    }

    /**
     * Create the frame.
     */
    public FriendModel() {
        init();
    }

    public FriendModel(String id, String user_name, int type, ClientUI clientUI, ChatShowInputUI csUI) {
        this.id = id;
        this.name = user_name;
        this.clientUI = clientUI;
        this.csUI = csUI;
        init();
        this.personName.setText(user_name);
        String person_type = type == 0 ? "离线" : "群聊";
        if (type == 0) {
            headIcon.setIcon(new ImageIcon(FriendModel.class.getResource("/client/images/" + this.id + ".jpg")));
        } else if (type == 1) {
            headIcon.setIcon(new ImageIcon(FriendModel.class.getResource("/client/images/535251.jpg")));
        }
        onlineStatus.setText(person_type);
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
//				System.out.println("显示" + csUI.getStr_Name());
                if (clientUI.getBottomRightPanel().isVisible() == true) {
                    clientUI.getBottomRightPanel().setVisible(false);
                }
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

        headIcon = new JLabel("头像");
        headIcon.setIcon(new ImageIcon(FriendModel.class.getResource("/client/images/000000.jpg")));
        headIcon.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        headIcon.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, new Color(179, 179, 179)));
        headIcon.setBounds(15, 10, 50, 50);
        this.add(headIcon);
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
        red_dot.setIcon(new ImageIcon(FriendModel.class.getResource("/client/images/red_dot.png")));
        red_dot.setBounds(268, 10, 16, 16);
        red_dot.setVisible(false);
        add(red_dot);
    }
}
