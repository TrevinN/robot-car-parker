package simulator;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import geometry.LineSegment;
import geometry.Vector;
import space.*;
import ui.Workspace;

public class Simulator2D implements Runnable, Simulator
{
	private static final double DELTA_TIME = 1;
	private static final double MAX_VELOCITY = 10;
	private static final double MAX_ACCELERATION = 2;
	private static final double MAX_ANGLE = 30;
	
	public ActionNode head;
	public ActionNode currentNode;
	public boolean finished = false;
	
	private Workspace _wspace;

	public Simulator2D(Workspace wspace)
	{
		_wspace = wspace;
	}

	public ActionNode simulate()
	{
		ActionNode first = new ActionNode();
		first.position = _wspace.car.position;
		first.theta = _wspace.car.getAngle();
		head = first;
		int i = 0;
		
		Queue<ActionNode> nodeQueue = new PriorityQueue<>(new Comparator<ActionNode>() 
		{
			@Override
			public int compare(ActionNode o1, ActionNode o2)
			{
				double distFromGoal1 = o1.position.subtract(_wspace.goal.center()).magnitude();
				double distFromGoal2 = o2.position.subtract(_wspace.goal.center()).magnitude();
				double total1 = distFromGoal1 + o1.elapsedDistance;
				double total2 = distFromGoal2 + o2.elapsedDistance;
				if(total1 - total2 < 0)
				{
					return -1;
				}
				else if (total1 - total2 == 0)
				{
					return 0;
				}
				else
				{
					return 1;
				}
			}
		});
		nodeQueue.add(first);
		
		while (!nodeQueue.isEmpty())
		{
			if(++i % 1000 == 0)
			{
				System.out.println("Loop count " + i);
				System.out.println("Queue elements: " + nodeQueue.size());
			}
			
			ActionNode n = nodeQueue.poll();
			currentNode = n;
			
			for (double turn = -30; turn <= 30; turn += 20)
			{
				double angle = turn * Math.PI / 180;
				
				for (double accel = -2; accel <= 2; accel += 1)
				{
					//If we're going as fast as we can, no reason to try to go faster
					if(n.velocity >= MAX_VELOCITY && accel > 0)
						continue;
					if(n.velocity <= -MAX_VELOCITY && accel < 0)
						continue;
					
					// Calculate velocity in the future
					double vPrime = n.velocity + accel * DELTA_TIME;
					
					// If the car is moving too fast, then clamp the values
					vPrime = Math.max(Math.min(vPrime, MAX_VELOCITY), -MAX_VELOCITY);
					
					// More calculations to find next node state
					double vAverage = (n.velocity + vPrime) / 2;
					double distance = vAverage * DELTA_TIME;
					double x = n.position.x + distance * Math.cos(n.theta);
					double y = n.position.y + distance * Math.sin(n.theta);
					double roh = _wspace.car.L / Math.tan(angle);
					double theta = n.theta + distance / roh;
					Vector position = new Vector(x, y);
					
					// Set the car's state to the new state and test it
					_wspace.car.setPosition(position);
					_wspace.car.setAngle(theta);
					if (!_wspace.hasCollision())
					{
						ActionNode nextNode = new ActionNode();
						nextNode.parent = n;
						nextNode.action = new Action(DELTA_TIME, accel, angle);
						nextNode.position = position;
						nextNode.theta = theta;
						nextNode.elapsedTime = n.elapsedTime + DELTA_TIME;
						nextNode.elapsedDistance = n.elapsedDistance + Math.abs(distance);
						nextNode.velocity = vPrime;
						nextNode.line = new LineSegment(n.position, position);
						
						currentNode = nextNode;
						n.addChild(nextNode);
						nodeQueue.add(nextNode);
						
						if (_wspace.goal.collides(_wspace.car.position))
						{
							finished = true;
							return nextNode;
						}
					}
				}
			}
		}
		return null;
	}

	@Override
	public void run()
	{
		simulate();
	}

	@Override
	public ActionNode getHead()
	{
		return head;
	}

	@Override
	public ActionNode getCurrent()
	{
		return currentNode;
	}

	@Override
	public boolean isFinished()
	{
		return finished;
	}
}