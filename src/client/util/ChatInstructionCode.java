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
package client.util;

/**
 * 〈一句话功能简述〉
 * 〈消息发送和接收的指令CODE〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */
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
