
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package missions;

import exceptions.DivisionsClosedException;
import exceptions.ElementNotFoundException;
import exceptions.EnemyAlreadyExistException;
import exceptions.InvalidOperationException;
import exceptions.InvalidWeightValueException;
import exceptions.NoDivisionsException;
import exceptions.NoEntriesException;
import exceptions.NoManualSimulationsException;
import exceptions.NoTargetDefinedException;
import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import exceptions.VersionAlreadyExistException;
import interfaces.IManualSimulation;
import interfaces.IMission;
import interfaces.IVersion;
import linkedListSentinela.OrderedLinkedList;
import interfaces.MissionsManagement;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.parser.ParseException;
import readWriteJson.ExporterData;
import readWriteJson.ImporterData;

/**
 * This class store a set of the missions.
 */
public class Missions implements MissionsManagement {

    private OrderedLinkedList<IMission> missions;

    /**
     * Constructor for missions.
     */
    public Missions() {
        this.missions = new OrderedLinkedList<>();
    }

    /**
     * Constructor for the mission.
     *
     * @param mission Mission to be added.
     * @throws NullElementValueException If parameter is null.
     */
    public Missions(IMission mission) throws NullElementValueException {
        this.missions = new OrderedLinkedList<>();
        this.missions.add(mission);
    }

    /**
     * Getter for the missions stored.
     *
     * @return Missions.
     */
    @Override
    public OrderedLinkedList<IMission> getMissions() {
        return this.missions;
    }

    /**
     * Import from the json file, one version of the mission.
     *
     * @param file File path to load the mission.
     */
    @Override
    public void importMission(String file) throws IOException, ParseException, NullElementValueException,
            RepeatedElementException, ElementNotFoundException, InvalidWeightValueException,
            NoEntriesException, EnemyAlreadyExistException, InvalidOperationException, VersionAlreadyExistException,
            NoDivisionsException, DivisionsClosedException, NoTargetDefinedException {

        IMission mission = ImporterData.importMission(file);
        if (this.missions.contains(mission)) {
            IVersion importedVersion = mission.getVersions().first();
            if (this.missions.getElement(mission).getVersions().contains(importedVersion)) {
                throw new VersionAlreadyExistException("This version already exist in this mission");
            } else {
                this.missions.getElement(mission).addVersion(importedVersion);
            }
        } else {
            this.missions.add(mission);
        }

    }

    /**
     * Create a json file with all manual simulations from a specific mission.
     *
     * @param codMission Mission to export..
     * @param codVersion Version of the mission to export all manual
     * simulations.
     * @return String with all information from json file .
     */
    @Override
    public String exportManualSimulations(String codMission, int codVersion)
            throws NoManualSimulationsException, NullElementValueException, IOException, ElementNotFoundException,
            ElementNotFoundException, NullElementValueException {
        IMission mission = this.missions.getElement(new Mission(codMission));
        IVersion version = new Version();
        version.setCodVersion(codVersion);
        version = mission.getVersions().getElement(version);

        return ExporterData.exportSimulacoesManuais(codMission, version);
    }

    /**
     * Getter for all missions stored ordered by code mission.
     *
     * @return All information about the missions stored.
     */
    @Override
    public String getAllMissionsByCode() {
        if (this.missions.size() == 0) {
            return "Sem missões disponíveis";
        } else {
            return this.missions.toString();
        }
    }

    /**
     * Getter for all manual simulations from a specific mission, ordered by
     * remaining life.
     *
     * @param codMission Mission to export the results of manual simulations.
     * @param codVersion Version of the mission to export the results of manual
     * simulations.
     * @return Results of manual simulations.
     */
    @Override
    public String getManualSimulationsResults(String codMission, int codVersion)
            throws ElementNotFoundException, NullElementValueException {

        IMission mission = this.missions.getElement(new Mission(codMission));
        IVersion version = new Version();
        version.setCodVersion(codVersion);
        version = mission.getVersions().getElement(version);

        if (version.getNumManualSimulations() == 0) {
            return "\n Não existem simulações manuais associadas a esta versão.";
        } else {
            String info = "\n Simulações Manuais: ";
            Iterator<IManualSimulation> sim = version.getManualSimulation().iterator();
            while (sim.hasNext()) {
                info += "\n-------------- Versão:" + codVersion + "--------------";
                info += "\n" +sim.next().toString();
                info += "\n-----------------------------------";
            }
            return info;
        }
    }

    @Override
    public String toString() {
        String info = "";
        Iterator<IMission> missions = this.missions.iterator();
        while (missions.hasNext()) {
            info += "\n" + missions.next().toString();
        }
        return info;
    }

}
