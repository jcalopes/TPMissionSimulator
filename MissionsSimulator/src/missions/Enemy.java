package missions;

/**
 * This class store the information about an enemy.
 */
public class Enemy {
    private String name;
    private int damage;

    /**
     * Constructor for the enemy.
     * @param name Name of the enemy.
     * @param damage Damage who enemy can make.
     */
    public Enemy(String name, int damage) {
        this.name = name;
        this.damage = damage;
    }

    /**
     * Constructor fot the enemy.
     */
    public Enemy() {
    }

    /**
     * Getter for the name.
     * @return name.
     */
    public String getName() {
        return name;
    }

    /**
     * Setter for the name of the enemy.
     * @param name Name of the enemy.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Getter for the damage associated with the enemy.
     * @return damage.
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Setter for the damage.
     * @param damage Damage associated with the enemy. 
     */
    public void setDamage(int damage) {
        this.damage = damage;
    }

    /**
     * Check if an enemy is equal with another object.
     * @param obj Object to be compared.
     * @return True if they are equals.
     * @return False if they are not equals.
     */
    @Override
    public boolean equals(Object obj) {
        if(obj!=null && obj instanceof Enemy){
            Enemy temp=(Enemy)obj;
            if(this.getName().equals(temp.getName())){
                return true;
            }
        }
        return false;
    }
    
    /**
     * Return all the information about an enemy.
     * @return Enemy information.
     */
    @Override
    public String toString(){
        String info="";
        info+="\n Nome: "+this.getName();
        info+="\n Poder: "+this.getDamage();
        return info;
    }
}
