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
import interfaces.IManualSimulation;
import interfaces.IMission;
import interfaces.IVersion;
import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;
import linkedListSentinela.UnorderedLinkedList;
import simulations.AutomaticSimulation;
import simulations.ManualSimulation;
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
            Division currentEntry = version.getBuilding().getVertex(divisions.next());
            Path current = null;
            try {
                current = new Path(version.getBuilding().shortestPathWeight(currentEntry, target),
                        currentEntry.getTotalDamage()
                        +  (int)version.getBuilding().shortestPathWeightCost(currentEntry, target));

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
        Division target = new Division(version.getTarget().getDivision());

        IAutomaticSimulation sim = new AutomaticSimulation();

        //Paths between entries and target 
        Iterator<Division> availableEntries = version.getEntries().iterator();
        LinkedHeap<Path> entriesTarget = this.findShortestPathsEntriesTarget(version, availableEntries, target);

        //Paths between target and exits 
        Iterator<Division> availableExits = version.getExits().iterator();
        LinkedHeap<Path> targetExits = this.findShortestPathsTargetExits(version, availableExits, target);;

        //Path with lower damage between entry <-> target      
        Path bestPathEntryTarget = null;

        //Path with lower damage between target <-> exit
        Path bestPathtargetExit = null;

        try {
            bestPathEntryTarget = entriesTarget.removeMin();
            bestPathtargetExit = targetExits.removeMin();
        } catch (InvalidOperationException ex) {
        }

        UnorderedLinkedList<Division> finalPath = new UnorderedLinkedList<>();
        Iterator<Division> pathEntryTarget = bestPathEntryTarget.getPath();
        while (pathEntryTarget.hasNext()) {
            finalPath.addToRear(pathEntryTarget.next());
        }

        Iterator<Division> pathTargetExit = bestPathtargetExit.getPath();
        pathTargetExit.next();
        while (pathTargetExit.hasNext()) {
            finalPath.addToRear(pathTargetExit.next());
        }

        //Add automatic simulation to mission.
        sim.setPath(finalPath);
        sim.setRemainingLife(100 - (bestPathEntryTarget.getDamage() + bestPathtargetExit.getDamage()));
        sim.setSuccess(sim.getRemainingLife() == 100);
        sim.setVersion(codVersion);
        version.setAutoSimulation(sim);
    }

    /**
     * Place the power up inside the building randomized.
     * @param version 
     * @param sim
     * @return Manual Simulation update.
     */
    private IManualSimulation randomPowerUps(IVersion version, IManualSimulation sim) {
        Random random1 = new Random();
        int randomIndex = random1.nextInt(version.getBuilding().size());
        sim.getPowerUps().setRecoverLastDamageDivision(version.getBuilding().getVertex(randomIndex).getName());
        
        Random random2 = new Random();
        int randIndex = random2.nextInt(version.getBuilding().size());
        sim.getPowerUps().setRestoreLifeDivision(version.getBuilding().getVertex(randIndex).getName());
        return sim;
    }
    
    /**
     * Check if exist power ups in the division where the agent is.
     * @param sim 
     * @param currentDivision 
     * @return ManualSimulation updated.
     */
    private IManualSimulation checkPowerUps(IManualSimulation sim, Division currentDivision) {
        if (sim.getPowerUps().getRecoverLastDamageDivision().equals(currentDivision.getName())
                &&!sim.getPowerUps().hasRecoverDamage()) {
            System.out.println("Encontrado um Power Up! Recuperar do dano de uma divisão");
            sim.getPowerUps().setRecoverDamage(true);
        }
        if (sim.getPowerUps().getRestoreLifeDivision().equals(currentDivision.getName()) && 
                !sim.getPowerUps().hasRestoreLife()) {
            System.out.println("Encontrado um Power Up! Vida 100%");
            sim.getPowerUps().setRestoreLife(true);
        }
        return sim;
    }

    /**
     * Check if exist powerups to be used and ask to user if he want to use them.
     * @param sim 
     * @param currentDivision Division where the agent is.
     * @return Simulation updated.
     */
      private IManualSimulation usePowerUps(IManualSimulation sim,Division currentDivision) {
        Scanner inputAnswer = new Scanner(System.in, "latin1");
        if (sim.getPowerUps().hasRecoverDamage() || sim.getPowerUps().hasRestoreLife()) {
            Integer answer;
            System.out.println("\n Quer usar utilizar algum Power Up?(sim/nao)");
            if (inputAnswer.nextLine().equals("sim")) {
                System.out.println("\n Power Ups disponiveis:");
                if (sim.getPowerUps().hasRecoverDamage()) {
                    System.out.println("\n    1 - Recuperar dano efetuado desta divisão ");
                }
                if (sim.getPowerUps().hasRestoreLife()) {
                    System.out.println("\n    2 - Recuperar vida total ");
                }
                System.out.println("\n Opcao: ");
                answer = inputAnswer.nextInt();
                if (answer == 1 && sim.getPowerUps().hasRecoverDamage()) {
                    System.out.println("\n +" + currentDivision.getTotalDamage() + "pontos de vida");
                    sim.setRemainingLife(sim.getRemainingLife() + currentDivision.getTotalDamage());
                    sim.getPowerUps().setRecoverDamage(false);
                } else if (answer == 2 && sim.getPowerUps().hasRestoreLife()) {
                    System.out.println("\n +" + (100-currentDivision.getTotalDamage()) + "pontos de vida");
                    sim.setRemainingLife(100);
                    sim.getPowerUps().setRestoreLife(false);
                } else {
                    System.out.println("Resposta inválida!Nenhum power up acionado");
                }
            }
        }
        return sim;
    }
    
      /**
       * Move the enemies in the building.
       * @param version Version to be updated.
       * @return Version with new enemies replaced.
       */
      private IVersion moveEnemies(IVersion version){
          
          for(int i=0;i<version.getBuilding().size();i++){
              if(version.getBuilding().getVertex(i).getTotalDamage()>0){
                  version.getBuilding().getVertex(i).
              }
          }
          return version;
      }
      
      /**
       * Clone a version in order to get the same version but with different reference.
       * @param versionOrigin Origin Version.
       * @return New Version
       */
      private IVersion cloneVersion(IVersion versionOrigin){
        IVersion version = new Version();
        version.setBuilding(versionOrigin.getBuilding());
        version.setCodVersion(versionOrigin.getCodVersion());
        version.setEntries(versionOrigin.getEntries());
        version.setExits(versionOrigin.getExits());
        version.setTarget(versionOrigin.getTarget());
        return version;
      }
      
    /**
     * Start a manual simulation with the information of the building, target
     * and enemies from a specific version.
     *
     * @param codMission Mission to start simulation.
     * @param version Version of the mission.
     */
    @Override
    public void startManualSimulation(int codVersion) throws ElementNotFoundException, NullElementValueException {
        IVersion versionOrigin = new Version();
        versionOrigin.setCodVersion(codVersion);
        versionOrigin = this.getVersions().getElement(versionOrigin);
        
        IVersion version=cloneVersion(versionOrigin);       
                
        IManualSimulation sim = new ManualSimulation();
        Scanner inputDivision = new Scanner(System.in, "latin1");
        Scanner inputAnswer = new Scanner(System.in, "latin1");
        //Put PowerUps inside the divisions
        sim = randomPowerUps(version, sim);
        sim.setVersion(codVersion);
        UnorderedLinkedList<Division> path = new UnorderedLinkedList<>();

        boolean alreadyIn = false;
        boolean alreadyHasTarget = false;
        boolean alreadyOut = false;
        boolean validDivision = false;
        Division currentDivision = null;
        Division lastDivision = null;
        while (!alreadyOut) {
            if(alreadyIn){
                version=moveEnemies(version);
            }          
            System.out.println("\n" + version.printMap());           
            System.out.println("\n ------------------Detalhes Atuais-----------------------------");
            System.out.println("PowerUps: ");
            System.out.println("\n  Recuperar dano da divisão: " + sim.getPowerUps().getRecoverLastDamageDivision());
            System.out.println("  100% vida: " + sim.getPowerUps().getRestoreLifeDivision());
            System.out.println("PowerUps disponiveis: ");
            if(sim.getPowerUps().hasRecoverDamage())
                System.out.println("\n  Recuperar dano da divisão");
            if(sim.getPowerUps().hasRestoreLife())
                System.out.println("  100% vida");
            if(currentDivision!=null)System.out.println("\nDivisão Atual: " + currentDivision);
            System.out.println("\nAlvo: " + version.getTarget().getDivision());
            System.out.println("\nPontos de Vida restantes: " + sim.getRemainingLife());
            System.out.println("\n ---------------------------------------------------------------");
            System.out.println("\nIntroduza divisão: ");
            try {
                currentDivision = version.getBuilding().getVertex(new Division(inputDivision.nextLine()));
                validDivision = true;
            } catch (ElementNotFoundException | NullElementValueException ex) {
                System.out.println("A divisão não existe no edificio.Consulte no mapa as opções.");
                currentDivision=lastDivision;
            }

            if (validDivision) {
                validDivision = false;
                if (!alreadyIn) {
                    try {
                        version.getEntries().getElement(currentDivision);
                        alreadyIn = true;
                        lastDivision = currentDivision;
                        if (currentDivision.equals(version.getTarget().getDivision())) {
                            alreadyHasTarget = true;
                            System.out.println("\n Já encontrou o alvo.");
                        }

                        if (currentDivision.getTotalDamage() > 0) {
                            sim.setRemainingLife(sim.getRemainingLife() - currentDivision.getTotalDamage());
                            System.out.println("\nImpacto:   \n - " + currentDivision.getTotalDamage() + " pontos de vida");
                        }
                        sim=this.checkPowerUps(sim, currentDivision);
                        sim=this.usePowerUps(sim, currentDivision);
                        if (sim.getRemainingLife() <= 0) {
                            System.out.println("\n Missão Falhada.Terminaram os pontos de vida.");
                            sim.setSuccess(false);
                            alreadyOut = true;
                        }
                    } catch (ElementNotFoundException | NullElementValueException ex) {
                        System.out.println("\n Entrada não existe no edificio.Insira uma entrada válida.");
                        currentDivision=lastDivision;
                    }
                } else {
                    if (!version.getBuilding().isNeighbor(lastDivision, currentDivision)) {
                        System.out.println("Movimentação Inválida!Não existe ligação entre as divisões.");
                        currentDivision=lastDivision;
                    } else {
                        path.addToRear(currentDivision);
                        sim=this.checkPowerUps(sim, currentDivision);
                        if (currentDivision.getName().equals(version.getTarget().getDivision())) {
                            alreadyHasTarget = true;
                            System.out.println("\n Já encontrou o alvo. Dirija-se a uma saída!");
                        }
                        int damage = (int) version.getBuilding().getEdgeCost(lastDivision, currentDivision);

                        sim.setRemainingLife(sim.getRemainingLife() - damage);
                        System.out.println("\n - " + damage + " pontos de vida");
                        
                        sim=this.usePowerUps(sim, currentDivision);
                        
                        if (sim.getRemainingLife() <= 0) {
                            System.out.println("\n Missão Falhada.Terminaram os pontos de vida.");
                            sim.setSuccess(false);
                            alreadyOut = true;
                        }
                        if (!alreadyOut && version.getExits().contains(currentDivision)) {
                            System.out.println("\n Chegou a uma saída.Pretende sair do edificio?(sim/nao)");
                            if (!inputAnswer.nextLine().equals("nao")) {
                                alreadyOut = true;
                                if (alreadyHasTarget) {
                                    System.out.println("\n Missão Manual concluída com sucesso!");
                                    sim.setSuccess(true);
                                } else {
                                    System.out.println("\n Não encontrou o alvo. Missão falhada!");
                                    sim.setSuccess(false);
                                }
                            }
                        }
                        lastDivision=currentDivision;
                    }
                }
            }
            System.out.println("------------------------------------------------");
        }
        sim.setPath(path);
        versionOrigin.getManualSimulation().add(sim);
        versionOrigin.setNumManualSimulations(version.getNumManualSimulations()+1);
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
