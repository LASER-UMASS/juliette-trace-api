package cardiacsurgery.trace;

import java.io.IOException;


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
		long currentTimestamp = 0; //TODO: Parse "February 21, 2018 9:33:57 AM"
		StateChangeEvent currentEvent = this.createStateChangeEvent(AUTO_AGENT_NAME, "performIsolatedCABG", State.started, currentTimestamp, previousEvent);
		
		// Step 2:
		previousEvent = currentEvent;
		currentTimestamp = 0; // See above
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
