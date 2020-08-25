package implementation;

import java.util.List;

/**
 * Ordered linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class OrderedLinkedListMultiset extends RmitMultiset
{
	private Node head;
	
	public OrderedLinkedListMultiset() {
		head = null;
	}

    @Override
	public void add(String item) {
    	boolean added = false;
		
		if ( head == null )
		{
			head = new Node(item + ":1");
			added = true;
		}
		else 
		{
			Node currNode = head;
			Node prevNode = null;
			while(currNode != null && !added)
			{
				String[] currSplitValue = currNode.value.split(":");
				
				//if item already exists
				if(!added && currSplitValue[0].equals(item)){
					int instanceCount = Integer.parseInt(currSplitValue[1]);
					instanceCount++;
					currNode.value = item + ":" + instanceCount;
					added = true;
				}
				else if(!added && currSplitValue[0].compareToIgnoreCase(item) >= 0 && 
						(currNode.next == null || currNode.next.value.split(":")[0].compareToIgnoreCase(item) >= 0)) {
						Node tempNode = currNode;
						Node newNode = new Node(item + ":1");
						if(prevNode != null) {
							prevNode.next = newNode;
						}else {
							head = newNode;
						}
						newNode.next = tempNode;
						added = true;
						
				}
				//if at the end of list
				else if (!added && currNode.next == null)
				{
					currNode.next = new Node(item + ":1");
					added = true;
				}
				// checks if the value variable is before the next node alphabetically
				else
				{
					prevNode = currNode;
					currNode = currNode.next;
				}
			}
		}
		System.out.println(print());
    } // end of add()


    @Override
	public int search(String item) {
        int count = 0;
        Node currNode = head;
        while(currNode != null) {
        	String[] currSplitValue = currNode.value.split(":");
        	if(currSplitValue[0].equals(item)) {
        		count = Integer.parseInt(currSplitValue[1]);
        	}
        	currNode = currNode.next;
        }

        // Placeholder, please update.
        return count > 0 ? count : searchFailed;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
    	List<String> list = new SimpleLinkList<String>();
    	Node currNode = head;
    	while(currNode != null) {
    		String[] currSplitValue = currNode.value.split(":");
    		
    		if(Integer.parseInt(currSplitValue[1]) == instanceCount) {
    			list.add(currSplitValue[0]);
    		}
    		currNode = currNode.next;
    	}
        
        return list;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        boolean doesContain = false;
    	Node currNode = head;
        
        while(currNode != null && !doesContain) {
        	String[] currSplitValue = currNode.value.split(":");
        	if(currSplitValue[0].equals(item)) {
        		doesContain = true;
        	}
        	currNode = currNode.next;
        }
    	
        return doesContain;
    } // end of contains()


    @Override
	public void removeOne(String item) {
    	if(head != null) {
    		
    		boolean removed = false;
        	Node currNode = head;
        	Node prevNode = null;
        	
        	//for if the head is the item
        	String[] currSplitValue = currNode.value.split(":");
        	if(currSplitValue[0] == item) {
        		int numOfInstances = Integer.parseInt(head.value.split(":")[1]);
        		if(numOfInstances > 1) {
        			numOfInstances--;
        			head.value = currSplitValue[0] + ":" + numOfInstances;
        		}else {
        			head = head.next;
        		}
        		
        		removed = true;
        	}
        	prevNode = currNode;
        	currNode = currNode.next;
        	
            while(!removed && currNode != null) {
            	currSplitValue = currNode.value.split(":");
            	
            	if(currSplitValue[0].equals(item)) {
            		
            		int numOfInstances = Integer.parseInt(currNode.value.split(":")[1]);
            		if(numOfInstances > 1) {
            			numOfInstances--;
            			currNode.value = currSplitValue[0] + ":" + numOfInstances;
            		}else {
            			prevNode.next = currNode.next;
            		}
            		removed = true;
            	}
            	prevNode = currNode;
            	currNode = currNode.next;
            }
    	}
        
    } // end of removeOne()


    @Override
	public String print() {
    	String result = "";
    	Node currNode = head;
    	
    	//find the largest instance
    	int largestInstance = 0;
    	while(currNode != null) {
    		int currInstance = Integer.parseInt(currNode.value.split(":")[1]);
    		largestInstance = largestInstance < currInstance ? currInstance : largestInstance;
    		currNode = currNode.next;
    	}
    	
    	//add to result in order of instance
    	for(int i = largestInstance; i > 0; i--) {
    		SimpleLinkList<String> list = (SimpleLinkList<String>) searchByInstance(i);
    		for(String entry : list) {
    			result += entry + ":" + i + "\n";
    		}
    		
    	}
        // Placeholder, please update.
        return result;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

    	String range = "";
    	Node currNode = head;
    	while(currNode != null) {
    		if(currNode.value.split(":")[0].compareToIgnoreCase(lower) >= 0 &&
    		   currNode.value.split(":")[0].compareToIgnoreCase(upper) <= 0) {
    			
    			range += currNode.value + "\n";
    		}
    		currNode = currNode.next;
    	}
        // Placeholder, please update.
        return range;
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        RmitMultiset union = new OrderedLinkedListMultiset();
        
        Node currNode = head;
        
        while(currNode != null) {
        	String[] currSplitValue = currNode.value.split(":");
        	int numInstance = Integer.parseInt(currSplitValue[1]);
        	for(int i = 0; i < numInstance; i++) {
        		union.add(currSplitValue[0]);
        	}
        	currNode = currNode.next;
        }
        
        if(other instanceof OrderedLinkedListMultiset) {
        	
        	currNode = ((OrderedLinkedListMultiset) other).head;
        	
        	while(currNode != null) {
            	String[] currSplitValue = currNode.value.split(":");
            	int numInstance = Integer.parseInt(currSplitValue[1]);
            	for(int i = 0; i < numInstance; i++) {
            		union.add(currSplitValue[0]);
            	}
            	currNode = currNode.next;
            }
        }
        
        return union;
    } // end of union()


    @Override
	public RmitMultiset intersect(RmitMultiset other) {

    	RmitMultiset intersect = new OrderedLinkedListMultiset();
    	
    	if(other instanceof OrderedLinkedListMultiset) {
    		
    		Node currNode = head;
    	
    		while(currNode != null) {
    			String[] currSplitValue = currNode.value.split(":");
    			int otherCount = other.search(currSplitValue[0]);
    			if(otherCount > 0) {
    				//finds the lesser value and sets numInstance to that value
    				int numInstance = otherCount < Integer.parseInt(currSplitValue[1]) ? otherCount : Integer.parseInt(currSplitValue[1]);
    				//adds value numInstance times
    				for(int i = 0; i < numInstance; i++) {
                		intersect.add(currSplitValue[0]);
                	}
    			}
    			currNode = currNode.next;
    		}
    		
    	}
        return intersect;
    } // end of intersect()


    @Override
	public RmitMultiset difference(RmitMultiset other) {
    	
    	RmitMultiset difference = new OrderedLinkedListMultiset();
    	
    	if(other instanceof OrderedLinkedListMultiset) {
    		Node currNode = head;
    		
    		while(currNode != null) {
    			String[] currSplitValue = currNode.value.split(":");
    			
    			int otherInstance = other.search(currSplitValue[0]);
    			otherInstance = otherInstance > 0 ? otherInstance : 0;
    			int diffInstance = Integer.parseInt(currSplitValue[1]) - otherInstance;
    			
    			if(diffInstance > 0) {
    				for(int i = 0; i < diffInstance; i++) {
    					difference.add(currSplitValue[0]);
    				}
    			}
    			currNode = currNode.next;
    		}
    	}
    	
        return difference;
    } // end of difference()
    
    
    private class Node{
    	protected String value;
    	protected Node next;
    	
    	public Node(String _value){
    		value = _value;
    		next = null;
    	}
    }

} // end of class OrderedLinkedListMultiset
