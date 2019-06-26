package server.service;

import server.model.User;
import server.util.BaseDao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager extends BaseDao {
    @Override
    protected ArrayList createObject(ResultSet rs) throws SQLException {
        ArrayList<User> users = new ArrayList<User>();
        while (rs.next()) {
            User user = new User();
            user.setId(rs.getInt("user_id"));
            user.setName(rs.getString("user_name"));
            user.setHobby(rs.getString("user_hobby"));
            user.setSex(rs.getString("user_sex"));
            user.setBirthday(rs.getString("user_birthday"));
            user.setPhone(rs.getString("user_phone"));
            user.setProvince(rs.getString("user_province"));
            users.add(user);
        }
        return users;
    }

    public String getUserFriends(String user_id) {
        String str = null;
        String sql = "SELECT user_friends FROM Chat_Friend WHERE user_id = \'" + user_id + "\'";
        ResultSet resultSet = this.execute(sql,null);
        try {
            while (resultSet.next()) {
//                System.out.println(resultSet.getString("user_friends"));
                str = resultSet.getString("user_friends");
            }
        }catch (SQLException e) {
            e.getMessage();
        }
        return str;
    }

    public String getUserGroups(String user_id) {
        String str = null;
        String sql = "SELECT user_groups FROM Chat_Friend WHERE user_id = \'" + user_id + "\'";
        ResultSet resultSet = this.execute(sql,null);
        try {
            while (resultSet.next()) {
//                System.out.println(resultSet.getString("user_groups"));
                str = resultSet.getString("user_groups");
            }
        }catch (SQLException e) {
            e.getMessage();
        }
        return str;
    }

    public void updateUserFriends(String friends, String user_id) {
        String sql = "UPDATE `Chat_Friend` SET `user_friends`=\"" + friends + "\" WHERE user_id = \'" + user_id + "\'";
        this.executeSQL(sql,null);
    }

    public void updateGroup(String groups,String user_id) {
        String sql = "UPDATE `Chat_Friend` SET `user_groups`=\"" + groups + "\" WHERE user_id = \'" + user_id + "\'";
        this.executeSQL(sql,null);
    }

    public void updateGroupFriend(String friends,String group_id) {
        String sql = "UPDATE `Chat_Group` SET `group_friends`=\"" + friends + "\" WHERE group_id = \'" + group_id + "\'";
        this.executeSQL(sql,null);
    }

    public String getGroupFriends(String to) {
        String sql = "SELECT group_friends FROM Chat_Group WHERE group_id = \'" + to + "\'";
        String str = null;
        String[] groupFriends_id = null;
        ResultSet resultSet = this.execute(sql,null);
        try {
            while (resultSet.next()) {
                System.out.println(resultSet.getString("group_friends"));
                str = resultSet.getString("group_friends");
            }
        }catch (SQLException e) {
            e.getMessage();
        }
        return str;
    }

    public String getGroupOwner(String group_id) {
        String user_id = null;
        String sql = "SELECT user_id FROM Chat_Group WHERE group_id = \'" + group_id + "\'";
        rs = execute(sql,null);
        try {
            while (rs.next()) {
                user_id = rs.getString("user_id");
            }
        }catch (SQLException e) {
            e.getMessage();
        }
        System.out.println(user_id);
        return user_id;
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
//        userManager.getGroupFriends("SELECT group_friends FROM Chat_Group WHERE group_id = '121234'");
        String s = userManager.getUserFriends("444444");
        System.out.println(s);
    }


}
