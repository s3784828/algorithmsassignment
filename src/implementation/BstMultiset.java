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
	int numNodes;
	
	public BstMultiset()
	{
		BstNode head = null;
		numNodes = 0;
	}

    @Override
	public void add(String item) {
    	
        boolean added = false;
    	
    	if (head == null)
    	{
    		head = new BstNode(item + ":" + 1);
    		numNodes += 1;
    		added = true;
    	}
    	
    	BstNode curr = head;
        while (!added)
        {
        	if (curr.value.split(":")[0].equals(item))
        	{
        		String[] values = curr.value.split(":");
        		int instances = Integer.parseInt(values[1]) + 1; 
        		curr.value = values[0] + ":" + instances;
        		added = true;
        	}
        	
        	if (item.compareToIgnoreCase(curr.value.split(":")[0]) > 0)
        	{
        		if (curr.right == null)
        		{
        			curr.right = new BstNode(item + ":" + 1);
        			numNodes += 1;
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
        			curr.left = new BstNode(item + ":" + 1);
        			numNodes += 1;
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
        	if (curr.value.split(":")[0].equals(item))
        	{
        		instanceCount = Integer.parseInt(curr.value.split(":")[1]);
        		searched = true;
        	}
        	
        	if (item.compareTo(curr.value.split(":")[0]) > 0)
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
    	String[] instances = getBstArray();
    	
    	for (int i = 0; i < numNodes; i++)
    	{
    		if (Integer.parseInt(instances[i].split(":")[1]) == instanceCount)
    		{
    			list.add(instances[i]);
    		}
    	}
              
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
        	if (curr.value.split(":")[0].equals(item))
        	{
        		contains = true;
        		searched = true;
        	}
        	
        	if (item.compareToIgnoreCase(curr.value.split(":")[0]) > 0)
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
        BstNode curr = head;
        BstNode prev = curr;
        boolean removedOne = false;
        
        
        
        
    } // end of removeOne()


    @Override
	public String print() {
    	
        
        return "";
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
    	
        
        return new String();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
    	RmitMultiset newMultiset = new ArrayMultiset();
        String numValues[] = getBstArray();
        
        for (int i = 0; i < numNodes; i++)
        {
        	
        }

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
    
	public String[] getBstArray() {
    	boolean gotArray = false;
    	String bstArray[] = new String[numNodes];
    	int bstArrayIter = 0;
    	
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	currentQue[currentIter] = head;

        while (!gotArray)
        {
        	curr = currentQue[currentIter];
        	bstArray[bstArrayIter] = curr.value;
        	bstArrayIter += 1;
        	
        	
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
        	
        	if (currentIter + 1 == currentSize && otherSize != 0)
        	{
        		currentQue = otherQue;
        		currentSize = otherSize;
        		currentIter = 0;
        		otherQue = new BstNode[100];
        		otherSize = 0;
        		otherIter = 0;
        	}
        	else if (currentIter + 1 == currentSize && otherSize == 0)
        	{
        		gotArray = true;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
        
        return bstArray;
    } // end of OrderedPrint
   
     
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
