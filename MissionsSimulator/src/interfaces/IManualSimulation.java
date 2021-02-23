/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import simulations.PowerUps;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface IManualSimulation extends ISimulation {
    /**
     * Getter fot the powerups.
     * @return PowerUps Details.
     */
    public PowerUps getPowerUps();
    
    /**
     * Return all the infomration about a manual simulation.
     * @return Manual Simulation Details
     */
    @Override
    public String toString();
    
}
