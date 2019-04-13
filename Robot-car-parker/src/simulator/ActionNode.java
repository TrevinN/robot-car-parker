package simulator;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import geometry.Arc;
import geometry.LineSegment;
import geometry.Vector;

public class ActionNode
{
	public ActionNode parent;
	public List<ActionNode> children;
	
	public Action action;
	public Vector position;
	public double theta;
	public double elapsedTime;
	public double elapsedDistance;
	public double velocity;
	
	public LineSegment line;
	public Arc arc;
	
	public ActionNode()
	{
		children = new ArrayList<>();
	}
	
	public synchronized void addChild(ActionNode n)
	{
		children.add(n);
	}
	
	public void draw(Graphics g)
	{
		if(line != null)
		{
			line.draw(g);
		}
		else if(arc != null)
		{
			arc.draw(g);
		}
	}
	
	public void drawParent(Graphics g)
	{
		draw(g);
		if(parent != null)
		{
			parent.drawParent(g);
		}
	}
	
	public synchronized void drawChildren(Graphics g)
	{
		draw(g);
		for(ActionNode child : children)
		{
			child.drawChildren(g);
		}
	}
	
	public boolean equals(Object o)
	{
		if(o == null || o.getClass() != this.getClass())
		{
			return false;
		}
		ActionNode an = (ActionNode) o;
		if((int)(an.position.x * 10) != (int)(position.x * 10))
		{
			return false;
		}
		if((int)(an.position.y * 10) != (int)(position.y * 10))
		{
			return false;
		}
		if((int)(an.theta * 100) != (int)(theta * 100))
		{
			return false;
		}
		return true;
	}
	
	public int hashCode()
	{
		int x = (int)(position.x * 10);
		int y = (int)(position.y * 10);
		int angle = (int)(theta * 100);
		
		return (((((x * 31) + y) * 31) + angle) * 31);
	}
}