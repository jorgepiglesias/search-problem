import java.util.LinkedList;

public class Queue<T> extends QueueingFunction<T> {

	Queue() {
		list = new LinkedList<Node<T>>();
	}
	
	@Override
	public void insert(Node<T> node) {
		current_nodes += 1;
		if (current_nodes > max_nodes) max_nodes = current_nodes;
		total_nodes += 1;
		list.add(node);
	}

}
