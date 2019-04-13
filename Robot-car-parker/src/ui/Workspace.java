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

import geometry.Arc;
import geometry.Rectangle;
import geometry.Vector;
import simulator.ActionNode;
import simulator.Simulator;
import simulator.Simulator1D;
import simulator.Simulator2D;
import space.Car;

public class Workspace extends JPanel implements ActionListener
{
	private static final long serialVersionUID = 1L;
	private static final int DELAY = 100;
	
	public static final int DEFAULT_WIDTH = 700;
	public static final int DEFAULT_HEIGHT = 500;
	
	private Timer timer;
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	public Simulator simulator;
	public Thread simulatorThread;
	public List<Rectangle> obstacles;
	public Car car;
	public Rectangle goal;

	public Workspace()
	{
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));
        
        timer = new Timer(DELAY, this);
        timer.start();
	}
	
	public boolean hasCollision()
	{
		if(obstacles != null && car != null)
		{
			for(Rectangle r : obstacles)
			{
				if(car.collides(r))
				{
					return true;
				}
			}
		}
		return false;
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
			
			// New goal!
			goal = new Rectangle(250, 300, 50, 50);
			
			break;
		}
		case 2:
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
			car = new Car(70, 120, 100, new Vector(650, 150));
			car.angle = Math.PI;
			
			// New goal!
			goal = new Rectangle(250, 300, 50, 50);
			
			break;
		}
		case 3:
		{
			width = 700;
			height = 500;
			obstacles = new ArrayList<>();
			
			// Borders
			obstacles.add(new Rectangle(0, 0, 14, 0.5));
			obstacles.add(new Rectangle(0, 0, 0.5, 10));
			obstacles.add(new Rectangle(0, 9.5, 14, 0.5));
			obstacles.add(new Rectangle(13.5, 0, 0.5, 10));
			
			// Extra shapes
			obstacles.add(new Rectangle(0, 6, 4, 4));
			obstacles.add(new Rectangle(7, 6, 7, 4));
			
			// New car!
			car = new Car(2, 3.5, 2.4, new Vector(12, 3));
			car.angle = Math.PI;
			
			// New goal!
			goal = new Rectangle(5, 6.2, 1, 1);
			
			break;
		}
		case 4:
		{
			width = 1000;
			height = 500;
			obstacles = new ArrayList<>();
			
			// Borders
			obstacles.add(new Rectangle(0, 0, 20, 0.5));
			obstacles.add(new Rectangle(0, 0, 0.5, 8));
			obstacles.add(new Rectangle(0, 7.5, 20, 0.5));
			obstacles.add(new Rectangle(19.5, 0, 0.5, 8));
			
			// Extra shapes
			obstacles.add(new Rectangle(0, 5, 7, 3));
			obstacles.add(new Rectangle(15, 5, 6, 3));
			
			// New car!
			car = new Car(2, 3.5, 2.4, new Vector(1.5, 3));
			car.angle = 0;
			
			// New goal!
			goal = new Rectangle(7, 6, 0.8, 1);
			
			break;
		}
		}
		
		simulator = null;
		simulatorThread = null;
		this.setSize(width, height);
		repaint();
	}
	
	public void startSimulator(int i)
	{
		if(i == 1)
		{
			simulator = new Simulator2D(this);
		}
		if(i == 2)
		{
			simulator = new Simulator1D(this);
		}
		simulatorThread = new Thread(simulator);
		System.out.println("Starting simulator!");
		simulatorThread.start();
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
		if(goal != null)
		{
			g.setColor(new Color(140, 230, 150));
			goal.draw(g);
		}
		if(simulator != null)
		{
			if(simulator.getHead() != null)
			{
				g.setColor(new Color(230, 120, 110));
				//simulator.getHead().drawChildren(g);
			}
			if(simulator.getCurrent() != null)
			{
				g.setColor(new Color(30, 40, 240));
				//simulator.getCurrent().drawParent(g);
			}
		}
		if(car != null)
		{
			g.setColor(new Color(30, 40, 240));
			if(simulator != null && simulator.isFinished())
			{
				ActionNode iter = simulator.getCurrent();
				while(iter != null)
				{
					car.position = iter.position;
					car.angle = iter.theta;
					car.draw(g);
					iter = iter.parent;
				}
			}
			//g.setColor(new Color(160, 200, 240));
			//car.draw(g);
			//g.setColor(new Color(10, 10, 10));
			//car.position.draw(g);
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
        	case KeyEvent.VK_1: 
        	{
        		setWorkspace(1);
        		break;
        	}
        	case KeyEvent.VK_2:
        	{
        		setWorkspace(2);
        		break;
        	}
        	case KeyEvent.VK_3:
        	{
        		setWorkspace(3);
        		break;
        	}
        	case KeyEvent.VK_4:
        	{
        		setWorkspace(4);
        		break;
        	}
        	case KeyEvent.VK_S: 
    		{
    			startSimulator(1);
    			break;
    		}
        	case KeyEvent.VK_D:
        		startSimulator(2);
        		break;
        	}
        }
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (simulator != null)
		{
			//System.out.println("Repaint");
			repaint();
		}
	}
}
