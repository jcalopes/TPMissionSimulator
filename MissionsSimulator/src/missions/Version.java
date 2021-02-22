package missions;

import exceptions.ElementNotFoundException;
import exceptions.NullElementValueException;
import graph.WeightedAdjMatrixDiGraph;
import interfaces.IAutomaticSimulation;
import interfaces.IManualSimulation;
import interfaces.IVersion;
import java.util.Iterator;
import linkedListSentinela.OrderedLinkedList;
import linkedListSentinela.UnorderedLinkedList;

/**
 * This class stores the information about the simulations relative to the
 * version.
 *
 * @author lopes
 */
public class Version implements IVersion {

    private int codVersion;
    private IAutomaticSimulation autoSimulation;
    private OrderedLinkedList<IManualSimulation> manualSimulation;
    private int numManualSimulations;
    private WeightedAdjMatrixDiGraph<Division> building;
    private Target target;
    private UnorderedLinkedList<Division> entries;
    private UnorderedLinkedList<Division> exits;

    /**
     * Constructor for the version of the mission.
     */
    public Version() {
        this.autoSimulation = null;
        this.manualSimulation = new OrderedLinkedList<>();
        this.building = new WeightedAdjMatrixDiGraph<>();
        this.numManualSimulations = 0;
        this.target = null;
        this.exits = new UnorderedLinkedList<>();
        this.entries = new UnorderedLinkedList<>();
    }

    /**
     * Getter for the code version of the mission.
     *
     * @return The version of the mission.
     */
    @Override
    public int getCodVersion() {
        return codVersion;
    }

    /**
     * Setter for the code version of the mission.
     *
     * @param codVersion
     */
    @Override
    public void setCodVersion(int codVersion) {
        this.codVersion = codVersion;
    }

    /**
     * Getter for the automatic simulation performed in the mission.
     *
     * @return The automatic simulation.
     */
    @Override
    public IAutomaticSimulation getAutoSimulation() {
        return autoSimulation;
    }

    /**
     * Getter for the set of the manual simulations performed int he mission.
     *
     * @return The list of the manual simulations.
     */
    @Override
    public OrderedLinkedList<IManualSimulation> getManualSimulation() {
        return manualSimulation;
    }

    /**
     * Getter for the total number of the manual simulations performed in the
     * mission.
     *
     * @return Number of the manual simulations.
     */
    @Override
    public int getNumManualSimulations() {
        return numManualSimulations;
    }

    /**
     * Setter for the structure of the building used for the simulations.
     *
     * @param building Graph of the building.
     */
    @Override
    public void setBuilding(WeightedAdjMatrixDiGraph<Division> building) {
        this.building = building;
    }

    /**
     * Getter for building representation that includes all the divisions and
     * their conexions.
     *
     * @return The graph of the building.
     */
    @Override
    public WeightedAdjMatrixDiGraph<Division> getBuilding() {
        return building;
    }

    /**
     * Setter for number of the manual simulations performed in the mission.
     *
     * @param numManualSimulations Number of the manual simulations.
     */
    @Override
    public void setNumManualSimulations(int numManualSimulations) {
        this.numManualSimulations = numManualSimulations;
    }

    /**
     * Getter for the target of the mission.
     *
     * @return Target of the mission.
     */
    @Override
    public Target getTarget() {
        return target;
    }

    /**
     * Setter for the target of the mission.
     *
     * @param target Target of the mission.
     */
    @Override
    public void setTarget(Target target) {
        this.target = target;
    }

    /**
     * Getter for the entries of the building.
     *
     * @return List of the entries.
     */
    @Override
    public UnorderedLinkedList<Division> getEntries() {
        return entries;
    }

    /**
     * Setter for the entries of the building.
     *
     * @param entries Entries of the building.
     */
    @Override
    public void setEntries(UnorderedLinkedList<Division> entries) {
        this.entries = entries;
    }

    /**
     * Setter for automatic simulation.
     * @param autoSimulation Simulation to be added.
     */
    public void setAutoSimulation(IAutomaticSimulation autoSimulation) {
        this.autoSimulation = autoSimulation;
    }

    /**
     * Getter for the exits of the building
     *
     * @return List of exits.
     */
    @Override
    public UnorderedLinkedList<Division> getExits() {
        return exits;
    }

    /**
     * Setter for the exits of the building.
     *
     * @param exits
     */
    @Override
    public void setExits(UnorderedLinkedList<Division> exits) {
        this.exits = exits;
    }

    /**
     * Check if another object is equal to this version.
     *
     * @param obj Object to be compared.
     * @return True if the object is equal to specific object
     * @return False if the object is not equal to a specific object
     */
    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            IVersion temp = (Version) obj;
            if (this.getCodVersion() == temp.getCodVersion()) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Building representation with their divisions and conections.
     * @return Map 
     */
    public String printMap() throws NullElementValueException, ElementNotFoundException{
        String info="\n Ligações: ";
        for(int i=0;i<this.building.size();i++){
            for(int j=0;j<this.building.size();j++){
                if(this.building.isNeighbor(this.building.getVertex(i),this.building.getVertex(j))){
                    Division vertex1=this.building.getVertex(i);
                    Division vertex2=this.building.getVertex(j);
                    info+="\n   "+vertex1.getName()+" -- "
                            +this.building.getEdgeCost(vertex1, vertex2)+"-> "+vertex2.getName();
                }
            }
        }
        return info;
    }

    /**
     * Return some details about this mission.
     *
     * @return Mission details
     */
    @Override
    public String toString() {
        String info = "";
        info += "\n--------------- Versão " + this.getCodVersion() + "----------------------";
        info += "\n Alvo: " + this.getTarget().toString();
        info += "\n Entradas: ";
        Iterator<Division> entries = this.entries.iterator();
        while (entries.hasNext()) {
            info +=  "\n    " + entries.next().toString();
        }
        info += "\n Saídas: ";
        Iterator<Division> exits = this.exits.iterator();
        while (exits.hasNext()) {
            info += "\n    " + exits.next().toString();
        }
        info += "\n Simulação Automática: ";
        if (this.autoSimulation != null) {
            info += "\n" + this.autoSimulation.toString();
        } else {
            info += "\n    Sem simulação automática efetuada.";
        }
        info += "\n Simulações Manuais: ";
        if (this.getNumManualSimulations() == 0) {
            info += "\n    Sem simulações manuais efetuadas.";
        } else {
            Iterator<IManualSimulation> sim = this.getManualSimulation().iterator();
            while (sim.hasNext()) {
                info += sim.next().toString();
            }
        }
        info += "\n ---------------------------------------------------";
        return info;
    }

}
