import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test8Puzzle {

	final static Pattern line_pat = Pattern.compile("^\\d\\s\\d\\s\\d$");
	static Matcher line_mat;
	
	// if only one state is needed
	public static int[] getState(String message) {
		int[] state = new int[9];
		System.out.println(message);
		Scanner in = new Scanner(System.in);
		String line, err_mess = "States should be given in 3 lines with values separated by spaces and using 0 as blank";
		boolean valid = false;
		while (!valid) {
			for (int i = 0; i < 3; i++) {
				line = in.nextLine();	// state is given in 3 lines
				line_mat = line_pat.matcher(line);
				if (line == null || !line_mat.matches()) break;
				for (int j = 0; j < 3; j++) { 
					state[i * 3 + j] = Character.getNumericValue(line.charAt(j * 2));	// values are separated by spaces
				}
			}
			try {
				valid = Node8Puzzle.validState(state);
			} catch (Exception e) {
				System.out.println(err_mess);
			}
		}
		in.close();
		return state;
	}

	// for multiple states
	public static int[][] getStates(String[] messages) {
		int n = messages.length;
		int[][] states = new int[n][9];
		Scanner in = new Scanner(System.in);
		int i = 0;
		while (i < n) {
			System.out.println(messages[i]);
			states[i] = getState(in);
			i++;
		}
		in.close();
		return states;
	}

	public static int[] getState(Scanner in) {
		int[] state = new int[9];
		String line;
		boolean valid = false;
		while (!valid) {
			for (int i = 0; i < 3; i++) {
				line = in.nextLine();	// state is given in 3 lines
				line_mat = line_pat.matcher(line);
				if (line == null || !line_mat.matches()) break;
				for (int j = 0; j < 3; j++) { 
					state[i * 3 + j] = Character.getNumericValue(line.charAt(j * 2));	// values are separated by spaces
				}
			}
			try {
				valid = Node8Puzzle.validState(state);
			} catch (Exception e) {
				System.out.println("States should be given in 3 lines with values separated by spaces and using 0 as blank");
			}
		}
		return state;
	}

	public static void main(String[] args) {
		
		//	to get initial and target states
		//	int[][] states = getStates(new String[] {"Enter intial state:", "Enter target state:"});
		//	int[] initial_state = states[0], target_state = states[1];
		

		//	to get only initial state 
		//	int[] initial_state = getState("Enter intial state:"), target_state = {1,2,3,8,0,4,7,6,5};
		
		int[] initial_state = {3,4,2,5,1,7,6,0,8}, target_state = {1,2,3,8,0,4,7,6,5};

		Search8Puzzle search = new Search8Puzzle(initial_state, target_state);
		if (!search.solvable()) {
			System.out.println(search.search("DF"));
			return;
		}

		String[] types = {"DF", "BF", "IDF", "G", "A"},
		names = {"Depth-first", "Breadth-first", "Iterative depth-first", "Greedy", "A*"},
		heuristics = {"PO", "MD"},
		heur_names = {"Pieces out", "Manhattan distance"};

		long initial_time, final_time;
		double time, av_time;
		int n_heur, n_times = 10;
		String sol = "", found, tab = "";
		boolean guided;

		for (int i = 0; i < 5; i++) {
			guided = "GA".contains(types[i]);
			System.out.println(names[i] + " search:");
			if (guided) {
				System.out.println();
				tab = "\t";
				n_heur = heuristics.length;
			} else {
				tab = "";
				n_heur = 1;
			}
			while (n_heur > 0) {
				n_heur -= 1;
				if (guided) {
					search.setHeuristic(heuristics[n_heur]);
					System.out.println(tab + heur_names[n_heur] + ":");
				}
				av_time = 0;
				for (int j = 0; j < n_times; j++) {
					initial_time = System.nanoTime();
					sol = search.search(types[i]);
					final_time = System.nanoTime();
					time = (final_time - initial_time) / 1000000000.0;
					av_time += time / n_times;
				}
				found = sol.equals("Not found") ? "No" : "Yes";
				System.out.println(tab + "Solution found? " + found);
				System.out.println(tab + "Time: " + av_time);
				System.out.println(tab + "Depth: " + sol.length());
				System.out.println(tab + "Total number of nodes: " + search.getTotalNodes());
				System.out.println(tab + "Max number of nodes: " + search.getMaxNodes());
				System.out.println();
			}
		}
	}
}
