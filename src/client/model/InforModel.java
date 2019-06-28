/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: inforModel
 * Author:   OuYoung
 * Date:     2019/06/25 19:41
 * Description: 消息通知的UI
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           版本号             描述
 */
package client.model;

import javax.swing.*;

import client.service.Message;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * 〈一句话功能简述〉
 * 〈消息通知的UI〉
 *
 * @author OuYoung
 * @create 2019/06/25
 * @since 1.0.0
 */
public class InforModel extends JPanel {

    private Message message;
    private JLabel infor_lbl;
    private JLabel headIcon;
    private String from_id;
    private String from_name;
    private String to_id;
    private int type;
    private JLabel re_lbl;
    private JButton agree_btn;
    private JButton refuse_btn;

    public void setMessage(Message message) {
        this.message = message;
    }

    public JLabel getInfor_lbl() {
        return infor_lbl;
    }

    public JLabel getHeadIcon() {
        return headIcon;
    }

    public static void main(String[] args) {
        JFrame j = new JFrame();
        j.setBounds(100, 100, 1000, 200);
        j.setVisible(true);
        InforModel i = new InforModel();
        j.getContentPane().add(i);
    }

    public InforModel() {
        this.init();
    }

    public InforModel(String from_id, String from_name, String to_id, int type) {
        this.from_id = from_id;
        this.from_name = from_name;
        this.to_id = to_id;
        this.type = type;
        this.init();
        if (type == 0) {
            try {
                headIcon.setIcon(new ImageIcon(InforModel.class.getResource("/client/images/" + from_id + ".jpg")));
            }catch (NullPointerException e) {
                headIcon.setIcon(new ImageIcon(InforModel.class.getResource("/client/images/000000.jpg")));
            }
        }else if (type == 1) {
            headIcon.setIcon(new ImageIcon(InforModel.class.getResource("/client/images/535251.jpg")));
        }else if (type == 3) {
            this.remove(re_lbl);
            this.remove(agree_btn);
            this.remove(refuse_btn);
            headIcon.setIcon(new ImageIcon(InforModel.class.getResource("/client/images/535251.jpg")));
        }
    }

    public void init() {
        this.setBounds(20, 5, 800, 70);
        this.setLayout(null);
        this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(179, 179, 179)));
        this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                // 设置被选中的背景色
//				personPanel.setBackground(new Color(191, 191, 191));
                // 设置当前的背景
                setBackground(new Color(191, 191, 191));
            }

            @Override
            public void mouseEntered(MouseEvent e) {
//				// 设置当前的背景
                setBackground(new Color(191, 191, 191));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(new Color(240, 240, 240));
            }
        });

        headIcon = new JLabel("头像");
        headIcon.setIcon(new ImageIcon(InforModel.class.getResource("/client/images/000000.jpg")));
        headIcon.setBounds(35, 10, 50, 50);
        add(headIcon);

        infor_lbl = new JLabel("不是木易杨请求添加你为好友");
        infor_lbl.setFont(new Font("微软雅黑", Font.PLAIN, 18));
        infor_lbl.setBounds(99, 17, 512, 35);
        add(infor_lbl);

        re_lbl = new JLabel("已忽略");
        re_lbl.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        re_lbl.setBounds(715, 25, 50, 18);
        re_lbl.setVisible(false);
        add(re_lbl);

        agree_btn = new JButton("同意");
        agree_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                agree_btn.setVisible(false);
                refuse_btn.setVisible(false);
                re_lbl.setText("已同意");
                re_lbl.setVisible(true);
                message.agreeSend(from_id, from_name, to_id, type);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });

        agree_btn.setForeground(Color.WHITE);
        agree_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        agree_btn.setBackground(new Color(105, 155, 178));
        agree_btn.setBounds(625, 20, 70, 30);
        agree_btn.setBorder(null);
        agree_btn.setFocusable(false);
        add(agree_btn);

        refuse_btn = new JButton("拒绝");
        refuse_btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent arg0) {
                agree_btn.setVisible(false);
                refuse_btn.setVisible(false);
                re_lbl.setText("已拒绝");
                re_lbl.setVisible(true);
                message.refuseSend(from_id, from_name, to_id, type);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
        refuse_btn.setForeground(Color.WHITE);
        refuse_btn.setFont(new Font("微软雅黑", Font.PLAIN, 16));
        refuse_btn.setBackground(new Color(105, 155, 178));
        refuse_btn.setBounds(716, 20, 70, 30);
        refuse_btn.setBorder(null);
        refuse_btn.setFocusable(false);
        add(refuse_btn);
    }

}
