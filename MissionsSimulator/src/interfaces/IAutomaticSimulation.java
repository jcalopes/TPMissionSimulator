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
public interface IAutomaticSimulation extends ISimulation{
     /**
     * Return all the information about an automatic simulation.
     *
     * @return Automatic Simulation Details.
     */
    @Override
    public String toString();
}
