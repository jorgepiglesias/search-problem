public class Search8Puzzle extends SearchProblem<int[]> {

    Search8Puzzle(int[] initial_state, int[] target_state) {
		super(initial_state, target_state);
	}

    @Override
	public boolean solvable() {
		int sum = 0, k; 	// sum holds number of inversions
		for (int i = 0; i < 9; i++) {	// iterate thru all members of initial_state
			if (initial_state[i] == 0) continue;	// do not count inversions relative to blank
			for (int j = i + 1; j < 9; j++) {	// iterate thru members rigth of initial_state[i]
				if (initial_state[j] == 0) continue;	// do not count inversions of blank
				k = 0;
				while (target_state[k] != target_state[i]) {	// iterate thru members left of initial_state[i] in this.target_state
					if (target_state[k] == initial_state[j]) {
						sum += 1;	// when a tile is on the right of initial_state[i] in initial_state and left of initial_state[i] in final_state
						break;
					}
					k += 1;
				}	
			}	
		}
		if (sum % 2 == 0) {		// if number of inversions is even the puzzle is solvable
			return true;
		}
		return false;
	}

	@Override
	public Node8Puzzle instInitialNode() {
		return new Node8Puzzle(initial_state);
	}

	@Override
	public boolean validState(int[] state) {
		return Node8Puzzle.validState(state);
	}
    
}
