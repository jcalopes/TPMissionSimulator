/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package demo;

import interfaces.MissionsManagement;
import missions.Missions;


/**
 *
 * @author lopes
 */
public class Simulator {

    public static void main(String[] args){
        MissionsManagement missions=new Missions();
        
        Menu menu=new Menu(missions);
        menu.start();
    }  
}
