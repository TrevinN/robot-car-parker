package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;

import geometry.Rectangle;

public class Workspace extends JPanel
{
	public static final int DEFAULT_WIDTH = 200;
	public static final int DEFAULT_HEIGHT = 200;
	
	private int width = DEFAULT_WIDTH;
	private int height = DEFAULT_HEIGHT;

	List<Rectangle> obstacles;
	Rectangle car;

	public Workspace()
	{
        addKeyListener(new TAdapter());
        setBackground(Color.black);
        setFocusable(true);

        setPreferredSize(new Dimension(width, height));
	}
	
	public void setWorkspace(int id)
	{
		obstacles = null;
		car = null;
		
		switch(id)
		{
		case 1: 
			{
				width = 700;
				height = 500;
				obstacles = new ArrayList<>();
				obstacles.add(new Rectangle(0, 0, ));
				
			}
		}
		
		setPreferredSize(new Dimension(width, height));
	}

	public void draw(Graphics g)
	{
		
	}
	
	private class TAdapter extends KeyAdapter {
		
	}
}
