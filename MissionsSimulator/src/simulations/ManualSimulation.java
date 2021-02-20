/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulations;

import interfaces.IManualSimulation;

/**
 * This class store the information about a manual simulation that can be performed
 * in the context of one mission.
 */
public class ManualSimulation extends Simulation implements IManualSimulation,Comparable<IManualSimulation>{

    private boolean restoreLife;//powerUp restore entire life
    private boolean recoverDamage;//powerUp recover from last damage
    
    public ManualSimulation(){
        super();
        this.restoreLife=true;
        this.recoverDamage=true;
    }

    /**
     * Check if is available the powerUp to restore entire life.
     * @return true if the powerUp is available to use in the simulation.
     * @return false if the powerUp is not already available to use in the simulation.
     */
    public boolean hasRestoreLife() {
        return restoreLife;
    }

    /**
     * Change the permission of use the powerUp to recover entire life.
     * @param restoreLife
     */
    public void setRestoreLife(boolean restoreLife) {
        this.restoreLife = restoreLife;
    }

    /**
     * Check if is available the powerUp to recover from last damage.
     * @return true if the powerUp is available to use in the simulation.
     * @return false if the powerUp is not already available to use in the simulation.
     */
    public boolean hasRecoverDamage() {
        return recoverDamage;
    }

    /**
     * Change the permission of use the powerUp recover from last damage.
     * @param recoverDamage
     */
    public void setRecoverDamage(boolean recoverDamage) {
        this.recoverDamage = recoverDamage;
    }

    /**
     * Compare with another manual simulation by their life points.
     * @param o Simulation to be compared
     * @return 1 If this manual simulation has more points life than the manual simulation compared. 
     * @return 0 If this manual simulation has the same points life than the manual simulation compared. 
     * @return -1 If this manual simulation has less points life than the manual simulation compared. 
     */
    @Override
    public int compareTo(IManualSimulation o) {
            if(this.getRemainingLife()>o.getRemainingLife()){
                return -1;
            }
            else if(this.getRemainingLife()==o.getRemainingLife()){
                return 0;
            }
            else{
                return 1;
            }             
    }  
}
