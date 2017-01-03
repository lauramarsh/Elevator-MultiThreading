//Lab4 SimClock class
//Partner1: Laura Marsh
//		ID: 28787340
//Partner2: Carl Altoveros 
//		ID: 31815680

public class SimClock
{
	private static int counter = 0;
	
	public static void tick()
	{
		++counter;
	}
	
	public static int getTime()
	{
		return counter;
	}
}
