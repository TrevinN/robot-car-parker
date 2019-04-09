package geometry;

public class Arc extends Collidable
{
	public final Vector start;
	public final double startAngle;
	public final Vector end;
	public final double endAngle;
	public final double rho;
	public final double length;
	
	/**
	 * Constructs a new arc
	 * @param start			The starting point of the arc
	 * @param startAngle	The angle the tangent of the arc at the start has to the world
	 * @param rho			The radius of the circle the arc lies on. If rho is negative,
	 * 						the arc turns to the left, if positive, the arc turns to the
	 * 						right. If rho is 0 or infinity, the arc does not turn, making
	 * 						it a line
	 * @param arcLength		The length of the arc
	 */
	public Arc(Vector start, double startAngle, double rho, double arcLength)
	{
		this.rho = rho;
		this.start = start;
		this.length = arcLength;
		this.startAngle = startAngle;
		if(isSegment())
		{
			this.end = 
		}
	}
	
	public boolean isSegment()
	{
		return rho == 0.0 || rho == Double.NEGATIVE_INFINITY || rho == Double.POSITIVE_INFINITY;
	}
	
}
