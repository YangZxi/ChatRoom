/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: ClientUI
 * Author:   OuYoung
 * Date:     2019/06/22 22:19
 * Description: 客户端聊天界面的主视图
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung       2019/06/23 13:50       版本号             描述
 */
package client.ui;

import client.util.ChatInstructionCode;
import client.util.Message;
import org.omg.CORBA.PRIVATE_MEMBER;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 〈一句话功能简述〉
 * 〈客户端聊天界面的主视图〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.2.0
 */

public class ChatShowInputUI extends JPanel {

    private Color BOTTOM_RIGHT_BGCOLOR = new Color(242, 242, 242);
    private JTextArea inputArea;
    private JTextArea showArea;
    private JLabel chatPersonName;

    private String str_Id = null;   // 接收消息的用户id or 群id
    private String str_name = null; // 接收消息的用户名称 or 群名称
    private Message message = null;
    private ChatInstructionCode CODE = new ChatInstructionCode();

    public JTextArea getInputArea() {
        return inputArea;
    }

    public JTextArea getShowArea() {
        return showArea;
    }

    public String getStr_Id() {
        return str_Id;
    }

    public String getStr_Name() {
        return str_name;
    }

    public ChatShowInputUI() {
        singleUI();
    }

    public ChatShowInputUI(String str_Id, String str_name,int type ,Message message) {
        this.str_Id = str_Id;
        this.str_name = str_name;
        this.message = message;
        if (type == 0) {
            singleUI();
        }else if (type == 1) {
            groupUI();
        }
    }

    public void singleUI() {
        // 聊天界面Panel
//        JPanel bottomRightPanel = new JPanel();
//        bottomRightPanel.setBackground(Color.ORANGE);
//        bottomRightPanel.setLayout(null);
//        bottomRightPanel.setBounds(310, 0, 809, 680);

        this.setBackground(Color.ORANGE);
        this.setLayout(null);
        this.setBounds(310, 0, 840, 680);
        this.setVisible(false);
        // 聊天界面TopBar
        JPanel chatTopPanel = new JPanel();
        chatTopPanel.setBounds(0, 0, 840, 45);
        chatTopPanel.setBackground(BOTTOM_RIGHT_BGCOLOR);
        chatTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
        this.add(chatTopPanel);
        chatTopPanel.setLayout(null);

        // 聊天界面联系人名称
        chatPersonName = new JLabel("测试");
        chatPersonName.setText(this.str_name);
        chatPersonName.setFont(new Font("微软雅黑", Font.BOLD, 20));
        chatPersonName.setBounds(14, 0, 200, 45);
        chatTopPanel.add(chatPersonName);

        // 聊天界面面板
        JPanel chatAreaPanel = new JPanel();
        chatAreaPanel.setBackground(BOTTOM_RIGHT_BGCOLOR);
        chatAreaPanel.setBounds(0, 45, 840, 635);
        this.add(chatAreaPanel);
        chatAreaPanel.setLayout(null);

        // 聊天记录面板
        // 滚动
        JScrollPane showAreasScrollPane = new JScrollPane();
        showAreasScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        showAreasScrollPane.setBounds(10, 5, 820, 405);
        showAreasScrollPane.setBorder(null);
        chatAreaPanel.add(showAreasScrollPane);
        // 显示框
        showArea = new JTextArea();
        showArea.setLineWrap(true);
        showArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        showArea.setBackground(BOTTOM_RIGHT_BGCOLOR);
        showArea.setEditable(false);
        showAreasScrollPane.setViewportView(showArea);

        // 聊天工具Bar
        JPanel chatToolPanel = new JPanel();
        chatToolPanel.setBounds(0, 415, 840, 35);
        chatToolPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(179, 179, 179)));
        chatAreaPanel.add(chatToolPanel);
        chatToolPanel.setLayout(null);
        
        JPanel chatToolLeftPanel = new JPanel();
        chatToolLeftPanel.setBounds(10, 6, 130, 24);
        chatToolPanel.add(chatToolLeftPanel);
        chatToolLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 1));
        
        JLabel emoji_label = new JLabel("");
        emoji_label.setIcon(new ImageIcon(ChatShowInputUI.class.getResource("/client/images/emoji.png")));
        chatToolLeftPanel.add(emoji_label);
        
        JLabel screenshot_label = new JLabel("");
        screenshot_label.setIcon(new ImageIcon(ChatShowInputUI.class.getResource("/client/images/screenshot.png")));
        chatToolLeftPanel.add(screenshot_label);
        
        JLabel file_label = new JLabel("");
        file_label.setIcon(new ImageIcon(ChatShowInputUI.class.getResource("/client/images/file.png")));
        chatToolLeftPanel.add(file_label);

        // 聊天输入面板
        // 滚动
        JScrollPane inputScrollPane = new JScrollPane();
        inputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        inputScrollPane.setBounds(10, 460, 820, 120);
        inputScrollPane.setBorder(null);
        inputArea = new JTextArea();
        inputArea.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyPressed(e);
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    getInputAreaText(0);
                }
            }
        });
        inputArea.setLineWrap(true);
        inputArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        inputArea.setBackground(BOTTOM_RIGHT_BGCOLOR);
        int height = 20000;
        Point p = new Point();
        p.setLocation(0, inputArea.getLineCount() * height);
        inputScrollPane.getViewport().setViewPosition(p);
        chatAreaPanel.add(inputScrollPane);
        inputScrollPane.setViewportView(inputArea);

        // 消息发送按钮
        JButton sendButton = new JButton("发 送");
        sendButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                getInputAreaText(0);
            }
        });
        sendButton.setForeground(Color.WHITE);
        sendButton.setBackground(new Color(105, 155, 178));
        sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
        sendButton.setBounds(750, 590, 70, 35);
        chatAreaPanel.add(sendButton);
    }

    public void groupUI() {
        // 聊天界面Panel
//      JPanel bottomRightPanel = new JPanel();
//      bottomRightPanel.setBackground(Color.ORANGE);
//      bottomRightPanel.setLayout(null);
//      bottomRightPanel.setBounds(310, 0, 809, 680);

      this.setBackground(Color.ORANGE);
      this.setLayout(null);
      this.setBounds(310, 0, 840, 680);
      this.setVisible(false);
      // 聊天界面TopBar
      JPanel chatTopPanel = new JPanel();
      chatTopPanel.setBounds(0, 0, 840, 45);
      chatTopPanel.setBackground(BOTTOM_RIGHT_BGCOLOR);
      chatTopPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
      this.add(chatTopPanel);
      chatTopPanel.setLayout(null);

      // 聊天界面联系人名称
      chatPersonName = new JLabel("测试");
      chatPersonName.setText(this.str_name);
      chatPersonName.setFont(new Font("微软雅黑", Font.BOLD, 20));
      chatPersonName.setBounds(14, 0, 200, 45);
      chatTopPanel.add(chatPersonName);

      // 聊天界面面板
      JPanel chatAreaPanel = new JPanel();
      chatAreaPanel.setBackground(BOTTOM_RIGHT_BGCOLOR);
      chatAreaPanel.setBounds(0, 45, 670, 635);
      this.add(chatAreaPanel);
      chatAreaPanel.setLayout(null);

      // 聊天记录面板
      // 滚动
      JScrollPane showAreasScrollPane = new JScrollPane();
      showAreasScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      showAreasScrollPane.setBounds(10, 5, 650, 405);
      showAreasScrollPane.setBorder(null);
      chatAreaPanel.add(showAreasScrollPane);
      // 显示框
      showArea = new JTextArea();
      showArea.setLineWrap(true);
      showArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
      showArea.setBackground(BOTTOM_RIGHT_BGCOLOR);
      showArea.setEditable(false);
      showAreasScrollPane.setViewportView(showArea);

      // 聊天工具Bar
      JPanel chatToolPanel = new JPanel();
      chatToolPanel.setBounds(0, 415, 670, 35);
      chatToolPanel.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(179, 179, 179)));
      chatAreaPanel.add(chatToolPanel);
      chatToolPanel.setLayout(null);
      
      JPanel chatToolLeftPanel = new JPanel();
      chatToolLeftPanel.setBounds(10, 6, 130, 24);
      chatToolPanel.add(chatToolLeftPanel);
      chatToolLeftPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 1));
      
      JLabel emoji_label = new JLabel("");
      emoji_label.setIcon(new ImageIcon(ChatShowInputUI.class.getResource("/client/images/emoji.png")));
      chatToolLeftPanel.add(emoji_label);
      
      JLabel screenshot_label = new JLabel("");
      screenshot_label.setIcon(new ImageIcon(ChatShowInputUI.class.getResource("/client/images/screenshot.png")));
      chatToolLeftPanel.add(screenshot_label);
      
      JLabel file_label = new JLabel("");
      file_label.setIcon(new ImageIcon(ChatShowInputUI.class.getResource("/client/images/file.png")));
      chatToolLeftPanel.add(file_label);

      // 聊天输入面板
      // 滚动
      JScrollPane inputScrollPane = new JScrollPane();
      inputScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      inputScrollPane.setBounds(10, 460, 650, 120);
      inputScrollPane.setBorder(null);
      inputArea = new JTextArea();
      inputArea.addKeyListener(new KeyAdapter() {
          @Override
          public void keyReleased(KeyEvent e) {
              super.keyPressed(e);
              if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                  getInputAreaText(1);
              }
          }
      });
      inputArea.setLineWrap(true);
      inputArea.setFont(new Font("微软雅黑", Font.PLAIN, 16));
      inputArea.setBackground(BOTTOM_RIGHT_BGCOLOR);
      int height = 20000;
      Point p = new Point();
      p.setLocation(0, inputArea.getLineCount() * height);
      inputScrollPane.getViewport().setViewPosition(p);
      chatAreaPanel.add(inputScrollPane);
      inputScrollPane.setViewportView(inputArea);

      // 消息发送按钮
      JButton sendButton = new JButton("发 送");
      sendButton.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent arg0) {
              getInputAreaText(1);
          }
      });
      sendButton.setForeground(Color.WHITE);
      sendButton.setBackground(new Color(105, 155, 178));
      sendButton.setFont(new Font("微软雅黑", Font.PLAIN, 14));
      sendButton.setBounds(590, 590, 70, 35);
      chatAreaPanel.add(sendButton);
      
      JPanel groupInformationPanel = new JPanel();
      groupInformationPanel.setBounds(670, 45, 170, 635);
      groupInformationPanel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, new Color(179, 179, 179)));
      add(groupInformationPanel);
      groupInformationPanel.setLayout(null);
      
      JPanel informationPanel = new JPanel();
      informationPanel.setBounds(5, 5, 160, 200);
      groupInformationPanel.add(informationPanel);
      informationPanel.setLayout(null);
      
      JLabel informationTitle = new JLabel("暂时没有新通知");
      informationTitle.setHorizontalAlignment(SwingConstants.CENTER);
      informationTitle.setFont(new Font("微软雅黑", Font.PLAIN, 16));
      informationTitle.setBounds(5, 160, 150, 18);
      informationPanel.add(informationTitle);
      
      JPanel groupFriendsPanel = new JPanel();
      groupFriendsPanel.setBounds(0, 205, 170, 425);
      groupFriendsPanel.setBorder(BorderFactory.createMatteBorder(1, 1, 0, 0, new Color(179, 179, 179)));
      groupInformationPanel.add(groupFriendsPanel);
      groupFriendsPanel.setLayout(null);
      
      JLabel lblNewLabel = new JLabel("成员 (1000/2000)");
      lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
      lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 16));
      lblNewLabel.setBounds(5, 0, 160, 25);
      groupFriendsPanel.add(lblNewLabel);
      
      // 内部好友列表
      JPanel groupFriendsPanel_inner = new JPanel();
      groupFriendsPanel_inner.setPreferredSize(new Dimension(160, 100));
      groupFriendsPanel_inner.setLayout(null);
      
      JScrollPane groupFriendsScrollPane = new JScrollPane(groupFriendsPanel_inner);
      groupFriendsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
      groupFriendsScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
      groupFriendsScrollPane.setBounds(5, 30, 160, 390);
      groupFriendsScrollPane.setBorder(null);
      groupFriendsPanel.add(groupFriendsScrollPane);
  }

    /**
     * 仅提取消息
     * 在语句的开头加上接收消息的用户id
     * 格式   [CODE]<FROM:>ID<TO>ID[MSG:]輸入内容
     */
    public void getInputAreaText(int type) {
        String str = inputArea.getText();
//        System.out.println(str_Id + "@"  + str);
        System.out.println(CODE.CLIENT_FROM_ID + message.getUser().getId() +
                CODE.CLIENT_TO + str_Id + CODE.MESSAGE_SPLIT_SYMBO + str);
        if (type == 0) {    // 私聊
            this.message.processSend(CODE.CLIENT_PRIVATE_CHAT + CODE.CLIENT_FROM_ID + message.getUser().getId() +
                    CODE.CLIENT_FROM_NAME + message.getUser().getName() + CODE.CLIENT_TO + str_Id +
                    CODE.MESSAGE_SPLIT_SYMBO + str); // 发送消息
        }else if (type == 1) {  // 群聊
            this.message.processSend(CODE.CLIENT_GROUP_CHAT + CODE.CLIENT_FROM_ID + message.getUser().getId() +
                    CODE.CLIENT_FROM_NAME + message.getUser().getName() + CODE.CLIENT_TO + str_Id +
                    CODE.MESSAGE_SPLIT_SYMBO + str); // 发送消息
        }
        this.setShowAreaText(message.getUser().getName() + " " + getTime() + "\n" + str);
        inputArea.setText(null);
    }

    /**
     * 將从服务器接收的消息显示出来
     * @param str
     */
    public void setShowAreaText(String str) {
        this.showArea.append(str + "\n");
    }

    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

    public static void main(String[] args) {
        ChatShowInputUI c = new ChatShowInputUI();
        JFrame jf = new JFrame();
        jf.setBounds(100,100,850,720);
        jf.getContentPane().add(c);
        jf.setVisible(true);
        c.setVisible(true);
    }
}
