package laser.juliette.trace.filter;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;


public class TypeParameterFilter implements ParameterFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = -1770082512516003211L;
	
	private String type_;
	
	
	public TypeParameterFilter(String type) {
		super();
		
		this.type_ = type;
	}
	
	@Override
	public boolean accept(Parameter parameter) 
	{
		if (parameter == null) {
			return false;
		}
		
		return this.type_.equals(parameter.getType());
	}
}
