package simulator;

import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import geometry.LineSegment;
import geometry.Vector;
import space.*;
import ui.Workspace;

public class Simulator1D implements Runnable, Simulator
{
	private static final double DELTA_DISTANCE = 0.5;
	
	public ActionNode head;
	public ActionNode currentNode;
	public boolean finished = false;
	
	private Workspace _wspace;

	public Simulator1D(Workspace wspace)
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
		
		HashSet<ActionNode> set = new HashSet<>();
		set.add(first);
		
		while (!nodeQueue.isEmpty())
		{
			if(i++ % 10000 == 0)
			{
				System.out.println("Loop count " + i);
				System.out.println("Queue elements: " + nodeQueue.size());
				System.out.println("Hashset elements: " + set.size());
			}
			
			ActionNode n = nodeQueue.poll();
			currentNode = n;
			
			for (double turn = -30; turn <= 30; turn += 15)
			{
				for (int direction = -1; direction <= 1; direction += 2)
				{
					double phi = turn * Math.PI / 180;
					double theta = phi + n.theta;
					double distance = DELTA_DISTANCE * direction;
					Vector position = new Vector(distance, 0).rotate(theta).add(n.position);
					
					// Set the car's state to the new state and test it
					_wspace.car.setPosition(position);
					_wspace.car.setAngle(theta);
					if (!_wspace.hasCollision())
					{
						ActionNode nextNode = new ActionNode();
						nextNode.parent = n;
						nextNode.position = position;
						nextNode.theta = theta;
						nextNode.elapsedDistance = n.elapsedDistance + Math.abs(distance);
						nextNode.line = new LineSegment(n.position, position);
						
						if(!set.contains(nextNode))
						{
							currentNode = nextNode;
							n.addChild(nextNode);
							nodeQueue.add(nextNode);
							set.add(nextNode);
							
							if (_wspace.goal.collides(_wspace.car.position))
							{
								finished = true;
								return nextNode;
							}
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