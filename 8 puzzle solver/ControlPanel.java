import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class ControlPanel extends JPanel implements ActionListener, ChangeListener{
	private EightPuzzle puzzle;
	public JButton random, bfs, dfs, ldfs, bestfs, astar;
	private JSlider velocityAnimation;
	
	public ControlPanel(EightPuzzle puzzle){
		super();
		this.setPreferredSize(new Dimension(600, 150));
		this.puzzle = puzzle;
		
		this.random = new JButton("Random");
		this.bfs = new JButton("Breadth FS");
		this.dfs = new JButton("Depth FS");
		this.ldfs = new JButton("Iterative DFS");
		this.bestfs = new JButton("Best FS");
		this.astar = new JButton("A Star");
		this.velocityAnimation = new JSlider(JSlider.HORIZONTAL, 0, 1000, 200);
		
		this.add(this.random);
		this.add(this.bfs);
		this.add(this.dfs);
		this.add(this.ldfs);
		this.add(this.bestfs);
		this.add(this.astar);
		this.add(this.velocityAnimation);
		
		this.random.setBounds(40, 40, 110, 30);
		this.bfs.setBounds(220, 40, 110, 30);
		this.dfs.setBounds(340, 40, 110, 30);
		this.ldfs.setBounds(460, 40, 110, 30);
		this.bestfs.setBounds(220, 90, 110, 30);
		this.astar.setBounds(340, 90, 110, 30);
		this.velocityAnimation.setBounds(0, 70, 200, 80);
		
		setLayout(null);
		
		this.random.addActionListener(this);
		this.bfs.addActionListener(this);
		this.dfs.addActionListener(this);
		this.ldfs.addActionListener(this);
		this.bestfs.addActionListener(this);
		this.astar.addActionListener(this);
		this.velocityAnimation.addChangeListener(this);
		
		this.velocityAnimation.setMajorTickSpacing(200);
		this.velocityAnimation.setMinorTickSpacing(200);
		this.velocityAnimation.setPaintTicks(true);
		this.velocityAnimation.setPaintLabels(true);
	}

	@Override
	public void stateChanged(ChangeEvent e) {
		this.puzzle.velocity = velocityAnimation.getValue();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == this.random){
			this.puzzle.getInitialState();
			this.puzzle.solutionState.setText("");
		}
		else if(e.getSource() == this.bfs){
			this.puzzle.breadthFirstSearch();
		}
		else if(e.getSource() == this.dfs){
			this.puzzle.depthFirstSearch();
		}
		else if(e.getSource() == this.ldfs){
			this.puzzle.iterativeDeepeningDepthFirstSearch();
		}
		else if(e.getSource() == this.bestfs){
			this.puzzle.bestFirstSearch();
		}
		else{
			this.puzzle.aStarSearch();
		}
	}

}