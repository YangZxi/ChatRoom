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

    public static void main(String[] args) {
        UserManager userManager = new UserManager();
//        ArrayList<User> users1 = new ArrayList<>();

        User u = (User) userManager.executeQuery(
                "SELECT * FROM Chat_User WHERE user_id = \'666666\'",null).get(0);
        System.out.println(u.getName());
    }


}
