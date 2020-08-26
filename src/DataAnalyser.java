

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.StringJoiner;

import implementation.*;

public class DataAnalyser {
	
	private static String[] arrayToTest;
	private static Random randGen;

	
	public static void runAddTests(RmitMultiset set, String implementationType)
	{
        long startTime = System.nanoTime();
        for (int j = 0; j < arrayToTest.length; j++)
    	{
    		set.add(arrayToTest[j]);
    	}
        
        long endTime = System.nanoTime();
        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
        System.out.println(timeTaken);
        //System.out.println("time taken adding for " + implementationType + ": " + timeTaken + " seconds ");
        //System.out.println(set.print());
	}
	
	public static void runRemoveTestsRandom(RmitMultiset set, String implementationType)
	{
		
		String outputResult = "";
		int[] percentRemoved = { 25, 50, 100 };
		for (int i = 0; i < arrayToTest.length; i++)
		{
			set.add(arrayToTest[i]);
		}
		
		randGen = new Random(System.currentTimeMillis());
		for (int j = 0; j < percentRemoved.length; j++)
		{
			
			String[] toRemove = new String[arrayToTest.length];
			for (int i = 0; i < toRemove.length; i++)
			{	
				if (randGen.nextInt(100) < percentRemoved[j])
				{
					toRemove[i] = arrayToTest[i];
				}
			}
			
			long startTime = System.nanoTime();
			for (int i = 0; i < toRemove.length; i++)
	    	{
				if(toRemove[i] != null) { 
					set.removeOne(toRemove[i]);
				}
	    	}
	        long endTime = System.nanoTime();
	        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
	        if (j != percentRemoved.length - 1)
	        {
	        	outputResult += timeTaken + ":"; 
	        }
	        else
	        {	
	        	outputResult += timeTaken; 
	        }
	        //System.out.println(set.print());
	        
	        //reset set
	        for(int i = 0; i < toRemove.length; i++) {
	        	if(toRemove[i] != null) { 
	        		set.add(toRemove[i]);
	        	}
	        }
	        	
		}
		System.out.println(outputResult);
	}
	
	public static void runRemoveTestsOrdered(RmitMultiset set, String implementationType)
	{
		for (int i = 0; i < arrayToTest.length; i++)
		{
			set.add(arrayToTest[i]);
		}
				
        long startTime = System.nanoTime();
        for (int j = 0; j < arrayToTest.length; j++)
    	{
    		set.removeOne(arrayToTest[j]);
    	}
        long endTime = System.nanoTime();
        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
        System.out.println(timeTaken);
        System.out.println("time taken removing for " + implementationType + ": " + timeTaken + " seconds ");
        System.out.println(set.print());
	}
	
	public static void runPrintTest(RmitMultiset set, String implementationType) {
		for (int i = 0; i < arrayToTest.length; i++)
		{
			set.add(arrayToTest[i]);
		}
				
        long startTime = System.nanoTime();
        set.print();
        long endTime = System.nanoTime();
        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
        System.out.println(timeTaken);
        //System.out.println("time taken removing for " + implementationType + ": " + timeTaken + " seconds ");
        //System.out.println(set.print());
	}
	
	public static void runIntersectTests(RmitMultiset set, RmitMultiset set2, String implementationType)
	{
		//int similar = 75;
		String outputResult = "";
		int[] similars = { 25, 50, 100 };
		randGen = new Random(System.currentTimeMillis());
		for (int j = 0; j < similars.length; j++)
		{
			for (int i = 0; i < arrayToTest.length; i++)
			{	
				if (randGen.nextInt(100) < similars[j])
				{
					set.add(arrayToTest[i]);
				}
				if (randGen.nextInt(100) < similars[j])
				{
					set2.add(arrayToTest[i]);
				}
			}
			long startTime = System.nanoTime();
	        set.intersect(set2);
	        long endTime = System.nanoTime();
	        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
	        if (j != similars.length - 1)
	        {
	        	outputResult += timeTaken + ":"; 
	        }
	        else
	        {	
	        	outputResult += timeTaken; 
	        }
	        	
		}
		System.out.println(outputResult);
//		System.out.println("similarity: " + similar);
//		System.out.println("set1: ");
//        System.out.println(set.print());
//        System.out.println("set2: ");
//        System.out.println(set2.print());
				
//        long startTime = System.nanoTime();
//        set.intersect(set2);
//        long endTime = System.nanoTime();
//        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
        
//        RmitMultiset set3 = set.intersect(set2);
//        System.out.println(set3.print());
        
        //System.out.println(timeTaken);
//        System.out.println("time taken removing for " + implementationType + ": " + timeTaken + " seconds ");
        
	}


	/**
	 * Process the operation commands coming from inReader, and updates the multiset according to the operations.
	 *
	 * @param inReader Input reader where the operation commands are coming from.
	 * @param outWriter Where to output the results of search and print related operations.
	 * @param creator Factory class to construct appropriate multiset instance.
	 * @param multisetd Map of id and multiset, used to store the multisets created and operated upon.
	 *
	 * @throws IOException If there is an exception to do with I/O.
	 */
	public static void processOperations(BufferedReader inReader, PrintWriter outWriter)
		throws IOException
	{
		String line;
		boolean bQuit = false;

		// continue reading in commands until we either receive the quit signal or there are no more input commands
		while (!bQuit && (line = inReader.readLine()) != null) {
			// tokens can be separted by one or more whitespaces
			String[] tokens = line.split("\\s+");

			arrayToTest = tokens;
			
			String printArray = "Testing: ";
			
			for (int i = 0; i < arrayToTest.length; i++)
			{
				printArray += arrayToTest[i] + " ";
			}
			
			//System.out.println(printArray);

		} // end of while
		
	} // end of processOperations()


	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {
		RmitMultiset set = null;
		RmitMultiset set2 = null;
		int similar = 0;
		// check number of command line arguments
		if (args.length != 2) {
			System.out.println("ERROR - wrong amount of arguments");
			System.out.println("<data structure> <test operation> < <input file>");
		}

		String implementationType = args[0];
		String testOperation = args[1];
//		if (testOperation.contains(":"))
//		{
//			String[] toSplit = testOperation.split(":");
//			testOperation = toSplit[0];
//			similar = Integer.parseInt(toSplit[1]);
//		}

		String outFilename = null;
		if (args.length == 3) {
			outFilename = args[2];
		}

		// Construct multiset container and the factory object to create multiset
		Map<String, RmitMultiset> hMultisets =  new HashMap<String, RmitMultiset>();
		// Factory
		try {
			switch (implementationType)
			{
				case "array":
					//System.out.println("creating array");
					set = new ArrayMultiset();
					set2 = new BstMultiset();
					break;
				case "bst":
					//System.out.println("creating bst");
					set = new BstMultiset();
					set2 = new BstMultiset();
					break;
				case "duallist":
					//System.out.println("creating dual link list");
					set = new DualLinkedListMultiset();
					set2 = new DualLinkedListMultiset();
					break;
				case "orderedlist":
					//System.out.println("creating ordered link list");
					set = new OrderedLinkedListMultiset();
					set2 = new OrderedLinkedListMultiset();
					break;
					
				
			}
			
			

			// construct in and output streams/writers/readers, then process each operation.
			BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
			PrintWriter outWriter = new PrintWriter(System.out, true);

			if (outFilename != null) {
				outWriter = new PrintWriter(new FileWriter(outFilename), true);
			}

			// process the operations
			processOperations(inReader, outWriter);
			
			switch (testOperation)
			{
				case "add":
					runAddTests(set, implementationType);
					break;
				
				case "removerandom":
					runRemoveTestsRandom(set, implementationType);
					break;
					
				case "removeorder":
					
					runRemoveTestsOrdered(set, implementationType);
					break;
					
				case "intersect":
					runIntersectTests(set, set2, implementationType);
					break;
				
				case "print":
					runPrintTest(set, implementationType);
					break;
					
			}
			
			
		} catch (IllegalArgumentException e) {
				System.out.println("Arg error");
		}
		catch (IOException e) {
			System.out.println("IO error");
		}
		
		

	} // end of main()

}
