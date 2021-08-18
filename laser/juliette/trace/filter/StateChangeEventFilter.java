package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.StateChangeEvent;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * The StateChangeEventFilter class accepts all StateChangeEvents with the
 * specified states (e.g., only posted).
 */
public class StateChangeEventFilter implements EventFilter 
{
	/**
	 * Automatically generated serial version UID 
	 */
	private static final long serialVersionUID = -8497163549952806224L;
	
	private Set<StateChangeEvent.State> statesSet_;
	
	
	/**
	 * Creates a new StateChangeEventFilter accepting StateChangeEvents with 
	 * the given states.
	 * 
	 * PRECONDITIONS: The states set must be non-null and non-empty.
	 * 
	 * @param statesSet The states of interest
	 */
	public StateChangeEventFilter(Set<StateChangeEvent.State> statesSet) {
		super();
		
		// Check preconditions
		if ((statesSet == null) || statesSet.isEmpty()) {
			throw new IllegalArgumentException("The states set must be non-null and non-empty.");
		}
		
		this.statesSet_ = new LinkedHashSet<StateChangeEvent.State>();
		this.statesSet_.addAll(statesSet);
	}
	
	@Override
	public boolean accept(Event event) 
	{	
		if (event instanceof StateChangeEvent) {
			StateChangeEvent stateChangeEvent = (StateChangeEvent)event;
			
			if (this.statesSet_.contains(stateChangeEvent.getState())) {
				return true;
			}
		}
		
		return false;
	}
}
