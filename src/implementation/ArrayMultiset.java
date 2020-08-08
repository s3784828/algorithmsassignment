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
        // Implement me!
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

        // Placeholder, please update.
        return null;
    } // end of searchByInstance


    @Override
	public boolean contains(String elem) {
        boolean contains = false;
        for (int i = 0; i < arrayLength; i++) 
        {
        	if (!contains && array[i] != null && array[i] == elem)
        		contains = true;
        }
        return contains;
    } // end of contains()


    @Override
	public void removeOne(String elem) {
    	boolean removedOne = false;
        for (int i = 0; i < arrayLength; i++)
        {
        	if (!removedOne && array[i] != null && array[i] == elem)
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
    			
    			if (i > 0)
    				arrayString += " " + array[i];
    			else
    				arrayString += array[i];
    	}
        
        return arrayString;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

        // Placeholder, please update.
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
