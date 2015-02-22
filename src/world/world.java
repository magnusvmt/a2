package world;

public class world {
	boolean[][] squares;
	int[] loc;
	/*
	 * Probably dont need this mess with a map n shit, but fuck you I want it now.
	 */
	public world(int n, int m){
		squares = new boolean[n][m];
		loc = new int[2];
		int randI = (int)(Math.random() * n + 1);
		int randJ = (int)(Math.random() * m + 1);
		for(int i = 0; i != n; i++){
			for(int j = 0; j != m; j++){
				squares[i][j] = (randI == i && randJ == j);
				if(randI == i && randJ == j){
					loc[0] = i;
					loc[1] = j;
				}
			}
		}
	}
	/*
	Return a pair of ints; the coordinates that the sensor 'reads'
	Also not implemented yet.
	*/
	public int[] sense(){
		int[] ans = new int[2];
		if( (int)(Math.random()) > 0.9 ){ //Nothing
			ans = new int[] {-1,-1};
		}else{
			int xChange = getChange((int)(Math.random()));
			int yChange = getChange((int)(Math.random()));
	
			ans[0] = loc[0]+xChange;
			ans[0] = loc[0]+yChange;	
		}
		return ans;
	}
	private int getChange(int die){
		int change = -1;
		if(die < 0.126){
			change = -2;
		}else if(die < 0.327){
			change = -1;
		}else if(die < 0.478){
			change = 0;
		}else if(die < 0.679){
			change = 1;
		}else if(die < 0.68){
			change = 2;
		}
		return change;
	}
	
	public int[] getRightLoc(){
		return loc;
	}
}
