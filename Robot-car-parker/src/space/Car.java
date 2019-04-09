package space;

import java.awt.Graphics;
import java.util.List;

import geometry.Vector;

public class Car
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

	public List<Vector> getPoints()
	{
		return null;
	}
	
	public void draw(Graphics g)
	{
		List<Vector> points = null;
	}
}
