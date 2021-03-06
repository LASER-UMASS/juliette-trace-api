package laser.juliette.trace;

import java.util.Stack;

import laser.juliette.trace.StateChangeEventParser.Cell;

import org.xml.sax.Attributes;

class CardinalityControllerParser extends EmptyTagParser {

	CardinalityControllerParser(Stack<Parser> stack, Attributes attrs,
			Cell<Controller> controller) {
		super(stack);
		controller.set(new CardinalityController(Integer.parseInt(attrs.getValue(XMLSupport.INDEX))));
	}
}
