/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

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
import interfaces.MissionsManagement;
import java.io.IOException;
import missions.Missions;
import org.json.simple.parser.ParseException;

/**
 *
 * @author lopes
 */
public class Simulator {

    public static void main(String[] args) {
         MissionsManagement missions=new Missions();
        try {
            missions.importMission("Mapas/mapa.json");
            System.out.println(missions);
        } catch (IOException | ParseException | NullElementValueException |
                RepeatedElementException | ElementNotFoundException | InvalidWeightValueException |
                NoEntriesException | EnemyAlreadyExistException | InvalidOperationException 
                | VersionAlreadyExistException | NoDivisionsException | DivisionsClosedException 
                | NoTargetDefinedException ex) {
            System.out.println(ex);
        }       
    }
    
}
