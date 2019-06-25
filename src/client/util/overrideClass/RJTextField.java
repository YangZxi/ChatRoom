/**
 * Copyright: 2019-2019，小树苗(www.xiaosm.cn)
 * FileName: RJTextField
 * Author:   OuYoung
 * Date:     2019/06/24 15:05
 * Description:
 * History:
 * <author>          <time>          <version>          <desc>
 * OuYoung         修改时间           版本号             描述
 */
package client.util.overrideClass;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;
import java.awt.Shape;

import javax.swing.JTextField;

/**
 * 〈一句话功能简述〉 〈〉
 *
 * @author OuYoung
 * @create 2019/06/24
 * @since 1.0.0
 */
public class RJTextField extends JTextField {

	private static final long serialVersionUID = 1410323255956414725L;

	public RJTextField(int columns) {
		super(columns);
		setMargin(new Insets(0, 2, 0, 2));
		setBorder(null);
	}

	@Override
	protected void paintBorder(Graphics g) {
		int h = getHeight();// 从JComponent类获取高宽
		int w = getWidth();
		Graphics2D g2d = (Graphics2D) g.create();
		Shape shape = g2d.getClip();
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setClip(shape);
		g2d.drawRoundRect(0, 1, w - 4, h - 2, h, h);
		super.paintBorder(g2d);
	}

}