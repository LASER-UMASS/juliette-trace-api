package laser.juliette.trace;

import java.io.Serializable;


/**
 * Interface for filters used to select subsets of the parameters.
 */
public interface ParameterFilter extends Serializable 
{
    /**
     * Determine if this parameter should be included in the filtered set of parameters.
     * 
     * @param parameter The parameter to examine
     * @return <code>True</code> if the parameter should be included
     */
	public boolean accept(Parameter parameter);
}
