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
        boolean flag = this.getUserByID(user.getId());
        if (flag == false) {    // 没有用户注册
            String sql = "INSERT INTO `Chat_User` (`user_id`, `user_name`, "
                    + " `user_password`, `user_sex`, `user_hobby`, `user_province`) "
                    + "VALUES (?, ?, ?, ?, ?, ?)";
            ArrayList users = new ArrayList();
            users.add(user.getId());
            users.add(user.getName());
            users.add(user.getPassword());
            users.add(user.getSex());
            users.add(user.getHobby());
            users.add(user.getProvince());
            int result_num = this.executeSQL(sql, users);
            System.out.println("创建成功！" + result_num);
            if (result_num > 0) flag = true;
            String sql1 = "INSERT INTO `Chat_Friend` (`user_id`, `user_friends`, `user_groups`) " +
                    "VALUES ('" + user.getId() + "', NULL, NULL);";
            this.executeSQL(sql1, null);
        }
        System.out.println(flag);
        return flag;
    }

    /**
     * 创建群聊
     * @param group_id
     * @param group_name
     * @param user_id
     * @return
     */
    public boolean createGroup(String group_id,String group_name, String user_id) {
        boolean flag = false;
        String sql = "INSERT INTO `Chat_Group` (`group_id`, `group_name`, `group_friends`, "
                + " `user_id`) VALUES (? , ?, ?, ?)";
        ArrayList<String> groups = new ArrayList<>();
        groups.add(group_id);
        groups.add(group_name);
        groups.add(user_id + ",");
        groups.add(user_id);
        int result_num = this.executeSQL(sql, groups);
//        System.out.println("创建成功！" + result_num);
        if (result_num > 0) flag = true;
        return flag;
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
     * @param userName
     * @return true表示已注册，false未注册
     * @throws SQLException
     */
    public boolean getUserByID(String userName) throws SQLException {
        boolean flag = false;
        String sql = "SELECT * FROM `Chat_User` WHERE user_id = ?";
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(userName);
        ResultSet resultSet = this.execute(sql, arrayList);
        while (resultSet.next()) {
            System.out.println(resultSet.getString("user_password"));
            flag = true;
        }
        return flag;
    }

    /**
     * 获取用户信息
     * @param user_id
     * @return
     */
    public User getUser(String user_id) {
        User user = null;
        String sql = "SELECT * FROM Chat_User WHERE user_id = \'" + user_id + "\'";
        user = (User) this.executeQuery(sql, null).get(0);
        return user;
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

    /**
     * 获取多个用户名
     * @param user_id
     * @return
     */
    public ArrayList<String> getUsersName(String[] user_id) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            conn = getConn();
            for (int i = 0;i < user_id.length;i++) {
                String sql = "SELECT user_name FROM Chat_User WHERE user_id = \'" + user_id[i] + "\'";
                pstmt = conn.prepareStatement(sql);
                rs = pstmt.executeQuery();
                while (rs.next()) {
//                    System.out.println(rs.getString("user_name"));
                    arrayList.add(rs.getString("user_name"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    /**
     * 获取群信息
     * @param group_id
     * @return
     */
    public Group getGroup(String group_id) {
        Group group = null;
        String sql = "SELECT * FROM Chat_Group WHERE group_id = \'" + group_id + "\'";
        group = (Group) this.executeQuery(sql, null).get(0);
        return group;
    }

    /**
     * 获取群名
     * @param group_id
     * @return
     */
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

    /**
     * 组装查询结果，通过数据库的type列进行判断是好友还是群
     * @param rs
     * @return
     * @throws SQLException
     */
    @Override
    protected ArrayList createObject(ResultSet rs) throws SQLException {
        ArrayList<Object> friends = new ArrayList<Object>();
        while (rs.next()) {
            if (rs.getInt("type") == 0) {  // 0表示User
                User user = new User();
                user.setId(rs.getString("user_id"));
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
                group.setGroup_friends(rs.getString("group_friends"));
                group.setUser_id(rs.getString("user_id"));
                friends.add(group);
                System.out.println("群成功");
            }
        }
        return friends;
    }

    public void updateGroup(String groups,String user_id) {
        String sql = "UPDATE `Chat_Friend` SET `user_groups`=\"" + groups + "\" WHERE user_id = \'" + user_id + "\'";
        this.executeSQL(sql,null);
    }

    public static void main(String[] args) throws SQLException {
        UserManager u = new UserManager();
//        u.createGroup("创建测试", "123456");
//        u.getUserFriends("666666");
    }
}
