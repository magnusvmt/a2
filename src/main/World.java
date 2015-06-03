package main;

import java.awt.Point;

public class World {
	boolean[][] map_of_walls;
	int[] robot_loc;
	int direction;
	//Map with walls surounding it, the robot is place in a random spot in it;
	public World(int n, int m){
		map_of_walls = new boolean[n][m]; //true if wall, false if not
		robot_loc = new int[2];
		for(int i = 0; i != n; ++i){
			for(int j = 0; j != m; ++j){
				if(i == 0 || j == 0 || j == (m-1) || i == (n-1))
					map_of_walls[i][j] = true;
				else
					map_of_walls[i][j] = false;
			}
		}
		do{
			robot_loc[0] = rand(n-1);
			robot_loc[1] = rand(m-1);
		} while(map_of_walls[robot_loc[0]][robot_loc[1]]);
		direction = rand(4);
	}
	/* return an int between 1 & n */
	private int rand(int n) {
		return (int) (Math.random() * n + 1);
	}
	// for testing purposes;
	public int[] getRightLoc() {
		return robot_loc;
	}
	// for testing purposes;
	public int getDirection(){
		return direction;
	}
	public String getDirectionString(){
		switch (direction) {
		case 1:
			return "east";
		case 2:
			return "north";
		case 3:
			return "west";
		case 4:
			return "south";
		default:
			System.err
			.println("Yeah something is wrong with the code is this happened.");
		}
		return "err";
	}
	public int[] senseRobotLoc() {
		int x = robot_loc[0];
		int y = robot_loc[1];
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
		Point p = L_s[rand(L_s.length)-1];
		Point p2 = L_s2[rand(L_s2.length)-1];
		try {
		if(die <= 1){
			return robot_loc;
		}else if(die <=  5 && !map_of_walls[p.x][p.y]){
			return new int[] {p.x, p.y};
		}else if(die <=  9 && !map_of_walls[p2.x][p2.y]){
			return new int[] {p2.x, p2.y};
		}
		} catch(ArrayIndexOutOfBoundsException e){
			return senseRobotLoc();
		}
		return new int[] {-1, -1};
	}
	public void moveRobot() {
		try {
			boolean foundMove = false;
			while(!foundMove){
				switch (direction) {
				case 1: // east
					if(shouldIMove(!map_of_walls[robot_loc[0]][robot_loc[1] + 1])){
						robot_loc[1] += 1;
						foundMove = true;
					} else {
						changeDir();
					}
					
					break;
				case 2: // north
					if(shouldIMove(!map_of_walls[robot_loc[0] + 1][robot_loc[1]])){
						robot_loc[0] += 1;
						foundMove = true;
					} else {
						changeDir();
					}
					break;
				case 3: // west
					if(shouldIMove(!map_of_walls[robot_loc[0]][robot_loc[1] - 1])){
						robot_loc[1] -= 1;
						foundMove = true;
					} else {
						changeDir();
					}
					break;
				case 4: // south
					if(shouldIMove(!map_of_walls[robot_loc[0] - 1][robot_loc[1]])){
						robot_loc[0] -= 1;
						foundMove = true;
					} else {
						changeDir();
					}
					break;
				default:
					System.err
							.println("Yeah something is wrong with the code is this happened.");
				}
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			// Om det kommer hit så har man nått en väg och då ska man alltid
			// byta väg.
			// System.err.println("("+loc[0] + ","+loc[1]+")- "+dir );
			changeDir();
			moveRobot();
		}
	}
	private void changeDir() {
		int ans;
		do {
			ans = rand(4);
		} while (ans == direction);
		direction = ans;
	}
	private boolean shouldIMove(boolean cond){
		double rand = Math.random();
		return rand >= 0.7 && cond;		
	}
}
