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
package client.service;

/**
 * 〈一句话功能简述〉
 * 〈连接数据库，对用户进行读取，写入，更新〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */

import client.model.Group;
import client.util.BaseDao;
import client.model.User;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserManager extends BaseDao {

    private ArrayList arrayList = new ArrayList();

    /**
     * 用户注册，新建一个用户
     *
     * @param user 写入数据库的用户信息
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
     *
     * @param user_id
     * @return 数据库查询到的密码
     * @throws SQLException
     */
    public String getPassword(String user_id) throws SQLException {
        String sql = "SELECT user_password FROM Chat_User WHERE user_id = \'" + user_id + "\'";
        ResultSet resultSet = execute(sql, null);
        String password = null;
        while (resultSet.next()) {
            password = resultSet.getString("user_password");
            System.out.println(password);
        }
        return password;
    }

    /**
     * 获取好友列表 和 群列表
     *
     * @param user_id
     * @return
     */
    public ArrayList<Object> getFriends(String user_id) throws SQLException {
        ArrayList<Object> friends_groups = new ArrayList<>();
        String[] friendID = null;
        String[] groupID = null;
        String strFriends = null;
        String strGroups = null;
        String sql = "SELECT user_friends,user_groups FROM Chat_Friend WHERE user_id = \'" + user_id + "\'";
        ResultSet resultSet = this.execute(sql, null);
        while (resultSet.next()) {
            if (resultSet.getString("user_friends") == null
                    || resultSet.getString("user_friends").equals("")) {
                System.out.println("No Friend");
            }
            strFriends = resultSet.getString("user_friends") + "";
            if (resultSet.getString("user_groups") == null
                    || resultSet.getString("user_groups").equals("")) {
                System.out.println("No Group");
            }
            strGroups = resultSet.getString("user_groups") + "";
//            System.out.println(strFriends);
        }
        System.out.println("我的好友：   " + strFriends);
        System.out.println("我的群组：   " + strGroups);
        // 将查询的好友账号分割放进数组
        if (strFriends != null || !strFriends.equals("null")) {
            friendID = strFriends.split(",");
            for (int i = 0; i < friendID.length; i++) {
//                System.out.print(friendID[i] + " ");
                if (friendID[i].equals("null")) continue;
                // 通过id获取用户信息
                String sql1 = "SELECT * FROM Chat_User WHERE user_id = \'" + friendID[i] + "\'";
//                friends.add((User) this.executeQuery(sql1,null).get(0));
                User user = (User) this.executeQuery(sql1, null).get(0);
                friends_groups.add(user);
//                friends.add(idArr[i]);
            }
            System.out.println(friends_groups.size() + "    123");
        }
        System.out.println(strGroups);
        // 将查询的群组账号分割放进数组
        if (strGroups != null || !strGroups.equals("null")) {
            groupID = strGroups.split(",");
            for (int i = 0; i < groupID.length; i++) {
//                System.out.print(groupID[i] + " ");
                if (groupID[i].equals("null")) continue;
                // 通过id获取群信息
                String sql1 = "SELECT * FROM Chat_Group WHERE group_id = \'" + groupID[i] + "\'";
                arrayList = this.executeQuery(sql1, null);
                if (arrayList.get(0) == null) {
                    return null;
                }
                Group group = (Group) arrayList.get(0);
                friends_groups.add(group);
//                friends.add(idArr[i]);
            }
            System.out.println(friends_groups.size() + "    123");
        }
        return friends_groups;
    }

    /**
     * 获取查询用户的ID
     *
     * @param userName
     * @return true表示已注册，false未注册
     * @throws SQLException
     */
    public boolean getUserByName(String userName) throws SQLException {
        boolean flag = false;
//        baseDao.getConn();   // 连接
//        String sql = "SELECT * FROM `chat_user` WHERE user_phone = \" + \"'\" + phone + \"'\"";
        String sql = "SELECT * FROM `Chat_User` WHERE user_name = ?";
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

    public User getUser(String user_id) {
        User user = null;
        String sql = "SELECT * FROM Chat_User WHERE user_id = \'" + user_id + "\'";
        user = (User) this.executeQuery(sql, null).get(0);
        return user;
    }

    public Group getGroup(String group_id) {
        Group group = null;
        String sql = "SELECT * FROM Chat_Group WHERE group_id = \'" + group_id + "\'";
        group = (Group) this.executeQuery(sql, null).get(0);
        return group;
    }

    public String getGroupName(String group_id) {
        String sql = "SELECT group_name FROM Chat_Group WHERE group_id = \'" + group_id + "\'";
        ResultSet resultSet = execute(sql, null);
        String group_name = null;
        try {
            while (resultSet.next()) {
                group_name = resultSet.getString("group_name");
                System.out.println(group_name);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return group_name;
    }

    @Override
    protected ArrayList createObject(ResultSet rs) throws SQLException {
        ArrayList<Object> friends = new ArrayList<Object>();
        while (rs.next()) {
            if (rs.getInt("type") == 0) {  // 0表示User
                User user = new User();
                user.setId(rs.getInt("user_id"));
                user.setName(rs.getString("user_name"));
                user.setHobby(rs.getString("user_hobby"));
                user.setSex(rs.getString("user_sex"));
                user.setBirthday(rs.getString("user_birthday"));
                user.setPhone(rs.getString("user_phone"));
                user.setProvince(rs.getString("user_province"));
                friends.add(user);
            } else if (rs.getInt("type") == 1) {     // 1表示Group
                Group group = new Group();
                group.setGroup_id(rs.getString("group_id"));
                group.setGroup_name(rs.getString("group_name"));
                group.setUser_id(rs.getString("user_id"));
                friends.add(group);
                System.out.println("群成功");
            }
        }
        return friends;
    }

    public static void main(String[] args) throws SQLException {
        UserManager u = new UserManager();
        u.getGroup("121234");
//        u.getUserFriends("666666");
    }
}
