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
    NUMPATHS_TEST_GRAPH1: tests numPaths function
    of the Graph Class. 
*/

@RunWith(Parameterized.class)
public class NumPathsTest_graph1{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public NumPathsTest_graph1()
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
			{'C', 'C', 0, 0},
			{'C', 'C', 1, 0},
			{'C', 'C', 3, 2}, 
			{'C', 'C', 6, 10},
			{'A', 'B', 1, 1}, 
			{'A', 'B', 2, 2}, 
			{'A', 'B', 3, 3},
			{'A', 'B', 4, 5}
		});
	}

	/* Inject the parameters into nonstatic fields */
	@Parameter
	public char pStart; /* Starting node */

	@Parameter(1)
	public char pEnd; /* Ending node */

	@Parameter(2) 
	public int pMaxNodes; /* Max nodes on path */

	@Parameter(3)
	public int pExpected; /* Expected Result  */

	/* Run a parameterization of the test. This will get 
	   called for each element in the array returned by 
	   data().
    */ 
    @Test
    public void test(){
        int n = test_graph.numPaths(pStart, pEnd, 0, 0, pMaxNodes);
        assertEquals(pExpected, n);
    }	
}
