package main;

import world.World;

public class Test {

	public static void main(String[] args) {
		World world = new World(5,5);
		for(int i = 0; i != 15; i++){
			int[] sup = world.getRightLoc();
			System.out.println("("+sup[0] + ","+sup[1]+")");
			world.move();
		}
	}

}
