/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: ClientMain
 * Author:   OuYoung
 * Date:     2019/06/22 22:58
 * Description: 这是程序的入口
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung       2019/06/22 22:58       1.3.1             描述
 */
package client;

/**
 * 〈一句话功能简述〉
 * 〈这是程序的入口〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */
import client.model.User;
import client.service.UserManager;
import client.ui.ClientUI;
import client.ui.LoginUI;
import client.service.Message;

import java.io.IOException;
import java.net.Socket;
import java.sql.SQLException;
import java.util.ArrayList;

public class ClientMain {

    private static int PORT = 3355;
//    private static String ADDRESS = "47.107.244.7";
    private static String ADDRESS = "localhost";
    private UserManager userManager = null;

    /**
     *
     * @param userID   用户ID
     */
    public ClientMain(String userID) {
        ClientUI clientUI = null;
        try {
            // 连接服务器
            Socket socket = new Socket(ADDRESS, PORT);
            // 从数据库获取User信息，传给客户端界面，有用!!! important
            // 登录用户的信息
            String strSQL = "SELECT * FROM Chat_User WHERE user_id = ?";
            userManager = new UserManager();
            ArrayList arrayList = new ArrayList();
            arrayList.add(userID);
            // 将查询的结果包装为User对象
            User user = (User) userManager.executeQuery(strSQL,arrayList).get(0);
//            System.out.println(user.getId()+user.getName()+user.getSex());
//            Message message = new Message(socket);
            clientUI = new ClientUI(user);
            clientUI.setClientUI(clientUI);
            clientUI.setVisible(true);
//            message.addElements(clientUI.getUser(),clientUI.getShowArea(),clientUI.getInputArea());
            Message message = new Message(socket,clientUI);
            clientUI.setMessage(message);
            clientUI.connection();
//            new ClientUI(message).setVisible(true);
            new Thread(message).start();
            System.out.println("客户端连接至服务器");
        } catch (IOException e) {
            System.out.println("服务器连接失败，请检查网络连接或联系管理员。");
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        LoginUI loginUI = new LoginUI();
        loginUI.setVisible(true);
    }
}

/*
 * 服务器发送给客户端的指令标识
 * 9 表示第一次上线
 * 0 表示下线
 * 1 表示私聊消息
 * 2 表示群聊消息
 * 3 表示刷新好友
 *
 * 01#666666@123456$
 *
 * 服务端发送给客户端的指令标识
 *
 * */
