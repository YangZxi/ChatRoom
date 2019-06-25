package server.util;

public class ChatInstructionCode {
    public final String CLIENT_ONLINE = "9#";  // 客户端发送上线
    public final String CLIENT_OFFLINE = "0#"; // 客户端发送下线
    public final String CLIENT_SINGLE_CHAT = "1#"; // 客户端发送私聊消息
    public final String CLIENT_GROUP_CHAT = "2#"; // 客户端发送群聊消息
    public final String CLIENT_REFRESH_FRIENDS = "3#"; // 客户端发送刷新列表请求
    public final String CLIENT_ADD_FRIEND = "4#"; // 客户端发送加好友请求
    public final String CLIENT_ADD_GROUP = "5#"; // 客户端发送加群请求
    public final String CLIENT_FROM_ID = "FROM_ID:";  // 发送人ID前缀
    public final String CLIENT_FROM_NAME = "FROM_NAME:";    // 发送人昵称浅醉
    public final String CLIENT_TO_ID = "TO:";  //  接收人前缀
    public final String MESSAGE_SPLIT_SYMBOL = "MSG:"; // 信息内容的前缀

    public final String SERVER_ONLINE = "19#";    // 服务端发送上线
    public final String SERVER_OFFLINE = "10#";   // 服务端发送下线
    public final String SERVER_SINGLE_CHAT = "11#"; // 服务端发送私聊消息
    public final String SERVER_GROUP_CHAT = "12#"; // 服务端发送群聊消息
    public final String SERVER_REFRESH_FRIENDS = "13#"; // 服务端发送刷新列表请求
    public final String SERVER_ADD_FRIEND = "14#"; // 服务端发送加好友请求
    public final String SERVER_ADD_GROUP = "15#"; // 服务端发送加群请求
}
