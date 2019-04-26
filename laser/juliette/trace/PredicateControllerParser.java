package laser.juliette.trace;

import java.util.Stack;

import laser.juliette.trace.StateChangeEventParser.Cell;

import org.xml.sax.Attributes;

class PredicateControllerParser extends EmptyTagParser {

	PredicateControllerParser(Stack<Parser> stack, Attributes attrs,
			Cell<Controller> controller) {
		super(stack);
		controller.set(new PredicateController(attrs.getValue(XMLSupport.PREDICATE), Integer.parseInt(attrs.getValue(XMLSupport.INDEX))));
	}

}
