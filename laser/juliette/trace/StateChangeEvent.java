/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Representation of a state change of an instance of a Little-JIL step
 * in a process trace.
 */
public class StateChangeEvent extends Event {
    
    /**
     * Enumeration of the possible states of a Little-JIL step instance.
     */
    public static enum State {
        posted,
        starting,
        started,
        completing,
        completed,
        terminating,
        terminated,
        retracted,
        optedout
        };
        
    /**
     * Minimal constructor for a state change event. All optional fields
     * are set to null, and the agent is specified as UNKNOWN_AGENT.
     * @param step the name of the step
     * @param state the new state for the step
     * @param timestamp the timestamp for the event
     */
    public StateChangeEvent(String step, State state, long timestamp, Event ancestor) {
        this(UNKNOWN_AGENT, step, state, timestamp, null, null, null, null, null);
    }
    
    /**
     * Create a new state change event.
     * @param agent the agent associated with the step or UNKNOWN_AGENT
     * @param step the step name
     * @param state the new state for the step
     * @param timestamp the timestamp for the event
     * @param ancestor the ancestor of this event
     * @param parameters a parameter map for the state or null
     * @param results a list of result tokens or null
     * @param context the instance context or null
     * @param controller the instance controller or null
     */
    public StateChangeEvent(String agent, String step, State state, long timestamp, Event ancestor, Map<String, String> parameters, List<String> results, Context context, Controller controller) {
        super(agent, step, timestamp, ancestor);
        this.state = state;
        this.parameters = parameters;
        this.results = results;
        this.context = context;
        this.controller = controller;
    }
    
    /**
     * Get the new state for the step instance.
     * @return the new state
     */
    public State getState() {
        return state;
    }
    
    /**
     * Get an unmodifiable map of parameter names to tokens. Because a trace is not
     * necessarily the result of an actual execution, parameter values are
     * represented by system defined tokens instead of actual values.
     * @return the parameter map or null
     */
    public Map<String, String> getParameters() {
    	if (parameters==null) return null;
        return Collections.unmodifiableMap(parameters);
    }
    
    /**
     * Get an unmodifiable list of the results tokens for a step.
     * Results may be used to represent exceptions that are thrown from
     * terminated steps.
     * @return the results list or null
     */
    public List<String> getResults() {
    	if (results==null) return null;
        return Collections.unmodifiableList(results);
    }
    
    /**
     * If a step is used more than once in a process, a context
     * can be used to distinguish between different instances.
     * @return the context or null
     */
    public Context getContext() {
        return context;
        
    }
    
    /**
     * If the instantiation of a step is controlled by external data
     * (e.g., an exception) a controller object can be used to record
     * this information.
     * @return the controller or null
     */
    public Controller getController() {
        return controller;
    }

    private State state;
    private Map<String, String> parameters;
    private List<String> results;
    private Context context;
    private Controller controller;
}
