/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package readWriteJson;

import exceptions.NullElementValueException;
import exceptions.RepeatedElementException;
import graph.WeightedAdjMatrixDiGraph;
import interfaces.IDivision;
import interfaces.IMission;
import interfaces.IVersion;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import missions.Division;
import missions.Mission;
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
            IOException, ParseException, NullElementValueException, RepeatedElementException {
        JSONObject resultObject;
        JSONParser parser = new JSONParser();

        Reader reader = new FileReader(file);
        resultObject = (JSONObject) parser.parse(reader);

        String jCod = (String) resultObject.get("cod-missao");
        long jVersion = (long) resultObject.get("versao");

        JSONArray jConections = (JSONArray) resultObject.get("ligacoes");
        JSONArray jEnemies = (JSONArray) resultObject.get("inimigos");
        JSONArray jEntries = (JSONArray) resultObject.get("entradas");
        JSONArray jExits = (JSONArray) resultObject.get("saidas");
        JSONObject jTarget = (JSONObject) resultObject.get("alvo");

        IMission mission = new Mission(jCod);

        //Construir versão.
        IVersion version = new Version();
        version.setCodVersion((int) jVersion);

        //Contruir grafo do edificio
        WeightedAdjMatrixDiGraph<IDivision> building = new WeightedAdjMatrixDiGraph<>();
        
        //Adicionar Vértices      
        building = importDivisions(building, (JSONArray) resultObject.get("edificio"));

        //Adicionar Ligações
        
    }

    /**
     * Import from the JSONFile all the divisions inside the building.
     * @param building Graph with the building.
     * @param jBuilding Json object with divisions to be imported.
     * @return The building with all divisions imported.
     * @throws NullElementValueException If the division has null value.
     * @throws RepeatedElementException If exist one division repeated in the Json file.
     */
    private static WeightedAdjMatrixDiGraph<IDivision> importDivisions(WeightedAdjMatrixDiGraph<IDivision> building,
            JSONArray jBuilding) throws NullElementValueException, RepeatedElementException {
        
        for (int i = 0; i < jBuilding.size(); i++) {
            IDivision currentDivision = new Division((String) jBuilding.get(i));
            building.addVertex(currentDivision);
        }
        return building;
    }
    
    private static WeightedAdjMatrixDiGraph<IDivision> importConections(WeightedAdjMatrixDiGraph<IDivision> building,
            JSONArray jConections){
        
         for (int i = 0; i <jConections.size(); i++) {
            JSONArray currentConection=(JSONArray)jConections.get(i);
            IDivision vertex1=new Division((String)currentConection.get(0));
            IDivision vertex2=new Division((String)currentConection.get(1));
            
        }
        
    }

}
