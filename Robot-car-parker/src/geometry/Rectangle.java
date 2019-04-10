package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Rectangle extends Collidable
{
	public Vector v0;
	public Vector v1;
	
	public Rectangle(double x, double y, double width, double height)
	{
		this(new Vector(x, y), new Vector(x + width, y + height));
	}
	
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

	public List<LineSegment> getSegments()
	{
		List<LineSegment> out = new ArrayList<>(4);
		out.add(new LineSegment(new Vector(v0.x, v0.y), new Vector(v0.x, v1.y)));
		out.add(new LineSegment(new Vector(v0.x, v1.y), new Vector(v1.x, v1.y)));
		out.add(new LineSegment(new Vector(v1.x, v1.y), new Vector(v1.x, v0.y)));
		out.add(new LineSegment(new Vector(v1.x, v0.y), new Vector(v0.x, v0.y)));
		return out;
	}
	
	public void draw(Graphics g)
	{
		g.drawRect((int) v0.x, (int) v0.y, (int) width(), (int) height());
	}
	
	public boolean collides(Rectangle r)
	{
		return (v0.x < r.v1.x && v1.x > r.v0.x && v0.y < r.v1.y && v1.y > r.v0.y);
	}
	
	public boolean collides(Arc a)
	{
		if(collides(a.startPoint) || collides(a.endPoint))
		{
			return true;
		}
		for(LineSegment l : getSegments())
		{
			if(l.collides(a))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean collides(LineSegment l)
	{
		if(collides(l.v0) || collides(l.v1))
		{
			return true;
		}
		for(LineSegment lp : getSegments())
		{
			if(l.collides(lp))
			{
				return true;
			}
		}
		return false;
	}
	
	public boolean collides(Vector v)
	{
		return (v.x >= v0.x && v.x <= v1.x && v.y >= v0.y && v.y <= v1.y);
	}
}
