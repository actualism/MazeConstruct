public class Coordinate
{
	protected int x;
	protected int y;
	
	public Coordinate(int _x, int _y)
	{
		x = _x;
		y = _y;
	}
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public void setX(int _x)
	{
		x = _x;
	}
	public void setY(int _y)
	{
		y = _y;
	}
	public String toString()
	{
		return new String("(" + x + "," + y + ")");
	}
}