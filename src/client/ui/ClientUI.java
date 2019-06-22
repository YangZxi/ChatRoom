package client.ui;

import client.model.User;
import client.model.UserModel;

import client.service.UserManager;
import client.util.CloseUtil;
import client.util.Message;
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
import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Iterator;
import java.util.Set;

public class ClientUI extends JFrame {

    private int mouseAtX = 0;    // 鼠标x轴
    private int mouseAtY = 0;    // 鼠标y轴

    private Color topButtonColor = new Color(66, 73, 153);
    private Color FONT_COLOR = new Color(51, 51, 51);
    private Color BOTTOM_LEFT_BGCOLOR = new Color(238, 238, 238);
    private Color BOTTOM_RIGHT_BGCOLOR = new Color(242, 242, 242);

    private JPanel PERSONPANEL_CLICK = null;
    private JPanel PERSONPANEL_HOVER = null;

    private Message message = null;
    private Socket socket = null;
    private User user = null;       // 登录的用户信息
    private boolean onlineStatus = false;   // 上线状态
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private StringBuffer myFriendsID = null;
    //    private ArrayList<JPanel> personList = new ArrayList<JPanel>();
    private HashMap<UserModel, ChatShowInputUI> personList = new HashMap<UserModel, ChatShowInputUI>();
    private Set set = null;
    private Iterator it = null;

    private JPanel contentPane;
    private JButton closeButton;
    private JButton zxhButton;
    private JPanel onlineListPanel;
    private JPanel bottomPanel;
    private JPanel bottomRightPanel;
    private ChatShowInputUI chatShowInputUI;
    private JLabel userNameLabel;

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

    public HashMap<UserModel, ChatShowInputUI> getPersonList() {
        return personList;
    }

    public void setPersonList(HashMap<UserModel, ChatShowInputUI> personList) {
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

    public void setMyFriendsID(StringBuffer myFriendsID) {
        this.myFriendsID = myFriendsID;
    }

    public void setUser() {
//        System.out.println(this.message.user.getName());
//        this.chatPersonName.setText(this.user.getName());
//        System.out.println(this.chatPersonName.getText());
    }

    public void connection() throws SQLException {
        this.message.connection();    // 连接服务器
        this.loadFriendList();      // 加载好友列表
        this.message.processSend("LOGIN");  // 给服务器发送上线通知
    }

    public ClientUI() {
        this.init();
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
//      // 加入好友
//      for (int i = 0; i < 10; i++) {
//          UserModel person = new UserModel();
//          personList.add(person.getPersonPanel());
//          person.getPersonPanel().setBounds(0, i * 70, 310, 70);
//          onlineListPanel.add(personList.get(i));
//          onlineListPanel.setLayout(null);
//      }
    }

    /**
     * Create the frame.
     */
    public ClientUI(User user) {
        this.user = user;   // 登录的用户信息
        this.myFriendsID = new StringBuffer();
        this.init();
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(0.0D, 0.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
        this.userNameLabel.setText(user.getName());
        this.setVisible(true);
    }

    public void init() {
        this.setUndecorated(true);
        this.setLocationRelativeTo(null);
        // 圆角窗体
        AWTUtilities.setWindowShape(this,
                new RoundRectangle2D.Double(20.0D, 20.0D, this.getWidth(), this.getHeight(), 16.0D, 16.0D));
        this.validate();
//		setIconImage(Toolkit.getDefaultToolkit().getImage(ClientUI.class.getResource("/LoginView/images/icon.png")));
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
        setBounds(100, 100, 1100, 750);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JPanel topPanel = new JPanel();
//		topPanel.setBackground(Color.PINK);
        topPanel.setBounds(0, 0, 1100, 70);
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
        zxhButton.setBounds(1000, 20, 32, 32);
        zxhButton.setBorder(null);
        zxhButton.setFocusPainted(false); // 去除点击后的文字虚线边框(焦点边框)
        zxhButton.setBackground(topButtonColor);
        topPanel.add(zxhButton);

        closeButton = new JButton("");
        closeButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                message.sendClose("close");
                CloseUtil.closeAll(socket, dis, dos);
                System.exit(0);
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
        closeButton.setBounds(1050, 20, 32, 32);
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
        topBgLabel.setBounds(0, 0, 1100, 70);
        topPanel.add(topBgLabel);

        bottomPanel = new JPanel();
        bottomPanel.setBounds(0, 70, 1100, 680);
        bottomPanel.setLayout(null);
        contentPane.add(bottomPanel);

        JPanel bottomLeftPanel = new JPanel();
        bottomLeftPanel.setBackground(Color.MAGENTA);
        bottomLeftPanel.setBounds(0, 0, 310, 680);
        bottomPanel.add(bottomLeftPanel);
        bottomLeftPanel.setLayout(null);

        // 好友列表Top固定Bar
        JPanel onlineTopBar = new JPanel();
        onlineTopBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
        onlineTopBar.setBounds(0, 0, 310, 45);
        onlineTopBar.setBackground(BOTTOM_LEFT_BGCOLOR);
        onlineTopBar.setLayout(null);
        bottomLeftPanel.add(onlineTopBar);

        JLabel onlinTopBar_name = new JLabel("  好友列表");
        onlinTopBar_name.setForeground(FONT_COLOR);
        onlinTopBar_name.setIcon(new ImageIcon(ClientUI.class.getResource("/client/images/user_icon.png")));
        onlinTopBar_name.setFont(new Font("微软雅黑", Font.PLAIN, 22));
        onlinTopBar_name.setBounds(30, 0, 146, 45);
        onlineTopBar.add(onlinTopBar_name);
        bottomLeftPanel.add(onlineTopBar);

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
        onlineTopBar.add(refreshFriendsBtn);

        // 在线列表 滚动面板
        onlineListPanel = new JPanel();
//		onlineListPanel.setSize(310, 1000);
        onlineListPanel.setBackground(BOTTOM_LEFT_BGCOLOR);
        onlineListPanel.setPreferredSize(new Dimension(300, 1100));
//		bottomLeftPanel.add(onlineListPanel);		// 无效代码
        onlineListPanel.setLayout(null);

//        // 加入好友
//        for (int i = 0; i < 10; i++) {
//            UserModel person = new UserModel();
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
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(0, 45, 310, 635);
        scrollPane.setBorder(null);
        bottomLeftPanel.add(scrollPane);

        bottomRightPanel = new JPanel();
        bottomRightPanel.setBackground(Color.PINK);
        bottomRightPanel.setLayout(null);
        bottomRightPanel.setBounds(310, 0, 809, 680);
        bottomPanel.add(bottomRightPanel);

//        chatShowInputUI = new ChatShowInputUI();
//        bottomPanel.add(chatShowInputUI);
    }

    /**
     * 加载好友
     */
    public void loadFriendList() throws SQLException {
        UserManager userManager = new UserManager();
        ArrayList<User> friends = userManager.getFriends(String.valueOf(this.user.getId()));
//        System.out.println("hhh");
//        System.out.println(friends.size());
        for (int i = 0;i < friends.size();i++) {
            // 实例化一个好友Panel
            String strID =  String.valueOf(friends.get(i).getId());
            String strName = friends.get(i).getName();
//            System.out.println("id" + strID);     // 验证数据
//            System.out.println("昵称" + strName);   // 验证数据
            ChatShowInputUI csUI = new ChatShowInputUI(strID, strName, message);     // 聊天界面
            UserModel person = new UserModel(strID, strName,this,csUI);    // 左侧好友列表的好友
            person.setBounds(0, this.getPersonList().size() * 70, 310, 70);
            // 添加进好友Map集合
            this.personList.put(person, csUI);
            // 添加到主界面
            this.onlineListPanel.add(person);      // 好友面板添加进界面
//            this.getBottomPanel().add(csUI);        // 聊天面板添加进界面
            // 添加到myFriendsID
            myFriendsID.append(strID + ",");
        }
//        System.out.println(myFriendsID.toString());
    }

    public static void main(String[] args) {
        ClientUI c = new ClientUI();
        c.setVisible(true);
    }
}
