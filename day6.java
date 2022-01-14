import java.util.*;
import java.io.*;

public class day6 {
	public static long part1(HashMap<Integer, Long> record, int duration){
		for (int i=0; i<duration; i++){
			record = nextDay(record);
		}

		long res = 0;
		for (long val : record.values()){
			res += val;
		}

		for (int key: record.keySet()){
			System.out.println("" + key + ": " + record.get(key));
		}

		return res;
	}

	public static long part2(HashMap<Integer, Long> record, int duration){
		return part1(record, duration);
	}

	public static HashMap<Integer, Long> nextDay(HashMap<Integer, Long> record){
		HashMap<Integer, Long> res = new HashMap<Integer, Long>();
		for (int i=0; i<9; i++){
			// initialise record
			res.put(i, 0L);
		}
		for (int key : record.keySet()){
			if (key==0){
				res.put(6, res.get(6) + record.get(key));
				res.put(8, res.get(8) + record.get(key)); // newborn fish
			}
			else {
				res.put(key-1, res.get(key-1) + record.get(key));
			}
		}
		return res;
	}


	public static void main(String[] args){
		HashMap<Integer, Long> record = new HashMap<Integer, Long>();
		try {
			File input = new File("./Inputs/day6_input.txt");
			Scanner sc = new Scanner(input);
			sc.useDelimiter(",");
			while (sc.hasNextInt()){
				int val = sc.nextInt();
//				System.out.println(val);
				if (record.containsKey(val)){
					record.put(val, record.get(val) + 1);
				}
				else {
					record.put(val, 1L);
				}
			}
		}
		catch (FileNotFoundException e){
			System.out.println("An error occurred.");
			e.printStackTrace();
		}


		System.out.println("Part1: " + part1(record, 80));
		System.out.println("Part2: " + part2(record, 256));

	}
}
