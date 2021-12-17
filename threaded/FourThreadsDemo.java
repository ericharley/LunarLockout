import java.util.*;
import java.io.*;

public class FourThreadsDemo {

    public static void main (String[] args) {

	String temp[]   = new String[40];
	String array1[] = new String[10];
	String array2[] = new String[10];
	String array3[] = new String[10];
	String array4[] = new String[10];

	Vector results = new Vector();

	//Loop across the arguments
	for (int i=0; i < args.length; i++) {
         //Open the file for reading
         try {
            FileReader fr =  new FileReader(args[i]);
            BufferedReader myInput = new BufferedReader(fr);

	    int j = 0;
	    String thisLine = new String();

            while ((thisLine = myInput.readLine()) != null) {
               // while loop begins here

		temp[j++] = thisLine;

            }
	    
	    for (int k = 0; k < 10; k++) {
		array1[k] = temp[k];

		array2[k] = temp[k+10];
		array3[k] = temp[k+20];
		array4[k] = temp[k+30];

	    }


         } catch (IOException e) {
            System.out.println("Error: " + e);
         }

	} //for

	
        LunarLockout t1 = new LunarLockout("3",array3,results);
	t1.start();

        LunarLockout t2 = new LunarLockout("4",array4,results);
	t2.start();

        LunarLockout t3 = new LunarLockout("1",array1,results);
	t3.start();

        LunarLockout t4 = new LunarLockout("2",array2,results);
	t4.start();

    }

}


