import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day8 {
	public static int part1(List<String> allOutputs){
		int res = 0;
		for (String s : allOutputs){
			int sLen = s.length();
			if (sLen==2 | sLen==3 | sLen==4 | sLen==7){
				res += 1;
			}
		}
		return res;
	}

	public static int part2(List<List<String>> signalsByLine,
							List<List<String>> outputsByLine,
							HashMap<String, Integer> translator) {
		int res = 0;
		for (int i = 0; i < signalsByLine.size(); i++) {
			List<String> signals = signalsByLine.get(i);
			HashMap<Integer, String> knownSignals = new HashMap<>();
			HashMap<Character, Character> charMappings = new HashMap<>();
			// stores mapping of wrong signals char to original signal char
			for (String signal : signals) {
				// first round of identifying known signals using length
				switch (signal.length()) {
					case 2:
						// gives signal_1
						knownSignals.put(1, signal);
						break;
					case 3:
						//gives signal_7
						knownSignals.put(7, signal);
						break;
					case 4:
						//gives signal_4
						knownSignals.put(4, signal);
						break;
					case 7:
						//gives signal_8
						knownSignals.put(8, signal);
						break;
				}
			}

			String one = knownSignals.get(1);
			String four = knownSignals.get(4);
			String seven = knownSignals.get(7);
			String eight = knownSignals.get(8);
			for (String signal : signals) {
				// second round of identifying known signals by comparing to known ones
				if (signal.length() == 6 && overlappingChars(one, signal).length == 1) {
					// signal_6 is the only 6-char-long signal that has exactly 1 overlap with signal_1
					knownSignals.put(6, signal);
				}

				if (signal.length() == 5 && overlappingChars(seven, signal).length == 3) {
					// signal_3 is the only 5-char-long signal that has exactly 3 overlaps with signal_7
					knownSignals.put(3, signal);
				}
				if (signal.length() == 6 && overlappingChars(four, signal).length == 4) {
					// signal_9 is the only 6-char-long signal that has exactly 4 overlaps with signal_4
					knownSignals.put(9, signal);
				}
			}
			charMappings.put(overlappingChars(one, knownSignals.get(6))[0], 'f');
			// signal_1 and signal_6 are overlapping only at 'f'

			charMappings.put(nonOverlappingChars(one, seven)[0], 'a');
			// signal_1 and signal_7 are non-overlapping only at 'a'
			charMappings.put(nonOverlappingChars(eight, knownSignals.get(6))[0], 'c');
			// signal_8 and signal_6 are non-overlapping only at 'c'
			charMappings.put(nonOverlappingChars(eight, knownSignals.get(9))[0], 'e');
			// signal_8 and signal_9 are non-overlapping only at 'e'

			String fourPlusSeven = combineSignals(four, seven);
			charMappings.put(nonOverlappingChars(fourPlusSeven, knownSignals.get(9))[0], 'g');
			// signal_4+7 and signal_9 are non-overlapping only at 'g'

			String E = String.valueOf(nonOverlappingChars(eight, knownSignals.get(9))[0]);
			String threePlusE = combineSignals(knownSignals.get(3), E);
			charMappings.put(nonOverlappingChars(threePlusE, eight)[0], 'b');
			// signal_3+e and signal_8 are non-overlapping only at 'b'


			for (char key : new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g'}) {
				if (!charMappings.containsKey(key)) {
					charMappings.put(key, 'd');
				}
			}


			for (String signal : signals){
				// complete all known signals
				char[] signalChars = signal.toCharArray();
//				System.out.println(signalChars);
				char[] decodedSignalChars = new char[signalChars.length];
				for (int j=0; j< signalChars.length; j++){
					decodedSignalChars[j] = charMappings.get(signalChars[j]);
				}
				String translatedSignal = sortString(String.valueOf(decodedSignalChars));
//				System.out.println(translatedSignal);
				knownSignals.putIfAbsent(translator.get(translatedSignal), signal);
			}
//			System.out.println(knownSignals.keySet());


			HashMap<String, Integer> decoder = new HashMap<>();
			for (int key : knownSignals.keySet()){
				decoder.put(sortString(knownSignals.get(key)), key);
			}


			List<String> outputs = outputsByLine.get(i);
			int k = 0;
			int N = 3;
			int outputsSum = 0;
			while (k <= N){
				int digit = decoder.get(sortString(outputs.get(k)));
				outputsSum += digit * Math.pow(10, N-k);
				k++;
			}
			res += outputsSum;
		}
		return res;
	}


	public static String sortString(String s){
		char[] temp = s.toCharArray();
		Arrays.sort(temp);
		return new String(temp);
	}

	public static char[][] overlappingAndNonOverlappingChars(String a, String b){
		char[] aArr = sortString(a).toCharArray();
		char[] bArr = sortString(b).toCharArray();
		List<Character> overlapList = new ArrayList<>();
		List<Character> nonOverlapList = new ArrayList<>();
		int i=0, j=0;
		while (i<aArr.length && j<bArr.length){
			int sign = Character.compare(aArr[i], bArr[j]);
			if (sign==0){
				overlapList.add(aArr[i]);
				i++;
				j++;
			}
			else if (sign<0){
				// aArr[i] < bArr[j]
				nonOverlapList.add(aArr[i]);
				i++;
			}
			else {
				// aArr[i] > bArr[j]
				nonOverlapList.add(bArr[j]);
				j++;
			}
		}
		for (i=i; i<aArr.length; i++){
			nonOverlapList.add(aArr[i]);
		}
		for (j=j; j<bArr.length; j++){
			nonOverlapList.add(bArr[j]);
		}

		char[] overlap = new char[overlapList.size()];
		char[] nonOverlap = new char[nonOverlapList.size()];

		for (int k=0; k<overlapList.size(); k++){
			overlap[k] = overlapList.get(k);
		}
		for (int k=0; k<nonOverlapList.size(); k++){
			nonOverlap[k] = nonOverlapList.get(k);
		}

		return new char[][]{overlap, nonOverlap};
	}

	public static char[] overlappingChars(String a, String b){
		return overlappingAndNonOverlappingChars(a, b)[0];
	}

	public static char[] nonOverlappingChars(String a, String b){
		return overlappingAndNonOverlappingChars(a, b)[1];
	}

	public static String combineSignals(String a, String b){
		char[] overlap = overlappingChars(a, b);
		char[] nonOverlap = nonOverlappingChars(a, b);
		char[] res = new char[overlap.length + nonOverlap.length];
		for (int i=0; i<overlap.length; i++){
			res[i] = overlap[i];
		}
		for (int i=0; i<nonOverlap.length; i++){
			res[i+overlap.length] = nonOverlap[i];
		}
		return sortString(String.valueOf(res));
	}

	public static HashMap<String, Integer> createTranslator(){
		HashMap<String, Integer> translator = new HashMap<>();
		translator.put("abcefg", 0);
		translator.put("cf", 1);
		translator.put("acdeg", 2);
		translator.put("acdfg", 3);
		translator.put("bcdf", 4);
		translator.put("abdfg", 5);
		translator.put("abdefg", 6);
		translator.put("acf", 7);
		translator.put("abcdefg", 8);
		translator.put("abcdfg", 9);
		return translator;
	}

	public static void main(String[] args) throws FileNotFoundException {
		File input = new File("./Inputs/day8_input.txt");
		List<List<String>> signalsByLine = new LinkedList<>();
		List<List<String>> outputsByLine = new LinkedList<>();
		List<String> allOutputs = new ArrayList<>();
		Scanner sc = new Scanner(input);
		while (sc.hasNextLine()){
			String line = sc.nextLine();
			String[] temp = line.split(" ");

			List<String> signals = Arrays.asList(temp).subList(0, temp.length - 5);
			signalsByLine.add(signals);
			List<String> outputs = Arrays.asList(temp).subList(temp.length - 4, temp.length);
			outputsByLine.add(outputs);
			allOutputs.addAll(outputs);
		}

		System.out.println("Part1: " + part1(allOutputs));

		HashMap<String, Integer> translator = createTranslator();
		System.out.println("Part2: " + part2(signalsByLine, outputsByLine, translator));

	}
}
