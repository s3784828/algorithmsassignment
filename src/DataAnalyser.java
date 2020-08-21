

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.StringJoiner;

import implementation.*;

public class DataAnalyser {
	
	private static String[] arrayToTest;

	
	public static void runAddTests(RmitMultiset set, int testAmount, String implementationType)
	{
        long startTime = System.nanoTime();
        for (int i = 0; i < testAmount; i++)
        {
        	for (int j = 0; j < arrayToTest.length; j++)
        	{
        		set.add(arrayToTest[j]);
        	}
        	
        }
//        for (int j = 0; j < arrayToTest.length; j++)
//    	{
//    		set.add(arrayToTest[j]);
//    	}
        long endTime = System.nanoTime();
        double timeTaken = ((double)(endTime - startTime)) / Math.pow(10, 9);
        System.out.println("time taken adding for " + implementationType + " running " + testAmount + " times = " + timeTaken + " sec");
        System.out.println("average time = " + timeTaken / testAmount);
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
			
			System.out.println(printArray);

		} // end of while

	} // end of processOperations()


	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {
		RmitMultiset set = null;
		// check number of command line arguments
		if (args.length != 3) {
			System.out.println("ERROR - wrong amount of arguments");
			System.out.println("<program name> <data structure> <test operation> <number of tests> < <input file>");
		}

		String implementationType = args[0];
		String testOperation = args[1];
		int testAmount = Integer.parseInt(args[2]);

		String outFilename = null;
		if (args.length == 4) {
			outFilename = args[4];
		}
		
		String type = args[0];


		// Construct multiset container and the factory object to create multiset
		Map<String, RmitMultiset> hMultisets =  new HashMap<String, RmitMultiset>();
		// Factory
		try {
			switch (implementationType)
			{
				case "array":
					System.out.println("creating array");
					set = new ArrayMultiset();
					break;
				case "bst":
					System.out.println("creating bst");
					set = new BstMultiset();
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
					runAddTests(set, testAmount, implementationType);
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
