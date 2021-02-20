package missions;

import graph.WeightedAdjMatrixDiGraph;
import interfaces.IAutomaticSimulation;
import interfaces.IManualSimulation;
import interfaces.IVersion;
import linkedListSentinela.OrderedLinkedList;

/**
 * This class stores the information about the simulations relative to the version.
 * @author lopes
 */
public class Version implements IVersion{
    private int codVersion;
    private IAutomaticSimulation autoSimulation;
    private OrderedLinkedList<IManualSimulation> manualSimulation;
    private int numManualSimulations;
    private WeightedAdjMatrixDiGraph<Division> building;
    
    /**
     * Constructor for the version of the mission.
     */
    public Version(){
        this.autoSimulation=null;
        this.manualSimulation=new OrderedLinkedList<>();
        this.building=new WeightedAdjMatrixDiGraph<>();
        this.numManualSimulations=0;
    }

    /**
     * Getter for the code version of the mission.
     * @return The version of the mission.
     */
    @Override
    public int getCodVersion() {
        return codVersion;
    }

    /**
     * Setter for the code version of the mission.
     * @param codVersion 
     */
    @Override
    public void setCodVersion(int codVersion) {
        this.codVersion = codVersion;
    }

    /**
     * Getter for the automatic simulation performed in the mission.
     * @return The automatic simulation.
     */
    @Override
    public IAutomaticSimulation getAutoSimulation() {
        return autoSimulation;
    }

    /**
     * Getter for the set of the manual simulations performed int he mission.
     * @return The list of the manual simulations.
     */
    @Override
    public OrderedLinkedList<IManualSimulation> getManualSimulation() {
        return manualSimulation;
    }

    /**
     * Getter for the total number of the manual simulations performed in the mission.
     * @return Number of the manual simulations.
     */
    @Override
    public int getNumManualSimulations() {
        return numManualSimulations;
    }

    /**
     * Getter for building representation that includes all the divisions and 
     * their conexions.
     * @return The graph of the building.
     */
    @Override
    public WeightedAdjMatrixDiGraph<Division> getBuilding() {
        return building;
    }

    /**
     * Setter for number of the manual simulations performed in the mission.
     * @param numManualSimulations Number of the manual simulations.
     */
    @Override
    public void setNumManualSimulations(int numManualSimulations) {
        this.numManualSimulations = numManualSimulations;
    }
       
}
