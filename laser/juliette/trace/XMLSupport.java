package laser.juliette.trace;

import java.io.IOException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

class XMLSupport {
    static final String PUBLIC_ID = "-//LASER//DTD Juliette Traces 1.0//EN";
    static final String SYSTEM_ID = "http://laser.cs.umass.edu/dtd/juliette-traces-1.0.dtd";
    static final String TRACES = "traces";
    static final String TRACE = "trace";
    static final String PROPERTY = "property";
    static final String NAME = "name";
    static final String VALUE = "value";
    static final String OBSERVED_EVENT = "observed-event";
    static final String STATE_CHANGE_EVENT = "state-change-event";
    static final String AGENT = "agent";
    static final String SEQUENCING_KIND = "sequencing-kind";
    static final String STATE = "state";
    static final String TIMESTAMP = "timestamp";
    static final String ANCESTOR = "ancestor";
    static final String ID = "id";
    static final String POSTED = "posted";
    static final String STARTING = "starting";
    static final String STARTED = "started";
    static final String COMPLETING = "completing";
    static final String COMPLETED = "completed";
    static final String TERMINATING = "terminating";
    static final String TERMINATED = "terminated";
    static final String RETRACTED = "retracted";
    static final String OPTED_OUT = "optedout";
    static final String ANNOTATION = "annotation";
    static final String KIND = "kind";
    static final String PARAMETER = "parameter";
    static final String RESULT = "result";
    static final String CONTEXT = "context";
    static final String CONNECTION = "connection";
    static final String INDEX = "index";
    static final String UNKNOWN = "unknown";
    static final String PREREQUISITE = "prerequisite";
    static final String POSTREQUISITE = "postrequisite";
    static final String SUBSTEP = "substep";
    static final String REACTION = "reaction";
    static final String HANDLER = "handler";
    static final String REACTIVE_CONTROLLER = "reactive-controller";
    static final String TRIGGER = "trigger";
    static final String CARDINALITY_CONTROLLER = "cardinality-controller";
    static final String PARAMETER_CONTROLLER = "parameter-controller";
    static final String PREDICATE_CONTROLLER = "predicate-controller";
    static final String PREDICATE = "predicate";
    
    InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
        if (publicId.equals(XMLSupport.PUBLIC_ID)) {
            return new InputSource(getClass().getResourceAsStream("jul.dtd"));
        }
        return null;
    }
}
