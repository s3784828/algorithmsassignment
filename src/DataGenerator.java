
import java.util.Random;


/**
 * Generates collection of alphabet characters.
 * This generation code is built off code written by Jeffrey Chan.
 *
 * @author Jeffrey Chan, Tristan Macaulay s3784828
 */
public class DataGenerator
{
	
	Random mRandGen;
	
	private String alphabetLower = "abcdefghijklmnopqrswxyz";
	private String alphabet = "abcdefghijklmnopqrswxyzABCDEFGHIJKLMNOPQRSWXYZ";

	public DataGenerator(int startOfRange, int endOfRange) throws IllegalArgumentException {
		if (startOfRange < 0 || endOfRange < 0 || startOfRange > endOfRange) {
			throw new IllegalArgumentException("startOfRange or endOfRange is invalid.");
		}
		// use current time as seed
		mRandGen = new Random(System.currentTimeMillis());
	} // end of DataGenerator()
	
	public DataGenerator() throws IllegalArgumentException {
		mRandGen = new Random(System.currentTimeMillis());
	} // end of DataGenerator()
	
	
	public char getRandomLetter()
	{
		return alphabet.charAt(mRandGen.nextInt(alphabet.length()));
	}
	
	public String getRandomLetters(int sampleSize) 
	{
		String randomLetters = "";
		
		for (int i = 0; i < sampleSize; i++) {
			randomLetters +=  getRandomLetter() + " ";
		}

		return randomLetters;
	} // end of sampleWithReplacement()
	
	public String getRandomLettersOrderedDuplicates(int sampleSize)
	{
		String randomLetters = "";
		for (int i = 0; i < sampleSize; i++) {
			char randomLetter = getRandomLetter();
			int number = mRandGen.nextInt(10);
			for (int j = 0; j < number; j++)
			{
				randomLetters += randomLetter + " ";
			}
		}
		return randomLetters;
	}
	
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
	
	private String sortValuesSplit(String toSort)
	{
		String array[] = toSort.split("\\s+");
		int middle = array.length / 2;
		String middlePoint = array[middle];
		String finalResult = "";
		String[] left = new String[array.length];
		int leftIter = 0;
		String[] right = new String[array.length];
		int rightIter = 0;
		
		for (int i = 0; i < array.length; i++)
		{
			String value1 = array[i];
			if (value1.compareToIgnoreCase(middlePoint) < 0)
			{
				left[leftIter] = value1;
				leftIter += 1;
			}
			else
			{
				right[rightIter] = value1;
				rightIter += 1;
			}
		}
		
		finalResult += middlePoint + " ";
		rightIter = 0;
		leftIter -= 1;
		
		for (int i = 0; i < array.length; i++)
		{
			if (i % 2 != 0 && right[rightIter] != null)
			{
				finalResult += right[rightIter] + " ";
				rightIter += 1;
			}
			else if (leftIter >= 0)
			{
				finalResult += left[leftIter] + " ";
				leftIter -= 1;
			}
			
		}
		return finalResult;
	}



	public static void main(String[] args) {
		
		String result = "";
		DataGenerator gen = new DataGenerator();

		try {
			

			String toGenerate = args[0];
			int sampleSize = Integer.parseInt(args[1]);
			
			switch (toGenerate) {
				case "random":
					result = gen.getRandomLetters(sampleSize);
					break;
					
				case "randomlower":
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
					
				case "valuessplit":
					result = gen.getRandomLetters(sampleSize);
					result = gen.sortValuesAsc(result);
					result = gen.sortValuesSplit(result);
					break;
				case "randomdupes":
					result = gen.getRandomLettersOrderedDuplicates(sampleSize);
					break;

				
			}

			// print out samples
			System.out.println(result);

		}
		catch (Exception e) {
			System.err.println(e.getMessage());
		}
		
		

	} // end of main()
} // end of class DataGenerator
