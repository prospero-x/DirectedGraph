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
    private int distance;               /* Used for calculating Shortest Path */
    private EdgeList to_list = null;    /* Edges pointing at the node  */
    private EdgeList from_list = null;  /* Edges pointing away from the node  */
    private Node prev;                  /* Used for finding Shortest Path */
    private int prev_dist;              /* Distance to previous node on Shortest Path */
    private Node link;                  /* A linked list of nodes  */               

    public Node(char node_name){
        name = node_name;
        distance = Integer.MAX_VALUE;   /* 2^31 - 1 (infinity) */
        to_list = new EdgeList();
        from_list = new EdgeList();
        link = null;
        prev = null;
    }

    /* Public NAME accessors */
    public void SetName(char newName)
    {
        name = newName;
    }
    public char GetName()
    {
        return name;
    }

    /* Public DIST accessors */
    public void setDist(int newDistance)
    {
        distance = newDistance;
    }
    public int Dist()
    {
        return distance;
    }

    /* Public TO_LIST accessors */
    public void addTo(Edge newEdge)
    {
        to_list.insert(newEdge);
    }
    public EdgeList getToList()
    {
        return to_list;
    }
    public void ShowToList()
    {
        System.out.println(name + "To:");
        to_list.Show();
    }

    /* Public FROM_LIST accessors */
    public EdgeList getFromList()
    {
        return from_list;
    }
    public void addFrom(Edge newEdge)
    {
        from_list.insert(newEdge);
    }

    public void ShowFromList()
    {
        System.out.println(name + "From:");
        from_list.Show();
    }

    /* For implementing a linked list of nodes */
    public Node GetLink()
    {
        return link;
    }
    public void SetLink(Node newLink)
    {
        link = newLink;
    }

    /* For use in Dijkstra's Algorithm */
    public Node GetPrev()
    {
        return prev;
    }
    public void SetPrev(Node newPrev)
    {
        prev = newPrev;
    }

}