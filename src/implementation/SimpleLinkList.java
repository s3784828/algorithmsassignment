package implementation;

import java.util.ArrayList;
import java.util.LinkedList;


public class SimpleLinkList<Element> extends LinkedList<Element> {
	
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
