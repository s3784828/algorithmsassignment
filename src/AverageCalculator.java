

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import implementation.ArrayMultiset;
import implementation.BstMultiset;
import implementation.DualLinkedListMultiset;
import implementation.OrderedLinkedListMultiset;
import implementation.RmitMultiset;

public class AverageCalculator {
	
	public static String toCalculate[];
	public static float sum = 0;
	public static int numValues = 0;
	public static float average = 0;
	
	public static void processOperations(BufferedReader inReader, PrintWriter outWriter)
			throws IOException
		{
			String line;
			boolean bQuit = false;

			// continue reading in commands until we either receive the quit signal or there are no more input commands
			while (!bQuit && (line = inReader.readLine()) != null) {
				// tokens can be separted by one or more whitespaces
				String[] tokens = line.split("\\s+");

				toCalculate = tokens;
				
				String printArray = "Testing: ";
				
				for (int i = 0; i < toCalculate.length; i++)
				{
					sum += Float.parseFloat(toCalculate[i]);
					numValues += 1;
				}
				
				
				
				

			} // end of while
			average = (sum / numValues);
			System.out.println("average calculated = " + average);
			System.out.println("num times calculated = " + numValues);
		} // end of processOperations()
	
	/**
	 * Main method.  Determines which implementation to test.
	 */
	public static void main(String[] args) {


		String outFilename = null;
		if (args.length == 1) {
			outFilename = args[0];
		}


		// construct in and output streams/writers/readers, then process each operation.
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter outWriter = new PrintWriter(System.out, true);

		if (outFilename != null) {
			try {
				outWriter = new PrintWriter(new FileWriter(outFilename), true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// process the operations
		try {
			processOperations(inReader, outWriter);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // end of main()
}
