/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulations;

import interfaces.ISimulation;
import linkedListSentinela.UnorderedLinkedList;
import missions.Division;


/**
 * This class store the information about the simulation.
 * @author lopes
 */
public abstract class Simulation implements ISimulation{
    private UnorderedLinkedList<Division> path;
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
    public UnorderedLinkedList<Division> getPath() {
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
     
}
