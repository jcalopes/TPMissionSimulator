/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulations;

import interfaces.IAutomaticSimulation;

/**
/**
 * This class store the information about an automatic simulation that can be performed
 * in the context of one mission.
 */
public class AutomaticSimulation extends Simulation implements IAutomaticSimulation{
   
    /**
     * Constructor for the automatic simulation.
     */
    public AutomaticSimulation(){
        super();
    }
}
