package spaceSim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame{
	private ArrayList<ArrayList<Dot>> worldHistory = new ArrayList<>();
	private Integer cnt = 0;
	private Random R = new Random();

	public Display() {
		DrawArea drawArea = new DrawArea();
		this.setContentPane(drawArea);
		this.setResizable(false);
		pack();
		this.setVisible(true);
	}

	public class DrawArea extends JPanel {
		public DrawArea() {
			setPreferredSize(new Dimension(1000, 800));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("Kolejny napis", 0, cnt * 20);
		}
	}

	public void getData(List<Dot> world) {
		worldHistory.add((ArrayList<Dot>) world);
		if (worldHistory.size() > 200) {
			worldHistory.remove(R.nextInt(100) + 100);
		}
		this.repaint();
	}
}
