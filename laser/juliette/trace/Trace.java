/*
 * Copyright (c) 2006, 2020, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * A Trace is an ordered collection of <code>Events</code> that
 * represents an execution of a Little-JIL process program.
 * If  two events  happened "at the same time" (have the same timestamp)
 * the order they will be returned is undefined.
 * 
 * Traces are <code>java.lang.Iterable</code>
 */
public class Trace implements Iterable<Event>, Serializable {
    /**
     * Iterate over this trace in the order the events were added.
     * 
     * To avoid the possibility of concurrent modification, this operation
     * makes a copy of the trace before creating the iterator.
     * 
     * This is the default iterator for the trace.
     * 
     * @return the iterator
     */
    public Iterator<Event> iterator() {
        List<Event> copy = new ArrayList<Event>(trace);
        return copy.iterator();
    }
    
    /**
     * Iterate over a specified subset of the event trace. E.g., a trace
     * of the actions of a specific agent.
     * 
     * To avoid the possibility of concurrent modification, this operation
     * makes a copy of the trace before creating the iterator.
     * 
     * @param filter The filter to apply to the trace
     * @param reverse If <code>true</code>, iterate from the most recently added
     * event to the oldest
     * @return the iterator
     */
    public Iterator<Event> iterator(EventFilter filter, boolean reverse) {
        List<Event> copy = new ArrayList<Event>(trace);
        
        if (reverse) {
            Collections.reverse(copy);
        }
        
        for (Iterator<Event> i = copy.iterator(); i.hasNext(); ) {
            Event event = i.next();
            if (! filter.accept(event)) {
                i.remove();
            }
        }
        
        return copy.iterator();
    }
    
    /**
     * Add an event to this trace. Events are sorted by timestamp.
     * @param event The event to add
     */
    public void add(Event event) {
        trace.add(event);
    }
    
    public Event get(int ID) {
    	if (this.length() > 0) {
    		// The array has 0-based indices but
    		// the ID has 1-based indices.
    		Event foundEvent = this.trace.get(ID - 1);
    		if (foundEvent.getID() == ID) {
    			return foundEvent;
    		}
    	}
    	
    	for (Iterator<Event> eventsItr = this.iterator(); eventsItr.hasNext(); ) {
    		Event currentEvent = eventsItr.next();
    		
    		if (currentEvent.getID() == ID) {
    			return currentEvent;
    		}
    	} // end for eventsItr
    	
    	return null;
    }
    
    public int length() {
    	return this.trace.size();
    }
    
    /**
     * Set a trace property value.
     * @param name the property name
     * @param value the property value
     */
    public void setProperty(String name, String value) {
    	properties.put(name, value);
    }
    
    /**
     * get the value for a trace property, or <code>null</code>
     * if the value is unset.
     * @param name the property name
     * @return the property value or <code>null</code>
     */
    public String getProperty(String name) {
    	return properties.get(name);
    }
    
    /**
     * Get the names of all trace properties
     * @return the set of property names
     */
    public Set<String> getPropertyNames() {
    	return properties.keySet();
    }

    private List<Event> trace = new ArrayList<Event>(); // TODO replace with SortedSet
    private Map<String, String> properties = new HashMap<String, String>();
    
	public static final String PROCESS_NAME_PROPERTY_NAME = "processName";
	public static final String CREATED_TIMESTAMP_PROPERTY_NAME = "createdTimestamp";
	public static final String UPDATED_TIMESTAMP_PROPERTY_NAME = "updatedTimestamp";	
	
	/**
	 * The Traces, Trace, or Event may have user comments
	 */
	public static final String USER_COMMENTS_ANNOTATION_NAME = "userComments";
}
