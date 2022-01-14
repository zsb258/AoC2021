import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class day21 {
	static class Player {
		int position;
		int score;

		Player (int startPos, int iniScore) {
			position = startPos;
			score = iniScore;
		}

		public void step (int val) {
			position = (position - 1 + val) % 10 + 1;
			score += position;
		}

		@Override
		public String toString() {
			return "Position: " + position + " Score: " + score;
		}

		@Override
		public int hashCode() {
			return this.toString().hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o == this) {
				return true;
			}
			if (getClass() != o.getClass()) {
				return false;
			}
			Player p = (Player) o;
			return (this.position==p.position && this.score==p.score);
		}
	}

	static class GameState {
		Player p1;
		Player p2;
		int next;
		GameState(Player one, Player two, int nextPlayer) {
			p1 = one;
			p2 = two;
			next = nextPlayer;
		}

		@Override
		public int hashCode() {
			return (this.p1.toString() + this.p2.toString() + next).hashCode();
		}

		@Override
		public boolean equals(Object o) {
			if (o == null) {
				return false;
			}
			if (o == this) {
				return true;
			}
			if (getClass() != o.getClass()) {
				return false;
			}
			GameState g = (GameState) o;
			return (this.p1==g.p1 && this.p2==g.p2 && this.next==g.next);
		}
	}

	static class DeterminedDice {
		int val = 0;
		int rollCount = 0;

		public int roll() {
			if (val == 100) {
				val = 0;
			}
			val++;
			rollCount++;
			return val;
		}

		public int rollThree() {
			return roll() + roll() + roll();
		}
	}

	static class DiracDice {

	}

	private static int part1(Player p1, Player p2) {
		DeterminedDice dd = new DeterminedDice();
		int playerNum = 1;
		while (true) {
			Player player = (playerNum==1) ? p1 : p2;
			player.step(dd.rollThree());
			System.out.printf("Player%d:", playerNum);
			System.out.println(player);
			playerNum = (playerNum==1)? 2 : 1;
			if (player.score >= 1000) {
				Player losingPlayer = (playerNum==1) ? p1 : p2;
				System.out.println(dd.rollCount);
				System.out.println(losingPlayer.score);
				return dd.rollCount * losingPlayer.score;
			}
		}
	}

	private static long part2(Player p1, Player p2) {
		long p1Victories = 0L;
		long p2Victories = 0L;
		HashMap<GameState, Long> unfinishedGames = new HashMap<>();
		unfinishedGames.put(new GameState(p1, p2, 1), 1L);
		HashMap<Integer, Integer> possibleRolls = new HashMap<>();
		for (int i=1; i<=3; i++) {
			for (int j=1; j<=3; j++) {
				for (int k=1; k<=3; k++) {
					int t = i + j + k;
					if (!possibleRolls.containsKey(t)) {
						possibleRolls.putIfAbsent(t, 1);
						continue;
					}
					possibleRolls.put(t, possibleRolls.get(t) + 1);
				}
			}
		}

		boolean uncompleted = true;
		while (uncompleted) {
			uncompleted = false;
			HashMap<GameState, Long> nextRounds = new HashMap<>();
			for (GameState g : unfinishedGames.keySet()) {
				for (int roll : possibleRolls.keySet()) {
					p1.step(roll);
					if (p1.score >= 21) {
						p1Victories += possibleRolls.get(roll);
						continue;
					}
					uncompleted = true;
					GameState curr = new GameState(p1, p2, 2);
					if (nextRounds.containsKey(curr)) {
						nextRounds.putIfAbsent(curr, 1L);
					} else {
						nextRounds.put(curr, unfinishedGames.get(curr) * possibleRolls.get(roll));
					}
				}
			}
		}
		return 0L;
	}

	public static void main(String[] args) throws FileNotFoundException {
		Scanner sc = new Scanner(new File("./Inputs/day21_test_input.txt"));
		Player p1 = new Player(sc.nextLine().charAt(28) - '0', 0);
		Player p2 = new Player(sc.nextLine().charAt(28) - '0', 0);
		sc.close();

		System.out.println("Part1: " + part1(p1, p2));
		System.out.println("Part2: " + part2(p1, p2));
	}
}
