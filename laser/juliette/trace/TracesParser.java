package laser.juliette.trace;

import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class TracesParser extends Parser {

	TracesParser(Stack<Parser> parsestack, Traces traces) {
		super(parsestack);
		this.traces = traces;
	}

	@Override
	void startElement(String tag, Attributes attrs) throws SAXException {
		if (tag.equals(XMLSupport.TRACE)) {
			stack.push(new TraceParser(stack, traces));
		} else {
			throw new SAXParseException("Unexpected tag " + tag, null);
		}
	}
	
	private Traces traces;

}
