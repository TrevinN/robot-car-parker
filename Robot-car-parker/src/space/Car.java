package space;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import geometry.Arc;
import geometry.Collidable;
import geometry.LineSegment;
import geometry.Rectangle;
import geometry.Scale;
import geometry.Vector;

public class Car extends Collidable
{
	public static final double TURNING_ANGLE = Math.PI / 6;
	public static final double MAX_VELOCITY = 10.0;
	public static final double MAX_ACCELERATION = 2.0;
	
	public final double width; 	// Width of the car frame
	public final double length; // Length of the car frame
	public final double L; 		// Distance between rear and front wheels along the length of the car
	
	public double angle;		// Angle of the car
	public Vector position;	// Position of the back wheels of the car
	
	private final double G; 	// Distance from the back of the car frame to the rear wheels
	
	public Car(Vector position)
	{
		this(30, 80, 50, position);
	}
	
	public Car(double width, double length, double L, Vector position)
	{
		this.width = width;
		this.length = length;
		this.L = L;
		this.G = (length - L)/2;
		this.position = position;
	}

	public synchronized double getAngle()
	{
		return angle;
	}
	
	public synchronized Vector getPosition()
	{
		return position;
	}
	
	public synchronized void setAngle(double angle)
	{
		this.angle = angle;
	}
	
	public synchronized void setPosition(Vector position)
	{
		this.position = position;
	}
	
	public double calcTurningRadius(double angle)
	{
		return L / Math.tan(angle);
	}
	
	public synchronized List<Vector> getPoints()
	{
		List<Vector> out = new ArrayList<>();
		
		double a = (length - L) / 2;
		out.add( position.add(-a, -width/2)		.rotate(angle, position) );
		out.add( position.add(-a, width/2)		.rotate(angle, position) );
		out.add( position.add(a + L, width/2)	.rotate(angle, position) );
		out.add( position.add(a + L, -width/2)	.rotate(angle, position) );
		
		return out;
	}
	
	public synchronized List<LineSegment> getLineSegments()
	{
		List<LineSegment> out = new ArrayList<>();
		List<Vector> points = getPoints();
		
		for(int i = 0; i < points.size(); i++)
		{
			out.add(new LineSegment(
					points.get(i), 
					points.get((i+1)%points.size())
					));
		}
		return out;
	}
	
	public synchronized void draw(Graphics g)
	{
		List<Vector> points = getPoints();
		int[] x = new int[points.size()];
		int[] y = new int[points.size()];
		for(int i = 0; i < points.size(); i++)
		{
			x[i] = (int) (points.get(i).x * Scale.SCALE);
			y[i] = (int) (points.get(i).y * Scale.SCALE);
		}
		g.drawPolygon(x, y, points.size());
	}

	public synchronized boolean collides(Rectangle r)
	{
		for(LineSegment l : getLineSegments())
		{
			if(r.collides(l))
			{
				return true;
			}
		}
		return false;
	}
}
