public class Node implements Comparable<Node>{
	public int board;
	public Node parent;
	public int depth;
	public int cost;
	public int heuristic;

	public Node(int board, Node parent, int depth, int cost, int heuristic){
		this.board = board;
		this.parent = parent;
		this.depth = depth;
		this.cost = cost;
		this.heuristic = heuristic;
	}
	
	@Override
	public int compareTo(Node anotherNode){
		double F_thisNode = this.cost + this.heuristic;
		double F_anotherNode = anotherNode.cost + anotherNode.heuristic;
		if (F_thisNode < F_anotherNode)
			return -1;
		else if (F_thisNode > F_anotherNode)
			return 1;
		return 0;
	}
}