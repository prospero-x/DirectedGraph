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
	private int num_nodes;

	public Graph()
	{
		head = null;
		num_nodes = 0;
	}
	public Node getHead()
	{
		return head;
	}
	public void insert(Node newNode)
	{
		newNode.SetLink(head);
		head = newNode;
		num_nodes++;
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
	   be different. This is Dijkstra's Algorithm with a MinHeap. */
	public Path ShortestPath(char start, char end)
	{
		MinHeap minheap = initializeHeap(start);
		Node closest;
		int final_dist = 0;
		while (minheap.size() > 0)
		{
			closest=minheap.pop();

			if (closest.getToList().size() == 0 && closest.GetName() != start)
				continue;

			EdgeList from_list = closest.getFromList();
			int distance = closest.Dist();
			Edge curr_link = from_list.getHead();
			while (curr_link != null)
			{
				Node end_node = getNode(curr_link.GetEnd());
				int new_dist = distance + curr_link.GetWeight();

				if (new_dist < end_node.Dist())
				{	
					if (end_node.GetName() == end)
					{
						final_dist = new_dist;
					}
					end_node.setDist(new_dist);
					end_node.SetPrev(closest);
					end_node.SetPrevDist(distance);
					minheap.PromoteNode(end_node);
				}
				curr_link = curr_link.getLink();
			}
		}

		/* Now the shortest path is set in the Graph nodes. We just need to trace it. */
		Node curr_node = getNode(end);

		/* The string only needs to be as large as the number of nodes in the graph */
		StringBuilder s = new StringBuilder(num_nodes);
		while(curr_node.GetPrev() != null)
		{
			s.append(curr_node.GetName());
			curr_node = curr_node.GetPrev();
		}
		s.append(start);
		Path path = new Path(s.reverse().toString());
		path.setWeight(final_dist);
		return path;
	}

	/* Calculates the shortest path if START and END are equal. 
	   It does this by calculating the shortest path from all of 
	   START's neighboring nodes and adding that path length 
	   to the edge weight between START and that neighbor, and finally 
	   finding the minimum of all of these. */
	public Path ShortestLoop(char start, char end)
	{
		EdgeList from_list = getNode(start).getFromList();
		Edge curr_link = from_list.getHead();
		Path final_path = new Path(null);
		int final_length = Integer.MAX_VALUE;

		while (curr_link != null)
		{
			int alt = curr_link.GetWeight();
			Path path = ShortestPath(curr_link.GetEnd(), end);

			/*Choose the shortest path */
			if (path.Weight() + alt < final_length)
			{
				final_path = path;
				final_length = path.Weight() + alt;
			}
			curr_link = curr_link.getLink();
		}

		/* Finally, add the starting node to the path. */
		StringBuilder s = new StringBuilder(num_nodes);
		s.append(start);
		s.append(final_path.PathString());
		final_path.setPath(s.toString());
		final_path.setWeight(final_length);
		return final_path;
	}

	/* Recursively finds the number of paths from START to END that have between minStops
	   and maxStops, inclusive. START and END may be equal. */
	public int numPathsByStops(char start, char end, int curr_stops, int minStops, int maxStops)
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
			numpaths += numPathsByStops(next, end, curr_stops, minStops, maxStops);
			curr_edge = curr_edge.getLink();
		}
		return numpaths;
	}

	/* Recursively finds the number of paths from START to END that are between 
	   minDist and maxDist, inclusivele. START and END may be equal. */
	public int numPathsByDist(char start, char end, int curr_dist, int minDist, int maxDist)
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
			numpaths += numPathsByDist(next, end, curr_dist + curr_edge.GetWeight(), minDist, maxDist);
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
