package super_duper_uber_2048;

import java.util.Arrays;
import java.util.Scanner;

public class game {
	public static int matrix_zeilen = 4;
	public static int matrix_spalten = 4;
	
	public static int[][] main_matrix = new int[matrix_zeilen][matrix_spalten];
	
	public static void main(String[] args) {
		long timer_start = System.currentTimeMillis();
		
		// GAME ABLAUF
		int game_over = 0;
		while (game_over == 0) {
			if (add_random() == 1) {
				if (is_game_over() == 0) {
					System.out.println("Score: " + get_matrix_score() + "\n");
					print_matrix();
					System.out.println("-----------------\n");
					// read user input
					int open_scanner = 1;
					while (open_scanner == 1) {
						String input = "";
						System.out.print("Please enter your next move (w, a, s, d): ");
						Scanner in = new Scanner(System.in);
						input = in.nextLine();
						if (input.equals("w")) {
							if (is_move_possible("to_top") == 1) {
								master_matrix_shifter("to_top");
								open_scanner = 0;
							}
						} else if (input.equals("a")) {
							if (is_move_possible("to_left") == 1) {
								master_matrix_shifter("to_left");
								open_scanner = 0;
							}
						} else if (input.equals("s")) {
							if (is_move_possible("to_bottom") == 1) {
								master_matrix_shifter("to_bottom");
								open_scanner = 0;
							}
						} else if (input.equals("d")) {
							if (is_move_possible("to_right") == 1) {
								open_scanner = 0;
								master_matrix_shifter("to_right");
							}
						}
					}
				} else {
					game_over = 1;
				}
			} else {
				game_over = 1;
			}
		}
		// GAME OVER
		System.out.println("Score: " + get_matrix_score() + "\n");
		print_matrix();
		System.out.println("-----------------\n");
		System.out.println("\nGAME OVER");
		System.out.println("\n=================\nRUNTIME: " + ((System.currentTimeMillis() - timer_start) / 1000.0) + " s");
	}
	
	
	public static int add_random() {
		if (is_matrix_full() == 1) {
			// Wenn jeder Slot der Matrix voll -> Game Over
			return 0;
		} else {
			while (true) {
				int freie_breite = get_random(matrix_zeilen);
				int freie_spalte = get_random(matrix_spalten);
				
				if (main_matrix[freie_breite][freie_spalte] == 0) {
					// 2 oder 4 in leeres random feld schreiben? 3:1 wahrscheinlichkeit
					if (get_random(3) < 2) {
						main_matrix[freie_breite][freie_spalte] = 2;
					} else {
						main_matrix[freie_breite][freie_spalte] = 4;
					}
					// erfolgreich random zahl eingefügt
					return 1;
				}
			}
		}
	}
	
	
	public static void print_matrix() {
		// find largest number
		int max_length = 0;
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				if (max_length < Integer.toString(main_matrix[i][j]).length()) {
					max_length = Integer.toString(main_matrix[i][j]).length();
				}
			}
		}
		// pretty print
		for (int i=0; i<matrix_zeilen; i++) {
			System.out.print("|");
			for (int j=0; j<matrix_spalten; j++) {
				String current = Integer.toString(main_matrix[i][j]);
				// replace "0" with "." for more readability
				if (current.equals("0")) {
					current = ".";
				}
				// equallize length for largest appearing number
				while (current.length() < (max_length + 2)) {	// '+2' for spacer between columns
					current = " " + current;
				}
				// print in orderly fashion
				if (j == matrix_spalten-1) {
					System.out.println(current + " |");
				} else {
					System.out.print(current);
				}
			}
		}
	}
	
	
	public static void print_matrix(int[][] input_matrix) {
		// find largest number
		int max_length = 0;
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				if (max_length < Integer.toString(input_matrix[i][j]).length()) {
					max_length = Integer.toString(input_matrix[i][j]).length();
				}
			}
		}
		// pretty print
		for (int i=0; i<matrix_zeilen; i++) {
			System.out.print("|");
			for (int j=0; j<matrix_spalten; j++) {
				String current = Integer.toString(input_matrix[i][j]);
				// replace "0" with "." for more readability
				if (current.equals("0")) {
					current = ".";
				}
				// equallize length for largest appearing number
				while (current.length() < (max_length + 2)) {	// '+2' for spacer between columns
					current = " " + current;
				}
				// print in orderly fashion
				if (j == matrix_spalten-1) {
					System.out.println(current + " |");
				} else {
					System.out.print(current);
				}
			}
		}
	}
	
	
	public static int get_random(int range) {
		// return random int in range 0...'range'-1
		return (int)(Math.random() * range);
	}
	
	
	public static int is_matrix_full() {
		int full_space_counter = 0;
		// zähle wie viele Felder der Matrix einen Wert haben
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				if (main_matrix[i][j] != 0) {
					full_space_counter++;
				}
			}
		}
		if (full_space_counter == (matrix_zeilen * matrix_spalten)) {
			// Matrix ist voll
			return 1;
		} else {
			// Matrix ist nicht voll
			return 0;
		}
	}
	
	
	public static int is_game_over() {
		if (is_move_possible("to_right") == 1 || is_move_possible("to_left") == 1 || is_move_possible("to_top") == 1 || is_move_possible("to_bottom") == 1) {
			// at least one move is possible and thus the game is not over
			return 0;
		} else {
			// no move is possible and thus the game is over
			return 1;
		}
	}
	
	
	public static int get_matrix_score() {
		int score = 0;
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				// count all values together
				score += main_matrix[i][j];
			}
		}
		return score;
	}
	
	
	public static void master_matrix_shifter(String direction) {
		int[][] current_matrix = new int[matrix_zeilen][matrix_spalten];
		int[][] last_matrix = new int[matrix_zeilen][matrix_spalten];
		// 1. copy main_matrix into current_matrix
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				current_matrix[i][j] = main_matrix[i][j];
			}
		}
		int iterator = 0;
		while (!Arrays.deepEquals(current_matrix, last_matrix)) {
			// 2. copy current_matrix into last_matrix
			for (int i=0; i<matrix_zeilen; i++) {
				for (int j=0; j<matrix_spalten; j++) {
					last_matrix[i][j] = current_matrix[i][j];
				}
			}
			// 3. shift current_matrix
			current_matrix = slave_matrix_shifter(current_matrix, direction);
			
		}
		// 4. if current_matrix is equal to last_matrix, copy current_matrix into main_matrix
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				main_matrix[i][j] = current_matrix[i][j];
			}
		}
	}
	
	
	public static int[][] slave_matrix_shifter(int[][] input_matrix, String direction) {
		// undergo pass by reference errors
		int[][] current_matrix = new int[matrix_zeilen][matrix_spalten];
		for (int i=0; i<matrix_zeilen; i++) {
			for (int j=0; j<matrix_spalten; j++) {
				current_matrix[i][j] = input_matrix[i][j];
			}
		}
		// shifting operation
		if (direction.equals("to_left")) {
			// laufe spalten von der zweit-ersten spalte aus durch bis zur letzten spalte
			for (int j=1; j<matrix_spalten; j++) {
				// laufe zeilen von oben aus komplett durch
				for (int i=0; i<matrix_zeilen; i++) {
					if (current_matrix[i][j-1] == 0) {
						current_matrix[i][j-1] = current_matrix[i][j];
						current_matrix[i][j] = 0;
					} else if (current_matrix[i][j-1] == current_matrix[i][j]) {
						current_matrix[i][j-1] = current_matrix[i][j-1] + current_matrix[i][j];
						current_matrix[i][j] = 0;
					}
				}
			}
		} else if (direction.equals("to_right")) {
			// laufe spalten von der zweit-letzten spalte aus durch bis zur ersten spalte
			for (int j=matrix_spalten-2; j>-1; j--) {
				// laufe zeilen von oben aus komplett durch
				for (int i=0; i<matrix_zeilen; i++) {
					if (current_matrix[i][j+1] == 0) {
						current_matrix[i][j+1] = current_matrix[i][j];
						current_matrix[i][j] = 0;
					} else if (current_matrix[i][j+1] == current_matrix[i][j]) {
						current_matrix[i][j+1] = current_matrix[i][j+1] + current_matrix[i][j];
						current_matrix[i][j] = 0;
					}
				}
			}
		} else if (direction.equals("to_top")) {
			// laufe zeilen von der zweit-obersten bis zur untersten zeile
			for (int i=1; i<matrix_zeilen; i++) {
				// laufe spalten von links nach rechts komplett durch
				for (int j=0; j<matrix_spalten; j++) {
					if (current_matrix[i-1][j] == 0) {
						current_matrix[i-1][j] = current_matrix[i][j];
						current_matrix[i][j] = 0;
					} else if (current_matrix[i-1][j] == current_matrix[i][j]) {
						current_matrix[i-1][j] = current_matrix[i-1][j] + current_matrix[i][j];
						current_matrix[i][j] = 0;
					}
				}
			}
			
		} else if (direction.equals("to_bottom")) {
			// laufe zeilen von der zweit-untersten bis zur obersten zeile
			for (int i=matrix_zeilen-2; i>-1; i--) {
				// laufe spalten von links nach rechts komplett durch
				for (int j=0; j<matrix_spalten; j++) {
					if (current_matrix[i+1][j] == 0) {
						current_matrix[i+1][j] = current_matrix[i][j];
						current_matrix[i][j] = 0;
					} else if (current_matrix[i+1][j] == current_matrix[i][j]) {
						current_matrix[i+1][j] = current_matrix[i+1][j] + current_matrix[i][j];
						current_matrix[i][j] = 0;
					}
				}
			}
		} else {
			System.out.println("\n=================\nslave_matrix_shifter got wrong direction value: " + direction);
		}
		// return shifted matrix
		return current_matrix;
	}
	
	
	public static int is_move_possible(String direction) {
		if (Arrays.deepEquals(main_matrix, slave_matrix_shifter(main_matrix, direction))) {
			// orig matrix and once shifted matrix are equal and thus move not possible
			return 0;
		} else {
			// orig matrix and once shifted matrix are not equal and thus move is possible
			return 1;
		}
	}

}
