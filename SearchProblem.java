import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public abstract class SearchProblem<T> {
	
	public T initial_state, target_state;
	public int total_nodes, max_nodes, max_depth = Integer.MAX_VALUE;
	public String heur, type;
	public boolean guided;

	private static final String[] types = {"DF", "BF", "IDF", "G", "A"},
	names = {"Depth-first", "Breadth-first", "Iterative depth-first", "Greedy", "A*"},
	heuristics = {"PO", "MD"},
	heur_names = {"Pieces out", "Manhattan distance"};

	SearchProblem(T initial_state, T target_state) {
		if (validState(initial_state) && validState(target_state)) {
			this.initial_state = initial_state;
			this.target_state = target_state;
		}
	}

	// return true if state is a valid state
	public abstract boolean validState(T state);

	// return true if problem is solvable or if there is no way to determine solvability
	public boolean solvable() {return true;}

	// return instantiated node with initial_state
	public abstract Node<T> instInitialNode();

	public T getInitialState() {
		return initial_state;
	}

	public T getTargetState() {
		return target_state;
	}

	public int getTotalNodes() {
		return total_nodes;
	}

	public int getMaxNodes() {
		return max_nodes;
	}

	public void setHeuristic(String heur) {
		if (validHeur(heur)) this.heur = heur; else throw new IllegalArgumentException();
	}

	public final static boolean validType(String type) {
		boolean contains = Arrays.stream(types).anyMatch(type::equals);
		if (!contains) {
			System.out.println();
			System.out.println("Invalid search type, possible ones are:");
			for (int i = 0; i < types.length; i++) {
				System.out.println(types[i] + " - " + names[i] + " search");
			}
		}
		return contains;
	}

	public final static boolean validHeur(String heur) {
		boolean contains = (heur == null) ? false : Arrays.stream(heuristics).anyMatch(heur::equals);
		if (!contains) {
			System.out.println();
			System.out.println("Invalid heuristic, possible ones are:");
			for (int i = 0; i < heuristics.length; i++) {
				System.out.println(heuristics[i] + " - " + heur_names[i]);
			}
		}
		return contains;
	}

	public String GeneralSearchAlgorithm(QueueingFunction<T> queue) {
		if (!solvable()) return "Not solvable";
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		Node<T> initial = instInitialNode();
		map.put(initial.getHashKey(), initial.getCost());
		queue.insert(initial);
		while (!queue.isEmpty()) {
			Node<T> node = queue.removeFrontNode();
			if (node.equalState(target_state)) return node.getMoves();
			if (node.getDepth() < max_depth) {
				LinkedList<Node<T>> descendant_list = (guided) ? node.makeDescendants(type, heur, target_state) : node.makeDescendants();
				while (!descendant_list.isEmpty()) {
					Node<T> descendant = descendant_list.remove(0);
					if (!map.containsKey(descendant.getHashKey()) || (type.equals("IDF") && map.get(descendant.getHashKey()) > descendant.getCost())) {
						queue.insert(descendant);
						map.put(descendant.getHashKey(), descendant.getCost());
					}
				}
			}
		}
        return "Not found";
	}
	
	public String search(String type) {
		if (!validType(type)) throw new IllegalArgumentException();
		this.type = type;
		total_nodes = 0;
		max_nodes = 0;
		guided = "GA".contains(type);
		String sol = "Not found";
		QueueingFunction<T> queue = new Stack<T>(); // for DF and IDF
		if (guided) {
			if (!validHeur(heur)) throw new IllegalArgumentException("For guided searches a heuristic must be set using the setHeuristic method");
			queue = new Heap<T>(); // for G and A
		} else if (type.equals("BF")) {
			queue = new Queue<T>(); // for BF
		}
		if (type.equals("IDF")) {
			max_depth = 0;
			while (sol.equals("Not found")) {
				sol = GeneralSearchAlgorithm(queue);
				max_depth += 1;
			}
			max_depth = Integer.MAX_VALUE;
		} else sol = GeneralSearchAlgorithm(queue);
		total_nodes = queue.getTotalNodes();
		max_nodes = queue.getMaxNodes();
		return sol;
	}	

}
