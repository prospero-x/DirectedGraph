package directed_graph;

/*
EDGELIST: A linked list of Edges. 
*/

public class EdgeList
{
	private Edge head;

	public EdgeList()
	{
		head = null;
	}
	public void Show()
	{
		Edge link = head;
		while (link != null)
		{
			link.Show();
			link = link.getLink();
		}
	}

	public Edge getHead()
	{
		return head;
	}

	/* Given an edge, we want to insert that edge into the 
	   list immediately after the EDGE whose weight is strictly 
	   less than the weight of the NEWEDGE. In this way, the 
	   EdgeList is sorted; that is, organized such that the Edge 
	   weights are monotonically increasing */
	public void insert(Edge newEdge)
	{
		Edge curr_link = head;
		Edge prev_link = curr_link;
		/* If this is an uninitialized EdgeList */
		if (curr_link == null)
			head = newEdge;

		/* Else if the NEWEDGE's weight is smaller than the weight 
		   of any other edge in the list, prepend it to the entire list */ 
		else if (curr_link.GetWeight() >= newEdge.GetWeight()){
			newEdge.SetLink(curr_link);
			head = newEdge;
		}

		/* Else, find the appropriate location in the linked list for NEWEDGE */
		else{
			while (curr_link != null && curr_link.GetWeight() < newEdge.GetWeight())
			{
				prev_link = curr_link;
				curr_link = curr_link.getLink();
			}
			prev_link.SetLink(newEdge);
			newEdge.SetLink(curr_link);
		}
	}

	/* Returns true if there is an EDGE in the list 
	 * with START or END equal to NAME */
	public boolean isNode(char name, String endpoint)
	{
		Edge curr_link = head;
		while (curr_link != null){
			char curr_name;
			if (endpoint.equals("start"))
				curr_name = curr_link.GetStart();
			else
				curr_name = curr_link.GetEnd();

			if (curr_name == name) 
				return true;
			curr_link = curr_link.getLink();
		} 
		return false;
	}
}












