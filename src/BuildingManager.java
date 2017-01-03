//Lab4 BuildingManager class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680


public class BuildingManager
{
	private BuildingFloor[] floors;
	
	public BuildingManager()
	{
		BuildingFloor zero = new BuildingFloor();
		BuildingFloor one = new BuildingFloor();
		BuildingFloor two = new BuildingFloor();
		BuildingFloor three = new BuildingFloor();
		BuildingFloor four = new BuildingFloor();
		BuildingFloor[] temp = {zero,one,two,three,four};
		floors = temp.clone();
	}
	
	public synchronized BuildingFloor[] getBuildingFloors()
	{
		return floors;
	}

	public synchronized void setArrival(int prevFloor, int destFloor, int passNum)
	{
		//prints diagnostic for the final report
		System.out.println(Integer.toString(SimClock.getTime()) 
			+ " | SPAWNING |       @ floor_" + Integer.toString(prevFloor) 
			+ " | " + Integer.toString(passNum) + " new passengers have requested to go to floor " 
			+ Integer.toString(destFloor));
		floors[prevFloor].setAP(destFloor, passNum);
		floors[prevFloor].setArrival(destFloor, passNum);
	}
	
	public synchronized int checkFloorRequests(int elevatorID)
	{
		for (int i = 0; i < 5; ++i)
		{
			for (int j = 0; j < 5; ++j)
			{
				if (floors[i].getPassengerRequests()[j] > 0 && floors[i].getApproachingElevator() == -1)
				{
					floors[i].setApproachingElevator(elevatorID);
					return i;
				}
			}
		}
		return -1;
	}
	
	public synchronized int goingUp(int floor)
	{
		int[] FPR = getFloorPassengerRequests(floor);
		int upNum = 0;
		for (int i = floor; i < 5; ++i)
		{
			if (FPR[i] > 0)
			{
				upNum += FPR[i];
			}
		}
		return upNum;
	}
	
	public synchronized int goingDown(int floor)
	{
		int[] FPR = getFloorPassengerRequests(floor);
		int downNum = 0;
		for (int i = 0; i < floor; ++i)
		{
			if (FPR[i] > 0)
			{
				downNum += FPR[i];
			}
		}
		return downNum;
	}
	
	public synchronized void loadUp(int floor)
	{
		int[] FPR = getFloorPassengerRequests(floor);
		for (int i = floor; i < 5; ++i)
		{
			if (FPR[i] > 0)
			{
				FPR[i] = 0;
			}
		}
	}
	
	public synchronized void loadDown(int floor)
	{
		int[] FPR = getFloorPassengerRequests(floor);
		for (int i = 0; i < floor; ++i)
		{
			if (FPR[i] > 0)
			{
				FPR[i] = 0;
			}
		}
	}
	
	public synchronized int[] getFloorPassengerRequests(int floor)
	{
		return floors[floor].getPassengerRequests();
	}
}