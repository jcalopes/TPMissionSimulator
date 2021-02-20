/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.NullElementValueException;
import linkedListSentinela.UnorderedLinkedList;
import missions.Division;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface ISimulation {
 /**
     * Getter for the route of the simulation.
     * @return The route.
     */
    public UnorderedLinkedList<IDivision> getPath();

    /**
     * Getter for the remaining life of the simulator.
     * @return Life.
     */
    public int getRemainingLife();
    
    /**
     * Setter for the life.
     * @param remainingLife Life of the simulator.
     */
    public void setRemainingLife(int remainingLife);
    
    /**
     * Check if the mission was completed with succes.
     * @return Bolean Value.
     */
    public boolean isSuccess();

    /**
     * Define if the mission was completed with success.
     * @param success Boolean value.
     */
    public void setSuccess(boolean success);
    
    /**
     * Add a new division where the simulator or user passed in the building.
     * @param div
     * @throws NullElementValueException 
     */
    public void addDivision(IDivision div) throws NullElementValueException;

    /**
     * Insert the route of the simulation where the simulator or user tested in the building.
     * @param path Path with the set of divisions.
     */
    public void setPath(UnorderedLinkedList<IDivision> path);
}
