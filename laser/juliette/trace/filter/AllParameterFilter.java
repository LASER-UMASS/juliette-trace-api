package laser.juliette.trace.filter;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;


public class AllParameterFilter implements ParameterFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = 1839181156485765508L;

	
	public AllParameterFilter() {
		super();
	}
	
	@Override
	public boolean accept(Parameter parameter) {
		if (parameter == null) {
			return false;
		}
		
		return true;
	}
}
