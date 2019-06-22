package client.util;

import client.model.User;
import client.model.UserModel;
import client.ui.ChatShowInputUI;
import client.ui.ClientUI;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

public class Message implements Runnable {

    private Socket socket = null;
    private ClientUI clientUI = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;

    private boolean isOnlineStatus = false;
    private User user = null;
    private JTextArea showArea;
    private JTextArea inputArea;
    private ChatInstructionCode CODE = new ChatInstructionCode();
    private UserModel userModel = null;
    private ChatShowInputUI csUI = null;

    public User getUser() {
        return user;
    }

    public Message(Socket socket, ClientUI clientUI) {
        this.socket = socket;
        this.clientUI = clientUI;
        this.user = clientUI.getUser();
    }

    @Override
    public void run() {
        String str = null;
        try {
            while ((str = receive()) != null) {
                // 获取客户端消息的标识
                String messageFlag = str.substring(0, 3);
                String message = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBO) + 4);
                System.out.println(messageFlag + "~~~~~~" + message);
                if (messageFlag.equals(CODE.SERVER_ONLINE)) {     // 好友上线 标识19
                    // 截取用户ID和昵称
                    String strID = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBO) + 6,
                            str.indexOf("昵称")).trim();
                    String strName = str.substring(str.indexOf("昵称") + 2);
                    System.out.println(strID + "---" + strName);
                    isFriendOnlineStatus(strID, true);
                } else if (messageFlag.equals(CODE.SERVER_OFFLINE)) {    // 下线消息 标识10#
                    // 截取用户ID和昵称
                    String strID = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBO) + 6,
                            str.indexOf("昵称")).trim();
                    String strName = str.substring(str.indexOf("昵称") + 2);
                    System.out.println(strID + "---" + strName);
                    isFriendOnlineStatus(strID, false);
                } else if (messageFlag.equals(CODE.SERVER_PRIVATE_CHAT)) {    // 私聊消息 标识11#
                    // 截取接收消息的ID
                    System.out.println(str);
                    String from_id = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) +
                            CODE.CLIENT_FROM_ID.length(),str.indexOf(CODE.CLIENT_FROM_NAME));
                    String from_name = str.substring(str.indexOf(CODE.CLIENT_FROM_NAME) +
                            CODE.CLIENT_FROM_NAME.length(),str.indexOf(CODE.CLIENT_TO));
                    System.out.println("接收私聊消息：" + from_id + "---" + message);
                    message = from_name + " " + message;
                    this.showMessage(from_id,message);
                } else if (messageFlag.equals(CODE.SERVER_GROUP_CHAT)) {      // 群聊消息 标识12#
                    System.out.println("接收群聊消息：" + str);
                    String msg = str.substring(2);
//                    this.clientUI.setShowAreaText(msg);
                } else if (messageFlag.equals(CODE.SERVER_REFRESH_FRIENDS)) {   // 好友列表刷新消息 标识13#
                    refreshFriendsReceive(message);
                }
                System.out.println("客户端接收消息：" + str + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 与服务器建立连接
     */
    public void connection() {
        try {
            this.dis = new DataInputStream(socket.getInputStream());
            this.dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            CloseUtil.closeAll(socket, dis, dos);
        }
    }

    /**
     * 对需要发送的消息加工
     * 发送上线提醒   上线标识：9#  CODE.CLIENT_ONLINE
     * 发送下线消息   下线标识：0#  CODE.CLIENT_OFFLINE
     * 发送私聊消息   私聊标识  1#  CODE.CLIENT_PRIVATE_CHAT
     * 发送群聊消息   群聊标识  2#  CODE.CLIENT_GROUP_CHAT
     * @param str
     */
    public void processSend(String str) {
        // 判断登录者用户的上线状态，当上线时给客户端发送上线提醒 标识:9
        if (isOnlineStatus == false && str.equals("LOGIN")) {
            try {
                dos.writeUTF(CODE.CLIENT_ONLINE + CODE.MESSAGE_SPLIT_SYMBO + "ID：" + this.clientUI.getUser().getId() +
                        " 昵称：" + this.clientUI.getUser().getName());
                // 刷新当前在线列表
                refreshFriendsSend();
                isOnlineStatus = true;
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (str.startsWith(CODE.CLIENT_PRIVATE_CHAT) || str.startsWith(CODE.CLIENT_GROUP_CHAT)) {
            // 截取id
            String strId = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) + CODE.CLIENT_FROM_ID.length(),
                    str.indexOf(CODE.CLIENT_TO));
            // 将消息截取出来
            String msg = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBO) + 4);
        }
        if (str.startsWith(CODE.CLIENT_PRIVATE_CHAT)) {      // 如果客户端发送的消息开头为1#，处理为私聊消息
            this.send(str);
        } else if (str.startsWith(CODE.CLIENT_GROUP_CHAT)) {    // 2#开头，处理群聊消息

        }
    }

    /**
     * 发送消息
     * @param msg
     */
    public void send(String msg) {
        try {
            this.dos.writeUTF(msg);
            this.dos.flush();
        } catch (IOException e) {
            e.printStackTrace();
            CloseUtil.closeAll(dis, dos);
        }
    }

    /**
     * 接收消息
     *
     * @throws IOException
     */
    public String receive() throws IOException {
        String str = null;
        str = dis.readUTF();
        System.out.println("接收消息" + str);
        return str;
    }

    /**
     * 发送下线提醒   下线标识：0# CODE.CLIENT_OFFLINE
     *
     * @param close
     */
    public void sendClose(String close) {
        if (close.equals("close")) {
            try {
                dos.writeUTF(CODE.CLIENT_OFFLINE + CODE.MESSAGE_SPLIT_SYMBO + "ID：" + this.clientUI.getUser().getId() +
                        " 昵称：" + this.clientUI.getUser().getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 改变好友的在线状态
     *
     * @param strID
     * @param status true为在线，false为离线
     */
    public void isFriendOnlineStatus(String strID, boolean status) {
        //
//        String strID = str.substring(2, str.indexOf("昵称")).trim();    // 获取ID
//        System.out.println(user.getId());
        // 如果获取的id与登录的id相符合，结束
        if (strID.equals(String.valueOf(user.getId()))) {
//            System.out.println("yes");
            return;
        }
//        String strName = str.substring(str.indexOf("昵称") + 2).trim();    // 获取昵称
//        System.out.println("id" + strID);     // 验证数据
//        System.out.println("昵称" + strName);   // 验证数据
        // 寻找好友列表中id相匹配的
        Set set = clientUI.getPersonList().keySet();
        Iterator it = set.iterator();
//        System.out.println("寻找匹配------------------------------------------" + clientUI.getPersonList().size());
        while (it.hasNext()) {
            UserModel userModel = (UserModel) it.next();
//            System.out.println(userModel.getGroup_id() + "###########");
            // 当id相同时
            if (userModel.getId().equals(strID)) {
                String strStatus = status == true ? "上线" : "离线";
                userModel.getOnlineStatus().setText(strStatus);
//                System.out.println(strStatus);
            }
        }
//        ChatShowInputUI csUI = new ChatShowInputUI(strID,strName,this);     // 聊天界面
//        UserModel person = new UserModel(strID,strName,clientUI,csUI);    // 左侧在线列表
//        person.setBounds(0, clientUI.getPersonList().size() * 70, 310, 70);
//        // 添加进好友Map集合
//        clientUI.getPersonList().put(person,csUI);
//        // 遍历Map集合，在好友列表显示
//        UserModel userModel = null;
//        Set set = clientUI.getPersonList().keySet();   //
//        Iterator it = set.iterator();
//        while (it.hasNext() == false) {
//            UserModel jPanel = (UserModel) it.next();
////            System.out.println(strID+" !! "+jPanel.getGroup_id());     // 数据验证
//            // 当服务器传来的id与放进列表panel的id相同
//            if (jPanel.getGroup_id().equals(strID)) {
//                userModel = jPanel;
//                clientUI.getOnlineListPanel().add(userModel);
//                clientUI.getOnlineListPanel().setVisible(false);
//                clientUI.getOnlineListPanel().setVisible(true);
//            }
//            System.out.println("好友列表添加成功");
//        }
//        clientUI.getOnlineListPanel().setLayout(null);
    }

    /**
     * 发送刷新好友列表的请求
     */
    public void refreshFriendsSend() {
        //
        String str = clientUI.getMyFriendsID().toString();
        try {
            dos.writeUTF(CODE.CLIENT_REFRESH_FRIENDS + CODE.CLIENT_FROM_ID +
                    user.getId() + CODE.CLIENT_TO + user.getId() + CODE.MESSAGE_SPLIT_SYMBO + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * 接收服务端返回的刷新好友列表
     * @param str
     */
    public void refreshFriendsReceive(String str) {
        if (str.equals("null")) return;     // 无需处理
        String[] arr = str.split(",");
        System.out.println(str + "          返回的刷新好友列表");
        for (int i = 0;i < arr.length;i++) {
            isFriendOnlineStatus(arr[i],true);
        }
    }

    /**
     * 将接收的消息显示到对应的聊天界面
     * @param from
     * @param msg
     */
    public void showMessage(String from,String msg) {
        clientUI.setSet(clientUI.getPersonList().keySet());
        clientUI.setIt(clientUI.getSet().iterator());
        while (clientUI.getIt().hasNext()) {
            userModel = (UserModel) clientUI.getIt().next();
            csUI = clientUI.getPersonList().get(userModel);
            if (csUI.getStr_Id().equals(from)) {
//                String userName = from.substring();
                // 在对应的好友块上显示小红点
                if (csUI.isVisible() == false) {
                    userModel.getRed_dot().setVisible(true);
                }
                // 在聊天界面的显示区域显示内容
                csUI.setShowAreaText(msg);
            }
        }
    }

    /**
     * @param user
     * @param shouArea
     * @param inputArea
     */
    public void addElements(User user, JTextArea shouArea, JTextArea inputArea) {
        this.user = user;
        this.showArea = shouArea;
        this.inputArea = inputArea;
    }
}
