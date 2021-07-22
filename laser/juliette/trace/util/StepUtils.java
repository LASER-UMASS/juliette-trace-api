package laser.juliette.trace.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laser.juliette.trace.Event;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.Trace;
import laser.juliette.trace.filter.UniqueStepIDEventFilter;


/**
 * The StepUtils class conceptually supports dynamic analysis of the context for a
 * given agenda item. The context includes the ancestry as well as the step life-cycle.
 */
public class StepUtils 
{
    /**
     * Returns the list of events that are the ancestors of the given agenda item.
     * The events are ordered from the root down to the given agenda item.
     *
     * @param event The event corresponding to the agenda item
     * @return The list of events that are the ancestors of that item
     */
    public static List<Event> getAncestry(Event event) {
	List<Event> ancestry = new ArrayList<Event>();
	
        Event previousEvent = event.getAncestor();
        while (previousEvent != null) {
	    ancestry.set(0, previousEvent);
            previousEvent = previousEvent.getAncestor();
        } // end while
        
        return ancestry;
    }
    
    /**
     * Returns the step life-cycle for the agenda item with the specified ID within the given trace.
     * The step life-cycle will start with the posting state for a full trace and end with one of the
     * finished (e.g., completed) states.
     *
     * @param trace The trace of interest
     * @param uniqueStepID The ID of the agenda item
     * @return The step life-cycle for the agenda item with the specified ID within the given trace
     */
    public static List<StateChangeEvent> getStepLifecycle(Trace trace, String uniqueStepID) {
	List<StateChangeEvent> stepLifecycle = new ArrayList<StateChangeEvent>();
	
	for (Iterator<Event> eventsItr = trace.iterator(new UniqueStepIDEventFilter(uniqueStepID), false);
             eventsItr.hasNext(); 
	     ) 
	    {                                                                      
		stepLifecycle.add((StateChangeEvent)eventsItr.next());               
	    } // end for eventsItr	
	
	return stepLifecycle;
    }
    
    /**
     * Returns the step life-cycle for the event corresponding to a particular agenda item within 
     * the given trace. The step life-cycle will start with the posting state for a full trace and
     * end with one of the finished (e.g., completed) states.
     *
     * @param trace The trace of interest
     * @param event The event corresponding to the agenda item of interest
     * @return The step life-cycle for the event corresponding to the particular agenda item
     *         within the given trace
     */
    public static List<StateChangeEvent> getStepLifecycle(Trace trace, StateChangeEvent event) {
	return StepUtils.getStepLifecycle(trace, event.getAnnotation(StateChangeEvent.UNIQUE_STEP_ID_ANNOTATION_NAME).getValue());
    }
}
