//Lab4 BuildingFloor class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680

public class BuildingFloor
{
	private int[] totalDestinationRequests;
	private int[] arrivedPassengers;
	private int[] passengerRequests;
	private int approachingElevator;
	
	public BuildingFloor()
	{
		totalDestinationRequests = new int[5]; //[0,0,0,0,0] --> [3,0,0,0,0] --> "3 people total have wanted to go to the 0th floor from this floor"
		arrivedPassengers = new int[5]; //[0,0,0,0,0] --> [0,0,10,0,0] --> "10 people arrived at this floor from the 2th elevator"
		passengerRequests = new int[5]; //[0,0,0,0,0] --> [0,5000,0,0,0] --> "5000 people currently want to go to the 1th floor from this floor" RESETS OFTEN
		approachingElevator = -1; //currently no elevator headed to this floor
	}
	
	public void setArrival(int floor, int passNum)
	{
		passengerRequests[floor] += passNum;
		totalDestinationRequests[floor] += passNum;
	}
	
	public void setAP(int floor, int passNum)
	{
		arrivedPassengers[floor] = passNum;
	}

	public void setApproachingElevator(int elev)
	{
		approachingElevator = elev;
	}
	
	public int[] getTotalDestinationRequests()
	{
		return totalDestinationRequests;
	}
	
	public int[] getArrivedPassengers()
	{
		return arrivedPassengers;
	}
	
	public int[] getPassengerRequests()
	{
		return passengerRequests;
	}
	
	public int getApproachingElevator()
	{
		return approachingElevator;
	}
}