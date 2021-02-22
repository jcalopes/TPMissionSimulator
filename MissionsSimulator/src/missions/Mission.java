/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import exceptions.ElementNotFoundException;
import exceptions.InvalidOperationException;
import exceptions.NoPathAvailableException;
import exceptions.NullElementValueException;
import exceptions.VersionAlreadyExistException;
import heap.LinkedHeap;
import interfaces.IAutomaticSimulation;
import interfaces.IMission;
import interfaces.IVersion;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import linkedListSentinela.UnorderedLinkedList;
import simulations.AutomaticSimulation;
import simulations.Path;

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
     * Find the shortest paths between a set of divisions and one specific target.
     * Save in LinkedHeap in order to find the shortest path between a 
     * set of entries and the target.
     * @return LinkedHeap
     */
    private LinkedHeap<Path> findShortestPathsEntriesTarget(IVersion version, Iterator<Division> divisions, Division target)
            throws NullElementValueException, ElementNotFoundException {
        LinkedHeap<Path> entriesTarget = new LinkedHeap<>();
        while (divisions.hasNext()) {
            Division currentEntry = divisions.next();
            Path current = null;
            try {
                current = new Path(version.getBuilding().shortestPathWeight(currentEntry, target),
                        currentEntry.getTotalDamage()
                        + (int) version.getBuilding().shortestPathWeightCost(currentEntry, target));
            } catch (NoPathAvailableException ex) {}
            entriesTarget.addElement(current);

        }
        return entriesTarget;
    }
    
    /**
     * Find the shortest paths between the target and a set of exits.
     * Save in LinkedHeap in order to find the shortest path between a 
     * target and a specific target.
     * @return LinkedHeap
     */
    private LinkedHeap<Path> findShortestPathsTargetExits(IVersion version, Iterator<Division> availableExits, Division target)
            throws NullElementValueException, ElementNotFoundException {
        LinkedHeap<Path> targetExits = new LinkedHeap<>();
        while (availableExits.hasNext()) {
            Division currentExit = availableExits.next();
            Path current = null;
            try {
                current = new Path(version.getBuilding().shortestPathWeight(target, currentExit),
                       (int) version.getBuilding().shortestPathWeightCost(target, currentExit));
            } catch (NoPathAvailableException ex) {
            }
            targetExits.addElement(current);
        }
        return targetExits;
    }

    /**
     * Start an automatic simulation with the information of the building,
     * target and enemies from a specific version.
     *
     * @param codMission Mission to start simulation.
     * @param codVersion Version of the mission.
     */
    @Override
    public void startAutomaticSimulation(int codVersion)
            throws ElementNotFoundException, NullElementValueException {
        IVersion version = new Version();
        version.setCodVersion(codVersion);
        version = this.getVersions().getElement(version);
        Division target=new Division(version.getTarget().getDivision());
        
        IAutomaticSimulation sim = new AutomaticSimulation();
        
        //Paths between entries and target 
        Iterator<Division> availableEntries = version.getEntries().iterator();
        LinkedHeap<Path> entriesTarget = this.findShortestPathsEntriesTarget(version, availableEntries, target);
        
        //Paths between target and exits 
        Iterator<Division> availableExits = version.getExits().iterator();
        LinkedHeap<Path> targetExits = this.findShortestPathsTargetExits(version, availableExits, target);;
        
        //Path with lower damage between entry <-> target      
        Path bestPathEntryTarget=null;
        
        //Path with lower damage between target <-> exit
        Path bestPathtargetExit=null;
        
        try {
            bestPathEntryTarget=entriesTarget.removeMin();
            bestPathtargetExit=targetExits.removeMin();
        } catch (InvalidOperationException ex) {}
        
        UnorderedLinkedList<Division> finalPath=new UnorderedLinkedList<>();
        Iterator<Division> pathEntryTarget=bestPathEntryTarget.getPath();
        while(pathEntryTarget.hasNext()){
            finalPath.addToRear(pathEntryTarget.next());
        }
        
        Iterator<Division> pathTargetExit=bestPathtargetExit.getPath();
        pathTargetExit.next();
        while(pathTargetExit.hasNext()){
            finalPath.addToRear(pathTargetExit.next());
        }
        
        //Add automatic simulation to mission.
        sim.setPath(finalPath);
        sim.setRemainingLife(100-(bestPathEntryTarget.getDamage()+bestPathtargetExit.getDamage()));
        sim.setSuccess(sim.getRemainingLife()==100);
        sim.setVersion(codVersion);    
        version.setAutoSimulation(sim);
    }

    /**
     * Start a manual simulation with the information of the building, target
     * and enemies from a specific version.
     *
     * @param codMission Mission to start simulation.
     * @param version Version of the mission.
     */
    @Override
    public void startManualSimulation(int version) {
        
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
