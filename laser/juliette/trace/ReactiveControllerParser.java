package laser.juliette.trace;

import java.util.Stack;

import laser.juliette.trace.StateChangeEventParser.Cell;

import org.xml.sax.Attributes;

class ReactiveControllerParser extends EmptyTagParser {

	ReactiveControllerParser(Stack<Parser> stack, Attributes attrs,
			Cell<Controller> controller) {
		super(stack);
		controller.set(new ReactiveController(attrs.getValue(XMLSupport.TRIGGER)));
	}

}
