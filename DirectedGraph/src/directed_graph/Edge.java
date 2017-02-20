package directed_graph;

/*
An EDGE of a graph. Embedded in the data structure
is a pointer to the next item in the eventual linked list of EDGES.
*/

public class Edge{
	private char start;
	private char end;
	private int weight;
	private Edge link;

	public Edge(char startNode, char endNode, int edgeWeight)
	{
		start = startNode;
		end = endNode;
		weight = edgeWeight;
		link = null;
	}
	public void SetStart(char newStart)
	{
		start = newStart;
	}
	public char GetStart()
	{
		return start;
	}
	public void SetEnd(char newEnd)
	{
		end = newEnd;
	}
	public char GetEnd()
	{
		return end;
	}
	public void SetWeight(int newWeight)
	{
		weight = newWeight;
	}
	public int GetWeight()
	{
		return weight;
	}
	public void SetLink(Edge newLink)
	{
		link = newLink;
	}
	public Edge getLink()
	{
		return link;
	}
	public void Show()
	{
		System.out.println("======= Edge ======== ");
		System.out.printf("start: %c\nend: %c\nweight: %d\n", start, end, weight);
	}
}