package directed_graph;
import java.util.*;
import java.lang.*;
import java.io.*;

/*
GRAPH: A doubly linked list of NODEs. 
*/

public class Graph
{
    private Node head;               /* The head of a linked list of nodes */
    private int num_nodes;           /* number of nodes */

    public Graph()
    {
        head = null;
        num_nodes = 0;
    }

    /* Public HEAD accessor */
    public Node getHead()
    {
        return head;
    }

    /* Insert a node to the linked list */
    public void insert(Node newNode)
    {
        newNode.SetLink(head);
        head = newNode;
        num_nodes++;
    }

    /* Check whether NAME has already been inserted to the graph */
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

    /* Load a graph form a plain text file (See README for 
     * details on the text file format). Calls PROCESS_EDGE
       for each character pair in the text file. */
    public void loadFile(String file)
    {
        File input = new File(file);
        Scanner stream = null;
        try
        {
            stream = new Scanner(input);
        }
        catch (FileNotFoundException ex)
        {
            System.err.println("Caught Exception " + ex.getMessage());
        }
        while (stream.hasNext())
        {
            String next = stream.next();

            /* The next() method of the Scanner class tokenizes the stream using spaces 
               for delimiters. Edges in the text file are either `AB3,` or `AB3`. Therefore, 
               next.charAt(next.length() - 1) returns ',' in the presence of commas, and 
               returns '3' in the absence of commas. */
            int weight = next.length() > 3 && next.charAt(next.length() - 1) == ',' ? 
                    Integer.parseInt(next.substring(2,next.length() - 1)) :
                    Integer.parseInt(next.substring(2));
            this.processEdge(next.charAt(0),
                              next.charAt(1),
                              weight);
        }
        stream.close();
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
        /* Create two instances: one for the To List and another 
         * for the From List */
        Edge edge1 = new Edge(start, end, weight);
        Edge edge2 = new Edge(start, end, weight);
        Node start_node;
        Node end_node;

        /* is START a new node in the graph? */
        if( !this.isNode(start))
        {
            start_node = new Node(start);
            this.insert(start_node);
        }

        /* Is END a new node in the graph? */
        if (!this.isNode(end))
        {
            end_node = new Node(end);
            this.insert(end_node);
        }

        /* Find the node corresponding to START */
        start_node = this.getNode(start);
        start_node.addFrom(edge1);

        /* Find the node corresponding to END */
        end_node = this.getNode(end);
        end_node.addTo(edge2);
    }

    /* A straighforward calculation which sets the WEIGHT field of the 
       input PATH to be the sum the weights along PATH's path string. 


       If the path does not exist the "path exists" field of PATH
       will be set to false. If one of the nodes in PATH's path string
       is not a node in the graph, the "bad_node_exists" field of PATH 
       will be set to true, and its "bad_node" field will 
       be set to the bad character.  */
    public void evaluatePath(Path path)
    {
        String path_str = path.PathString(); 
        int weight = 0;
        Node curr_node;
        char nodename = path_str.charAt(0);

        /* Proposed path contains a bad node */
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

            /* Proposed path contains a bad node */
            if (!isNode(nodename)){
                path.setBadNodeExists(true);
                path.setBadNode(nodename);
                return;
            }

            /* Iterate through the current node's 
               accessible neighbors until the next character of 
               PATH's path string is found. */
            Edge curr_link = curr_node.getFromList().getHead();
            while(curr_link != null)
            {
                if (curr_link.GetEnd() == nodename)
                {
                    weight += curr_link.GetWeight();
                    break;
                }
                curr_link = curr_link.getLink();

                /* If that character is not found within the 
                   current node's accessible neighbors, then the 
                   proposed path does not exist. */
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
    
    /* Display the path weight of a PATH object */
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

    /* Builds a MINHEAP data structure of the graph's nodes, 
       where the heap priority is the distance 
       from START. All nodes are initialized as being an infinite 
       distance from START. START itslef is given a distance of 0. */
    public MinHeap initializeHeap(char start)
    {
        MinHeap minheap = new MinHeap();
        Node graph_node = head;
        while (graph_node != null) 
        {
            /* The node corresponding with START is given an initial
               distance of 0 away from itself */
            if (graph_node.GetName() == start)
                graph_node.setDist(0);

            /* All other nodes are given an initial distance of 
               2^31 - 1 (infinity). */
            else
                graph_node.setDist(Integer.MAX_VALUE);

            /* No paths have been set yet. This ensures that ShortestPath
               is handed a "blank" graph, on which to "draw" the path one 
               edge at a time. */
            graph_node.SetPrev(null);
            minheap.insert(graph_node);
            graph_node = graph_node.GetLink();
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
            /*  The node closest to start. On first iteration, this is start itself*/
            closest=minheap.pop();

            /* The equivilent of a "dead-end" for Dijkstra's Algorithm: A node
               which has no access to other nodes (all edges point toward, zero
                edges pointing away). */
            if (closest.getToList().size() == 0 && closest.GetName() != start)
                continue;

            /* We're sitting at node CLOSEST. Three variables need to be examined:
               How far we currently are from the start node, the distance 
               to all of our neighbors, and the current value of the DISTANCE field
               of all of our neighbors.   

               Two values will be compared: 

                (1)  (our own distance from start) + (our neighbors distance from us)

                and 

                (2)  (the value of our neighbor's DISTANCE field).

                If, for a given neighbor, (1) is smaller than (2), then we have found a 
                the edge from us to our neighbor is now a primary candidate for the 
                shortest path. 
                */
            int distance = closest.Dist();


            EdgeList from_list = closest.getFromList();
            Edge curr_link = from_list.getHead();
            while (curr_link != null)
            {
                Node end_node = getNode(curr_link.GetEnd());
                int new_dist = distance + curr_link.GetWeight();

                if (new_dist < end_node.Dist())
                {   
                    /* This is a candidate for the final distance */
                    if (end_node.GetName() == end)
                        final_dist = new_dist;


                    end_node.setDist(new_dist);
                    end_node.SetPrev(closest);
                    minheap.PromoteNode(end_node);
                }
                curr_link = curr_link.getLink();
            }
        }

        /* Now the shortest path is set in the Graph nodes. We just need to trace it. */
        Node curr_node = getNode(end);

        /* The string only needs to be as large as the number of nodes in the graph */
        StringBuilder s = new StringBuilder(num_nodes);

        /* Trace the path backward starting at the end. */
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

    /* For debugging purposes */
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
