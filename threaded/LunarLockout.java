import java.util.*;
import java.io.*;

import State;
import Queue;

public class LunarLockout extends Thread {

   static int SOLVING    = 0,
	      SOLVED     = 1,
	      UNSOLVABLE = 2;

    String puzzles[];
    Vector results;

    public LunarLockout(String label, String data[], Vector x) {

        super(label);

        puzzles = new String[data.length];
	this.results = x;
        for (int i = 0; i < data.length; i++) {
                puzzles[i] = data[i];
        }

    }

   /*
   Add all reachable configurations from a given configuration to a
   vector
    */
   public static Vector genReachable(State curState) {

      int upX, upY, downX, downY, leftX, leftY, rightX, rightY;

      State  tempState;
      int[][] grid = curState.grid;

      Vector reachable = new Vector();

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


   public static boolean isGoal(State puzzleState) {
      return (puzzleState.grid[2][2] == 2);
   }

   public static int solve(int[][] grid) {

      HashMap hash		= new HashMap();
      Stack states		= new Stack();
      State currentConfig	= new State(grid);
      Integer newInt		= new Integer(1);
      Vector list;

      int mode 		= SOLVING;


      //push current configuration onto queue
      states.push(currentConfig);
      hash.put(currentConfig.toString(), newInt);

      while(mode != SOLVED) {
         if (states.isEmpty()) {
            //unsolvable
            mode = UNSOLVABLE;
            break;
         }

         //dequeue state
         currentConfig = (State)states.pop();

         //is state goal?
         if ( isGoal(currentConfig) ) {
            mode=SOLVED;
            continue;
         }

         //if not, genReachable, add states to queue if not
         //seen before
         list = genReachable(currentConfig);
         for( Enumeration e = list.elements(); e.hasMoreElements(); ) {

            State tempState = (State)e.nextElement();

            String strGrid  = tempState.toString();

            if (hash.get(strGrid) == null) {
               hash.put(strGrid,newInt);
               states.push(tempState);
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
         //Stack moves = new Stack();
         while (currentConfig != null) {
            steps++;
            //moves.push(currentConfig);
            currentConfig = currentConfig.ancestor;
         }
         //while(!moves.isEmpty()) {
         //	currentConfig = (State)moves.pop();
         //	System.out.println(currentConfig.toS());
         //}

         return steps-1;
      }

   }	// solve


   public void run() {

      String label = getName();

      int grid[][] = new int[5][5];

      //read in the string from STDIN
      String thisLine;

      // Number of steps to solve puzzle
      int numSteps;

      //Loop across the arguments
      for (int i=0; i < puzzles.length; i++) {
		
	       thisLine = puzzles[i];

               for (int j = 0; j < 5; j++) {
                  for (int k = 0; k < 5; k++) {

                     grid[j][k] = Character.getNumericValue(
                        thisLine.charAt(j*5+k)
                        );
                  }
               }

               //System.out.print(label + ":" + thisLine);


               numSteps = solve(grid);

               if (numSteps != -1) {


		  System.out.println(label+":"+thisLine+"s"+numSteps);
		  results.add(label+":"+thisLine+"s"+numSteps);

               } else {

		  System.out.println(label+":"+thisLine+"u");
		  results.add(label+":"+thisLine+"u");

               }
               //System.out.println(thisLine);

      }	// for ends here

   }	//run

}	//LunarLockout
