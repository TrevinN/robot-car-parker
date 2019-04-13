package geometry;

public abstract class Collidable
{	
	public boolean collides(Rectangle r)
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean collides(Arc a)
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean collides(LineSegment l)
	{
		throw new UnsupportedOperationException();
	}
	
	public boolean collides(Vector v)
	{
		throw new UnsupportedOperationException();
	}

}
