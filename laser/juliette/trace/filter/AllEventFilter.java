package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.Event.Annotation;


public class AllEventFilter implements EventFilter 
{
	public AllEventFilter() {
		super();
	}
	
	@Override
	public boolean accept(Event event) {
		return true;
	}

}
