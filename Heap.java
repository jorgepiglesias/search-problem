public class Heap<T> extends QueueingFunction<T> {

	public BinaryTree<Node<T>> list;

	Heap() {
		list = new BinaryTree<Node<T>>();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void arrange(BTElem<Node<T>> root) {
		BTElem<Node<T>> left_child = root.getLeftChild(), right_child = root.getRightChild(), min = root;
		int min_cost = min.getContent().getCost();
		if (left_child != null) {
			int left_cost = left_child.getContent().getCost();
			if (left_cost < min_cost) {
				min = left_child;
				min_cost = left_cost;
			}
			if (right_child != null) {
				int right_cost = right_child.getContent().getCost();
				if (right_cost < min_cost) {
					min = right_child;
				}
			}
		}
		if (min != root) {
			Node<T> cont_min = min.getContent();
			min.setContent(root.getContent());
			root.setContent(cont_min);
			arrange(min);
		}
	}
	
	@Override
	public void insert(Node<T> node) {
		total_nodes += 1;
		current_nodes += 1;
		if (current_nodes > max_nodes) max_nodes = current_nodes;
		BTElem<Node<T>> elem = list.add(node), parent = elem.getParent();
		while (parent != null) {
			if (elem.getContent().getCost() < parent.getContent().getCost()) {
				elem.setContent(parent.getContent());
				parent.setContent(node);
				elem = elem.getParent();
				parent = elem.getParent();
			} else break;
		}
	}

	@Override
	public Node<T> removeFrontNode() {
		current_nodes -= 1;
		Node<T> front_node = list.removeRoot();
		if (list.size() > 1) {
			arrange(list.getRoot());
		}
		return front_node;
	}

}
