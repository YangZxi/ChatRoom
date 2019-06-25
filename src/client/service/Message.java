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
package client.service;

/**
 * 〈一句话功能简述〉
 * 〈客户端的对消息的处理，包括发送和接收〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */

import client.model.FriendModel;
import client.model.User;
import client.ui.ChatShowInputUI;
import client.ui.ClientUI;
import client.util.ChatInstructionCode;
import client.util.CloseUtil;

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
    private User user = null;   // 当前登录的用户
    private JTextArea showArea;
    private JTextArea inputArea;
    private ChatInstructionCode CODE = new ChatInstructionCode();
    private FriendModel friendModel = null;
    private ChatShowInputUI csUI = null;

    public User getUser() {
        return user;
    }

    public Message() {
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
                String message = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL) + 4);
                System.out.println(messageFlag + "~~~~~~" + message);
                if (messageFlag.equals(CODE.SERVER_ONLINE)) {     // 好友上线 标识19
                    // 截取用户ID和昵称
                    String strID = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL) + 6,
                            str.indexOf("昵称")).trim();
                    String strName = str.substring(str.indexOf("昵称") + 2);
                    System.out.println(strID + "---" + strName);
                    isFriendOnlineStatus(strID, true);
                } else if (messageFlag.equals(CODE.SERVER_OFFLINE)) {    // 下线消息 标识10#
                    // 截取用户ID和昵称
                    String strID = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL) + 6,
                            str.indexOf("昵称")).trim();
                    String strName = str.substring(str.indexOf("昵称") + 2);
                    System.out.println(strID + "---" + strName);
                    isFriendOnlineStatus(strID, false);
                } else if (messageFlag.equals(CODE.SERVER_SINGLE_CHAT)) {    // 私聊消息 标识11#
                    // 截取发送消息的ID
                    String from_id = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) +
                            CODE.CLIENT_FROM_ID.length(), str.indexOf(CODE.CLIENT_FROM_NAME));
                    // 截取发送消息的名称
                    String from_name = str.substring(str.indexOf(CODE.CLIENT_FROM_NAME) +
                            CODE.CLIENT_FROM_NAME.length(), str.indexOf(CODE.CLIENT_TO_ID));
                    System.out.println("接收私聊消息：" + from_id + "---" + message);
                    // 消息将会显示；发送人昵称 时间 和消息内容
                    message = from_name + " " + message;
                    this.showMessage(from_id, message);
                } else if (messageFlag.equals(CODE.SERVER_GROUP_CHAT)) {      // 群聊消息 标识12#
                    // 截取接收(这个在客户端代表自己的id）消息的ID
                    String to_id = str.substring(str.indexOf(CODE.CLIENT_TO_ID) +
                            CODE.CLIENT_TO_ID.length(), str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL));
                    // 截取发送消息的名称
                    String from_name = str.substring(str.indexOf(CODE.CLIENT_FROM_NAME) +
                            CODE.CLIENT_FROM_NAME.length(), str.indexOf(CODE.CLIENT_TO_ID));
                    System.out.println("接收群聊消息：" + to_id + "---" + message);
                    // 消息将会显示；发送人昵称 时间 和消息内容
                    message = from_name + " " + message;
                    this.showMessage(to_id, message);
//                    this.clientUI.setShowAreaText(msg);
                } else if (messageFlag.equals(CODE.SERVER_REFRESH_FRIENDS)) {   // 好友列表刷新消息 标识13#
                    refreshFriendsReceive(message);
                } else if (messageFlag.equals(CODE.SERVER_ADD_FRIEND)) {    // 加好友请求 标识14#
                    // 截取发送消息的ID
                    String from_id = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) +
                            CODE.CLIENT_FROM_ID.length(), str.indexOf(CODE.CLIENT_FROM_NAME));
                    // 截取发送消息的名称
                    String from_name = str.substring(str.indexOf(CODE.CLIENT_FROM_NAME) +
                            CODE.CLIENT_FROM_NAME.length(), str.indexOf(CODE.CLIENT_TO_ID));
                    this.addFriendOrGroupReceive(from_id, from_name, 0);
                } else if (messageFlag.equals(CODE.SERVER_ADD_GROUP)) {     // 加群请求 标识15#
                    // 截取发送消息的ID
                    String from_id = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) +
                            CODE.CLIENT_FROM_ID.length(), str.indexOf(CODE.CLIENT_FROM_NAME));
                    // 截取发送消息的名称
                    String from_name = str.substring(str.indexOf(CODE.CLIENT_FROM_NAME) +
                            CODE.CLIENT_FROM_NAME.length(), str.indexOf(CODE.CLIENT_TO_ID));
                    this.addFriendOrGroupReceive(from_id, from_name, 1);
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
     * 发送私聊消息   私聊标识  1#  CODE.CLIENT_SINGLE_CHAT
     * 发送群聊消息   群聊标识  2#  CODE.CLIENT_GROUP_CHAT
     *
     * @param str
     */
    public void processSend(String str) {
        // 判断登录者用户的上线状态，当上线时给客户端发送上线提醒 标识:9
        if (isOnlineStatus == false && str.equals("LOGIN")) {
            try {
                dos.writeUTF(CODE.CLIENT_ONLINE + CODE.MESSAGE_SPLIT_SYMBOL + "ID：" + this.clientUI.getUser().getId() +
                        " 昵称：" + this.clientUI.getUser().getName());
                // 刷新当前在线列表
                refreshFriendsSend();
                isOnlineStatus = true;
                return;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 好像没用
        if (str.startsWith(CODE.CLIENT_SINGLE_CHAT) || str.startsWith(CODE.CLIENT_GROUP_CHAT)) {
            // 截取id
            String strId = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) + CODE.CLIENT_FROM_ID.length(),
                    str.indexOf(CODE.CLIENT_TO_ID));
            // 将消息截取出来
            String msg = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL) + 4);
        }
        // 没用 end
        if (str.startsWith(CODE.CLIENT_SINGLE_CHAT)) {      // 如果客户端发送的消息开头为1#，处理为私聊消息
            this.send(str);
        } else if (str.startsWith(CODE.CLIENT_GROUP_CHAT)) {    // 2#开头，处理群聊消息
            this.send(str);
        }
    }

    /**
     * 发送消息
     *
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
                dos.writeUTF(CODE.CLIENT_OFFLINE + CODE.MESSAGE_SPLIT_SYMBOL + "ID：" + this.clientUI.getUser().getId() +
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
            FriendModel friendModel = (FriendModel) it.next();
//            System.out.println(friendModel.getGroup_id() + "###########");
            // 当id相同时
            if (friendModel.getId().equals(strID)) {
                String strStatus = status == true ? "上线" : "离线";
                friendModel.getOnlineStatus().setText(strStatus);
//                System.out.println(strStatus);
            }
        }
//        ChatShowInputUI csUI = new ChatShowInputUI(strID,strName,this);     // 聊天界面
//        FriendModel person = new FriendModel(strID,strName,clientUI,csUI);    // 左侧在线列表
//        person.setBounds(0, clientUI.getPersonList().size() * 70, 310, 70);
//        // 添加进好友Map集合
//        clientUI.getPersonList().put(person,csUI);
//        // 遍历Map集合，在好友列表显示
//        FriendModel friendModel = null;
//        Set set = clientUI.getPersonList().keySet();   //
//        Iterator it = set.iterator();
//        while (it.hasNext() == false) {
//            FriendModel jPanel = (FriendModel) it.next();
////            System.out.println(strID+" !! "+jPanel.getGroup_id());     // 数据验证
//            // 当服务器传来的id与放进列表panel的id相同
//            if (jPanel.getGroup_id().equals(strID)) {
//                friendModel = jPanel;
//                clientUI.getOnlineListPanel().add(friendModel);
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
                    user.getId() + CODE.CLIENT_FROM_NAME + CODE.CLIENT_TO_ID + user.getId() + CODE.MESSAGE_SPLIT_SYMBOL + str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return;
    }

    /**
     * 接收服务端返回的刷新好友列表
     *
     * @param str
     */
    public void refreshFriendsReceive(String str) {
        // 刷新好友列表
//        try {
//            clientUI.loadFriendList(1);
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
        // 刷新好友在线状态
        if (str.equals("null")) return;     // 无需处理
        String[] arr = str.split(",");
        System.out.println(str + "          返回的刷新好友列表");
        for (int i = 0; i < arr.length; i++) {
            isFriendOnlineStatus(arr[i], true);
        }
    }

    /**
     * 发送加好友h或加群请求请求
     *
     * @param id   被添加的id
     * @param type 类型，0是加好友，1是加群
     */
    public void addFriendOrGroupSend(String id, int type) {
        // FROM_ID:<ID>FROM_NAME:<NUll>TO:<ID>MSG:<NUll>
        String msg = CODE.CLIENT_FROM_ID + user.getId() + CODE.CLIENT_FROM_NAME +
                user.getName() + CODE.CLIENT_TO_ID + id + CODE.MESSAGE_SPLIT_SYMBOL;
        if (type == 0) {    // 加好友
            msg = CODE.CLIENT_ADD_FRIEND + msg;
        } else if (type == 1) {   // 加群
            msg = CODE.CLIENT_ADD_GROUP + msg;
        }
        this.send(msg);
    }

    /**
     * 接收加群或者加好友请求
     *
     * @param from_id
     * @param from_name
     * @param type
     */
    public void addFriendOrGroupReceive(String from_id, String from_name, int type) {
        if (type == 0) {
            System.out.println(from_name + "(" + from_id + ")，请求添加你为好友");
        } else if (type == 1) {
            System.out.println(from_name + "(" + from_id + ")，申请加入群");
        }
    }

    /**
     * 将接收的消息显示到对应的聊天界面
     *
     * @param from
     * @param msg
     */
    public void showMessage(String from, String msg) {
        clientUI.setSet(clientUI.getPersonList().keySet());
        clientUI.setIt(clientUI.getSet().iterator());
        while (clientUI.getIt().hasNext()) {
            friendModel = (FriendModel) clientUI.getIt().next();
            csUI = clientUI.getPersonList().get(friendModel);
            if (csUI.getStr_Id().equals(from)) {
//                String userName = from.substring();
                // 在对应的好友块上显示小红点
                if (csUI.isVisible() == false) {
                    friendModel.getRed_dot().setVisible(true);
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
