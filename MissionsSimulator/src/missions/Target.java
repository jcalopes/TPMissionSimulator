/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package missions;

/**
 * This class store the information about the division target of the mission.
 * @author lopes
 */
public class Target {
    private String division;
    private String type;

    /**
     * Constructor for the target.
     * @param division Place where the target is.
     * @param type Type of the target.
     */
    public Target(String division, String type) {
        this.division = division;
        this.type = type;
    }

    /**
     * Constructor for the target.
     */
    public Target() {
    }
    
    /**
     * Getter for division of the target.
     * @return The name of the division.
     */
    public String getDivision() {
        return division;
    }

    /**
     * Setter for the division of the target.
     * @param division Place where the target is.
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /**
     * Getter for the type of the target.
     * @return The type of the target.
     */
    public String getType() {
        return type;
    }

    /**
     * Setter for the type of the target.
     * @param type Type of the target.
     */
    public void setType(String type) {
        this.type = type;
    }
    
    /**
     * Return all the infomration about the target.
     * @return All information.
     */
    @Override
    public String toString(){
        String info="";
        info+="\n    Divisão: "+this.getDivision();
        info+="\n    Tipo: "+this.getType();
        return info;
    }
    
    
}
