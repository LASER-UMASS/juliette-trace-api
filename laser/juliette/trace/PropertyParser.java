package laser.juliette.trace;

import java.util.Stack;

import org.xml.sax.Attributes;

class PropertyParser extends EmptyTagParser {

	public PropertyParser(Stack<Parser> stack, Trace trace, Attributes attrs) {
		super(stack);
		trace.setProperty(attrs.getValue(XMLSupport.NAME), attrs.getValue(XMLSupport.VALUE));
	}

}
