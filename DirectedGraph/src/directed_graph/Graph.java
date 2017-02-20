package directed_graph;
import java.util.*;
import java.lang.*;
import java.io.*;

/*
GRAPH: A doubly linked list of NODEs. 
*/

public class Graph
{
	private Node head;

	public Graph()
	{
		head = null;
	}
	public Node getHead()
	{
		return head;
	}
	public void insert(Node newNode)
	{
		newNode.SetLink(head);
		head = newNode;
	}
	public boolean isNode(char name)
	{
		Node curr_link = head;
		while (curr_link != null)
		{
			if (curr_link.GetName() == name)
				return true;
			curr_link = curr_link.GetLink();
		}
		return false;
	}
	public void loadFile(String file)
	{
	    File input = new File(file);
		Scanner Stream = null;
		try
		{
			Stream = new Scanner(input);
		}
		catch (FileNotFoundException ex)
		{
			System.err.println("Caught Exception " + ex.getMessage());
		}
		while (Stream.hasNext()){
			String next = Stream.next();
			/* Requires a comment!! */
			int weight = next.length() > 3 && next.charAt(next.length() - 1) == ',' ? 
					Integer.parseInt(next.substring(2,next.length() - 1)) :
					Integer.parseInt(next.substring(2));
			this.processEdge(next.charAt(0),
							  next.charAt(1),
							  weight);
		}
		Stream.close();
	}
	/* Will fail if NODE is not yet in the graph */
	public Node getNode(char name)
	{
		if (!isNode(name))
			return null;
		Node curr_link = head;
		while (curr_link != null && curr_link.GetName() != name)
			curr_link = curr_link.GetLink();	
		return curr_link;
	}
	/* Used for building the graph at the beginning of the program. */
	public void processEdge(char start, char end, int weight)
	{
		Edge edge1 = new Edge(start, end, weight);
		Edge edge2 = new Edge(start, end, weight);
		Node start_node;
		Node end_node;
		if( !this.isNode(start))
		{
			start_node = new Node(start);
			this.insert(start_node);
		}
		if (!this.isNode(end))
		{
			end_node = new Node(end);
			this.insert(end_node);
		}
		start_node = this.getNode(start);
		start_node.addFrom(edge1);
		end_node = this.getNode(end);
		end_node.addTo(edge2);
	}

	/* A straighforward calculation to sum the weights along an explicit PATH
	   and print the result. If PATH does not exist, the resulting message will be 
	   NO SUCH ROUTE */
	public void evaluatePath(Path path)
	{
            String path_str = path.PathString(); 
		int weight = 0;
		Node curr_node;
		char nodename = path_str.charAt(0);
		if (!isNode(nodename)){
                path.setBadNodeExists(true);
                path.setBadNode(nodename);
				return;
			}
		curr_node = this.getNode(nodename);
		Node prev_node;
		for (int i = 1; i < path_str.length(); i++)
		{
			prev_node = curr_node;
			nodename = path_str.charAt(i);
			if (!isNode(nodename)){
                path.setBadNodeExists(true);
                path.setBadNode(nodename);
				return;
			}
			
			Edge curr_link = curr_node.getFromList().getHead();
			while(curr_link != null)
			{
				if (curr_link.GetEnd() == nodename)
				{
					weight += curr_link.GetWeight();
					break;
				}
				curr_link = curr_link.getLink();
				if (curr_link == null)
				{
                    path.setExists(false);
					return;
				}
			}
			curr_node = this.getNode(nodename);
		}
                path.setWeight(weight);
                return;
	}
 
	public void showPathWeight(Path path)
        {
            if (path.BadNodeExists())
            {
                System.out.printf("Error in pathWeight(%s): node %c not in graph\n",
                                  path.PathString(), path.BadNode()); 
            } 
            else if (!path.Exists())
            { 
                System.out.printf("Error in pathWeight(%s): path does not exist\n",
				 path.PathString());
            }
            else
            {
                String message = String.format("The weight of path %s is:", 
                                  path.PathString());
                System.out.printf("%-30s %d\n", message, path.Weight());
            }
        }

	/* Builds a MINHEAP data structure in preparation for finding the shortest path */
	public MinHeap initializeHeap(char start)
	{
		MinHeap minheap = new MinHeap();
		Node curr_link = head;
		while (curr_link != null) {

			if (curr_link.GetName() == start)
				curr_link.setDist(0);
			else
				curr_link.setDist(Integer.MAX_VALUE);
			curr_link.SetPrev(null);
			minheap.insert(curr_link);
			curr_link = curr_link.GetLink();
		}
		return minheap;
	}

	/* Calculate the shortest path between start and end. START and END must 
	   be different */
	public void setShortestPath(char start, char end)
	{
		MinHeap minheap = initializeHeap(start);
		Node closest;
		while (minheap.size() > 0)
		{
			closest=minheap.pop();
			EdgeList from_list = closest.getFromList();
			int distance = closest.Dist();
			Edge curr_link = from_list.getHead();
			while (curr_link != null)
			{
				Node end_node = getNode(curr_link.GetEnd());
				int new_dist = distance + curr_link.GetWeight();
				if (new_dist < end_node.Dist())
				{	
					end_node.setDist(new_dist);
					end_node.SetPrev(closest);
					minheap.PromoteNode(end_node);
				}
				curr_link = curr_link.getLink();
			}
		}
	}


	/* Calculates the shortest path between START and END, and 
	   then walks backward from END in order to calculate the path 
	   length */
	public int ShortestPathLength(char start, char end)
	{
		setShortestPath(start, end);
		Node curr_node = getNode(end);
		int length = 0;
		while(curr_node.GetPrev() != null)
		{
			EdgeList to_list = curr_node.getToList();
			curr_node = curr_node.GetPrev();
			Edge curr_link = to_list.getHead();
			while (curr_link != null)
			{
				if (curr_link.GetStart() == curr_node.GetName())
				{
					length += curr_link.GetWeight();
					break;
				}
				curr_link = curr_link.getLink();
			}
		}
		return length;
	}


	/* Calculates the shortest path if START and END are equal. 
	   It does this by calculating the shortest path from all of 
	   START's neighboring nodes and adding that path length 
	   to the edge weight between START and that neighbor, and finally 
	   finding the minimum of all of these. */
	public int ShortestLoopLength(char start, char end)
	{
		EdgeList from_list = getNode(start).getFromList();
		int distance = Integer.MAX_VALUE;
		Edge curr_link = from_list.getHead();
		while (curr_link != null)
		{
			int alt = curr_link.GetWeight();
			int new_dist = ShortestPathLength(curr_link.GetEnd(),end) + alt;
			distance = Math.min(distance, new_dist);
			curr_link = curr_link.getLink();
		}
		return distance;
	}

	/* Recursively finds the number of paths from START to END that have between minStops
	   and maxStops, inclusive. START and END may be equal. */
	public int numPaths(char start, char end, int curr_stops, int minStops, int maxStops)
	{
		int numpaths = 0;
		boolean arrived = start == end && curr_stops > 0;

		/* Base Case (recursion stops) */
		if (curr_stops > maxStops)
			return 0;
		/* We may have arrived back at START but still wish to continue. 
		   That is why this condition is not a base case. */
		else if (arrived && curr_stops >= minStops)
			numpaths++;
		curr_stops++;
		Node node = getNode(start);
		EdgeList from_list = node.getFromList();
		Edge curr_edge = from_list.getHead();
		/* Search through all of start's neighbors, and through all of those neighbor's
		   neighbors, etc. */
		while(curr_edge != null)
		{
			char next = curr_edge.GetEnd();
			numpaths += numPaths(next, end, curr_stops, minStops, maxStops);
			curr_edge = curr_edge.getLink();
		}
		return numpaths;
	}

	/* Recursively finds the number of paths from START to END that are between 
	   minDist and maxDist, inclusivele. START and END may be equal. */
	public int numPathsDist(char start, char end, int curr_dist, int minDist, int maxDist)
	{
		int numpaths = 0;
		boolean arrived = start == end && curr_dist > 0;

		/* Base Case (recursion stops) */
		if (curr_dist > maxDist)
			return 0;
		/*
		We may have arrived back at START but still wish to continue. 
	   	That is why this conditino is not a base case. */
		else if (arrived && curr_dist >= minDist)
			numpaths++;
		Node node = getNode(start);
		EdgeList from_list = node.getFromList();
		Edge curr_edge = from_list.getHead();
		/* Search through all of start's neighbors, and through all of those neighbor's
	    neighbors, etc. */
		while(curr_edge != null)
		{
			char next = curr_edge.GetEnd();
			numpaths += numPathsDist(next, end, curr_dist + curr_edge.GetWeight(), minDist, maxDist);
			curr_edge = curr_edge.getLink();
		}
		return numpaths;
	}
	public void Show()
	{
		Node curr_link = head;
		while (curr_link != null)
		{
			System.out.println("Node Name: " + curr_link.GetName());
			curr_link.ShowToList();
			curr_link.ShowFromList();
			curr_link = curr_link.GetLink();
		}
	}

}
