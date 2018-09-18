package laser.juliette.trace;

import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

class AnnotationParser extends EmptyTagParser {

	AnnotationParser(Stack<Parser> stack, Map<String, String> annotations, Attributes attrs) {
		super(stack);
		annotations.put(attrs.getValue(XMLSupport.KIND), attrs.getValue(XMLSupport.VALUE));
	}

}
