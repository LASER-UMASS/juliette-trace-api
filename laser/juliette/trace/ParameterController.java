/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

/**
 * A <code>ParameterController</code> is used to record the resource
 * associated with a instance created as a result of a
 * resource or artifact bounded cardinality.
 */
public class ParameterController extends CardinalityController {
    /**
     * Create a new <code>ParameterController.</code>
     * @param parameter the name of the parameter
     * @param index the index for the instance
     */
    public ParameterController(String parameter, int index) {
    	super(index);
        this.parameter = parameter;
    }
    
    /**
     * Get the name of the parameter.
     * @return the parameter name
     */
    public String getParameter() {
        return parameter;
    }
    
    private String parameter;
}
