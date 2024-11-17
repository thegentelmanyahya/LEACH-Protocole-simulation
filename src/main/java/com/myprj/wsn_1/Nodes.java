package com.myprj.wsn_1;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class Nodes {

	private int id;
	private int energie;
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
	public int getEnergie() {
		return energie;
	}
	public void setEnergie(int energie) {
		this.energie = energie;
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
	


}
