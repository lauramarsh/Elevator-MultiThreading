//Lab4 Elevator class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680

import java.util.ArrayList;

public class Elevator implements Runnable
{
	private int elevatorID;
	private int currentFloor;
	private int numPassengers;
	private int totalLoadedPassengers;
	private int totalUnloadedPassengers;
	private ArrayList<ElevatorEvent> moveQueue;
	private int[] passengerDestinations;
	private BuildingManager manager;

	public void setCurrentFloor(int floor)
	{
		currentFloor = floor;
	}
	
	public void setPassDest(int floor, int passengers)
	{
		passengerDestinations[floor] = passengers;
	}
	
	public int getElevatorID()
	{
		return elevatorID;
	}
	
	public int getCurrentFloor()
	{
		return currentFloor;
	}
	
	public int getNumPassengers()
	{
		return numPassengers;
	}
	
	public int getLoadedPassengers()
	{
		return totalLoadedPassengers;
	}
	
	public int getUnloadedPassengers()
	{
		return totalUnloadedPassengers;
	}
	
	public ArrayList<ElevatorEvent> getMoveQueue()
	{
		return moveQueue;
	}
	
	public int[] getPassengerDestinations()
	{
		return passengerDestinations;
	}

	public Elevator(int ID, BuildingManager bm)
	{
		elevatorID = ID;
		manager = bm;
		moveQueue = new ArrayList<ElevatorEvent>();
		currentFloor = 0;
		passengerDestinations = new int[5];
		totalLoadedPassengers = 0;
		totalUnloadedPassengers = 0;
	}
	
	public void updateDestinations(int floor)
	{
		int[] FPR = manager.getFloorPassengerRequests(floor);
		for (int i = 0; i < 5; ++i)
		{
			passengerDestinations[i] += FPR[i];
		}
	}
	
	
	public void run()
	{
		while (!Thread.interrupted())
		{
			if (moveQueue.size() == 0)
			{
				int pickUpFloor = manager.checkFloorRequests(this.getElevatorID());	
				if (pickUpFloor == -1)
				{
					continue;
				}
				ElevatorEvent addedEvent = new ElevatorEvent(pickUpFloor, Math.abs(currentFloor - pickUpFloor));
				moveQueue.add(addedEvent);
				continue;
			}
			else
			{
				if (SimClock.getTime() == moveQueue.get(0).getExpectedArrival())
				{
					int floor = moveQueue.get(0).getDestination();
					if (numPassengers == 0)
					{
						int upNum = manager.goingUp(floor);
						if (upNum > 0)
						{
							moveQueue.remove(0);
							currentFloor = floor;
							numPassengers = upNum;
							totalLoadedPassengers += upNum;
							int testDest = -1;
							updateDestinations(floor);
							for (int i = 0; i < 5; ++i)
							{
								if (passengerDestinations[i] > 0) //checking everything shouldn't matter, the entire array should be 0 by the time another pick up happens
								{
									ElevatorEvent addedEvent = new ElevatorEvent(i, Math.abs(i - currentFloor));
									moveQueue.add(addedEvent);
									testDest = addedEvent.getDestination();
								}
							}
							manager.loadUp(floor);
							manager.getBuildingFloors()[floor].setApproachingElevator(-1);
							//prints diagnostic for the final report
							System.out.println(Integer.toString(SimClock.getTime()) 
								+ " | PICKUP   | eID_" + Integer.toString(elevatorID) + " @ floor_" + Integer.toString(floor) 
								+ " | picked up passengers, currently heading to floor " + Integer.toString(testDest));
						}
						else
						{
							int downNum = manager.goingDown(floor);
							moveQueue.remove(0);
							currentFloor = floor;
							numPassengers = downNum;
							totalLoadedPassengers += downNum;
							updateDestinations(floor);
							int testDest = -1;
							for (int i = 0; i < 5; ++i)
							{
								if (passengerDestinations[i] > 0)
								{
									ElevatorEvent addedEvent = new ElevatorEvent(i, Math.abs(i - currentFloor));
									moveQueue.add(addedEvent);
									testDest = addedEvent.getDestination();
								}
							}
							manager.loadDown(floor);
							manager.getBuildingFloors()[floor].setApproachingElevator(-1);
							//prints diagnostic for the final report
							System.out.println(Integer.toString(SimClock.getTime()) 
								+ " | PICKUP   | eID_" + Integer.toString(elevatorID) + " @ floor_" + Integer.toString(floor) 
								+ " | picked up passengers, currently heading to floor " + Integer.toString(testDest));
						}
					}
					else
					{
						moveQueue.remove(0);
						currentFloor = floor;
						numPassengers -= passengerDestinations[floor];
						totalUnloadedPassengers += passengerDestinations[floor];
						int testPN = passengerDestinations[floor];
						passengerDestinations[floor] = 0;
						//prints diagnostic for the final report
						System.out.println(Integer.toString(SimClock.getTime()) 
							+ " | DROPOFF  | eID_" + Integer.toString(elevatorID) + " @ floor_" + Integer.toString(floor) 
							+ " | dropped off " + Integer.toString(testPN) + " passengers");
					
					}
				}
			}
		}
	}
}