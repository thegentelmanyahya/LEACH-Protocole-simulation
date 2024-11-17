package com.myprj.wsn_1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.Color;


public class CommunicationModel {

    private int radius;
    private Set<Nodes> clusterHead;
    private List<Nodes> nodesListInSomeCluster;

	public List<Nodes> getNodesListInSomeCluster() {
		return nodesListInSomeCluster;
	}

	public void setNodesListInSomeCluster(List<Nodes> nodesListInSomeCluster) {
		this.nodesListInSomeCluster = nodesListInSomeCluster;
	}

	public void addNodesListInSomeCluster(Nodes node) {
		nodesListInSomeCluster.add(node);
	}

	public CommunicationModel() {
        this.radius = 80; // Ajustez la taille du rayon selon vos besoins
        this.clusterHead = new HashSet<>();
        this.nodesListInSomeCluster = new ArrayList<Nodes>();
    }
	//to reinitialise the clusterHeads
	public void clearClusterHeads() {
		clusterHead.clear();
	}
	public Set<Nodes> getClusterHeads() {
		return clusterHead;
	}
    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    public void addClusterHeadRayon(Nodes node) {
    	clusterHead.add(node);
    }

    public void removeClusterHeadRayon(Nodes node) {
        clusterHead.remove(node);
    }
    
    public boolean isClusterHeadRayon(Nodes node) {
        return clusterHead.contains(node);
    }

	public boolean hasClusterHead() {
		return !clusterHead.isEmpty();
	}

	public void determineClusterMembership(List<Nodes> nodesList) {
	    for (Nodes clusterHead : clusterHead) {
			Color clusterColor = generateRandomColor(); // Couleur unique pour le cluster
			clusterHead.setColorNode(clusterColor);
	        for (Nodes node : nodesList) {
	            if (!node.isClusterHead() && isNodeInCluster(node, clusterHead)) {
	                // Le nœud appartient au cluster
	                clusterHead.addClusterMember(node);
					node.setColorNode(clusterColor); // Attribuer la même couleur
	                nodesListInSomeCluster.add(node);
	            }

			}
	    }
	}
	
	private boolean isNodeInCluster(Nodes node, Nodes clusterHead) {
	    // Implémentez la logique pour déterminer si un nœud appartient à un cluster head
	    int distance = calculateDistance(node, clusterHead);
	    return distance <= radius;
	}

	private int calculateDistance(Nodes node1, Nodes node2) {
	    // Implémentez la logique pour calculer la distance entre deux nœuds
	    int dx = node1.getPositionX() - node2.getPositionX();
	    int dy = node1.getPositionY() - node2.getPositionY();
	    return (int) Math.sqrt(dx * dx + dy * dy);
	}
	private Color generateRandomColor() {
		return new Color((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
	}

	public Nodes[] getClusterHeadSet() {
	    return clusterHead.toArray(new Nodes[0]);
	}

	
}
