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
	private Set<String> agentNames_;
	private StateChangeEvent event_;
	private Map<String,Parameter> resourcesMap_;
	private Set<Parameter> resourcesSet_;
	private Parameter agent_;
	private Set<Parameter> agentsSet_;
	
	
	public ResourceUtils() {
		super();
	}
	
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
	
	public Set<Parameter> getResourcesSet() {
		Set<Parameter> resourcesSetCopy = new LinkedHashSet<Parameter>();
		resourcesSetCopy.addAll(this.resourcesSet_);
		
		return resourcesSetCopy;
	}
	
	public Set<Parameter> getAgentsSet() {
		Set<Parameter> agentsSetCopy = new LinkedHashSet<Parameter>();
		agentsSetCopy.addAll(this.agentsSet_);
		
		return agentsSetCopy;
	}
	
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
	
	public Set<Parameter> getSecondaryAgentsSet() {
		Set<Parameter> secondaryAgentsSetCopy = new LinkedHashSet<Parameter>();
		secondaryAgentsSetCopy.addAll(this.getAgentsSet());
		secondaryAgentsSetCopy.removeAll(this.getPrimaryAgentsSet());
		
		return secondaryAgentsSetCopy;
	}
	
	public Set<Parameter> getNonAgentsSet() {
		Set<Parameter> nonAgentsSetCopy = new LinkedHashSet<Parameter>();
		nonAgentsSetCopy.addAll(this.getResourcesSet());
		nonAgentsSetCopy.removeAll(this.getAgentsSet());
		
		return nonAgentsSetCopy;
	}
	
	public void tearDown() {
		this.agentNames_ = null;
		this.event_ = null;
		this.resourcesMap_ = null;
		this.resourcesSet_ = null;
		this.agent_ = null;
		this.agentsSet_ = null;
	}
} // end ResourceUtils
