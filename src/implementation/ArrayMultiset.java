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
	protected static final int initialArraySize = 10;
	
	public ArrayMultiset()
	{
		array = new String[initialArraySize];
		arrayLength = 0;
		instanceCount = 0;
	}

    @Override
	public void add(String elem) {
		array[arrayLength] = elem;
		arrayLength += 1;
		instanceCount += 1;
    } // end of add()


    @Override
	public int search(String elem) {
        // Implement me!
    	int dupCount = 0;
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null && array[i].equals(elem))
    			dupCount += 1;
    	}
        return dupCount;
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {
    	List<String> list = new SimpleLinkList<String>();
    	String selectedInstance;
    	int dupCount = 0;
    	String uniqueValues[] = getUniqueValues();
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (uniqueValues[i] != null)
    		{
    			selectedInstance = uniqueValues[i];
        		dupCount = search(selectedInstance);
        		if (dupCount == instanceCount)
        		{
        			list.add(selectedInstance);
        		}
        		dupCount = 0;	
    		}
    	}
        return list;
    } // end of searchByInstance

    @Override
	public boolean contains(String elem) {
        boolean contains = false;
        for (int i = 0; i < arrayLength; i++) 
        {
        	if (!contains && array[i] != null && array[i].equals(elem))
        		contains = true;
        }
        return contains;
    } // end of contains()


    @Override
	public void removeOne(String elem) {
    	boolean removedOne = false;
        for (int i = 0; i < arrayLength; i++)
        {
        	if (!removedOne && array[i] != null && array[i].equals(elem))
        	{
        		removedOne = true;
        		array[i] = null;
        	}
        }
    } // end of removeOne()


    @Override
	public String print() {
        
    	String toPrint = "";
    	String toPrintArray[] = getUniqueValues();
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (toPrintArray[i] != null)
    		{
    			String selectedInstance = toPrintArray[i];
    			toPrint += selectedInstance + ":" + search(selectedInstance) + "\n";
    		}
    	}
        return toPrint;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
        String range = "";
        String uniqueValues[] = getUniqueValues();
        for (int i = 0; i < arrayLength; i++)
        {
        	if (uniqueValues[i] != null)
        	{
        		String value = uniqueValues[i];
        		
        		if (value.compareToIgnoreCase(lower) >= 0 && value.compareToIgnoreCase(upper) <= 0)
        		{
        			range += value + ":" + search(value) + "\n";
        		}
        	}
        }

        return range;
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
    	
    	RmitMultiset union = new ArrayMultiset();
    	String selectedValue = "";
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null)
    		{
    			selectedValue = array[i];
    			if (other.contains(selectedValue))
    			{
    				union.add(selectedValue);
    			}    			
    		}	
    	}
        
        return union;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {

        // Placeholder, please update.
        return null;
    } // end of difference()
    
    private String[] getUniqueValues()
    {
    	String[] uniqueValues = new String[arrayLength];
    	int uniqueValuesLength = 0;
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null)
    		{
    			String selectedValue = array[i];
    			boolean addValue = true;
    			for (int j = 0; j < uniqueValuesLength; j++)
    			{
    				if (uniqueValues[j].equals(selectedValue))
    				{
    					addValue = false;
    				}
    			}
    			if (addValue)
    			{
    				uniqueValues[uniqueValuesLength] = selectedValue;
    				uniqueValuesLength += 1;
    			}
    		}
    	}
    	return uniqueValues;
    }

} // end of class ArrayMultiset
