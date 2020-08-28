

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;


/**
 * Calculates averages from files of data
 * 
 * @author Tristan Macaulay
 *
 */

public class AverageCalculator {
	
	public static String toCalculate[];
	public static float sum = 0;
	public static int numValues = 0;
	public static float average = 0;
	
	public static float sum1 = 0;
	public static float sum2 = 0;
	public static float sum3 = 0;
	
	public static float average1 = 0;
	public static float average2 = 0;
	public static float average3 = 0;
	
	public static void processOperations(BufferedReader inReader, PrintWriter outWriter)
			throws IOException
		{
			String line;
			boolean bQuit = false;
			
			boolean intersectCalculation = false;

			while (!bQuit && (line = inReader.readLine()) != null) {

				String[] tokens = line.split("\\s+");

				toCalculate = tokens;
				
				
				
				if (toCalculate[0].contains(":"))
				{
					intersectCalculation = true;
				}
				
				String printArray = "Testing: ";

				if (!intersectCalculation)
				{
					for (int i = 0; i < toCalculate.length; i++)
					{
						sum += Float.parseFloat(toCalculate[i]);
						numValues += 1;
					}
				}
				else
				{
					for (int i = 0; i < toCalculate.length; i++)
					{
						sum1 += Float.parseFloat(toCalculate[i].split(":")[0]);
						sum2 += Float.parseFloat(toCalculate[i].split(":")[1]);
						sum3 += Float.parseFloat(toCalculate[i].split(":")[2]);
						numValues += 1;
					}
				}
				
				
			} // end of while
			
			if (!intersectCalculation)
			{
				average = (sum / numValues);
				System.out.println("average calculated = " + average);
				System.out.println("num times calculated = " + numValues);
			}
			else
			{
				average1 = (sum1 / numValues);
				average2 = (sum2 / numValues);
				average3 = (sum3 / numValues);
				System.out.println("25% average calculated = " + average1);
				System.out.println("50% average calculated = " + average2);
				System.out.println("100% average calculated = " + average3);
				System.out.println("num times calculated = " + numValues);
			}
			
		} // end of processOperations()

	public static void main(String[] args) {

		String outFilename = null;
		if (args.length == 1) {
			outFilename = args[0];
		}
		
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		PrintWriter outWriter = new PrintWriter(System.out, true);

		if (outFilename != null) {
			try {
				outWriter = new PrintWriter(new FileWriter(outFilename), true);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			processOperations(inReader, outWriter);
		} catch (IOException e) {
			e.printStackTrace();
		}
	} // end of main()
}
