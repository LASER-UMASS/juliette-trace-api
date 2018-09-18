/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

/**
 * Interface for filters used to select subsets of a trace.
 */
public interface EventFilter {
    /**
     * Determine if this event should be included in the filtered trace.
     * @param event The event to examine
     * @return <code>True</code> if the event should be included
     */
    public boolean accept(Event event);
}
