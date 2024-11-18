package com.myprj.wsn_1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Nodes {

	private int id;
	private double energy; // Énergie restante en Joules
	private int rayonCommunication;
	private int positionX;
	private int positionY;
	private Color colorNode;
	private boolean isClusterHead;
    private boolean previouslyClusterHead;
    private double currentEnergy;
    private String message; 
    private List<Nodes> clusterMembers;
	
    public Nodes() {
        this.clusterMembers = new ArrayList<>();
        this.message="";
		this.energy=0.5; //energie initiale
	}
    
    
    public void addClusterMember(Nodes member) {
        clusterMembers.add(member);
    }

    public List<Nodes> getClusterMembers() {
        return clusterMembers;
    }
    
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Color getColorNode() {
		return colorNode;
	}
	public void setColorNode(Color colorNode) {
		this.colorNode = colorNode;
	}

	public boolean isClusterHead() {
		return isClusterHead;
	}
	public void setClusterHead(boolean isClusterHead) {
		this.isClusterHead = isClusterHead;
	}
	public boolean isPreviouslyClusterHead() {
		return previouslyClusterHead;
	}
	public void setPreviouslyClusterHead(boolean previouslyClusterHead) {
		this.previouslyClusterHead = previouslyClusterHead;
	}
	public double getCurrentEnergy() {
		return currentEnergy;
	}
	public void setCurrentEnergy(double currentEnergy) {
		this.currentEnergy = currentEnergy;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public double getEnergy() {
		return energy;
	}
	public void setEnergy(double energie) {
		this.energy = energie;
	}
	public int getRayonCommunication() {
		return rayonCommunication;
	}
	public void setRayonCommunication(int rayonCommunication) {
		this.rayonCommunication = rayonCommunication;
	}
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	//soustraire l'energie consommée
	public void reduceEnergy(double amount) {
		this.energy -= amount;
		if (this.energy < 0) {
			this.energy = 0; // Éviter une énergie négative
		}
	}
}
