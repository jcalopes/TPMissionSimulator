/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package missions;

import exceptions.ElementNotFoundException;
import exceptions.EnemyAlreadyExistException;
import exceptions.NullElementValueException;
import interfaces.IDivision;
import linkedListSentinela.UnorderedLinkedList;

/**
 * This class store all the information about one division that belong to the building.
 * @author lopes
 */
public class Division implements IDivision{
    private String name;
    private UnorderedLinkedList<Enemy> enemies;
    private int totalDamage;
    
    /**
     * Cosntructor for the division
     * @param name Name of the division.
     */
    public Division(String name){
        this.name=name;
        this.totalDamage=0;
        this.enemies=new UnorderedLinkedList<>();
    }

    /**
     * Getter for the name of the division.
     * @return Name of the division.
     */
    @Override
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the division.
     * @param name Name of the division.
     */
    @Override
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the enemies into this division.
     * @return List pf the enemies.
     */
    @Override
    public UnorderedLinkedList<Enemy> getEnemies() {
        return enemies;
    }

    /**
     * Adds a new enemy in this division.
     * @param enemy Enemy to be added.
     * @throws NullElementValueException If the enemy is null.
     */
    @Override
    public void addEnemy(Enemy enemy) throws NullElementValueException, EnemyAlreadyExistException {
        if(enemy==null){
            throw new NullElementValueException("The enemy value is null");
        }     
        if(this.enemies.contains(enemy)){
            throw new EnemyAlreadyExistException("This enemy already exist in the division");
        }
        
        this.enemies.addToRear(enemy);
        this.setTotalDamage(totalDamage+enemy.getDamage());
    }
    
    /**
     * Adds a new enemy in this division.
     * @param enemy Enemy to be removed.
     * @throws NullElementValueException If the enemy is null.
     */
    @Override
    public void removeEnemy(Enemy enemy) throws ElementNotFoundException, NullElementValueException {
        if(enemy==null){
            throw new NullElementValueException("The enemy value is null");
        }
        this.enemies.remove(enemy);
        this.setTotalDamage(totalDamage-enemy.getDamage());
    }
    
    /**
     * Getter for the total damage which the enemies into this division can make.
     * @return Total damage.
     */
    @Override
    public int getTotalDamage() {
        return totalDamage;
    }

    /**
     * Setter for the total damage which the enemies into this division can make.
     * @param totalDamage Total damage of this division.
     */
    @Override
    public void setTotalDamage(int totalDamage) {
        this.totalDamage = totalDamage;
    }  
}
