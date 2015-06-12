package main;

import java.awt.Point;

public class BotMain {
	public static void main(String[] args) {
		int xSize = 3;
		int ySize = 3;
		World w  = new World(xSize,ySize);
		Locate l = new Locate(xSize, ySize);
		int correctEstimates = 0;
		int iterations = 1000;
		for(int i=0;i<1000;i++){
			Point sensorPos = w.senseRobotLoc();
			Point rightPos = w.getRightLoc();
			Point estimatePos = l.getMostLikelyLocation(sensorPos);
			System.out.println("Gussed: " + estimatePos + " correct: " + rightPos);
			if (rightPos.equals(estimatePos)) {
				correctEstimates++;
			}
			w.moveRobot();
		}
		double prob = ((double) (correctEstimates)) / iterations;
		System.out.println("guessed correctly with prob: " + prob);
	}

}