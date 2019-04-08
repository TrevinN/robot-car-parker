package cspace;

import java.util.ArrayList;

public class CSpace {
	
	private final int _xSize;
	private final int _ySize;
	private final int _thetaSize;
	private final CPoint _start;
	private final CPoint _goal;
	private ArrayList<CPoint> _knownPoints;

	public CSpace(int xSize, int ySize, int thetaSize, CPoint start, CPoint goal) {
		_xSize = xSize;
		_ySize = ySize;
		_thetaSize = thetaSize;
		_start = start;
		_goal = goal;
		_knownPoints = new ArrayList<CPoint>();
		_knownPoints.add(goal);
		_knownPoints.add(start);
	}
	
	public int getXSize() {
		return _xSize;
	}
	
	public int getYSize() {
		return _ySize;
	}
	
	public int getThetaSize() {
		return _thetaSize;
	}
	
	public CPoint getGoal() {
		return _goal;
	}
	
	public CPoint getStart() {
		return _start;
	}
	
	public void AddKnownPoint(CPoint newPoint) {
		if (!_knownPoints.stream().anyMatch((p) -> p.equals(newPoint))) {
			_knownPoints.add(newPoint);
		}
	}
	
	public CPoint[] getKnownPoints() {
		CPoint[] toReturn = new CPoint[_knownPoints.size()];
		_knownPoints.toArray(toReturn);
		return toReturn;
	}

	public void changeKnownType(CPoint toChange) {
		_knownPoints.stream().map(cp -> cp.Equals(toChange) ? toChange : cp);
	}
	
	public boolean isColision(int x, int y, int theta) {
		// TODO
		return false;
	}
}
