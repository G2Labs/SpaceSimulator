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

import physics.MassObject;
import physics.SpaceObject;
import physics.Vector2D;

public class Display extends JFrame implements KeyListener {
	private ArrayList<ArrayList<SpaceObject>> worldHistory = new ArrayList<>();
	private Double scale = 1.0;
	private Vector2D center;
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
		center = new Vector2D(getWidth() / 2, this.getHeight() / 2);
	}

	public class DrawArea extends JPanel {
		public DrawArea() {
			setPreferredSize(new Dimension(1000, 800));
			setBackground(Color.BLACK);
		}

		@Override
		protected synchronized void paintComponent(Graphics g) {
			super.paintComponent(g);
			int cntOnScreen = 0;

			for (int i = 0; i < worldHistory.size(); i++) {
				boolean currentOne = (i == worldHistory.size() - 1);
				List<SpaceObject> currentWorld = worldHistory.get(i);

				for (int j = 0; j < currentWorld.size(); j++) {
					SpaceObject m = currentWorld.get(j);
					if (isObjectOnScreen(m)) {
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
			g.setColor(Color.GRAY);
			g.drawString(sb.toString(), 10, this.getHeight() - 10);
		}
	}

	private boolean isObjectOnScreen(SpaceObject so) {
		Vector2D v = transformObjectCoordToScreenCoord(so);
		if ((v.getX() < 0) || (v.getX() > this.getWidth()))
			return false;
		if ((v.getY() > this.getHeight()) || (v.getY() < 0))
			return false;
		return true;
	}

	private Vector2D transformObjectCoordToScreenCoord(SpaceObject so) {
		double x = so.getPosition().getX();
		double y = so.getPosition().getY();

		return center.add(new Vector2D(x, -y).mul(scale));
	}

	private void drawCosmicElement(Graphics g, SpaceObject so, boolean isCurrent) {
		double minR = 2, maxR = 10;
		double radius = (isCurrent) ? ((so.getMass() > maxR) ? so.getMass() : minR) : minR;
		if (so.getName().contains("Sun"))
			radius *= scale;
		else
			radius = ((radius > maxR) ? maxR : radius) * scale;
		radius = (radius < minR) ? minR : radius;
		drawOvalInSpace(g, so, radius);
	}

	private void drawOvalInSpace(Graphics g, SpaceObject so, double radius) {
		Vector2D v = transformObjectCoordToScreenCoord(so).sub(new Vector2D(radius / 2, radius / 2));
		g.setColor(computeColorFromMass(so.getMass()));
		g.fillOval((int) v.getX(), (int) v.getY(), (int) radius, (int) radius);
	}

	private Color computeColorFromMass(double mass) {
		if (mass > 10)
			return Color.YELLOW;
		if (mass > 5)
			return Color.CYAN;
		if (mass > 2)
			return Color.LIGHT_GRAY;
		if (mass > 1)
			return Color.RED;
		return Color.WHITE;
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
			if (scale > 0.2)
				scale -= 0.1;
			break;
		case KeyEvent.VK_RIGHT:
			center = center.add(new Vector2D(-10, 0));
			break;
		case KeyEvent.VK_LEFT:
			center = center.add(new Vector2D(10, 0));
			break;
		case KeyEvent.VK_UP:
			center = center.add(new Vector2D(0, 10));
			break;
		case KeyEvent.VK_DOWN:
			center = center.add(new Vector2D(0, -10));
			break;
		case KeyEvent.VK_R:
			center = new Vector2D(getWidth() / 2, getHeight() / 2);
			scale = 1.0;
			break;
		case KeyEvent.VK_T:
			MassObject.setDeltaT(1);
			break;
		case KeyEvent.VK_G:
			MassObject.setDeltaT(0.2);
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
