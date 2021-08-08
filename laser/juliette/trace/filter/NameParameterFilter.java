package laser.juliette.trace.filter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.Map;

import org.xml.sax.SAXException;

import laser.juliette.trace.Parameter;
import laser.juliette.trace.ParameterFilter;
import laser.juliette.trace.StateChangeEvent;
import laser.juliette.trace.Trace;
import laser.juliette.trace.Traces;
import laser.juliette.trace.XMLIO;


/**
 * The NameParameterFilter class accepts all Parameters where their names
 * match the given name pattern.
 */
public class NameParameterFilter implements ParameterFilter 
{
	/**
	 * Automatically generated serial version UID
	 */
	private static final long serialVersionUID = 3172166137740549650L;
	
	public enum NamePattern { 
		STARTS_WITH,
		CONTAINS,
		ENDS_WITH,
		EQUALS
	};
	
	/** The partial or full name to be matched against */
	private String name_;
	
	/** The name pattern (see above) */
	private NamePattern namePattern_;
	
	
	/**
	 * Creates a new NameParameterFilter where the partial or full name is 
	 * matched against the given name pattern.
	 * 
	 * @param name The partial or full name to be matched against
	 * @param namePattern The name pattern (see above)
	 */
	public NameParameterFilter(String name, NamePattern namePattern) {
		super();
		
		// Check preconditions
		if ((name == null) || name.isEmpty()) {
			throw new IllegalArgumentException("The name must be non-null and non-empty.");
		}
		if (namePattern == null) {
			throw new IllegalArgumentException("The name pattern must be non-null.");
		}
		
		this.name_ = name;
		this.namePattern_ = namePattern;
	}
	
	@Override
	public boolean accept(Parameter parameter) 
	{
		if (parameter == null) {
			return false;
		}
		
		if (this.namePattern_ == NamePattern.STARTS_WITH) {
			return parameter.getName().startsWith(this.name_);
		}
		else if (this.namePattern_ == NamePattern.CONTAINS) {
			return parameter.getName().contains(this.name_);
		}
		else if (this.namePattern_ == NamePattern.ENDS_WITH) {
			return parameter.getName().endsWith(this.name_);
		}
		else if (this.namePattern_ == NamePattern.EQUALS) {
			return this.name_.equals(parameter.getName());
		}
		else {
			throw new UnsupportedOperationException("The name pattern " + this.namePattern_ + " is not supported.");
		}
	}
	
	public static void main (String[] args) throws IOException, SAXException 
	{
		if (args.length != 3) {
			System.err.println("The trace, name, and name pattern must be specified.");
			System.exit(1);
		}
		
		File traceFile = new File(args[0]);
		Reader in = new BufferedReader(new FileReader(traceFile));
		Traces tracesList = XMLIO.read(in);
		if (tracesList.size() != 1) {
			throw new IllegalArgumentException("The trace.xml file must contains a single trace.");
		}
		System.out.println("Reading from directory " + traceFile.getPath());
		Trace trace = tracesList.get(0);
		
		String name = args[1];
		NameParameterFilter.NamePattern namePattern = NameParameterFilter.NamePattern.valueOf(args[2]);
		NameParameterFilter nameParameterFilter = new NameParameterFilter(name, namePattern);
		for (int eventID = 1; eventID <= trace.length(); eventID++) {
			StateChangeEvent event = (StateChangeEvent)trace.get(eventID);
			System.out.println("Matching parameter(s):");
			Map<String,Parameter> parametersMap = event.getParameters(nameParameterFilter);
			if (parametersMap != null) {
				for (Parameter parameter : parametersMap.values()) {
					System.out.println("\t" + parameter);
				} // end for parameter
			}
		} // end for eventID
	}
	
} // end of NameParameterFilter
