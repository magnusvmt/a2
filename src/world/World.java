package world;

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
		int[] ans = new int[2];
		if (Math.random() > 0.9) { // Nothing
			ans = new int[] { -1, -1 };
		} else {
			int xError = getChange((int) (Math.random()));
			int yError = getChange((int) (Math.random()));
			
			ans[0] = loc[0] + xError;
			ans[1] = loc[1] + yError;
			
		}
		return ans;
	}

	private int getChange(int die) {
		int change = 0;
		if (die < 0.126) {
			change = -2;
		} else if (die < 0.327) {
			change = -1;
		} else if (die < 0.478) {
			change = 0;
		} else if (die < 0.679) {
			change = 1;
		} else if (die < 0.68) {
			change = 2;
		}
		return change;
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
				System.err.println("Yeah something is wrong with the code is this happened.");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			//Om det kommer hit så har man nått en väg och då ska man alltid byta väg.
			//System.err.println("("+loc[0] + ","+loc[1]+")- "+dir );
			changeDir();
			move();
		}

	}

	private void changeDir() {
		int ans;
		do{
			ans = rand(4);
		}while(ans == dir);
		dir = ans;
	}

	/* return an int between 1 & n */
	private int rand(int n) {
		return (int) (Math.random() * n + 1);
	}
}