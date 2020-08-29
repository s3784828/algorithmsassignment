package implementation;

import java.util.List;

/**
 * Array implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class ArrayMultiset extends RmitMultiset
{
	protected String array[];
	protected int arrayLength;
	protected int instanceCount;
	protected static final int initialArraySize = 100000;
	
	public ArrayMultiset()
	{
		array = new String[initialArraySize];
		arrayLength = 0;
	}
	
	/**
	 * Array needs to iterate previous values before being able to
	 * add a new one, this is to ensure the added value is not a duplicate.
	 */

    @Override
	public void add(String elem) {
		
		boolean alreadyExists = false;
		for (int i = 0; i < arrayLength; i++)
		{
			
			if (!alreadyExists && array[i] != null && array[i].split(":")[0].equals(elem))
			{
				int newValue = Integer.parseInt(array[i].split(":")[1]) + 1;
				array[i] = array[i].split(":")[0] + ":" + newValue;
				alreadyExists = true;
			}
		}
		if (!alreadyExists)
		{
			array[arrayLength] = elem + ":" + 1;
			arrayLength += 1;
		}
    } // end of add()

    /**
	 * To search for an element the array must iterate through
	 * the entire array to find the respective value.
	 */

    @Override
	public int search(String elem) {
    	int dupCount = -1;
    	boolean searched = false;
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (!searched && array[i] != null && array[i].split(":")[0].equals(elem))
    		{
    			dupCount = Integer.parseInt(array[i].split(":")[1]);
    			searched = true;
    		}
    	}
        return dupCount;
    } // end of search()

    /**
	 * To search for a number of elements the array must search
	 * all of its values to find certain instance count
	 */

    @Override
    public List<String> searchByInstance(int instanceCount) {
    	List<String> list = new SimpleLinkList<String>();
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null 
    				&& Integer.parseInt(array[i].split(":")[1]) == instanceCount)
    		{
    			list.add(array[i].split(":")[0]);
    		}
    	}
        return list;
    } // end of searchByInstance
    
    /**
     * To find if a value is contained in an array it must search through
     * all its values until its finds a matching element.
     */

    @Override
	public boolean contains(String elem) {
        boolean contains = false;
        for (int i = 0; i < arrayLength; i++) 
        {
        	if (!contains && array[i] != null && array[i].split(":")[0].equals(elem))
        		contains = true;
        }
        return contains;
    } // end of contains()

    /**
     * If a unique value is removed it will replace the value with null, my implementation of the array
     * multiset is a fixed size and as such did not seem neccessary to resize to prevent null values. 
     * one of the advantages of the array structure is the speed in being able to remove and add values
     * rather than creating new nodes, and scuch I wanted the array multiset to take advantage of it.
     */
    
    @Override
	public void removeOne(String elem) {
    	boolean removedOne = false;
        for (int i = 0; i < arrayLength; i++)
        {
        	if (!removedOne && array[i] != null && array[i].split(":")[0].equals(elem))
        	{
        		int numInstances = Integer.parseInt(array[i].split(":")[1]);
        		if (numInstances > 1)
        		{
        			array[i] = array[i].split(":")[0] + ":" + (numInstances - 1);
        		}
        		else
        		{
        			array[i] = null;
        		}
        		removedOne = true;
        	}
        }
    } // end of removeOne()

    /**
     * Print finds the largest value first and uses search by instance
     * to add them to the link list implementation.
     */
    
    @Override
	public String print() {
        
    	String toPrint = "";
    	int largestValue = 0;
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null && Integer.parseInt(array[i].split(":")[1]) > largestValue)
    		{
    			largestValue = Integer.parseInt(array[i].split(":")[1]);
    		}
    	}
    	
    	for(int i = largestValue; i > 0; i--) {
    		SimpleLinkList<String> list = (SimpleLinkList<String>) searchByInstance(i);
    		for(String entry : list) {
    			toPrint += entry + ":" + i + "\n";
    		}
    		
    	}
    	
        return toPrint;
    } // end of OrderedPrint

    /**
     * PrintRange has a result string that iterates through the entire array
     * and adds the values if they fit into the certain bounds.
     */
    
    @Override
	public String printRange(String lower, String upper) {
        String range = "";
        
        for (int i = 0; i < arrayLength; i++)
        {
        	if (array[i] != null)
        	{
        		String value = array[i].split(":")[0];
        		
        		if (value.compareToIgnoreCase(lower) >= 0 && value.compareToIgnoreCase(upper) <= 0)
        		{
        			range += array[i] + "\n";
        		}
        	}
        }

        return range;
    } // end of printRange()

    /**
     * Union iterates through both arrays, duplicate values calculations are negated
     * by simply using the add function to work out similar values. 
     * The other RmitMultiset is turned into an array via the split function, this sort
     * of implementation is different to the type casting conducted in the other data structures.
     * Since the ArrayMultiset is an array, it did not seem unreasonable to create an array
     * out of the print and split function, additionally it meant I did have to create additional methods.
     */
    
    @Override
	public RmitMultiset union(RmitMultiset other) {
    	
    	RmitMultiset newMultiset = new ArrayMultiset();
        String[] otherValuesArray = other.print().split("\n");
 
        for (int i = 0; i < otherValuesArray.length; i++)
        {
        	int addAmount = Integer.parseInt(otherValuesArray[i].split(":")[1]);
        	String toAdd = otherValuesArray[i].split(":")[0];
        	
        	for (int j = 0; j < addAmount; j++)
        	{
        		newMultiset.add(toAdd);
        	}
        }
        
        for (int i = 0; i < arrayLength; i++)
        {
        	if (array[i] != null)
        	{
        		int addAmount = Integer.parseInt(array[i].split(":")[1]);
            	String toAdd = array[i].split(":")[0];
            	
            	for (int j = 0; j < addAmount; j++)
            	{
            		newMultiset.add(toAdd);
            	}
        	}
        }
        return newMultiset;
    } // end of union()

    /**
     * Intersect utilizes contains to iterate through each value of the array
     */
    
    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	RmitMultiset newMultiset = new ArrayMultiset();
    	int otherNumInstances = 0;
    	for (int i = 0; i < arrayLength; i++)
        {
    		if (array[i] != null)
    		{
    			otherNumInstances = other.search(array[i].split(":")[0]);
    		}
    		
    		
        	if (otherNumInstances > 0)
        	{
        		
        		int numInstances = Integer.parseInt(array[i].split(":")[1]);
        		int addSize = 0;

        		if (numInstances <= otherNumInstances)
        		{
        			addSize = numInstances;
        		}
        		else if (otherNumInstances < numInstances)
        		{
        			addSize = otherNumInstances;
        		}
        		
        		for (int j = 0; j < addSize; j++)
        		{
        			newMultiset.add(array[i].split(":")[0]);
        		}
        	}
        	
        	otherNumInstances = 0;
        }
        return newMultiset;
    } // end of intersect()
    
    /**
     * Difference follows a similar structure to the array method.
     */
    
    @Override
	public RmitMultiset difference(RmitMultiset other) {

    	RmitMultiset newMultiset = new BstMultiset();
        for (int i = 0; i < arrayLength; i++)
        {
        	if (array[i] != null)
        	{
        		int numInstance = Integer.parseInt(array[i].split(":")[1]);
            	int addSize = 0;
            	if (other.contains(array[i].split(":")[0]))
            	{
            		int otherNumInstance = other.search(array[i].split(":")[0]);
            		if (numInstance == otherNumInstance || otherNumInstance > numInstance)
            		{
            			addSize = 0;
            		}
            		else
            		{
            			addSize = numInstance - otherNumInstance;
            		}
            	}
            	else
            	{
            		addSize = numInstance;
            	}
            	
            	for (int j = 0; j < addSize; j++)
            	{
            		newMultiset.add(array[i].split(":")[0]);
            	}
        	}
        }
        
        return newMultiset;
    } // end of difference()
    

} // end of class ArrayMultiset
