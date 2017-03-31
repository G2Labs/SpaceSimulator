package spaceSim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

import physics.Mover;

public class Display extends JFrame implements KeyListener {
	private ArrayList<ArrayList<Mover>> worldHistory = new ArrayList<>();
	private Double scale = 1.0;
	private Integer cnt = 0;
	private Integer historyLimit = 50;
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
			int w = (int) this.getSize().getWidth();
			int size = 0;

			if (!worldHistory.isEmpty())
				size = worldHistory.get(0).size();

			StringBuilder sb = new StringBuilder();
			sb.append("Skala:").append(String.format("%.1f", scale)).append(" ");
			sb.append("Obiektów: ").append(size).append(" ");
			sb.append("Iteracja: ").append(cnt);
			g.drawString(sb.toString(), 10, h - 10);

			synchronized (this) {
				for (int i = 0; i < worldHistory.size(); i++) {
					boolean currentOne = (i == worldHistory.size() - 1);
					List<Mover> currentWorld = worldHistory.get(i);

					for (int j = 0; j < currentWorld.size(); j++) {
						Mover m = currentWorld.get(j);
						int x = (int) m.getPosition().getX();
						int y = (int) m.getPosition().getY();
						int r = (int) m.getMass();
						if ((x > w) || (x < 0))
							continue;
						if ((y > (h - 30)) || (y < 0))
							continue;
						drawCosmicElement(g, x, y, r, currentOne);
					}
				}
			}
		}
	}

	private void drawCosmicElement(Graphics g, int x, int y, int mass, boolean isCurrent) {
		int radius = (isCurrent) ? mass : 2;
		g.setColor(computeColorFromMass(mass));
		x -= radius / 2;
		y -= radius / 2;
		g.setColor(computeColorFromMass(radius));
		g.fillOval(x, y, radius, radius);
	}

	private Color computeColorFromMass(double mass) {
		if (mass > 10)
			return Color.YELLOW;
		if (mass > 5)
			return Color.BLUE;
		if (mass > 2)
			return Color.GREEN;
		if (mass > 1)
			return Color.RED;
		return Color.BLACK;
	}

	public synchronized void insertNewData(Message message) {
		worldHistory.add((ArrayList<Mover>) message.getData());
		if (worldHistory.size() > historyLimit) {
			worldHistory.remove(R.nextInt(historyLimit / 2));
		}
		cnt++;
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
