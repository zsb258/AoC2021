import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day9 {
	public static int part1(int[][] map){
		int res = 0;
		for (int row=0; row<map.length; row++){
			for (int col=0; col<map[0].length; col++){
				int curr = map[row][col];
				int count = 0;
				List<Integer> neighbours = findVHNeighbours(map, row, col);
				for (int neighbour : neighbours){
					if (curr < neighbour){
						count += 1;
					}
				}
				if (count==neighbours.size()){
//					System.out.println(curr + ": " + row + ", " + col);
					res += curr + 1;
				}
			}
		}
		return res;
	}

	public static int part2(int[][] map){
		PriorityQueue<Integer> res = new PriorityQueue<>(Collections.reverseOrder());
		res.add(0);
		res.add(0);
		res.add(0);

		for (int row=0; row<map.length; row++){
			for (int col=0; col<map[0].length; col++){
				int curr = map[row][col];
				int count = 0;
				List<Integer> neighbours = findVHNeighbours(map, row, col);
				for (int neighbour : neighbours){
					if (curr < neighbour){
						count += 1;
					}
				}
				if (count==neighbours.size()){
					int size = findSize(map, row, col);
//					System.out.println(size);
					res.add(size);
//					System.out.println(res);

				}

			}
		}
		int a = 0, b = 0, c = 0;
		if (!res.isEmpty()) {
			a = res.poll();
		}
//		System.out.println("a: " + a);
		if (!res.isEmpty()) {
			b = res.poll();
		}
//		System.out.println("b: " + b);
		if (!res.isEmpty()) {
			c = res.poll();
		}
//		System.out.println("c: " + c);
		return a * b * c;

	}

	public static int findSize(int[][] map, int row, int col){
		int curr = map[row][col];
		if (curr==9 | curr==99){
			return 0;
		}

		int rowLimit = map.length-1;
		int colLimit = map[0].length-1;
		map[row][col] = 99;
		int res = 1;

		if ((row - 1) >= 0){
			// top
			res += findSize(map, row-1, col);
		}
		if ((row + 1) <= rowLimit) {
			// bott
			res += findSize(map, row+1, col);
		}
		if ((col - 1) >= 0) {
			// left
			res += findSize(map, row, col-1);
		}
		if ((col + 1) <= colLimit) {
			// right
			res += findSize(map, row, col+1);
		}
		return res;
	}

	public static List<Integer> findVHNeighbours(int[][] map, int row, int col){
		// find vertical and horizontal neighbours
		int rowLimit = map.length-1;
		int colLimit = map[0].length-1;

		List<Integer> res = new ArrayList<>();
		if ((row - 1) >= 0){
			// top
			res.add(map[Math.max(0, row-1)][col]);
		}
		if ((row + 1) <= rowLimit) {
			// bott
			res.add(map[Math.min(row + 1, rowLimit)][col]);
		}
		if ((col - 1) >= 0) {
			// left
			res.add(map[row][Math.max(0, col - 1)]);
		}
		if ((col + 1) <= colLimit) {
			// right
			res.add(map[row][Math.min(col+1, colLimit)]);
		}

//		System.out.println(res);
		return res;
	}

	public static List<Integer> findNeighbours(int[][] map, int row, int col){
		// find neighbours including diagonal
		int rowLimit = map.length-1;
		int colLimit = map[0].length-1;
		List<Integer> res = new ArrayList<>();
		for (int i=Math.max(0, row-1); i<=Math.min(row+1, rowLimit); i++){
			for (int j=Math.max(0, col-1); j<=Math.min(col+1, colLimit); j++){
				if (i != row | j != col){
					res.add(map[i][j]);
				}
			}
		}
		System.out.println(res);
		return res;
	}

	public static void main(String[] args) throws FileNotFoundException {
		List<int[]> mapList = new ArrayList<>();
		File input = new File("./Inputs/day9_input.txt");
		Scanner sc = new Scanner(input);
		while (sc.hasNextLine()){
			String line = sc.nextLine();
			char[] chars = line.toCharArray();
			int[] row = new int[chars.length];
			for (int i=0; i< chars.length; i++){
				row[i] = Character.getNumericValue(chars[i]);
			}
			mapList.add(row);
		}
		int[][] map = new int[mapList.size()][];
		for (int i=0; i< mapList.size(); i++){
			map[i] = mapList.get(i);
		}
//		System.out.println(map[0].length);

		System.out.println("Part1: " + part1(map));
		System.out.println("Part2: " + part2(map));
	}
}
