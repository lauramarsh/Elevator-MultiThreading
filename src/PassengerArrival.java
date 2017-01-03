//Lab4 PassengerArrival class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680


public class PassengerArrival
{
	private int numPassengers;
	private int destinationFloor;
	private int timePeriod;
	private int expectedTimeOfArrival;
	
	public PassengerArrival(String nP, String dF, String tP)
	{
		numPassengers = Integer.parseInt(nP);
		destinationFloor = Integer.parseInt(dF);
		timePeriod = Integer.parseInt(tP);
		expectedTimeOfArrival = timePeriod;
	}
	
	public void setExpectedTimeOfArrival()
	{
		expectedTimeOfArrival += timePeriod;
	}
		
	public int getNumPassengers()
	{
		return numPassengers;
	}
	
	public int getDestinationFloor()
	{
		return destinationFloor;
	}
	
	public int getTimePeriod()
	{
		return timePeriod;
	}
	
	public int getExpectedTimeOfArrival()
	{
		return expectedTimeOfArrival;
	}
}