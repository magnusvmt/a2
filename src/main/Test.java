package main;

import world.World;

public class Test {

	public static void main(String[] args) {
		World world = new World(10,10);
		int nbrCorrect = 0;
		for(int i = 0; i != 50; i++){
			int[] right = world.getRightLoc();
			int[] wrong = world.sense();
			System.out.print("Move "+i+": "+"("+right[0] + ","+right[1]+") ~= "+"("+wrong[0] + ","+wrong[1]+")" );
			if(right[0] == wrong[0] && right[1] == wrong[1]){
				++nbrCorrect;
				System.out.println(", you got lucky.");
			}else{
				System.out.println(", WRONG!");
			}
			world.move();
		}
		System.out.println("This many was spot on:" + nbrCorrect);
	}

}
