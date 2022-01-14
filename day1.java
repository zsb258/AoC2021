import java.io.*;
import java.util.*;

public class day1 {
	public static int part1(ArrayList<Integer> a){
		int res = 0;
		for (int i=0; i<a.size(); i++){
			if (i > 0){
				if (a.get(i) > a.get(i-1)){
					res += 1;
				}
			}
		}

		return res;
	}

	public static int part2(ArrayList<Integer> a){
		int res = 0;
		int prevWindow = 0, thisWindow = 0;
		for (int i=0; i<a.size(); i++){
			if (i <= 2){
				prevWindow += a.get(i);
			}
			if (i > 2){
				thisWindow = prevWindow - a.get(i-3) + a.get(i);
				if (thisWindow > prevWindow){
					res += 1;
				}
				prevWindow = thisWindow;
			}
		}

		return res;
	}

	public static void main(String[] args) {
		ArrayList<Integer> a = new ArrayList<Integer>();
		try {
			File my_input = new File("./Inputs/day1_input.txt");

			Scanner sc = new Scanner(my_input);
			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				a.add(Integer.parseInt(data));
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}

		System.out.println(part1(a));
		System.out.println(part2(a));


	}
}


