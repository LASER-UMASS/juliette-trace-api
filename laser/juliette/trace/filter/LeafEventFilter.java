package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.StateChangeEvent.SequencingKind;
import laser.juliette.trace.StateChangeEvent;


/**
 * The LeafEventFilter class accepts all StateChangeEvents corresponding to leaf steps.
 */
public class LeafEventFilter implements EventFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = 6742462514659916134L;

	
	public LeafEventFilter() {
		super();
	}
	
	@Override
	public boolean accept(Event event) 
	{		
		if (event instanceof StateChangeEvent) {
			StateChangeEvent stateChangeEvent = (StateChangeEvent)event;
			
			return (stateChangeEvent.getSequencingKind() == SequencingKind.leaf);
		}
		
		return false;
	}

}
