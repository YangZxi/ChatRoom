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

    public String[] getGroupFriends(String sql) {
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
        if (str != null) {
            groupFriends_id = str.split(",");
        }
        return groupFriends_id;
    }

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
        userManager.getGroupFriends("SELECT group_friends FROM Chat_Group WHERE group_id = '121234'");
    }


}
