import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class day22 {
	private static class Operation {
		boolean toTurnOn;
		int xMin;
		int xMax;
		int yMin;
		int yMax;
		int zMin;
		int zMax;

		Operation (boolean toTurnOn, int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
			this.toTurnOn = toTurnOn;
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			this.zMin = zMin;
			this.zMax = zMax;
		}

		@Override
		public String toString() {
			return String.format("%s %d to %d, %d to %d, %d to %d",
					(toTurnOn ? "on" : "off"), xMin, xMax, yMin, yMax, zMin, zMax);
		}
	}

	private static int part1(List<Operation> operations) {
		int[][][] cube = new int[101][101][101];
		for (Operation op : operations) {
			for (int x = Math.max(op.xMin + 50, 0); x <= Math.min(op.xMax + 50, 100); x++) {
				for (int y = Math.max(op.yMin + 50, 0); y <= Math.min(op.yMax + 50, 100); y++) {
					for (int z = Math.max(op.zMin + 50, 0); z <= Math.min(op.zMax + 50, 100); z++) {
						cube[x][y][z] = op.toTurnOn ? 1 : 0;
					}
				}
			}
		}
		int res = 0;
		for (int i = 0; i < 101; i++) {
			for (int j = 0; j < 101; j++) {
				for (int k = 0; k < 101; k++) {
					if (cube[i][j][k] == 1) {
						res += 1;
					}
				}
			}
		}
		return res;
	}

	private static class Cuboid {
		int xMin;
		int xMax;
		int yMin;
		int yMax;
		int zMin;
		int zMax;
		List<Cuboid> emptyVolumes = new ArrayList<>();

		Cuboid (int xMin, int xMax, int yMin, int yMax, int zMin, int zMax) {
			this.xMin = xMin;
			this.xMax = xMax;
			this.yMin = yMin;
			this.yMax = yMax;
			this.zMin = zMin;
			this.zMax = zMax;
		}

		Cuboid (Operation op) {
			this.xMin = op.xMin;
			this.xMax = op.xMax;
			this.yMin = op.yMin;
			this.yMax = op.yMax;
			this.zMin = op.zMin;
			this.zMax = op.zMax;
		}

		static Cuboid intersection(Cuboid c1, Cuboid c2) {
			int[] xOverlap = Cuboid.axisOverlap(c1.xMin, c1.xMax, c2.xMin, c2.xMax);
			int[] yOverlap = Cuboid.axisOverlap(c1.yMin, c1.yMax, c2.yMin, c2.yMax);
			int[] zOverlap = Cuboid.axisOverlap(c1.zMin, c1.zMax, c2.zMin, c2.zMax);
			if (xOverlap.length==2 &&
					xOverlap.length== yOverlap.length && xOverlap.length==zOverlap.length) {
				// overlap in all 3 axes
				return new Cuboid(xOverlap[0], xOverlap[1],
						yOverlap[0], yOverlap[1],
						zOverlap[0], zOverlap[1]);
			}
			return null;
		}

		static int[] axisOverlap(int min1, int max1, int min2, int max2) {
			// returns {minIndex, maxIndex} of overlapping segment
			if (max1 >= max2 && min1 <= min2) {
				// segment 1 totally overlaps segment 2
				// return ends of smaller segment
				return new int[] {min2, max2};
			}
			if (max2 >= max1 && min2 <= min1) {
				// segment 1 totally overlaps segment 2
				// return ends of smaller segment
				return new int[] {min1, max1};
			}
			if (min1 < min2) {
				if (max1 < min2) {
					// no overlap
					return new int[] {};
				}
				if (max1 > min2) {
					// partial overlap
					return new int[] {min2, max1};
				}
				if (max1 == min2) {
					// segments touch at the end
					return new int[] {max1, max1};
				}
			}
			if (min2 < min1) {
				if (max2 < min1) {
					// no overlap
					return new int[] {};
				}
				if (max2 > min1) {
					// partial overlap
					return new int[] {min1, max2};
				}
				if (max2 == min1) {
					// segments touch at the end
					return new int[] {max2, max2};
				}
			}

			return new int[] {};
		}

		long getVolume() {
			long emptyVol = 0L;
			for (Cuboid c : this.emptyVolumes) {
				emptyVol += c.getVolume();
			}
			return (long) (xMax - xMin + 1) * (yMax-yMin+1) * (zMax-zMin+1) - emptyVol;
		}

		void removeIntersection (Cuboid toRemove) {
			Cuboid intersection = Cuboid.intersection(this, toRemove);
			if (intersection == null) {
				return;
			}

			for (Cuboid vacuum : emptyVolumes) {
				Cuboid deepIntersection = Cuboid.intersection(vacuum, intersection);
				if (deepIntersection == null) {
					continue;
				}
				vacuum.removeIntersection(intersection);
			}
			this.emptyVolumes.add(toRemove);
		}
	}

	private static long part2(List<Operation> operations) {
		// keep a list of cuboids operated
		// for each new operation, find intersection with each cuboid in list and deal accordingly

		List<Cuboid> onCuboids = new ArrayList<>();
		for (Operation op : operations) {
			Cuboid curr = new Cuboid(op);
			for (Cuboid c : onCuboids) {
				Cuboid intersection = Cuboid.intersection(c, curr);
				if (intersection==null) {
					continue;
				}
				c.removeIntersection(intersection);
			}
			if (op.toTurnOn) {
				onCuboids.add(curr);
			}

		}
		long res = 0L;
		for (Cuboid c : onCuboids) {
			res += c.getVolume();
		}
		return res;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day22_test_input.txt"));
		List<Operation> operations = new ArrayList<>();
		while (sc.hasNextLine()) {
			String[] line = sc.nextLine().split(" ");
			boolean toTurnOn = line[0].equals("on");
			String[] ranges = line[1].split(",");
			String[] xRange = ranges[0].split("\\.\\.");
			int xMin = Integer.parseInt(xRange[0].substring(2));
			int xMax = Integer.parseInt(xRange[1]);
			String[] yRange = ranges[1].split("\\.\\.");
			int yMin = Integer.parseInt(yRange[0].substring(2));
			int yMax = Integer.parseInt(yRange[1]);
			String[] zRange = ranges[2].split("\\.\\.");
			int zMin = Integer.parseInt(zRange[0].substring(2));
			int zMax = Integer.parseInt(zRange[1]);
			operations.add(new Operation(toTurnOn, xMin, xMax, yMin, yMax, zMin, zMax));
		}
		sc.close();
		for (Operation op : operations) {
			System.out.println(op);
		}

		System.out.println("Part1: " + part1(operations));
		System.out.println("Part2: " + part2(operations));
	}
}
