/**
 * Map.java
 *
 * COMP 1020 SECTION D01
 * INSTRUCTOR    Dr. Heather Matheson
 * ASSIGNMENT    Assignment 4, Phase 4, 5, 7
 * @author       Xinyue Liu, 7981769
 * @version      2025/3/20
 *
 * PURPOSE: Provides a map containing lakes and portages, 
 *          and functions of loading and searching route between lakes
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javax.naming.LinkException;

public class Map{
    private String name;
    private ArrayList<Lake> lakes;

    public Map(String name){
        this.name = name;
        this.lakes = new ArrayList<Lake>();
    }

    /**
     * PURPOSE: Adds a new lake to the map if it does not already exist.
     *
     *
     * @param lakeName     The name of the lake.
     * @param waterQuality The water quality description.
     * @return true if the lake was added; false otherwise.
     */
    public boolean addLake(String lakeName, String waterQuality){
        boolean added = true;
        if(findLake(lakeName) != null){
            added = false;
        }
        else{
            lakes.add(new Lake(lakeName, waterQuality));
        }
        return added;
    }

    /**
     * PURPOSE: Finds a lake in the map by its name.
     *
     * A linear search is used here as the dataset is expected to be small,
     * keeping the implementation simple.
     *
     * @param lakeName The name of the lake to search for.
     * @return the Lake object if found; null otherwise.
     */
    public Lake findLake(String lakeName){
        Lake found = null;
        // search for matching lake
        for(int i = 0; i < lakes.size(); i++){
            if(lakes.get(i).getName().equals(lakeName)){
                found = lakes.get(i);
                break;
            }
        }
        return found;
    }
    /**
     * PURPOSE: Adds a portage between two lakes.
     *
     * The portage is added to both lakes to represent bidirectional connectivity.
     * This ensures that each lake's record reflects all its direct connections.
     *
     * @param lake1Name Name of the first lake.
     * @param lake2Name Name of the second lake.
     * @param distance  Distance of the portage.
     * @return true if the portage was successfully added; false otherwise.
     */
    public boolean addPortage(String lake1Name, String lake2Name, double distance){
        boolean added = true;
        Lake lake1 = findLake(lake1Name);
        Lake lake2 = findLake(lake2Name);
        if (lake1 != null && lake2 != null){
            Portage p = new Portage(lake1, lake2, distance);
            lake1.addPortage(p);
            lake2.addPortage(p);
        }
        else{
            added = false;
            System.out.println("Failed to add portage!");
        }
        return added;
    }

    /**
     * PURPOSE: Calculates the total distance of all unique portages in the map.
     * @return the total portage distance.
     */
    public double getTotalPortageDistance(){
        double total = 0.0;
        // use a helper list to avoid counting same portage twice
        ArrayList<Portage> counted = new ArrayList<Portage>();
        for(int i = 0; i < lakes.size(); i++){
            // get the list of portages for the current lake
            ArrayList<Portage> currenPortages = lakes.get(i).getPortageList();
            for(int j = 0; j < currenPortages.size(); j++){
                // if the current portage has not been counted, add the distance
                if(!counted.contains(currenPortages.get(j))){
                total += currenPortages.get(j).getDistance(); 
                // add the current portage to counted portage list
                counted.add(currenPortages.get(j));
                }  
            }
        }
        return total;
    }

    public String toString(){
        String result = name + " contains the lakes:\n";
        for(int i = 0; i < lakes.size(); i++){
            result += lakes.get(i).toString() + "\n";
        }
        return result;
    }

    public void loadLakes(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();
            while(line != null){
            String[] tokens = line.split(",");
            int correct = 2;
            int nameIndex = 0;
            int qualityIndex = 1;
            // when the tokens have the correct length of 2
            if(tokens.length == correct){
                String lakeName = tokens[nameIndex].trim();
                String wateQuality = tokens[qualityIndex].trim();
                // add lake to the map
                addLake(lakeName, wateQuality);
                // find the lake object based on its name
                Lake finded = findLake(lakeName);
                // if the lake already exists
                if(finded != null){
                    System.out.println("A lake with the name " + lakeName + " already\r\n" + //
                                                "exists! Not adding again." );
                }
            }
            line = br.readLine();
        }
        } catch (IOException ioe){
            System.out.println(ioe.getMessage());
        }
    }

    public void loadPortages(String filename){
        try(BufferedReader br = new BufferedReader(new FileReader(filename))){
            String line = br.readLine();
            while(line != null){
                String[] tokens = line.split(",");
                int correct = 3; // the correct length of the line
                // initialize the index of each part of the line
                int lake1Name = 0;
                int lake2Name = 1;
                int distanceIndex = 2;
                if(tokens.length == correct){
                    String lake1 = tokens[lake1Name].trim();
                    String lake2 = tokens[lake2Name].trim();
                    Double distance = 0.0;
                    try {
                        distance  = Double.parseDouble(tokens[distanceIndex]);
                    } catch (NumberFormatException e) {
                        // skip the line 
                        System.out.println("Error in number field. Ignoring the line!");
                        line = br.readLine();
                        continue;
                    }
                    // add portage using extraced values
                    addPortage(lake1, lake2, distance);
                }
                line = br.readLine();
            } 
        }catch (IOException ioe){
                System.out.println(ioe.getMessage());
            }
        }

    /**
     * PURPOSE: Checks if a direct portage exists between two specified lakes.
     * 
     * A simple linear search through one lake's portage list meets the requirement
     * to quickly verify direct connectivity
     * 
     * @param lake1Name Name of the first lake.
     * @param lake2Name Name of the second lake.
     * @return true if a portage exists; false otherwise.
     */
        public boolean portageExists(String lake1Name, String lake2Name){
            // find the lake objects with provided names
            Lake lake1 = findLake(lake1Name);
            Lake lake2 = findLake(lake2Name);
            // if either lake is missing, then there won't be any portages exist
            if(lake1 == null || lake2 == null){
                return false;
            }
            // get the list of all portages of the first lake
            ArrayList<Portage> lake1List = lake1.getPortageList();
            for(int i = 0; i < lake1List.size(); i++){
                // check if the other connected lake matches
                String otherEnd = lake1List.get(i).otherEnd(lake1).getName();
                if(otherEnd.equals(lake2Name)){
                    return true;
                }
            }
            return false;
        }
    /**
     * PURPOSE: Checks if there is any route between two specified lakes.
     *
     * This method delegates to a recursive helper to determine if a route exists.
     * Recursion simplifies the route search problem by breaking it down into subproblems.
     *
     * @param lake1Name Name of the starting lake.
     * @param lake2Name Name of the destination lake.
     * @return true if a route exists; false otherwise.
     */
        public boolean routeExists(String lake1Name, String lake2Name){
            // find if a route exists recursively using helper method
            return routeExistsHelper(lake1Name, lake2Name, null);
        }

/**
 * The helper method for checking if route exists using recursion
 *
 * @param lake1Name  the name of the start lake
 *                        
 * @param lake2Name the name of the destination lake
 *
 * @param visited the list to keep track of the lakes that have been visited
 * @return        true if a route exists, false otherwise
 */
        private boolean routeExistsHelper(String lake1Name, String lake2Name, ArrayList<String> visited){
            // Base step
            if(visited == null){
                visited = new ArrayList<String>();
            }
            if(lake1Name.equals(lake2Name)){
                return true;
            }
            // add start lake to the list of visited lakes
            visited.add(lake1Name);
            Lake current = findLake(lake1Name);
            if(current == null){
                return false;
            }
            // get the list of all portages of the start lake
            ArrayList<Portage> startPortages = current.getPortageList();
            for(int i = 0; i < startPortages.size(); i++){
                Portage p = startPortages.get(i);
                Lake other = p.otherEnd(current);
                // recursive step
                if(other != null && !visited.contains(other.getName())){
                    // move to the next lake(the other end of the start lake)
                    if(routeExistsHelper(other.getName(), lake2Name, visited)){
                        return true;
                    }
                }
            }
            return false;
        }
    /**
     * PURPOSE: Retrieves all possible routes between two specified lakes.
     *
     * The method uses recursion to explore every possible route.
     * This comprehensive search allows park staff to view all travel options.
     *
     * @param lake1Name Name of the starting lake.
     * @param lake2Name Name of the destination lake.
     * @return ArrayList of Route objects representing all possible routes.
     */
        public ArrayList<Route> getAllRoutes(String lake1Name, String lake2Name){
            ArrayList<Route> routes = new ArrayList<Route>(); // list to hold all routes
            getAllRoutesHelper(lake1Name, lake2Name, null, null, routes);
            return routes;
        }

    /**
     * PURPOSE: get all routes between passed lakes using recusion
     * @param lake1Name Name of the start lake.
     * @param lake2Name Name of the destination lake.
     * @param visited list to keep track of the lakes that have been visited
     * @param current list to keep track of current portages in the route
     * @param routes list of the found routes
     */
        private void getAllRoutesHelper(String lake1Name, String lake2Name, ArrayList<String> visited, ArrayList<Portage> current, ArrayList<Route> routes){
            // Base step
            if(visited == null){
                visited = new ArrayList<String>();
            }
            if(current == null){
                current = new ArrayList<Portage>();
            }
            if(lake1Name.equals(lake2Name)){
                Route newRoute = new Route(lake2Name, new ArrayList<>(current));
                routes.add(newRoute);
                return;
            }
            // add current lake to visited list
            visited.add(lake1Name);
            // find the current lake object
            Lake lake1 = findLake(lake1Name);
            // loop through each portage leaving the current lake
            for(int i = 0; i < lake1.getPortageList().size(); i++){
                Portage p = lake1.getPortageList().get(i);
                // find the other end lake of the current lake
                Lake other = p.otherEnd(lake1);
                if(other != null && !visited.contains(other.getName())){
                    current.add(p);
                    // search for all routes recursively
                    getAllRoutesHelper(other.getName(), lake2Name, visited, current, routes);
                    // backtrack to explore other paths
                    current.remove(current.size() - 1);
                }
            }
            // after getting all routes, remove the current lake
            visited.remove(lake1Name);
        }
    }
