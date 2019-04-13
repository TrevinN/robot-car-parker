package geometry;

import java.awt.Graphics;

public class Vector extends Collidable
{
	private static double EPSILON = 1e-6;
	
	public final double x;
	public final double y;
	
	public Vector(double x, double y)
	{
		assert x != Double.NaN;
		assert y != Double.NaN;
		
		this.x = x;
		this.y = y;
	}
	
	public Vector add(Vector v)
	{
		return new Vector(this.x + v.x, this.y + v.y);
	}
	public Vector add(double x, double y)
	{
		return new Vector(this.x + x, this.y + y);
	}

	public Vector subtract(Vector v)
	{
		return new Vector(this.x - v.x, this.y - v.y);
	}
	public Vector subtract(double x, double y)
	{
		return new Vector(this.x - x, this.y - y);
	}

	public Vector multiply(Vector v)
	{
		return new Vector(this.x * v.x, this.y * v.y);
	}
	public Vector multiply(double x, double y)
	{
		return new Vector(this.x * x, this.y * y);
	}

	public Vector divide(Vector v)
	{
		return new Vector(this.x / v.x, this.y / v.y);
	}
	public Vector divide(double x, double y)
	{
		return new Vector(this.x / x, this.y / y);
	}
	
	public double dot(Vector v)
	{
		return this.x * v.x + this.y * v.y;
	}
	public double cross(Vector v)
	{
		return this.x * v.y - this.y * v.x;
	}
	public double magnitude()
	{
		return Math.sqrt(x*x + y*y);
	}

	public Vector rotate(double angle)
	{
		double qx = x*Math.cos(angle) - y*Math.sin(angle);
		double qy = x*Math.sin(angle) + y*Math.cos(angle);
		return new Vector(qx, qy);
	}
	public Vector rotate(double angle, Vector origin)
	{
		return this.subtract(origin).rotate(angle).add(origin);
	}

	public double angle()
	{
		return Math.atan2(y, x);
	}
	
	public boolean equals(Object o)
	{
		if(o == null || o.getClass() != this.getClass())
			return false;
		
		Vector v = (Vector) o;
		double dx = this.x - v.x;
		double dy = this.y - v.y;
		
		return dx < EPSILON && dx > -EPSILON && dy < EPSILON && dy > -EPSILON;
	}
	public String toString()
	{
		return "(" + x + ", " + y + ")";
	}
	public void draw(Graphics g)
	{
		int px = ((int) (x * Scale.SCALE)) - 3;
		int py = ((int) (y * Scale.SCALE)) - 3;
		g.fillOval(px, py, 6, 6);
	}
	
	public static void setEpsilon(double e)
	{
		EPSILON = e;
	}	
}
