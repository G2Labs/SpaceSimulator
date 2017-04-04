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
	private static final Random R = new Random();
	private static final int historyLimit = 50;
	private DrawArea drawArea;
	private ArrayList<List<SpaceObject>> worldHistory = new ArrayList<>();
	private Vector2D centerOfScreen;
	private double scaleOfScreen = 1.0;
	private int iterationCnt = 0;

	public Display() {
		drawArea = new DrawArea();
		this.setContentPane(drawArea);
		pack();

		this.setResizable(false);
		addKeyListener(this);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		centerOfScreen = new Vector2D(getWidth() / 2, this.getHeight() / 2);
		this.setVisible(true);
	}

	public synchronized void insertNewData(List<SpaceObject> message) {
		worldHistory.add(message);
		if (worldHistory.size() > historyLimit) {
			worldHistory.remove(R.nextInt(historyLimit / 2));
		}
		iterationCnt++;
		this.repaint();
	}

	private class DrawArea extends JPanel {
		public DrawArea() {
			setPreferredSize(new Dimension(1000, 800));
			setBackground(Color.BLACK);
		}

		@Override
		protected synchronized void paintComponent(Graphics g) {
			super.paintComponent(g);
			int cntObjs = worldHistory.get(worldHistory.size() - 1).size();
			for (int i = 0; i < worldHistory.size(); i++) {
				boolean currentOne = (i == worldHistory.size() - 1);
				List<SpaceObject> currentWorld = worldHistory.get(i);

				for (int j = 0; j < currentWorld.size(); j++) {
					SpaceObject m = currentWorld.get(j);
					if (isObjectOnScreen(m)) {
						drawCosmicElement(g, m, currentOne);
					}
				}
			}

			StringBuilder sb = new StringBuilder();
			sb.append("Scale:").append(String.format("%.1f", scaleOfScreen)).append(" ");
			sb.append("Objects: ").append(cntObjs).append(" ");
			sb.append("Iteration: ").append(iterationCnt);
			g.setColor(Color.GRAY);
			g.drawString(sb.toString(), 10, this.getHeight() - 10);
		}
	}

	private boolean isObjectOnScreen(SpaceObject so) {
		Vector2D v = transformObjectCoordToScreenCoord(so);
		if ((v.getX() < 0) || (v.getX() > this.getWidth()))
			return false;
		if ((v.getY() < 0) || (v.getY() > this.getHeight()))
			return false;
		return true;
	}

	private Vector2D transformObjectCoordToScreenCoord(SpaceObject so) {
		double x = so.getPosition().getX();
		double y = so.getPosition().getY();

		return centerOfScreen.add(new Vector2D(x, -y).mul(scaleOfScreen));
	}

	private void drawCosmicElement(Graphics g, SpaceObject so, boolean isCurrent) {
		double minRadius = 2;
		double radius = minRadius;
		if (isCurrent) {
			if (so.getName().contains("Sun"))
				radius = 25;
			else
				radius = 6;
		}
		radius *= scaleOfScreen;
		radius = (radius < minRadius) ? minRadius : radius;
		drawOvalInSpace(g, so, radius);
	}

	private void drawOvalInSpace(Graphics g, SpaceObject so, double radius) {
		Vector2D v = transformObjectCoordToScreenCoord(so).sub(new Vector2D(radius / 2, radius / 2));
		g.setColor(computeColorFromMass(so));
		g.fillOval((int) v.getX(), (int) v.getY(), (int) radius, (int) radius);
	}

	private Color computeColorFromMass(SpaceObject so) {
		if (so.getName().contains("Sun"))
			return Color.YELLOW;
		if (so.getMass() > 10)
			return Color.MAGENTA;
		if (so.getMass() > 5)
			return Color.RED;
		if (so.getMass() > 2)
			return Color.CYAN;
		if (so.getMass() > 1)
			return Color.LIGHT_GRAY;
		return Color.WHITE;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_Q:
			if (scaleOfScreen < 10.0)
				scaleOfScreen += 0.1;
			break;
		case KeyEvent.VK_Z:
			if (scaleOfScreen > 0.2)
				scaleOfScreen -= 0.1;
			break;
		case KeyEvent.VK_D:
			centerOfScreen = centerOfScreen.add(new Vector2D(-10, 0));
			break;
		case KeyEvent.VK_A:
			centerOfScreen = centerOfScreen.add(new Vector2D(10, 0));
			break;
		case KeyEvent.VK_W:
			centerOfScreen = centerOfScreen.add(new Vector2D(0, 10));
			break;
		case KeyEvent.VK_S:
			centerOfScreen = centerOfScreen.add(new Vector2D(0, -10));
			break;
		case KeyEvent.VK_R:
			centerOfScreen = new Vector2D(getWidth() / 2, getHeight() / 2);
			scaleOfScreen = 1.0;
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
