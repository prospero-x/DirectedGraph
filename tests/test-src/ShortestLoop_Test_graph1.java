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
public class ShortestLoop_Test_graph1{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public ShortestLoop_Test_graph1()
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
			{'B', 'B', 9, "BCEB"},
			{'C', 'C', 9, "CEBC"},
			{'D', 'D', 16, "DCD"},
			{'E', 'E', 9, "EBCE"}, /* FAILING */ 
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
        Path path = test_graph.ShortestLoop(pStart, pEnd);
        assertEquals(pExpectedDist, path.Weight());
        assertEquals(pExpectedPath, path.PathString());
    }	
}
