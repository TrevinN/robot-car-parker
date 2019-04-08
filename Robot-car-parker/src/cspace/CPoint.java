package cspace;

import shared.CType;;

public class CPoint {

	private final int _x;
	private final int _y;
	private final int _theta;
	private CType _type;
	
	public CPoint(int x, int y, int theta, CType type) {
		_x = x;
		_y = y;
		_theta = theta;
		_type = type;
	}
	
	public int getX() {
		return _x;
	}
	
	public int getY() {
		return _y;
	}
	
	public int getTheta() {
		return _theta;
	}
	
	public CType getType() {
		return _type;
	}
	
	public void setType(CType type) {
		_type = type;
	}
	
	public boolean Equals(CPoint toCompare) {
		return _x == toCompare.getX() && _y == toCompare.getY() && _theta == toCompare.getY();
	}
}
