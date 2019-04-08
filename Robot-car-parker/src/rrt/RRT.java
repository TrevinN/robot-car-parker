package rrt;

import java.util.ArrayList;
import java.util.PriorityQueue;

import javax.sound.sampled.LineEvent.Type;

import cspace.*;
import shared.*;

public class RRT {

	private CSpace _cspace;
	private CPoint _goal;
	private CPoint _start;
	private Vertex _root;
	
	public RRT(CSpace cspace) {
		_cspace = cspace;
		_goal = cspace.getGoal();
		_start = cspace.getStart();
		_root = new Vertex(_start);
		createTree();
	}
	
	private void createTree() {
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
			}
		}
	}
	
	private Vertex nextAlpha(Vertex curr) {
		// TODO
		return new Vertex(new CPoint(0, 0, 0, CType.FREE));
	}
	
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
