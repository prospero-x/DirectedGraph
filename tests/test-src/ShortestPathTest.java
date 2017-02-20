import static org.junit.Assert.assertEquals;

import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import directed_graph.*;
import java.io.*;
import java.util.*;
import java.lang.*;

/*
    SHORTEST_PATH_TEST_GRAPH1: tests that the 
    program identifies requested paths which contain 
    a nonexistant node 
*/

@RunWith(Parameterized.class)
public class ShortestPathTest{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public ShortestPathTest()
	{
		/* Only load the graph once */
		if (test_graph == null)
		{
			Graph graph = new Graph();
			graph.loadFile("sample_graphs/graph1");
			test_graph = graph;
	    }
	}

    @Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
			{'A', 'B', 5, "AB"},
			{'A', 'C', 9, "ABC"},
			{'A', 'D', 5, "AD"},
			{'A', 'E', 7, "AE"},
			{'B', 'C', 4, "BC"}, /* FAILING */ 
			{'B', 'D', 12, "BCD"}, 
			{'B', 'E', 6, "BCE"},  
			{'C', 'B', 5, "CEB"}, 
			{'C', 'D', 8, "CD"}, 
			{'C', 'E', 2, "CE"}, 
			{'D', 'B', 9, "DEB"}, 
			{'D', 'C', 8, "DC"}, 
			{'D', 'E', 6, "DE"}, 
			{'E', 'B', 3, "EB"}, 
			{'E', 'C', 7, "EBC"}, 
			{'E', 'D', 15, "EBCD"},  
		});
	}
	/* Inject the parameters into nonstatic fields */
	@Parameter
	public char pStart;  /* Starting node of path */

	@Parameter(1)
	public char pEnd;  /* End node of the path */

	@Parameter(2)
	public int pExpectedDist; /* Expected path length */

	@Parameter(3)
	public String pExpectedPath; /* Expected shortest path */


	/* Run a parameterization of the test. This will get 
	   called for each element in the array returned by 
	   data().
    */ 
    @Test
    public void test(){
        Path path = test_graph.ShortestPath(pStart, pEnd);
        assertEquals(pExpectedDist, path.Weight());
        assertEquals(pExpectedPath, path.PathString());
    }	
}
