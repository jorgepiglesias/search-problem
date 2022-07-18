import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;

public class Node8Puzzle extends Node<int[]> {
    
	public int blank;
	public LinkedList<Character> poss_moves;

	Node8Puzzle(int[] state) {
        super(state);
        setBlank();
        poss_moves = new LinkedList<Character>();
        if (blank > 2) {
            poss_moves.add('U');
        }
        if (blank < 6) {
            poss_moves.add('D');
        }
        if ((blank + 1) % 3 != 0) {
            poss_moves.add('R');
        }
        if (blank % 3 != 0) {
            poss_moves.add('L');
        }
    }

	public static boolean validState(int[] state) {
		boolean valid = true;
		if (state.length == 9) {
			for (int i = 0; i < 9; i++) {
				final int j = i;
				if (!IntStream.of(state).anyMatch(x -> x == j)) {
					valid = false;
					break;
				}
			}
		} else valid = false;
		if (!valid) throw new IllegalArgumentException("State must be int array with one of each number in [0,8]");
		return valid;
	}

	
	private void setBlank(){
        for (int i = 0; i < 9; i++) {
            if (state[i] == 0) {
                blank = i;
                break;
            }
        }
    }

	public String toString() {
		String str = "";
		for (int n: state) {
			str = str + n;
		}
		return str;
	} 

    public Node8Puzzle makeMove(String move) {
        int new_blank;
        if (move.equals("U")) {
            new_blank = blank - 3;
        } else if (move.equals("D")) {
            new_blank = blank + 3;
        } else if (move.equals("R")) {
            new_blank = blank + 1;
        } else {
            new_blank = blank - 1;
        } 
        int[] new_state = state.clone();
        new_state[blank] = state[new_blank];
        new_state[new_blank] = 0;
        Node8Puzzle node = new Node8Puzzle(new_state);
        node.setFather(this);
        node.setDepth(depth + 1);
        node.setMove(move);
        node.setCost(depth + 1);
        return node;
    }

	public static int[] getRandomState() {
		int[] state = {1, 2, 3, 4, 5, 6, 7, 8, 0};
		Random rand = new Random();
		int temp, rand_i;
		for (int i = 0; i < 9; i++) {
			rand_i = rand.nextInt(9);
			temp = state[i];
			state[i] = state[rand_i];
			state[rand_i] = temp;
		}
		return state;
	}
	
	@Override
	public String genHashKey() {
		return toString();
	} 
	
	@Override
	public boolean equalState(int[] state) {
		for (int i = 0; i < 9; i++) {
			if (this.state[i] != state[i]) {
				return false;
			}
		}
		return true;
	}

	@Override
    public LinkedList<Node<int[]>> makeDescendants() {
    	LinkedList<Node<int[]>> descendants = new LinkedList<Node<int[]>>();
    	if (poss_moves.contains('U') && !move.equals("D")) {
    		descendants.add(makeMove("U"));
    	}
    	if (poss_moves.contains('D') && !move.equals("U")) {
    		descendants.add(makeMove("D"));
    	}
    	if (poss_moves.contains('R') && !move.equals("L")) {
    		descendants.add(makeMove("R"));
    	}
    	if (poss_moves.contains('L') && !move.equals("R")) {
    		descendants.add(makeMove("L"));
    	}
    	return descendants;
    }
    
	@Override
    public LinkedList<Node<int[]>> makeDescendants(String type, String heur, int[] final_state) {
    	LinkedList<Node<int[]>> descendants = makeDescendants();
    	int cost;
		for (Node<int[]> node: descendants) {
			cost = (heur.equals("MD")) ? node.manhattanDistance(final_state) : node.piecesOut(final_state);
        	if (type.equals("G")) node.setCost(cost); else node.setCost(cost + this.cost);
		}
    	return descendants;
    }

	@Override
    public int manhattanDistance(int[] final_state) {
    	int sum = 0, final_column, final_line;
    	for (int i = 0; i < 9; i++) {
            if (final_state[i] == 0) continue;
    		final_column = i % 3;
    		final_line = i / 3;
    		for (int j = 0; j < 9; j++) {
    			if (state[j] == final_state[i]) {
   					int column = j % 3, line = j / 3;
   					sum = sum + Math.abs(column - final_column) + Math.abs(line - final_line);
                    break;
   				}
   			}
   		}
    	return sum;
    }
    
	@Override
    public int piecesOut(int[] final_state) {
    	int sum = 0;
    	for (int i = 1; i < 9; i++) {
            if (final_state[i] == 0) continue;
    		if (state[i] != final_state[i]) {
    			sum += 1;
    		}
    	}
    	return sum;
    }

}
