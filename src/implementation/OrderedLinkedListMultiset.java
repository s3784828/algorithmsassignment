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
	private int length;
	
	public OrderedLinkedListMultiset() {
		head = null;
		length = 0;
	}

    @Override
	public void add(String item) {
    	boolean added = false;
		
		if ( head == null )
		{
			head = new Node(item);
			added = true;
		}
		else 
		{
			Node currNode = head;
			while(currNode != null && !added)
			{
				if (currNode.next == null)
				{
					currNode.next = new Node(item);
					added = true;
				}
				// checks if the value variable is before the next node alphabetically
				else if(!added && currNode.next.value.compareToIgnoreCase(item) <= 0) {
					
					Node tempNode = currNode.next;
					Node newNode = new Node(item);
					currNode.next = newNode;
					newNode.next = tempNode;
					added = true;
				}
				else
				{
					currNode = currNode.next;
				}
			}
		}
		length += 1;
    } // end of add()


    @Override
	public int search(String item) {
        int count = 0;
        Node currNode = head;
        while(currNode != null) {
        	if(currNode.value.equals(item)) {
        		count++;
        	}
        	currNode = currNode.next;
        }

        // Placeholder, please update.
        return count;
    } // end of search()


    @Override
	public List<String> searchByInstance(int instanceCount) {
    	List<String> list = new SimpleLinkList<String>();
    	Node currNode = head;
    	while(currNode != null) {
    		int count = 1;
    		//continues counting until next node's value isn't equal to it's own
    		while(currNode.value.equals(currNode.next.value)) {
    			count++;
    			currNode = currNode.next;
    		}
    		if(count == instanceCount) {
    			list.add(currNode.value);
    		}
    		currNode = currNode.next;
    	}
        
        return list;
    } // end of searchByInstance


    @Override
	public boolean contains(String item) {
        boolean doesContain = false;
    	Node currNode = head;
        
        while(currNode != null) {
        	if(currNode.value.equals(item)) {
        		doesContain = true;
        	}
        }
    	
        return doesContain;
    } // end of contains()


    @Override
	public void removeOne(String item) {
    	boolean removed = false;
    	Node currNode = head;
    	Node prevNode = null;
    	//for if the head is the item
    	if(head.value == item) {
    		head = head.next;
    		removed = true;
    	}
        while(!removed && currNode != null) {
        	if(currNode.value.equals(item)) {
        		prevNode.next = currNode.next;
        		removed = true;
        	}
        	prevNode = currNode;
        	currNode = currNode.next;
        }
        
        if(removed) {
        	length--;
        }
    } // end of removeOne()


    @Override
	public String print() {
    	String result = "";
    	Node currNode = head;
    	while(currNode != null) {
    		result += currNode.value + " ";
    		currNode = currNode.next;
    	}
        // Placeholder, please update.
        return result;
    } // end of OrderedPrint


    @Override
	public String printRange(String lower, String upper) {

        String range = "";
        //String uniqueValues[] = getUniqueValues();
        return new String();
    } // end of printRange()


    @Override
	public RmitMultiset union(RmitMultiset other) {
        RmitMultiset union = new ArrayMultiset();
        
        
        
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
    
    private class Node{
    	protected String value;
    	protected Node next;
    	
    	public Node(String _value){
    		value = _value;
    		next = null;
    	}
    }

} // end of class OrderedLinkedListMultiset
