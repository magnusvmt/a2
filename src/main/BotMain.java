package main;

import java.awt.Point;

public class BotMain {
	public static void main(String[] args) {
		boolean print = true;
		boolean debug = true;
		int xSize = 30;
		int ySize = 30;
		World w  = new World(xSize,ySize);
		Locate l = new Locate(xSize, ySize);
		int correctEstimates = 0;
		double correctSense = 0;
		double amountNothing = 0;
		double off = 0;
		int iterations = 100;
		for(int i=0;i<iterations;i++){
			Point sensorPos = w.senseRobotLoc();
			Point rightPos = w.getRightLoc();
			Point estimatePos = l.getMostLikelyLocation(sensorPos);
			if(print){
				System.out.print("Gussed: (" + estimatePos.x +","+estimatePos.y + "), Correct: (" + rightPos.x + "," + rightPos.y + ")");
				System.out.println(", Sense: ("+sensorPos.x + "," + sensorPos.y + ")");
			}
			if (rightPos.equals(estimatePos)) {
				correctEstimates++;
			}
			if (debug && rightPos.equals(sensorPos)){
				correctSense++;
			}
			if( debug && (sensorPos.x == -1) )
				amountNothing++;
			off += Math.sqrt((estimatePos.x - rightPos.x)*(estimatePos.x - rightPos.x) + (estimatePos.y - rightPos.y)*(estimatePos.y - rightPos.y));
			w.moveRobot();
		}
		double prob = ((double) (correctEstimates)) / iterations;
		System.out.println("Guessed correctly with prob: " + prob);
		System.out.println("Was off by " + (double)(off/iterations) + " in general");
		if(debug){
			double probSense = correctSense/iterations;
			System.out.println("Sensed correctly prob: " + probSense);
			double probNothing = amountNothing/iterations;
			System.out.println("Nothing prob: " + probNothing);
			
		}
		
	}

}