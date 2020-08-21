

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.StringJoiner;

import implementation.RmitMultiset;

/**
 * Generates collection of integers from sampling a uniform distribution.
 *
 * @author Jeffrey Chan
 */
public class DataGenerator
{
	/** Program name. */
	protected static final String progName = "DataGenerator";

	/** Start of integer range to generate values from. */
	protected int mStartOfRange;
	/** End of integer range to generate values from. */
	protected int mEndOfRange;
	/** Random generator to use. */
	Random mRandGen;
	
	private String alphabetLower = "abcdefghijklmnopqrswxyz";
	private String alphabet = "abcdefghijklmnopqrswxyzABCDEFGHIJKLMNOPQRSWXYZ";

	/**
	 * Constructor.
	 *
	 * @param startOfRange Start of integer range to generate values.
	 * @param endOfRange End of integer range to generate values.
	 * @throws IllegalArgumentException If range of integers is inappropriate
	 */
	public DataGenerator(int startOfRange, int endOfRange) throws IllegalArgumentException {
		if (startOfRange < 0 || endOfRange < 0 || startOfRange > endOfRange) {
			throw new IllegalArgumentException("startOfRange or endOfRange is invalid.");
		}
		mStartOfRange = startOfRange;
		mEndOfRange = endOfRange;
		// use current time as seed
		mRandGen = new Random(System.currentTimeMillis());
	} // end of DataGenerator()
	
	public DataGenerator() throws IllegalArgumentException {
		mRandGen = new Random(System.currentTimeMillis());
	} // end of DataGenerator()


	/**
	 * Generate one sample, using sampling with replacement.
	 */
	public int sampleWithReplacement() {
		return mRandGen.nextInt(mEndOfRange - mStartOfRange + 1) + mStartOfRange;
	} // end of sampleWithReplacement()


	/**
	 * Generate 'sampleSize' number of samples, using sampling with replacement.
	 *
	 * @param sampleSize Number of samples to generate.
	 */
	public int[] sampleWithReplacement(int sampleSize) {
		int[] samples = new int[sampleSize];

		for (int i = 0; i < sampleSize; i++) {
			samples[i] = sampleWithReplacement();
		}

		return samples;
	} // end of sampleWithReplacement()
	
	public char getRandomLettersFromRange() 
	{
		return alphabetLower.charAt(mRandGen.nextInt(mEndOfRange - mStartOfRange + 1) + mStartOfRange);
	}
	
	public char getRandomLetters()
	{
		return alphabet.charAt(mRandGen.nextInt(alphabet.length()));
	}
	
	public String getRandomLetters(int sampleSize) 
	{
		String randomLetters = "";
		
		for (int i = 0; i < sampleSize; i++) {
			randomLetters +=  getRandomLetters() + " ";
		}

		return randomLetters;
	} // end of sampleWithReplacement()


	/**
	 * Sample without replacement, using "Algorithm R" by Jeffrey Vitter, in paper "Random sampling without a reservoir".
	 * This algorithm has O(size of range) time complexity.
	 *
	 * @param sampleSize Number of samples to generate.
	 * @throws IllegalArgumentException When sampleSize is greater than the valid integer range.
	 */
	public int[] sampleWithOutReplacement(int sampleSize) throws IllegalArgumentException {
	    int populationSize = mEndOfRange - mStartOfRange + 1;

	    if (sampleSize > populationSize) {
	    	throw new IllegalArgumentException("SampleSize cannot be greater than populationSize for sampling without replacement.");
	    }

	    int[] samples = new int[sampleSize];
	    // fill it with initial values in the range
	    for (int i = 0; i < sampleSize; i++) {
	    	samples[i] = i + mStartOfRange;
	    }

	    // replace
	    for (int j = sampleSize; j < populationSize; j++) {
	    	int t = mRandGen.nextInt(j+1);
	    	if (t < sampleSize) {
	    		samples[t] = j + mStartOfRange;
	    	}
	    }

	   return samples;
	} // end of sampleWithOutReplacement()
	
	private String sortValuesDesc(String toSort)
    {
    	String array[] = toSort.split("\\s+");
    	
    	for (int i = 0; i < array.length; i++)
    	{
    		for(int j = 0; j < array.length - 1; j++)
    		{
    			if (array[j] != null && array[j + 1] != null)
    			{
    				String value1 = array[j];
        			String value2 = array[j + 1];
        			if (value1.compareToIgnoreCase(value2) < 0)
        			{
        				String temp1 = array[j];
        				String temp2 = array[j + 1];
        				array[j] = temp2;
        				array[j + 1] = temp1;
        			}
    			}
    		}
    	}
    	
    	String sorted = "";
    	
    	for (int i = 0; i < array.length; i++)
    	{
    		sorted += array[i] + " ";
    	}
    	
    	return sorted;
    }
	
	private String sortValuesAsc(String toSort)
    {
    	String array[] = toSort.split("\\s+");
    	
    	for (int i = 0; i < array.length; i++)
    	{
    		for(int j = 0; j < array.length - 1; j++)
    		{
    			if (array[j] != null && array[j + 1] != null)
    			{
    				String value1 = array[j];
        			String value2 = array[j + 1];
        			if (value1.compareToIgnoreCase(value2) > 0)
        			{
        				String temp1 = array[j];
        				String temp2 = array[j + 1];
        				array[j] = temp2;
        				array[j + 1] = temp1;
        			}
    			}
    		}
    	}
    	
    	String sorted = "";
    	
    	for (int i = 0; i < array.length; i++)
    	{
    		sorted += array[i] + " ";
    	}
    	
    	return sorted;
    }


	/**
	 * Error message.
	 */
	public static void usage() {
		System.err.println(progName + ": <start of range to sample from> <end of range to sample from> <number of values to sample> <type of sampling>");
		System.err.println("<type of sample> = {with | without}.");
		System.exit(1);
	} // end of usage()


	/**
	 * Main method.
	 */
	public static void main(String[] args) {
		
		String result = "";
		DataGenerator gen = new DataGenerator();

		try {
			

			String toGenerate = args[0];
			int sampleSize = Integer.parseInt(args[1]);
			
			switch (toGenerate) {
				// sampling with replacement
				case "random":
					result = gen.getRandomLetters(sampleSize);
					break;
					
				case "valuesdesc":
					result = gen.getRandomLetters(sampleSize);
					result = gen.sortValuesDesc(result);
					break;
				
				case "valuesasc":
					result = gen.getRandomLetters(sampleSize);
					result = gen.sortValuesAsc(result);
					break;
				// sampling without replacement
				case "without":
					//samples = gen.sampleWithOutReplacement(sampleSize);
					break;
				
			}

			// print out samples
			System.out.println(result);
//			if (samples != null) {
//				for (int i = 0; i < samples.length; i++) {
//					System.out.print(samples[i] + " ");
//				}
//				System.out.println("");
//			}

		}
		catch (Exception e) {
			System.err.println(e.getMessage());
			usage();
		}
		
		

	} // end of main()
} // end of class DataGenerator
