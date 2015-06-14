package main;

import java.awt.Point;

public class BotMain {
	public static double[] go(int xSize, int ySize, int iterations) {
		double[] ans = new double[2];
		boolean print = true;
		boolean debug = true;
		boolean printStats = true;
		World w  = new World(xSize,ySize);
		Locate l = new Locate(xSize, ySize);
		int correctEstimates = 0;
		double correctSense = 0;
		double amountNothing = 0;
		double offGuess = 0;
		double offSense = 0;
		double last = 0.9;
		double correctGuessLast = 0;
		for(int i=0;i<iterations;i++){
			Point sensorPos = w.senseRobotLoc();
			Point rightPos = w.getRightLoc();
			Point estimatePos = l.getMostLikelyLocation(sensorPos);
			if(print){
				System.out.print("Estimate: (" + estimatePos.x +","+estimatePos.y + "), Actuall: (" + rightPos.x + "," + rightPos.y + ")");
				System.out.println(", Sense: ("+sensorPos.x + "," + sensorPos.y + ")");
			}
			if (rightPos.equals(estimatePos)) {
				correctEstimates++;
			}
			if (rightPos.equals(estimatePos) && (iterations*last) <= i) {
				correctGuessLast++;
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
		double guessProb = ((double) (correctEstimates)) / iterations;
		double guessLastProb = ((double) (correctGuessLast)) / (iterations*(1-last));
		ans[0] = guessProb;
		ans[1] =  guessLastProb;
		System.out.println();
		if(printStats){
			System.out.println("Guess stats:");
			System.out.println("\t Guess correctly prob: " + guessProb);
			System.out.println("\t Guess(last 10%) correctly prob: " + guessLastProb);
			System.out.println("\t Guess generaly off by " + (double)(offGuess/iterations));
		}
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
		return ans;
	}
	public static void main(String[] args){
		int tries = 1;
		double totFull = 0;
		double totLast = 0;
		int iterations = 100;
		int xSize = 30;
		int ySize = 30;
		if(args.length == 3){
			xSize = Integer.parseInt(args[0]);
			ySize = Integer.parseInt(args[1]);
			iterations = Integer.parseInt(args[2]);
		}
		
		for(int i = 0; i != tries; ++i){
			double[] ans = go(xSize, ySize, iterations);
			totFull += ans[0];
			totLast += ans[1];
		}
		totFull = totFull / tries;
		totLast = totLast / tries;
		//System.out.println("totFull: " + totFull + ", totLast:" + totLast);
		
	}
}