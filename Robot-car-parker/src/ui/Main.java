package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;

public class Main extends JFrame
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Main()
	{
		add(new Workspace());
		setResizable(false);
		pack();
		setTitle("Car Planning Simulation");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args)
	{
		EventQueue.invokeLater(() -> {
			JFrame ex = new Main();
			ex.setVisible(true);
		});
	}
}
