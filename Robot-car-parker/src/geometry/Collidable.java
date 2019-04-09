package geometry;

public abstract class Collidable
{
	public boolean collides(Collidable c)
	{
		return false;
	}
	
	public static boolean collidesLineOnArc(LineSegment l, Arc a)
	{
		return false;
	}
}
