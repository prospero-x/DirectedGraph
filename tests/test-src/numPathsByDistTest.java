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
    NUMPATHS_BY_DIST TEST: tests numPathsByStops function
    of the Graph Class. 
*/

@RunWith(Parameterized.class)
public class numPathsByDistTest{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public numPathsByDistTest()
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
			{'C', 'C', 0, 10, 1},
			{'C', 'C', 0, 29, 7},
			{'C', 'C', 0, 15, 1},
			{'C', 'C', 0, 22, 4},
			{'A', 'B', 29, 30, 2},
			{'A', 'B', 22, 30, 7},
			{'A', 'B', 17, 30, 9},
			{'A', 'B', 12, 30, 11},
			{'A', 'B', 5, 30, 13},
		});
	}

	/* Inject the parameters into nonstatic fields */
	@Parameter
	public char pStart; /* Starting node */

	@Parameter(1)
	public char pEnd; /* Ending node */

	@Parameter(2)
	public int pMinDist; /* Min distance of paths */

	@Parameter(3) 
	public int pMaxDist; /* Max distance of paths */

	@Parameter(4)
	public int pExpected; /* Expected number of paths  */


	/* Run a parameterization of the test. This will get 
	   called for each element in the array returned by 
	   data().
    */ 
    @Test
    public void test(){
        int n = test_graph.numPathsByDist(pStart, pEnd, 0, pMinDist, pMaxDist);
        assertEquals(pExpected, n);
    }	
}
