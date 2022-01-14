import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;

public class day17 {
	static int minX, maxX, minY, maxY;

	private static class Probe {
		int x = 0, y = 0;
		int vx, vy;
		int height = -1;
		Probe (int vx, int vy) {
			this.vx = vx;
			this.vy = vy;
		}

		void step() {
			x += vx;
			y += vy;
//			System.out.println("y: " + y);
//			System.out.println("height: " + height);
//			System.out.println("============================");
			if (vx > 0) {
				vx--;
			}
			else if (vx < 0) {
				vx++;
			}
			vy--;
			if (y > height) {
				height = y;
			}
		}

		boolean withinTarget() {
			if (x >= minX && x <= maxX && y >= minY && y <= maxY) {
				 return true;
			}
			return false;
		}
	}

	private static class Velocity implements Comparable<Velocity> {
		int vx, vy;
		Velocity(int vx, int vy) {
			this.vx = vx;
			this.vy = vy;
		}

		@Override
		public int compareTo(Velocity v) {
			if (this.vx != v.vx) {
				return this.vx - v.vx;
			}
			else if (this.vy != v.vy) {
				return this.vy - v.vy;
			}

			return 0;
		}

		@Override
		public int hashCode() {
			return this.vx + this.vy;
		}

		@Override
		public boolean equals(Object o) {
			if (this == o)
				return true;
			if (o == null)
				return false;
			if (getClass() != o.getClass())
				return false;
			Velocity other = (Velocity) o;
			if (this.vx != other.vx)
				return false;
			if (this.vy != other.vy)
				return false;
			return true;
		}


	}

	private static int part1() {
		int maxHeight = -1;
		for (int vx=maxX; vx>=0; vx--) {
			for (int vy=0; vy<=-minY; vy++) {
				Probe probe = new Probe(vx, vy);
				while (probe.y >= minY) {
					probe.step();
					if (probe.withinTarget()) {
						if (probe.height > maxHeight) {
							maxHeight = probe.height;
						}
					}
				}
			}
		}
		return maxHeight;
	}

	private static int part2() {
		Set<Velocity> set = new HashSet<>();
		for (int vx=0; vx<=maxX; vx++) {
			for (int vy=minY; vy<=-minY; vy++) {
				Probe probe = new Probe(vx, vy);
				while (probe.y >= minY) {
					probe.step();
					if (probe.withinTarget()) {
						set.add(new Velocity(vx, vy));
					}
				}
			}
		}
//		for (Velocity v : set) {
//			System.out.printf("(%d, %d)%n", v.vx, v.vy);
//		}
		return set.size();
	}

	public static void main (String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day17_input.txt"));
		String[] input = sc.nextLine().substring(13).split(", ");
		sc.close();

		String[] xRange = input[0].substring(2).split("\\.\\.");
		String[] yRange = input[1].substring(2).split("\\.\\.");
		minX = Integer.parseInt(xRange[0]);
		maxX = Integer.parseInt(xRange[1]);
		minY = Integer.parseInt(yRange[0]);
		maxY = Integer.parseInt(yRange[1]);

		System.out.println("Part1: " + part1());
//		Probe probe = new Probe(6,9);
//		while (probe.y >= minY) {
//			probe.step();
//			if (probe.withinTarget()) {
//				System.out.println(probe.height);
//			}
//		}
		System.out.println("Part2: " + part2());
//		Velocity v1 = new Velocity(9, -1);
//		Velocity v2 = new Velocity(9, -1);
//		System.out.println(Objects.equals(v1, v2));
//		System.out.println(v1.compareTo(v2));
	}
}
