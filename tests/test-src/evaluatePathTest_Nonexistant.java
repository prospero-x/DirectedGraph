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
    PATHTEST_NONEXISTANT_GRAPH1: tests that the 
    program identifies requested paths which contain 
    a nonexistant node 
*/

@RunWith(Parameterized.class)
public class evaluatePathTest_Nonexistant{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public evaluatePathTest_Nonexistant()
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
	        "Z", "ADCZ", "ZADC", "ADZC"
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
        assertEquals(true, path.BadNodeExists());
        assertEquals('Z', path.BadNode());
    }	
}
