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


    @Override
	public int search(String elem) {
    	int dupCount = -1;
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null && array[i].split(":")[0].equals(elem))
    		{
    			dupCount = Integer.parseInt(array[i].split(":")[1]);
    		}
    	}
        return dupCount;
    } // end of search()


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

    @Override
	public boolean contains(String elem) {
        boolean contains = false;
        for (int i = 0; i < arrayLength; i++) 
        {
        	if (array[i] != null && array[i].split(":")[0].equals(elem))
        		contains = true;
        }
        return contains;
    } // end of contains()


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


    @Override
	public RmitMultiset union(RmitMultiset other) {
    	
    	RmitMultiset newMultiset = new BstMultiset();
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


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	RmitMultiset newMultiset = new ArrayMultiset();
    	for (int i = 0; i < arrayLength; i++)
        {
        	if (array[i] != null && other.contains(array[i].split(":")[0]))
        	{
        		int numInstances = Integer.parseInt(array[i].split(":")[1]);
        		int otherNumInstances = other.search(array[i].split(":")[0]);
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
        }
        return newMultiset;
    } // end of intersect()


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
