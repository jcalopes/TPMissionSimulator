/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import linkedListSentinela.OrderedLinkedList;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface MissionsManagement {
    /**
     * Getter for the missions stored.
     * @return Missions
     */
    public OrderedLinkedList<IMission> getMissions();
    
    /**
     * Import from the json file, one version of the mission.
     * @param file File path to load the mission.
     */
    public void importMission(String file);
    
    /**
     * Create a json file with all manual simulations from a specific mission. 
     * @param codMission Mission to export all manual simulations.
     * @return String with all information from json file .
     */
    public String exportManualSimulations(String codMission);
    
    /**
     * Getter for all missions stored ordered by code mission.
     * @return All information about the missions stored.
     */
    public String getAllMissionsByCode();
    
    /**
     * Getter for all manual simulations from a specific mission, ordered by 
     * remaining life.
     * @param codMission Mission to export the results of manual simulations.
     * @return Results of manual simulations.
     */
    public String getManualSimulationsResults(String codMission);
      
}
