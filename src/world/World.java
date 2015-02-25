package world;

import java.awt.Point;

public class World {
	boolean[][] squares;
	int[] loc;
	int dir;

	/*
	 * Probably dont need this mess with a map n shit, but fuck you I want it
	 * now.
	 */
	public World(int n, int m) {
		squares = new boolean[n][m];
		loc = new int[2];
		int randI = rand(n);
		int randJ = rand(m);
		for (int i = 0; i != n; i++) {
			for (int j = 0; j != m; j++) {
				squares[i][j] = (randI == i && randJ == j);
				if (randI == i && randJ == j) {
					loc[0] = i;
					loc[1] = j;
				}
			}
		}
		dir = rand(4);
	}

	/*
	 * Return a pair of ints; the coordinates that the sensor 'reads' Also not
	 * implemented yet.
	 */
	public int[] sense() {
		int x = loc[0];
		int y = loc[1];
		Point[] L_s = { new Point(x - 1, y - 1), new Point(x - 1, y),
				new Point(x - 1, y + 1), new Point(x, y - 1),
				new Point(x, y + 1), new Point(x + 1, y - 1),
				new Point(x + 1, y), new Point(x + 1, y + 1) };
		Point[] L_s2 = { new Point(x - 2, y - 2), new Point(x - 2, y - 1),
				new Point(x - 2, y), new Point(x - 2, y + 1),
				new Point(x - 2, y + 2), new Point(x - 1, y - 2),
				new Point(x - 1, y + 2), new Point(x, y - 2),
				new Point(x, y + 2), new Point(x + 1, y - 2),
				new Point(x + 1, y + 2), new Point(x + 2, y - 2),
				new Point(x + 2, y - 1), new Point(x + 2, y),
				new Point(x + 2, y + 1), new Point(x + 2, y + 2) };
		int die = rand(10);
		if(die <= 1){
			return loc;
		}else if(die <=  5){
			Point p = L_s[rand(L_s.length)-1];
			return new int[] {p.x, p.y};
		}else if(die <=  9){
			Point p = L_s2[rand(L_s2.length)-1];
			return new int[] {p.x, p.y};
		}
		return new int[] {-1, -1};
	}

	public int[] getRightLoc() {
		return loc;
	}

	public void move() {
		try {
			switch (dir) {
			case 1: // east
				if (Math.random() >= 0.7) {
					squares[loc[0] + 1][loc[1]] = true;
					squares[loc[0]][loc[1]] = false;
					loc[0] += 1;
				} else {
					changeDir();

					move();
				}
				break;
			case 2: // north
				if (Math.random() >= 0.7) {
					squares[loc[0]][loc[1] + 1] = true;
					squares[loc[0]][loc[1]] = false;
					loc[1] += 1;
				} else {
					changeDir();
					move();
				}
				break;
			case 3: // west
				if (Math.random() >= 0.7) {
					squares[loc[0] - 1][loc[1]] = true;
					squares[loc[0]][loc[1]] = false;
					loc[0] -= 1;
				} else {
					changeDir();
					move();
				}
				break;
			case 4: // south
				if (Math.random() >= 0.7) {
					squares[loc[0]][loc[1] - 1] = true;
					squares[loc[0]][loc[1]] = false;
					loc[1] -= 1;
				} else {
					changeDir();
					move();
				}
				break;
			default:
				System.err
						.println("Yeah something is wrong with the code is this happened.");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Om det kommer hit så har man nått en väg och då ska man alltid
			// byta väg.
			// System.err.println("("+loc[0] + ","+loc[1]+")- "+dir );
			changeDir();
			move();
		}

	}

	private void changeDir() {
		int ans;
		do {
			ans = rand(4);
		} while (ans == dir);
		dir = ans;
	}

	/* return an int between 1 & n */
	private int rand(int n) {
		return (int) (Math.random() * n + 1);
	}
}
