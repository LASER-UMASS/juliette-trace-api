package laser.juliette.trace;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

class TraceParser extends Parser {

	public TraceParser(Stack<Parser> stack, Traces traces) {
		super(stack);
		this.trace = new Trace();
		traces.add(trace);
	}

	@Override
	void startElement(String tag, Attributes attrs) throws SAXException {
		if (tag.equals(XMLSupport.PROPERTY)) {
			stack.push(new PropertyParser(stack, trace, attrs));
		} else if (tag.equals(XMLSupport.STATE_CHANGE_EVENT)) {
			stack.push(new StateChangeEventParser(stack, trace, attrs, ancestors));
		} else if (tag.equals(XMLSupport.OBSERVED_EVENT)) {
			stack.push(new ObservedEventParser(stack, trace, attrs, ancestors));
		} else {
			throw new SAXParseException("Unexpected tag " + tag, null);
		}
	}
	
	private Trace trace;
	private Map<String, Event> ancestors = new HashMap<String, Event>();
}
