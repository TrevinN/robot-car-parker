package simulator;

public class Action
{
	public double deltaTime;
	public double acceleration;
	public double theta;

	public Action(double t, double a, double theta)
	{
		this.deltaTime = t;
		this.acceleration = a;
		this.theta = theta;
	}
}
