package spaceSim;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Display extends JFrame implements KeyListener {
	private ArrayList<ArrayList<Dot>> worldHistory = new ArrayList<>();
	private Double scale = 1.0;
	private Integer cnt = 0;
	private Integer historyLimit = 200;
	private Random R = new Random();
	private DrawArea drawArea;
	private boolean canLoad = true;

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
			sb.append("Skala:").append(scale).append(" ");
			sb.append("Obiektów: ").append(size).append(" ");
			sb.append("Iteracja: ").append(cnt);
			g.drawString(sb.toString(), 10, h - 10);

			canLoad = false;
			for (int i = 0; i < worldHistory.size(); i++) {
				boolean currentOne = (i == 0);
				List<Dot> currentWorld = worldHistory.get(i);
				
				for(int j = 0; j<currentWorld.size();j++){
					Dot dot = currentWorld.get(j);
					int x = (int) dot.getX().doubleValue();
					int y = (int) dot.getY().doubleValue();
					int r = (int) dot.getRadius().doubleValue();		
					r *= 2;
					x -= r;
					y -= r;
					if ((x > w) || (x < 0))
						continue;
					if ((y > (h - 30)) || (y < 0))
						continue;
					g.setColor(dot.getColor());
					if(!currentOne)
						r=2;
					g.fillOval(x, y, r, r);			
				}
			}
			canLoad = true;
		}
	}

	public void insertNewData(Message message) {
		while (!canLoad) {
		}
		worldHistory.add(0, (ArrayList<Dot>) message.getData());
		if (worldHistory.size() > historyLimit) {
			worldHistory.remove(R.nextInt(historyLimit / 2) + (historyLimit / 2));
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
