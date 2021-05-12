package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.*;

public class Label implements Comparable<Label>{
	private Node sommet_courant;
	private boolean marque;
	private double cout; 
	private Arc pere;
	
	public double getCost() {
		return this.cout;
	}
	
	public void setCost(double cost) {
		this.cout=cost;
	}
	
	public boolean getMark() {
		return this.marque;
	}
	
	public void setMark(boolean mark) {
		this.marque=mark;
	}
	
	public Node getSommet() {
		return this.sommet_courant;
	}
	
	public Arc getPere() {
		return this.pere;
	}
	
	public void setPere(Arc father) {
		this.pere=father;
	}
	
	public double getTotCost() {
		return this.cout;
	}
	
	public void setTotCost(double cout) {
		this.cout=cout;
	}
	

	
	public Label(Node init_sommet) {
		this.sommet_courant= init_sommet;
		marque=false;
		cout=Float.POSITIVE_INFINITY;
		pere=null;
	}

	@Override
	public int compareTo(Label other) {
		int valeur;
		if (this.getTotCost()<other.getTotCost()) valeur=-1;
		else if (this.getTotCost()>other.getTotCost()) valeur=1;
		else valeur=0;
		return valeur;
	}
}
