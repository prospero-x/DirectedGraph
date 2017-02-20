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
        start = startNode;    /* Start node of the Edge */
        end = endNode;        /* End node of the Edge   */
        weight = edgeWeight;  /* Weight of the Edge     */
        link = null;          /* This will be an element 
                                 of a linked list       */
    }

    /* Public START accessors */
    public void SetStart(char newStart)
    {
        start = newStart;
    }
    public char GetStart()
    {
        return start;
    }

    /* Public END accessors */
    public void SetEnd(char newEnd)
    {
        end = newEnd;
    }
    public char GetEnd()
    {
        return end;
    }

    /* Public WEIGHT accessors  */
    public void SetWeight(int newWeight)
    {
        weight = newWeight;
    }
    public int GetWeight()
    {
        return weight;
    }

    /* Public LINK accessors */
    public void SetLink(Edge newLink)
    {
        link = newLink;
    }
    public Edge getLink()
    {
        return link;
    }

    /* For debugging purposes */
    public void Show()
    {
        System.out.println("======= Edge ======== ");
        System.out.printf("start: %c\nend: %c\nweight: %d\n", start, end, weight);
    }
}