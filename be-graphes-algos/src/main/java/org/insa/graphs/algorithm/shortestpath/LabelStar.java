package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.model.*;

public class LabelStar extends Label{
	
	private double cout_estime;
	private double cout_total;

	public LabelStar(Node init_sommet) {
		super(init_sommet);
		cout_estime=0;
		cout_total=cout_estime+this.getCost();
		
	}
	
	public double GetHeuristicCost() {
		this.cout_total=this.getCost()+this.cout_estime;
		//System.out.println(" cout " + this.getCost() + " cout estime " + this.GetCoutEstime());
		return this.cout_total;
	}
	
	public void setCout_Estime(double cout) {
		this.cout_estime=cout;
	}
	
	@Override
	public int compareTo(Label other) {
		// TODO Auto-generated  method stub
	       int valeur;
	       if (this.GetHeuristicCost()<((LabelStar) other).GetHeuristicCost()) valeur=-1;
	       else if (this.GetHeuristicCost()>((LabelStar) other).GetHeuristicCost()) valeur=1;
	       else valeur=0;
	       return valeur;
	}
	

}
