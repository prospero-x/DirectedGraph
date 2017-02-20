import static org.junit.Assert.*;

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
    PATHTEST_Invalid_GRAPH1: test that the program 
    identifies paths which are not valid according 
    to the graph (even though the nodes specified
    might individually exist in the graph )
*/

@RunWith(Parameterized.class)
public class PathTest_Invalid_graph1{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public PathTest_Invalid_graph1()
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
	public static Object[] data() {
		return new Object[]{
			"BAE", "AEBE", "DCA", "DB" 
		};
	}

	/* Inject the parameters into nonstatic fields */
	@Parameter
	public String pPath;


	/* Run a parameterization of the test. This will get 
	   called for each element in the array returned by 
	   data().
    */ 
    @Test
    public void test(){
        Path path = new Path(pPath);
        test_graph.evaluatePath(path);
        assertEquals(false, path.Exists());
    }	
}
