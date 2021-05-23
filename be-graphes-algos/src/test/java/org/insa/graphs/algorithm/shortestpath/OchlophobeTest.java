package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.*;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.BinaryPathReader;
import org.insa.graphs.model.io.GraphReader;
import org.insa.graphs.model.io.PathReader;
import org.junit.BeforeClass;
import org.junit.Test;

public class OchlophobeTest {
	// Small graph use for tests
    private static Graph graph1;
    
    // path
    private static Path path1;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2d, a2f, a2h, b2a, b2c, b2d, c2b, c2d, c2e, c2j, d2a, d2b, d2c, d2e, 
    d2f, d2g, e2c, e2d, e2g, e2j, f2a, f2d, f2g, f2h, g2d, g2e, g2f, g2h, g2i, g2j, h2a, h2f, h2g, 
    h2i, i2g, i2h, i2j, j2c, j2e, j2g, j2i;
    
    // data examples
    protected static ShortestPathData data1;
    
    // Ochlophobe code
    private static OchlophobeAlgorithm ochlophobe;
    
    @BeforeClass
    public static void initAll() throws IOException {


        // 10 and 20 meters per seconds
        RoadInformation speed10 = new RoadInformation(RoadType.MOTORWAY, null, true, 36, "");
        
        // Create nodes
        nodes = new Node[10];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 1, speed10, null);
        a2d = Node.linkNodes(nodes[0], nodes[3], 11, speed10, null);
        a2f = Node.linkNodes(nodes[0], nodes[5], 16, speed10, null);
        a2h = Node.linkNodes(nodes[0], nodes[7], 3, speed10, null);
        
        b2a = Node.linkNodes(nodes[1], nodes[0], 1, speed10, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, speed10, null);
        b2d = Node.linkNodes(nodes[1], nodes[3], 10, speed10, null);
        
        c2b = Node.linkNodes(nodes[2], nodes[1], 10, speed10, null);
        c2d = Node.linkNodes(nodes[2], nodes[3], 1, speed10, null);
        c2e = Node.linkNodes(nodes[2], nodes[4], 2, speed10, null);
        c2j = Node.linkNodes(nodes[2], nodes[9], 2, speed10, null);
        
        d2a = Node.linkNodes(nodes[3], nodes[0], 11, speed10, null);
        d2b = Node.linkNodes(nodes[3], nodes[1], 10, speed10, null);
        d2c = Node.linkNodes(nodes[3], nodes[2], 1, speed10, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 4, speed10, null);
        d2f = Node.linkNodes(nodes[3], nodes[5], 5, speed10, null);
        d2g = Node.linkNodes(nodes[3], nodes[6], 1, speed10, null);
        
        e2c = Node.linkNodes(nodes[4], nodes[2], 2, speed10, null);
        e2d = Node.linkNodes(nodes[4], nodes[3], 4, speed10, null);
        e2g = Node.linkNodes(nodes[4], nodes[6], 5, speed10, null);
        e2j = Node.linkNodes(nodes[4], nodes[9], 5, speed10, null);
        
        f2a = Node.linkNodes(nodes[5], nodes[0], 6, speed10, null);
        f2d = Node.linkNodes(nodes[5], nodes[3], 5, speed10, null);
        f2g = Node.linkNodes(nodes[5], nodes[6], 3, speed10, null);
        f2h = Node.linkNodes(nodes[5], nodes[7], 2, speed10, null);
        
        g2d = Node.linkNodes(nodes[6], nodes[3], 1, speed10, null);
        g2e = Node.linkNodes(nodes[6], nodes[4], 5, speed10, null);
        g2f = Node.linkNodes(nodes[6], nodes[5], 3, speed10, null);
        g2h = Node.linkNodes(nodes[6], nodes[7], 6, speed10, null);
        g2i = Node.linkNodes(nodes[6], nodes[8], 3, speed10, null);
        g2j = Node.linkNodes(nodes[6], nodes[9], 8, speed10, null);
        
        h2a = Node.linkNodes(nodes[7], nodes[0], 3, speed10, null);
        h2f = Node.linkNodes(nodes[7], nodes[5], 2, speed10, null);
        h2g = Node.linkNodes(nodes[7], nodes[6], 6, speed10, null);
        h2i = Node.linkNodes(nodes[7], nodes[8], 8, speed10, null);
        
        i2g = Node.linkNodes(nodes[8], nodes[6], 3, speed10, null);
        i2h = Node.linkNodes(nodes[8], nodes[7], 8, speed10, null);
        i2j = Node.linkNodes(nodes[8], nodes[9], 5, speed10, null);
        
        j2c = Node.linkNodes(nodes[9], nodes[2], 2, speed10, null);
        j2e = Node.linkNodes(nodes[9], nodes[4], 5, speed10, null);
        j2g = Node.linkNodes(nodes[9], nodes[6], 8, speed10, null);
        j2i = Node.linkNodes(nodes[9], nodes[8], 5, speed10, null);
        
        
        //add graphs
        graph1 = new Graph("ID", "", Arrays.asList(nodes), null);
        
        // add paths
        path1 = new Path(graph1, Arrays.asList(new Arc[] { a2b, b2c, c2j }));
        
        // add data
        data1= new ShortestPathData(graph1, nodes[0], nodes[9], ArcInspectorFactory.getAllFilters().get(0));
        
        //do algorithm
        ochlophobe= new OchlophobeAlgorithm(data1);

    }
    
    @Test 
    public void TestSolution() {
    	assertEquals(path1.getLength(), ochlophobe.doRun().getPath().getLength(), 1e-6);
    	assertEquals(path1.getArcs(), ochlophobe.doRun().getPath().getArcs());
    	assertEquals(13, ochlophobe.doRun().getPath().getLength(), 1e-6);
    	assertTrue(ochlophobe.doRun().getPath().isValid());
    }

}
