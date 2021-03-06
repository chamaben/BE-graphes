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
        
        Initialisation(graph, labels, heap, data);
        
        //itérations
        Label x;
        double cout= 0;
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
	    			System.out.println(x.getTotCost()+data.getCost(succ));
	    			if (y.getTotCost()>x.getTotCost()+data.getCost(succ)) {
	    				if(y.getPere()!=null) heap.remove(y);
	    				y.setTotCost(x.getTotCost()+data.getCost(succ));
	    				heap.insert(y);
	    				// test validité du tas
	    				if(heap.isValid()) {
	    		        	System.out.println("Le tas est valide.");
	    		        } else {
	    		        	System.out.println("Le tas est invalide.");
	    		        }
	    				y.setPere(succ);
	    				cout= cout + y.getTotCost();  
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
        	notifyDestinationReached(data.getDestination());
        	solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));
            
            // test solution valide
            if(solution.getPath().isValid()) {
            	System.out.println("La solution est valide.");
            } else {
            	System.out.println("La solution est invalide.");
            }
            // affichage solution correcte
            System.out.println("Longueur du chemin trouvé " + solution.getPath().getLength());
        }

        
        return solution;
    }
    
    public void Initialisation(Graph graph, ArrayList<Label> labels, BinaryHeap<Label> heap, ShortestPathData data){
    	for (Node node: graph.getNodes()) {
        	labels.add(new Label(node));
        }
        Label labelOrigin = labels.get(data.getOrigin().getId()); // origine de l'arc
        labelOrigin.setCost(0);
        heap.insert(labelOrigin);
    }

}
