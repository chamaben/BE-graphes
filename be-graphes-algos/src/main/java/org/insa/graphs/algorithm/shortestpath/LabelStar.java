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
	
	public double getCout_Estime() {
		return this.cout_estime;
	}
	
	public void setCout_Estime(double cout_estime) {
		this.cout_estime= cout_estime;
	}
		
	final public double getTotCost() {
		this.cout_total=this.getCout_Estime()+this.getCost();
		return this.cout_total;
	}
	
	@Override
	final public int compareTo(Label other) {
		int valeur;
		if (this.getTotCost()<((LabelStar)other).getTotCost()) valeur=-1;
		else if (this.getTotCost()>((LabelStar)other).getTotCost()) valeur=1;
		else valeur=0;
		return valeur;
	}
	

}
