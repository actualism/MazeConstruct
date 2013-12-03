public class Entry<K,V>
{
	protected K key;
	protected V value;
	
	public Entry(K k, V v)
	{
		key = k;
		value = v;
	}
	public K getKey()
	{
		return key;
	}
	public V getValue()
	{
		return value;
	}
	public String toString()
	{
		return "(" + key + " , " + value + ")";
	}
	public Long compareTo(Entry rightHead) 
	{
		return (Long)this.getKey() - (Long)rightHead.getKey();
	}
}