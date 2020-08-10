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
	protected static final int initialArraySize = 10;
	
	public ArrayMultiset()
	{
		array = new String[initialArraySize];
	}

    @Override
	public void add(String elem) {
		array[arrayLength] = elem;
		arrayLength += 1;
    } // end of add()


    @Override
	public int search(String elem) {
        // Implement me!
    	int instanceCount = 0;
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null && array[i] == elem)
    			instanceCount += 1;
    	}
        return instanceCount;
    } // end of search()


    @Override
    public List<String> searchByInstance(int instanceCount) {
    	List<String> list = new SimpleLinkList<String>();
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
    	String selectedInstance;
    	int numInstance = 0;
    	for (int i = 0; i < uniqueValuesLength; i++)
    	{
    		selectedInstance = uniqueValues[i];
    		for (int j = 0; j < arrayLength; j++)
    		{
    			if (array[j] != null && array[j].equals(selectedInstance))
    			{
    				numInstance += 1;
    			}
    		}
    		if (numInstance == instanceCount)
    		{
    			list.add(selectedInstance);
    		}
    		numInstance = 0;
    	}
    	System.out.println("value: " + list.toString() + " count: " + list.size());
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
    	String arrayString = "";
    	for (int i = 0; i < arrayLength; i++)
    	{
    		if (array[i] != null)
    		{
    			if (i > 0)
    				arrayString += " " + array[i];
    			else
    				arrayString += array[i];
    		}
    	}
        
        return arrayString;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

        
        return new String();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {

        // Placeholder, please update.
        return null;
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

} // end of class ArrayMultiset
