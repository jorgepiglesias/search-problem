import java.util.LinkedList;

public abstract class Node<T> {
	
	public T state;
	public int depth = 0, cost = 0;
	public String move = "", hash_key;
	public Node<T> father = null;
	
	Node(T state){
		this.state = state;
		hash_key = genHashKey();
	}

	// return String hashkey which must be the same for equal states
	public abstract String genHashKey(); 

	// returns true if node's state is equal to state
	public abstract boolean equalState(T state);

	// returns descendants of a node for unguided searches (cost = depth)
	public abstract LinkedList<Node<T>> makeDescendants();

	// returns descendants of a node for guided searches (cost = some heuristic)
	public abstract LinkedList<Node<T>> makeDescendants(String type, String heur, T target_state);

	// methods that calculate heuristic values
	// returns sum of each tile's manhattan distance relative to their position in state
	public abstract int manhattanDistance(T state);
	// returns number of tiles out of place relative to state
	public abstract int piecesOut(T state);

	public String getHashKey() {
		return this.hash_key;
	}
	
	public T getState() {
		return this.state;
	}
	
	public void setFather(Node<T> father) {
		this.father = father;
	}

	public Node<T> getFather() {
		return this.father;
	}
	
	public void setCost(int cost) {
		this.cost = cost;
	}
	
	public int getCost() {
		return this.cost;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	public String getMove() {
		return this.move;
	}
	
	public void setDepth(int depth) {
		this.depth = depth;
	}
	
	public int getDepth() {
		return this.depth;
	}

	public String getMoves() {
		String moves = "";
		Node<T> node = this;
		while (node.getFather() != null) {
			moves = node.getMove() + moves;
			node = node.getFather();
		}
		return moves;
	}

}
