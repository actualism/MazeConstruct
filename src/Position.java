public class Position<E>
{
	E element; // element stored in this position
	int index; // index of this position in ArrayList
	public Position(E e, int i)
	{
		element = e;
		index = i;	
	}
	public E element()
	{
		return element;
	}
	public int index()
	{
		return index;
	}
	public E setElement(E e)
	{
		E temp = element;
		element = e;
		return temp;
	}
}