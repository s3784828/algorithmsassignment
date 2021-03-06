package implementation;

import java.util.List;



/**
 * Dual linked list implementation of a multiset.  See comments in RmitMultiset to
 * understand what each overriden method is meant to do.
 *
 * @author Jeffrey Chan & Yongli Ren, RMIT 2020
 */
public class DualLinkedListMultiset extends RmitMultiset
{
	
	private Node head;
	private Node instanceHead;
	
	public DualLinkedListMultiset() {
		head = null;
		instanceHead = null;
	}

	/*
	 * This add method finds the position of the new item
	 * the same way it did for the ordered list, however the
	 * second list adds this value if it already exist and repositions it
	 * in accordance with instance count first, then in terms of alphabetical order
	 */
    @Override
	public void add(String item) {
    	boolean added = false;
		
		if ( head == null && instanceHead == null )
		{
			head = new Node(item + ":1");
			instanceHead = new Node(item + ":1");
			added = true;
		}
		else 
		{
			//adding to alphabetical list
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
						(currNode.next == null || currNode.next.value.split(":")[0].compareToIgnoreCase(item) > 0)) {
					
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
			
			//adding to instance list
			added = false;
			currNode = instanceHead;
			prevNode = null;
			
			while(currNode != null && !added) {
				String[] currSplitValue = currNode.value.split(":");
				
				//if item already exists
				if(currSplitValue[0].equals(item)){
					int instanceCount = Integer.parseInt(currSplitValue[1]);
					instanceCount++;
					currNode.value = item + ":" + instanceCount;
					
					//repositioning node based on instance
					if(prevNode != null) {
						prevNode.next = currNode.next;
						Node checkNode = instanceHead;
						Node prevCheckNode = null;
						
						//checking each node to see where currNode fits
						while(!added) {
							if(checkNode == null) {
								if(prevCheckNode != null) {
									prevCheckNode.next = currNode;
								}else {
									instanceHead = currNode;
								}
								currNode.next = null;
								added = true;
							}else {
								String[] checkSplitString = checkNode.value.split(":");
								
								//if checkNode has lower instance or is equal instance but behind alphabetically
								if(Integer.parseInt(checkSplitString[1]) < instanceCount ||
								   Integer.parseInt(checkSplitString[1]) == instanceCount && checkSplitString[0].compareToIgnoreCase(item) >= 0) {
									
									if(prevCheckNode != null) {
										currNode.next = checkNode;
										prevCheckNode.next = currNode;
									}else {
										currNode.next = instanceHead;
										instanceHead = currNode;
									}
									
									added = true;
								}
								prevCheckNode = checkNode;
								checkNode = checkNode.next;
							}
						}
						
					}else {
						added = true;
					}
				}
				else if(!added && Integer.parseInt(currSplitValue[1]) == 1 && currSplitValue[0].compareToIgnoreCase(item) >= 0 && 
						(currNode.next == null || currNode.next.value.split(":")[0].compareToIgnoreCase(item) > 0)) {
					
					Node tempNode = currNode;
					Node newNode = new Node(item + ":1");
					if(prevNode != null) {
						prevNode.next = newNode;
					}else {
						instanceHead = newNode;
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
    } // end of add()


    /*
     * This method simply loops through the list until
     * it finds a matching item, returning the instance number
     * or searchFailed if no item exists
     */
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


    /*
     * This search method iterates through the instance list, finding a matching
     * instance count. It then adds all values after to the new list until the instance
     * count differs. Saving time by exiting early as it knows there are no more
     * matching instance counts
     */
    @Override
	public List<String> searchByInstance(int instanceCount) {
    	boolean oneEntryFound = false;
    	boolean allEntriesFound = false;
    	List<String> list = new SimpleLinkList<String>();
    	Node currNode = instanceHead;
    	while(!allEntriesFound && currNode != null) {
    		String[] currSplitValue = currNode.value.split(":");
    		
    		if(Integer.parseInt(currSplitValue[1]) == instanceCount) {
    			list.add(currSplitValue[0]);
    		}else if(oneEntryFound) {
    			
    			//if currNode instance != instance count and at least one entry has been found
    			//then there are no more entries to be found
    			allEntriesFound = true;
    		}
    		currNode = currNode.next;
    	}
        
        return list;
    } // end of searchByInstance    

    /*
     * The contains method iterates through the list.
     * If the matching item is found, it breaks out of the loop
     * and returns true. Otherwise false
     */
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

    /*
     * The remove one method repeats the same process as the ordered list
     * with the addition of finding the value within the second list, then once found
     * it will remove it from the list, subtract its old instance value by one.
     * Lastly if the instance value is still above 1, it will search for its new
     * correct position, similar to the add method
     */
    @Override
	public void removeOne(String item) {
    	if(head != null && instanceHead != null) {
    		
    		boolean removed = false;
        	Node currNode = head;
        	Node prevNode = null;
        	
        	//for if the head is the item
        	String[] currSplitValue = currNode.value.split(":");
        	if(currSplitValue[0].equals(item)) {
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
            
            removed = false;
            currNode = instanceHead;
            prevNode = null;
            
            while(!removed && currNode != null) {
            	
            	currSplitValue = currNode.value.split(":");
            	
            	if(currSplitValue[0].equals(item)) {
            		int instanceCount = Integer.parseInt(currSplitValue[1]);
            		
            		if(prevNode == null) {
            			instanceHead = currNode.next;
            		}else {
            			prevNode.next = currNode.next;
            		}
            		if(instanceCount > 1) {
            			instanceCount--;
            			currNode.value = currSplitValue[0] + ":" + instanceCount;
            			
            			//add new instance to list
            			if(instanceHead != null) {
            				boolean added = false;
                			Node checkNode = instanceHead;
                			Node prevCheckNode = null;
                			while(!added) {
                				String[] checkSplitString = checkNode.value.split(":");
                				//if checkNode has lower instance or is equal instance but behind alphabetically
    							if(Integer.parseInt(checkSplitString[1]) < instanceCount ||
    							   Integer.parseInt(checkSplitString[1]) == instanceCount && checkSplitString[0].compareToIgnoreCase(item) >= 0) {
    								
    								if(prevCheckNode != null) {
    									currNode.next = checkNode;
    									prevCheckNode.next = currNode;
    								}else {
    									currNode.next = instanceHead;
    									instanceHead = currNode;
    								}
    								
    								added = true;
    								
    								
    							}
    							//if at end of list
    							else if(checkNode.next == null) {
    								checkNode.next = currNode;
    								currNode.next = null;
    								
    								added = true;
    							}
    							prevCheckNode = checkNode;
    							checkNode = checkNode.next;
                			}
            			}else {
            				instanceHead = currNode;
            				currNode.next = null;
            			}
            		}
            		removed = true;
            	}
            	
            	prevNode = currNode;
            	currNode = currNode.next;
            }
            
    	}
    } // end of removeOne()

    /*
     * This print method simply iterates through
     * the instance list and adds all values to a string
     * as they are already in instance order
     */
    @Override
	public String print() {
    	Node currNode = instanceHead;
    	String result = "";
        while(currNode != null){
        	result += currNode.value + "\n";
        	currNode = currNode.next;
        }
        return result;
    } // end of OrderedPrint

    /*
     * This method iterates through the list finding any
     * items that are between the lower and upper values
     * and adding them to the final output
     */
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

    /*
     * The union method creates a new ordered list and adds
     * all items from the list, this process is then repeated with the
     * other multiset parameter. The resulting list is then returned
     */
    @Override
	public RmitMultiset union(RmitMultiset other) {
        RmitMultiset union = new DualLinkedListMultiset();
        
        Node currNode = head;
        
        while(currNode != null) {
        	String[] currSplitValue = currNode.value.split(":");
        	int numInstance = Integer.parseInt(currSplitValue[1]);
        	for(int i = 0; i < numInstance; i++) {
        		union.add(currSplitValue[0]);
        	}
        	currNode = currNode.next;
        }
        
        if(other instanceof DualLinkedListMultiset) {
        	
        	currNode = ((DualLinkedListMultiset) other).head;
        	
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

    /*
     * This method creates a new OrderedList then iterates though this object's
     * list. For each value, the search method is use on the other parameter. If
     * that method returns a value higher than 0, the value is added to the intersect
     * list equal to the lowest instance count
     */
    @Override
	public RmitMultiset intersect(RmitMultiset other) {

    	RmitMultiset intersect = new DualLinkedListMultiset();
    	
    	if(other instanceof DualLinkedListMultiset) {
    		
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


    /*
     * This method iterates through the list. For each value
     * , it searches through the other parameter for any matching
     * values and subtracts that instance count from it's own. This 
     * new instance count is the amount of times the value will be added
     * to the return list
     */
    @Override
	public RmitMultiset difference(RmitMultiset other) {
    	
    	RmitMultiset difference = new OrderedLinkedListMultiset();
    	
    	if(other instanceof DualLinkedListMultiset) {
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
} // end of class DualLinkedListMultiset
