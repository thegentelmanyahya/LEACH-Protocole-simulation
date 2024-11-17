package com.myprj.wsn_1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class NodesFrame extends JFrame {

    private List<Nodes> nodesList;
    private List<Nodes> nodesList2;
    private StartSim network;
    
    private static final double MAX_ENERGY = 100.0; // Maximum energy
    private CommunicationModel communicationModel;
    private boolean hasBeenExecuted = false;
    private HistoryFrame historyFrame;
    private List<Arrow> arrowsList = new ArrayList<>();
    private boolean isSimulationRunning = false;
    


    private int currentRound = 1;
    private JButton nextRoundButton;

    public NodesFrame(StartSim net) {   // (Network net)
        this.nodesList = new ArrayList<>();
        this.nodesList2 = new ArrayList<>();
        this.network = net;
        this.communicationModel = new CommunicationModel();
        this.historyFrame = new HistoryFrame();


        setTitle("Simulation WSN with LEACH protocol");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        //ajouter les noeuds sans sink
        for (int i = 0; i < network.getNbrNoeuds(); i++) {
            Nodes node = new Nodes();
            node.setId(i + 1);
            node.setPositionX((int) (Math.random() * getWidth()));
            node.setPositionY((int) (Math.random() * getHeight()));
            node.setPositionX(Math.min(node.getPositionX(), getWidth() - 10));
            node.setPositionY(Math.min(node.getPositionY(), getHeight() - 10));
            node.setColorNode(Color.BLUE);
            node.setCurrentEnergy(MAX_ENERGY);
            nodesList.add(node);
            nodesList2.add(node);
        }
        //ajouter maintenant le sink
        Nodes sink = new Nodes();
        sink.setId(nodesList.size()+1);
        sink.setPositionX((int) (Math.random() * getWidth()));
        sink.setPositionY((int) (Math.random() * getHeight()));
        sink.setPositionX(Math.min(sink.getPositionX(), getWidth() - 10));
        sink.setPositionY(Math.min(sink.getPositionY(), getHeight() - 10));
        sink.setColorNode(Color.YELLOW);
        sink.setCurrentEnergy(MAX_ENERGY);
        nodesList.add(sink);

        JButton historyButton = new JButton("History");
        historyButton.addActionListener(new ActionListener() {
        	@Override
            public void actionPerformed(ActionEvent e) {
                historyFrame.setVisible(true);
            }
        });

        nextRoundButton = new JButton("Start Simulation");
        nextRoundButton.addActionListener(e -> {
            if (currentRound == 1) {
                simulation(); // Lance la simulation pour le premier tour
                nextRoundButton.setText("Next Round"); // Change le texte après le premier clic
            } else if (currentRound <= network.getNbrRounds()) {
                simulation(); // Lance les tours suivants
            } else {
                JOptionPane.showMessageDialog(this, "Simulation completed!", "Info", JOptionPane.INFORMATION_MESSAGE);
                nextRoundButton.setEnabled(false); // Désactive le bouton à la fin de la simulation
            }
        });


        JPanel buttonPanel = new JPanel();
        buttonPanel.add(nextRoundButton);
        buttonPanel.add(historyButton);


        JPanel drawPanel = new JPanel() {
        	
            private void drawNode(Graphics g, Nodes node) {
                int x = node.getPositionX();
                int y = node.getPositionY();
                g.setColor(node.getColorNode());
                g.fillOval(x, y, 15, 15);
                if (node.isClusterHead()){
                    g.setColor(Color.RED);
                    g.drawString(Integer.toString(node.getId())+"-CH", x - 3, y + 5);
                }else {
                g.setColor(Color.BLACK);
                g.drawString(Integer.toString(node.getId()), x - 3, y + 5);
                }
            }
       
            private void drawCommunicationRadius(Graphics g, Nodes node) {
                if (communicationModel.isClusterHeadRayon(node)) {
                    int x = node.getPositionX();
                    int y = node.getPositionY();
                    int radius = communicationModel.getRadius();

                    g.setColor(Color.BLACK);
                    g.drawOval(x - radius, y - radius, 2 * radius, 2 * radius);
                }
            }

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                for (Nodes node : nodesList) {
                    drawNode(g, node);
                    drawCommunicationRadius(g, node);
                }
                if (isSimulationRunning) {
                    for (Arrow arrow : arrowsList) {
                        arrow.draw(g);
                    }
                }
            }
        };

        setLayout(new BorderLayout());
        add(buttonPanel, BorderLayout.SOUTH);
        add(drawPanel, BorderLayout.CENTER);
    }
    
    private void simulation() {
        arrowsList.clear();
    	isSimulationRunning = true;
        communicationModel.clearClusterHeads();
        //notifier l'utilisateure du debut de la simulation
        System.out.println("Starting round " + currentRound);
        historyFrame.appendText("Starting round " + currentRound);

        //attribuer des Cluster Heads
        for (Nodes node : nodesList) {
            double threshold = calculateThreshold(node, currentRound);
            if (Math.random() < threshold && !isWithinRadiusOfExistingCH(node)) { //verifier si deux clusterHeads appartiennent au meme rayon de communication
                node.setClusterHead(true);
                node.setColorNode(Color.RED);
                communicationModel.addClusterHeadRayon(node);
                System.out.println("Node " + node.getId() + " is now a Cluster Head ");
                historyFrame.appendText("Node " + node.getId() + " is now a Cluster Head ");
            }
        }
        //affecter les nœuds au clusterHead
        communicationModel.determineClusterMembership(nodesList2);

        // Phase de communication
        communicationPhase();
        currentRound++;
         // Envoyer des messages en fonction des cas 
            //1er cas : le CH existe
           
           // S'il n'y a pas de cluster head, envoyer le message directement au sink
           
           

        repaint();
           // A la fin de la simulation
          if (currentRound==network.getNbrRounds()+1){
           System.out.println("Simulation completed.");
            historyFrame.appendText("Simulation completed.");
            isSimulationRunning = false;
            this.dispose(); 
            new StartSim().setVisible(true); 
          }
        
    }

    private boolean isWithinRadiusOfExistingCH(Nodes node) {
        int radius = communicationModel.getRadius();
        for (Nodes clusterHead : communicationModel.getClusterHeads()) {
            double distance = Math.sqrt(
                    Math.pow(node.getPositionX() - clusterHead.getPositionX(), 2) +
                            Math.pow(node.getPositionY() - clusterHead.getPositionY(), 2)
            );
            if (distance <= radius) {
                return true;
            }
        }
        return false;
    }
    //methode qui envoie le message au sink directement
    private void sendMessagesToSink(Nodes node) {
    	node.setMessage("Message from Node " + node.getId() + " to Sink");
		System.out.println(node.getMessage());
		historyFrame.appendText(node.getMessage());
		drawArrow(node.getPositionX(), node.getPositionY(), nodesList.get(nodesList.size()-1).getPositionX(), nodesList.get(nodesList.size()-1).getPositionY());
	}
    
  //methode pour les noeuds qui se trouvent en dehors des cluster et qui envoie le messge vers le sink directement
    private void sendMessagesWithoutCluster() {
        // Vérifier si la méthode a déjà été exécutée
        if (!hasBeenExecuted) {
            for (Nodes node : nodesList2) {
                if (!communicationModel.getNodesListInSomeCluster().contains(node) && !node.isClusterHead()) {
                    sendMessagesToSink(node);
                }
            }
    }
   }
  //methode pour les clusters heads qui envoient le messge vers le sink 
	private void sendClusterHeadMessagesToSink(Nodes clusterHead) {
		if(!clusterHead.getClusterMembers().isEmpty()) {
			for (Nodes clusterMember : clusterHead.getClusterMembers()) {
	            clusterHead.setMessage("Message from Cluster Head " + clusterHead.getId() + " (Recu depuis Node " + clusterMember.getId() + ") to Sink");
	            System.out.println(clusterHead.getMessage());
	            historyFrame.appendText(clusterHead.getMessage());
	    		drawArrow(clusterHead.getPositionX(), clusterHead.getPositionY(), nodesList.get(nodesList.size()-1).getPositionX(), nodesList.get(nodesList.size()-1).getPositionY());
	        }
		}
		else {
			System.out.println("Message from Cluster Head " + clusterHead.getId() + "to Sink");
			historyFrame.appendText("Message from Cluster Head " + clusterHead.getId() + "to Sink");
    		drawArrow(clusterHead.getPositionX(), clusterHead.getPositionY(), nodesList.get(nodesList.size()-1).getPositionX(), nodesList.get(nodesList.size()-1).getPositionY());
		}
        
    }
	//methode pour les noeuds qui se trouve à l'interieur des cluster et qui envoie le messge vers le cluster head
    private void sendMessagesWithinCluster(Nodes clusterHead) {
        for (Nodes clusterMember : clusterHead.getClusterMembers()) {
            clusterMember.setMessage("Message from Node " + clusterMember.getId() + " to Cluster Head " + clusterHead.getId());
            System.out.println(clusterMember.getMessage());
            historyFrame.appendText(clusterMember.getMessage());
    		drawArrow(clusterMember.getPositionX(), clusterMember.getPositionY(), clusterHead.getPositionX(), clusterHead.getPositionY());
        }
    }

    //phase de communication (on a choisi l'option de communication la plus simple 😁)
    private void communicationPhase() {
        double sendThreshold = 0.3; // Probabilité d'envoi (entre 0 et 1)

        for (Nodes clusterHead : communicationModel.getClusterHeads()) {
            // Les nœuds membres envoient leurs messages au Cluster Head
            for (Nodes clusterMember : clusterHead.getClusterMembers()) {
                if (Math.random() < sendThreshold) {
                    clusterMember.setMessage("Message from Node " + clusterMember.getId() + " to Cluster Head " + clusterHead.getId());
                    System.out.println(clusterMember.getMessage());
                    historyFrame.appendText(clusterMember.getMessage());
                    drawArrow(clusterMember.getPositionX(), clusterMember.getPositionY(), clusterHead.getPositionX(), clusterHead.getPositionY());
                } else {
                    System.out.println("Node " + clusterMember.getId() + " did not send a message this round.");
                    historyFrame.appendText("Node " + clusterMember.getId() + " did not send a message this round.");
                }
            }

            // Le Cluster Head envoie un message au Sink
            sendClusterHeadMessagesToSink(clusterHead);
        }

        // Les nœuds sans Cluster Head envoient directement au Sink
        sendMessagesWithoutCluster();
    }


    private void drawArrow(int x1, int y1, int x2, int y2) {
        arrowsList.add(new Arrow(x1, y1, x2, y2));
    }
    
    // formule de LEACH (On a opté pour la formule la plus simple 😁)
	private double calculateThreshold(Nodes node, int currentRound) {
        double p = 0.1; // Probabilité d'être Cluster Head
        if (node.isClusterHead() || node.getCurrentEnergy() <= 0) {
            return 0;
        }
        return p / (1 - p * (currentRound % (1 / p)));
    }

   /* public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> new NodesFrame(new StartSim()).setVisible(true));
    }*/
}
