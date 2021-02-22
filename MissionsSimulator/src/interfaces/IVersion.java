/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import exceptions.ElementNotFoundException;
import exceptions.NullElementValueException;
import graph.WeightedAdjMatrixDiGraph;
import linkedListSentinela.OrderedLinkedList;
import linkedListSentinela.UnorderedLinkedList;
import missions.Division;
import missions.Target;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface IVersion {

    /**
     * Getter for the code version of the mission.
     *
     * @return The version of the mission.
     */
    public int getCodVersion();

    /**
     * Setter for the code version of the mission.
     *
     * @param codVersion
     */
    public void setCodVersion(int codVersion);

    /**
     * Getter for the automatic simulation performed in the mission.
     *
     * @return The automatic simulation.
     */
    public IAutomaticSimulation getAutoSimulation();

    /**
     * Getter for the set of the manual simulations performed int he mission.
     *
     * @return The list of the manual simulations.
     */
    public OrderedLinkedList<IManualSimulation> getManualSimulation();

    /**
     * Getter for the total number of the manual simulations performed in the
     * mission.
     *
     * @return Number of the manual simulations.
     */
    public int getNumManualSimulations();

    /**
     * Getter for building representation that includes all the divisions and
     * their conexions.
     *
     * @return The graph of the building.
     */
    public WeightedAdjMatrixDiGraph<Division> getBuilding();

    /**
     * Setter for number of the manual simulations performed in the mission.
     *
     * @param numManualSimulations Number of the manual simulations.
     */
    public void setNumManualSimulations(int numManualSimulations);

    /**
     * Getter for the target of the mission.
     *
     * @return Target of the mission.
     */
    public Target getTarget();

    /**
     * Setter for the target of the mission.
     *
     * @param target Target of the mission.
     */
    public void setTarget(Target target);

    /**
     * Getter for the entries of the building.
     *
     * @return List of the entries.
     */
    public UnorderedLinkedList<Division> getEntries();

    /**
     * Setter for the entries of the building.
     *
     * @param entries Entries of the building.
     */
    public void setEntries(UnorderedLinkedList<Division> entries);

    /**
     * Getter for the exits of the building
     *
     * @return List of exits.
     */
    public UnorderedLinkedList<Division> getExits();

    /**
     * Setter for the exits of the building.
     *
     * @param exits
     */
    public void setExits(UnorderedLinkedList<Division> exits);

    /**
     * Setter for the structure of the building used for the simulations.
     *
     * @param building Graph of the building.
     */
    public void setBuilding(WeightedAdjMatrixDiGraph<Division> building);

    /**
     * Setter for automatic simulation.
     * @param autoSimulation Simulation to be added.
     */
    public void setAutoSimulation(IAutomaticSimulation autoSimulation);
    
    /**
     * Building representation with their divisions and conections.
     * @return Map 
     */
    public String printMap() throws NullElementValueException, ElementNotFoundException;
            
    /**
     * Return some details about this mission.
     *
     * @return Mission details
     */
    @Override
    public String toString();
}
