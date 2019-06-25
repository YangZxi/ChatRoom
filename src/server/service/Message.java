package server.service;

import server.ui.ServerUI;
import server.util.ChatInstructionCode;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;

public class Message implements Runnable {

    private Socket socket = null;
    private ServerUI serverUI = null;

    private DataOutputStream dos = null;
    private DataInputStream dis = null;

    private UserManager userManager = new UserManager();

    private ChatInstructionCode CODE = new ChatInstructionCode();

    public Message(Socket socket, ServerUI serverUI) {
        this.socket = socket;
        this.serverUI = serverUI;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            dis = new DataInputStream(socket.getInputStream()); // 接收数据
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        String str = null;
        try {
            while ((str = receive()) != null) {     // 不能使用if语句，否则服务器端不能一直接受消息
                // 获取标识     3#FROM:666666TO:666666MSG:123456,111111,222222,333333,444444,555555,777777,888888
                String messageFlag = str.substring(0, 2);
                // 获取内容
                String message = str.substring(str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL) +
                        CODE.MESSAGE_SPLIT_SYMBOL.length());
                if (messageFlag.equals(CODE.CLIENT_ONLINE)) {              // 如果标识为9#，表示上线
                    System.out.println(messageFlag + " 上线处理 " + message);
                    dealWithOnlineFunction(message);    // 处理上线
                    continue;
                } else if (messageFlag.equals(CODE.CLIENT_OFFLINE)) {       // 为0#，用户下线
                    dealWithOfflineFunction(message);   // 处理下线
                    continue;
                }
//                System.out.println(str + "aaaaaaaaaaaaaaaaaaaaa");
                // 获取发送者ID
                String from_id = str.substring(str.indexOf(CODE.CLIENT_FROM_ID) +
                        CODE.CLIENT_FROM_ID.length(), str.indexOf(CODE.CLIENT_FROM_NAME));
                // 获取发送者ID
                String from_name = str.substring(str.indexOf(CODE.CLIENT_FROM_NAME) +
                        CODE.CLIENT_FROM_NAME.length(), str.indexOf(CODE.CLIENT_TO_ID));
                // 获取接收者
                String to_id = str.substring(str.indexOf(CODE.CLIENT_TO_ID) + CODE.CLIENT_TO_ID.length(),
                        str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL));
                System.out.println(messageFlag + "---" + from_id + "---" + from_name + "---" + to_id + "---" + message);
                // 通过标识执行不同的信息处理
                if (messageFlag.equals(CODE.CLIENT_SINGLE_CHAT)) {       // 为1#，处理私聊消息
                    message = CODE.SERVER_SINGLE_CHAT +
                            str.substring(str.indexOf(CODE.CLIENT_SINGLE_CHAT) +
                                    CODE.CLIENT_SINGLE_CHAT.length(), str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL)) +
                            CODE.MESSAGE_SPLIT_SYMBOL +
                            getTime() + "\n" + message;
                    System.out.println("私聊" + message);     // 验证数据
                    this.send(to_id, message); // 给接收人发送
                } else if (messageFlag.equals(CODE.CLIENT_GROUP_CHAT)) {       // 为2#，处理群聊消息
                    System.out.println("群聊" + message);     // 验证数据
                    message = CODE.SERVER_GROUP_CHAT +
                            str.substring(str.indexOf(CODE.CLIENT_SINGLE_CHAT) +
                                    CODE.CLIENT_SINGLE_CHAT.length(), str.indexOf(CODE.MESSAGE_SPLIT_SYMBOL)) +
                            CODE.MESSAGE_SPLIT_SYMBOL +
                            getTime() + "\n" + message;
                    sendGroup(from_id, to_id, message);
                } else if (messageFlag.equals(CODE.CLIENT_REFRESH_FRIENDS)) {       // 为3#，处理刷新指令
//                    System.out.println(message + "ddddddddddddd");
                    dealWithRefreshFunction(to_id, message);
                } else if (messageFlag.equals(CODE.CLIENT_ADD_FRIEND)) {    // 为4#，加好友
                    this.dealWithAddFiendFunction(from_id,to_id);
                } else if (messageFlag.equals(CODE.CLIENT_ADD_GROUP)) {     // 为5#，加群
                    this.dealWithAddGroupFunction(from_id,to_id);
                }
            }
        } catch (IOException e) {
//            serverUI.setShowPanel("有一个客户端下线");	// 在界面显示
//            e.printStackTrace();
        }
    }

    /**
     * 给单个用户发送消息
     *
     * @param msg
     */
    public void send(String to, String msg) {
        if (to == null) {
            try {
                System.out.println(msg);  // 验证数据
                dos.writeUTF(msg);
                dos.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Set set = serverUI.getUserMap().keySet();   //
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String strID = (String) it.next();
                if (strID.equals(to)) {
                    Message message = serverUI.getUserMap().get(strID);
//                System.out.println("sendAll 中");
                    message.send(null, msg);    // 发送给指定的接收客户端
                }
            }
        }
    }

    /**
     * 给所有用户发送消息
     *
     * @param str
     */
    public void sendAll(String str) {
        if (serverUI.getUserMap() == null || serverUI.getUserMap().size() == 0) {
            return;
        } else {
            Set set = serverUI.getUserMap().keySet();   //
            Iterator it = set.iterator();
            while (it.hasNext()) {
                String key = (String) it.next();
                Message message = serverUI.getUserMap().get(key);
//                System.out.println("sendAll 中");
                message.send(null, str);
            }
        }
    }

    /**
     * 通过群id
     * 给加了此群的所有用户发送消息
     *
     * @param to  接受消息的群ID
     * @param msg 消息
     */
    public void sendGroup(String from, String to, String msg) {
        String sql = "SELECT group_friends FROM Chat_Group WHERE group_id = \'" + to + "\'";
        // 得到返回的群成员id数组
        String[] groupFriends_id = userManager.getGroupFriends(sql);
        // 发送给每个群成员且上线的用户
        for (int i = 0; i < groupFriends_id.length; i++) {
            // 如果接收者的id和发送者的id相同，跳过
            System.out.println(groupFriends_id[i]);
            if (from.equals(groupFriends_id[i])) continue;
            Message message = serverUI.getUserMap().get(groupFriends_id[i]);
            // 如果用户没有上线，跳过
            if (message == null) continue;
            message.send(null, msg);    // 发送给指定的接收客户端
        }
    }

    public String receive() throws IOException {
        String str = null;
        str = dis.readUTF();
        System.out.println(str);
        return str;
    }


    /**
     * 客户端上线处理
     *
     * @param str
     */
    public void dealWithOnlineFunction(String str) {
        // 通过用户id截取用户的信息
        String strID = str.substring(3, str.indexOf("昵称")).trim();     // 输出测试
//        serverUI.setShowPanel(str + " 已上线 " + getTime() + "\n");  // 在服务器控制台显示
//        String sql = "SELECT * FROM Chat_User WHERE user_id = \'" + strID + "\'";
//                    System.out.println(sql);
//        // 将返回的结果包装成User对象
////                    User user = (User) serverUI.getUserManager().executeQuery(
////                            sql, null).get(0);
////                    System.out.println(user.getName());
        // 将用户的id和Message对象放进Map
        serverUI.getUserMap().put(strID, this);
        // 以下是判断Map是否加入成功，仅作测试使用
        serverUI.setSet(serverUI.getUserMap().keySet());   //
        serverUI.setIt(serverUI.getSet().iterator());
        while (serverUI.getIt().hasNext()) {
            String strID1 = (String) serverUI.getIt().next();
            System.out.println("Map添加成功" + strID1);
        }
//        // Map检测 end
//        // 给当前在线用户发送上线信息，传一个ID
        this.sendAll(CODE.SERVER_ONLINE + CODE.MESSAGE_SPLIT_SYMBOL + "ID" +
                strID + "昵称" + str.substring(str.indexOf("昵称：") + 3));
    }

    /**
     * 客户端下线处理 标识 0
     * from 客户端
     *
     * @param str
     */
    public void dealWithOfflineFunction(String str) {
        // 通过用户id截取用户的信息
        String strID = str.substring(3, str.indexOf("昵称")).trim();
//        serverUI.setShowPanel(str + " 已下线 " + getTime() + "\n");
        // 删除Map中当前下线的用户
        serverUI.getUserMap().remove(strID);
        // 以下是判断，仅作测试
        serverUI.setSet(serverUI.getUserMap().keySet());   //
        serverUI.setIt(serverUI.getSet().iterator());
        while (serverUI.getIt().hasNext()) {
            String strID1 = (String) serverUI.getIt().next();
            System.out.println("Map删除成功" + strID1);
        }
        // 发送下线信息
        this.sendAll(CODE.SERVER_OFFLINE + CODE.MESSAGE_SPLIT_SYMBOL + "ID" +
                strID + "昵称" + str.substring(str.indexOf("昵称：") + 3));
    }

    /**
     * 处理好友刷新请求
     *
     * @param to  将刷新结果返回的用户ID
     * @param str 请求刷新用户的全部好友好友
     */
    public void dealWithRefreshFunction(String to, String str) {
        String msg = "";
        // 将字符串转成数组
        String[] arr = str.split(",");
        for (int i = 0; i < arr.length; i++) {
            // 获取当前在线的用户
            serverUI.setSet(serverUI.getUserMap().keySet());   //
            serverUI.setIt(serverUI.getSet().iterator());
            while (serverUI.getIt().hasNext()) {
                // 获取在线用户的id
                String strID = (String) serverUI.getIt().next();
                //                    System.out.println(strID + "map的id");
                if (arr[i].equals(strID)) {     // 与当前在线的用户进行匹配
                    msg = msg + strID + ",";
                }
            }
        }
        if (msg == null || msg.length() == 0) {
            msg = "null";
        }
        msg = CODE.SERVER_REFRESH_FRIENDS + CODE.MESSAGE_SPLIT_SYMBOL + msg;
        System.out.println(msg + "返回处理");
        this.send(to, msg);
    }

    /**
     * 处理加好友请求
     * @param from_id   添加好友的用户id
     * @param to_id     被添加的用户id
     */
    public void dealWithAddFiendFunction(String from_id,String to_id) {
        String msg = this.getMessage(CODE.SERVER_ADD_FRIEND,from_id,"",to_id,"");
        this.send(to_id,msg);
    }

    /**
     * 处理加群请求
     * @param from_id   添加群的用户id
     * @param group_id     被添加的群id
     */
    public void dealWithAddGroupFunction(String from_id,String group_id) {
        String msg = this.getMessage(CODE.SERVER_ADD_GROUP,from_id,"",group_id,"");
        // 通过群号获取群主id
        String user_id = userManager.getGroupOwner(group_id);
        this.send(user_id,msg);
    }

    /**
     * 包装服务端消息
     * @param code      消息代码指令
     * @param from_id   发送用户id
     * @param from_name 发送用户昵称
     * @param to_id     接收用户id
     * @param msg       消息内容
     * @return
     */
    public String getMessage(String code,String from_id,String from_name,String to_id,String msg) {
        String str = code + CODE.CLIENT_FROM_ID + from_id + CODE.CLIENT_FROM_NAME + from_name +
                CODE.CLIENT_TO_ID + to_id + CODE.MESSAGE_SPLIT_SYMBOL + msg;
        return str;
    }

    /**
     * 获取本地时间
     * 插入在消息之前
     *
     * @return
     */
    public String getTime() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        return sdf.format(date);
    }

}
