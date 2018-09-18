/*
 * Copyright (c) 2006, University of Massachusetts Amherst
 * All Rights Reserved.
 */
package laser.juliette.trace;

import java.io.Serializable;

/**
 * Abstract base class for controllers. A controller is an object
 * that is responsible for the creation of an event. For example, an
 * exception is a controller for an exception handler. 
 */
public abstract class Controller implements Serializable {
}
