package org.insa.graphs.algorithm.shortestpath;

import java.util.ArrayList;

import org.insa.graphs.algorithm.AbstractInputData.Mode;
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
    		if (data.getMode()==Mode.LENGTH) {
    			a.setCout_Estime(a.getSommet().getPoint().distanceTo(data.getDestination().getPoint()));
    		} else {
    			a.setCout_Estime((a.getSommet().getPoint().distanceTo(data.getDestination().getPoint()))/data.getGraph().getGraphInformation().getMaximumSpeed());
    		}
    		
        	labels.add(a);
        }
    	
        LabelStar labelOrigin = (LabelStar) labels.get(data.getOrigin().getId()); // origine de l'arc
        labelOrigin.setCost(0);
        heap.insert(labelOrigin);
    }

}
