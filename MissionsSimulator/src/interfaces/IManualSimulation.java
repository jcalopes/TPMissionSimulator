/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface IManualSimulation extends ISimulation {
       
    /**
     * Return all the infomration about a manual simulation.
     * @return Manual Simulation Details
     */
    @Override
    public String toString();
    
     /**
     * Change the permission of use the powerUp recover from last damage.
     * @param recoverDamage
     */
    public void setRecoverDamage(boolean recoverDamage);
    
      /**
     * Check if is available the powerUp to recover from last damage.
     * @return true if the powerUp is available to use in the simulation.
     * @return false if the powerUp is not already available to use in the simulation.
     */
    public boolean hasRecoverDamage();
    
     /**
     * Change the permission of use the powerUp to recover entire life.
     * @param restoreLife
     */
    public void setRestoreLife(boolean restoreLife);
    
    /**
     * Check if is available the powerUp to restore entire life.
     * @return true if the powerUp is available to use in the simulation.
     * @return false if the powerUp is not already available to use in the simulation.
     */
    public boolean hasRestoreLife();
    
    
}
