import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class day18 {
	private static class SnailFishNumber {
		SnailFishNumber parent;
		SnailFishNumber left;
		SnailFishNumber right;
		Integer val;
		boolean isLiteral;
		int nested;

		SnailFishNumber() {
			isLiteral = false;
		}

		SnailFishNumber (String line) {
			if (line.charAt(0) == '[') {
				isLiteral = false;
				Stack<Character> stack = new Stack<>();
				String s = line.substring(1, line.length()-1);
				int i = 0;
				while (i < s.length()) {
					char c = s.charAt(i);
					if (c == ',') {
						if (stack.empty()) {
							break;
						}
					}
					if (c == '[') {
						stack.push(c);
					}
					else if (c == ']') {
						stack.pop();
					}
					i++;
				}
				left = new SnailFishNumber(s.substring(0, i));
				left.setParent(this);
				right = new SnailFishNumber(s.substring(i+1));
				right.setParent(this);
			}
			else if (line.charAt(0) <= '9' && line.charAt(0) >= '0') {
				isLiteral = true;
				this.val = line.charAt(0) - '0';
				nested = -1;
			}
		}

		@Override
		public String toString() {
			if (Objects.nonNull(val)) {
				return String.valueOf(val);
			}
			if (Objects.nonNull(left) && Objects.nonNull(right)) {
				return "[" + left + "," + right + "]";
			}
			return "[,]";
		}

		public void setParent(SnailFishNumber sfn) {
			this.parent = sfn;
			if (Objects.nonNull(this.parent.left) && Objects.nonNull(this.parent.right)) {
				sfn.updateNested();
			}
		}

		private void updateNested() {
			this.nested = Math.max(left.nested, right.nested) + 1;
		}

		public static SnailFishNumber add (SnailFishNumber sfn1, SnailFishNumber sfn2) {
			SnailFishNumber res = new SnailFishNumber();
			res.left = sfn1;
			res.left.setParent(res);
			res.right = sfn2;
			res.right.setParent(res);
			return res;
		}

		public static SnailFishNumber explode (SnailFishNumber sfn, int n) {
			if (sfn.nested < n) {
				// cannot explode
				return sfn;
			}
			if (sfn.nested == 0) {
				// contains 2 literal values
				int leftVal = sfn.left.val;
				int rightVal = sfn.right.val;
			}
			SnailFishNumber leftNum = sfn.left;
			SnailFishNumber rightNum = sfn.right;
			if (!leftNum.isLiteral && leftNum.nested == n-1) {
				return explode(leftNum, n-1);
			}
			if (!rightNum.isLiteral && rightNum.nested == n-1) {
				return explode(rightNum, n-1);
			}

			return new SnailFishNumber();
		}

	}

	public static void main (String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day18_test_input.txt"));
		List<SnailFishNumber> numberList = new ArrayList<>();
		while (sc.hasNextLine()) {
			SnailFishNumber number = new SnailFishNumber(sc.nextLine());
			numberList.add(number);
		}
		sc.close();

		SnailFishNumber res = SnailFishNumber.add(numberList.get(0), numberList.get(1));
		res = SnailFishNumber.add(res, numberList.get(2));
//		for (int i=2; i<numberList.size(); i++) {
//			res = SnailFishNumber.add(res, numberList.get(i));
//		}
		System.out.println(res);
		System.out.println(res.nested);
		System.out.println(res.left);
		System.out.println(res.left.left);
		System.out.println(res.left.left.isLiteral);


	}
}
