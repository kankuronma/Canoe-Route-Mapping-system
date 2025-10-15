/**
 * Route.java
 *
 * COMP 1020 SECTION D01
 * INSTRUCTOR    Dr. Heather Matheson
 * ASSIGNMENT    Assignment 4, Phase 6
 * @author       Xinyue Liu, 7981769
 * @version      2025/3/20
 *
 * PURPOSE: provides a portage object connecting two lakes with a distance
 */

import java.util.ArrayList;

public class Route{
    private String startLakeName;
    private String endLakeName;
    private ArrayList<Portage> portages;
    

    public Route(String endLakeName, ArrayList<Portage> portages){
        // Copy the list to avoid external modifications affecting the route
        this.portages = new ArrayList<Portage>(portages);
        this.endLakeName = endLakeName;
        String otherEnd = null;
        for(int i = 0; i < portages.size() - 1; i++){
            otherEnd = portages.get(i).otherEnd(endLakeName);
        }
        this.startLakeName = otherEnd;
    }

    /**
     * PURPOSE: Calculates the total portage distance along the route.
     *
     * @return the total distance by summing each portage's distance.
     */
    public double getDistance(){
        double total = 0.0;
        for(int i = 0; i < portages.size(); i++){
            total += portages.get(i).getDistance();
        }
        return total;
    }

    public String toString(){
        String result;
        String lakes = startLakeName;
        for(int i = 0; i < portages.size(); i++){
            lakes += ", " + portages.get(i).otherEnd(startLakeName);
        }
        String distance = String.format("%.2f", getDistance());
        result = "Route from " + startLakeName + " to " + endLakeName + " is " + 
                    distance + "km "+ ": " + lakes;
        return result;
    }

    /**
     * PURPOSE: Compares this route to another route based on total portage distance.
     *
     * Routes are compared solely by the physical effort required for portages.
     * This method allows us to sort routes to identify the shortest one.
     *
     * @param secondRoute The route to compare with.
     * @return 1 if this route is longer, -1 if shorter, 0 if equal.
     */
    public int compareTo(Route secondRoute){
        Double distance1 = this.getDistance();
        Double distance2 = secondRoute.getDistance();
        int larger = 1;
        int smaller = -1;
        int equal = 0;
        if(distance1 > distance2){
            return larger;
        }
        else if(distance1 < distance2){
            return smaller;
        }
        else{
            return equal;
        }
    }

    /**
     * PURPOSE: Sorts a list of routes in ascending order based on their total portage distance.
     *
     * A selection sort is implemented explicitly to comply with the assignment rules,
     * which disallow use of built-in sort methods. This makes the sorting logic transparent.
     *
     * @param routes The ArrayList of routes to be sorted.
     */
    public static void sortRoutes(ArrayList<Route> routes){
        for(int i = 0; i < routes.size() - 1; i++){
            int minIndex = i;
            for(int j = i + 1; j < routes.size(); j++){
                if(routes.get(j).compareTo(routes.get(minIndex)) < 0){
                    minIndex = j;
                }
            }
            // swap the routes in order
            if(minIndex != i){
                Route temp = routes.get(minIndex);
                routes.set(minIndex, routes.get(i));
                routes.set(i, temp);
            }
        }
    }
}