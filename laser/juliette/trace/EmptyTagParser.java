package laser.juliette.trace;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

abstract class EmptyTagParser extends Parser {

	protected EmptyTagParser(Stack<Parser> stack) {
		super(stack);
	}

	@Override
	void startElement(String tag, Attributes attrs) throws SAXException {
		throw new SAXException("Unexpected nested tag " + tag);
	}

}
