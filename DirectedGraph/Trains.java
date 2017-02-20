import java.io.*;
import java.util.*;
import java.lang.*;
import directed_graph.*;



public class Trains
{
	public static void main(String[] args) throws FileNotFoundException {
		if (args.length != 1){
			System.out.println("\nUsage: java Trains <input>\nwhere <input> "
					   + "is a text file containing weights of edges on a "
					   + "directed graph>\n\n");
			System.exit(-1);
		}
		File input = new File(args[0]);
		Scanner Stream = null;
		try
		{
			Stream = new Scanner(input);
		}
		catch (FileNotFoundException ex)
		{
			System.err.println("Caught Exception " + ex.getMessage());
		}
		Graph graph = new Graph();
		while (Stream.hasNext()){
			String next = Stream.next();
			int weight = next.length() > 3 && next.charAt(next.length() - 1) == ',' ? 
					Integer.parseInt(next.substring(2,next.length() - 1)) :
					Integer.parseInt(next.substring(2));
			graph.processEdge(next.charAt(0),
							  next.charAt(1),
							  weight);
		}
		Stream.close();
      	
		System.out.println(graph.numPaths('A', 'B', 0, 0, 1));
	}
}
