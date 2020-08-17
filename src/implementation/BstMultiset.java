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
        	
        	if (item.compareToIgnoreCase(curr.value.split(":")[0]) > 0 && !added)
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
        	else if (!added)
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
        	
        	if (item.compareTo(curr.value.split(":")[0]) > 0 && !searched)
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
        	else if (!searched)
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
        	
        	if (item.compareToIgnoreCase(curr.value.split(":")[0]) > 0 && !searched)
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
        	else if (!searched)
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
    	String toPrint = "";
    	String[] values = getBstArray();
    	
    	for (int i = 0; i < numNodes; i++)
    	{
    		for(int j = 0; j < numNodes - 1; j++)
    		{
    			int value1 = Integer.parseInt(values[j].split(":")[1]);
    			int value2 = Integer.parseInt(values[j + 1].split(":")[1]);
    			if (value1 < value2)
    			{
    				String temp1 = values[j];
    				String temp2 = values[j + 1];
    				values[j] = temp2;
    				values[j + 1] = temp1;
    			}
    		}
    	}
    	
    	for (int i = 0; i < numNodes; i++)
    	{
    		toPrint += values[i] + "\n";
    	}
        
        return toPrint;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {
    	
    	String toPrint = "";
    	boolean printed = false;
    	
    	
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	currentQue[currentIter] = head;
    	
    	if (head == null)
    	{
    		printed = true;
    	}
    	else if (head.value.split(":")[0].compareToIgnoreCase(lower) <= 0
    			&& head.value.split(":")[0].compareToIgnoreCase(upper) >= 0)
    	{
    		toPrint += head.value + "\n";
    	}
    	

        while (!printed)
        {
        	curr = currentQue[currentIter];
        	
        	if (curr != head)
        	{
        		toPrint += curr.value + "\n";
        	}

        	if (curr.right != null
        			&& curr.right.value.split(":")[0].compareToIgnoreCase(lower) >= 0
        			&& curr.right.value.split(":")[0].compareToIgnoreCase(upper) <= 0)
        	{
        		otherQue[otherIter] = curr.right;
        		otherIter += 1;
        		otherSize += 1;
        	}
        	if (curr.left != null
        			&& curr.left.value.split(":")[0].compareToIgnoreCase(lower) >= 0
        			&& curr.left.value.split(":")[0].compareToIgnoreCase(upper) <= 0)
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
        		printed = true;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
        
        return toPrint;
    } 


    @Override
	public RmitMultiset union(RmitMultiset other) {
    	RmitMultiset newMultiset = new BstMultiset();
        String[] otherValuesArray = other.print().split("\n");
        String[] valuesArray = getBstArray();

        for (int i = 0; i < otherValuesArray.length; i++)
        {
        	int addAmount = Integer.parseInt(otherValuesArray[i].split(":")[1]);
        	String toAdd = otherValuesArray[i].split(":")[0];
        	
        	for (int j = 0; j < addAmount; j++)
        	{
        		newMultiset.add(toAdd);
        	}
        }
        
        for (int i = 0; i < numNodes; i++)
        {
        	int addAmount = Integer.parseInt(valuesArray[i].split(":")[1]);
        	String toAdd = valuesArray[i].split(":")[0];
        	
        	for (int j = 0; j < addAmount; j++)
        	{
        		newMultiset.add(toAdd);
        	}
        }
    	
        return newMultiset;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	RmitMultiset newMultiset = new BstMultiset();
        String numValues[] = getBstArray();
        
        for (int i = 0; i < numNodes; i++)
        {
        	if (other.contains(numValues[i].split(":")[0]))
        	{
        		int numInstances = Integer.parseInt(numValues[i].split(":")[1]);
        		int otherNumInstances = other.search(numValues[i].split(":")[0]);
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
        			newMultiset.add(numValues[i].split(":")[0]);
        		}
        	}
        		
        }
        return newMultiset;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
    	RmitMultiset newMultiset = new BstMultiset();
        String numValues[] = getBstArray();
        
        for (int i = 0; i < numNodes; i++)
        {
        	int numInstance = Integer.parseInt(numValues[i].split(":")[1]);
        	int addSize = 0;
        	if (other.contains(numValues[i].split(":")[0]))
        	{
        		int otherNumInstance = other.search(numValues[i].split(":")[0]);
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
        		newMultiset.add(numValues[i].split(":")[0]);
        	}
        }
        
        return newMultiset;
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
    }

} // end of class BstMultiset
