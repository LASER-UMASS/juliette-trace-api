package laser.juliette.trace;

/**
 * An <code>ObservedEvent</code> is a representation of an externally
 * observed event in a trace. <code>ObservedEvent</code> allows traces to be
 * used to describe traces collected during process elicitation in addition
 * to the traces created from a process definition.
 */
public class ObservedEvent extends Event {

	/**
	 * Create a new observed event.
	 * @param agent the agent that performed the action or UNKNOWN_AGENT
	 * @param name the name of the event
	 * @param timestamp the timestamp for the event
	 * @param ancestor the ancestor to this event, or <code>null</code>
	 */
	public ObservedEvent(String agent, String name, long timestamp,
			Event ancestor) {
		super(agent, name, timestamp, ancestor);
	}

}
