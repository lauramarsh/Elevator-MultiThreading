//Lab4 ElevatorSimulation class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;

public class ElevatorSimulation extends Thread
{
	private int simLength;
	private int simSecond;
	private ArrayList<ArrayList<PassengerArrival>> pB = new ArrayList<ArrayList<PassengerArrival>>();
	
	
	public void start()
	{
		BuildingManager BM = new BuildingManager();
		readText();
		
		Elevator E0 = new Elevator(0, BM); //RUNNABLE
		Elevator E1 = new Elevator(1, BM);
		Elevator E2 = new Elevator(2, BM);
		Elevator E3 = new Elevator(3, BM);
		Elevator E4 = new Elevator(4, BM);
		
		Thread t1 = new Thread(E0);
		Thread t2 = new Thread(E1);
		Thread t3 = new Thread(E2);
		Thread t4 = new Thread(E3);
		Thread t5 = new Thread(E4);
		
		t1.start();
		t2.start();
		t3.start();
		t4.start();
		t5.start();
		
		while (SimClock.getTime() <= simLength)
		{
			for (int i = 0; i < pB.size(); ++i)
			{
				for (int j = 0; j < pB.get(i).size(); ++j)
				{
					if (SimClock.getTime() == pB.get(i).get(j).getExpectedTimeOfArrival())
					{
						BM.setArrival(i, pB.get(i).get(j).getDestinationFloor(), pB.get(i).get(j).getNumPassengers());
						pB.get(i).get(j).setExpectedTimeOfArrival();
					}
				}
			}
			try
			{
				Thread.sleep(simSecond);
			}
			catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			SimClock.tick();
		}
		
		String buildingString = setBuildingState(simLength, simSecond, E0, E1, E2, E3, E4, BM);
		t1.interrupt();
		t2.interrupt();
		t3.interrupt();
		t4.interrupt();
		t5.interrupt();
		printBuildingState(buildingString);
	}
	
	private String setBuildingState(int simLength, int simSecond, Elevator e0, Elevator e1, Elevator e2, Elevator e3, Elevator e4, BuildingManager bm)
	{
		String buildingString = new String("\n\nThis elevator simulation ran for " + Integer.toString(simLength)
		+ " simulated seconds where" + " each simulated second is equal to " + Integer.toString(simSecond) 
		+ " real time miliseconds.\n\n" + "---------------------------------------- FLOOR STATES ----------------------------------------\n");
		for (int i=0; i < 5; ++i)
		{
			int totalPass = 0;
			int totalExit = 0;
			int currentPass = 0;
			int currentElev = bm.getBuildingFloors()[i].getApproachingElevator();
			for (int j=0; j < 5; ++j)
			{
				totalPass += bm.getBuildingFloors()[i].getTotalDestinationRequests()[j];
				totalExit += bm.getBuildingFloors()[i].getArrivedPassengers()[j];
				currentPass += bm.getBuildingFloors()[i].getPassengerRequests()[j];
			}
			buildingString += "Floor " + Integer.toString(i) +  "\t| Total number of passengers requesting access: " + Integer.toString(totalPass)
			+ "\n\t| Total number of passengers that exited an elevator on this floor: " + Integer.toString(totalExit)
			+ "\n\t| Current number of passengers waiting for an elevator on the floor: " + Integer.toString(currentPass)
			+ "\n\t| Elevator number currently heading towards this floor: " + Integer.toString(currentElev)
			+ "\n-----------------------------------------------------------------------------------------------\n";
		}
		
		buildingString += "\n-------------------------------------- ELEVATOR STATES ----------------------------------------\n";
		Elevator[] elevArray= {e0, e1, e2, e3, e4};
		for (int i=0; i < 5; ++i)
		{
			buildingString += "Elevator " + Integer.toString(i) +  "\t| Total number of passengers that entered the elevator: " + Integer.toString(elevArray[i].getLoadedPassengers())
			+ "\n\t\t| Total number of passengers that exited the elevator: " + Integer.toString(elevArray[i].getUnloadedPassengers())
			+ "\n\t\t| Current number of passengers heading to any floor: " + Integer.toString(elevArray[i].getNumPassengers())
			+ "\n-----------------------------------------------------------------------------------------------\n";
		}
		return buildingString;
	}
	
	public void printBuildingState(String buildingString)
	{
		System.out.println(buildingString);		
	}
	
	public void readText()
	{
		Scanner inFile = null;
		ArrayList<ArrayList<PassengerArrival>> passBehav = new ArrayList<ArrayList<PassengerArrival>>();

		try
		{
			inFile = new Scanner(new File("ElevatorConfig.txt"));
			String Line;
			simLength = inFile.nextInt();
			simSecond = inFile.nextInt();
			inFile.nextLine(); //advances that one extra token that was missed
			
			while (inFile.hasNextLine())
			{
				ArrayList<PassengerArrival> currentFloorBehavior = new ArrayList<PassengerArrival>();
				Line = inFile.nextLine();
				String[] tokens = Line.split(";");
				
				for (int i = 0; i< tokens.length; i++)
				{
					String[] innerTokens = tokens[i].split(" ");
					PassengerArrival PA = new PassengerArrival(innerTokens[0], innerTokens[1], innerTokens[2]);
					currentFloorBehavior.add(PA);
				}
				passBehav.add(currentFloorBehavior);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		inFile.close();
		pB = passBehav;
	}
}