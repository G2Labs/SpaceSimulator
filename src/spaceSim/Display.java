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

import physics.SpaceObject;

public class Display extends JFrame implements KeyListener {
	private ArrayList<ArrayList<SpaceObject>> worldHistory = new ArrayList<>();
	private Double scale = 1.0;
	private Integer cnt = 0;
	private Integer historyLimit = 50;
	private static final Random R = new Random();
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
		protected synchronized void paintComponent(Graphics g) {
			super.paintComponent(g);
			Dimension screen = this.getSize();
			int cntOnScreen = 0;

			for (int i = 0; i < worldHistory.size(); i++) {
				boolean currentOne = (i == worldHistory.size() - 1);
				List<SpaceObject> currentWorld = worldHistory.get(i);

				for (int j = 0; j < currentWorld.size(); j++) {
					SpaceObject m = currentWorld.get(j);
					if (isObjectOnScreen(m, screen)) {
						drawCosmicElement(g, m, currentOne);
						if (currentOne)
							cntOnScreen++;
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("Scale:").append(String.format("%.1f", scale)).append(" ");
			sb.append("Objects: ").append(cntOnScreen).append(" ");
			sb.append("Iteration: ").append(cnt);
			g.setColor(Color.BLACK);
			g.drawString(sb.toString(), 10, (int) screen.getHeight() - 10);
		}
	}

	private boolean isObjectOnScreen(SpaceObject so, Dimension screen) {
		double x = so.getPosition().getX();
		double y = so.getPosition().getY();

		if ((x * scale > screen.getWidth()) || (x < 0))
			return false;
		if ((y * scale > (screen.getHeight() - 30)) || (y < 0))
			return false;
		return true;
	}

	private void drawCosmicElement(Graphics g, SpaceObject so, boolean isCurrent) {
		double minR = 3;
		double radius = (isCurrent) ? ((so.getMass() > 10) ? so.getMass() : minR) : minR;
		double x = (so.getPosition().getX()) - (radius / 2);
		double y = (so.getPosition().getY()) - (radius / 2);

		x *= scale;
		y *= scale;
		radius = (radius * scale < minR) ? minR : radius * scale;

		g.setColor(computeColorFromMass(so.getMass()));
		g.fillOval((int) x, (int) y, (int) radius, (int) radius);
	}

	private Color computeColorFromMass(double mass) {
		if (mass > 10)
			return Color.YELLOW;
		if (mass > 5)
			return Color.BLUE;
		if (mass > 2)
			return Color.DARK_GRAY;
		if (mass > 1)
			return Color.RED;
		return Color.BLACK;
	}

	public synchronized void insertNewData(Message message) {
		worldHistory.add((ArrayList<SpaceObject>) message.getData());
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
