
public class State {

	public int[][] grid;
	public State ancestor;
	public static int LEFT  = 0,
			  RIGHT = 1,
			  UP    = 2,
			  DOWN  = 3;

	State() {
		grid = new int[5][5];
	}
	
	State(int[][] x) {
		grid = new int[5][5];

		// copy the array value
		for (int i = 0; i < 5; i++ ){
			for (int j = 0; j < 5; j++ ){
				this.grid[i][j] = x[i][j];
			}
		}

		// set no ancestor
		ancestor = null;
	}

	State applyMove(int x, int y, int xp, int yp, int move) {
		// x,y - coordinates of the piece to move
		// xp,yp - coordinates of the blocking bot
		// move - what direction the bot is moving

		int typeToMove = 0;	//1 or 2
		int[][] gridp = new int[5][5];
		State newState;

		// copy the array value
		for (int i = 0; i < 5; i++ ){
			for (int j = 0; j < 5; j++ ){
				gridp[i][j] = grid[i][j];
			}
		}

		if (grid[x][y] == 1) {
			typeToMove = 1;
		} else {
			typeToMove = 2;
		}

		if (move == LEFT) {
			gridp[x][y] = 0;
			gridp[xp][yp+1] = typeToMove;

			newState = new State(gridp);
			newState.ancestor = this;

			return newState;

		} else if (move == RIGHT) {
			gridp[x][y] = 0;
			gridp[xp][yp-1] = typeToMove;

			newState = new State(gridp);
			newState.ancestor = this;

			return newState;
		} else if (move == UP) {
			gridp[x][y] = 0;
			gridp[xp+1][yp] = typeToMove;

			newState = new State(gridp);
			newState.ancestor = this;

			return newState;
		} else if (move == DOWN) {
			gridp[x][y] = 0;
			gridp[xp-1][yp] = typeToMove;

			newState = new State(gridp);
			newState.ancestor = this;

			return newState;
		} else {
			return null;
		}

	}

	public String toString() {
		StringBuffer tFA = new StringBuffer();
		for (int i=0 ; i<5 ; ++i) {
			for (int j=0 ; j<5 ; ++j) {
				tFA.append(this.grid[i][j]);
			}
		}
		return tFA.toString();
	}

/*
	public String toS() {
		StringBuffer tFA = new StringBuffer();
		for (int i=0 ; i<5 ; ++i) {
			for (int j=0 ; j<5 ; ++j) {
				tFA.append(this.grid[i][j]);
			}
			tFA.append("\n");
		}
		return tFA.toString();
	}
*/

}

