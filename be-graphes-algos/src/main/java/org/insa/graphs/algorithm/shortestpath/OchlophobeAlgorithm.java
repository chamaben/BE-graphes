package org.insa.graphs.algorithm.shortestpath;
import java.util.ArrayList;
import java.util.Collections;

import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.*;

public class OchlophobeAlgorithm extends ShortestPathAlgorithm{

	protected OchlophobeAlgorithm(ShortestPathData data) {
		super(data);
	}

	@Override
	protected ShortestPathSolution doRun() {
		final ShortestPathData data = getInputData();
        Graph graph = data.getGraph();
        ArrayList<Label> labels = new ArrayList<Label>();
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        
        ShortestPathSolution solution = null;
        //initialisation 
        int m_nbsucc= 0;  //moyenne des nombre de successeurs des nodes
        
        for (Node node: graph.getNodes()) {
        	m_nbsucc+= node.getNumberOfSuccessors();
        	labels.add(new Label(node));
        }
        m_nbsucc= m_nbsucc/graph.size();
        int seuil= m_nbsucc + m_nbsucc*20/100; //seuil qui détermine les noeuds les plus fréquentés
        Label labelOrigin = labels.get(data.getOrigin().getId()); // origine de l'arc
        labelOrigin.setCost(0);
        heap.insert(labelOrigin);
        
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
	    				if (succ.getDestination().getNumberOfSuccessors()<=seuil) {
	    					y.setTotCost(x.getTotCost()+data.getCost(succ));
	    				} else {
	    					y.setTotCost(x.getTotCost()+data.getCost(succ)+(x.getTotCost()+data.getCost(succ))/2);
	    				}
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

}
