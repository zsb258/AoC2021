import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class day24 {
	static HashMap<Character, Integer> variables = new HashMap<>();

	private static class Operation {
		String type;
		Character a, b;
		Integer bVal;
		Operation (String type, char a) {
			this.type = type;
			this.a = a;
		}

		Operation (String type, char a, char b) {
			this(type, a);
			this.b = b;
		}

		Operation (String type, char a, int b) {
			this(type, a);
			this.bVal = b;
		}

		@Override
		public String toString() {
			if (b != null) {
				return type + " " + a + " " + b;
			}
			if (bVal != null) {
				return type + " " + a + " " + bVal;
			}
			return type + " " + a;
		}
	}

	private static String part1(List<Operation> operations) {
		initialiseVariables();
		int i = 0;
		do {
			System.out.println(operations.get(i));
			if (operations.get(i).type.equals("inp")) {
				resolveOperationInp(operations.get(i), 9);
			}
			else {
				resolveOperation(operations.get(i));
			}
			i++;
		} while (!operations.get(i).type.equals("inp"));
		for (char key : variables.keySet()) {
			System.out.println(key + ": " + variables.get(key));
		}

		return "";
	}

	private static void initialiseVariables() {
		variables.clear();
		variables.put('w', 0);
		variables.put('x', 0);
		variables.put('y', 0);
		variables.put('z', 0);
	}

	private static void resolveOperationInp (Operation op, int digit) {
		if (op.type.equals("inp")) {
			variables.put(op.a, digit);
		}
	}

	private static void resolveOperation (Operation op) {
		switch (op.type) {
			case "inp":
				break;
			case "add":
				if (op.b != null && variables.containsKey(op.b)) {
					variables.put(op.a, variables.get(op.a) + variables.get(op.b));
					break;
				}
				if (op.bVal != null) {
					variables.put(op.a, variables.get(op.a) + op.bVal);
					break;
				}
			case "mul":
				if (op.b != null && variables.containsKey(op.b)) {
					variables.put(op.a, variables.get(op.a) * variables.get(op.b));
					break;
				}
				if (op.bVal != null) {
					variables.put(op.a, variables.get(op.a) * op.bVal);
					break;
				}
			case "div":
				if (op.b != null && variables.containsKey(op.b)) {
					variables.put(op.a, variables.get(op.a) / variables.get(op.b));
					break;
				}
				if (op.bVal != null) {
					variables.put(op.a, variables.get(op.a) / op.bVal);
					break;
				}
			case "mod":
				if (op.b != null && variables.containsKey(op.b)) {
					variables.put(op.a, variables.get(op.a) % variables.get(op.b));
					break;
				}
				if (op.bVal != null) {
					variables.put(op.a, variables.get(op.a) % op.bVal);
					break;
				}
			case "eql":
				if (op.b != null && variables.containsKey(op.b)) {
					variables.put(op.a, (variables.get(op.a).equals(variables.get(op.b))) ? 1 : 0);
					break;
				}
				if (op.bVal != null) {
					variables.put(op.a, (variables.get(op.a).equals(op.bVal)) ? 1 : 0);
					break;
				}
		}
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day24_input.txt"));
		List<Operation> operations = new ArrayList<>();
		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");
			if (line.length == 2) {
				operations.add( new Operation(line[0], line[1].charAt(0)) );
				continue;
			}
			if (line.length == 3) {
				if (line[2].charAt(0) >= 'w') {
					operations.add(
							new Operation(line[0], line[1].charAt(0), line[2].charAt(0)) );
				}
				else {
					operations.add(
							new Operation(line[0], line[1].charAt(0), Integer.parseInt(line[2])) );
				}
			}
		}
//		for (Operation op : operations) {
//			System.out.println(op);
//		}
		part1(operations);
	}
}
