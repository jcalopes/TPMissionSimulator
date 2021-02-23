/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulations;

import interfaces.IManualSimulation;
import java.util.Iterator;
import missions.Division;

/**
 * This class store the information about a manual simulation that can be
 * performed in the context of one mission.
 */
public class ManualSimulation extends Simulation implements IManualSimulation, Comparable<IManualSimulation> {
    private PowerUps powerUps;
    
    public ManualSimulation() {
        super();
        this.powerUps=new PowerUps();
    }

    /**
     * Getter for the powerups.
     * @return PowerUps Details.
     */
    public PowerUps getPowerUps() {
        return powerUps;
    }

    
   
    /**
     * Compare with another manual simulation by their life points.
     *
     * @param o Simulation to be compared
     * @return 1 If this manual simulation has more points life than the manual
     * simulation compared.
     * @return 0 If this manual simulation has the same points life than the
     * manual simulation compared.
     * @return -1 If this manual simulation has less points life than the manual
     * simulation compared.
     */
    @Override
    public int compareTo(IManualSimulation o) {
        if (this.getRemainingLife() > o.getRemainingLife()) {
            return -1;
        } else if (this.getRemainingLife() == o.getRemainingLife()) {
            return 0;
        } else {
            return 1;
        }
    }

    /**
     * Return all the infomration about a manual simulation.
     *
     * @return Manual Simulation Details
     */
    @Override
    public String toString() {
        String info = "";
        info += "\n Vida Restante:" + this.getRemainingLife();
        info += "\n Missão Sucedida: " + this.isSuccess();
        Iterator<Division> sim = this.getPath().iterator();
        info += "\n Trajeto: \n";
        while (sim.hasNext()) {
            info += sim.next();
            if (sim.hasNext()) {
                info += " --> ";
            }
        }
        info += "-----------------------------------------------";
        return info;
    }
}
