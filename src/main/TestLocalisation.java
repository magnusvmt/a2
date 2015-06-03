package main;


public class TestLocalisation {
	private static int xSize, ySize;
	public static void main(String[] args) {
		int ammount;
		if(args.length == 3){
			xSize = Integer.parseInt(args[0]);
			ySize = Integer.parseInt(args[1]);
			ammount = Integer.parseInt(args[2]);
		}else{
			xSize = 40;
			ySize = 40;
			ammount = 100;
		}
		
		World world = new World(xSize,ySize);
		
		int[][] sensed_data = generate_data(world,ammount);
		double[][] nullMatrix = nullMatrix();
		int[] robot_loc = Locate.locate(sensed_data,nullMatrix);
		
		// test
		int[] right_robot_loc = world.getRightLoc();
		int xOff = robot_loc[0] - right_robot_loc[0];
		int yOff = robot_loc[1] - right_robot_loc[1];
		double dist = Math.sqrt(xOff*xOff + yOff*yOff);
		System.out.println("Off by : "+ dist);
	}
	
	

	private static int[][] generate_data(World world, int ammount){
		int[][] positions = new int[ammount][2];
		
		//lets not have an start place thats "nothing" (-1,1)
		do{
			positions[0] = world.senseRobotLoc();
		} while(positions[0][0] == -1 || positions[0][1] == -1);
		
		for(int i = 1; i != ammount; ++i){
			positions[i] = world.senseRobotLoc();
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
