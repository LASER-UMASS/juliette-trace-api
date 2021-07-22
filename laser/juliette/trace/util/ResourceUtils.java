package laser.juliette.trace.util;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.filter.ResourceParameterFilter;


/**
 * The ResourceUtils class supports dynamic analysis of the resources 
 * used by a given agenda item.
 */
public class ResourceUtils 
{
        /** The set of all potential agent names in the trace containing the agenda item */ 
	private Set<String> agentNames_;

        /** The event corresponding to the agenda item of interest */
	private StateChangeEvent event_;

        /** Maps from each resource name to resource value used by the given agenda item */
	private Map<String,Parameter> resourcesMap_;

        /** The set of all resources used by the given agenda item */
	private Set<Parameter> resourcesSet_;
	
        /** The agent responsible for performing the given agenda item */ 
        private Parameter agent_;
	
        /** 
	 * The set of all agents responsible for performing one or more agenda items rooted 
	 * at the given agenda item 
	 */
        private Set<Parameter> agentsSet_;
	
	
        /**
	 * Creates a new ResourceUtils.
	 */
	public ResourceUtils() {
		super();
	}
	
        /**
	 * Sets up and performs the dyanamic analysis of the given agenda item.
	 *
	 * @param agentNames The set of all potential agent names
	 * @param event The event corresponding to the agenda item of interest
	 */
	public void setup(Set<String> agentNames, StateChangeEvent event) {
		this.agentNames_ = agentNames;
		this.event_ = event;
		this.resourcesMap_ = this.event_.getParameters(new ResourceParameterFilter());
		this.resourcesSet_ = new LinkedHashSet<Parameter>();
		this.agentsSet_ = new LinkedHashSet<Parameter>();
		if (this.resourcesMap_ != null) {
			for (String currentResourceName : this.resourcesMap_.keySet()) {
				Parameter currentResourceValue = this.resourcesMap_.get(currentResourceName);
				this.resourcesSet_.add(currentResourceValue);
				
				String currentPotentialAgentName = currentResourceValue.getType() + " " + currentResourceValue.getValue();
				if (currentResourceValue.getName().equals("agent")) {
					this.agent_ = currentResourceValue;
				}
				if (this.agentNames_.contains(currentPotentialAgentName)) {
					this.agentsSet_.add(currentResourceValue);
				}
			} // end for currentResourceName
		}
	}

        /**
	 * Returns all of the resources used by the given agenda item.
	 *
	 * @return All of the resources used by the given agenda item
	 */
	public Set<Parameter> getResourcesSet() {
		Set<Parameter> resourcesSetCopy = new LinkedHashSet<Parameter>();
		resourcesSetCopy.addAll(this.resourcesSet_);
		
		return resourcesSetCopy;
	}
	
        /**
         * Returns all of the agents involved in the given agenda item.
	 *
	 * @return All of the agenda involved in the given agenda item.
	 */
	public Set<Parameter> getAgentsSet() {
		Set<Parameter> agentsSetCopy = new LinkedHashSet<Parameter>();
		agentsSetCopy.addAll(this.agentsSet_);
		
		return agentsSetCopy;
	}
	
        /**
	 * Returns the primary agents set, meaning all agents that perform one or more 
	 * of the agenda items rooted at this agenda item.
	 *
	 * @return The primary agents set
	 */
	public Set<Parameter> getPrimaryAgentsSet() {
		Set<Parameter> primaryAgentsSetCopy = new LinkedHashSet<Parameter>();

		// The primary agent is the resource named "agent"
		if (this.event_.getSequencingKind() == StateChangeEvent.SequencingKind.leaf) {
			if (this.agent_ != null) {
				primaryAgentsSetCopy.add(this.agent_);
			}
		}
		// The primary agents are any agents responsible for performing
		// the sub-steps.
		else {
			primaryAgentsSetCopy.addAll(this.agentsSet_);
		}
		
		return primaryAgentsSetCopy;
	}
	
        /**
         * Returns the secondary agents set, meaning all agents that are potentially involved
	 * in one or more of the agenda items rooted at this agenda item.
	 *
	 * @return The secondary agents set
	 */
	public Set<Parameter> getSecondaryAgentsSet() {
		Set<Parameter> secondaryAgentsSetCopy = new LinkedHashSet<Parameter>();
		secondaryAgentsSetCopy.addAll(this.getAgentsSet());
		secondaryAgentsSetCopy.removeAll(this.getPrimaryAgentsSet());
		
		return secondaryAgentsSetCopy;
	}
	
        /**
	 * Returns the non-agents set, meaning all resources that are never agents for one 
	 * or more of the agenda items rooted at this agenda item.
	 *
	 * @return The non-agents set
	 */
	public Set<Parameter> getNonAgentsSet() {
		Set<Parameter> nonAgentsSetCopy = new LinkedHashSet<Parameter>();
		nonAgentsSetCopy.addAll(this.getResourcesSet());
		nonAgentsSetCopy.removeAll(this.getAgentsSet());
		
		return nonAgentsSetCopy;
	}
	
        /**
	 * Tears down after performing the dynamic analysis on the given agenda item.
	 */
	public void tearDown() {
		this.agentNames_ = null;
		this.event_ = null;
		this.resourcesMap_ = null;
		this.resourcesSet_ = null;
		this.agent_ = null;
		this.agentsSet_ = null;
	}
} // end ResourceUtils
