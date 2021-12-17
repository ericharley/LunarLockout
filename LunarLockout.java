import java.util.Enumeration;
import java.util.HashMap;
import java.util.Vector;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;

class State {

	public int[][] grid;
	public State ancestor;
	public final static int LEFT  = 0,
			  RIGHT = 1,
			  UP    = 2,
			  DOWN  = 3;

	State() {
		grid = new int[5][5];
		ancestor = null;
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


         gridp[x][y] = 0;
         int dx = 0; int dy = 0;


	 switch(move)
	     {
	     case LEFT:
		 dx =  0 ; dy = +1 ;
		 break;
	     case RIGHT:
		 dx =  0 ; dy = -1 ;
		 break;
	     case UP:
		 dx = +1 ; dy =  0 ;
		 break;
	     case DOWN:
		 dx = -1 ; dy =  0 ;
		 break;
	     default:
		 System.out.println("Nope, nope nope");
		 break;
	     }

         gridp[xp+dx][yp+dy] = typeToMove;
         newState = new State(gridp);
         newState.ancestor = this;

         return newState;
	 
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
    
}


public class LunarLockout {
    
    static int SOLVING    = 0,
	SOLVED     = 1,
	UNSOLVABLE = 2;
    
    public static boolean isGoal(State puzzleState) {
	return (puzzleState.grid[2][2] == 2);
    }

    /*
      Add all reachable configurations from a given configuration to a
      vector
    */
    public static Vector<State> genReachable(State curState) {
	
	int upX, upY, downX, downY, leftX, leftY, rightX, rightY;
	
	State  tempState;
	int[][] grid = curState.grid;
	
	Vector<State> reachable = new Vector<State>();
	
	for (int i = 0; i < 5; i++) {
	    for (int j = 0; j < 5; j++) {
		
		upX	= 6;	upY	= 6;
		
		downX	= 6;	downY	= 6;
		
		leftX	= 6;	leftY	= 6;
		
		rightX	= 6;	rightY	= 6;
		

		if (grid[i][j] != 0) {
		    
		    // Can I move the piece ___?
		    
		    //up
		    for (int k = i-1; k >= 0; k--) {
			if (grid[k][j] != 0) {
			    upX = k;
			    upY = j;
			    break;
			}
		    }
		    //down
		    for (int k = i+1; k < 5; k++) {
			if (grid[k][j] != 0) {
			    downX = k;
			    downY = j;
			    break;
			}
		    }
		    //left
		    for (int k = j-1; k >= 0; k--) {
			if (grid[i][k] != 0) {
			    leftX = i;
			    leftY = k;
			    break;
			}
		    }
		    //right
		    for (int k = j+1; k < 5; k++) {
			if (grid[i][k] != 0) {
			    rightX = i;
			    rightY = k;
			    break;
			}
		    }
		    
		    // check to see if the robots are only one square away
		    // from the bot in question, if no then add move
		    if (rightX !=6) {
			if ((rightY - j) != 1) {
			    //right can be added
			    tempState = curState.applyMove(i,j,rightX,rightY,State.RIGHT);
			    tempState.ancestor = curState;
			    reachable.addElement(tempState);
			}
		    }
		    if (leftX !=6) {
			if ((j - leftY) != 1) {
			    //left can be added
			    tempState = curState.applyMove(i,j,leftX,leftY,State.LEFT);
			    tempState.ancestor = curState;
			    reachable.addElement(tempState);
			}
		    }
		    if (upX !=6) {
			if ((upX - i) != 1) {
			    //up can be added
			    tempState = curState.applyMove(i,j,upX,upY,State.UP);
			    tempState.ancestor = curState;
			    reachable.addElement(tempState);
			}
		    }
		    if (downX !=6) {
			if ((i - downX) != 1) {
			    //down can be added
			    tempState = curState.applyMove(i,j,downX,downY,State.DOWN);
			    tempState.ancestor = curState;
			    reachable.addElement(tempState);
			}
		    }
		    
		}	// for
	    }
	}	// for
	

	return reachable;
	
    }	// genReachable


    public static int solve(int[][] grid) {
	
	HashMap<String, Integer> hash = new HashMap<String, Integer>();
	Queue<State> states	      = new Queue<State>();
	State currentConfig	      = new State(grid);
	Integer newInt		      = new Integer(1);
	Vector<State> list;
	
	int mode = SOLVING;


      //push current configuration onto queue
      states.enqueue(currentConfig);
      hash.put(currentConfig.toString(), newInt);

      while(mode != SOLVED) {
         if (states.isEmpty()) {
            //unsolvable
            mode = UNSOLVABLE;
            break;
         }

         //dequeue state
         currentConfig = states.dequeue();

         //is state goal?
         if ( isGoal(currentConfig) ) {
            mode=SOLVED;
            continue;
         }

         //if not, genReachable, add states to queue if not
         //seen before
         list = genReachable(currentConfig);
         for( State tempState : list ) {

            String strGrid  = tempState.toString();

            if (hash.get(strGrid) == null) {
               hash.put(strGrid,newInt);
               states.enqueue(tempState);
            } else {
               // State already entered in queue.
               // forget about it, eh?
            }

         }	// for

      }	// while

      // if we get here, then we're solvable, maybe
      if (mode == UNSOLVABLE) {
         return -1;
      } else {
         int steps = 0;
         while (currentConfig != null) {
            steps++;
            currentConfig = currentConfig.ancestor;
         }

         return (steps - 1);
      }

   }	// solve


   public static void main(String args[]) {

       int grid[][] = new int[5][5];
       
       //read in the string from STDIN
       String thisLine;
       
       // Number of steps to solve puzzle
       int numSteps;
       
       //Loop across the arguments
       for (int i=0; i < args.length; i++) {
	   try {
	       FileReader fr =  new FileReader(args[i]);
	       BufferedReader myInput = new BufferedReader(fr);
	       
	       while ((thisLine = myInput.readLine()) != null) {
		   
		   for (int j = 0; j < 5; j++) {
		       for (int k = 0; k < 5; k++) {
			   
			   grid[j][k] = Character.getNumericValue(
								  thisLine.charAt(j*5+k)
								  );
		       }
		   }
		   
		   System.out.print(thisLine);
		   
		   numSteps = solve(grid);
		   if (numSteps != -1) {
		       System.out.println("s" +numSteps);
		   } else {
		       System.out.println("u");
		   }
		   
	       }	// while
	       
	   } catch (IOException e) {
	       System.out.println("Error: " + e);
	   }
	   
       }	// for
       
   }	//main
    
}	//LunarLockout
