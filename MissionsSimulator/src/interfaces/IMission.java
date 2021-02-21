/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.NullElementValueException;
import exceptions.VersionAlreadyExistException;
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
    
    /**
     * Add a new version of this mission.
     * @param version Version to be added.
     * @throws NullElementValueException If the parameter is null
     * @throws VersionAlreadyExistException If the code version already exist in this mission.
     */
    public void addVersion(IVersion version) throws NullElementValueException, VersionAlreadyExistException;
    
      /**
     * Getter for the code mission.
     * @return Code mission
     */
    public String getCodMission();
    
    /**
     * Check if a specific object is equal the mission.
     * @param obj Object to be compared.
     * @return 
     */
    @Override
    public boolean equals(Object obj);
    
    /**
     * Return all the information about the mission.
     * @return Mission Details
     */
    @Override
    public String toString();
}
