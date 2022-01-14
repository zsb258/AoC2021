import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class day4 {
	static int N = 5; // length of Bingo board

	public static int part1(int[] draws, int[][][] boards, int[][][] scoreboards){
		for (int i=0; i<4; i++){
			// mark first 4 draws
			// there will not be a winning board yet
			int val = draws[i];
			for (int j=0; j<boards.length; j++){
				scoreboards[j] = markBoard(val, boards[j], scoreboards[j]);
			}
		}

		for (int i=4; i< draws.length; i++){
			// starts with 5th draw
			int val = draws[i];
			for (int j=0; j<boards.length; j++){
				scoreboards[j] = markBoard(val, boards[j], scoreboards[j]);
				if (checkBoard(scoreboards[j])==true){
					int unmarkedSum = scoreBingoBoard(boards[j], scoreboards[j]);
					return val * unmarkedSum;
				}
			}
		}

		return -1;
	}

	public static int part2(int[] draws, int[][][] boards, int[][][] scoreboards){
		for (int i=0; i<4; i++){
			// mark first 4 draws
			// there will not be a winning board yet
			int val = draws[i];
			for (int j=0; j<boards.length; j++){
				scoreboards[j] = markBoard(val, boards[j], scoreboards[j]);
			}
		}

		int boardsWon = 0, numBoards = boards.length;
		int[] tracker = new int[numBoards];

		for (int i=4; i< draws.length; i++){
			// starts with 5th draw
			int val = draws[i];
			for (int j=0; j<numBoards; j++){
				if (tracker[j]==1){
					continue;
				}
				scoreboards[j] = markBoard(val, boards[j], scoreboards[j]);
				if (checkBoard(scoreboards[j])==true){
					boardsWon += 1;
					tracker[j] = 1;
					if (boardsWon == boards.length) {
						int unmarkedSum = scoreBingoBoard(boards[j], scoreboards[j]);
						return val * unmarkedSum;
					}
				}

			}
		}

		return -1;
	}

	public static int[][] markBoard(int val, int[][] board, int[][] scoreboard){
		// update scoreboard after each new draw (val)
		// in scoreboard, 1 means marked and 0 means unmarked
		for (int i=0; i<N; i++) {
			for (int j = 0; j < N; j++) {
				if (board[i][j]==val && scoreboard[i][j]==0){
					scoreboard[i][j] = 1;
				}
			}
		}

		return scoreboard;
	}

	public static boolean checkBoard(int[][] scoreboard){
		// to check if the board wins
		int[] colSums = new int[N];
		for (int i=0; i<N; i++) {
			int rowSum = 0;
			for (int j = 0; j < N; j++) {
				int temp = scoreboard[i][j];
				rowSum += temp;
				colSums[j] += temp;
			}
			if (rowSum==N){
				return true;
			}
		}
		for (int val: colSums){
			if (val==5){
				return true;
			}
		}

		return false;
	}

	public static int scoreBingoBoard(int[][] board, int[][] scoreboard){
		// to return the sum of unmarked numbers
		int res = 0;
		for (int i=0; i<N; i++) {
			for (int j = 0; j < N; j++) {
				if (scoreboard[i][j]==0){
					res += board[i][j];
				}
			}
		}

		return res;
	}

	public static void main(String[] args){
		ArrayList<int[][]> tempBoards = new ArrayList<>();
		ArrayList<Integer> tempDraws = new ArrayList<>();
		try {
			File my_input = new File("./Inputs/day4_input.txt");
			Scanner sc = new Scanner(my_input);

			String line = sc.nextLine();
			String[] drawsStr = line.split(",");
			for (String s : drawsStr) {
				tempDraws.add(Integer.parseInt(s));
			}


			while (sc.hasNext()) {
				int[][] temp = new int[N][N];
				for (int i=0; i<N; i++) {
					for (int j=0; j < N; j++) {
						temp[i][j] = sc.nextInt();
					}
				}
				tempBoards.add(temp);
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		int[] draws = new int[tempDraws.size()];
		for (int i=0; i<tempDraws.size(); i++){
			draws[i] = tempDraws.get(i);
		}

		int[][][] boards = new int[tempBoards.size()][N][N];
		for (int i=0; i<tempBoards.size(); i++){
			boards[i] = tempBoards.get(i);
		}

		int[][][] scoreboards = new int[boards.length][N][N];

//		System.out.println(Arrays.toString(draws));
//		System.out.println(Arrays.deepToString(boards));
//		System.out.println(Arrays.deepToString(scoreboards));

		System.out.println("Part1: " + part1(draws, boards, scoreboards));
		System.out.println("Part2: " + part2(draws, boards, scoreboards));

	}
}
