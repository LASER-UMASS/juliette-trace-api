/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

import java.io.Serializable;

/**
 * A <code>Context</code> provides a mechanism to disambiguate step instances.
 * For example, a reference can record the parent step name to disambiguate
 * the reference from the original declaration.
 * 
 * Optionally, context objects may be nested to further disambiguate
 * step instances.
 */
public class Context implements Serializable {
    /**
     * The kinds of connections that a possible between two step instances.
     * Defined by the relationship between the declarations in the Little-JIL.
     */
    public static enum Connection {
    	unknown,
        prerequisite,
        postrequisite,
        substep,
        reaction,
        handler
        };

    /**
     * Minimal constructor for a context object.
     * @param name the name of the parent step
     */
    public Context (String name) {
      this(name, Connection.unknown, 1, null);
    }
    
    /**
     * Create a new context object.
     * @param name the name of the parent step
     * @param connection the kind of the connection between this step and the parent step
     * @param index the index of this step if this step appears more than once in the same connection kind. If the step appears only once, this should be 1
     * @param parent the parent context or <code>null</code>
     */
    public Context(String name, Connection connection, int index, Context parent) {
        this.name = name;
        this.parent = parent;
        this.connection = connection;
        this.index = index;
    }

    /**
     * Retrieve the name of the enclosing step.
     * @return the step name
     */
    public String getEnclosingStepName() {
        return name;
    }
    
    /**
     * Retrieve the kind of connection between this step and its parent.
     * @return the connection kind or <code>unknown</code>
     */
    public Connection getConnection() {
        return connection;
    }
    
    /**
     * Retrieve the index of this step if the step appears more than once
     * in the same connection kind.
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    
    /**
     * Retrieve the parent context for this context, if any.
     * @return the parent context or <code>null</node>
     */
    public Context getParentContext() {
        return parent;
    }
    
    private String name;
    private Context parent;
    private Connection connection;
    private int index;
}
