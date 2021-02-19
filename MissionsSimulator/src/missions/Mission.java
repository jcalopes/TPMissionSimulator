/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import exceptions.NullElementValueException;
import interfaces.IMission;
import interfaces.IVersion;
import linkedListSentinela.UnorderedLinkedList;

/**
 * This class store all the information about one mission.
 */
public class Mission implements IMission, Comparable<IMission> {

    private UnorderedLinkedList<IVersion> versions;

    /**
     * Constructor for the mission.
     */
    public Mission() {

    }

    /**
     * Constructor for the mission.
     *
     * @param version Version to be added.
     * @throws NullElementValueException If the parameter is null.
     */
    public Mission(IVersion version) throws NullElementValueException {
        this.versions = new UnorderedLinkedList<>();
        this.versions.addToRear(version);
    }

    /**
     * Start an automatic simulation with the information of the building,
     * target and enemies from a specific version.
     *
     * @param codMission Mission to start simulation.
     * @param version Version of the mission.
     */
    @Override
    public void startAutomaticSimulation(String codMission, int version) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Start a manual simulation with the information of the building, target
     * and enemies from a specific version.
     *
     * @param codMission Mission to start simulation.
     * @param version Version of the mission.
     */
    @Override
    public void startManualSimulation(String codMission, int version) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int compareTo(IMission o) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * Getter for the versions of the mission.
     *
     * @return The versions of the mission.
     */
    @Override
    public UnorderedLinkedList<IVersion> getVersions() {
        return this.versions;
    }
}
