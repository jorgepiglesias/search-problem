import java.util.List;

public abstract class QueueingFunction<T> {

    public List<Node<T>> list;
    public int total_nodes = 0, current_nodes = 0, max_nodes = 0;

    public abstract void insert(Node<T> node);
		 
	public Node<T> removeFrontNode() {
        current_nodes -= 1;
        return list.remove(0);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public int getTotalNodes() {
        return total_nodes;
    }

    public int getMaxNodes() {
        return max_nodes;
    }

}
