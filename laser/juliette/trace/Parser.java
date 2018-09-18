package laser.juliette.trace;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

abstract class Parser {
	protected Parser(Stack<Parser> stack) {
		this.stack = stack;
	}
	
	abstract void startElement(String tag, Attributes attrs) throws SAXException;
	
	void endElement() {
		stack.pop();
	}
	
	protected Stack<Parser> stack;
}
