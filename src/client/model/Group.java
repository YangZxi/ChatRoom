/**
 * Copyright (C), 2015-2019
 * FileName: Group
 * Author:   OuYoung
 * Date:     2019/06/22 22:11
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           1.0.0             描述
 */
package client.model;

/**
 * 〈一句话功能简述〉<br> 
 * 群模型
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */
public class Group {

    private String group_id;
    private String group_name;
    private String group_friends;
    private String user_id;

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }

    public String getGroup_friends() {
        return group_friends;
    }

    public void setGroup_friends(String group_friends) {
        this.group_friends = group_friends;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Group() {}

    public Group(String group_id, String group_name, String group_friend, String user_id) {
        this.group_id = group_id;
        this.group_name = group_name;
        this.group_friends = group_friend;
        this.user_id = user_id;
    }
}
