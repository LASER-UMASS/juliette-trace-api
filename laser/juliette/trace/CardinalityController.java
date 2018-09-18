/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

/**
 * A <code>CardinalityController</code> is used to record the index of a
 * step instance created as a result of a cardinality. For sequential
 * cardinalities, this is straight-forward. For parallel cardinality,
 * instances should be assigned an index based on the order in which
 * they are posted.
 */
public class CardinalityController extends Controller {
    /**
     * Create a new <code>CardinalityController.</code>
     * @param index the index for the instance.
     */
    public CardinalityController(int index) {
        this.index = index;
    }
    
    /**
     * Get the associated index.
     * @return the index
     */
    public int getIndex() {
        return index;
    }
    
    private int index;
}
