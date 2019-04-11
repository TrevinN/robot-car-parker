package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

import geometry.Rectangle;
import geometry.Vector;
import space.Car;

public class Workspace extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final int DELAY = 50;
	
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 500;
	
	private Timer timer;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	List<Rectangle> obstacles;
	Car car;
	Rectangle goal;

	public Workspace()
	{
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));
        
        timer = new Timer(DELAY, this);
        timer.start();
	}
	
	public void setWorkspace(int id)
	{
		System.out.println("Setting workspace to " + id);
		obstacles = null;
		car = null;
		
		switch(id)
		{
		case 1:
		{
			width = 700;
			height = 500;
			obstacles = new ArrayList<>();
			
			// Borders
			obstacles.add(new Rectangle(0, 0, 700, 10));
			obstacles.add(new Rectangle(0, 0, 10, 500));
			obstacles.add(new Rectangle(0, 490, 700, 10));
			obstacles.add(new Rectangle(690, 0, 10, 500));
			
			// Extra shapes
			obstacles.add(new Rectangle(0, 300, 200, 200));
			obstacles.add(new Rectangle(350, 300, 350, 200));
			
			// New car!
			car = new Car(90, 170, 100, new Vector(650, 250));
			car.angle = Math.PI;
			break;
		}
		}
		
		this.setSize(width, height);
		repaint();
	}
	
	public void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		g.setColor(new Color(230, 230, 230));
		g.fillRect(0, 0, width, height);
		if(obstacles != null)
		{
			g.setColor(new Color(100, 80, 60));
			for(Rectangle r : obstacles)
			{
				r.draw(g);
			}
		}
		if(car != null)
		{
			g.setColor(new Color(160, 200, 240));
			car.draw(g);
			g.setColor(new Color(10, 10, 10));
			car.position.draw(g);
		}
		if(goal != null)
		{
			g.setColor(new Color(140, 230, 150));
			goal.draw(g);
		}
	}
	
	private class TAdapter extends KeyAdapter
	{
		@Override
        public void keyPressed(KeyEvent e)
        {
        	int key = e.getKeyCode();
        	
        	System.out.println("Key press");
        	
        	switch(key)
        	{
        	case KeyEvent.VK_1: setWorkspace(1);
        	}
        }
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		
	}
}
