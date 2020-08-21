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
	private BstNode root;
	int numNodes;
	
	public BstMultiset()
	{
		BstNode root = null;
		numNodes = 0;
	}

    @Override
	public void add(String item) {
    	
        boolean added = false;
    	
    	if (root == null)
    	{
    		root = new BstNode(item + ":" + 1);
			numNodes += 1;
			added = true;
    	}
    	BstNode curr = root;
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
        BstNode curr = root;
        int instanceCount = -1;
        boolean searched = false;
        
        if (root == null)
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
	
    	boolean searched = false;
	
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	currentQue[currentIter] = root;

        while (!searched)
        {
        	curr = currentQue[currentIter];
        	if (Integer.parseInt(curr.value.split(":")[1]) == instanceCount)
        	{
        		list.add(curr.value.split(":")[0]);
        	}

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
        		searched = true;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
              
        return list;
    } // end of searchByInstance    


    @Override
	public boolean contains(String item) {
        boolean contains = false;
        boolean searched = false;
        BstNode curr = root;
        
        if (root == null)
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
        
    	BstNode curr = root;
    	BstNode prev = curr;
    	
    	if (root.value.split(":")[0].equals(item))
    	{
    		int numValues = Integer.parseInt(root.value.split(":")[1]);
    		if (numValues > 1)
    		{
    			int newValue = numValues - 1;
    			root.value = root.value.split(":")[0] + ":" + (newValue);
    		}
    		else
    		{
    			removeRoot();
    		}
    	}
    	else
    	{
    		boolean searched = false;
    		while(!searched)
    		{
    			if (item.equals(curr.value.split(":")[0]))
    			{
    				int numValues = Integer.parseInt(curr.value.split(":")[1]);
    				if (numValues > 1)
    				{
    					int newValue = numValues - 1;
    	    			curr.value = curr.value.split(":")[0] + ":" + (newValue);
    				}
    				else
    				{
    					removeLeaf(curr, prev);
    				}
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
    					prev = curr;
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
    					prev = curr;
    					curr = curr.left;
    				}
    			}

    		}
    	}
    } // end of removeOne()

    public void removeRoot()
    { 	
    	
    	BstNode curr = root;
    	BstNode prev = curr;
    	BstNode toRemove = null;
    	boolean removed = false;
    	
    	if (root.left == null && root.right == null)
    	{
    		System.out.println("removed head");
    		
    		root = null;
    		numNodes -= 1;
    		System.out.println(print());
    	}
    	else if (root.right != null && root.left == null)
    	{
    		System.out.println("right is head");
    		
    		root = root.right;
    		numNodes -= 1;
    		System.out.println(print());
    		
    	}
    	else if (root.right == null && root.left != null)
    	{
    		System.out.println("left is head");
    		
    		root = root.left;
    		numNodes -= 1;
    		System.out.println(print());
    	}
    	else
    	{
    		
    		curr = curr.right;
    		
    		while (!removed)
    		{
    			if (curr.left != null)
    			{
    				prev = curr;
    				curr = curr.left;
    			}
    			else
    			{
    				if (prev == root)
    				{
    					System.out.println("prev is root");
    					root.value = curr.value;
    					
    					if (curr.right != null)
    					{
    						root.right = curr.right;
    					}
    					else
    					{
    						root.right = null;
    					}
    					removed = true;
    					curr = null;
    					numNodes -= 1;
    					System.out.println("root" + root.value);
    					System.out.println(print());
    				}
    				else
    				{
    					System.out.println("leftmost node is root");
    					root.value = curr.value;
    					prev.left = null;
    					curr = null;
    					numNodes -= 1;
    					removed = true;
    					System.out.println("root" + root.value);
    					System.out.println(print());
    				}
    				
    			}
    		}
    	}
    }
    
    public void removeLeaf(BstNode curr, BstNode prev)
    {
    	String prevValue = prev.value.split(":")[0];
    	String currValue = curr.value.split(":")[0];
    	
    	if (curr.left == null && curr.right == null)
    	{
    		if (currValue.compareTo(prevValue) > 0)
    		{
    			prev.right = null;
    			curr = null;
    		}
    		else
    		{
    			prev.left = null;
    			curr = null;
    		}
    		numNodes -= 1;
    	}
    	else if (curr.left != null && curr.right == null)
    	{
    		if (currValue.compareTo(prevValue) > 0)
    		{
    			prev.right = curr.left;
    			curr = null;
    			numNodes -= 1;
    		}
    		else
    		{
    			prev.left = curr.left;
    			curr = null;
    			numNodes -= 1;
    		}
    	}
    	else if (curr.left == null && curr.right != null)
    	{
    		if (currValue.compareTo(prevValue) > 0)
    		{
    			prev.right = curr.right;
    			curr = null;
    			numNodes -= 1;
    		}
    		else
    		{
    			prev.left = curr.right;
    			curr = null;
    			numNodes -= 1;
    		}
    	}
    	else
    	{
    		BstNode toReplace = curr;
    		prev = curr;
    		curr = curr.right;
    		boolean removed = false;
    		
    		while(!removed)
    		{
    			
    			if (curr.left == null)
        		{
    				prevValue = prev.value.split(":")[0];
    		    	currValue = curr.value.split(":")[0];
    		    	
    		    	toReplace.value = curr.value;
    		    	
    		    	if (currValue.compareTo(prevValue) > 0)
    		    	{
    		    		prev.right = null;
    		    		curr = null;
    		    	}
    		    	else
    		    	{
    		    		prev.left = null;
    		    		curr = null;
    		    	}
    		    	removed = true;
    		    	numNodes -= 1;
        		}
    			else
    			{
    				prev = curr;
    				curr = curr.left;
    			}
    		}
    		
    		
    	}
    	
    }

    @Override
	public String print() {
    	String toPrint = "";
    	
    	int largestValue = 0;
    	
    	boolean searched = false;
    	
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	currentQue[currentIter] = root;
    	
        while (!searched)
        {
        	curr = currentQue[currentIter];
        	
        	if (Integer.parseInt(curr.value.split(":")[1]) > largestValue)
        	{
        		largestValue = Integer.parseInt(curr.value.split(":")[1]);
        	}

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
        		searched = true;
        	}
        	else
        	{
        		currentIter += 1;
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
    	
    	String toPrint = "";
    	boolean printed = false;
    	
    	
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	currentQue[currentIter] = root;
    	
    	if (root == null)
    	{
    		printed = true;
    	}
    	else if (root.value.split(":")[0].compareToIgnoreCase(lower) <= 0
    			&& root.value.split(":")[0].compareToIgnoreCase(upper) >= 0)
    	{
    		toPrint += root.value + "\n";
    	}
    	

        while (!printed)
        {
        	curr = currentQue[currentIter];
        	
        	if (curr != root)
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
        
        BstMultiset otherBst = (BstMultiset) other;
        
    	BstNode curr;
    	
    	int numSearches = 0;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	int numInstance = 0;
    	
    	currentQue[currentIter] = root;
        while (numSearches <= 1)
        {
        	curr = currentQue[currentIter];
        	
        	numInstance = Integer.parseInt(curr.value.split(":")[1]);

        	for (int j = 0; j < numInstance; j++)
        	{
        		
        		newMultiset.add(curr.value.split(":")[0]);
        	}

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
        		numSearches += 1;
        		currentQue = new BstNode[100];
            	otherQue = new BstNode[100];
            	currentIter = 0;
            	currentSize = 1;
            	otherIter = 0;
            	otherSize = 0;
            	
            	currentQue[currentIter] = otherBst.root;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
        
        
       
        return newMultiset;

    } // end of union()
    
    @Override
	public RmitMultiset intersect(RmitMultiset other) {
    	RmitMultiset newMultiset = new BstMultiset();

        boolean searched = false;
        
        BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	int numInstances;
    	int otherNumInstances;
    	int addSize;
    	
    	currentQue[currentIter] = root;
        while (!searched)
        {
        	curr = currentQue[currentIter];
 	
        	if (other.contains(curr.value.split(":")[0]))
        	{
        		numInstances = Integer.parseInt(curr.value.split(":")[1]);
        		otherNumInstances = other.search(curr.value.split(":")[0]);
        		addSize = 0;

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
        			newMultiset.add(curr.value.split(":")[0]);
        		}
        	}

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
        		searched = true;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
        return newMultiset;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
    	RmitMultiset newMultiset = new BstMultiset();
        boolean searched = false;
    	
    	BstNode curr;
    	
    	BstNode currentQue[] = new BstNode[100];
    	BstNode otherQue[] = new BstNode[100];
    	int currentIter = 0;
    	int currentSize = 1;
    	int otherIter = 0;
    	int otherSize = 0;
    	
    	int numInstance;
    	int addSize;
    	
    	currentQue[currentIter] = root;

        while (!searched)
        {
        	curr = currentQue[currentIter];
        	
        	numInstance = Integer.parseInt(curr.value.split(":")[1]);
        	addSize = 0;
        	if (other.contains(curr.value.split(":")[0]))
        	{
        		int otherNumInstance = other.search(curr.value.split(":")[0]);
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
        		newMultiset.add(curr.value.split(":")[0]);
        	}

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
        		searched = true;
        	}
        	else
        	{
        		currentIter += 1;
        	}
        }
        
        return newMultiset;
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
    }

} // end of class BstMultiset
