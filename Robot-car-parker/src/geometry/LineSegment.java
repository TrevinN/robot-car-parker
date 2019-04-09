package geometry;

import java.awt.geom.Line2D;

public class LineSegment
{
	public final Vector v0;
	public final Vector v1;

	public LineSegment(Vector v0, Vector v1)
	{
		this.v0 = v0;
		this.v1 = v1;
	}

	public boolean intersects(LineSegment l)
	{
		return Line2D.linesIntersect(
				this.v0.x, this.v0.y, this.v1.x, this.v1.y, 
				l.v0.x, l.v0.y, l.v1.x, l.v1.y);
	}
}
