/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import graph.WeightedAdjMatrixDiGraph;
import linkedListSentinela.OrderedLinkedList;
import missions.Division;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface IVersion {
    
     /**
     * Getter for the code version of the mission.
     * @return The version of the mission.
     */
    public int getCodVersion();
    
    /**
     * Setter for the code version of the mission.
     * @param codVersion 
     */
    public void setCodVersion(int codVersion);
    
     /**
     * Getter for the automatic simulation performed in the mission.
     * @return The automatic simulation.
     */
    public IAutomaticSimulation getAutoSimulation();
    
    /**
     * Getter for the set of the manual simulations performed int he mission.
     * @return The list of the manual simulations.
     */
    public OrderedLinkedList<IManualSimulation> getManualSimulation();
    
     /**
     * Getter for the total number of the manual simulations performed in the mission.
     * @return Number of the manual simulations.
     */
    public int getNumManualSimulations();
    
     /**
     * Getter for building representation that includes all the divisions and 
     * their conexions.
     * @return The graph of the building.
     */
    public WeightedAdjMatrixDiGraph<Division> getBuilding();
    
    /**
     * Setter for number of the manual simulations performed in the mission.
     * @param numManualSimulations Number of the manual simulations.
     */
    public void setNumManualSimulations(int numManualSimulations);
}
