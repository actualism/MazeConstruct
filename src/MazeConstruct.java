import java.util.ArrayList;
import java.util.Scanner;
import java.util.Arrays;

public class MazeConstruct 
{
	private int size, x1, x2, y1, y2, start, finish;
	private int[][] verticalWeights, horizontalWeights; // 2D arrays to store weights
	private ArrayList<Edge>[] adj, mst; // array of ArrayLists containing edges
	private int[] distance;
	private PriorityQueue<Entry<Integer,Edge>,Integer> pq;
	
	@SuppressWarnings("unchecked")
	public MazeConstruct()
	{
		getInput();
		adj = new ArrayList[size*size]; // one list per vertex
		distance = new int[size*size]; // one entry per vertex
		for(int i = 0; i < size*size; i++) // populate array with empty lists
		{
			adj[i] = new ArrayList<Edge>();	
		}
		System.out.println(Arrays.deepToString(verticalWeights));
		System.out.println(Arrays.deepToString(horizontalWeights));
		makeEdges();
		System.out.println("Adj list size: " + adj.length);
		for(int i = 0; i < size*size; i++)
		{
			System.out.println("Size of list " + i + ": " + adj[i].size());
			for(int j = 0; j < adj[i].size(); j++)
			{
				// swaps vertex/terminus if necessary for vertex-first listing
				if(adj[i].get(j).getStart() != i)
				{
					int s = adj[i].get(j).getStart();
					int t = adj[i].get(j).getTerminus();
					adj[i].get(j).setStart(t);
					adj[i].get(j).setTerminus(s);
				}

				System.out.println(adj[i].get(j));
			}
		}
		primJarnik(adj);
	}
	
	public void makeEdges()
	{
		// creates edges between vertices from left to right
		for(int i = 0; i < size; i++) // for each row in the grid
		{
			for(int j = 0; j < size - 1; j++) // for each column except the last
			{
				// vertical weights (between each vertex)
				Coordinate vStart = new Coordinate(i, j);
				Coordinate vEnd = new Coordinate(i, j + 1);
				int vs = coordToVertex(vStart);
				int ve = coordToVertex(vEnd);
				int vw = verticalWeights[i][j];
				
				Edge vEdge = new Edge(vs, ve, vw);
				adj[vs].add(vEdge);
				adj[ve].add(vEdge);
				System.out.println(vEdge.toString());
			}
		}
		// creates edges between vertices from top to bottom
		for(int i = 0; i < size - 1; i++) // for each row except the last
		{
			for(int j = 0; j < size; j++) // for each column
			{
				// horizontal weights (beneath each vertex)
				Coordinate hStart = new Coordinate(i,j);
				Coordinate hEnd = new Coordinate(i + 1, j);
				int hs = coordToVertex(hStart);
				int he = coordToVertex(hEnd);
				int hw = horizontalWeights[i][j];
				
				Edge hEdge = new Edge(hs, he, hw);
				adj[hs].add(hEdge);
				adj[he].add(hEdge);
				System.out.println(hEdge.toString());
			}
		}
	}
	public void getInput()
	{
		Scanner in = new Scanner(System.in);
		String[] init = in.nextLine().trim().split("\\s+");
		size = Integer.parseInt(init[0]); // maze will be n x n, where n = init[0]
		
		int expectedInput = (size * 2) - 1; // n sets of vertical weight, n - 1 horizontal
		verticalWeights = new int[size][size - 1]; // n sets containing n - 1 entries
		horizontalWeights = new int[size - 1][size]; // n - 1 sets containing n entries
		
		// start and end coordinates
		x1 = Integer.parseInt(init[1]);
		y1 = Integer.parseInt(init[2]);
		x2 = Integer.parseInt(init[3]);
		y2 = Integer.parseInt(init[4]);
		
		Coordinate s = new Coordinate(x1, y1);
		Coordinate f = new Coordinate(x2, y2);
		start = coordToVertex(s);
		finish = coordToVertex(f);
		
		System.out.println("Starting coordinates: " + s.toString());
		System.out.println("Ending coordinates: " + f.toString());
		System.out.println("Starting vertex: " + start);
		System.out.println("Ending vertex: " + finish);
		
		int vCount = 0;
		int hCount = 0;
		for(int i = 0; i < expectedInput; i++)
		{
			if(i == 0 || i % 2 == 0) // if input is even, input is for vertical weights
			{
				System.out.println("Enter row " + vCount + " vertical weights: ");
				String[] v = in.nextLine().trim().split("\\s+");
				int[] v_Weights = new int[v.length];
				for(int j = 0; j < v.length; j++)
				{
					v_Weights[j] = Integer.parseInt(v[j]);
				}
				verticalWeights[vCount] = v_Weights;
				vCount = vCount + 1;				
			}
			else // input is for horizontal weights
			{
				System.out.println("Enter row " + hCount + " horizontal weights: ");
				String[] h = in.nextLine().trim().split("\\s+");
				int[] h_Weights = new int[h.length];
				for(int j = 0; j < h.length; j++)
				{
					h_Weights[j] = Integer.parseInt(h[j]);
				}
				horizontalWeights[hCount] = h_Weights;
				hCount = hCount + 1;
			}
		}
		
		in.close();
	}
	public int coordToVertex(Coordinate c)
	{
		return (size * c.getX() + c.getY());
	}
	public Coordinate vertexToCoord(int v)
	{
		int newX = v % size;
		int newY = (v - newX) / size;
		return new Coordinate(newX, newY);
	}

	public void primJarnik(ArrayList<Edge>[] adjList)
	{
		adjList = adj;
		setDistance();
		makeMST();
		makePQ();
		// shows postorder traversal of priority queue
		pq.postOrder();
		
		while(!pq.isEmpty())
		{
			try
			{
				Entry<Entry<Integer,Edge>,Integer> min = pq.removeMin();
				Entry<Integer,Edge> minEntry = min.getKey();
				int minVertex = minEntry.getKey();
				Edge minEdge = minEntry.getValue();
				mst[minVertex].add(minEdge);
				// for each edge incident to the minimum vertex
				for(Edge e : adj[minVertex])
				{
					int t = e.getTerminus();
					int w = e.getWeight();
					if(w < distance[t])
					{
						distance[t] = w;
						pqSearch(t);
					}
				}
			}
			catch(Exception ex)
			{
				break;
			}
		}
	}
	public void makePQ()
	{
		// initialize and populate priority queue with entries for each vertex and its distance
		pq = new PriorityQueue<Entry<Integer,Edge>,Integer>();
		for(int i = 0; i < size*size; i++)
		{
			// each edge stores starting vertex, (null) ending vertex, and the distance between
			try 
			{
				pq.insert(new Entry<Integer,Edge>(i, null), distance[i]);
				// System.out.println(distance[i]);
			} 
			catch (Exception ex) 
			{
				ex.printStackTrace();
			}
		}
	}
	@SuppressWarnings("unchecked")
	public void makeMST()
	{
		// initialize and populate minimum spanning tree with empty ArrayLists
		mst = new ArrayList[size*size];
		for(int i = 0; i < size*size; i++)
		{
			mst[i] = new ArrayList<Edge>();	
		}
	}
	public void setDistance()
	{
			// set vertex distances to "infinity"
			for(int u = 0; u < size*size; u++)
			{
				distance[u] = Integer.MAX_VALUE;
			}
			// initialize distance of starting vertex to 0;
			distance[start] = 0; 
	}
	// pqSearch doesn't work properly, an implementation using iterator is below
	public void pqSearch(int vertex)
	{
		for(int i = 1; i <= pq.size(); i++)
		{
			Entry<Entry<Integer,Edge>,Integer> firstEntry = pq.getEntry(i);
			Entry<Integer,Edge> secondEntry = pq.getEntryKey(firstEntry);
			int position = pq.getPosition(firstEntry).index();
			if(secondEntry.getKey() == vertex)
			{
				System.out.println("Position of " + secondEntry + " is " + position);
			}
		}
	}
	public void pqSearch2(int vertex)
	{
	}
	
	public static void main(String[] args)
	{
		new MazeConstruct();
	}

}
