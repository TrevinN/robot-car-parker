package rrt;

import java.util.ArrayList;
import java.util.PriorityQueue;
import cspace.*;
import shared.*;

public class RRT {

	private final CSpace _cspace;
	private CPoint _goal;
	private CPoint _start;
	private Node _root;
	private Node _goalVertex;
	private boolean _pathUpToDate;
	private CPoint[] _path;
	
	public RRT(CSpace cspace) {
		_cspace = cspace;
		_goal = _cspace.getGoal();
		_start = _cspace.getStart();
		_root = new Node(_start);
		createTree();
	}
	
	public CPoint[] getPath() {
		if (!_pathUpToDate) {
			_path = generatePath();
			_pathUpToDate = true;
		}
		return _path;
	}
	
	private CPoint[] generatePath() {
		Node curr = _goalVertex;
		ArrayList<CPoint> path = new ArrayList<>();
		while (!curr.equals(_root)) {
			path.add(curr.getPoint());
			curr = curr.getParent();
		}
		path.add(_start);
		CPoint[] toReturn = new CPoint[path.size()];
		return path.toArray(toReturn);
	}
	
	private void createTree() {
		_pathUpToDate = false;
		boolean hasFinished = false;
		PriorityQueue<Node> pq = new PriorityQueue<>();
		pq.add(new Node(_start));
		int verticesVisited = 1;
		while (!hasFinished && !pq.isEmpty()) {
			Node curr = pq.poll();
			Node alpha;
			if (verticesVisited % 50 == 0) {
				alpha = new Node(_goal);
			}
			else {
				alpha = nextAlpha(curr);
			}
			verticesVisited++;
			if (true) {
				curr.addChild(alpha);
				alpha.setParent(curr);
				pq.add(alpha);
				if (alpha.getPoint().Equals(_goal)) {
					_goalVertex = alpha;
					hasFinished = true;
				}
			}
		}
	}
	
	private Node nextAlpha(Node curr) {
		// TODO
		return new Node(new CPoint(0, 0, 0, CType.FREE));
	}
	
	private class Node implements Comparable {
		
		private Node _parent;
		private ArrayList<Node> _children;
		private CPoint _point;
		private double _mag;
		private double _costTo;
		
		public Node(CPoint point) {
			_point = point;
			_mag = Math.sqrt(Math.pow(point.getX() - _goal.getX(), 2) + Math.pow(point.getY() - _goal.getY(), 2) + Math.pow(point.getTheta() - _goal.getTheta(), 2));
		}
		
		public void setParent(Node parent) {
			_parent = parent;
			CPoint point = _parent.getPoint();
			_costTo = _parent.costToPoint() + Math.sqrt(Math.pow(_point.getX() - point.getX(), 2) + Math.pow(_point.getY() - point.getY(), 2) + Math.pow(_point.getTheta() - point.getTheta(), 2));
		}
		
		public Node getParent() {
			return _parent;
		}
		
		public void addChild(Node child) {
			_children.add(child);
		}
		
		public CPoint getPoint() {
			return _point;
		}
		
		private double getMag() {
			return _mag;
		}
		
		// need to change to include how many steps taken
		@Override
		public int compareTo(Object arg0) {
			Node point = (Node) arg0;
			double pmag = point.getMag();
			if (_mag + _costTo > pmag + point.costToPoint()) return 1;
			if (_mag + _costTo < pmag + point.costToPoint()) return -1;
			return 0;
		}
		
		public double costToPoint() {
			return _costTo;
		}
	}
	
}
