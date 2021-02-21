/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulations;

import exceptions.NullElementValueException;
import interfaces.IDivision;
import interfaces.ISimulation;
import linkedListSentinela.UnorderedLinkedList;


/**
 * This class store the information about the simulation.
 * @author lopes
 */
public abstract class Simulation implements ISimulation{
    private int version;
    private UnorderedLinkedList<IDivision> path;
    private int remainingLife;
    private boolean success;
    private final int DEFAULT_LIFE=100;
    
    /**
     * Constructor for the simulation.
     */
    public Simulation() {
        this.path = new UnorderedLinkedList<>();
        this.remainingLife = DEFAULT_LIFE;
        this.success = false;
    }

    /**
     * Getter for the route of the simulation.
     * @return The route.
     */
    @Override
    public UnorderedLinkedList<IDivision> getPath() {
        return path;
    }

    /**
     * Getter for the remaining life of the simulator.
     * @return Life.
     */
    @Override
    public int getRemainingLife() {
        return remainingLife;
    }

    /**
     * Setter for the life.
     * @param remainingLife Life of the simulator.
     */
    @Override
    public void setRemainingLife(int remainingLife) {
        this.remainingLife = remainingLife;
    }

    /**
     * Check if the mission was completed with succes.
     * @return Bolean Value.
     */
    @Override
    public boolean isSuccess() {
        return success;
    }

    /**
     * Define if the mission was completed with success.
     * @param success Boolean value.
     */
    @Override
    public void setSuccess(boolean success) {
        this.success = success;
    }
    
    /**
     * Add a new division where the simulator or user passed in the building.
     * @param div
     * @throws NullElementValueException 
     */
    @Override
    public void addDivision(IDivision div) throws NullElementValueException{       
        this.path.addToRear(div);
    }

    /**
     * Insert the route of the simulation where the simulator or user tested in the building.
     * @param path Path with the set of divisions.
     */
    @Override
    public void setPath(UnorderedLinkedList<IDivision> path) {
        this.path = path;
    }

    /**
     * Getter for the version of this simulation.
     * @return Version
     */
    @Override
    public int getVersion() {
        return version;
    }

    /**
     * Setter for the version of this simulation.
     * @param version Version to be added.
     */
    @Override
    public void setVersion(int version) {
        this.version = version;
    }
    
    
    
        
}
