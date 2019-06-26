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
package client.ui;

/**
 * 〈一句话功能简述〉
 * 〈客户端聊天界面的主视图〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */

import client.model.FriendModel;
import client.model.Group;
import client.model.InforModel;
import client.model.User;

import client.service.UserManager;
import client.util.CloseUtil;
import client.service.Message;
import com.sun.awt.AWTUtilities;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.RoundRectangle2D;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class ClientUI extends JFrame {

    private int mouseAtX = 0;    // 鼠标x轴
    private int mouseAtY = 0;    // 鼠标y轴
    
    private int inforMsgNum = 0;

    private Color topButtonColor = new Color(66, 73, 153);
    private Color FONT_COLOR = new Color(51, 51, 51);
    private Color BOTTOM_LEFT_BGCOLOR = new Color(238, 238, 238);
    private Color BOTTOM_RIGHT_BGCOLOR = new Color(242, 242, 242);

    private JPanel PERSONPANEL_CLICK = null;
    private JPanel PERSONPANEL_HOVER = null;

    private Message message = null;
    private Socket socket = null;
    private UserManager userManager = new UserManager();
    private User user = null;       // 登录的用户信息
    private boolean onlineStatus = false;   // 上线状态
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private StringBuffer myFriendsID = null;    // 所有好友ID
    private StringBuffer myGroupsID = null;     // 所有群ID
    private ArrayList<InforModel> inforMessageList = new ArrayList<InforModel>();   // 消息通知列表
    private HashMap<FriendModel, ChatShowInputUI> personList = new HashMap<FriendModel, ChatShowInputUI>();     // 好友列表和聊天界面
    private Set set = null;
    private Iterator it = null;

    private ClientUI clientUI;
    private JPanel contentPane;
    private JButton closeButton;
    private JButton zxhButton;
    private JPanel onlineListPanel;
    private JPanel bottomPanel;
    private JPanel bottomRightPanel;
    private JLabel userNameLabel;
    private ChatShowInputUI chatShowInputUI;
    private AddFriendUI addFriendUI;
    private int ADDFRIENDUI_WIDTH = 0;
    private int ADDFRIENDUI_HEIGHT = 0;
	private JPanel infor_Panel;
	private JPanel b_r_childPanel;
	private JPanel inforInner_Panel;
	private JLabel red_dot;

    /**
     * Launch the application.
     */
//	public static void main(String[] args) {
//		EventQueue.invokeLater(new Runnable() {
//			public void run() {
//				try {
//					ClientUI frame = new ClientUI();
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		});
//	}
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setMessage(Message message) {
        this.message = message;
    }

    public void setClientUI(ClientUI clientUI) {
        this.clientUI = clientUI;
    }

    public JPanel getOnlineListPanel() {
        return onlineListPanel;
    }

    public JPanel getBottomPanel() {
        return bottomPanel;
    }

    public JPanel getBottomRightPanel() {
        return bottomRightPanel;
    }

    public ChatShowInputUI getChatShowInputUI() {
        return chatShowInputUI;
    }

    public void setChatShowInputUI(ChatShowInputUI chatShowInputUI) {
        this.chatShowInputUI = chatShowInputUI;
    }

    public void setBottomRightPanel(JPanel bottomRightPanel) {
        this.bottomRightPanel = bottomRightPanel;
    }

    public HashMap<FriendModel, ChatShowInputUI> getPersonList() {
        return personList;
    }

    public void setPersonList(HashMap<FriendModel, ChatShowInputUI> personList) {
        this.personList = personList;
    }

    public Set getSet() {
        return set;
    }

    public void setSet(Set set) {
        this.set = set;
    }

    public Iterator getIt() {
        return it;
    }

    public void setIt(Iterator it) {
        this.it = it;
    }

    public StringBuffer getMyFriendsID() {
        return myFriendsID;
    }

    public StringBuffer getMyGroupsID() {
        return myGroupsID;
    }

    public void setUser() {
//        System.out.println(this.message.user.getName());
//        this.chatPersonName.setText(this.user.getName());
//        System.out.println(this.chatPersonName.getText());
    }

    public void connection() throws SQLException {
        this.message.connection();    // 连接服务器
        this.loadFriendList(0);      // 加载好友列表
        this.message.processSend("LOGIN");  // 给服务器发送上线通知
    }

    public ClientUI() {
        this.init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
//      // 加入好友
//      for (int i = 0; i < 10; i++) {
//          FriendModel person = new FriendModel();
//          personList.add(person.getPersonPanel());
//          person.getPersonPanel().setBounds(0, i * 70, 310, 70);
//          onlineListPanel.add(personList.get(i));
//          onlineListPanel.setLayout(null);
//      }
    }

    /**
     * @param user 登录用户的信息
     */
    public ClientUI(User user) {
        this.user = user;   // 登录的用户信息
        this.myFriendsID = new StringBuffer();
        this.myGroupsID = new StringBuffer();
        this.init();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
        this.userNameLabel.setText(user.getName());
        this.setVisible(true);
    }

    public void init() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginUI.class.getResource("/client/images/icon.png")));
        this.setUndecorated(true);
        // 圆角窗体
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(20.0D, 20.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
        this.validate();
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

        setTitle("ChatRoom");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1150, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel topPanel = new JPanel();
//		topPanel.setBackground(Color.PINK);
        topPanel.setBounds(0, 0, 1150, 70);
        contentPane.add(topPanel);
        topPanel.setLayout(null);

        zxhButton = new JButton("");
        zxhButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                setExtendedState(JFrame.ICONIFIED);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                zxhButton.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/zxh_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                zxhButton.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/zxh.png")));
            }
        });
        zxhButton.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/zxh.png")));
        zxhButton.setBounds(1050, 20, 32, 32);
        zxhButton.setBorder(null);
        zxhButton.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        zxhButton.setBackground(topButtonColor);
        topPanel.add(zxhButton);

        closeButton = new JButton("");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try {
                    message.sendClose("close");
                } catch (NullPointerException e1) {
                    e1.getMessage();
                } finally {
                    CloseUtil.closeAll(socket, dis, dos);
                    System.exit(0);
                }

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                closeButton.setBackground(Color.RED);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                closeButton.setBackground(topButtonColor);
            }
        });
        closeButton.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/close.png")));
        closeButton.setBounds(1100, 20, 32, 32);
        closeButton.setBorder(null);
        closeButton.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        closeButton.setBackground(topButtonColor);
        topPanel.add(closeButton);
        // 登录用户的用户名
        userNameLabel = new JLabel("当前登录用户的的昵称");
        userNameLabel.setForeground(Color.BLACK);
        userNameLabel.setFont(new Font("微软雅黑", Font.BOLD, 20));
        userNameLabel.setBounds(74, 20, 205, 30);
        topPanel.add(userNameLabel);

        JLabel topBgLabel = new JLabel("");
        topBgLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/topBg.jpg")));
        topBgLabel.setBounds(0, 0, 1150, 70);
        topPanel.add(topBgLabel);

        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 70, 1150, 680);
        bottomPanel.setLayout(null);
        contentPane.add(bottomPanel);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.MAGENTA);
        bottomLeftPanel.setBounds(0, 0, 310, 680);
        bottomPanel.add(bottomLeftPanel);
        bottomLeftPanel.setLayout(null);

        // 好友列表Top固定Bar
        JPanel botLeftTopBar = new JPanel();
        botLeftTopBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
        botLeftTopBar.setBounds(0, 0, 310, 45);
        botLeftTopBar.setBackground(BOTTOM_LEFT_BGCOLOR);
        botLeftTopBar.setLayout(null);
        bottomLeftPanel.add(botLeftTopBar);

        JLabel onlinTopBar_name = new JLabel("  好友列表");
        onlinTopBar_name.setForeground(FONT_COLOR);
        onlinTopBar_name.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/user_icon.png")));
        onlinTopBar_name.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        onlinTopBar_name.setBounds(30, 0, 146, 45);
        botLeftTopBar.add(onlinTopBar_name);
        bottomLeftPanel.add(botLeftTopBar);

        JButton refreshFriendsBtn = new JButton("");
        refreshFriendsBtn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                message.refreshFriendsSend();
            }
        });
        refreshFriendsBtn.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/refresh.png")));
        refreshFriendsBtn.setBounds(253, 7, 32, 32);
        refreshFriendsBtn.setBorder(null);
        refreshFriendsBtn.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        refreshFriendsBtn.setBackground(BOTTOM_LEFT_BGCOLOR);
        botLeftTopBar.add(refreshFriendsBtn);

        // 在线列表 滚动面板
        onlineListPanel = new JPanel();
//		onlineListPanel.setSize(310, 1000);
        onlineListPanel.setBackground(BOTTOM_LEFT_BGCOLOR);
        onlineListPanel.setPreferredSize(new Dimension(300, 1100));
//		bottomLeftPanel.add(onlineListPanel);		// 无效代码
        onlineListPanel.setLayout(null);

//        // 加入好友
//        for (int i = 0; i < 10; i++) {
//            FriendModel person = new FriendModel();
//            personList.add(person.getPersonPanel());
//            person.getPersonPanel().setBounds(0, i * 70, 310, 70);
//            onlineListPanel.add(personList.get(i));
//            onlineListPanel.setLayout(null);
//        }

//		for (int i = 0;i < 5;i++) {
//			JPanel onlinePerson = new JPanel();
//			onlinePerson.setBounds(0, i*210, 300, 200);
//			onlinePerson.setBackground(Color.DARK_GRAY);
//			onlineListPanel.add(onlinePerson);
//		}

        JScrollPane scrollPane = new JScrollPane(onlineListPanel);
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 45, 310, 575);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        scrollPane.setBorder(null);
        bottomLeftPanel.add(scrollPane);

        JPanel botLeftBottomPanel = new JPanel();
        botLeftBottomPanel.setBounds(0, 620, 310, 60);
        bottomLeftPanel.add(botLeftBottomPanel);
        botLeftBottomPanel.setLayout(null);

        JLabel addFriendLabel = new JLabel("");
        addFriendLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (addFriendUI == null) {
                    addFriendUI = new AddFriendUI(message);
                }
                addFriendUI.setLocationRelativeTo(clientUI);
                addFriendUI.setVisible(true);
                red_dot.setVisible(false);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                addFriendLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/add_friend_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                addFriendLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/add_friend.png")));
            }
        });
        
        addFriendLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/add_friend.png")));
        addFriendLabel.setBounds(250, 10, 40, 40);
        botLeftBottomPanel.add(addFriendLabel);

        red_dot = new JLabel("");
        red_dot.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/red_dot.png")));
        red_dot.setBounds(220, 5, 16, 16);
        red_dot.setVisible(false);
        botLeftBottomPanel.add(red_dot);

        JLabel inforLabel = new JLabel("");
        inforLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (chatShowInputUI != null) {
                	bottomPanel.remove(chatShowInputUI);
                }
                if (b_r_childPanel != null) {
                    bottomRightPanel.remove(b_r_childPanel);
                	b_r_childPanel = null;
                }
                if (bottomRightPanel.isVisible() == false) {
                	bottomRightPanel.setVisible(true);
                	bottomPanel.add(bottomRightPanel);
                }
                red_dot.setVisible(false);
                bottomPanel.updateUI();
                infor_Panel.setVisible(true);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
                inforLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/infor_1.png")));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
                inforLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/infor.png")));
            }
        });
        inforLabel.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/infor.png")));
        inforLabel.setBounds(186, 10, 40, 40);
        botLeftBottomPanel.add(inforLabel);

        bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.PINK);
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setBounds(310, 0, 840, 680);
        bottomPanel.add(bottomRightPanel);

        b_r_childPanel = new JPanel();
        b_r_childPanel.setBounds(0, 0, 840, 680);
        bottomRightPanel.add(b_r_childPanel);
        b_r_childPanel.setLayout(null);

        JLabel b_r_childTitel = new JLabel("欢迎使用多鱼聊天室");
        b_r_childTitel.setFont(new Font("微软雅黑", Font.BOLD, 22));
        b_r_childTitel.setBounds(335, 180, 203, 40);
        b_r_childPanel.add(b_r_childTitel);
        
        infor_Panel = new JPanel();
        infor_Panel.setBounds(0, 0, 840, 680);
        infor_Panel.setVisible(false);
        bottomRightPanel.add(infor_Panel);
        infor_Panel.setLayout(null);
        
        JLabel lblNewLabel = new JLabel("消息通知");
        lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        lblNewLabel.setBounds(20, 10, 90, 25);
        infor_Panel.add(lblNewLabel);
        
        inforInner_Panel = new JPanel();
        inforInner_Panel.setPreferredSize(new Dimension(840, 600));
        inforInner_Panel.setLayout(null);
        
        JScrollPane infor_scrollPane = new JScrollPane(inforInner_Panel);
        infor_scrollPane.setBounds(0, 45, 840, 635);
        infor_Panel.add(infor_scrollPane);
        infor_scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED);
        infor_scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        infor_scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        infor_scrollPane.setBorder(null);

//        chatShowInputUI = new ChatShowInputUI();
//        bottomPanel.add(chatShowInputUI);
    }

    /**
     * 加载好友
     */
    public void loadFriendList(int type) throws SQLException {
        ArrayList<Object> friends = userManager.getFriends(String.valueOf(this.user.getId()));
        if (friends == null) return;
//        System.out.println("hhh");
//        System.out.println(friends.size());
        for (Object object : friends) {
            // 实例化一个好友Panel
            if (object instanceof User) {
                User user = (User) object;
                String strID = String.valueOf(user.getId());
                String strName = user.getName();
                ChatShowInputUI csUI = new ChatShowInputUI(strID, strName, 0, message);     // 聊天界面
                FriendModel person = new FriendModel(strID, strName, 0, this, csUI);    // 左侧好友列表的好友
                person.setBounds(0, this.getPersonList().size() * 70, 310, 70);
                // 添加进好友Map集合
                this.personList.put(person, csUI);
                // 添加到主界面
                this.onlineListPanel.add(person);      // 好友面板添加进界面
                // 添加到myFriendsID
                myFriendsID.append(strID + ",");
            }
            // 实例化一个群组Panel
            if (object instanceof Group) {
                Group group = (Group) object;
                String strID = String.valueOf(group.getGroup_id());
                String strName = group.getGroup_name();
                ChatShowInputUI csUI = new ChatShowInputUI(strID, strName, 1, message);     // 聊天界面
                FriendModel person = new FriendModel(strID, strName, 1, this, csUI);    // 左侧好友列表的好友
                person.setBounds(0, this.getPersonList().size() * 70, 310, 70);
                // 添加进好友Map集合
                this.personList.put(person, csUI);
                // 添加到主界面
                this.onlineListPanel.add(person);      // 好友面板添加进界面
                // 添加到myFriendsID
                myGroupsID.append(strID + ",");
            }
        }
//        System.out.println(myFriendsID.toString());
    }

    /**
     * 同意以后加载进好友列表
     * @param id
     * @param type
     */
    public void addFriendToList(String id,int type) {
        if (type == 0) {
            User user = userManager.getUser(id);
            String strID = String.valueOf(user.getId());
            String strName = user.getName();
            ChatShowInputUI csUI = new ChatShowInputUI(strID, strName, 0, message);     // 聊天界面
            FriendModel person = new FriendModel(strID, strName, 0, this, csUI);    // 左侧好友列表的好友
            person.setBounds(0, this.getPersonList().size() * 70, 310, 70);
            // 添加进好友Map集合
            this.personList.put(person, csUI);
            // 添加到主界面
            this.onlineListPanel.add(person);      // 好友面板添加进界面
            // 添加到myFriendsID
            myFriendsID.append(strID + ",");
        }else if (type == 1) {
            Group group = userManager.getGroup(id);
            String strID = String.valueOf(group.getGroup_id());
            String strName = group.getGroup_name();
            ChatShowInputUI csUI = new ChatShowInputUI(strID, strName, 1, message);     // 聊天界面
            FriendModel person = new FriendModel(strID, strName, 1, this, csUI);    // 左侧好友列表的好友
            person.setBounds(0, this.getPersonList().size() * 70, 310, 70);
            // 添加进好友Map集合
            this.personList.put(person, csUI);
            // 添加到主界面
            this.onlineListPanel.add(person);      // 好友面板添加进界面
            // 添加到myFriendsID
            myGroupsID.append(strID + ",");
        }
    }

    /**
     * 创建消息通知
     * @param from_id
     * @param from_name
     * @param to_id
     * @param msg
     * @param type
     */
    public void createInforMessage(String from_id,String from_name,String to_id,String msg,int type) {
        InforModel inforModel = new InforModel(from_id,from_name,to_id,type);
        this.inforInner_Panel.add(inforModel);
        inforModel.setMessage(message);
        inforModel.getInfor_lbl().setText(msg);
        inforModel.setBounds(20,inforMsgNum * 70 +5,800,70);
        if (infor_Panel.isVisible() == false) {
            red_dot.setVisible(true);
        } else {
            red_dot.setVisible(false);
        }
        inforMsgNum++;
        this.inforMessageList.add(inforModel);
    }

    public void changeInforMessage() {

    }

    public static void main(String[] args) {
        ClientUI c = new ClientUI();
        c.setClientUI(c);
        c.setVisible(true);
    }
}
