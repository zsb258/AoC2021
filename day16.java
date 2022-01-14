import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.function.Function;

public class day16 {
	static int versionSum = 0;
	static String input = "";
	static int idx = 0;

	day16 () {
		versionSum = 0;
		input = "";
		idx = 0;
	}

	private static void readInput() throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day16_input.txt"));
		char[] line = sc.nextLine().toCharArray();
		sc.close();
		HashMap<Character, String> conversionMap = hexadecimalToBinaryMap();
		for (char c : line) {
			input = input.concat(conversionMap.get(c));
		}
	}

	private static long readPacket() {
		System.out.printf("Reading packet beginning at index %d%n", idx);
		int version = Integer.parseInt(input.substring(idx, idx+3), 2);
		versionSum += version;
		int type = Integer.parseInt(input.substring(idx+3, idx+6), 2);

		idx += 6;
		if (type == 4) { // literal value
			return processLiteralValue();
		}
		else {
			char lengthType = input.charAt(idx);
			idx++;
			if (lengthType == '0') {
				Function<List<Long>, Long> fn;
				switch (type) {
					case 0:
						fn = integers -> {
							long res = 0L;
							for (long val : integers) {
								res += val;
							}
							return res;
						};
						break;
					case 1:
						fn = integers -> {
							long res = 1L;
							for (long val : integers) {
								res *= val;
							}
							return res;
						};
						break;
					case 2:
						fn = integers -> {
							long res = Long.MAX_VALUE;
							for (long val : integers) {
								if (val < res) {
									res = val;
								}
							}
							return res;
						};
						break;
					case 3:
						fn = integers -> {
							long res = Long.MIN_VALUE;
							for (long val : integers) {
								if (val > res) {
									res = val;
								}
							}
							return res;
						};
						break;
					case 5:
						fn = integers -> {
							if (integers.get(0) > integers.get(1)) {
								return 1L;
							}
							return 0L;
						};
						break;
					case 6:
						fn = integers -> {
							if (integers.get(0) < integers.get(1)) {
								return 1L;
							}
							return 0L;
						};
						break;
					case 7:
						fn = integers -> {
							if (Objects.equals(integers.get(0), integers.get(1))) {
								return 1L;
							}
							return 0L;
						};
						break;

					default:
						throw new IllegalStateException("Unexpected value: " + type);
				}
				return processTypeZero(fn);
			} else if (lengthType == '1') {
				Function<List<Long>, Long> fn;
				switch (type) {
					case 0:
						fn = integers -> {
							long res = 0L;
							for (long val : integers) {
								res += val;
							}
							return res;
						};
						break;
					case 1:
						fn = integers -> {
							long res = 1L;
							for (long val : integers) {
								res *= val;
							}
							return res;
						};
						break;
					case 2:
						fn = integers -> {
							long res = Long.MAX_VALUE;
							for (long val : integers) {
								if (val < res) {
									res = val;
								}
							}
							return res;
						};
						break;
					case 3:
						fn = integers -> {
							long res = Long.MIN_VALUE;
							for (long val : integers) {
								if (val > res) {
									res = val;
								}
							}
							return res;
						};
						break;
					case 5:
						fn = integers -> {
							if (integers.get(0) > integers.get(1)) {
								return 1L;
							}
							return 0L;
						};
						break;
					case 6:
						fn = integers -> {
							if (integers.get(0) < integers.get(1)) {
								return 1L;
							}
							return 0L;
						};
						break;
					case 7:
						fn = integers -> {
							if (Objects.equals(integers.get(0), integers.get(1))) {
								return 1L;
							}
							return 0L;
						};
						break;

					default:
						throw new IllegalStateException("Unexpected value: " + type);
				}
				return processTypeOne(fn);
			}
		}
		return -1;
	}

	private static long processLiteralValue() {
		System.out.printf("Processing literal value beginning at index %d%n", idx);
		String res = "";
		while (input.charAt(idx) != '0') {
			res = res.concat(input.substring(idx + 1, idx + 5));
			idx += 5;
		}
		res = res.concat(input.substring(idx + 1, idx + 5));
		idx += 5;

		long ans = Long.parseLong(res, 2);
		System.out.printf("Finished processing literal value returns: %d%n", ans);
		System.out.printf("Finished processing literal value ending before index %d%n", idx);
		return ans;
	}

	private static long processTypeZero(Function<List<Long>, Long> fn) {
		System.out.printf("Sorting type zero beginning at index %d%n", idx);
		int length = Integer.parseInt(input.substring(idx, idx + 15), 2);
		idx += 15;
		int terminated = idx + length;

		List<Long> subpackets = new ArrayList<>();
		while (idx < terminated) {
			subpackets.add(readPacket());
		}
		System.out.printf("Finished processing type zero returns: %d%n", fn.apply(subpackets));
		System.out.printf("Finished processing type zero ending before index %d%n", idx);
		return fn.apply(subpackets);
	}

	private static long processTypeOne(Function<List<Long>, Long> fn) {
		System.out.printf("Sorting type one beginning at index %d%n", idx);
		int numSubPackets = Integer.parseInt(input.substring(idx, idx + 11), 2);
		idx += 11;

		List<Long> subpackets = new ArrayList<>();
		while (numSubPackets > 0) {
			System.out.printf("Num. of sub-packets left %d%n", numSubPackets);
			subpackets.add(readPacket());
			numSubPackets--;
		}
		System.out.printf("Finished processing type one returns: %d%n", fn.apply(subpackets));
		System.out.printf("Finished processing type one ending before index %d%n", idx);
		return fn.apply(subpackets);
	}

	private static void part1() {
		readPacket();
	}

	private static long part2() {
		return readPacket();
	}

	private static HashMap<Character, String> hexadecimalToBinaryMap() {
		HashMap<Character, String> res = new HashMap<>();
		res.put('0', "0000");
		res.put('1', "0001");
		res.put('2', "0010");
		res.put('3', "0011");
		res.put('4', "0100");
		res.put('5', "0101");
		res.put('6', "0110");
		res.put('7', "0111");
		res.put('8', "1000");
		res.put('9', "1001");
		res.put('A', "1010");
		res.put('B', "1011");
		res.put('C', "1100");
		res.put('D', "1101");
		res.put('E', "1110");
		res.put('F', "1111");
		return res;
	}

	public static void main(String[] args) throws FileNotFoundException {
		new day16();
		readInput();
		part1();
		System.out.println("Part1: " + versionSum);

		new day16();
		readInput();
		System.out.println("Part2: " + part2());
	}

}
