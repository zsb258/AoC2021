import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class day13 {
	public static int part1(int[][] map, char axis, int val){
		int res = 0;
		int[][] foldedMap = fold(map, axis, val);

		for (int y=0; y<foldedMap.length; y++){
			for (int x=0; x<foldedMap[0].length; x++){
				if (foldedMap[y][x] >= 1){
					res++;
				}
			}
		}
		return res;
	}

	public static int[][] part2(int[][] map, char[] axes, int[] values){
		int[][] res = map.clone();

		for (int i=0; i< axes.length; i++){
			res = fold(res, axes[i], values[i]);
		}
		for (int y=0; y<res.length; y++){
			for (int x=0; x<res[0].length; x++){
				if (res[y][x] >= 1){
					res[y][x] = 1;
				}
			}
		}
		return res;
	}

	public static int[][] fold(int[][] map, char axis, int val){
		int[][] foldedMap;
		if (axis=='y'){
			foldedMap = new int[val][map[0].length];
			for (int x=0; x<map[0].length; x++) {
				for (int y=0; y<val; y++) {
					foldedMap[y][x] = map[y][x] + map[map.length-1-y][x];
				}
			}
		}
		else {
			foldedMap = new int[map.length][val];
			for (int x=0; x<val; x++){
				for (int y=0; y<map.length; y++) {
					foldedMap[y][x] = map[y][x] + map[y][map[0].length-1-x];
				}
			}
		}
		return foldedMap;
	}


	public static void main(String[] args) throws FileNotFoundException {
//		File input = new File("./Inputs/day13_test_input.txt");
//		Scanner sc = new Scanner(input);
//		int[][] map = new int[15][11];
//		for (int i=0; i<18; i++){
//			String[] pt = sc.nextLine().split(",");
//			map[Integer.parseInt(pt[1])][Integer.parseInt(pt[0])] = 1;
//		}

		File input = new File("./Inputs/day13_input.txt");
		Scanner sc = new Scanner(input);
		int[][] map = new int[895][1311];
		for (int i=0; i<959; i++){
			String[] pt = sc.nextLine().split(",");
			map[Integer.parseInt(pt[1])][Integer.parseInt(pt[0])] = 1;
		}

		sc.nextLine(); //skips empty line
		char[] axes = new char[12];
		int[] values = new int[12];

		for (int i=0; i<12; i++) {
			String[] line = sc.nextLine().split(" ");
			String[] firstFold = line[2].split("=");
			axes[i] = firstFold[0].charAt(0);
			values[i] = Integer.parseInt(firstFold[1]);
			System.out.println("Along " + axes[i] + "=" + values[i]);
		}


		System.out.println("Part1: " + part1(map, axes[0], values[0]));
//		int[][] first = fold(map,'y',7);
//		int[][] foldedMap = fold(first, 'x', 5);
//		for (int[] row : first){
//			System.out.println(Arrays.toString(row));
//		}
//		System.out.println("=================");
//		for (int[] row : foldedMap){
//			System.out.println(Arrays.toString(row));
//		}
		System.out.println("Part2: ");
		int[][] partTwo = part2(map, axes, values);
		for (int[] row : partTwo){
			System.out.println(Arrays.toString(row));
		}
	}
}
