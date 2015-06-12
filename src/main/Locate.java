package main;

import java.awt.Point;

public class Locate {
	private int xSize, ySize, nbrOfStates;
	private double[][] transitionMatrix;
	private State[] stateVector;

	public Locate(int xSize, int ySize){
		this.xSize = xSize;
		this.ySize = ySize;
		nbrOfStates = xSize*ySize*4;
		transitionMatrix = new double[nbrOfStates][nbrOfStates];
		stateVector = new State[nbrOfStates];
		generateStateVector();
		generateTransitionMatrix();
	}

	public void generateStateVector(){
		int index = 0;
		for (int i = 0; i < xSize; i++) {
			for (int j = 0; j < ySize; j++) {
				for (int direction = 0; direction < 4; direction++) {
					State state = new State(new Point(i , j), direction);
					state.setProbability(1.0/nbrOfStates);
					stateVector[index] = state;
					index++;
				}
			}
		}
	}
	
	public void generateTransitionMatrix(){
		for (int i = 0; i < nbrOfStates; i++) {
			State from = stateVector[i];
			for (int j = 0; j < nbrOfStates; j++) {
				State to = stateVector[j];
				if (from.isPossibleNextState(to)) {
					if (from.isRunningIntoWall(xSize,ySize)) {
						transitionMatrix[i][j] = 1.0 / from
								.nbrOfClosestNeighbours(xSize,ySize);
					} else if (from.direction == to.direction) {
						transitionMatrix[i][j] = 0.7;
					} else {
						transitionMatrix[i][j] = 0.3 / (from
								.nbrOfClosestNeighbours(xSize,ySize) - 1);
					}
				} else {
					transitionMatrix[i][j] = 0;
				}
			}
		}
		//printMatrix(transitionMatrix);
	}
	
	private void printMatrix(double[][] m){
		for (int i = 0; i < nbrOfStates; i++) {
			for (int j = 0; j < nbrOfStates; j++) {
				System.out.print(m[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}
	private double[] getObservationMatrix(Point p) {
		double[] observationMatrix = new double[nbrOfStates];
		if (p.equals(State.NOTHING)) {
			for (int i = 0; i < nbrOfStates; i++) {
				observationMatrix[i] = 0.1 + (0.025*stateVector[i].nbrOfNeighboursOutsideLimits(xSize, ySize));
			}
		} else {
			for (int i = 0; i < nbrOfStates; i++) {
				State s = stateVector[i];
				int off = max(Math.abs(p.x - s.p.x), Math.abs(p.y - s.p.y));
				System.out.println("distance from: " + p + " to " + s.p + " is: " + off);
				if (p.equals(s.p)) {
					observationMatrix[i] = 0.1;
				} else if (off == 1) {
					observationMatrix[i] = 0.05;
				} else if (off == 2) {
					observationMatrix[i] = 0.025;
				} else {
					observationMatrix[i] = 0;
				}
			}
		}
		return observationMatrix;
	}	

	private static int max(int i, int j) {
		if (i >= j)
			return i;
		else
			return j;
	}

	//sid 589
	private int updateProbabilities(double[] obsMatrix) {
		double[] stateProb_next = new double[nbrOfStates];
		double alpha = 0;
		double max = 0;
		int mostLikelyState = -1;
		for (int row = 0; row < nbrOfStates; row++) {
			stateProb_next[row] = 0;
			for (int col = 0; col < nbrOfStates; col++) {
				//transitionMatrix is transposed
				stateProb_next[row] += transitionMatrix[col][row] * stateVector[col].probability * obsMatrix[row];
//				if(stateProb_next[row] == 0){
//					System.out.println(col + ": " + transitionMatrix[col][row] + " " + stateVector[col].probability + " " + obsMatrix[row]);
//				}
				//System.out.println(stateVector[col].probability);
				//System.out.println(stateProb_next[row]);
			}
			alpha += stateProb_next[row];
		}
		System.out.println(alpha);
		alpha = 1 / alpha;
		for (int i = 0; i < nbrOfStates; i++) {
			stateVector[i].setProbability(stateProb_next[i] * alpha);
			//System.out.println(stateProb_next[i]);
			if (stateProb_next[i] > max) {
				mostLikelyState = i;
				max = stateProb_next[i];
			}
		}
		if(mostLikelyState == -1){
			System.out.println("--Here it went wrong--");
//			for (int i = 0; i < nbrOfStates; i++) {
//				System.out.println(obsMatrix[i]);
//				
//			}
//			for (int i = 0; i < nbrOfStates; i++) {
//				System.out.println(stateProb_next[i]);
//				//System.out.println(stateProb_next[i]);
//				if (stateProb_next[i] > max) {
//					mostLikelyState = i;
//					max = stateProb_next[i];
//				}
//			}
			System.out.println("--End--");
		}
		return mostLikelyState;
	}

	public Point getMostLikelyLocation(Point sensorRead) {
		double[] obsMatrix = getObservationMatrix(sensorRead);
		int i = updateProbabilities(obsMatrix);
		//System.out.println(i);
		return stateVector[i].p;
	}
}
