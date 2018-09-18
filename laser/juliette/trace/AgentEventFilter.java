/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

/**
 * An <code>EventFilter</code> that selects events based on the agent.
 */
public class AgentEventFilter implements EventFilter {
    public AgentEventFilter(String agent) {
        this.agent = agent;
    }

    public boolean accept(Event event) {
        if (event instanceof StateChangeEvent &&
                ((StateChangeEvent) event).getAgentName().equals(agent)) {
            return true;
        } else {
            return false;
        }
    }
    
    private String agent;
}
