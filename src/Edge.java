/* Edges will be stored in an adjacency list structure.
 * Each entry in the adjacency array will be a list corresponding with the starting vertex.
 * Each entry in each vertex list will contain an edge, defined by the terminus and weight.
 */
public class Edge
{
	protected int start;
	protected int terminus;
	protected int weight;
	
	public Edge(int s, int t, int w) // start, terminus, weight
	{
		start = s;
		terminus = t;
		weight = w;
	}
	public int getStart()
	{
		return start;
	}
	public int getTerminus()
	{
		return terminus;
	}
	public int getWeight()
	{
		return weight;
	}
	public void setStart(int s)
	{
		start = s;
	}
	public void setTerminus(int t)
	{
		terminus = t;
	}
	public void setWeight(int w)
	{
		weight = w;
	}
	public String toString()
	{
		return ("Edge from " + start + " to " + terminus + " has weight: " + weight);
	}
}