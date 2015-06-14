package main;

public class TestDataGeneration {

	public static void main(String[] args) {
		World world = new World(10,10);
		int nbrCorrect = 0;
		int ammount_of_tries = 100;
		int nbrFaults = 0;
		for(int i = 0; i != ammount_of_tries; i++){
			int[] right = new int[2];// = [];world.getRightLoc();
			int[] wrong = {world.senseRobotLoc().x,world.senseRobotLoc().y};
			System.out.print("Move "+i+": "+"("+right[0] + ","+right[1]+"), (direction:"+ world.getDirectionString() + ") ~= "+"("+wrong[0] + ","+wrong[1]+")" );
			if(right[0] == wrong[0] && right[1] == wrong[1]){
				++nbrCorrect;
				System.out.println(", you got lucky.");
			}else{
				System.out.println(", WRONG!");
			}
			if(wrong[0] == -1 || wrong[1] == -1)
				nbrFaults++;
			world.moveRobot();
		}
		System.out.println("This many was spot on: " + nbrCorrect + " of "+ammount_of_tries+" (nrbFaults:"+ nbrFaults + ")");
	}

}
