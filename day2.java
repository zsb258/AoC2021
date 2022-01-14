import java.io.*;
import java.util.*;

public class day2 {
	public static int part1(ArrayList<String> moves, ArrayList<Integer> values){
		int pos = 0, depth = 0;
		for (int i=0; i<moves.size(); i++){
			String move = moves.get(i);
			int val = values.get(i);
			if (move.equals("forward")){
				pos += val;
			}
			else if (move.equals("down")){
				depth += val;
			}
			else{
				depth -= val;
			}
		}

		return pos * depth;
	}

	public static int part2(ArrayList<String> moves, ArrayList<Integer> values){
		int pos = 0, depth = 0, aim = 0;
		for (int i=0; i<moves.size(); i++){
			String move = moves.get(i);
			int val = values.get(i);
			if (move.equals("down")){
				aim += val;
			}
			else if (move.equals("up")){
				aim -= val;
			}
			else{
				pos += val;
				depth += val * aim;
			}
		}

		return pos * depth;
	}


	public static void main(String[] args){
		ArrayList<String> moves = new ArrayList<>();
		ArrayList<Integer> values = new ArrayList<>();
		try {
			File my_input = new File("./Inputs/day2_input.txt");

			Scanner sc = new Scanner(my_input);
			int i = 0;
			while (sc.hasNext()) {
				String data = sc.next();
				if (i==0) {
					moves.add(data);
					i++;
				}
				else {
					values.add(Integer.parseInt(data));
					i--;
				}
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println(part1(moves, values));
		System.out.println(part2(moves, values));
	}
}
