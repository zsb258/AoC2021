import java.util.*;
import java.io.*;

public class day7 {
	public static int part1(HashMap<Integer, Integer> positions, int maxInput){
		int minSumDist= Integer.MAX_VALUE;
		for (int i=0; i<=maxInput; i++){
			int sumDist = 0;
			for (int key : positions.keySet()){
				sumDist += Math.abs(key - i) * positions.get(key);
			}
			if (sumDist < minSumDist){
				minSumDist = sumDist;
			}
		}

		return minSumDist;
	}

	public static long part2(HashMap<Integer, Integer> positions, int maxInput){
		long minSumFuel= Integer.MAX_VALUE;
		for (int i=0; i<=maxInput; i++){
			long sumFuel = 0;
			for (int key : positions.keySet()){
				int dist = Math.abs(key - i);
				long fuel = Math.round(((double)0.5 * (1+dist) * dist));
				sumFuel += fuel * positions.get(key);
			}
			if (sumFuel < minSumFuel){
				minSumFuel = sumFuel;
			}
		}

		return minSumFuel;
	}

	public static void main(String[] args){
		HashMap<Integer, Integer> positions = new HashMap<>();
		int maxInput = Integer.MIN_VALUE;
		try {
			File input = new File("./Inputs/day7_input.txt");
			Scanner sc = new Scanner(input);
			sc.useDelimiter(",");

			while (sc.hasNextInt()){
				int key = sc.nextInt();
				if (positions.containsKey(key)){
					positions.put(key, positions.get(key) + 1);
				}
				positions.putIfAbsent(key, 1);

				if (key > maxInput){
					maxInput = key;
				}
			}
		}
		catch (FileNotFoundException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
//		int res = 0;
//		for (int key : positions.keySet()){
//			System.out.println("" + key + ": " + positions.get(key));
//			res += positions.get(key);
//		}
//		System.out.println("Sum of values: " + res);

		System.out.println("Part 1: " + part1(positions, maxInput));
		System.out.println("Part 2: " + part2(positions, maxInput));
	}
}
