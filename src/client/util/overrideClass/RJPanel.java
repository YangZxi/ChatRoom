package client.util.overrideClass;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RJPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public RJPanel() {
		super();
		setOpaque(true);
		setSize(80, 30);
//		setBackground(new Color(204, 204, 204));
		JLabel nameLabel = new JLabel("", JLabel.CENTER);
//		nameLabel.setForeground(Color.white);
		nameLabel.setBounds(0, 0, 80, 30);
		nameLabel.setAlignmentY(0.1f);
		add(nameLabel);
	}

	@Override
	public void paint(Graphics g) {
		int fieldX = 0;
		int fieldY = 0;
		int fieldWeight = getSize().width;
		int fieldHeight = getSize().height;
		RoundRectangle2D rect = new RoundRectangle2D.Double(fieldX, fieldY, fieldWeight, fieldHeight, 100, 100);
		g.setClip(rect);
		super.paint(g);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("圆角面板");
		frame.setLayout(null);
		frame.setBounds(500, 300, 500, 500);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		RJPanel panel = new RJPanel();
		panel.setLocation(100, 100);
		frame.add(panel);
		frame.setVisible(true);
	}
}