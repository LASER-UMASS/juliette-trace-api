package laser.juliette.trace.filter;

import java.util.StringTokenizer;

import laser.juliette.trace.ParameterFilter;


/**
 * The ParameterFilterParser class takes as input a parameter filter specification
 * and produces as output a parameter filter matching that specification.
 */
public class ParameterFilterParser 
{
    public static final String PARAMETER_FILTER_SPEC_SEPARATOR = ",";
    
    /** The supported parameter filter kinds to be parsed */
    public static final String PARAMETER_FILTER_KIND_ANY = "ANY";
    public static final String PARAMETER_FILTER_KIND_NAME = "NAME";
    public static final String PARAMETER_FILTER_KIND_TYPE = "TYPE";
    
    
    /**
     * Returns the ParameterFilter matching the given parameter filter specification.
     * 
     * @param parameterFilterSpec The parameter filter specification to be parsed
     * @return The ParameterFilter matching the given specification
     * @throws UnsupportedOperationException if the given specification is not supported
     */
	public static ParameterFilter parse(String parameterFilterSpec) 
	{
        //Parse in the parameter filter
        StringTokenizer parameterFilterTokenizer = new StringTokenizer(parameterFilterSpec, PARAMETER_FILTER_SPEC_SEPARATOR);
        ParameterFilter parameterFilter = null;
        
        if (parameterFilterTokenizer.countTokens() < 1) {
            throw new IllegalArgumentException("The parameter filter kind must be specified first.");
        }
        String parameterFilterKind = parameterFilterTokenizer.nextToken();
        if (parameterFilterKind.equals(PARAMETER_FILTER_KIND_ANY)) {
            parameterFilter = new ArtifactParameterFilter();
        }
        else if (parameterFilterKind.equals(PARAMETER_FILTER_KIND_NAME)) {
            if (parameterFilterTokenizer.countTokens() != 2) {
                throw new IllegalArgumentException("For parameter filter kind " + parameterFilterKind + ", the name and name pattern must be specified.");
            }
            String name = parameterFilterTokenizer.nextToken();
            String namePatternString = parameterFilterTokenizer.nextToken();
            parameterFilter = new NameParameterFilter(name, NameParameterFilter.NamePattern.valueOf(namePatternString));
        }
        else if (parameterFilterKind.equals(PARAMETER_FILTER_KIND_TYPE)) {
            if (parameterFilterTokenizer.countTokens() != 2) {
                throw new IllegalArgumentException("For parameter filter kind " + parameterFilterKind + ", the type and type pattern must be specified.");
            }
            String type = parameterFilterTokenizer.nextToken();
            String typePatternString = parameterFilterTokenizer.nextToken();
            parameterFilter = new TypeParameterFilter(type, TypeParameterFilter.TypePattern.valueOf(typePatternString));
        }
        else {
            throw new UnsupportedOperationException("Parameter filter kind " + parameterFilterKind + " is not supported.");
        }
        
        return parameterFilter;
	}
}
