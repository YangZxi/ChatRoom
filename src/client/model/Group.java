package client.model;

public class Group {

    private String group_id;
    private String group_name;
    private String group_friend;
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

    public String getGroup_friend() {
        return group_friend;
    }

    public void setGroup_friend(String group_friend) {
        this.group_friend = group_friend;
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
        this.group_friend = group_friend;
        this.user_id = user_id;
    }
}
