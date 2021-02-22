
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
import exceptions.NoTargetDefinedException;
import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import exceptions.VersionAlreadyExistException;
import interfaces.IMission;
import interfaces.IVersion;
import linkedListSentinela.OrderedLinkedList;
import interfaces.MissionsManagement;
import java.io.IOException;
import java.util.Iterator;
import org.json.simple.parser.ParseException;
import readWriteJson.ImporterData;

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
    public void importMission(String file) throws IOException,ParseException,NullElementValueException,
            RepeatedElementException,ElementNotFoundException,InvalidWeightValueException,
            NoEntriesException,EnemyAlreadyExistException,InvalidOperationException,VersionAlreadyExistException,
            NoDivisionsException,DivisionsClosedException,NoTargetDefinedException{
        
            IMission mission=ImporterData.importMission(file);
            if(this.missions.contains(mission)){
                IVersion importedVersion=mission.getVersions().first();
                if(this.missions.getElement(mission).getVersions().contains(importedVersion)){
                    throw new VersionAlreadyExistException("This version already exist in this mission");
                }
                else{
                    this.missions.getElement(mission).addVersion(importedVersion);
                }
            }
            else{
                this.missions.add(mission);
            }
   
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
    public String getManualSimulationsResults(String codMission,int version) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public String toString(){
        String info="";
        Iterator<IMission> missions=this.missions.iterator();
        while(missions.hasNext()){
            info+="\n"+missions.next().toString();
        }
        return info;
    }
    
}
