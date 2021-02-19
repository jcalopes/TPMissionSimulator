/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package missions;

import interfaces.IMission;
import interfaces.SimulationsManagement;
import linkedListSentinela.OrderedLinkedList;

/**
 * 
 *  Nome:João Carlos Abreu Lopes 
    Número:8190221
    Turma:T1
 */
public class Missions implements SimulationsManagement {
    private OrderedLinkedList<IMission> missions;

    @Override
    public OrderedLinkedList<IMission> getMissions() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importMission(String file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String exportManualSimulations(String codMission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getAllMissionsByCode() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getManualSimulationsResults(String codMission) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
