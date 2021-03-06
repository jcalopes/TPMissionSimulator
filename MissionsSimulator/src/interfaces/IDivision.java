/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package interfaces;

import exceptions.ElementNotFoundException;
import exceptions.EnemyAlreadyExistException;
import exceptions.NullElementValueException;
import linkedListSentinela.UnorderedLinkedList;
import missions.Enemy;

/**
 *
 * @author JoaoLopes 8190221
 */
public interface IDivision{
    /**
     * Getter for the name of the division.
     * @return Name of the division.
     */
    public String getName();

    /**
     * Setter for the name of the division.
     * @param name Name of the division.
     */
    public void setName(String name);

    /**
     * Getter for the enemies into this division.
     * @return List pf the enemies.
     */
    public UnorderedLinkedList<Enemy> getEnemies();

    /**
     * Adds a new enemy in this division.
     * @param enemy Enemy to be added.
     * @throws NullElementValueException If the enemy is null.
     */
    public void addEnemy(Enemy enemy) throws NullElementValueException, EnemyAlreadyExistException;
    /**
     * Adds a new enemy in this division.
     * @param enemy Enemy to be removed.
     * @throws NullElementValueException If the enemy is null.
     */
    public Enemy removeEnemy(Enemy enemy) throws ElementNotFoundException, NullElementValueException;
    
    /**
     * Getter for the total damage which the enemies into this division can make.
     * @return Total damage.
     */
    public int getTotalDamage();
    /**
     * Setter for the total damage which the enemies into this division can make.
     * @param totalDamage Total damage of this division.
     */
    public void setTotalDamage(int totalDamage);
    
    /**
     * Check if contains an specific enemy in the division.
     * @param enemy Enemy to be searched.
     * @return True If exist the enemy in the division. 
     * @return False If doesn´t exist the enemy in the division. 
     */
    public boolean containEnemy(Enemy enemy)throws NullElementValueException;
    
    /**
     * Compare with another object and check if are equals.
     * @param obj Objecto to be compared.
     * @return True if the name of their divisions are equals.
     */
    @Override
    public boolean equals(Object obj);
    
     /**
     * Return all the information about this division.
     * @return Division Information
     */
    @Override
    public String toString();
}
