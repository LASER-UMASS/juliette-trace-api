/*
 * Copyright (c) 2019, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;


/**
 * A <code>PredicateController</code> is used to record the predicate
 * expression (that evaluated to true) guarding this step.
 */
public class PredicateController extends CardinalityController 
{
    /**
     * Create a new <code>PredicateController.</code>
     * @param predicate a token representing the predicate guarding this step
     * @param index the index for the instance
     */
    public PredicateController(String predicate, int index) {
    	super(index);
        this.predicate = predicate;
    }
    
    /**
     * Get the token representing the predicate.
     * @return the predicate
     */
    public String getPredicate() {
        return predicate;
    }
    
    private String predicate;
}
