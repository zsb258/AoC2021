import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class day20 {
//	private static char[][] imageToBinary(char[][] image) {
//		for (int row = 0; row < image.length; row++) {
//			for (int col = 0; col < image[0].length; col++) {
//				image[row][col] = (image[row][col]=='.') ? '0' : '1';
//			}
//		}
//		return image;
//	}


	private static char[][] addImageBorder(char[][] image, char borderChar) {
		char[][] res = new char[image.length+4][image[0].length+4];
		for (int row = 0; row < res.length; row++) {
			for (int col = 0; col < res[0].length; col++) {
				if (row == 0 || row == 1 || row == res.length-2 || row == res.length-1) {
					res[row][col] = borderChar;
					continue;
				}
				if (col == 0 || col == 1 || col == res.length-2 || col == res.length-1) {
					res[row][col] = borderChar;
					continue;
				}
				res[row][col] = image[row-2][col-2];
			}

		}
		for (char[] row : res) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println();
		return res;
	}

	private static int evaluatePixel(char[][] image, int row, int col) {
		StringBuilder res = new StringBuilder();
		for (int i = row-1; i <= row+1; i++) {
			for (int j = col - 1; j <= col + 1; j++) {
				res.append((image[i][j]=='.') ? '0' : '1');
			}
		}
		return Integer.parseInt(res.toString(), 2);
	}

	private static char[][] enhanceImage(char[][] image, String conversionString, char borderChar) {
		char[][] widenedImage = addImageBorder(image, borderChar);
		char[][] enhancedImage = new char[widenedImage.length][widenedImage[0].length];
		for (int row = 0; row < widenedImage.length; row++) {
			for (int col = 0; col < widenedImage[0].length; col++) {
				if (row == 0 || row == widenedImage.length-1 || col == 0 || col == widenedImage.length-1) {
					int borderIndex = (borderChar=='.') ? 0 : 511;
					enhancedImage[row][col] = conversionString.charAt(borderIndex);
					continue;
				}
				enhancedImage[row][col] = conversionString.charAt(evaluatePixel(widenedImage, row, col));
			}
		}
		System.out.println("Enhanced image:");
		for (char[] row : enhancedImage) {
			System.out.println(Arrays.toString(row));
		}
		System.out.println("Rows: " + enhancedImage.length + ", Cols: " + enhancedImage[0].length);
		return enhancedImage;
	}

	private static int getLightPixels(char[][] image, String conversionString, int step) {
		char[][] enhancedImage = image;
		for (int i=0; i<step; i++) {
			if (i%2 == 0) {
				enhancedImage = enhanceImage(enhancedImage, conversionString, '.');
				continue;
			}
			enhancedImage = enhanceImage(enhancedImage, conversionString, conversionString.charAt(0));
		}
		int res = 0;
		for (int row = 0; row < enhancedImage.length; row++) {
			for (int col = 0; col < enhancedImage[0].length; col++) {
				res += (enhancedImage[row][col] == '.') ? 0 : 1;
			}
		}
		return res;
	}

	public static void main (String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day20_input.txt"));
		String conversionString = sc.nextLine();
		sc.nextLine(); // skips empty line
		List<char[]> imageList = new ArrayList<>();
		while (sc.hasNextLine()) {
			char[] line = sc.nextLine().toCharArray();
			imageList.add(line);
		}

		char[][] image = new char[imageList.size()][imageList.get(0).length];
		for (int i=0; i< imageList.size(); i++) {
			image[i] = imageList.get(i);
		}

		for (char[] row : image) {
			System.out.println(Arrays.toString(row));
		}

		System.out.println("Part1: " + getLightPixels(image, conversionString, 2));
		System.out.println("Part2: " + getLightPixels(image, conversionString, 50));


	}
}
