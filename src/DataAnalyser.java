

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
		for (int i = 0; i < arrayToTest.length; i++)
		{
			set.add(arrayToTest[i]);
		}
		
		String[] toRemove = new String[arrayToTest.length];
		randGen = new Random(System.currentTimeMillis());
		for (int i = 0; i < arrayToTest.length; i++)
		{
			toRemove[i] = arrayToTest[randGen.nextInt(arrayToTest.length)];
		}
		
		
        long startTime = System.nanoTime();
        for (int j = 0; j < arrayToTest.length; j++)
    	{
    		set.removeOne(toRemove[j]);
    	}
        long endTime = System.nanoTime();
        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
        
        System.out.println(timeTaken);
        //System.out.println("time taken removing for " + implementationType + ": " + timeTaken + " seconds ");
        //System.out.println(set.print());
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

		// Construct multiset container and the factory object to create multiset
		Map<String, RmitMultiset> hMultisets =  new HashMap<String, RmitMultiset>();
		// Factory
		try {
			switch (implementationType)
			{
				case "array":
					//System.out.println("creating array");
					set = new ArrayMultiset();
					break;
				case "bst":
					//System.out.println("creating bst");
					set = new BstMultiset();
					break;
				case "duallist":
					//System.out.println("creating dual link list");
					set = new DualLinkedListMultiset();
					break;
				case "orderedlist":
					//System.out.println("creating ordered link list");
					set = new OrderedLinkedListMultiset();
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
			}
			
			
		} catch (IllegalArgumentException e) {
				System.out.println("Arg error");
		}
		catch (IOException e) {
			System.out.println("IO error");
		}
		
		

	} // end of main()

}
