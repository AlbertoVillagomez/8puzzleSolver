import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class EightPuzzle extends JPanel{
	public JLabel[][] labels = new JLabel[3][3];
	private Timer timer;
	private int cont = 0;
	public JLabel solutionState = new JLabel("");
	public int velocity = 200;
	private static final int[] depthsForIterativeSearch = new int[]{1, 4, 16, 64, 256, 1024, 4096, 16384, 65536, 264144, 1056576};
	private static final int tenPowers[] = new int[]{1, 10, 100, 1000, 10000, 100000, 1000000, 10000000, 100000000};
	Node current = new Node(randomBoard(), null, 0, 0, 0);
	
	public EightPuzzle(){
		super();
		this.setPreferredSize(new Dimension(600, 350));
		
		Font fuente = new Font("Serif", Font.PLAIN, 80);
		
		int board = this.current.board;
		for(int i=2; i>=0; i--){
			for(int j=2; j>=0; j--){
				if(board%10 == 9){
					this.labels[i][j] = new JLabel("");
				}
				else{
					this.labels[i][j] = new JLabel(board%10+"");
				}
				board/=10;
				
				this.labels[i][j].setFont(fuente);
				this.add(labels[i][j]);
				this.labels[i][j].setBounds(180+j*100, 50+i*100, 60, 60);
			}
		}
		
		this.add(this.solutionState);
		this.solutionState.setFont(new Font("Serif", Font.PLAIN, 12));
		this.solutionState.setBounds(5, 5, 150, 40);
		
		setLayout(null);
	}
	
	public void getInitialState(){
		this.current = new Node(randomBoard(), null, 0, 0, 0);
		int board = this.current.board;
		for(int i=2; i>=0; i--){
			for(int j=2; j>=0; j--){
				if(board%10 == 9){
					labels[i][j].setText("");
				}
				else{
					labels[i][j].setText(board%10+"");
				}
				board/=10;
			}
		}
	}
	
	public void breadthFirstSearch(){
		Queue<Node> abiertos=new LinkedList<Node>();
		HashSet<Integer> cerrados=new HashSet<Integer>();
		
		abiertos.add(current);
		
		while(!abiertos.isEmpty()){
			current = abiertos.poll();
			if(current.board == 912345678){
				this.solutionState.setText("Solution found at "+this.current.depth+" move(s)");
				constructSolution(current);
				return;
			}
			ArrayList<Node> list=getSuccessors(current);
			cerrados.add(current.board);
			for(Node neighbor: list){
				if(!cerrados.contains(neighbor.board)){
					abiertos.add(neighbor);
				}
			}
		}
		this.solutionState.setText("Solution not found!");
	}

	public void depthFirstSearch(){
		Stack<Node> abiertos=new Stack<Node>();
		HashSet<Integer> cerrados=new HashSet<Integer>();
		
		abiertos.push(current);
		
		while(!abiertos.isEmpty()){
			current = abiertos.pop();
			if(current.board == 912345678){
				this.solutionState.setText("Solution found at "+this.current.depth+" move(s)");
				constructSolution(current);
				return;
			}
			ArrayList<Node> list=getSuccessors(current);
			cerrados.add(current.board);
			for(Node neighbor: list){
				if(!cerrados.contains(neighbor.board)){
					abiertos.push(neighbor);
				}
			}
		}
		this.solutionState.setText("Solution not found!");
	}

	public void iterativeDeepeningDepthFirstSearch(){
		for(int i=0; i<depthsForIterativeSearch.length; i++){
			Stack<Node> abiertos=new Stack<Node>();
			HashSet<Integer> cerrados=new HashSet<Integer>();
			
			abiertos.push(current);
			
			while(!abiertos.isEmpty()){
				current = abiertos.pop();
				if(current.board == 912345678){
					this.solutionState.setText("Solution found at "+this.current.depth+" move(s)");
					constructSolution(current);
					return;
				}
				if(current.depth < depthsForIterativeSearch[i]){
					ArrayList<Node> list=getSuccessors(current);
					cerrados.add(current.board);
					for(Node neighbor: list){
						if(!cerrados.contains(neighbor.board)){
							abiertos.push(neighbor);
						}
					}
				}
			}
		}
		this.solutionState.setText("Solution not found!");
	}
	
	public void bestFirstSearch(){
		PriorityQueue<Node> abiertos=new PriorityQueue<Node>();
		HashSet<Integer> cerrados=new HashSet<Integer>();
		
		abiertos.add(current);
		
		while(!abiertos.isEmpty()){
			current = abiertos.poll();
			if(current.board == 912345678){
				this.solutionState.setText("Solution found at "+current.depth+" move(s)");
				constructSolution(current);
				return;
			}
			ArrayList<Node> list=getSuccessors(current);
			cerrados.add(current.board);
			for(Node neighbor: list){
				if(!cerrados.contains(neighbor.board)){
					neighbor.cost = 0;
					neighbor.heuristic = calculateHeuristic(neighbor);
					abiertos.add(neighbor);
				}
			}
		}
		this.solutionState.setText("Solution not found!");
	}
	
	public void aStarSearch(){
		PriorityQueue<Node> abiertos=new PriorityQueue<Node>();
		HashSet<Integer> cerrados=new HashSet<Integer>();
		
		abiertos.add(current);
		
		while(!abiertos.isEmpty()){
			current = abiertos.poll();
			if(current.board == 912345678){
				this.solutionState.setText("Solution found at "+current.depth+" move(s)");
				constructSolution(current);
				return;
			}
			ArrayList<Node> list=getSuccessors(current);
			cerrados.add(current.board);
			for(Node neighbor: list){
				if(!cerrados.contains(neighbor.board)){
					neighbor.heuristic = calculateHeuristic(neighbor);
					abiertos.add(neighbor);
				}
			}
		}
		this.solutionState.setText("Solution not found!");
	}
	
	public ArrayList<Node> getSuccessors(Node temp){
		ArrayList<Node> listOfSuccesors = new ArrayList<Node>();
		int num = temp.board;
		int num2 = num;

		int pos = 0;
		while(num2%10 != 9){
			pos++;
			num2/=10;
		}
		
		//Left
		if(pos != 2 && pos != 5 && pos != 8){
			listOfSuccesors.add(new Node(swapNumbers(num, pos, 1), temp, temp.depth+1, temp.depth+1, 0));
		}
		//Up
		if(pos < 6){
			listOfSuccesors.add(new Node(swapNumbers(num, pos, 3), temp, temp.depth+1, temp.depth+1, 0));
		}
		//Right
		if(pos != 0 && pos != 3 && pos != 6){
			listOfSuccesors.add(new Node(swapNumbers(num, pos, -1), temp, temp.depth+1, temp.depth+1, 0));
		}
		//Down
		if(pos > 2){
			listOfSuccesors.add(new Node(swapNumbers(num, pos, -3), temp, temp.depth+1, temp.depth+1, 0));
		}
		return listOfSuccesors;
	}

	public static int swapNumbers(int numCopy, int pos, int factor){
		int swap = (numCopy/tenPowers[pos+factor])%10;
		numCopy += swap*(-tenPowers[pos+factor] + tenPowers[pos]) + 9*(tenPowers[pos+factor] - tenPowers[pos]);
		return numCopy;
	}
	
	public void constructSolution(Node temp){
		ArrayList<Node> list=new ArrayList<Node>();	
		while(temp.parent != null){
			list.add(temp);
			temp = temp.parent;
		}
		list.add(temp);
		Collections.reverse(list);
			
		ActionListener solutionAnimation=new ActionListener(){
			public void actionPerformed(ActionEvent ae){
				int board = list.get(cont).board;
				for(int j=2; j>=0; j--){
					for(int k=2; k>=0; k--){
						if(board%10 == 9){
							labels[j][k].setText("");
						}
						else{
							labels[j][k].setText(board%10+"");
						}
						board/=10;
					}
				}

				cont++;
				if(cont >= list.size()){
					solutionState.setText("");
					cont = 0;
					timer.stop();
				}
			}
		};

		timer=new Timer(velocity, solutionAnimation);
		timer.start();
		
		this.current = new Node(912345678, null, 0, 0, 0);
	}
	
	public int randomBoard(){
		ArrayList<Integer> arrList = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
		Collections.shuffle(arrList);
		
		int[][] temp = new int[3][3];
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				temp[i][j] = arrList.get(3*i+j);
			}
		}
		int board = 0;
		for(int i=0; i<arrList.size(); i++){
			board = board*10 + arrList.get(i);
		}
		return board;
	}
	
	public int calculateHeuristic(Node temp){
		int board = temp.board;
		int i = 8;
		int cont = 0;
		while(board>9){
			if(board%10 != i){
				cont++;
			}
			board/=10;
			i--;
		}
		return cont;
	}
	
}