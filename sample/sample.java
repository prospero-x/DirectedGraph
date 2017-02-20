import java.io.*;
import java.util.*;
import java.lang.*;
import directed_graph.*;

/*
SAMPLE: a sample program of the directed_graph program. 
*/

public class sample
{
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1){
			System.out.println("\nUsage: java Trains <input>\nwhere <input> "
					   + "is a text file containing weights of edges on a "
					   + "directed graph>\n\n");
			System.exit(-1);
		}

		/* Load the input data into the graph */
		Graph graph = new Graph();
		graph.loadFile(args[0]);


		/* Analyze the graph with functions (see sample_graphs)  */

		/* Shortest path  */

		System.out.println("The shortet path starting at A and ending at C is: "
			               + graph.ShortestPath('A', 'C').Weight());
		System.out.println("The shortest path starting at D and ending at D is: "
			                + graph.ShortestLoop('D', 'D').Weight());

		/* Find the total path weight of path AEBCD */
		Path path1 = new Path("AEBCD");
		graph.evaluatePath(path1);
		graph.showPathWeight(path1);

		/* Find the number of paths given a number of stops */
		System.out.println("The number of paths starting at C and ending at C with "
			+ "AT MOST 3 stops is " + graph.numPathsByStops('C', 'C', 0, 0, 3));

		System.out.println("The number of paths starting at A and ending at C with "
			+ "EXACTLY 4 stops is " + graph.numPathsByStops('A', 'C', 0, 4, 4));

		/* Find the number of paths given a distance */
		System.out.println("The number of paths starting at C and ending at C with "
			+ "a distance of AT MOST 30 is " + graph.numPathsByDist('C', 'C', 0, 0, 29));
	}
}
