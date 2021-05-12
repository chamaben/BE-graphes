package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;

public class AStarAlgorithm extends DijkstraAlgorithm {

    public AStarAlgorithm(ShortestPathData data) {
        super(data);
        
    }
    final public void Initialisation(Graph graph, ArrayList<Label> labels, BinaryHeap<Label> heap, ShortestPathData data) {
    	for (Node node: graph.getNodes()) {
    		LabelStar a=new LabelStar(node);
    		a.setCout_Estime(a.getSommet().getPoint().distanceTo(data.getDestination().getPoint()));
    		System.out.println("Id du label " + a.getSommet().getId());
        	labels.add(a);
        }
    	
        LabelStar labelOrigin = (LabelStar) labels.get(data.getOrigin().getId()); // origine de l'arc
        labelOrigin.setCost(0);
        heap.insert(labelOrigin);
    }

}
