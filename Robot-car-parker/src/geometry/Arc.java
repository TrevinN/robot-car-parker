package geometry;

import java.awt.Graphics;

public class Arc extends Collidable
{
	//public final Vector start;
	//public final double startAngle;
	//public final Vector end;
	//public final double endAngle;
	//public final double rho;
	//public final double length;
	
	public final Vector center;
	public final double radius;
	public final double startAngle;
	public final double arcAngle;
	public final Vector startPoint;
	public final Vector endPoint;
	
	public Arc(Vector center, double radius, double startAngle, double arcAngle)
	{
		if(center == null)
			throw new NullPointerException("Arc's center cannot be null");
		if(radius <= 0.0)
			throw new IllegalArgumentException("Arc must have a positive radius");
		
		startAngle = startAngle % Math.PI;
		
		this.center = center;
		this.radius = radius;
		this.startAngle = startAngle;
		this.arcAngle = arcAngle;
		
		double x,y;
		x = Math.cos(startAngle)*radius;
		y = Math.sin(startAngle)*radius;
		this.startPoint = center.add(x,y);
		x = Math.cos(arcAngle)*radius;
		y = Math.sin(arcAngle)*radius;
		this.endPoint = center.add(x,y);
		
	}
	
	public void draw(Graphics g)
	{
		System.out.println("Drawing arc");
		g.drawArc(
				(int) (center.x * Scale.SCALE), 
				(int) (center.y * Scale.SCALE), 
				(int) (radius * Scale.SCALE), 
				(int) (radius * Scale.SCALE), 
				(int) (startAngle/Math.PI*360), 
				(int) (arcAngle/Math.PI*360));
	}
	
	public boolean collides(Rectangle r)
	{
		return r.collides(this);
	}
	
	public boolean collides(LineSegment l)
	{
		Vector d = l.v1.subtract(l.v0);
		Vector f = l.v0.subtract(center);
		
		double a = d.dot(d);
		double b = 2*f.dot(d);
		double c = f.dot(f) - radius*radius;
		
		double discriminant = b*b - 4*a*c;
		if(discriminant < 0)
		{
			return false;
		}
		else
		{
			discriminant = Math.sqrt(discriminant);
			
			double t1 = (-b - discriminant) / (2*a);
			double t2 = (-b + discriminant) / (2*a);
			
			if(t1 >= 0 && t1 <= 1)
			{
				Vector p = l.v0.add(d.multiply(t1, t1));
				double angle = p.subtract(center).angle();
				return true;
			}
			if(t2 >= 0 && t2 <= 1)
			{
				return true;
			}
			return false;
		}
	}
}
