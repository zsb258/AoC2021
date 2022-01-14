import java.io.*;
import java.util.*;

public class day5 {
	public static class LineSegment {
		int x1, y1, x2, y2;
		int[] start = {x1, y1};
		int[] end = {x2, y2};


		LineSegment(int[] s, int[] e){
			this.x1 = s[0];
			this.y1 = s[1];
			this.x2 = e[0];
			this.y2 = e[1];
		}

		public boolean checkHorizontalOrVertical(){
			return (this.x1 == this.x2 || this.y1 == this.y2);
		}

		public int[][] getPtsCovered(){
			int numPts;
			int dx = 0;
			int xStep = 1;
			int dy = 0;
			int yStep = 1;
			if (this.x1 == this.x2){
				// vertical line
				numPts = Math.abs(this.y2 - this.y1) + 1;
				xStep = 0;
				if (this.y1 > this.y2){
					yStep = -1;
				}
			}
			else if (this.y1 == this.y2) {
				// horizontal lines
				numPts = Math.abs(this.x2 - this.x1) + 1;
				yStep = 0;
				if (this.x1 > this.x2){
					xStep = -1;
				}
			}
			else {
				// 45 degree diagonal lines
				numPts = Math.abs(this.x2 - this.x1) + 1;
				if (this.x1 > this.x2){
					xStep = -1;
				}
				if (this.y1 > this.y2){
					yStep = -1;
				}
			}

			int[][] res = new int[numPts][2];
			int i = 0;
			while (i<numPts){
				res[i] = new int[]{this.x1 + dx, this.y1 + dy};
				i++;
				dx += xStep;
				dy += yStep;
			}

			return res;
		}
	}

	public static int part1(List<LineSegment> segments){
		HashMap<String, Integer> map = new HashMap<>();
		for (LineSegment segment : segments) {
			if (segment.checkHorizontalOrVertical()) {
				int[][] ptsCovered = segment.getPtsCovered();
				for (int[] pt : ptsCovered) {
					String ptStr = Integer.toString(pt[0]).concat(",");
					ptStr = ptStr.concat(Integer.toString(pt[1]));
					if (map.containsKey(ptStr)) {
						int val = map.get(ptStr);
						map.put(ptStr, val + 1);
					} else {
						map.put(ptStr, 1);
					}
				}
			}
		}

		int res = 0;
		for (String key: map.keySet()){
			if (map.get(key) > 1){
				res += 1;
			}
		}
		return res;
	}

	public static int part2(List<LineSegment> segments){
		HashMap<String, Integer> map = new HashMap<>();
		for (LineSegment segment : segments){
			int[][] ptsCovered = segment.getPtsCovered();
			for (int[] pt : ptsCovered){
				String ptStr = Integer.toString(pt[0]).concat(",");
				ptStr = ptStr.concat(Integer.toString(pt[1]));
				if (map.containsKey(ptStr)){
					int val = map.get(ptStr);
					map.put(ptStr, val + 1);
				}
				else {
					map.put(ptStr, 1);
				}
			}
		}

		int res = 0;
		for (String key: map.keySet()){
			if (map.get(key) > 1){
				res += 1;
			}
		}
//		System.out.println(map.size());
//		map.forEach((key, value) -> System.out.println(key + " " + value));
		return res;
	}

	public static void main(String[] args){
		List<LineSegment> segments = new ArrayList<>();
		try {
			File input = new File("./Inputs/day5_input.txt");
			Scanner sc = new Scanner(input);

			while (sc.hasNextLine()){
				String[] line = sc.nextLine().split(" -> ");
				String[] ptOne = line[0].split(",");
				String[] ptTwo = line[1].split(",");

				int[] start = {Integer.parseInt(ptOne[0]), Integer.parseInt(ptOne[1])};
				int[] end = {Integer.parseInt(ptTwo[0]), Integer.parseInt(ptTwo[1])};

				LineSegment segment = new LineSegment(start, end);
				segments.add(segment);
			}
		}
		catch (FileNotFoundException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		System.out.println("Part1: " + part1(segments));
		System.out.println("Part2: " + part2(segments));

	}
}
