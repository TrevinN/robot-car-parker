package simulator;

public interface Simulator extends Runnable
{
	public ActionNode getHead();
	public ActionNode getCurrent();
	public boolean isFinished();
}
