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
import interfaces.IMission;
import interfaces.IVersion;
import interfaces.MissionsManagement;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;
import missions.Mission;
import org.json.simple.parser.ParseException;
import readWriteJson.ImporterData;

/**
 * This class show the user interface.
 */
public class Menu {

    private static String menu = "\n ********Simulador de Missões********"
            + "\n Selecione uma opção:"
            + "1 - Importar missão"
            + "2 - Apresentar Mapas Dísponiveis"
            + "3 - Iniciar Simulação Automática"
            + "4 - Iniciar Simulação Manual"
            + "5 - Apresentar Resultados Manuais"
            + "6 - Apresentar Resultados Automáticos"
            + "7 - Consultar Informação de uma Missão"
            + "8 - Apresentar todas as missões"
            + "9 - Exportar simulações manuais"
            + "0 - Sair";
    private MissionsManagement missions;

    public Menu(MissionsManagement missions) {
        this.missions = missions;
    }

    public void start() {
        Boolean isRunning = true;
        Integer choice = 0;

        while (isRunning) {
            System.out.println(menu);
            System.out.println("\n Opção: ");
            Scanner input = new Scanner(System.in, "latin1");
            try {
                choice = input.nextInt();
            } catch (InputMismatchException ex) {
                System.out.println("Introduza valores entre 0-7");
            }
            switch (choice) {
                case 0:
                    isRunning = false;
                    break;
                case 1: {
                    try {
                        showImport();
                    } catch (IOException | ParseException 
                            | NullElementValueException | RepeatedElementException | ElementNotFoundException
                            | InvalidWeightValueException | NoEntriesException | EnemyAlreadyExistException
                            | InvalidOperationException | VersionAlreadyExistException | NoDivisionsException
                            | DivisionsClosedException | NoTargetDefinedException ex) {
                        System.out.println(ex + "\n Mapa inválido.Verifique mapa ou caminho do ficheiro." );
                    }
                }
                break;
                case 2:
                    System.out.println(showAvailableMaps());
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    try {
                        showManualSimulations();
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                        System.out.println(ex + "\n Dados inválidos.");}
                    break;
                case 6:
                    try {
                        showAutomaticSimulations();
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                        System.out.println(ex + "\n Dados inválidos.");}
                    break;

                case 7:
                    try {
                        showMissionDetails();
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                         System.out.println(ex + "\n Dados inválidos.");}
                    break;

                case 8:
                    System.out.println(this.missions.getAllMissionsByCode());
                    break;
                case 9:
                    try {
                        showExportManualSimulations();
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                        System.out.println(ex + "\n Dados inválidos.");}
                    break;

                default:
                    System.out.println("Introduza valores entre 0-7");
            }
        }
    }

    public void showImport() throws IOException, FileNotFoundException, ParseException,
            NullElementValueException, RepeatedElementException, ElementNotFoundException,
            InvalidWeightValueException, NoEntriesException, EnemyAlreadyExistException,
            InvalidOperationException, VersionAlreadyExistException, NoDivisionsException,
            DivisionsClosedException, NoTargetDefinedException {

        System.out.println("\n Mapas disponiveis:");
        Scanner inputMap = new Scanner(System.in, "latin1");

        System.out.println("\n Mapas disponiveis para carregar:");
        File arquivo = new File("Mapas");
        File[] files = arquivo.listFiles();
        if (files.length == 0) {
            System.out.println("Sem mapas dísponiveis.Introduza no diretorio Mapas.");
        } else {
            for (File file : files) {
                System.out.println("    " + file);
            }

            System.out.println("\n Introduza o mapa a carregar:");
            ImporterData.importMission(inputMap.nextLine());
        }
    }
    
    public String showAvailableMaps(){
        if(this.missions.getMissions().isEmpty()){
            return "Sem mapas disponíveis.Carregue uma missão.";
        }
        else{
            String result="\n Mapas:";
            Iterator<IMission> missions=this.missions.getMissions().iterator();
            while(missions.hasNext()){
                IMission currentMission=missions.next();
                result+="\n -------------Missão "+currentMission.getCodMission()+"-------------";
                Iterator<IVersion> versions=currentMission.getVersions().iterator();
                while(versions.hasNext()){
                    IVersion currentVersion=versions.next();
                    result+="\n Versão: "+currentVersion.getCodVersion();
                    try {
                        
                        result+="\n "+ currentVersion.printMap();
                    } catch (NullElementValueException | ElementNotFoundException  ex) {}
                }
            }
             return result;
        }      
    }
    
    public void showManualSimulations() throws ElementNotFoundException, NullElementValueException{
        System.out.println("\n Selecione uma missão:");
        Iterator<IMission> missions=this.missions.getMissions().iterator();
        while (missions.hasNext()) {
            System.out.println("\n    "+missions.next().getCodMission());            
        }
        
        Scanner inputMission = new Scanner(System.in, "latin1");
        Scanner inputVersion = new Scanner(System.in, "latin1");
        
        String mission=inputMission.nextLine();
        
        IMission choosenMission=this.missions.getMissions().getElement(new Mission(mission));
        Iterator<IVersion> versions=choosenMission.getVersions().iterator();
        System.out.println("\n Selecione uma versão:");
        while(versions.hasNext()){
            System.out.println("\n    "+versions.next().getCodVersion());
        }
        System.out.println("\n Versão: ");
        int version=inputVersion.nextInt();
        
        System.out.println(this.missions.getManualSimulationsResults(mission, version));  
    }
    
     public void showAutomaticSimulations() throws ElementNotFoundException, NullElementValueException{
        System.out.println("\n Selecione uma missão:");
        Iterator<IMission> missions=this.missions.getMissions().iterator();
        while (missions.hasNext()) {
            System.out.println("\n    "+missions.next().getCodMission());            
        }
        
        Scanner inputMission = new Scanner(System.in, "latin1");
        Scanner inputVersion = new Scanner(System.in, "latin1");
        
        System.out.println("\n Missão: ");
        String mission=inputMission.nextLine();
        
        IMission choosenMission=this.missions.getMissions().getElement(new Mission(mission));
        Iterator<IVersion> versions=choosenMission.getVersions().iterator();
         System.out.println("\n Simulações Automáticas: ");
        while(versions.hasNext()){
            IVersion currentVersion=versions.next();
            System.out.println("\n-------------"+currentVersion.getCodVersion()+"-------------");
            System.out.println(currentVersion.getAutoSimulation().toString());
            System.out.println("\n--------------------------");
        }    
    }
     
    public void showMissionDetails() throws ElementNotFoundException, NullElementValueException{
        System.out.println("\n Selecione uma missão:");
        Iterator<IMission> missions=this.missions.getMissions().iterator();
        while (missions.hasNext()) {
            System.out.println("\n    "+missions.next().getCodMission());            
        }
        
        Scanner inputMission = new Scanner(System.in, "latin1");
        System.out.println("\n Missão: ");
        String mission=inputMission.nextLine();
        
        System.out.println(this.missions.getMissions().getElement(new Mission(mission)));
    } 
    
    public void showExportManualSimulations() throws ElementNotFoundException, NullElementValueException{
        System.out.println("\n Selecione uma missão:");
        Iterator<IMission> missions=this.missions.getMissions().iterator();
        while (missions.hasNext()) {
            System.out.println("\n    "+missions.next().getCodMission());            
        }
        
        Scanner inputMission = new Scanner(System.in, "latin1");
        Scanner inputVersion = new Scanner(System.in, "latin1");
        
        System.out.println("\n Missão: ");
        String choosenMission=inputMission.nextLine();
        IMission mission=new Mission(choosenMission);
        mission=this.missions.getMissions().getElement(mission);
        
        Iterator<IVersion> versions=mission.getVersions().iterator();
        System.out.println("\n Selecione uma versão:");
        while(versions.hasNext()){
            System.out.println("\n    "+versions.next().getCodVersion());
        }
        System.out.println("\n Versão: ");
        int version=inputVersion.nextInt();
        
        System.out.println(this.missions.getManualSimulationsResults(choosenMission, version));
    }
 
}
