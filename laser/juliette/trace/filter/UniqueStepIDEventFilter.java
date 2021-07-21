package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.Event.Annotation;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.StateChangeEvent;


/**
 * The UniqueStepIDEventFilter class accepts all StateChangeEvents with the 
 * specified unique step ID. For a given step, this class returns all of 
 * its step life-cycle events.
 */
public class UniqueStepIDEventFilter implements EventFilter 
{	
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = -6798571716533949362L;
	
	private String uniqueStepID_;
	
	
	public UniqueStepIDEventFilter(String uniqueStepID) {
		super();
		
		// Check preconditions
		if ((uniqueStepID == null) || uniqueStepID.isEmpty()) {
			throw new IllegalArgumentException("The unique step ID must be non-null and non-empty.");
		}
		
		this.uniqueStepID_ = uniqueStepID;
	}
	
	@Override
	public boolean accept(Event event) 
	{		
		if (event instanceof StateChangeEvent) {
			StateChangeEvent stateChangeEvent = (StateChangeEvent)event;
			Annotation uniqueStepIDAnnotation = stateChangeEvent.getAnnotation(StateChangeEvent.UNIQUE_STEP_ID_ANNOTATION_NAME);
			
			if (uniqueStepIDAnnotation != null) {
				if (uniqueStepIDAnnotation.getValue().equals(this.uniqueStepID_)) {
					return true;
				}
			}
		}
		
		return false;
	}
}
