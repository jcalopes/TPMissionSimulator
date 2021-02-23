
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
            + "\n  1 - Importar missão"
            + "\n  2 - Apresentar Mapas Disponiveis"
            + "\n  3 - Iniciar Simulação Automática"
            + "\n  4 - Iniciar Simulação Manual"
            + "\n  5 - Apresentar Resultados Manuais"
            + "\n  6 - Apresentar Resultados Automáticos"
            + "\n  7 - Consultar Informação de uma Missão"
            + "\n  8 - Apresentar todas as missões"
            + "\n  9 - Exportar simulações manuais"
            + "\n  0 - Sair"
            + "\n ******************************";
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
                choice=-1;
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
                        System.out.println(ex + "\n Mapa inválido.Verifique mapa ou caminho do ficheiro.");
                    }
                }
                break;
                case 2:
                    System.out.println(showAvailableMaps());
                    break;
                case 3:
                    try {
                        showStartAutomaticSimulation();
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                        System.out.println(ex + "\n Dados inválidos.");}
                    break;

                case 4:
                    try {
                        showStartManualSimulation();
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                        System.out.println(ex + "\n Dados inválidos.");}
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

        Scanner inputMap = new Scanner(System.in, "latin1");

        System.out.println("\n Mapas disponiveis para carregar:");
        File arquivo = new File("Mapas");
        File[] files = arquivo.listFiles();
        if (files.length == 0) {
            System.out.println("Sem mapas disponiveis.Introduza no diretorio Mapas.");
        } else {
            for (File file : files) {
                System.out.println("    " + file);
            }

            System.out.println("\n Introduza o mapa a carregar (ex:Mapas/exemplo.json): ");
            this.missions.importMission(inputMap.nextLine());
            System.out.println("\n Mapa Importado com sucesso!");
        }
    }
    
    public String showAvailableMaps(){
        if(this.missions.getMissions().isEmpty()){
            return "Sem mapas disponíveis.Carregue uma missão para o simulador.";
        }
        else{
            String result="\n Mapas: \n    Origem ---Danos do inimigos-->Destino \n";
            Iterator<IMission> missions=this.missions.getMissions().iterator();
            while(missions.hasNext()){
                IMission currentMission=missions.next();
                result+="\n -------------Missão "+currentMission.getCodMission()+"-------------";
                Iterator<IVersion> versions=currentMission.getVersions().iterator();
                while(versions.hasNext()){
                    IVersion currentVersion=versions.next();
                    result+="\n Versão: "+currentVersion.getCodVersion();
                    try {

                        result += "\n " + currentVersion.printMap();
                    } catch (NullElementValueException | ElementNotFoundException ex) {
                    }
                }
                 result+="\n ---------------------------------";
            }
            return result;
        }
    }

    public void showManualSimulations() throws ElementNotFoundException, NullElementValueException {
        if (this.missions.getMissions().size() == 0) {
            System.out.println("Não existem missões disponiveis.Carregue um mapa para o simulador.");
        } else {
            System.out.println("\n Missões Disponiveis:");
            Iterator<IMission> missions = this.missions.getMissions().iterator();
            while (missions.hasNext()) {
                System.out.println("\n    " + missions.next().getCodMission());
            }

            Scanner inputMission = new Scanner(System.in, "latin1");
            Scanner inputVersion = new Scanner(System.in, "latin1");
            System.out.println("\n Missão: ");
            String mission = inputMission.nextLine();

            IMission choosenMission = this.missions.getMissions().getElement(new Mission(mission));
            Iterator<IVersion> versions = choosenMission.getVersions().iterator();
            System.out.println("\n Versões Disponiveis:");
            while (versions.hasNext()) {
                System.out.println("\n    " + versions.next().getCodVersion());
            }
            System.out.println("\n Versão: ");
            int version = inputVersion.nextInt();

            System.out.println(this.missions.getManualSimulationsResults(mission, version));
        }
    }

    public void showAutomaticSimulations() throws ElementNotFoundException, NullElementValueException {
        if (this.missions.getMissions().size() == 0) {
            System.out.println("Não existem missões disponiveis.Carregue um mapa para o simulador.");
        } else {
            System.out.println("\n Missões Disponiveis:");
            Iterator<IMission> missions = this.missions.getMissions().iterator();
            while (missions.hasNext()) {
                System.out.println("\n    " + missions.next().getCodMission());
            }

            Scanner inputMission = new Scanner(System.in, "latin1");
            Scanner inputVersion = new Scanner(System.in, "latin1");

            System.out.println("\n Missão: ");
            String mission = inputMission.nextLine();

            IMission choosenMission = this.missions.getMissions().getElement(new Mission(mission));
            Iterator<IVersion> versions = choosenMission.getVersions().iterator();
            System.out.println("\n Simulações Automáticas: ");
            while (versions.hasNext()) {
                IVersion currentVersion = versions.next();
                System.out.println("\n-------------" + currentVersion.getCodVersion() + "-------------");
                if(currentVersion.getAutoSimulation()==null){
                    System.out.println("Simulação Automática não realizada.");}
                else{
                    System.out.println(currentVersion.getAutoSimulation().toString());}
                System.out.println("\n--------------------------");
            }
        }
    }

    public void showMissionDetails() throws ElementNotFoundException, NullElementValueException {
        if (this.missions.getMissions().size() == 0) {
            System.out.println("Não existem missões disponiveis.Carregue um mapa para o simulador.");
        } else {
            System.out.println("\n Missoes Disponiveis:");
            Iterator<IMission> missions = this.missions.getMissions().iterator();
            while (missions.hasNext()) {
                System.out.println("\n    " + missions.next().getCodMission());
            }

            Scanner inputMission = new Scanner(System.in, "latin1");
            System.out.println("\n Missão: ");
            String mission = inputMission.nextLine();

            System.out.println(this.missions.getMissions().getElement(new Mission(mission)));
        }
    }

    public void showExportManualSimulations() throws ElementNotFoundException, NullElementValueException {
        if (this.missions.getMissions().size() == 0) {
            System.out.println("Não existem missões disponiveis.Carregue um mapa para o simulador.");
        } else {
            System.out.println("\n Missoes Disponiveis:");
            Iterator<IMission> missions = this.missions.getMissions().iterator();
            while (missions.hasNext()) {
                System.out.println("\n    " + missions.next().getCodMission());
            }

            Scanner inputMission = new Scanner(System.in, "latin1");
            Scanner inputVersion = new Scanner(System.in, "latin1");

            System.out.println("\n Missão: ");
            String choosenMission = inputMission.nextLine();
            IMission mission = new Mission(choosenMission);
            mission = this.missions.getMissions().getElement(mission);

            Iterator<IVersion> versions = mission.getVersions().iterator();
            System.out.println("\n Versões Disponiveis:");
            while (versions.hasNext()) {
                System.out.println("\n    " + versions.next().getCodVersion());
            }
            System.out.println("\n Versão: ");
            int version = inputVersion.nextInt();

            System.out.println(this.missions.getManualSimulationsResults(choosenMission, version));
        }
    }

    public void showStartAutomaticSimulation() throws ElementNotFoundException, NullElementValueException {
        if (this.missions.getMissions().size() == 0) {
            System.out.println("Não existem missões disponiveis.Carregue um mapa para o simulador.");
        } else {
            System.out.println("\n Missoes Disponiveis:");
            Iterator<IMission> missions = this.missions.getMissions().iterator();
            while (missions.hasNext()) {
                System.out.println("\n    " + missions.next().getCodMission());
            }

            Scanner inputMission = new Scanner(System.in, "latin1");
            Scanner inputVersion = new Scanner(System.in, "latin1");

            System.out.println("\n Missão: ");
            String choosenMission = inputMission.nextLine();

            IMission mission = new Mission(choosenMission);
            mission = this.missions.getMissions().getElement(mission);
            Iterator<IVersion> versions = mission.getVersions().iterator();

            System.out.println("\n Versões Disponiveis:");
            while (versions.hasNext()) {
                System.out.println("\n    " + versions.next().getCodVersion());
            }
            System.out.println("\n Versão: ");
            int version = inputVersion.nextInt();

            mission.startAutomaticSimulation(version);
        }

    }

    public void showStartManualSimulation() throws ElementNotFoundException, NullElementValueException{
        if (this.missions.getMissions().size() == 0) {
            System.out.println("Não existem missões disponiveis.Carregue um mapa para o simulador.");
        } else {
            System.out.println("\n Missoes Disponiveis:");
            Iterator<IMission> missions = this.missions.getMissions().iterator();
            while (missions.hasNext()) {
                System.out.println("\n    " + missions.next().getCodMission());
            }

            Scanner inputMission = new Scanner(System.in, "latin1");
            Scanner inputVersion = new Scanner(System.in, "latin1");

            System.out.println("\n Missão: ");
            String choosenMission = inputMission.nextLine();

            IMission mission = new Mission(choosenMission);
            mission = this.missions.getMissions().getElement(mission);
            Iterator<IVersion> versions = mission.getVersions().iterator();

            System.out.println("\n Versões Disponiveis:");
            while (versions.hasNext()) {
                System.out.println("\n    " + versions.next().getCodVersion());
            }
            System.out.println("\n Versão: ");
            int version = inputVersion.nextInt();

            mission.startManualSimulation(version);
        }
    }
}
