package main;

import java.awt.Point;

public class State {

	public Point p;
	public static final int N = 0;
	public static final int E = 1;
	public static final int S = 2;
	public static final int W = 3;
	public static final Point NOTHING = new Point(-1,-1);
	public double probability;


	int direction;

	public State(Point p, int direction) {
		this.p = p;
		this.direction = direction;
		probability = 0;
	}

	public void setProbability(double p){
		probability = p;
	}
	
	public int nbrOfClosestNeighbours(int xSize, int ySize){
		int i = 4;
		if(p.x<= 0 || p.x>= xSize-1){
			i--;
		}
		if(p.y<= 0 || p.y>= ySize-1){
			i--;
		}
		return i;
	}
	
	public int nbrOfNeighboursOutsideLimits(int xSize, int ySize){
		int outside = 0;
		for (int x = -2; x <= 2; x++) {
			for (int y = -2; y <= 2; y++) {
				if (p.x + x < 0 || p.y + y < 0 || p.x + x >= xSize || p.y + y >= ySize) {
					outside++;
				}
			}
		}
		return outside;
	}
	

	
	public boolean isPossibleNextState(State state) {
		Point nextPoint = state.p;
		if ( (nextPoint.x == p.x && nextPoint.y == p.y-1 && state.direction == State.N)
				|| (nextPoint.x == p.x && nextPoint.y == p.y+1
				&& state.direction == State.S) || (nextPoint.x == p.x+1
				&& nextPoint.y == p.y && state.direction == State.E)
				|| (nextPoint.x == p.x-1 && nextPoint.y == p.y
				&& state.direction == State.W) ) {
			return true;
		}
		return false;
	}
	
	public boolean isRunningIntoWall(int xsize, int ysize) {
		if (direction == State.N && p.y <= 0 || direction == State.S
				&& p.y >= ysize - 1 || direction == State.W && p.x <= 0
				|| direction == State.E && p.x >= xsize - 1) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean equals(Object obj) {
		State state = (State) obj;
		return (state.p.equals(p) && state.direction == direction);
	}
	

	
	

}
