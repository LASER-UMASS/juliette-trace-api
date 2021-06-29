package laser.juliette.trace;

import java.util.Map;
import java.util.Stack;

import laser.juliette.trace.Parameter.ParameterKind;

import org.xml.sax.Attributes;

class ParameterParser extends EmptyTagParser {

	ParameterParser(Stack<Parser> stack, Map<String, Parameter> parameters,
			Attributes attrs) {
		super(stack);
		String newParameterName = attrs.getValue(XMLSupport.NAME);
		Parameter newParameter = new Parameter(newParameterName, ParameterKind.valueOf(attrs.getValue(XMLSupport.KIND)), attrs.getValue(XMLSupport.TYPE), attrs.getValue(XMLSupport.VALUE));
		parameters.put(newParameterName,  newParameter);
	}

}
