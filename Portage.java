/**
 * Portage.java
 *
 * COMP 1020 SECTION D01
 * INSTRUCTOR    Dr. Heather Matheson
 * ASSIGNMENT    Assignment 4, Phase 2
 * @author       Xinyue Liu, 7981769
 * @version      2025/3/20
 *
 * PURPOSE: provides a portage object connecting two lakes with a distance
 */

import java.util.ArrayList;

public class Portage{
    private double distance;
    private Lake startLake;//start lake
    private Lake destination;//end lake

    public Portage(Lake startLake, Lake destination, double distance){
        this.startLake = startLake;
        this.destination = destination;
        this.distance = distance;
    }

    public String getStartName(){
        return startLake.getName();
    }

    public String getEndName(){
        return destination.getName();
    }

    public Lake getStart(){
        return startLake;
    }

    public Lake getEnd(){
        return destination;
    }

    public Double getDistance(){
        return distance;
    }

    public String toString(){
        return String.format("%s and %s are connected by a portage of %.2f km", startLake.getName(), destination.getName(), distance);
    }

    /**
     * PURPOSE: Returns the name of the lake at the opposite end from the given lake name.
     *
     * @param LakeName The name of one end of the portage.
     * @return the name of the other lake, or null if not found.
     */
    public String otherEnd(String LakeName){
        if(LakeName == null){
            return null;
        }
        if(LakeName.equals(startLake.getName())){
            return destination.getName();
        }
        else if(LakeName.equals(destination.getName())){
            return startLake.getName();
        }
        else{
            return null;
        }
    }

    /**
     * PURPOSE: Returns the lake at the opposite end from the given Lake object.
     *
     * @param lake One end of the portage.
     * @return the other Lake object, or null if not found.
     */
    public Lake otherEnd(Lake lake){
        if(lake == null){
            return null;
        }
        if(lake.equals(startLake)){
            return destination;
        }
        else if(lake.equals(destination)){
            return startLake;
        }
        else{
            return null;
        }
    }
}