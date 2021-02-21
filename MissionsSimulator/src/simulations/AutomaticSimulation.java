/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulations;

import interfaces.IAutomaticSimulation;
import interfaces.IDivision;
import java.util.Iterator;

/**
 * /**
 * This class store the information about an automatic simulation that can be
 * performed in the context of one mission.
 */
public class AutomaticSimulation extends Simulation implements IAutomaticSimulation {

    /**
     * Constructor for the automatic simulation.
     */
    public AutomaticSimulation() {
        super();
    }

    /**
     * Return all the information about an automatic simulation.
     *
     * @return Automatic Simulation Details.
     */
    @Override
    public String toString() {
        String info = "";
        info += "\n Vida Restante:" + this.getRemainingLife();
        info += "\n Missão Sucedida: " + this.isSuccess();
        Iterator<IDivision> sim = this.getPath().iterator();
        info += "\n Trajeto: \n";
        while (sim.hasNext()) {
            info += sim.next();
            if (sim.hasNext()) {
                info += " --> ";
            }
        }
        return info;
    }
}
