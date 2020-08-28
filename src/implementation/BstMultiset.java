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
	
	/**
	 * Add does not used a recursive implementation
	 * Ultimately I would rather use a recursive implementation 
	 * for binary search tree however this was implemented 
	 * before I knew we could make our own methods and as such
	 * did not have time to change the implementation.
	 * 
	 * Values greater than the root are made right node 
	 * and values less are made left node, an iterator iterates
	 * through the tree.
	 */

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

    /**
	 * An iterator tries to find the particular element by iterating
	 * through the respective nodes greater than or equal to the item it 
	 * is searching for.
	 */
    
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

    /**
	 * ALL IMPLEMENTATIONS THAT REQUIRE SEARCHING THROUGH THE ENTIRE
	 * BINARY SEARCH TREE INVOLVE THE 2 QUE IMPLEMENTATION OF SEARCHING
	 * 
	 * A 2 que implementation is used to find every element 
	 * in the binary search tree, one que is used to show the current level being
	 * iterated and the other que is used to show the next que being iterated
	 * once a level of the tree has been iterated it switches que over and repeats
	 * the process to all values of the tree have been searched.
	 * 
	 * search by instance searches each value and adds them to a list
	 * depending on if their duplicate counts are equals to inserted instance count
	 */

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

    /**
	 * contains iterates through each node depending on the item it is searching for
	 */

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
    
    /**
     * In a previous comment for add I mentioned that 
     * I did not know methods could be added, luckily removeOne was implemented
     * much later into the life cycle of this and as such features two methods removeRoot
     * and removeLeaf. Keep in mind these methods are not recursive, despite 
     * implementing this method with the knowledge of being able to add methods
     * i felt it was neccessary to not use a recursive approach to removing and keep 
     * the entirety of the bst's method iterable as opposed to some recursive and some not 
     * recursive. 
     * 
     * removeOne uses two methods removeLeaf and removeRoot, they are only called
     * if the value being removed has only one value.
     */


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
    			if (curr.value.split(":")[0].equals(item))
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
    	boolean removed = false;
    	
    	if (root.left == null && root.right == null)
    	{	
    		root = null;
    		numNodes -= 1;
    	}
    	else if (root.right != null && root.left == null)
    	{
    		root = root.right;
    		numNodes -= 1;
    	}
    	else if (root.right == null && root.left != null)
    	{
    		root = root.left;
    		numNodes -= 1;
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
    				}
    				else
    				{
    					root.value = curr.value;
    					prev.left = curr.right;
    					curr = null;
    					numNodes -= 1;
    					removed = true;
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
    		if (currValue.compareToIgnoreCase(prevValue) > 0)
    		{
    			prev.right = null;
    			curr = null;
    			numNodes -= 1;
    		}
    		else
    		{
    			prev.left = null;
    			curr = null;
    			numNodes -= 1;
    		}
    		
    	}
    	else if (curr.left != null && curr.right == null)
    	{
    		if (currValue.compareToIgnoreCase(prevValue) > 0)
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
    		if (currValue.compareToIgnoreCase(prevValue) > 0)
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

    				toReplace.value = curr.value;
    					
    				if ( toReplace != prev)
        		    {
        		    	prev.left = curr.right;
        		    }
    				else
    				{
    					toReplace.right = curr.right;
    				}
    				
    				curr = null;
    				numNodes -= 1;
    				removed = true;
    			}
    			else
    			{
    				prev = curr;
    				curr = curr.left;
    			}
    		}	
    	}
    }
    
    /**
	 * ALL IMPLEMENTATIONS THAT REQUIRE SEARCHING THROUGH THE ENTIRE
	 * BINARY SEARCH TREE INVOLVE THE 2 QUE IMPLEMENTATION OF SEARCHING
	 * 
	 * A 2 que implementation is used to find every element 
	 * in the binary search tree, one que is used to show the current level being
	 * iterated and the other que is used to show the next que being iterated
	 * once a level of the tree has been iterated it switches que over and repeats
	 * the process to all values of the tree have been searched.
	 * 
	 * print follows a similar method of calculation found in other data structures
	 * of finding the largest value and iterating starting from that value and using 
	 * search by instance to find the largest value. This gives conclusive results to
	 * analyse the different data structures
	 */

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
    	
    	
    	if (root != null)
    	{
    		currentQue[currentIter] = root;
    		
    	}
    	else 
    	{
    		searched = true;
    	}
    	
    	
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

    /**
	 * Print range is a hybrid of the 2 que
	 * system, instead of using the other que to store values of the next
	 * level, now the other que only stores values in the certain specified
	 * range.
	 */
    
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

    /**
	 * ALL IMPLEMENTATIONS THAT REQUIRE SEARCHING THROUGH THE ENTIRE
	 * BINARY SEARCH TREE INVOLVE THE 2 QUE IMPLEMENTATION OF SEARCHING
	 * 
	 * A 2 que implementation is used to find every element 
	 * in the binary search tree, one que is used to show the current level being
	 * iterated and the other que is used to show the next que being iterated
	 * once a level of the tree has been iterated it switches que over and repeats
	 * the process to all values of the tree have been searched.
	 * 
	 * union essentially uses the 2 que method twice, simply changing the starting node
	 * to iterate through at the end of iterating through the first tree.
	 */

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
    
    /**
	 * ALL IMPLEMENTATIONS THAT REQUIRE SEARCHING THROUGH THE ENTIRE
	 * BINARY SEARCH TREE INVOLVE THE 2 QUE IMPLEMENTATION OF SEARCHING
	 * 
	 * A 2 que implementation is used to find every element 
	 * in the binary search tree, one que is used to show the current level being
	 * iterated and the other que is used to show the next que being iterated
	 * once a level of the tree has been iterated it switches que over and repeats
	 * the process to all values of the tree have been searched.
	 * 
	 * intersect follows similar implementation to the other data structures.
	 */
    
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
    
    /**
	 * ALL IMPLEMENTATIONS THAT REQUIRE SEARCHING THROUGH THE ENTIRE
	 * BINARY SEARCH TREE INVOLVE THE 2 QUE IMPLEMENTATION OF SEARCHING
	 * 
	 * A 2 que implementation is used to find every element 
	 * in the binary search tree, one que is used to show the current level being
	 * iterated and the other que is used to show the next que being iterated
	 * once a level of the tree has been iterated it switches que over and repeats
	 * the process to all values of the tree have been searched.
	 * 
	 * difference is implemented similar to other data structures.
	 */
    
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
