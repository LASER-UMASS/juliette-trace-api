package laser.juliette.trace.filter;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;


/**
 * The TypeParameterFilter class accepts all Parameters with either 
 * the exact type or one of its subtypes based on the TypePattern.
 */
public class TypeParameterFilter implements ParameterFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = -3464896286093936336L;
	
	public enum TypePattern {
		SUBTYPE,
		EQUALS
	};
	
	/** The partial or full type name to be matched against */
	private String type_;
	
	/** The type pattern (see above) */
	private TypePattern typePattern_;
	
	
	/**
	 * Creates a new TypeParameterFilter where the partial or full type is 
	 * matched against the given type pattern.
	 * 
	 * PRECONDITIONS: 
	 * - The type must be non-null and non-empty.
	 * - The type pattern must be non-null.
	 * 
	 * @param type The partial or full type to be matched against
	 * @param typePattern The type pattern (see above)
	 */
	public TypeParameterFilter(String type, TypePattern typePattern) {
		super();
		
		// Check preconditions
		if ((type == null) || type.isEmpty()) {
			throw new IllegalArgumentException("The type must be non-null and non-empty.");
		}
		if (typePattern == null) {
			throw new IllegalArgumentException("The type pattern must be non-null.");
		}
		
		this.type_ = type;
		this.typePattern_ = typePattern;
	}
	
	@Override
	public boolean accept(Parameter parameter) 
	{
		if (parameter == null) {
			return false;
		}
		
		// Check if the Parameter's type is a subtype of the Filter's type.
		if (this.typePattern_ == TypePattern.SUBTYPE) {
			try {
				Class typePatternClass = Class.forName(this.type_);
				Class parameterClass = Class.forName(parameter.getType());
				return typePatternClass.isAssignableFrom(parameterClass);
			}
			catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(cnfe);
			}
		}
		// Check if the Parameter's type is equal to the Filter's type.
		else if (this.typePattern_ == TypePattern.EQUALS) {
			return this.type_.equals(parameter.getType());
		}
		else {
			throw new UnsupportedOperationException("Type pattern " + this.typePattern_ + " is not supported");
		}
	}
}
