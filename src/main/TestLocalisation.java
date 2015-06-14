package main;

import java.awt.Point;
import java.util.ArrayList;


public class TestLocalisation {
	private static int xSize, ySize;
	private static ArrayList<Point> correctPositions;
	public static void main(String[] args) {
		int ammount;
		boolean debug = false;
		if(args.length >= 3){
			xSize = Integer.parseInt(args[0]);
			ySize = Integer.parseInt(args[1]);
			ammount = Integer.parseInt(args[2]);
		}else{
			xSize = 40;
			ySize = 40;
			ammount = 100;
		}
		if(args.length == 4){
			debug = args[3] == "-d"? false:true ;
		}
				
		int[][] sensed_data = generate_data(ammount);
		double[][] nullMatrix = nullMatrix();
		int[] robot_loc = new int[2];//Locate.locate(sensed_data,nullMatrix);
		
		// test
		int wx, wy, rx, ry;
		for(int i = 0; i != ammount*2; i+=2){
			wx = robot_loc[i];
			wy = robot_loc[i+1];
			System.out.print("Step "+(i/2)+": (" + wx + "," + wy +")" );
			if(debug){
				rx = correctPositions.get(i/2).x;
				ry = correctPositions.get(i/2).y;
				double off = Math.sqrt((rx-wx)*(rx-wx) + (ry-wy)*(ry-wy) );
				System.out.print(", correct position: (" + rx + "," + ry +"), it's off by: "+ (int)off + " (rounded down). ");
			}
			System.out.println("");
		}
		System.out.println("");
		
	}
	
	

	private static int[][] generate_data(int ammount){
		World world = new World(xSize,ySize);
		
		int[][] positions = new int[ammount][2];
		correctPositions = new ArrayList<Point>();
		int[] p = new int[2];
		//lets not have an start place thats "nothing" (-1,-1)
		do{
			positions[0][0] = world.senseRobotLoc().x;
			positions[0][1] = world.senseRobotLoc().y;
			p = new int[2];//world.getRightLoc();					
			correctPositions.add(new Point(p[0], p[1])) ;
		} while(positions[0][0] == -1 || positions[0][1] == -1);
		
		for(int i = 1; i != ammount; ++i){
			positions[i][0] = world.senseRobotLoc().x;
			positions[i][1] = world.senseRobotLoc().y;
			p = new int[2];//world.getRightLoc();					
			correctPositions.add(new Point(p[0], p[1])) ;
			world.moveRobot();
		}
		return positions;
	}
	
	private static double[][] nullMatrix(){
		double[][] matrix = new double[xSize][ySize]; 
		for(int i = 0; i != xSize; ++i){
			for(int j = 0; j != ySize; j++){
				matrix[i][j] = -1;
			}
		}
		return matrix;
	}

}
