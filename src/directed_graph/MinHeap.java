package directed_graph;
import java.util.*;

/*
MINHEAP: a helpful data structure for reducing the runtime 
of the shortest path calculation. Items in the HEAP are of type 
class NODE, and they are organized by their DIST() field. 
*/


public class MinHeap
{
	private List<Node> heap;

	public MinHeap() {
		heap = new ArrayList<Node>();
	}

	/* Add NEWNODE into the heap, and immediately promote
	   the bottom element.  
	 */
	public void insert(Node newNode)
	{
		heap.add(newNode);
		PromoteBottom();
	}

	/* PromoteNode: to be called whenever NODE's priority 
	   is changed. 
	 */
	public void PromoteNode(Node node)
	{
		/* First, delete it from the list */
		for (int i = 0; i < heap.size(); i++){
			Node curr = heap.get(i);
			if (curr.GetName() == node.GetName()){
				heap.remove(i);
				break;
			}
		}
		/* Then, add it to the list, assuming 
		   it's got a new priority */
		insert(node);
	}

	/* Immediately after the TOP is popped off the heap in pop(),
	  the very end of the heap is placed at the top. It must be pushed 
	  down through the heap, and in doing so a new top will be found. 
	 */
	public void DemoteTop()
	{
		int parent = 0;
		int left_child = 2*parent + 1;
		int right_child = 2*parent + 2;
		while (left_child < heap.size()) {
			
			/* In preparation for a comparison between the parent element
			 * and the least of its two children */
			if (right_child < heap.size() && 
					heap.get(right_child).Dist() < heap.get(left_child).Dist() )
				left_child = right_child;

			/* If the parent is larger than the smallest child, swap them */
			if (heap.get(parent).Dist() > heap.get(left_child).Dist()){
				Node temp = heap.get(parent);
				heap.set(parent, heap.get(left_child));
				heap.set(left_child, temp);

				/* Move down the heap */
				parent = left_child;
				left_child = 2*parent + 1;
			}
			else 
				break;
		}
	}

	/* After appending a new item to the heap, it must be pushed up through the 
	   heap until an appropriate place is found for it. */
	public void PromoteBottom()
	{
		int child = heap.size() - 1;
		while (child > 0)
		{
			int parent = (child - 1)/2;
			if (heap.get(child).Dist() < heap.get(parent).Dist())
			{
				Node temp = heap.get(child);
				heap.set(child, heap.get(parent));
				heap.set(parent, temp);

				/* Move up the heap */
				child = parent;
			}
			else
				break;
		}
	}


	/* Returns the node with the least DIST field*/
	public Node pop()
	{
		if (heap.size() == 0) {
			System.err.printf("Error: no item in the heap to pop!");
			return null;
		}
		else if (heap.size() == 1)
			return heap.remove(0);

		/* Copy the first element*/
		Node top = heap.get(0);

		/* Replace the first element with the last element */
		heap.set(0, heap.remove(heap.size() - 1));

		/* Sift the top element down to its new place */
		DemoteTop();
		return top;
	}

	/* For debugging purposes */
	public void show()
	{
		for (int i = 0; i < heap.size(); i++)
		{
			System.out.printf("------\n");
			System.out.printf("%c %f\n", 
					heap.get(i).GetName(), heap.get(i).Dist());
		}
		    System.out.printf("------\n");
	}

	/* Public SIZE accessor */
	public int size()
	{
		return heap.size();
	}
}
