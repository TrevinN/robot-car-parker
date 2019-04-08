package rrt;

import java.util.ArrayList;
import java.util.PriorityQueue;
import cspace.*;
import shared.*;

public class RRT {

	private final CSpace _cspace;
	private CPoint _goal;
	private CPoint _start;
	private Vertex _root;
	private Vertex _goalVertex;
	private boolean _pathUpToDate;
	private CPoint[] _path;
	
	public RRT(CSpace cspace) {
		_cspace = cspace;
		_goal = _cspace.getGoal();
		_start = _cspace.getStart();
		_root = new Vertex(_start);
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
		Vertex curr = _goalVertex;
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
		PriorityQueue<Vertex> pq = new PriorityQueue<>();
		pq.add(new Vertex(_start));
		int verticesVisited = 1;
		while (!hasFinished && !pq.isEmpty()) {
			Vertex curr = pq.poll();
			Vertex alpha;
			if (verticesVisited % 50 == 0) {
				alpha = new Vertex(_goal);
			}
			else {
				alpha = nextAlpha(curr);
			}
			verticesVisited++;
			if (isLegalPoint(curr, alpha)) {
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
	
	// going to need to generate a random point using some sort of distribution which will favor more towards the 
	// point being closer to the given curr value, can also only generate valid points which could potentially be harder but idk
	// also may want to add a private boolean value taken in in the constructor which determines if we can go backwards
	private Vertex nextAlpha(Vertex curr) {
		// TODO
		return new Vertex(new CPoint(0, 0, 0, CType.FREE));
	}
	
	// we may not need this if we decide to go with the approach of only generating valid points
	private boolean isLegalPoint(Vertex curr, Vertex next) {
		// TODO
		return true;
	}
	
	private class Vertex implements Comparable {
		
		private Vertex _parent;
		private ArrayList<Vertex> _children;
		private CPoint _point;
		private double _mag;
		
		public Vertex(CPoint point) {
			_point = point;
			_mag = Math.sqrt(Math.pow(point.getX(), 2) + Math.pow(point.getY(), 2) + Math.pow(point.getTheta(), 2));
		}
		
		public void setParent(Vertex parent) {
			_parent = parent;
		}
		
		public Vertex getParent() {
			return _parent;
		}
		
		public void addChild(Vertex child) {
			_children.add(child);
		}
		
		public CPoint getPoint() {
			return _point;
		}
		
		private double getMag() {
			return _mag;
		}
		
		@Override
		public int compareTo(Object arg0) {
			Vertex point = (Vertex) arg0;
			double pmag = point.getMag();
			if (_mag > pmag) return 1;
			if (_mag < pmag) return -1;
			return 0;
		}
	}
	
}
