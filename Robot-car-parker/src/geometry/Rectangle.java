package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends Collidable
{
	public Vector v0;
	public Vector v1;
	
	public Rectangle(Vector v0, Vector v1)
	{
		double x0 = Math.min(v0.x, v1.x);
		double y0 = Math.min(v0.y, v1.y);
		double x1 = Math.max(v0.y, v1.y);
		double y1 = Math.max(v0.y, v1.y);
		v0 = new Vector(x0, y0);
		v1 = new Vector(x1, y1);
	}
	
	public double width()
	{
		return v1.x - v0.x;
	}
	
	public double height()
	{
		return v1.y - v0.y;
	}

	public void draw(Graphics g, Color c)
	{
		g.setColor(c);
		g.drawRect((int) v0.x, (int) v0.y, (int) width(), (int) height());
	}
}
