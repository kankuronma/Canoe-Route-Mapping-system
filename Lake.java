/**
 * Lake.java
 *
 * COMP 1020 SECTION D01
 * INSTRUCTOR    Dr. Heather Matheson
 * ASSIGNMENT    Assignment 4, Phase 1, 3
 * @author       Xinyue Liu, 7981769
 * @version      2025/3/20
 *
 * PURPOSE: Provides a lake object with name, water qulity, and portages connected
 *          with other lakes
 */

import java.util.ArrayList;

public class Lake{
    private String name;
    private String waterQuality;
    private ArrayList<Portage> portages;

    public Lake(String name, String waterQuality){
        this.name = name;
        this.waterQuality = waterQuality;
        this.portages = new ArrayList<Portage>();
    }

    public String toString(){
        String result = name + " has " + waterQuality + " water quality";
        ArrayList<Lake> neighbours = new ArrayList<Lake>();
        if(!portages.isEmpty()){ //add the portage part only when portage list is not empty
            result += ", and is connected to: ";
            for(int i = 0; i < portages.size(); i++){
                Lake neighbor = portages.get(i).otherEnd(this); //use other end to find the lake connected to the current lake
                if(!neighbours.contains(neighbor)){
                    neighbours.add(neighbor);
                }
            }
        }
        //add neighbour lakes' names to the result
        for(int i = 0; i < neighbours.size(); i++){
            result += neighbours.get(i).getName();
            if(i != neighbours.size() - 1){ //if not the last lake in the arraylist, add a following comma to seperate between lakes
                result += ", ";
            }
        }
        return result;
    }

    public String getName(){
        return name;
    }

    public String getWaterQuality(){
        return waterQuality;
    }

    /**
     * PURPOSE: Adds a portage to the lake's list if it connects to this lake.
     *
     * @param p The portage to be added.
     * @return true if added successfully; false otherwise.
     */
    public boolean addPortage(Portage p){
        if(this.equals(p.getStart()) || this.equals(p.getEnd())){
            portages.add(p);
            return true;
        }
        else{
            return false;
        }
    }

    public ArrayList<Portage> getPortageList(){
        return portages;
    }
}