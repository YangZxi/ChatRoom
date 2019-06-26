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
package client.model;

/**
 * 〈一句话功能简述〉
 * 〈客户端聊天界面的主视图〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */
public class User {
    // 用户属性
    private String id;
    private String name;
    private String phone;
    private String password;
    private String birthday;
    private String sex;
    private String hobby;
    private String province;
    private int status;

    public User(){}

    public User(String id, String name, String phone, String password, String birthday, String sex, String hobby, String province) {
        this.id = id;   // 标识ID
        this.name = name;   // 昵称
        this.phone = phone;     // 手机号（用户名）
        this.password = password;   // 密码
        this.birthday = birthday;   // 生日
        this.sex = sex;     // 性别
        this.hobby = hobby;     // 爱好
        this.province = province;   // 籍贯
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
