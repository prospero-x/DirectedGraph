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
    NUMPATHS_TEST_INVALID_GRAPH1: Some invalid 
    input is given to the numPaths function. 
*/

@RunWith(Parameterized.class)
public class NumPathsTest_Invalid_graph1{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public NumPathsTest_Invalid_graph1()
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
			{'A', 'Z', 0, 0}, /* Nonexistant Node */
			{'A', 'B', 0, -1}, /* Negative number of Nodes */
			{'C', 'C', 3, 2}, /* Min nodes greater than max nodes */
		});
	}

	/* Inject the parameters into nonstatic fields */
	@Parameter
	public char pStart; /* Starting node */

	@Parameter(1)
	public char pEnd; /* Ending node */

	@Parameter(2) 
	public int pMinNodes; /* Min nodes on path */

	@Parameter(3)
	public int pMaxNodes; /* Max nodes on path */

	/* Run a parameterization of the test. This will get 
	   called for each element in the array returned by 
	   data().
    */ 
    @Test
    public void test(){
        int n = test_graph.numPaths(pStart, pEnd, 0, pMinNodes, pMaxNodes);
        assertEquals(0, n);
    }	
}
