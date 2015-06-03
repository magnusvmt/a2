package main;

public class Locate {
	private static int xSize, ySize;
	public static int[] locate(int[][] sensed_data,double[][] oldMatrix) {
		int[] first_sensed = sensed_data[0];
		xSize = oldMatrix.length;
		ySize = oldMatrix[1].length;
		double[][] next_matrix = new double[xSize][ySize];
		for(int i = 0; i != xSize; ++i){
			for(int j = 0; j != ySize; j++){
				int off = max(first_sensed[0]-i, first_sensed[1]-j);
				if(first_sensed[0] == -1){ 
					;
				} else if(oldMatrix[i][j] == -1){
					double prob;
					if(off == 0)
						prob = 0.1;
					else if(off == 1 || off == 2)
						prob = off*0.025;
					else
						prob = 0;
					next_matrix[i][j] = prob;
				}
				else if(oldMatrix[i][j] == 0){
					double prob = -1;
					if(off == 0)
						prob = 0.1;
					else if((off == 1 || off == 2))
						prob = off*0.025;
					else
						prob = 0;
					next_matrix[i][j] = prob;
				}else{
					double prob = -1;
					if(off == 0)
						prob += 0.1;
					else if((off == 1 || off == 2))
						prob += off*0.025;
					else
						prob = 0;
					next_matrix[i][j] = prob;
				}
			}
		}
		if(sensed_data.length == 1)	
			return highest_prob(next_matrix);
		
				
		int[][] other_data = new int[sensed_data.length-1][2];
		for(int i = 1; i != sensed_data.length; ++i)
			other_data[i-1] = sensed_data[i];
		
		return arrayConcat(highest_prob(next_matrix),locate(other_data, next_matrix));
	}
	private static int[] arrayConcat(int[] a, int[] b){
		int[] ans = new int[a.length+b.length];
		System.arraycopy( a, 0, ans, 0, a.length);
		System.arraycopy( b, 0, ans, a.length, b.length );
		return ans;
	}

	private static int max(int i, int j) {
		if(i >= j)
			return i;
		else
			return j;
	}
	private static int[] highest_prob(double[][] matrix){
		int[] bestChoice = new int[2];
		bestChoice[0] = -1;
		bestChoice[1] = -1;
		double bestValue = -1;
		for(int i = 0; i != xSize; ++i){
			for(int j = 0; j != ySize; j++){
				if(matrix[i][j] > bestValue){
					bestChoice[0] = i;
					bestChoice[1] = j;
					bestValue = matrix[i][j];
				}
			}
		}
		return bestChoice;
	}
}
