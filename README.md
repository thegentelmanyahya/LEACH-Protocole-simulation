# LEACH Protocol Simulation
<img width="878" alt="image" src="https://github.com/user-attachments/assets/67b89c11-69cd-4918-aa5a-b452c15e4af2">

## Project Description

This project is a simulation of a wireless sensor network (WSN) with features like communication between nodes. It includes:

- **Cluster formation**: Nodes are grouped into clusters, with each cluster having a Cluster Head (CH).
- **Energy dissipation**: The simulation calculates and tracks the energy used by each node for communication.
- **Communication**: Nodes communicate with their CH and the sink node based on a probabilistic model (using random thresholds).
- **Visualization**: The simulation includes a graphical representation of nodes and their communications with energy tracking.

## Features

- Clustering of nodes with CH selection.
- Energy dissipation calculation for each communication.
- Visualization of communication and energy usage.
- Step-wise simulation with "Start simulation" and "Next round" buttons.
- Adjustable communication probability and other parameters.

## Installation

To run this project, you need to have Java installed on your system.

1. Clone the repository:
   ```bash
   git clone https://github.com/yahya03safi/LEACH-Protocole-simulation.git
2. Navigate to the project folder:
   ```bash
   cd LEACH-Protocole-simulation
3. Compile the project (assuming you are using javac):
   ```bash
   javac -d bin src/*.java
4. Run the simulation:
   ```bash
   java -cp bin Main

## Usage

1. Launch the simulation by clicking the Start simulation button.
2. Use the Next round button to step through the rounds of communication.
3. Track energy dissipation for each node at each round.



