//Lab4 ElevatorEvent class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680

public class ElevatorEvent
{
	private int destination;
	private int expectedArrival;
	
	public ElevatorEvent(int floor, int floorDifference)
	{
		destination = floor;
		expectedArrival = SimClock.getTime() + floorDifference*5 + 20;
	}
	
	public void setDestination(int dest)
	{
		destination = dest;
	}
	
	public int getDestination()
	{
		return destination;
	}
	
	public int getExpectedArrival()
	{
		return expectedArrival;
	}
	
	public void setExpectedArrival(int FloorDifference)
	{
		expectedArrival = SimClock.getTime() + FloorDifference*5;
	}
}
