import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day15 {
	private static int part1_right_down_only(int[][] map){
		int sizeLimit = map.length - 1;
		int[][] memo = new int[map.length][map.length];
		for (int row=0; row<map.length; row++){
			for (int col=0; col<map.length; col++){
				if (row==0 && col==0){
					memo[row][col] = 0;
					continue;
				}
				if (row == 0){
					memo[row][col] = memo[row][col-1] + map[row][col];
				}
				else if (col == 0){
					memo[row][col] = memo[row-1][col] + map[row][col];
				}
				else {
					memo[row][col] = Math.min(memo[row][col-1], memo[row-1][col]) + map[row][col];
				}
			}
		}
//		for (int[] row : memo){
//			System.out.println(Arrays.toString(row));
//		}
		return memo[sizeLimit][sizeLimit];
	}

	private static int part1_Dijkstra(int[][] map) {
		int size = map.length;
		int[][] memo = new int[map.length][map.length];
		for (int row=0; row<map.length; row++){
			for (int col=0; col<map.length; col++){
				if (row==0 && col==0){
					memo[row][col] = 0;
					continue;
				}
				memo[row][col] = Integer.MAX_VALUE;
			}
		}

		PriorityQueue<Node> pq = new PriorityQueue<>();
		Set<Coordinate> seen = new HashSet<>();
		Node start = new Node( new Coordinate(0, 0, size) );
		start.minCost = 0;
		pq.add(start);
		Coordinate endCoord = new Coordinate(size-1, size-1, size);

		while (!pq.isEmpty() && pq.peek().coordinate.equals(endCoord)) {
			var top = pq.poll();
			if (seen.contains(top.coordinate)){
				continue;
			}
			seen.add(top.coordinate);
			if (top.coordinate.equals(endCoord)){
				return top.minCost;
			}
			for (Coordinate adjCoord : top.coordinate.getAdjacent()) {
				if (!seen.contains(adjCoord)) {
					int alt = top.minCost + map[adjCoord.row][adjCoord.col];
					pq.add(new Node(adjCoord));
				}
			}
		}

		return 0;
	}

	private static int part2(){
		return 0;
	}

	private static class Node implements Comparable<Node>{
		Coordinate coordinate;
		int minCost;

		Node (Coordinate coordinate){
			this.coordinate = coordinate;
			this.minCost = Integer.MAX_VALUE;
		}


		@Override
		public int compareTo(Node node2) {
			if (this.minCost != node2.minCost){
				return this.minCost - node2.minCost;
			}

			return 0;
		}
	}

	private static class Coordinate implements Comparable<Coordinate>{
		int row, col, limit;
		Coordinate(int row, int col, int size) {
			this.row = row;
			this.col = col;
			this.limit = size - 1;
		}

		List<Coordinate> getAdjacent(){
			List<Coordinate> res = new ArrayList<>();
			if (col-1 >= 0){
				res.add(new Coordinate(row, col-1, limit+1));
			}
			if (col+1 <= limit){
				res.add(new Coordinate(row, col+1, limit+1));
			}
			if (row-1 >= 0){
				res.add(new Coordinate(row-1, col, limit+1));
			}
			if (row+1 <= limit){
				res.add(new Coordinate(row+1, col, limit+1));
			}
			return res;
		}

		@Override
		public int compareTo(Coordinate coord2) {
			if (this.row==coord2.row && this.col== coord2.col) {
				return 0;
			}
			if (this.row != coord2.row) {
				return this.row - coord2.row;
			}
			return this.col - coord2.col;
		}

		@Override
		public int hashCode() {
			return this.col + this.row;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null)
				return false;
			if (getClass() != o.getClass())
				return false;
			Coordinate other = (Coordinate) o;
			if (this.row != other.row)
				return false;
			if (this.col != other.col)
				return false;
			return true;
		}
	}

	private static int[][] readFile(String path) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(path));

		char[] chars = sc.nextLine().toCharArray();
		int n = chars.length;
		int[][] map = new int[n][n];
		for (int i=0; i<n; i++){
			map[0][i] = chars[i] - '0';
		}
		while (sc.hasNextLine()){
			for (int i=1; i<n; i++){
				char[] line = sc.nextLine().toCharArray();
				for (int j=0; j<n; j++){
					map[i][j] = line[j] - '0';
				}
			}
		}
		return map;
	}

	public static void main(String[] args) throws FileNotFoundException {
		int[][] map = readFile("./Inputs/day15_input.txt");
		System.out.println("Part1: " + part1_right_down_only(map));

	}
}
