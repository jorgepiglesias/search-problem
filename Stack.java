import java.util.LinkedList;

public class Stack<T> extends QueueingFunction<T> {
	
	Stack() {
		list = new LinkedList<Node<T>>();
	}

	public void insert(Node<T> node) {
		total_nodes += 1;
		current_nodes += 1;
		if (current_nodes > max_nodes) max_nodes = current_nodes;
		list.add(0, node);
	}


}