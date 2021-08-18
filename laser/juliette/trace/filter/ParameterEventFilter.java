package laser.juliette.trace.filter;

import java.util.Map;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;
import laser.juliette.trace.StateChangeEvent;


/**
 * The ParameterEventFilter class accepts all StateChangeEvents with 
 * Parameters of interest to the user (as specified by the given 
 * parameter filter).
 */
public class ParameterEventFilter implements EventFilter 
{
	/**
	 * Automatically generated serial version UID 
	 */
	private static final long serialVersionUID = -2454153916252957369L;
	
	/** Specifies the Parameters of interest */
	private ParameterFilter parameterFilter_;
	
	
	/**
	 * Creates a new ParameterEventFilter where the user specifies the 
	 * parameters of interest with the given filter.
	 * 
	 * PRECONDITIONS: The parameter filter must be non-null.
	 * 
	 * @param parameterFilter Specifies the Parameters of interest
	 */
	public ParameterEventFilter(ParameterFilter parameterFilter) {
		//TODO) Consider providing a collection of filters
		super();
		
		// Check the preconditions
		if (parameterFilter == null) {
			throw new IllegalArgumentException("The parameter filter must be non-null.");
		}
		
		this.parameterFilter_ = parameterFilter;
	}
	
	@Override
	public boolean accept(Event event) 
	{
		// This covers the null case
		if (! (event instanceof StateChangeEvent)) {
			return false;
		}
		
		// Check if the given event involves any Parameters of interest to the user
		StateChangeEvent stateChangeEvent = (StateChangeEvent)event;
		Map<String,Parameter> parametersMap = stateChangeEvent.getParameters(this.parameterFilter_);
		if ((parametersMap == null) || parametersMap.isEmpty()) {
			return false;
		}
		
		// This event has parameters of interest to the user
		return true;
	}
	
} // end of ParameterEventFilter
