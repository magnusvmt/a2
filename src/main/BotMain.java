package main;

import java.awt.Point;

public class BotMain {
	public static void main(String[] args) {
		boolean print = false;
		boolean debug = true;
		int xSize = 30;
		int ySize = 30;
		World w  = new World(xSize,ySize);
		Locate l = new Locate(xSize, ySize);
		int correctEstimates = 0;
		double correctSense = 0;
		double amountNothing = 0;
		double offGuess = 0;
		double offSense = 0;
		int iterations = 100;
		for(int i=0;i<iterations;i++){
			Point sensorPos = w.senseRobotLoc();
			Point rightPos = w.getRightLoc();
			Point estimatePos = l.getMostLikelyLocation(sensorPos);
			if(print){
				System.out.print("Gussed: (" + estimatePos.x +","+estimatePos.y + "), Correct: (" + rightPos.x + "," + rightPos.y + ")");
				System.out.println(", Sense: ("+sensorPos.x + "," + sensorPos.y + ")");
			}
			if (rightPos.equals(estimatePos) && (iterations*0.9) <= i) {
				correctEstimates++;
			}
			if (debug && rightPos.equals(sensorPos)){
				correctSense++;
			}
			if( debug && (sensorPos.x == -1) )
				amountNothing++;
			offGuess += Math.sqrt((estimatePos.x - rightPos.x)*(estimatePos.x - rightPos.x) + (estimatePos.y - rightPos.y)*(estimatePos.y - rightPos.y));
			if(sensorPos.x != -1)
				offSense += Math.sqrt((sensorPos.x - rightPos.x)*(sensorPos.x - rightPos.x) + (sensorPos.y - rightPos.y)*(sensorPos.y - rightPos.y));
			w.moveRobot();
		}
		System.out.println("Guess stats:");
		double prob = ((double) (correctEstimates)) / (iterations*0.1);
		System.out.println("\t Guess correctly prob: " + prob);
		System.out.println("\t Guess generaly off by " + (double)(offGuess/iterations));
		if(debug){
			double temp;
			System.out.println("Sense stats:");
			temp = correctSense/iterations;
			System.out.println("\t Sensed correctly prob: " + temp);
			temp = offSense/(iterations-amountNothing);
			System.out.println("\t Sense generaly off by " + temp + " (not counting 'Nothing')");
			temp = amountNothing/iterations;
			System.out.println("\t Nothing prob: " + temp);
			
			
		}
		
	}

}