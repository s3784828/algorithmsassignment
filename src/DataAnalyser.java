import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


import implementation.*;

/**
 * Generates test results based off different data structures
 *
 * @author Tristan Macaulay s3784828
 */

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
	}
	
	public static void runIntersectTests(RmitMultiset set, RmitMultiset set2, String implementationType)
	{
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
		/*
		 * NOTE intersect tests do not reset the data structure after being tested,
		 * this means 50% calculations are actually 75% calculations. When running intersect
		 * tests more quantitive results were given when the set was not reset and as such
		 * I removed the option to reset the tests.
		 */
		
		System.out.println(outputResult);
        
	}
	
	public static void runContainsTests(RmitMultiset set, String implementationType)
	{
		String outputResult = "";
		int[] similars = { 25, 50, 100 };
		randGen = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < arrayToTest.length; i++)
		{
			set.add(arrayToTest[i]);
		}

		long startTime = System.nanoTime();
		for (int i = 0; i < arrayToTest.length; i++)
		{	
			set.contains(arrayToTest[randGen.nextInt(arrayToTest.length)]);
		}        
	    long endTime = System.nanoTime();
	    double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
	    outputResult += timeTaken;
		
		System.out.println(outputResult);
	}
	
	public static void runSearchTests(RmitMultiset set, String implementationType)
	{
		String outputResult = "";
		int[] similars = { 25, 50, 100 };
		randGen = new Random(System.currentTimeMillis());
		
		for (int i = 0; i < arrayToTest.length; i++)
		{
			set.add(arrayToTest[i]);
		}

		long startTime = System.nanoTime();
		for (int i = 0; i < arrayToTest.length; i++)
		{	
			set.search(arrayToTest[randGen.nextInt(arrayToTest.length)]);
		}        
	    long endTime = System.nanoTime();
	    double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
	    outputResult += timeTaken;
		
		System.out.println(outputResult);
	}

	public static void processOperations(BufferedReader inReader, PrintWriter outWriter)
		throws IOException
	{
		String line;
		boolean bQuit = false;


		while (!bQuit && (line = inReader.readLine()) != null) {
			String[] tokens = line.split("\\s+");

			arrayToTest = tokens;
			
			String printArray = "Testing: ";
			
			for (int i = 0; i < arrayToTest.length; i++)
			{
				printArray += arrayToTest[i] + " ";
			}

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
		
		String outFilename = null;
		if (args.length == 3) {
			outFilename = args[2];
		}

		Map<String, RmitMultiset> hMultisets =  new HashMap<String, RmitMultiset>();
		// Factory
		try {
			switch (implementationType)
			{
				case "array":
					set = new ArrayMultiset();
					set2 = new BstMultiset();
					break;
				case "bst":
					set = new BstMultiset();
					set2 = new BstMultiset();
					break;
				case "duallist":
					set = new DualLinkedListMultiset();
					set2 = new DualLinkedListMultiset();
					break;
				case "orderedlist":
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
					
				case "contains":
					runContainsTests(set, implementationType);
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
