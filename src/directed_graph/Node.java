package directed_graph;
import java.lang.*;

/*
NODE: a data structure containing all relevant information about 
a node. A NODE contains two EDGELISTS: a list representing 
the NODES accessible from itself (the FROMLIST), and all the nodes
which can access it (the TOLIST). 
*/

public class Node
{
	private char name;
	private int distance;				/* Used for calculating Shortest Path */
	private EdgeList to_list = null; 			/* Used for finding Shortest Path */
	private EdgeList from_list = null; 		
	private Node link;					
	private Node prev;					/* Used for finding Shortest Path */
	private int prev_dist;              /* Distance to previous node on Shortest Path */

	public Node(char node_name){
		name = node_name;
		distance = Integer.MAX_VALUE;
		to_list = new EdgeList();
		from_list = new EdgeList();
		link = null;
		prev = null;
	}
	public void SetName(char newName)
	{
		name = newName;
	}
	public char GetName()
	{
		return name;
	}
	public void setDist(int newDistance)
	{
		distance = newDistance;
	}
	public int Dist()
	{
		return distance;
	}
	public void addTo(Edge newEdge)
	{
		to_list.insert(newEdge);
	}
	public void addFrom(Edge newEdge)
	{
		from_list.insert(newEdge);
	}
	public EdgeList getToList()
	{
		return to_list;
	}
	public EdgeList getFromList()
	{
		return from_list;
	}
	public void ShowToList()
	{
		System.out.println(name + "To:");
		to_list.Show();
	}
	public void ShowFromList()
	{
		System.out.println(name + "From:");
		from_list.Show();
	}
	public Node GetLink()
	{
		return link;
	}
	public void SetLink(Node newLink)
	{
		link = newLink;
	}
	public Node GetPrev()
	{
		return prev;
	}
	public void SetPrev(Node newPrev)
	{
		prev = newPrev;
	}
	public int GetPrevDist()
	{
		return prev_dist;
	}
	public void SetPrevDist(int newDist)
	{
		prev_dist = newDist;
	}
}