package laser.juliette.trace.filter;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;


public class ResourceParameterFilter implements ParameterFilter 
{	
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = 5526705889273513569L;

	
	public ResourceParameterFilter() {
		super();
	}
	
	@Override
	public boolean accept(Parameter parameter) 
	{
		if (parameter == null) {
			return false;
		}
		
		return parameter.getKind() == Parameter.ParameterKind.RESOURCE;
	}
}
