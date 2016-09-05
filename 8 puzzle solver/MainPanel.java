import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;

public class MainPanel extends JFrame{
	private ControlPanel controles;
	private EightPuzzle puzzle;
	
	public MainPanel(){
		super("8 puzzle!");
		this.setPreferredSize(new Dimension(600, 500));
		this.setResizable(false);

		this.puzzle=new EightPuzzle();
		this.controles=new ControlPanel(puzzle);

		this.add(this.puzzle,BorderLayout.CENTER);
		this.add(controles, BorderLayout.SOUTH);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}
	
	public static void main(String[] args) {
		new MainPanel();
	}
	
}