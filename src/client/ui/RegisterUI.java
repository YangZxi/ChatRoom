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
package client.ui;

/**
 * 〈一句话功能简述〉
 * 〈客户端聊天界面的主视图〉
 *
 * @author OuYoung
 * @create 2019/06/22
 * @since 1.0.0
 */
import client.model.User;
import client.service.UserManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class RegisterUI extends JFrame {

    private JPanel contentPane;
    private final ButtonGroup sexGroup = new ButtonGroup();
    private JTextField userName;
    private JPasswordField password;
    private JPasswordField passwordComfirm;
    private LoginUI login;
    private JRadioButton rdMale;
    private JRadioButton rdFemale;
    private JComboBox cbProvince;
    private JCheckBox chkHobby1;
    private JCheckBox chkHobby2;
    private JCheckBox chkHobby3;
    private JScrollPane scrollPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RegisterUI frame = new RegisterUI();
                    frame.setResizable(false);
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public RegisterUI() {
        // 调用初始化方法
        init();
        this.setLocationRelativeTo(null);
    }

    public RegisterUI(LoginUI login) {
        this.login = login;
        init();
        this.setLocationRelativeTo(null);
    }

    public void init() {
        setTitle("注册");
        setIconImage(Toolkit.getDefaultToolkit().getImage(LoginUI.class.getResource("/images/icon.png")));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 550, 450);
        contentPane = new JPanel();
        contentPane.setBackground(SystemColor.control);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        JLabel label = new JLabel("注册界面");
        label.setFont(new Font("微软雅黑", Font.BOLD, 18));
        label.setBounds(228, 13, 72, 29);
        contentPane.add(label);

        // 用户名
        JLabel label_1 = new JLabel("用 户 名：");
        label_1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label_1.setBounds(33, 52, 87, 30);
        contentPane.add(label_1);

        userName = new JTextField();
        userName.setBounds(111, 50, 200, 35);
        contentPane.add(userName);
        userName.setColumns(10);

        // 密码
        JLabel label_2 = new JLabel("密    码：");
        label_2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label_2.setBounds(33, 95, 87, 30);
        contentPane.add(label_2);

        password = new JPasswordField();
        password.setBounds(111, 95, 200, 35);
        contentPane.add(password);

        // 确认密码
        JLabel label_3 = new JLabel("确认密码：");
        label_3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label_3.setBounds(33, 138, 87, 30);
        contentPane.add(label_3);

        passwordComfirm = new JPasswordField();
        passwordComfirm.setBounds(111, 138, 200, 35);
        contentPane.add(passwordComfirm);

        // 性别
        JLabel label_4 = new JLabel("性    别：");
        label_4.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label_4.setBounds(33, 181, 87, 30);
        contentPane.add(label_4);

        rdMale = new JRadioButton("男");
        rdMale.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sexGroup.add(rdMale);
        rdMale.setFocusPainted(true);
        rdMale.setBounds(111, 178, 62, 37);
        contentPane.add(rdMale);

        rdFemale = new JRadioButton("女");
        rdFemale.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        sexGroup.add(rdFemale);
        rdFemale.setBounds(199, 178, 62, 37);
        contentPane.add(rdFemale);

        // 爱好
        JLabel label_5 = new JLabel("爱    好：");
        label_5.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label_5.setBounds(33, 224, 87, 30);
        contentPane.add(label_5);

        chkHobby1 = new JCheckBox("编程");
        chkHobby1.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        chkHobby1.setBounds(111, 221, 62, 37);
        contentPane.add(chkHobby1);

        chkHobby2 = new JCheckBox("旅游");
        chkHobby2.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        chkHobby2.setBounds(181, 221, 62, 37);
        contentPane.add(chkHobby2);

        chkHobby3 = new JCheckBox("游戏");
        chkHobby3.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        chkHobby3.setBounds(249, 221, 62, 37);
        contentPane.add(chkHobby3);

        // 籍贯
        JLabel label_jg = new JLabel("籍    贯：");
        label_jg.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        label_jg.setBounds(33, 271, 87, 30);
        contentPane.add(label_jg);

        cbProvince = new JComboBox();
        cbProvince.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        cbProvince.setModel(new DefaultComboBoxModel(new String[]{"--请选择--", "湖南", "湖北", "江西"}));
        cbProvince.setBounds(111, 269, 200, 35);
        contentPane.add(cbProvince);

        scrollPane = new JScrollPane();
        scrollPane.setBounds(332, 52, 164, 252);
        contentPane.add(scrollPane);

        JTextArea textArea = new JTextArea();
        scrollPane.setViewportView(textArea);
        textArea.setLineWrap(true);
        textArea.setRows(20);

        // 注册按钮
        JButton btnReg = new JButton("注册");
        btnReg.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btnReg.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                registerButton();
            }
        });
        btnReg.setBounds(58, 347, 153, 37);
        btnReg.setBackground(Color.lightGray);
        contentPane.add(btnReg);

        // 重置按钮
        JButton btnReset = new JButton("重置");
        btnReset.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        btnReset.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                userName.setText("");
                password.setText("");
                passwordComfirm.setText("");
                rdMale.setSelected(false);
                rdFemale.setSelected(false);
                chkHobby1.setSelected(false);
                chkHobby2.setSelected(false);
                chkHobby3.setSelected(false);
                cbProvince.setSelectedIndex(0);
                textArea.setText("");
            }
        });
        btnReset.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        btnReset.setBounds(315, 348, 153, 37);
        btnReset.setBackground(Color.lightGray);
        contentPane.add(btnReset);

        // 重写默认关闭方法
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
//				super.windowClosing(e);
                setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);    // 关闭当前窗口
                login.setVisible(true);
            }
        });
    }

    // 注册按钮
    public void registerButton() {
        Boolean flag = true;
        // 获取用户名
        String strUserName = this.userName.getText().trim();
        System.out.println("用户名：" + strUserName);
        // 获取密码
        char[] charArrPassWord = this.password.getPassword();
        String strPassWord = String.valueOf(charArrPassWord).trim();
        System.out.println("密码：" + strPassWord);
        // 获取确认密码
        char[] charArrPassWordComfirm = this.passwordComfirm.getPassword();
        String strPassWordComfirm = String.valueOf(charArrPassWordComfirm).trim();
        System.out.println("确认密码：" + strPassWordComfirm);
        // 判断两次密码输入是否相同
        if (!strPassWord.equals(strPassWordComfirm)) {
            flag = false;
        }

        // 获得用户选择的性别
        String strSex = (this.rdMale.isSelected() == true ? "男" : "女").trim();
        System.out.println("性别：" + strSex);

        // 获得爱好
        String strHobby = "";
        if (this.chkHobby1.isSelected()) {
            strHobby += this.chkHobby1.getText().trim() + ",";
        }
        if (this.chkHobby2.isSelected()) {
            strHobby += this.chkHobby2.getText().trim() + ",";
        }
        if (this.chkHobby3.isSelected()) {
            strHobby += this.chkHobby3.getText().trim() + ",";
        }
        try {
            System.out.println("爱好：" + strHobby.substring(0, strHobby.length() - 1));
        } catch (StringIndexOutOfBoundsException e) {
//			System.out.println("输入的密码不能为空"+e.getMessage());
        }

        // 获得籍贯
        String strProvince = this.cbProvince.getSelectedItem().toString();
        System.out.println("籍贯：" + strProvince);

        if (strUserName.equals("") || !strPassWord.equals(strPassWordComfirm) || strSex.equals("") ||
                strHobby.equals("") || strProvince.equals("--请选择--") || strProvince.equals("")) {
            flag = false;
        }

        // 当所有注册信息通过
        if (flag == true) {
            User user = new User();
            //在对话框中显示用户输入的数据
            String msg = "请确认你的注册信息:";
            msg += "\n用户名：" + strUserName;
            user.setName(strUserName);
            msg += "\n密  码：" + strPassWord;
            user.setPassword(strPassWord);
            msg += "\n性  别：" + strSex;
            user.setSex(strSex);
            msg += "\n爱  好：" + strHobby;
            user.setHobby(strHobby);
            msg += "\n籍  贯：" + strProvince;
            user.setProvince(strProvince);
            JOptionPane.showMessageDialog(null, msg);

            // 执行数据库，插入新用户
            UserManager userManager = new UserManager();
            boolean isCreateUser = true;
            try {
//                isCreateUser = userManager.createUser(user);
            } catch (Exception e) {
                e.printStackTrace();
            }

            // 关闭窗口，显示登陆界面
            if (isCreateUser == true) {
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            System.out.println("123");
                            dispose();
                            login.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            } else {
            }
        } else {
            System.out.println("请检查注册信息 注册失败(注册界面)");
        }
    }
}
