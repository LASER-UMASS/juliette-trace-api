package laser.juliette.trace.filter;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;


/**
 * The ArtifactParameterFilter class accepts all Parameters corresponding 
 * to artifacts.
 */
public class ArtifactParameterFilter implements ParameterFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = -3124861822781743136L;

	
	public ArtifactParameterFilter() {
		super();
	}
	
	@Override
	public boolean accept(Parameter parameter) 
	{
		if (parameter == null) {
			return false;
		}
		
		return parameter.getKind() == Parameter.ParameterKind.ARTIFACT;
	}
}
