package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.StateChangeEvent;

import java.util.LinkedHashSet;
import java.util.Set;


/**
 * The HighLevelStateChangeEventFilter class accepts all StateChangeEvents 
 * corresponding to the high-level state changes (i.e. ending in 'ed').
 */
public class HighLevelStateChangeEventFilter implements EventFilter 
{
	/**
	 * Automatically generated serial version UID 
	 */
	private static final long serialVersionUID = 3727496644792284058L;
	
	private StateChangeEventFilter highLevelStateChangeEventFilter_;
	
	
	public HighLevelStateChangeEventFilter() {
		super();
		
		Set<StateChangeEvent.State> highLevelStateChangesSet = new LinkedHashSet<StateChangeEvent.State>();
		// The user commands
		highLevelStateChangesSet.add(StateChangeEvent.State.posted);
		highLevelStateChangesSet.add(StateChangeEvent.State.optedout);
		highLevelStateChangesSet.add(StateChangeEvent.State.started);
		highLevelStateChangesSet.add(StateChangeEvent.State.completed);
		highLevelStateChangesSet.add(StateChangeEvent.State.terminated);
		// The interpreter commands
		highLevelStateChangesSet.add(StateChangeEvent.State.aborted);
		highLevelStateChangesSet.add(StateChangeEvent.State.cancelled);
		highLevelStateChangesSet.add(StateChangeEvent.State.retracted);
		this.highLevelStateChangeEventFilter_ = new StateChangeEventFilter(highLevelStateChangesSet);
	}
	
	@Override
	public boolean accept(Event event) {
		return this.highLevelStateChangeEventFilter_.accept(event);
	}

}
