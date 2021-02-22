/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import exceptions.NullElementValueException;
import exceptions.VersionAlreadyExistException;
import interfaces.IMission;
import interfaces.IVersion;
import java.util.Iterator;
import linkedListSentinela.UnorderedLinkedList;

/**
 * This class store all the information about one mission.
 */
public class Mission implements IMission, Comparable<IMission> {
    private String codMission;
    private UnorderedLinkedList<IVersion> versions;

    /**
     * Constructor for the mission.
     */
    public Mission(String code) {
        this.codMission=code;
        this.versions=new UnorderedLinkedList<>();
    }

    /**
     * Constructor for the mission.
     *
     * @param version Version to be added.
     * @throws NullElementValueException If the parameter is null.
     */
    public Mission(String code,IVersion version) throws NullElementValueException {
        this.codMission=code;
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
        return (this.codMission.compareTo(o.getCodMission()));
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
     
    /**
     * Add a new version of this mission.
     * @param version Version to be added.
     * @throws NullElementValueException If the parameter is null
     * @throws VersionAlreadyExistException If the code version already exist in this mission.
     */
    @Override
    public void addVersion(IVersion version) throws NullElementValueException, VersionAlreadyExistException{
        if(version==null){
            throw new NullElementValueException("The version has null value");
        }
        
        if(this.versions.contains(version)){
            throw new VersionAlreadyExistException("This version already exist in this mission");
        }
        
        this.versions.addToRear(version);
    }

    /**
     * Getter for the code mission.
     * @return Code mission
     */
    @Override
    public String getCodMission() {
        return codMission;
    }    
    
    /**
     * Return all the information about the mission.
     * @return Mission Details
     */
    @Override
    public String toString(){
        String info="Código Missão: " +this.getCodMission();
        Iterator<IVersion> versions=this.getVersions().iterator();
        
        while(versions.hasNext()){
            info+=versions.next().toString();
        }
        return info;
    }
    
    /**
     * Check if a specific object is equal the mission.
     * @param obj Object to be compared.
     * @return 
     */
    public boolean equals(Object obj){
        if(obj!=null && obj instanceof IMission){
            IMission mission=(Mission)obj;
            if(this.getCodMission().equals(mission.getCodMission())){
                return true;
            }
        }
        return false;
    }
}
