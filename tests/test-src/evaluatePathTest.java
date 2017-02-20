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
    PATHTEST_GRAPH1: tests the length a series of valid,
    existant paths for graph1 
*/

@RunWith(Parameterized.class)
public class evaluatePathTest{

	/* One static graph for all instances */
	private static Graph test_graph = null;


	public evaluatePathTest()
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
			{"ABC", 9}, {"AD", 5}, {"ADC", 13}, {"AEBCD", 22} 
		});
	}

	/* Inject the parameters into nonstatic fields */
	@Parameter
	public String pPath;

	@Parameter(1)
	public int pExpected;


	/* Run a parameterization of the test. This will get 
	   called for each element in the array returned by 
	   data().
    */ 
    @Test
    public void test(){
        Path path = new Path(pPath);
        test_graph.evaluatePath(path);
        if (!path.Exists())
        {
        	fail("Program thought path did not exist");
        }
        else if (path.BadNodeExists())
        {
        	fail("Program found a bad node on an existant path");
        }
        assertEquals(pExpected, path.Weight());
    }	
}
