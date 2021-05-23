package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest{
	// Small graph use for tests
    private static Graph graph, graph1, graph2;
    
    // path
    private static Path pathl, patht;
    
    // graph statistics
    private static GraphStatistics graphstatistics;
    
    // list of Points
    private static Point[] points; 

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d, f2g, e2h, e2i, e2j;
    
    // data examples
    protected static ShortestPathData data1d, data1t, data2d, data2t, data3d, data3t, data4d, data4t;
    
    // Bellman Ford
    @SuppressWarnings("unused")
	private static BellmanFordAlgorithm bellman1d, bellman1t, bellman2d, bellman2t, bellman3d, bellman3t;
    
    //Dijkstra
    @SuppressWarnings("unused")
	protected static DijkstraAlgorithm cas1d, cas2d, cas3d, cas1t, cas2t, cas3t, cas4d, cas4t;
    
    //A*
    @SuppressWarnings("unused")
	private static AStarAlgorithm astar1d, astar2d, astar3d, astar1t, astar2t, astar3t;
    


    @BeforeClass
    public static void initAll() throws IOException {
    	
    	// real maps
    	final String map_t = "C:\\Be Graphes\\Maps\\haute-garonne.mapgr";
    	final String path_t = "C:\\\\Be Graphes\\\\Maps\\path_fr31_insa_aeroport_time.path";
    	final String map_l = "C:\\Be Graphes\\Maps\\insa.mapgr";
	    final String path_l = "C:\\\\Be Graphes\\\\Maps\\path_fr31insa_rangueil_r2.path";
	    

	    final GraphReader readert = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_t))));
	    final GraphReader readerl = new BinaryGraphReader(new DataInputStream(new BufferedInputStream(new FileInputStream(map_l))));
	    final PathReader pathReaderl = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(path_l))));
	    final PathReader pathReadert = new BinaryPathReader(new DataInputStream(new BufferedInputStream(new FileInputStream(path_t))));
	   
	    

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, ""),
                speed30 = new RoadInformation(RoadType.MOTORWAY, null, true, 100, "");
        
        // Create points
        points= new Point[11];
        points[0] = new Point(222, 222);
        points[1] = new Point(235, 127);
        points[2] = new Point(200, 225);
        points[3] = new Point(229, 199);
        points[4] = new Point(192, 222);
        points[5] = new Point(290, 190);
        points[6] = new Point(222, 200);
        points[7] = new Point(217, 230);
        points[8] = new Point(197, 197);
        points[9] = new Point(201, 189);
        points[10] = new Point(130, 240);
        
        // Create nodes
        nodes = new Node[11];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, points[i]);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, speed10, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, speed10, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, speed20, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, speed10, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, speed10, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, speed20, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, speed20, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, speed10, null);
        f2g = Node.linkNodes(nodes[6], nodes[7], 10, speed30, null);
        e2h = Node.linkNodes(nodes[4], nodes[8], 35, speed10, null);
        e2i = Node.linkNodes(nodes[4], nodes[9], 45, speed20, null);
        e2j = Node.linkNodes(nodes[4], nodes[10], 10, speed30, null);
        
        //add graph statistic
        graphstatistics= new GraphStatistics(null, 0, 0, 40, 0);
        
        //add graphs
        graph = new Graph("ID", "", Arrays.asList(nodes), graphstatistics);
        graph1 = readert.read();
        graph2 = readerl.read();
        
        // add paths
        pathl = pathReaderl.readPath(graph2);
        patht = pathReadert.readPath(graph1);
        
        // add data
        data1d= new ShortestPathData(graph, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(0));
        data1t= new ShortestPathData(graph, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(2));
        data2d= new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(0));
        data2t= new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(2));
        data3d= new ShortestPathData(graph, nodes[0], nodes[7], ArcInspectorFactory.getAllFilters().get(0));
        data3t= new ShortestPathData(graph, nodes[0], nodes[7], ArcInspectorFactory.getAllFilters().get(2));
        data4d = new ShortestPathData(graph2,pathl.getOrigin(),pathl.getDestination(),ArcInspectorFactory.getAllFilters().get(0));
	    data4t = new ShortestPathData(graph1,patht.getOrigin(),patht.getDestination(),ArcInspectorFactory.getAllFilters().get(2));
        
        //add Bellman Ford, Dijkstra and A*
        bellman1d = new BellmanFordAlgorithm(data1d);
        bellman1t = new BellmanFordAlgorithm(data1t);
        bellman2d = new BellmanFordAlgorithm(data2d);
        bellman2t = new BellmanFordAlgorithm(data2t);
        bellman3d = new BellmanFordAlgorithm(data3d);
        bellman3t = new BellmanFordAlgorithm(data3t);
        
        
        
    }
    
    public void executeAlgo() {
    	cas1d = new DijkstraAlgorithm(data1d);
        cas1t = new DijkstraAlgorithm(data1t);
        cas2d = new DijkstraAlgorithm(data2d);
        cas2t = new DijkstraAlgorithm(data2t);
        cas3d = new DijkstraAlgorithm(data3d);
        cas3t = new DijkstraAlgorithm(data3t);
        cas4d = new DijkstraAlgorithm(data4d);
        cas4t = new DijkstraAlgorithm(data4t);
    }
    

    
    @Test
    public void testIsValid() {  //teste si les solutions sont valides
    	executeAlgo();
    	//distance
    	assertTrue(cas2d.doRun().getPath().isValid()); //solution possible
    	//temps
    	assertTrue(cas2t.doRun().getPath().isValid()); //solution possible
    }
    
    @Test
    public void TestInfeasable1() {
    	executeAlgo();
    	assertEquals(Status.INFEASIBLE, cas1d.doRun().getStatus());
    	assertNull(cas1d.doRun().getPath());
    	assertEquals(Status.INFEASIBLE, cas1t.doRun().getStatus());
    	assertNull(cas1t.doRun().getPath());
    	
    }
    
    @Test
    public void TestInfeasable2() {
    	executeAlgo();
    	assertEquals(Status.INFEASIBLE, cas3d.doRun().getStatus());
    	assertNull(cas3d.doRun().getPath());
    	assertEquals(Status.INFEASIBLE, cas3t.doRun().getStatus());
    	assertNull(cas3t.doRun().getPath());
    	
    }
    

	@Test
    public void testBellman() {
		executeAlgo();
    	//distance
        assertEquals(bellman2d.doRun().getPath().getArcs(), cas2d.doRun().getPath().getArcs());
        assertEquals(Status.OPTIMAL, cas2d.doRun().getStatus());
        //temps
        assertEquals(bellman2t.doRun().getPath().getArcs(), cas2d.doRun().getPath().getArcs());
        assertEquals(Status.OPTIMAL, cas2t.doRun().getStatus());
    }
	

	@Test
    public void Oracle_Length(){
		executeAlgo();
		assertEquals(pathl.getLength(), cas4d.doRun().getPath().getLength(), 1e-6);    
		assertEquals(pathl.getMinimumTravelTime(), cas4d.doRun().getPath().getMinimumTravelTime(), 1e-6);
		assertTrue(cas4d.doRun().getPath().isValid());
    }
	
	@Test
    public void Oracle_Time(){
		executeAlgo();
		assertEquals(patht.getLength(), cas4t.doRun().getPath().getLength(), 1e-6);  
		assertEquals(patht.getMinimumTravelTime(), cas4t.doRun().getPath().getMinimumTravelTime(), 1e-6);
		assertTrue(cas4t.doRun().getPath().isValid());
	}
	

}
