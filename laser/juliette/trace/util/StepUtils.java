package laser.juliette.trace.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.ReactiveController;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.Trace;
import laser.juliette.trace.filter.AllEventFilter;
import laser.juliette.trace.filter.UniqueStepIDEventFilter;


/**
 * The StepUtils class conceptually supports dynamic analysis of the context for a
 * given agenda item. The context includes the ancestry as well as the step life-cycle.
 */
public class StepUtils 
{
	/**
	 * Returns the list of events that are the ancestors of the given agenda item.
	 * 
	 * NOTES)
	 *   If the filter is null, then the AllEventFilter will be used.
	 *   The events are ordered from the root down to the given agenda item.
	 *
	 * @param event The event corresponding to the agenda item
	 * @param filter The filter for which events should be returned
	 * @return The list of events that are the ancestors of that item
	 */
	public static List<Event> getAncestry(Event event, EventFilter filter) {
		List<Event> ancestry = new ArrayList<Event>();

		if (filter == null) {
			filter = new AllEventFilter();
		}
		
		Event previousEvent = event.getAncestor();
		while (previousEvent != null) {
			if (filter.accept(previousEvent)) {
				ancestry.add(0, previousEvent);
			}
			previousEvent = previousEvent.getAncestor();
		} // end while

			return ancestry;
	}
	
	/**
	 * Returns the list of events that are the immediate children of the given 
	 * agenda item. 
	 * 
	 * NOTES) 
	 *   If the filter is null, then the AllEventFilter will be used.
	 *   The events are ordered from the oldest to the newest agenda item.
	 * 
	 * @param trace The trace containing the ancestor event
	 * @param ancestorEvent The ancestor event of interest
	 * @param filter The filter for which events should be returned
	 * @return The list of events that are the children of the given agenda item
	 */
	public static List<Event> getChildren(Trace trace, Event ancestorEvent, EventFilter filter) {
		int ancestorEventID = ancestorEvent.getID(); 
		List<Event> children = new ArrayList<Event>();

		if (filter == null) {
			filter = new AllEventFilter();
		}
		
		for (Iterator<Event> eventsItr = trace.iterator(); eventsItr.hasNext(); ) {
			Event currentEvent = eventsItr.next();
			Event currentAncestorEvent = currentEvent.getAncestor();
			
			if ((currentAncestorEvent != null) && (currentAncestorEvent.getID() == ancestorEventID) &&
				filter.accept(currentEvent)) 
			{
				children.add(currentEvent);
			}
			else if ((ancestorEvent instanceof StateChangeEvent) &&
					 (currentEvent instanceof StateChangeEvent) &&
					 StepUtils.isParentStep((StateChangeEvent)ancestorEvent, (StateChangeEvent)currentEvent) &&
					 filter.accept(currentEvent)) 
			{
				children.add(currentEvent);
			}
		} // end for eventsItr
		
		return children;
	}
	
	/**
	 * Returns the unique step ID for the given agenda item.
	 * 
	 * @param event The event corresponding to the agenda item
	 * @return The unique step ID for that agenda item
	 */
	public static String getUniqueStepID(StateChangeEvent event) {
		return event.getAnnotation(StateChangeEvent.UNIQUE_STEP_ID_ANNOTATION_NAME).getValue();
	}
	
	/**
	 * Returns whether or not the given agenda item corresponds to a leaf step.
	 * 
	 * @param event The event corresponding to the agenda item
	 * @return True if that agenda item corresponds to a leaf step and false otherwise
	 */
	public static boolean isLeaf(StateChangeEvent event) {
		return (event.getSequencingKind() == StateChangeEvent.SequencingKind.leaf);
	}

	/**
	 * Returns whether or not the given agenda item corresponds to a non-leaf step.
	 * 
	 * @param event The event corresponding to the agenda item
	 * @return True if that agenda item corresponds to a non-leaf step and false otherwise
	 */
	public static boolean isNonLeaf(StateChangeEvent event) {
		return (! StepUtils.isLeaf(event));
	}

	/**
	 * Returns whether or not the given agenda item corresponds to a sequential non-leaf step.
	 * 
	 * @param event The event corresponding to the agenda item
	 * @return True if that agenda item corresponds to a sequential non-leaf step and false otherwise
	 */
	public static boolean isSequential(StateChangeEvent event) {
		StateChangeEvent.SequencingKind seqKind = event.getSequencingKind();
		
		return ((seqKind == StateChangeEvent.SequencingKind.nonleaf_seq) ||
				(seqKind == StateChangeEvent.SequencingKind.nonleaf_try));
	}
	
	/**
	 * Returns whether or not the given agenda item corresponds to a concurrent non-leaf step.
	 * 
	 * @param event The event corresponding to the agenda item
	 * @return True if that agenda item corresponds to a concurrent non-leaf step and false otherwise
	 */
	public static boolean isConcurrent(StateChangeEvent event) {
		StateChangeEvent.SequencingKind seqKind = event.getSequencingKind();
		
		return ((seqKind == StateChangeEvent.SequencingKind.nonleaf_par) ||
				(seqKind == StateChangeEvent.SequencingKind.nonleaf_cho));		
	}
	
	/**
	 * Returns whether or not first agenda item is the parent of the second agenda item.
	 * 
	 * @param event1 The event corresponding to the first agenda item
	 * @param event2 The event corresponding to the second agenda item
	 * @return True if the first agenda item is the parent of the second agenda item
	 */
	public static boolean isParentStep(StateChangeEvent event1, StateChangeEvent event2) {
		StateChangeEvent parentEvent = (StateChangeEvent)event2.getAncestor();
		
		// The parent must be non-null.
		if ((event1 == null) || (parentEvent == null)) {
			return false;
		}
		
		// The Little-JIL execution semantics state that the parent has started 
		// and the child has posted.
		if (event1 == parentEvent) {
			return true;
		}
		
		// The parent is at one of its states and the child is at one of its states.
		String event1ID = StepUtils.getUniqueStepID(event1);
		String parentEventID = StepUtils.getUniqueStepID(parentEvent);
		if (event1ID.equals(parentEventID)) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * Returns whether or not the two agenda items are siblings.
	 * 
	 * @param event1 The event corresponding to the first agenda item
	 * @param event1 The event corresponding to the second agenda item
	 * @return True if the two agenda items are siblings and false otherwise
	 */
	public static boolean isSiblingStep(StateChangeEvent event1, StateChangeEvent event2) {
		StateChangeEvent parentEvent1 = (StateChangeEvent)event1.getAncestor();
		StateChangeEvent parentEvent2 = (StateChangeEvent)event2.getAncestor();
		
		// The parent must be non-null.
		if ((parentEvent1 == null) || (parentEvent2 == null)) {
			return false;
		}
		
		String parentEventID1 = StepUtils.getUniqueStepID(parentEvent1);
		String parentEventID2 = StepUtils.getUniqueStepID(parentEvent2);
		String stepID1 = StepUtils.getUniqueStepID(event1);
		String stepID2 = StepUtils.getUniqueStepID(event2);
		if (parentEventID1.equals(parentEventID2) && 
			(! stepID1.equals(stepID2))) 
		{
			return true;
		}
		
		return false;
	}

	/**
	 * Returns the step life-cycle for the agenda item with the specified ID within the given trace.
	 * The step life-cycle will start with the posting state for a full trace and end with one of the
	 * finished (e.g., completed) states.
	 * 
	 * NOTES) If the filter is null, then the AllEventFilter will be used.
	 *
	 * @param trace The trace containing the events corresponding to the agenda items of interest
	 * @param uniqueStepID The ID of the agenda item
	 * @param filter The filter for which events should be returned
	 * @return The step life-cycle for the agenda item with the specified ID within the given trace
	 */
	public static List<StateChangeEvent> getStepLifecycle(Trace trace, String uniqueStepID, EventFilter filter) {
		List<StateChangeEvent> stepLifecycle = new ArrayList<StateChangeEvent>();

		if (filter == null) {
			filter = new AllEventFilter();
		}
		
		for (Iterator<Event> eventsItr = trace.iterator(new UniqueStepIDEventFilter(uniqueStepID), false);
				eventsItr.hasNext(); 
				) 
		{   
			StateChangeEvent currentEvent = (StateChangeEvent)eventsItr.next();
			
			if (filter.accept(currentEvent)) {
				stepLifecycle.add(currentEvent);
			}
		} // end for eventsItr	

		return stepLifecycle;
	}

	/**
	 * Returns the step life-cycle for the event corresponding to a particular agenda item within 
	 * the given trace. The step life-cycle will start with the posting state for a full trace and
	 * end with one of the finished (e.g., completed) states.
	 * 
	 * NOTES) If the filter is null, then the AllEventFilter will be used.
	 *
	 * @param trace The trace containing the given event
	 * @param event The event corresponding to the agenda item of interest
	 * @param filter The filter for which events should be returned
	 * @return The step life-cycle for the event corresponding to the particular agenda item
	 *         within the given trace
	 */
	public static List<StateChangeEvent> getStepLifecycle(Trace trace, StateChangeEvent event, EventFilter filter) {
		return StepUtils.getStepLifecycle(trace, event.getAnnotation(StateChangeEvent.UNIQUE_STEP_ID_ANNOTATION_NAME).getValue(), filter);
	}
}
