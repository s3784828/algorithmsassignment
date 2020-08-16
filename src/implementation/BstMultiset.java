package implementation;

import java.util.List;

/**
 * BST implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class BstMultiset extends RmitMultiset
{
	private BstNode head;
	
	public BstMultiset()
	{
		BstNode head = null;
	}

    @Override
	public void add(String item) {
    	
        boolean added = false;
    	
    	if (head == null)
    	{
    		head = new BstNode(item);
    		System.out.println("added");
    		added = true;
    	}
    	
    	BstNode curr = head;
        while (!added)
        {
        	if (item.compareToIgnoreCase(curr.value) > 0)
        	{
        		if (curr.right == null)
        		{
        			System.out.println("RIGHT");
        			curr.right = new BstNode(item);
        			added = true;
        		}
        		else
        		{
        			curr = curr.right;
        		}
        	}
        	else
        	{
        		if (curr.left == null)
        		{
        			System.out.println("LEFT");
        			curr.left = new BstNode(item);
        			added = true;
        		}
        		else
        		{
        			curr = curr.left;
        		}
        	}
        }
    } 


    @Override
	public int search(String item) {
        BstNode curr = head;
        int instanceCount = 0;
        boolean searched = false;
        
        if (head == null)
        {
        	searched = true;        	
        }
        
        while ( !searched )
        {
        	if (curr.value.equals(item))
        	{
        		instanceCount += 1;
        	}
        	
        	if (item.compareTo(curr.value) > 0)
        	{
        		if (curr.right == null)
        		{
        			searched = true;
        		}
        		else
        		{
        			curr = curr.right;
        		}
        	}
        	else
        	{
        		if (curr.left == null)
        		{
        			searched = true;
        		}
        		else
        		{
        			curr = curr.left;
        		}
        	}
        }
        
        
        
        return instanceCount;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
    	List<String> list = new SimpleLinkList<String>();
    	
              
        return list;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) {
        boolean contains = false;
        boolean searched = false;
        BstNode curr = head;
        
        if (head == null)
        {
        	searched = true;
        }
        
        while (!searched)
        {
        	if (curr.value.equals(item))
        	{
        		contains = true;
        		searched = true;
        	}
        	
        	if (item.compareToIgnoreCase(curr.value) > 0)
        	{
        		if (curr.right == null)
        		{
        			searched = true;
        		}
        		else
        		{
        			curr = curr.right;
        		}
        	}
        	else
        	{
        		if (curr.left == null)
        		{
        			searched = true;
        		}
        		else
        		{
        			curr = curr.left;
        		}
        	}
        }
        return contains;
    } // end of contains()


    @Override
	public void removeOne(String item) {
        
    } // end of removeOne()


    @Override
	public String print() {
    	boolean printed = false;
    	String string = "";
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	int currentLevel = 0;
    	string += "===== LEVEL: " + currentLevel + "=====" + "\n";
    	
    	currentQue[currentIter] = head;

        while (!printed)
        {
        	curr = currentQue[currentIter];
        	string += curr.value + "\n";
        	
        	
        	if (curr.right != null)
        	{
        		otherQue[otherIter] = curr.right;
        		otherIter += 1;
        		otherSize += 1;
        	}
        	if (curr.left != null)
        	{
        		otherQue[otherIter] = curr.left;
        		otherIter += 1;
        		otherSize += 1;
        	}
        	
        	System.out.println("value: " + curr.value + " level: " + currentLevel + " currentIter: " + currentIter + " otherSize: " + otherSize + " current size: " + currentSize);
        	
        	if (currentIter + 1 == currentSize && otherSize != 0)
        	{
        		currentQue = otherQue;
        		currentSize = otherSize;
        		currentIter = 0;
        		otherQue = new BstNode[100];
        		otherSize = 0;
        		otherIter = 0;
        		
        		currentLevel += 1;
        		string += "===== LEVEL: " + currentLevel + "=====" + "\n";
        	}
        	else if (currentIter + 1 == currentSize && otherSize == 0)
        	{
        		printed = true;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
        
        return string;
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
    
    private class BstNode
    {
    	String value;
    	BstNode left;
    	BstNode right;
    	
    	public BstNode(String value)
    	{
    		this.value = value;
    		left = null;
    		right = null;
    	}
    	
    	public String getLeftValue()
    	{
    		return left.value;
    	}
    	
    	public String getRightValue()
    	{
    		return right.value;
    	}
    }

} // end of class BstMultiset
