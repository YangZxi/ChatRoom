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
    public final String CLIENT_ONLINE = "9#";  // 客户端发送上线
    public final String CLIENT_OFFLINE = "0#"; // 客户端发送下线
    public final String CLIENT_SINGLE_CHAT = "1#"; // 客户端发送私聊消息
    public final String CLIENT_GROUP_CHAT = "2#"; // 客户端发送群聊消息
    public final String CLIENT_REFRESH_FRIENDS = "3#"; // 客户端发送刷新列表请求
    public final String CLIENT_CHANGE_FRIEND = "4#"; // 客户端发送好友请求   包括删除，添加
    public final String CLIENT_CHANGE_GROUP = "5#"; // 客户端发送群请求   包括申请，退出
    public final String CLIENT_REQUEST_FRIEND = "6#";    // 客户端回应请求  同意，拒绝
    public final String CLIENT_REQUEST_GROUP = "7#";    // 客户端回应请求  同意，拒绝
    public final String CLIENT_FROM_ID = "FROM_ID:";  // 发送人ID前缀
    public final String CLIENT_FROM_NAME = "FROM_NAME:";    // 发送人昵称浅醉
    public final String CLIENT_TO_ID = "TO:";  //  接收人前缀
    public final String MESSAGE_SPLIT_SYMBOL = "MSG:"; // 信息内容的前缀

    public final String SERVER_ONLINE = "19#";    // 服务端发送上线
    public final String SERVER_OFFLINE = "10#";   // 服务端发送下线
    public final String SERVER_SINGLE_CHAT = "11#"; // 服务端发送私聊消息
    public final String SERVER_GROUP_CHAT = "12#"; // 服务端发送群聊消息
    public final String SERVER_REFRESH_FRIENDS = "13#"; // 服务端发送刷新列表请求
    public final String SERVER_CHANGE_FRIEND = "14#"; // 服务端发送好友请求
    public final String SERVER_CHANGE_GROUP = "15#"; // 服务端发送群请求
    public final String SERVER_REQUEST_FRIEND = "16#";    // 服务端好友请求
    public final String SERVER_REQUEST_GROUP = "17#";    // 服务端好友请求
}
