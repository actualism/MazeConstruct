import java.util.ArrayList;
import java.util.Iterator;

public class BinaryTree<E> {
	protected ArrayList<Position<E>> T; // list of tree positions
	
	public BinaryTree()
	{
		T = new ArrayList<Position<E>>();
		T.add(0,null); // first index must be empty
	}
	public int size()
	{
		return (T.size() - 1); // subtract one for dummy node
	}
	public boolean isEmpty()
	{
		return size() == 0;
	}
	public boolean isInternal(Position<E> v) throws InvalidPositionException
	{
		return hasLeft(v); // if it has a child, it is internal
	}
	public boolean isExternal(Position<E> v) throws InvalidPositionException
	{
		return (!isInternal(v));
	}
	public boolean isRoot(Position<E> v) throws InvalidPositionException
	{
		Position<E> tempPos = checkPosition(v);
		return tempPos.index() == 1;
	}
	public boolean hasLeft(Position<E> v) throws InvalidPositionException
	{
		Position<E> tempPos = checkPosition(v);
		return 2 * tempPos.index() <= size(); // check that 2x index is within bounds
	}
	public boolean hasRight(Position<E> v) throws InvalidPositionException
	{
		Position<E> tempPos = checkPosition(v);
		return (2 * tempPos.index() + 1) <= size(); // check within bounds
	}
	public Position<E> root() throws EmptyTreeException
	{
		if(isEmpty())
		{
			throw new EmptyTreeException("Tree is empty"); // make this
		}
		return T.get(1);
	}
	public Position<E> left(Position<E> v) throws InvalidPositionException,
	BoundaryViolationException
	{
		if(!hasLeft(v))
		{
			throw new BoundaryViolationException("No left child exists.");
		}
		Position<E> tempPos = checkPosition(v);
		return T.get(2*tempPos.index());
	}
	public Position<E> right(Position<E> v) throws InvalidPositionException,
	BoundaryViolationException
	{
		if(!hasRight(v))
		{
			throw new BoundaryViolationException("No right child exists.");
		}
		Position<E> tempPos = checkPosition(v);
		return T.get(2*tempPos.index() + 1);
	}
	public Position<E> parent(Position<E> v) throws InvalidPositionException,
	BoundaryViolationException
	{
		if(isRoot(v))
		{
			throw new BoundaryViolationException("Root has no parent.");
		}
		Position<E> tempPos = checkPosition(v);
		return T.get(tempPos.index()/2); 
	}
	public void postOrder(Position<E> v)
	{
		if(!(v==null))
		{
			try
			{
				if(hasLeft(v))
				postOrder(left(v));
				if(hasRight(v))
				postOrder(right(v));
				System.out.println("Index: " + v.index() + "; Element: " + v.element());
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
	}
	public Position<E> getPosition(E element)
	{
		for (int i = 1; i <= T.size(); i++)
		{
			if(T.get(i).element() == element)
			{
				return T.get(i);
			}
		}
		return T.get(0);
	}
	public E getElement(int index)
	{
		return T.get(index).element();
	}
	public E replace(Position<E> v, E o) throws InvalidPositionException
	{
		Position<E> tempPos = checkPosition(v);
		return tempPos.setElement(o);
	}
	public Position<E> add(E e)
	{
		int i = size() + 1;
		Position<E> p = new Position<E>(e,i); // element, index
		T.add(i,p); // index, position in arraylist
		return p;
	}
	public E remove() throws EmptyTreeException // removes last element
	{
		if(isEmpty())
		{
			throw new EmptyTreeException("Tree is empty.");
		}
		return T.remove(size()).element();
	}
	public Position<E> checkPosition(Position<E> v) throws InvalidPositionException
	{

		if(v == null || !(v instanceof Position))
		{
			throw new InvalidPositionException("Not a valid position.");
		}
		return v;
	}
	public Iterator<E> iterator()
	{
		ArrayList<E> list = new ArrayList<E>();
		Iterator<Position<E>> iter = T.iterator();
		iter.next(); // skip the first (dummy) element
		while(iter.hasNext())
		{
			list.add(iter.next().element());
		}
		return list.iterator();
	}
	public static class InvalidPositionException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public InvalidPositionException(String message)
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
	public static class BoundaryViolationException extends Exception
	{
		private static final long serialVersionUID = 1L;
		public BoundaryViolationException(String message)
		{
			super(message);
		}
	}


}
