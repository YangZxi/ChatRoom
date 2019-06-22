package client.util;

public class ChatInstructionCode {
    public String CLIENT_ONLINE = "9#";  // 客户端发送上线
    public String CLIENT_OFFLINE = "0#"; // 客户端发送下线
    public String CLIENT_PRIVATE_CHAT = "1#"; // 客户端发送私聊消息
    public String CLIENT_GROUP_CHAT = "2#"; // 客户端发送群聊消息
    public String CLIENT_REFRESH_FRIENDS = "3#"; // 客户端发送刷新列表请求
    public String CLIENT_FROM_ID = "FROM_ID:";  // 发送人前缀
    public String CLIENT_FROM_NAME = "FROM_NAME:";
    public String CLIENT_TO = "TO:";  //  接收人前缀
    public String MESSAGE_SPLIT_SYMBO = "MSG:"; // 信息内容的前缀

    public String SERVER_ONLINE = "19#";    // 服务端发送上线
    public String SERVER_OFFLINE = "10#";   // 服务端发送下线
    public String SERVER_PRIVATE_CHAT = "11#"; // 客户端发送私聊消息
    public String SERVER_GROUP_CHAT = "12#"; // 客户端发送群聊消息
    public String SERVER_REFRESH_FRIENDS = "13#"; // 客户端发送刷新列表请求
}
