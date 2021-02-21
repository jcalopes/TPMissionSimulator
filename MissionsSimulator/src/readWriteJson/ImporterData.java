/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readWriteJson;

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
import graph.WeightedAdjMatrixDiGraph;
import interfaces.IDivision;
import interfaces.IMission;
import interfaces.IVersion;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import linkedListSentinela.UnorderedLinkedList;
import missions.Division;
import missions.Enemy;
import missions.Mission;
import missions.Target;
import missions.Version;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * This class import and validate an specific mission from a json file.
 */
public class ImporterData {

    /**
     * Import a specific json file if the structure is according with the rules
     * defined.
     *
     * @param file Path file to be imported.
     * @return String with information imported.
     */
    public static IMission importMission(String file) throws FileNotFoundException,
            IOException, ParseException, NullElementValueException, RepeatedElementException, 
            ElementNotFoundException, InvalidWeightValueException, NoEntriesException,
            EnemyAlreadyExistException, InvalidOperationException, VersionAlreadyExistException, 
            NoDivisionsException, DivisionsClosedException, NoTargetDefinedException {
        JSONObject resultObject;
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(file);
        resultObject = (JSONObject) parser.parse(reader);

        String jCod = (String) resultObject.get("cod-missao");
        long jVersion = (long) resultObject.get("versao");

        IMission mission = new Mission(jCod);

        //Add version to the mission
        IVersion version = new Version();
        version.setCodVersion((int) jVersion);

        WeightedAdjMatrixDiGraph<Division> building = new WeightedAdjMatrixDiGraph<>();
        
        //Add divisions to the building   
        building = importDivisions(building, (JSONArray) resultObject.get("edificio"));
        if(building.size()==0)throw new NoDivisionsException("The building don´t have divisions");
        
        //Add conections to the building
        building=importConections(building, (JSONArray) resultObject.get("ligacoes"));
        if(!building.isConnected())throw new DivisionsClosedException("The building have divisions closed");
        
        //Add entries to the building
        version.setEntries(importEntries(building, (JSONArray) resultObject.get("entradas")));
        if(version.getEntries().size()==0)throw new NoEntriesException("The building don´t have entries");
        
        //Add exits to the building
        version.setExits(importExits(building, (JSONArray) resultObject.get("saidas")));
        if(version.getExits().size()==0)throw new NoEntriesException("The building don´t have exits");
        
        //Add enemies to the building
        building=importEnemies(building, (JSONArray) resultObject.get("inimigos"));
        
        //Add target to the mission
        version.setTarget(importTarget(building, (JSONObject) resultObject.get("alvo")));
        if(version.getTarget()==null)throw new NoTargetDefinedException("The mission don´t have a target");
        
        //Add building to the mission
        version.setBuilding(building);
        
        mission.addVersion(version);
        return mission;
    }

    /**
     * Import from the JSONFile all the divisions inside the building.
     * @param building Graph with the building.
     * @param jBuilding Json object with divisions to be imported.
     * @return The building with all divisions imported.
     * @throws NullElementValueException If the division has null value.
     * @throws RepeatedElementException If exist one division repeated in the Json file.
     */
    private static WeightedAdjMatrixDiGraph<Division> importDivisions(WeightedAdjMatrixDiGraph<Division> building,
            JSONArray jBuilding) throws NullElementValueException, RepeatedElementException {
        
        for (int i = 0; i < jBuilding.size(); i++) {
            Division currentDivision = new Division((String) jBuilding.get(i));
            building.addVertex(currentDivision);
        }
        return building;
    }
   
    /**
     * Import from the Json File all conections between divisions.
     * @param building Building to be updated.
     * @param jConections Conections to be imported.
     * @return Building updated.
     * @throws NullElementValueException 
     * @throws ElementNotFoundException If some division doesn´t exist in the building.
     * @throws InvalidWeightValueException If the conection's weight inserted are not valid.
     */
    private static WeightedAdjMatrixDiGraph<Division> importConections(WeightedAdjMatrixDiGraph<Division> building,
            JSONArray jConections) throws NullElementValueException, ElementNotFoundException, InvalidWeightValueException{
        
         for (int i = 0; i <jConections.size(); i++) {
            JSONArray currentConection=(JSONArray)jConections.get(i);
            Division vertex1=new Division((String)currentConection.get(0));
            building.getVertex(vertex1);
            Division vertex2=new Division((String)currentConection.get(1));           
            building.getVertex(vertex2);
            building.addEdge(vertex1, vertex2, 0);
            building.addEdge(vertex2, vertex1, 0);           
        }
        return building;
    }

    /**
     * Import the entries of the building from JSON File.
     * @param building Building to be updated.
     * @param jEntries Entries to be imported.
     * @return Entries imported.
     * @throws NullElementValueException If division has null value
     * @throws ElementNotFoundException If doesn´t exist a specific entry in the building
     * @throws NoEntriesException If the building doesn´t have any entries
     */
    private static UnorderedLinkedList<Division> importEntries(WeightedAdjMatrixDiGraph<Division> building,
            JSONArray jEntries) throws NullElementValueException, ElementNotFoundException, NoEntriesException {
        UnorderedLinkedList<Division> divisions = new UnorderedLinkedList<>();

        for (int i = 0; i < jEntries.size(); i++) {
            Division division=new Division((String)jEntries.get(i));
            building.getVertex(division);
            divisions.addToRear(division);
        }
        if(divisions.isEmpty())throw new NoEntriesException("The building don´t have entries");
        return divisions;
    }

    /**
     * Import the entries of the building from JSON File.
     * @param building Building to be updated.
     * @param jExits Entries to be imported.
     * @return Exits imported.
     * @throws NullElementValueException If division has null value
     * @throws ElementNotFoundException If doesn´t exist a specific entry in the building
     * @throws NoEntriesException If the building doesn´t have any entries
     */
    private static UnorderedLinkedList<Division> importExits(WeightedAdjMatrixDiGraph<Division> building,
            JSONArray jExits) throws NullElementValueException, ElementNotFoundException, NoEntriesException {
        UnorderedLinkedList<Division> divisions = new UnorderedLinkedList<>();

        for (int i = 0; i < jExits.size(); i++) {
            Division division=new Division((String)jExits.get(i));
            building.getVertex(division);
            divisions.addToRear(division);
        }
        if(divisions.isEmpty())throw new NoEntriesException("The building don´t have exits");
        return divisions;
    }
    
    /**
     * Import enemies and put them inside the divisions.
     * @param building Building to be updated.
     * @param jEnemies Enemies to be imported.
     * @return Buiding updated.
     * @throws NullElementValueException
     * @throws ElementNotFoundException
     * @throws RepeatedElementException
     * @throws EnemyAlreadyExistException
     * @throws InvalidOperationException 
     */
    private static WeightedAdjMatrixDiGraph<Division> importEnemies(WeightedAdjMatrixDiGraph<Division> building,
            JSONArray jEnemies) throws NullElementValueException, 
            ElementNotFoundException, RepeatedElementException, EnemyAlreadyExistException, InvalidOperationException{
        
        IDivision[] divisions=building.getVertices();
        for(int i=0;i<jEnemies.size();i++){
            JSONObject jEnemy=(JSONObject)jEnemies.get(i); 
            Enemy currentEnemy=new Enemy((String) jEnemy.get("nome"), (int) ((long) jEnemy.get("poder")));
            
            Division currentDivision=new Division((String) jEnemy.get("divisao"));           
            currentDivision=building.getVertex(currentDivision);
            currentDivision.addEnemy(currentEnemy);
            currentDivision.setTotalDamage(currentDivision.getTotalDamage()+currentEnemy.getDamage());
                       
            for(int j=0;j<building.size();j++){
                if(building.isNeighbor(building.getVertex(j), currentDivision)){
                    building.setEdgeCost(building.getVertex(j), currentDivision, currentDivision.getTotalDamage());
                }
            }  
        }
        return building;
    }
    
    /**
     * Import target to the mission.
     * @param building Building to check division
     * @param jTarget Target to be imported.
     * @return Target
     * @throws NullElementValueException If the parameter is null
     * @throws ElementNotFoundException If doesn´t exist the target divisions
     */
    private static Target importTarget(WeightedAdjMatrixDiGraph<Division> building, JSONObject jTarget)
            throws NullElementValueException, ElementNotFoundException{
        String divisionName=(String)jTarget.get("divisao");
        building.getVertex(new Division(divisionName));
        
        Target resultTarget =new Target(divisionName, (String)jTarget.get("tipo"));
        return resultTarget;
    }
      
}
