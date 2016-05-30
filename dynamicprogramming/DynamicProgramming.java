/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dynamicprogramming;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.regex.Pattern;
import java.util.Arrays;

/**
 *
 * @author Tomek
 */
public class DynamicProgramming {

    class Edge {
        final long departure;
        final long arrival;
        
        public Edge (String departureTime, String arrivalTime) throws Exception
        {
            String[] time;
            if (departureTime.contains(":") && arrivalTime.contains(":"))
                        {
                            time = departureTime.split(":");
                        
                            if ((Integer.parseInt(time[0]) <= 24 && Integer.parseInt(time[0]) >= 0) && (Integer.parseInt(time[1]) >= 0 && Integer.parseInt(time[1]) < 60))
                            {
                            
                                departure = Integer.parseInt(time[0])*60*60*1000+Integer.parseInt(time[1])*60*1000;
                                time = arrivalTime.split(":");
                                arrival = Integer.parseInt(time[0])*60*60*1000+Integer.parseInt(time[1])*60*1000;
                            }
                            else
                            {
                                throw (new Exception("Exceeded time format error"));
                            }
                        }
                        else
                        {
                            throw (new Exception("Wrong time format error"));
                        }
        }
    }
    
    private Edge[][] graph;
    private ArrayList<String> cities;
    private String[] nextCity; // Array stores information about the next city to go from city mapped as array index
    private Long[] shortestTimeFromCityToDestination; // contains most recently calculated information about shortest time
    private ArrayList<String> visitedNodes = new ArrayList();
    private int recursionDepth = 0;
    private String departureCity;
    private String arrivalCity;
    /**
     * @param args the command line arguments
     */
    
    void readGraph (String scheduleName)
    {
        File schedule = new File(scheduleName);
        if(schedule.exists())
        {
            BufferedReader f1 = null;
            String line;
            try
            {
                f1 = new BufferedReader(new FileReader(schedule));
                
                // reading city names into list of cities
                line = f1.readLine();
                Pattern space = Pattern.compile(" ");
                cities =new ArrayList (Arrays.asList(space.split(line)));
                
                graph = new Edge[cities.size()][cities.size()];
                nextCity = new String[cities.size()];
                shortestTimeFromCityToDestination = new Long[cities.size()];
                
                // initialize V table and table of next cities (predecesstors in inverted order)
                for (int i=0;i<cities.size();i++)
                {
                    shortestTimeFromCityToDestination[i] = Long.MAX_VALUE;
                    nextCity[i] = null;      
                }
                
                for ( int i=0; i<cities.size();i++)
                {
                    graph[i][i] = new Edge("0:0","0:0"); // There is 0 distance from departure to arrival in the same city
                }
                
                // Reading schedule into graph
                String[] record; // Stores splitted string from line in the file
                                 // record[0] - String : cityOfDeparture
                                 // record[1] - String : cityOfArrival 
                                 // record[2] - String : timeOfDeparture
                                 // record[3] - String : timeOfArrival
                while ((line = f1.readLine()) != null)
                {
                    record = space.split(line);
                    int dIndex = -1;
                    int aIndex = -1;
                    if (((dIndex = cities.indexOf(record[0])) != -1) && (aIndex = cities.indexOf(record[1])) != -1 )   
                    {
                        if (graph[dIndex][aIndex] == null)
                        {
                            graph[dIndex][aIndex] = new Edge(record[2],record[3]);
                        }
                    }
                    else
                    {
                        throw(new Exception("No such city in the graph error"));
                    }
                    }
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    
    public void printTripTime()
    {
        long hour = 1000*60*60;
        long min = 1000*60;
        long milis = shortestTimeFromCityToDestination[cities.indexOf(departureCity)];
        long mins = (milis%hour)/min;
        long hours = (milis - (milis%hour))/hour;
        System.out.println("Podroz zajela "+hours+" godzin i " + mins + " minut.");
    }
    public void dynamicProgrammingAlgorith(String cityOfDeparture, String cityOfArrival)
    {
        departureCity = cityOfDeparture;
        arrivalCity = cityOfArrival;
        visitedNodes = new ArrayList();
        recursionDepth = 0;
        findShortestPath(cityOfDeparture,cityOfArrival);
        
    }
    private void findShortestPath(String cityOfDeparture, String cityOfArrival) // cityOfDeparture - startCity, cityOfArrival - endCity
    {
        visitedNodes.add(cityOfArrival);
        ArrayList<String> stageList = new ArrayList();
        int dIndex = -1;
        int aIndex = -1;
        long distance = 0;
        //ArrayList<String> shortestPathFromDepartureToArrival = new ArrayList();

        try
        {
            if ((dIndex = cities.indexOf(cityOfDeparture)) != -1 && (aIndex = cities.indexOf(cityOfArrival)) != -1)
            {
                //shortestPathFromDepartureToArrival.add(cities.get(cities.indexOf(cityOfArrival)));
                if (recursionDepth == 0)
                {
                    nextCity[aIndex] = cities.get(cities.indexOf(cityOfArrival));
                    shortestTimeFromCityToDestination[aIndex] = 0l;
                }
                
                if ( cityOfDeparture.equals(cityOfArrival))
                {
                    return;
                }
                else
                {
                    for (int i=0;i<cities.size();i++)
                    {
                        int indexOfNextCity = cities.indexOf(nextCity[cities.indexOf(cityOfArrival)]); // index of city where one should go from present city
                        int cityIndex = cities.indexOf(cityOfArrival);
                        
                        if (graph[i][cityIndex] == null || i == cities.indexOf(cityOfArrival))
                            continue;
                        
                        if (cityIndex != indexOfNextCity)
                        {
                            long directDistance = graph[i][cityIndex].arrival - graph[i][cityIndex].departure;
                            if (directDistance < 0)
                                directDistance +=24*60*60*1000;
                            long waitingforNextTrain = graph[cityIndex][indexOfNextCity].departure - graph[i][cityIndex].arrival;
                            if (waitingforNextTrain < 0)
                                waitingforNextTrain += 24*60*60*1000;
                            distance = directDistance + waitingforNextTrain;
                        }
                        else
                        {
                            distance = graph[i][cityIndex].arrival - graph[i][cityIndex].departure; // if nextCity is last city, the distance is calculated as time from departure to arrival
                        }
                        if ( distance < 0) // it means that the departure is on the next day
                        {
                            distance += 24*60*60*1000; // Adding next day to distance
                        }
                        
                        if (distance + shortestTimeFromCityToDestination[cityIndex] < shortestTimeFromCityToDestination[i])
                        {
                            shortestTimeFromCityToDestination[i] = distance + shortestTimeFromCityToDestination[cityIndex];
                            nextCity[i] = cities.get(cityIndex);
                        }
                        if (!visitedNodes.contains(cities.get(i)))
                        {
                            stageList.add(cities.get(i));
                        }
                    }
                    for (String cityToAnalyse : stageList)
                    {
                        recursionDepth++;
                        findShortestPath(cityOfDeparture, cityToAnalyse);
                    }                    
                }
            }
            else
            {
                throw (new Exception("City of departure or arrival not in the graph error"));
            }
        }catch(Exception e)
        {
            e.printStackTrace();
        }
        //recursionDepth = 0;
        //visitedNodes = new ArrayList();
        return;
    }
    
    public static void main(String[] args) {
        DynamicProgramming alg = new DynamicProgramming();
        alg.readGraph("train.txt");
        alg.dynamicProgrammingAlgorith("Olsztyn", "ZielonaGora");
        alg.printTripTime();
    }
    
}
