import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day10 {
	public static int part1(List<String> lines, HashMap<Character, Integer> syntaxErrorScores){
		int res = 0;
		for (String line : lines){
			Stack<Character> stack = new Stack<>();
			for (char c : line.toCharArray()){
				if (c=='(' | c=='[' | c=='{' | c=='<'){
					stack.push(c);
					continue;
				}
				if (!stack.empty()){
					char top = stack.pop();
					if (checkNonMatch(top, c)){
						// brackets do not match
						res += syntaxErrorScores.get(c);
					}
				}
			}
		}
		return res;
	}

	public static long part2(List<String> lines, HashMap<Character, Integer> autocompleteScores){
		List<Long> res = new ArrayList<>();
		for (String line : lines){
			long score = 0;
			Stack<Character> stack = processCorruptedLines(line);
			if (stack.empty()){
				// case of correct line or corrupted line
				continue;
			}
			// case of incomplete line
			while (!stack.empty()){
				char c = stack.pop();
//				System.out.println(score);
//				System.out.println(c);
				score = score * 5 + autocompleteScores.get(c);
			}
//			System.out.println(score);
//			System.out.println("==================");
			res.add(score);
		}
//		System.out.println(res);
		Collections.sort(res);
//		System.out.println(res);
		return res.get(res.size()/2);
	}

	public static boolean checkNonMatch(char top, char c){
		if (top=='(' && c==')'){
			return false;
		}
		else if (top=='[' && c==']'){
			return false;
		}
		else if (top=='{' && c=='}'){
			return false;
		}
		else return top != '<' || c != '>';
	}

	public static Stack<Character> processCorruptedLines(String line){
		Stack<Character> stack = new Stack<>();
		for (char c : line.toCharArray()){
			if (c=='(' | c=='[' | c=='{' | c=='<'){
				stack.push(c);
				continue;
			}
			if (!stack.empty()){
				char top = stack.pop();
				if (checkNonMatch(top, c)){
					// brackets do not match => corrupted line
					return new Stack<Character>();
				}
			}
		}
		return stack;
	}

	public static HashMap<Character, Integer> createSyntaxErrorScores(){
		HashMap<Character, Integer> res = new HashMap<>();
		res.put(')', 3);
		res.put(']', 57);
		res.put('}', 1197);
		res.put('>', 25137);
		return res;
	}

	public static HashMap<Character, Integer> createAutocompleteScores(){
		HashMap<Character, Integer> res = new HashMap<>();
		res.put('(', 1);
		res.put('[', 2);
		res.put('{', 3);
		res.put('<', 4);
		return res;
	}

	public static void main(String[] args) throws FileNotFoundException {
		File input = new File("./Inputs/day10_input.txt");
		Scanner sc = new Scanner(input);
		List<String> lines = new ArrayList<>();
		while (sc.hasNextLine()){
			lines.add(sc.nextLine());
		}
		HashMap<Character, Integer> syntaxErrorScores = createSyntaxErrorScores();
		HashMap<Character, Integer> autocompleteScores = createAutocompleteScores();

		System.out.println("Part1: " + part1(lines, syntaxErrorScores));
		System.out.println("Part2: " + part2(lines, autocompleteScores));
	}
}
