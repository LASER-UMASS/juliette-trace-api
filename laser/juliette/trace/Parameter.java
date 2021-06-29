package laser.juliette.trace;

import java.io.Serializable;


public class Parameter implements Serializable
{
	/**
	 * Automatically generated serial version UID 
	 */
	private static final long serialVersionUID = 7940589784629977656L;
	
	public enum ParameterKind { ARTIFACT, RESOURCE };
	
	private String name;
	private ParameterKind kind;
	private String type;
	private String value;
	
	
	/**
	 * Creates a new Parameter with the given name, type, and value.
	 * 
	 * PRE-CONDITIONS:
	 * 1) The name is non-null and non-empty.
	 * 2) The kind is non-null.
	 * 3) The type is non-null and non-empty.
	 * 4) The value is non-null.
	 * 
	 * @param name The name
	 * @param kind The kind
	 * @param type The type
	 * @param value The value
	 * 
	 * @throws IllegalArgumentException One or more of the pre-conditions are violated
	 */
	public Parameter(String name, ParameterKind kind, String type, String value) {
		super();
		
		// Check the pre-conditions
		String errorMessage = "";
		if ((name == null) || name.trim().equals("")) {
			errorMessage = "The name must be non-null and non-empty.";
		}
		if (kind == null) {
			if (errorMessage.length() > 0) {
				errorMessage += " ";
			}			
			errorMessage += "The kind must be non-null.";
		}
		if ((type == null) || type.trim().equals("")) {
			if (errorMessage.length() > 0) {
				errorMessage += " ";
			}
			errorMessage += "The type must be non-null and non-empty.";
		}
		if (value == null) {
			if (errorMessage.length() > 0) {
				errorMessage += " ";
			}
			errorMessage += "The value must be non-null.";
		}
		if (errorMessage.length() > 0) {
			throw new IllegalArgumentException(errorMessage);
		}
		
		this.name = name;
		this.kind = kind;
		this.type = type;
		this.value = value;
	}
	
	public String getName() {
		return this.name;
	}
	
	public ParameterKind getKind() {
		return this.kind;
	}
	
	public String getType() {
		return this.type;
	}
	
	public String getValue() {
		return this.value;
	}
	
	public boolean equals(Object obj2) {
		// It must be non-null and be a Parameter
		if (! (obj2 instanceof Parameter)) {
			return false;
		}
		
		// The name, kind, type, and value all must be exactly the same.
		Parameter param2 = (Parameter)obj2;
		if (! this.getName().equals(param2.getName())) {
			return false;
		}
		
		if (this.getKind() != param2.getKind()) {
			return false;
		}
		
		if (! this.getType().equals(param2.getType())) {
			return false;
		}
		
		if (! this.getValue().equals(param2.getValue())) {
			return false;
		}
		
		return true;
	}
	
	public int hashCode() {
		return this.toString().hashCode();
	}
	
	public String toString() {
		return "PARAMETER{name=" + this.getName() + ", kind=" + this.getKind().name() + ", type=" + this.getType() + ", value=" + this.getValue() + "}";
	}
}
