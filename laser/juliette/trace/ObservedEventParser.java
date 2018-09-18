package laser.juliette.trace;

import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;

class ObservedEventParser extends EmptyTagParser {

	ObservedEventParser(Stack<Parser> stack, Trace trace,
			Attributes attrs, Map<String, Event> ancestors) {
		super(stack);
		Event ancestor = null;
		String ancestorId = attrs.getValue(XMLSupport.ANCESTOR);
		if (ancestorId != null) {
			ancestor = ancestors.get(ancestorId);
		}
		ObservedEvent event = new ObservedEvent(attrs.getValue(XMLSupport.AGENT),
				attrs.getValue(XMLSupport.NAME),
				Long.parseLong(attrs.getValue(XMLSupport.TIMESTAMP)),
				ancestor);
		ancestors.put(attrs.getValue(XMLSupport.ID), event);
		trace.add(event);
	}

}
