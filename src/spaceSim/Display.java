package spaceSim;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame implements KeyListener {
	private ArrayList<ArrayList<Dot>> worldHistory = new ArrayList<>();
	private Double scale = 1.0;
	private Random R = new Random();
	private DrawArea drawArea;

	public Display() {
		drawArea = new DrawArea();
		this.setContentPane(drawArea);
		this.setResizable(false);
		pack();
		this.setVisible(true);
		addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public class DrawArea extends JPanel {
		public DrawArea() {
			setPreferredSize(new Dimension(1000, 800));
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			int h = (int) this.getSize().getHeight();
			int size = 0;
			if (!worldHistory.isEmpty()) {
				size = worldHistory.get(0).size();
				
				g.drawOval(1, 1, 1, 1);
			}

			g.drawString("Skala: " + String.format("%.1f", scale) + " Obiektów: " + size, 10, h - 10);
		}
	}

	public void insertNewData(Message message) {
		worldHistory.add((ArrayList<Dot>) message.getData());
		if (worldHistory.size() > 200) {
			worldHistory.remove(R.nextInt(100) + 100);
		}
		this.repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			if (scale < 10.0)
				scale += 0.1;
			break;
		case KeyEvent.VK_S:
			if (scale > 0.1)
				scale -= 0.1;
			break;
		default:
			break;
		}
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}
}
