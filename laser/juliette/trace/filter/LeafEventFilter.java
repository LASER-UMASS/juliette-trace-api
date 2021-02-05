package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.StateChangeEvent.SequencingKind;
import laser.juliette.trace.StateChangeEvent;


public class LeafEventFilter implements EventFilter 
{
	public LeafEventFilter() {
		super();
	}
	
	@Override
	public boolean accept(Event event) {
		if (event instanceof StateChangeEvent) {
			StateChangeEvent stateChangeEvent = (StateChangeEvent)event;
			
			return (stateChangeEvent.getSequencingKind() == SequencingKind.leaf);
		}
		
		return false;
	}

}
