package client.service;

import client.util.BaseDao;
import client.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager extends BaseDao {

    /**
     *
     * @param user
     * @return
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public boolean createUser(User user) throws SQLException, ClassNotFoundException {
        boolean flag = this.getUserByName(user.getName());
        if (flag == false) {    // 没有用户注册

        }
        return true;
    }

    /**
     * 获取密码
     * @param user_id
     * @return  数据库查询到的密码
     * @throws SQLException
     */
    public String getPassword(String user_id) throws SQLException {
        String sql = "SELECT user_password FROM Chat_User WHERE user_id = \'" + user_id +"\'";
        ResultSet resultSet = execute(sql,null);
        String password = null;
        while (resultSet.next()) {
            password = resultSet.getString("user_password");
            System.out.println(password);
        }
        return password;
    }

    /**
     * 获取好友列表
     * @param user_id
     * @return
     */
    public ArrayList<User> getFriends(String user_id) throws SQLException {
        ArrayList<User> friends = new ArrayList<>();
        String[] friendID = null;
        String strFriends = null;
        String sql = "SELECT user_friends FROM Chat_Friend WHERE user_id = \'" + user_id +"\'";
        ResultSet resultSet = this.execute(sql,null);
        while (resultSet.next()) {
            if (resultSet.getString("user_friends") == null
            || resultSet.getString("user_friends").equals("")) {
                System.out.println("No Friends");
            }
            strFriends = resultSet.getString("user_friends");
            System.out.println(strFriends);
        }
        System.out.println("ppppppppppppppp   " + strFriends);
        // 将查询的朋友账号分割放进数组
        if (strFriends != null || !strFriends.equals("null")) {
            friendID = strFriends.split(",");
            for (int i = 0;i < friendID.length;i++) {
                System.out.print(friendID[i] + " ");
                // 通过id获取用户信息
                String sql1 = "SELECT * FROM Chat_User WHERE user_id = \'" + friendID[i] +"\'";
//                friends.add((User) this.executeQuery(sql1,null).get(0));
                User user = (User) this.executeQuery(sql1, null).get(0);
                friends.add(user);
//                friends.add(idArr[i]);
            }
            System.out.println(friends.size() + "    123");
        }
        return friends;
    }

    // 查询用户用户名（手机号）
    public boolean getUserByName(String userName) throws SQLException, ClassNotFoundException {
        boolean flag = false;
//        baseDao.getConn();   // 连接
//        String sql = "SELECT * FROM `chat_user` WHERE user_phone = \" + \"'\" + phone + \"'\"";
        String sql = "SELECT * FROM `chat_user` WHERE user_name = ?";
//        baseDao.setPreparedStatement(baseDao.getConnection().prepareStatement(sql));
//        baseDao.getPreparedStatement().setString(1,userName);
//        baseDao.setResultSet(baseDao.getPreparedStatement().executeQuery());
//        while (baseDao.getResultSet().next()) {
//            System.out.println("数据库已有用户，密码是："+baseDao.getResultSet().getString("user_password"));
//            user = new User();
//            user.setId(connectSQL.getResultSet().getInt("user_id"));
//            user.setName(connectSQL.getResultSet().getString("user_name"));
//            user.setPhone(connectSQL.getResultSet().getString("user_phone"));
//            user.setBirthday(connectSQL.getResultSet().getString("user_birthday"));
//            user.setPassword(connectSQL.getResultSet().getString("user_password"));
//            user.setSex(connectSQL.getResultSet().getString("user_sex"));
//            user.setHobby(connectSQL.getResultSet().getString("user_hobby"));
//            user.setProvince(connectSQL.getResultSet().getString("user_province"));
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(userName);
        ResultSet resultSet = this.execute(sql, arrayList);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("user_password"));
        }
        flag = true;
//        }
//        baseDao.closeAll(); // 关闭
        this.closeAll();
        return flag;
    }

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

    public static void main(String[] args) throws SQLException {
        UserManager u = new UserManager();
        u.getFriends("123456");
        u.getFriends("666666");
    }
}
