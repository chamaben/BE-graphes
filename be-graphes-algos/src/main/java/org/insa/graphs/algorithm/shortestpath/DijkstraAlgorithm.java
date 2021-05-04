package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

import java.util.*;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
    }

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ArrayList<Label> labels = new ArrayList<Label>();
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        
        ShortestPathSolution solution = null;
        // TODO:
        //initialisation 
        for (Node node: graph.getNodes()) {
        	labels.add(new Label(node));
        }
        Label labelOrigin = labels.get(data.getOrigin().getId()); // origine de l'arc
        labelOrigin.setCost(0);
        heap.insert(labelOrigin);
        
        //itérations
        Label x;
        float cout= 0;
        while (!heap.isEmpty()) {
	    	x=heap.findMin();
	    	heap.remove(x);
	    	x.setMark(true);
	    	notifyNodeMarked(x.getSommet());
	    	System.out.println("Nombre de successeurs de x: "+ x.getSommet().getNumberOfSuccessors());
	    	if(x.getSommet() == data.getDestination()) break;
	    	for (Arc succ: x.getSommet().getSuccessors()) {
	    		if(!data.isAllowed(succ)) break;
	    		Label y=labels.get(succ.getDestination().getId());
	    		if (y.getMark()==false) {
	    			if (y.getCost()>x.getCost()+succ.getLength()) {
	    				if(y.getPere()!=null) heap.remove(y);
	    				y.setCost(x.getCost()+succ.getLength());
	    				heap.insert(y);
	    				y.setPere(succ);
	    				cout= cout + y.getCost();
	    				System.out.println(cout);
	    			}
	    		}
	    	}
        }
        if (labels.get(data.getDestination().getId()).getPere() == null) {
            solution = new ShortestPathSolution(data, Status.INFEASIBLE);
        }
        else {
        	
        	ArrayList<Arc> arcs = new ArrayList<>();
            Arc arc = labels.get(data.getDestination().getId()).getPere();
            while (arc != null) {
                arcs.add(arc);
                arc = labels.get(arc.getOrigin().getId()).getPere();
            }
            Collections.reverse(arcs);
            /*if (arcs.get((int)(arc.getLength()-1)).getDestination()!=data.getDestination()) {
            	solution = new ShortestPathSolution(data, Status.INFEASIBLE);
            }
            else {*/
        	notifyDestinationReached(data.getDestination());
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
            //}
        }
    
        return solution;
    }

}
