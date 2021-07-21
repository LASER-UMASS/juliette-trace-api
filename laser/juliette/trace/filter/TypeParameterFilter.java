package laser.juliette.trace.filter;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;


/**
 * The TypeParameterFilter class accepts all Parameters with the 
 * specified type.
 */
public class TypeParameterFilter implements ParameterFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = -1770082512516003211L;
	
	private String type_;
	
	
	public TypeParameterFilter(String type) {
		super();
		
		// Check preconditions
		if ((type == null) || type.isEmpty()) {
			throw new IllegalArgumentException("The type must be non-null and non-empty.");
		}
		
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
