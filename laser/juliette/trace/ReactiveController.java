/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

/**
 * A <code>ReactiveController</code> is used to record the exception or message that
 * is responsible for firing a handler or reaction.
 */
public class ReactiveController extends Controller {
    /**
     * Create a new <code>ReactiveController.</code>
     * @param trigger a token representing the exception or message
     */
    public ReactiveController(String trigger) {
        this.trigger = trigger;
    }
    
    /**
     * Get the token representing the trigger.
     * @return the trigger
     */
    public String getTrigger() {
        return trigger;
    }
    
    private String trigger;
}
