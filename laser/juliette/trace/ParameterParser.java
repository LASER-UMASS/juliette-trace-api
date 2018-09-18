package laser.juliette.trace;

import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

class ParameterParser extends EmptyTagParser {

	ParameterParser(Stack<Parser> stack, Map<String, String> parameters,
			Attributes attrs) {
		super(stack);
		parameters.put(attrs.getValue(XMLSupport.NAME), attrs.getValue(XMLSupport.VALUE));
	}

}
