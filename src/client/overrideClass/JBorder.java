package client.overrideClass;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.border.Border;

/**
 * Swing
 * 设置圆角边框（可以自定义边框的颜色）
 * 可以为button，文本框等人以组件添加边框
 * 使用方法：
 * JButton close = new JButton(" 关 闭 ");
 * close.setOpaque(false);// 设置原来按钮背景透明
 * close.setBorder(new RoundBorder());黑色的圆角边框
 * close.setBorder(new RoundBorder(Color.RED)); 红色的圆角边框
 * 
 * @author Monsoons
 */

public class JBorder implements Border {
    private Color color;

    private int arcH = 15;
    private int arcW = 15;

    public JBorder(int arcH,int arcW) {
    	this.arcH = arcH;
    	this.arcW = arcW;
    	this.color = Color.BLACK;
        // 如果实例化时，没有传值
        // 默认是黑色边框
    }

    public JBorder(int arcH,int arcW,Color color) {
    	this.arcH = arcH;
    	this.arcW = arcW;
        this.color = color;
    }

    public Insets getBorderInsets(Component c) {

        // top:可以调节光标与边枉的距离, 间接影响高度
        // left:可以调节光标与边枉的距离
        // bottom:可以调节光标与边枉的距离, 间接影响高度
        // right:可以调节光标与边枉的距离
        return new Insets(10, 15, 10, 15);
    }

    public boolean isBorderOpaque() {
        return false;
    }

    // 实现Border（父类）方法
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        //        g.setColor(color);
        //        g.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcH, arcW);

        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(color);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.drawRoundRect(0, 0, c.getWidth() - 1, c.getHeight() - 1, arcH, arcW);

        g2d.dispose();
    }
}