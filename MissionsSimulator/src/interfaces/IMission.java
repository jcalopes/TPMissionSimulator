/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import linkedListSentinela.UnorderedLinkedList;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface IMission {
    
    /**
     * Getter for the versions of the mission.
     * @return The versions of the mission.
     */
    public UnorderedLinkedList<IVersion> getVersions();
    
    /**
     * Start an automatic simulation with the information of the building, target and enemies
     * from a specific version.
     * @param codMission Mission to start simulation.
     * @param version Version of the mission.
     */
    public void startAutomaticSimulation(String codMission,int version);
    
    /**
     * Start a manual simulation with the information of the building, target and enemies
     * from a specific version.
     * @param codMission Mission to start simulation.
     * @param version Version of the mission.
     */
    public void startManualSimulation(String codMission,int version);
}
