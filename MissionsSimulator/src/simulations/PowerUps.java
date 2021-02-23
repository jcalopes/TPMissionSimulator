/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package simulations;

/**
 * This class the bonus available in the building.
 */
public class PowerUps {
    private boolean restoreLife;//powerUp restore entire life
    private String restoreLifeDivision;//Division where the powerUp is
    private boolean recoverDamage;//powerUp recover from last damage
    private String recoverLastDamageDivision;//Division where the powerUp is
    
    public PowerUps(){
        this.restoreLife = false;
        this.recoverDamage = false;
        this.restoreLifeDivision=null;
        this.recoverLastDamageDivision=null;
    }
    
    /**
     * Check if is available the powerUp to restore entire life.
     *
     * @return true if the powerUp is available to use in the simulation.
     * @return false if the powerUp is not already available to use in the
     * simulation.
     */
    public boolean hasRestoreLife() {
        return restoreLife;
    }

    /**
     * Change the permission of use the powerUp to recover entire life.
     *
     * @param restoreLife
     */
    public void setRestoreLife(boolean restoreLife) {
        this.restoreLife = restoreLife;
    }

    /**
     * Check if is available the powerUp to recover from last damage.
     *
     * @return true if the powerUp is available to use in the simulation.
     * @return false if the powerUp is not already available to use in the
     * simulation.
     */
    public boolean hasRecoverDamage() {
        return recoverDamage;
    }

    /**
     * Change the permission of use the powerUp recover from last damage.
     *
     * @param recoverDamage
     */
    public void setRecoverDamage(boolean recoverDamage) {
        this.recoverDamage = recoverDamage;
    }

    /**
     * Define where the power up for restore entire life is.
     * @param restoreLifeDivision 
     */
    public void setRestoreLifeDivision(String restoreLifeDivision) {
        this.restoreLifeDivision = restoreLifeDivision;
    }

    /**
     * Define where the power up for restore the last damage is.
     * @param recoverLastDamageDivision
     */
    public void setRecoverLastDamageDivision(String recoverLastDamageDivision) {
        this.recoverLastDamageDivision = recoverLastDamageDivision;
    }

    /**
     *Getter for the power up restore life division. 
     * @return Division where the power up was placed
     */
    public String getRestoreLifeDivision() {
        return restoreLifeDivision;
    }

    /**
     *Getter for the power up restore last damage division. 
     * @return Division where the power up was placed
     */
    public String getRecoverLastDamageDivision() {
        return recoverLastDamageDivision;
    }
    
    @Override
    public String toString(){
        String info="\n ******PowerUps Disponiveis******";
        if(this.hasRestoreLife()){
            info+="\n Recuperar 100% Vida: ";
        }      
        if(this.hasRecoverDamage()){
            info+="\n Recuperar Último Dano: ";
        }
        if(!this.hasRecoverDamage() && !this.hasRestoreLife()){
            info+="\n Nenhum power up disponivel!";
        }
        return info;      
    }
}
