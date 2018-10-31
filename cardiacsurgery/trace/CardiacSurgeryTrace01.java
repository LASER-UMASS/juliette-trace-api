package cardiacsurgery.trace;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;


import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.StateChangeEvent.State;


public class CardiacSurgeryTrace01 extends CardiacSurgeryTrace
{
	public CardiacSurgeryTrace01(String traceName) {
		super(traceName);
	}
	
	public void encode() throws IOException
	{
		// Encode each StateChangeEvent
		
		// Step 1: The root step STARTED
		StateChangeEvent previousEvent = null;
		Calendar currentCalendar = Calendar.getInstance();
		currentCalendar.set(2018, Calendar.FEBRUARY, 21, 9, 33, 57);
		Date currentDate = currentCalendar.getTime();
		long currentTimestamp = currentDate.getTime(); //TODO: Parse "February 21, 2018 9:33:57 AM"
		//Date is parsed in milliseconds from January 1st 1970 00:00:00GMT, or exact Midnight of January 1st 1970 in the town of Greenwich
		
		StateChangeEvent currentEvent = this.createStateChangeEvent(AUTO_AGENT_NAME, "performIsolatedCABG", State.started, currentTimestamp, previousEvent);
		
		// Step 2:
		previousEvent = currentEvent;
		currentTimestamp += 1000000; // See above
		currentEvent = this.createStateChangeEvent(AUTO_AGENT_NAME, "perform pre-incision timeout", State.started, currentTimestamp, previousEvent);
		
		// Step 3:
		
		// ...
		
		// Step N: The root step COMPLETED or TERMINATED
	}
	
	public static void main (String args[]) throws IOException {
		CardiacSurgeryTrace01 currentTrace = new CardiacSurgeryTrace01("CABG-VideoAnnotation-2-21-18");
		
		currentTrace.encode();
		
		currentTrace.save();
	}
}
