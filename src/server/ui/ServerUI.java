package server.ui;

import server.service.UserManager;
import server.model.User;
import server.util.Message;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ServerUI {

    private JPanel contentPane;
    private JTextArea textArea = null;    // 信息展示区域
    private ConcurrentHashMap<String, Message> userMap = new ConcurrentHashMap<String, Message>();  // 所有在线的用户的id和Message对象
    private UserManager userManager = new UserManager();
    private Set set = null;
    private Iterator it = null;
    private ArrayList<String> userIDs = new ArrayList<>();  // 储存所有在线的用户id

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

    public ArrayList<String> getUserIDs() {
        return userIDs;
    }

    public void setUserIDs(ArrayList<String> userIDs) {
        this.userIDs = userIDs;
    }

    public void setShowPanel(String str) {
        this.textArea.append(str + "\n");
    }

    /**
     * Create the frame.
     */
    public ServerUI() {
//        init();
        this.set = userMap.keySet();
    }

    public void init() {
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setBounds(100, 100, 700, 450);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//        setContentPane(contentPane);
        contentPane.setLayout(null);

        textArea = new JTextArea();
        textArea.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 16));
        textArea.setLineWrap(true);
        textArea.setBounds(14, 40, 638, 350);

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setBounds(14, 40, 638, 350);
        contentPane.add(scrollPane);
    }

    public ConcurrentHashMap<String, Message> getUserMap() {
        return userMap;
    }

    public UserManager getUserManager() {
        return userManager;
    }

}
