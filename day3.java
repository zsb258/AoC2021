import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class day3 {
	public static int part1(ArrayList<String> values){
		int x = values.size();
		int[] counts = new int[values.get(0).length()];
		Arrays.fill(counts, 0);

		for (String s: values){
			for (int i=0; i<values.get(0).length(); i++){
				counts[i] += Character.getNumericValue(s.charAt(i));
			}
		}

		String gamma = "", epsilon = "";
		for (int val: counts){
			if (val > x/2){
				gamma = gamma.concat("1");
				epsilon = epsilon.concat("0");
			}
			else {
				gamma = gamma.concat("0");
				epsilon = epsilon.concat("1");
			}
		}
		return Integer.parseInt(gamma, 2) * Integer.parseInt(epsilon, 2);
	}

	public static int part2(ArrayList<String> values){
		String oxygen = findOxygen(values), co2 = findCO2(values);
		return Integer.parseInt(oxygen, 2) * Integer.parseInt(co2, 2);
	}

	public static String findOxygen(ArrayList<String> values){
		int i = 0;
		int strLen = values.get(0).length();
		while (i<strLen && values.size()>1){
			double x = values.size();
			int curr = 0;
			ArrayList<String> ones = new ArrayList<>(), zeros = new ArrayList<>();
			for (String s: values){
				if (Character.getNumericValue(s.charAt(i))==1){
					curr ++;
					ones.add(s);
				}
				else{
					zeros.add(s);
				}
			}

			if (curr >= x/2){
				values = ones;
			}
			else {
				values = zeros;
			}
			i++;
		}
		return values.get(0);
	}

	public static String findCO2(ArrayList<String> values){
		int i = 0;
		int strLen = values.get(0).length();
		while (i<strLen && values.size()>1){
			double x = values.size();
			int curr = 0;
			ArrayList<String> ones = new ArrayList<>(), zeros = new ArrayList<>();
			for (String s: values){
				if (Character.getNumericValue(s.charAt(i))==1){
					curr ++;
					ones.add(s);
				}
				else{
					zeros.add(s);
				}
			}

			if (curr < x/2){
				values = ones;
			}
			else {
				values = zeros;
			}
			i++;
		}
		return values.get(0);
	}

	public static void main(String[] args){
		ArrayList<String> values = new ArrayList<>();
		try {
			File my_input = new File("./Inputs/day3_input.txt");
			Scanner sc = new Scanner(my_input);

			while (sc.hasNextLine()) {
				String data = sc.nextLine();
				values.add(data);
			}
			sc.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		System.out.println(part1(values));
		System.out.println(part2(values));
//		String oxygen = findOxygen(values), co2 = findCO2(values);
//		System.out.println(oxygen);
//		System.out.println(co2);
	}
}
