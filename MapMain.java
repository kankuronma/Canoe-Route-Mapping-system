/**
 * @author       Xinyue Liu, 7981769
 * @version      2025/3/20
 *
 * PURPOSE: User interface to run the park canoe route tool
 */

import java.util.Scanner;
import java.util.ArrayList;


public class MapMain{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map map = null;
        boolean exit = false;

        while(!exit){
            // display options
            System.out.println("Welcome to the park canoe route tool!\r\n" + //
                                "Available options are:\r\n" + //
                                "\t1: Create a new map\r\n" + //
                                "\t2: Load lakes from file\r\n" + //
                                "\t3: Load portages from file\r\n" + //
                                "\t4: Print total distance of all trails in park\r\n" + //
                                "\t5: Print map info, including all lakes\r\n" + //
                                "\t6: Test for portage between two lakes\r\n" + //
                                "\t7: Test for route between two lakes\r\n" + //
                                "\t8: Find shortest route between two lakes\r\n" + //
                                "\t9: Find all routes between two lakes\r\n" + //
                                "\t0: Exit\r\n" + //
                                "Enter your choice (0-9):");
            String input = sc.nextLine();
            int choice = 0;
            try {
                choice = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid input! Try again.");
            }
            switch (choice) {
                case 1:
                // Option 1: create a new map
                    System.out.print("Please enter the name of your map: ");
                    String mapName = sc.nextLine();
                    map = new Map(mapName);
                    System.out.println("New map created.");
                    break;
                case 2:
                // Option 2: load lakes from provided file
                    System.out.print("Please enter the name of the lake file: ");
                    String lakeFile = sc.nextLine();
                    map.loadLakes(lakeFile);
                    System.out.println("Lake file processing complete.");
                    break;
                case 3:
                // Option 3: load portages from provided file
                    System.out.print("Please enter the name of the portage file: ");
                    String portageFile = sc.nextLine();
                    map.loadPortages(portageFile);
                    System.out.println("Portage file processing complete.");
                    break;
                case 4:
                // Option 4: print the total distance of all portages
                    double distance = map.getTotalPortageDistance();
                    String distanceString = String.format("%.2f", distance);
                    System.out.println("The total distance of trails in the park is " + distanceString + " km.");
                    break;
                case 5:
                // Option 5: print detailed map information
                    System.out.println(map.toString());
                    break;
                case 6:
                // Option 6: check if a portage exists between two lakes
                    System.out.print("Please enter the name of the first lake: ");
                    String lake1Name = sc.nextLine();
                    System.out.print("Please enter the name of the second lake: ");
                    String lake2Name = sc.nextLine();
                    boolean found = map.portageExists(lake1Name, lake2Name);
                    if(found){
                        System.out.println("There is a portage between " + lake1Name + " and " + lake2Name);
                    }
                    else{
                        System.out.println("There is not a portage between " + lake1Name + " and " + lake2Name);
                    }
                    break;
                case 7:
                // Option 7: check if a route exists between two lakes
                    System.out.print("Please enter the name of the first lake: ");
                    String start = sc.nextLine();
                    System.out.print("Please enter the name of the second lake: ");
                    String destination = sc.nextLine();
                    boolean routeFound = map.routeExists(start, destination);
                    if(routeFound){
                        System.out.println("There is a route between " + start + " and " + destination);
                    }
                    else{
                        System.out.println("There is not a route between " + start + " and " + destination);
                    }
                    break;
                case 8:
                // Option 8: find the shortest route
                    System.out.print("Please enter the name of the first lake: ");
                    String lake1 = sc.nextLine();
                    System.out.print("Please enter the name of the second lake: ");
                    String lake2 = sc.nextLine();
                    ArrayList<Route> allRoutes = map.getAllRoutes(lake1, lake2);
                    Route.sortRoutes(allRoutes);
                    Route shortest = allRoutes.get(0);
                    System.out.println("The shortest route is: " + shortest.toString());
                    break;
                case 9:
                // Option 9: find all routes
                    System.out.print("Please enter the name of the first lake: ");
                    String startLake = sc.nextLine();
                    System.out.print("Please enter the name of the second lake: ");
                    String destinationLake = sc.nextLine();
                    ArrayList<Route> routes = map.getAllRoutes(startLake, destinationLake);
                    System.out.println("All routes between Geejay and Eagle are:");
                    for(int i = 0; i < routes.size(); i++){
                        System.out.println(routes.get(i).toString());
                    }
                    break;
                case 0:
                // Option 0: exit
                    exit = true;
                    System.out.println("Goodbye!");
                    break;
                default:
                // When user type invalid input
                    System.out.println("Invalid input! Try again.");
            }
        }
        sc.close();
    }
}