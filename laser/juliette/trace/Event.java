/*
 * Copyright (c) 2006, 2018, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * An <code>Event</code> represents a single event in a process
 * trace. <code>Event</code> is an abstract base class for all
 * possible events.
 */
public abstract class Event implements Serializable {
    /**
     * If an event cannot be attributed to a particular agent,
     * it it should be attributed to the unknown agent.
     */
    public static final String UNKNOWN_AGENT = "*unknown-agent*";
    
    protected Event(String agent, String name, long timestamp, Event ancestor) {
    	this.agent = agent;
    	this.name = name;
        this.timestamp = timestamp;
        this.ancestor = ancestor;
    }
    
    /**
     * Get the name of the agent responsible for this event.
     * @return the responsible agent or <code>UNKNOWN_AGENT</code>
     */
    public String getAgentName() {
    	return agent;
    }
    
    /**
     * Get the name of this event
     * @return the event name
     */
    public String getEventName() {
    	return name;
    }
    
    /**
     * The timestamp for this event. Every event has a timestamp
     * that records when the event occurred. Time in a trace may
     * be abstract and not based on any notion of real time.
     * @return the timestamp
     */
    public long getTimestamp() {
        return timestamp;
    }
    
    /**
     * Events may be threaded by ancestor events. For example,
     * a 'started' step may have its corresponding 'posted' event
     * as an ancestor.
     * @return the ancestor or <code>null</code>
     */
    public Event getAncestor() {
    	return ancestor;
    }
    
    public int getID() {
    	if (id==0) {
    		id = nextID++;
    	}
    	return id;
    }
    
    /**
     * Add an annotation to this event. Annotation kinds should
     * be established in a unique namespace, to avoid conflicts.
     * For example, the kind for a FLAVERS event associated with a trace
     * event might be: 'laser.flavers.event'.
     * @param kind the annotation kind
     * @param value the annotation value
     */
    public void addAnnotation(String kind, String value) {
    	annotations.add(new Annotation(kind, value));
    }
    
    /**
     * Gets the value of the annotation with the given kind if it exists.
     * 
     * @param kind the annotation kind
     * @return The annotation value if it exists
     */
    public Annotation getAnnotation(String kind) {
    	for (Annotation currentAnnotation : annotations) {
    		if (currentAnnotation.getKind().equals(kind)) {
    			return currentAnnotation;
    		}
    	} // end for currentAnnotation
    	
    	return null;
    }
    
    /**
     * Get the list of annotations associated with this event
     * @return an unmodifiable list of the annotations
     */
    public List<Annotation> getAnnotations() {
    	return Collections.unmodifiableList(annotations);
    }
    
    /**
     * Returns a String representation of this Object.
     * 
     * @return A String representation of this Object
     */
    public String toString() {
    	String stringRep = this.getID() + ") " + this.getAgentName() + " \"" + this.getEventName() + "\"";
    	
    	return stringRep;
    }
    
    /**
     * Event annotations are stored as kind, value pairs. This class
     * represent that pair.
     */
    public static class Annotation {
    	Annotation(String kind, String value) {
    		this.kind = kind;
    		this.value = value;
    	}
    	
    	public String getKind() {
			return kind;
		}
    	
		public String getValue() {
			return value;
		}
		
		private String kind;
    	private String value;
    }

    private String agent;
    private String name;
    private long timestamp;
    private Event ancestor;
    private int id;
    private List<Annotation> annotations = new ArrayList<Annotation>();
    private static int nextID = 1;
    
	public static final String ISLEAF_ANNOTATION_NAME = "isLeaf";
}
