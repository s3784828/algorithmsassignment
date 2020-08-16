package implementation;

import java.util.Iterator;
import java.util.LinkedList;



public class SimpleLinkList<Element> extends LinkedList<Element> implements Iterable<Element> {
	
	private Node head;
	private int length;
	
	public SimpleLinkList()
	{
		head = null;
		length = 0;
	}

	@Override
	public boolean add(Element value)
	{
		boolean added = false;
		
		if ( head == null )
		{
			head = new Node(value);
			added = true;
		}
		else 
		{
			Node currNode = head;
			for (int i = 0; i < length; i++)
			{
				if (currNode.next == null)
				{
					currNode.next = new Node(value);
					added = true;
				}
				else if (!added)
				{
					currNode = currNode.next;
				}
			}
		}	
		length += 1;
		return added;
	}
	
	@Override
	public String toString()
	{
		String string = "";
		Node currNode = head;
		for (int i = 0; i < length; i++)
		{
			if (currNode.value != null)
			{
				string += currNode.value + " ";
				if (currNode.next != null)
				{
					currNode = currNode.next;
				}
			}
		}
		return string;
	}
	
	@Override
	public int size()
	{
		return length;
	}
	
	public Iterator<Element> iterator()
	{
		return new SimpleListIterator<Element>(this);
	}
	
	private class SimpleListIterator<Element> implements Iterator<Element>
	{
        Node<Element> curr;
        
        public SimpleListIterator(SimpleLinkList<Element> list)
        {
        	curr = list.head;
        }
		
		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Element next() {
			Element value = curr.value;
			curr = curr.next;
			return value;
		}
	}
	
	
	private class Node<Element>
	{
		protected Element value;
		protected Node next;
		
		public Node(Element value)
		{
			this.value = value;
			next = null;
		}
	}
}
