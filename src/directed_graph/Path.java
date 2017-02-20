package directed_graph;
import java.util.*;
import java.lang.*;

/*
    PATH class: a wrapper for an path which 
    includes fields to indicate whether the requested 
    path includes a field which indicates whether the 
    path exists, and whether the nodes specified in the 
    path are actually in the graph. 
    
*/

public class Path{
    private String path;              /* characters representing nodes */
    private int weight;               /* The weight of the path */
    private boolean path_exists;      /* Does the path exist in the graph? */
    private boolean bad_node_exists;  /* Is there a bad node in the path string? */
    private char bad_node;            /* The character in this.path not in the graph */

    public Path (String newpath)
    {
       path = newpath;
       path_exists = true;
       bad_node_exists = false;
    }

    /* Public PATH accessors */
    public void setPath(String newpath)
    {
        path = newpath;
    }
    public String PathString()
    {
        return path;
    }

    /* Public WEIGHT accessors */
    public void setWeight(int newWeight)
    {
        weight = newWeight;
    }
    public int Weight()
    {
        return weight;
    }

    /* Public EXISTS accessors */
    public void setExists(boolean b)
    {
        path_exists = b;
    }
    public boolean Exists()
    {
        return path_exists;
    }

    /* Public BAD_NODE_EXISTS accessors */
    public void setBadNodeExists(boolean b)
    {
        bad_node_exists = b;
    }
    public boolean BadNodeExists()
    {
        return bad_node_exists;
    }

    /* Public BAD_NODE accessors */
    public void setBadNode(char c)
    {
        bad_node = c;
    }
    public char BadNode()
    {
        return bad_node;
    }
}
