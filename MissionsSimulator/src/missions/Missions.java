
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package missions;

import exceptions.NullElementValueException;
import interfaces.IMission;
import linkedListSentinela.OrderedLinkedList;
import interfaces.MissionsManagement;

/**
 * This class store a set of the missions.
 */
public class Missions implements MissionsManagement {
    private OrderedLinkedList<IMission> missions;

    /**
     * Constructor for missions.
     */
    public Missions(){
        this.missions=new OrderedLinkedList<>();
    }
    
    /**
     * Constructor for the mission.
     * @param mission Mission to be added.
     * @throws NullElementValueException If parameter is null.
     */
    public Missions(IMission mission) throws NullElementValueException{
        this.missions=new OrderedLinkedList<>();
        this.missions.add(mission);
    }
    
    /**
     * Getter for the missions stored.
     * @return Missions.
     */
    @Override
    public OrderedLinkedList<IMission> getMissions() {
        return this.missions;
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
