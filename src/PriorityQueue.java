import java.util.ArrayList;

public class PriorityQueue<K,V> {
	protected BinaryTree<Entry<K,V>> heap; // underlying heap for the priority queue
	private Entry<K,V> min;
	

	public PriorityQueue() // default constructor
	{
		heap = new BinaryTree<Entry<K,V>>();
	}
	public int size()
	{
		return heap.size();
	}
	public boolean isEmpty()
	{
		return heap.size() == 0;
	}
	public Entry<K,V> min() throws EmptyQueueException, BinaryTree.EmptyTreeException
	{
		if(isEmpty())
		{
			throw new EmptyQueueException("Queue is empty.");
		}
		return heap.root().element();
	}
	public Entry<K,V> insert(K k, V x) throws InvalidKeyException
	{
		Entry<K,V> entry = new Entry<K,V>(k,x);
		upHeap(heap.add(entry));
		return entry;
	}
	public Entry<K,V> removeMin() throws EmptyQueueException
	{
		try{
			if(isEmpty())
			{
				throw new EmptyQueueException("Queue is empty.");
			}
			min = heap.root().element(); // root is min
			if(size() == 1) // heap only contains root
			{
				heap.remove();
			}
			else
			{
				heap.replace(heap.root(), heap.remove()); // copy last node to root
				downHeap(heap.root()); // restore heap order
			}
			return min;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		return min;
	}
	public void postOrder()
	{
		try
		{
			heap.postOrder(heap.root());
			// System.out.println("Entry is: " + myEntry);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	// these three may not work properly
	public Position<Entry<K,V>> getPosition(Entry<K,V> myEntry)
	{
		return heap.getPosition(myEntry);
	}
	public Entry<K,V> getEntry(int index)
	{
		return heap.getElement(index);
	}
	public K getEntryKey(Entry<K,V> myEntry)
	{
		return myEntry.getKey();
	}

	protected void upHeap(Position<Entry<K,V>> v)
	{
		Position<Entry<K,V>> u; // starting position
		try
		{
			while(!heap.isRoot(v)) // if v is root, can't upheap
			{
				u = heap.parent(v);
				if((Integer)u.element().getValue() - (Integer)v.element().getValue() <= 0)
				{
					break; // already in heap order since v is larger
				}
				swap(u,v);
				v = u; // move up one level
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	protected void downHeap(Position<Entry<K,V>> r) throws BinaryTree.InvalidPositionException, BoundaryViolationException, InvalidPositionException
	{
		while(heap.isInternal(r)) // can't downheap leaf nodes
		{
			Position<Entry<K,V>> s; // position of smaller child

			try
			{
				if(!heap.hasRight(r))
				{
					s = heap.left(r);
				}
				else if((Integer)heap.left(r).element().getValue() - 
						(Integer) heap.right(r).element().getValue() <=0)
				{
					s = heap.left(r); // left is smaller
				}
				else
				{
					s = heap.right(r); // right is smaller
				}
				// if parent is larger than smallest child, swap them
				if((Integer)s.element().getValue() - (Integer)r.element().getValue() < 0)
				{
					swap(r,s);
					r = s; // move down one level
				}
				else
				{
					break;
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	protected void swap(Position<Entry<K,V>> x, Position<Entry<K,V>> y) throws InvalidPositionException
	{
		// swaps elements of x and y, not positions
		Entry<K,V> temp = x.element();
		try 
		{
			heap.replace(x, y.element());
			heap.replace(y, temp);
		} catch (BinaryTree.InvalidPositionException e) {
			e.printStackTrace();
		}
		
	}
	public String toString()
	{
		return heap.toString();
	}

	public static class EmptyQueueException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public EmptyQueueException(String message)
		{
			super(message);
		}
	}
	public static class EmptyTreeException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public EmptyTreeException(String message)
		{
			super(message);
		}
	}
	public static class InvalidKeyException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public InvalidKeyException(String message)
		{
			super(message);
		}
	}
	public static class InvalidPositionException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public InvalidPositionException(String message)
		{
			super(message);
		}
	}
	public static class BoundaryViolationException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public BoundaryViolationException(String message)
		{
			super(message);
		}
	}
}
