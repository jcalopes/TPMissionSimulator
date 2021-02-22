/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package simulations;

import java.util.Iterator;
import missions.Division;

/**
 * This class store a path between two points in the building.
 */
public class Path implements Comparable<Path>{

    private Iterator<Division> path;
    private int damage;

    public Path(Iterator<Division> path, int damage) {
        this.damage = damage;
        this.path = path;
    }

    public Iterator<Division> getPath() {
        return path;
    }

    /**
     * Getter for the damage caused by enemies if the agent use this path.
     *
     * @return Damage
     */
    public int getDamage() {
        return damage;
    }

    @Override
    public int compareTo(Path o) {
        if (this.getDamage() < o.getDamage()) {
            return -1;
        } else if (this.getDamage() == o.getDamage()) {
            return 0;
        } else {
            return 1;
        }
    }

}
