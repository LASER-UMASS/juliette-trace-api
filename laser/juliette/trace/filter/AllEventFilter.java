package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;


public class AllEventFilter implements EventFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = -4901105032005475804L;

	
	public AllEventFilter() {
		super();
	}
	
	@Override
	public boolean accept(Event event) {
		if (event == null) {
			return false;
		}
		
		return true;
	}

}
