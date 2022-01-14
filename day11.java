import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day11 {
	public static int part1(int[][] energyMap, int numSteps){
		int res = 0;
		int step = 0;
		while (step < numSteps) {
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					process(energyMap, row, col);
				}
			}
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					if (energyMap[row][col] == -1){
						res++;
						energyMap[row][col] = 0;
					}
				}
			}
//			System.out.println(step);
//			System.out.println(Arrays.deepToString(energyMap));
			step++;
		}
		return res;
	}

	public static int part2(int[][] energyMap){
		int step = 0;
		while (true){
			step++;
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					process(energyMap, row, col);
				}
			}
			int count = 0;
			for (int row = 0; row < 10; row++) {
				for (int col = 0; col < 10; col++) {
					if (energyMap[row][col] == -1){
						count++;
						energyMap[row][col] = 0;
					}
				}
			}
//			System.out.println(step);
//			System.out.println(Arrays.deepToString(energyMap));
			if (count == 100){
				return step;
			}
		}
	}

	public static void process(int[][] energyMap, int row, int col){
		if (energyMap[row][col] == -1){
			// has flashed in this step
			return;
		}
		energyMap[row][col]++; // increase energy by 1 by default
		if (energyMap[row][col] > 9){
			// flash
			energyMap[row][col] = -1;
			for (int i=Math.max(0, row-1); i<=Math.min(row+1, 9); i++){
				for (int j=Math.max(0, col-1); j<=Math.min(col+1, 9); j++){
					if (i != row | j != col){
						process(energyMap, i, j);
					}
				}
			}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		File input = new File("./Inputs/day11_test_input.txt");
		Scanner sc = new Scanner(input);

		int[][] energyMap = new int[10][10];
		int lineNum = 0;
		while (sc.hasNextLine()){
			String line = sc.nextLine();
			char[] chars = line.toCharArray();
			int[] digits = new int[10];
			for (int i=0; i<10; i++) {
				digits[i] = Character.getNumericValue(chars[i]);
			}
			energyMap[lineNum] = digits;
			lineNum++;
		}

		int[][] energyMap1 = new int[10][10], energyMap2 = new int[10][10];
		for (int i = 0; i < 10; i++) {
			energyMap1[i] = energyMap[i].clone();
			energyMap2[i] = energyMap[i].clone();
		}


		System.out.println("Part1: " + part1(energyMap1, 100));
		System.out.println("Part2: " + part2(energyMap2));
	}
}
