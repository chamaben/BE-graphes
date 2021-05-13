package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.util.*;

import org.junit.BeforeClass;
import org.junit.Test;

public class DijkstraTest{
	// Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d, f2g, e2h, e2i, e2j;
    
    // data examples
    private static ShortestPathData data1, data2, data3;
    
    // Bellman Ford
    private static BellmanFordAlgorithm bellman1, bellman2, bellman3;
    
    //Dijkstra
    private static DijkstraAlgorithm dijkstra1, dijkstra2, dijkstra3;


    @BeforeClass
    public static void initAll() throws IOException {

        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, ""),
                speed20 = new RoadInformation(RoadType.MOTORWAY, null, true, 72, ""),
                speed30 = new RoadInformation(RoadType.MOTORWAY, null, true, 100, "");
        

        // Create nodes
        nodes = new Node[11];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
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
     
        
        
        //add graph
        graph = new Graph("ID", "", Arrays.asList(nodes), null);
        
        // add data
        data1= new ShortestPathData(graph, nodes[0], nodes[0], ArcInspectorFactory.getAllFilters().get(0));
        data2= new ShortestPathData(graph, nodes[0], nodes[1], ArcInspectorFactory.getAllFilters().get(1));
        data3= new ShortestPathData(graph, nodes[0], nodes[7], ArcInspectorFactory.getAllFilters().get(2));
        
        //add Bellman Ford and Dijkstra
        bellman1 = new BellmanFordAlgorithm(data1);
        bellman2 = new BellmanFordAlgorithm(data2);
        bellman3 = new BellmanFordAlgorithm(data3);
        dijkstra1 = new DijkstraAlgorithm(data1);
        dijkstra2 = new DijkstraAlgorithm(data2);
        dijkstra3 = new DijkstraAlgorithm(data3);
        
    }
    @Test
    public void testIsValidD() {
    	assertTrue(bellman1.doRun().getPath().isValid());
    	assertTrue(bellman2.doRun().getPath().isValid());
    	assertTrue(bellman3.doRun().getPath().isValid());
    }
    
    @Test
    public void testIsValid() {
    	assertTrue(dijkstra1.doRun().getPath().isValid());
    	assertTrue(dijkstra2.doRun().getPath().isValid());
    	assertTrue(dijkstra3.doRun().getPath().isValid());
    }
    
    @Test
    public void testBellmanFord() {
        assertEquals(bellman1.doRun().getPath(), dijkstra1.doRun().getPath());
        assertEquals(bellman2.doRun().getPath(), dijkstra2.doRun().getPath());
        assertEquals(bellman3.doRun().getPath(), dijkstra3.doRun().getPath());
    }

}
