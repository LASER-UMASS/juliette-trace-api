package laser.juliette.trace;

import java.util.Stack;

import laser.juliette.trace.StateChangeEventParser.Cell;

import org.xml.sax.Attributes;

class ParameterControllerParser extends EmptyTagParser {

	ParameterControllerParser(Stack<Parser> stack, Attributes attrs,
			Cell<Controller> controller) {
		super(stack);
		controller.set(new ParameterController(attrs.getValue(XMLSupport.NAME), Integer.parseInt(attrs.getValue(XMLSupport.INDEX))));
	}

}
