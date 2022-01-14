import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day14 {
	public static int part1(char[] template, HashMap<String, Character> insertionMap){
		for (int step=0; step<10; step++){
			template = insert(template, insertionMap);
//			System.out.println(Arrays.toString(template));
		}
		int[] res = maxAndMinOccurrence(template);
		return res[0] - res[1];
	}

	public static long part2(char[] template, HashMap<String, Character> insertionMap){
		HashMap<Character, Long> letterCounts = new HashMap<>();
		for (char c : template){
			if (letterCounts.containsKey(c)){ //initialise letterCounts HashMap using char[] template
				letterCounts.replace(c, letterCounts.get(c) + 1);
				continue;
			}
			letterCounts.putIfAbsent(c, 1L);
		}

		HashMap<String, Long> pairCounts = new HashMap<>();
		for (int i=1; i<template.length; i++){ //initialise pairCounts HashMap using char[] template
			String pair = Character.toString(template[i-1]) + template[i];
			if (pairCounts.containsKey(pair)){
				pairCounts.replace(pair, pairCounts.get(pair) + 1);
			}
			else {
				pairCounts.putIfAbsent(pair, 1L);
			}
		}

		for (int step=0; step<40; step++) {
			HashMap<String, Long> updatedPairCounts = new HashMap<>();
			for (String pair : pairCounts.keySet()){
				long pairOccur = pairCounts.get(pair);
				if (insertionMap.containsKey(pair)){ // inserts char according to insertionMap
					char insertChar = insertionMap.get(pair);
					if (letterCounts.containsKey(insertChar)){ // updates letterCounts
						letterCounts.replace(insertChar, letterCounts.get(insertChar) + pairOccur);
					}
					else {
						letterCounts.putIfAbsent(insertChar, pairOccur);
					}

					// updates newPairCounts
					String first = Character.toString(pair.charAt(0)) + insertChar;
					String second = Character.toString(insertChar) + pair.charAt(1);
					if (updatedPairCounts.containsKey(first)){
						updatedPairCounts.replace(first, updatedPairCounts.get(first) + pairOccur);
					}
					else {
						updatedPairCounts.putIfAbsent(first, pairOccur);
					}
					if (updatedPairCounts.containsKey(second)){
						updatedPairCounts.replace(second, updatedPairCounts.get(second) + pairOccur);
					}
					else {
						updatedPairCounts.putIfAbsent(second, pairOccur);
					}
				}
			}
			pairCounts = updatedPairCounts;
//			System.out.println("After step " + (step+1));
//			for (String key : pairCounts.keySet()){
//				System.out.println(key + ": " + pairCounts.get(key));
//			}
//			for (char key : letterCounts.keySet()){
//				System.out.println(key + ": " + letterCounts.get(key));
//			}
		}

		return Collections.max(letterCounts.values()) - Collections.min(letterCounts.values());
	}

	public static char[] insert(char[] template, HashMap<String, Character> insertionMap){
		List<Character> insertedPolymer = new ArrayList<>();
		insertedPolymer.add(template[0]);
		for (int i=1; i< template.length; i++){
			String pair = Character.toString(template[i-1]) + template[i];
			if (insertionMap.containsKey(pair)){
				insertedPolymer.add(insertionMap.get(pair));
			}
			insertedPolymer.add(template[i]);
		}


		char[] res = new char[insertedPolymer.size()];
		for (int i=0; i<insertedPolymer.size(); i++){
			res[i] = insertedPolymer.get(i);
		}
		return res;
	}

	public static int[] maxAndMinOccurrence(char[] polymer){
		HashMap<Character, Integer> counts = new HashMap<>();
		for (char c : polymer){
			if (counts.containsKey(c)){
				counts.replace(c, counts.get(c) + 1);
				continue;
			}
			counts.putIfAbsent(c, 1);
		}
		return new int[]{Collections.max(counts.values()), Collections.min(counts.values())};
	}

	public static void main(String[] args) throws FileNotFoundException {
		File input = new File("./Inputs/day14_input.txt");
		Scanner sc = new Scanner(input);
		char[] template = sc.nextLine().toCharArray();
		sc.nextLine(); // skips empty line
		HashMap<String, Character> insertionMap = new HashMap<>();
		while (sc.hasNextLine()){
			String[] line = sc.nextLine().split(" -> ");
			insertionMap.put(line[0], line[1].charAt(0));
		}

		System.out.println("Part1: " + part1(template, insertionMap));
		System.out.println("Part2: " + part2(template, insertionMap));
	}
}
