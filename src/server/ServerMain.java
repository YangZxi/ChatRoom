package server;

import server.ui.ServerUI;
import server.util.CloseUtil;
import server.util.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {

    private static final int PORT = 3355;   // 端口

    public static void main(String[] args) {
        ServerUI serverUI = new ServerUI();
//        serverUI.setVisible(true);
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
//            serverUI.setShowPanel("服务器已启动");
            System.out.println("服务器已启动");
            while (true) {
                Socket socket = serverSocket.accept();
//                serverUI.setShowPanel(socket.getInetAddress().getHostAddress()+"已经连接到服务器！");
                System.out.println(socket.getInetAddress().getHostAddress()+"已经连接到服务器！");
                // 多线程
                Message m = new Message(socket,serverUI);
                new Thread(m).start();
            }
        } catch (IOException e) {
            System.out.println("关闭");
            CloseUtil.closeAll(serverSocket);
            e.printStackTrace();
        }

    }

}
