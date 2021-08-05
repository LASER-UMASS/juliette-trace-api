package laser.juliette.trace;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;
import org.xml.sax.helpers.AttributesImpl;

class StateChangeEventParser extends Parser {

	StateChangeEventParser(Stack<Parser> stack, Trace trace,
			Attributes attrs, Map<String, Event> ancestors) {
		super(stack);
		this.trace = trace;
		// Copy the attributes so that the endElement method works correctly
		this.attrs = new AttributesImpl(attrs);
		this.ancestors = ancestors;
	}

	@Override
	void startElement(String tag, Attributes attrs) throws SAXException {
		if (tag.equals(XMLSupport.ANNOTATION)) {
			if (annotations == null) {
				annotations = new LinkedHashMap<String, String>();
			}
			stack.push(new AnnotationParser(stack, annotations, attrs));
		} else if (tag.equals(XMLSupport.PARAMETER)) {
			if (parameters == null) {
				parameters = new LinkedHashMap<String, Parameter>();
			}
			stack.push(new ParameterParser(stack, parameters, attrs));
		} else if (tag.equals(XMLSupport.RESULT)) {
			if (results == null) {
				results = new ArrayList<String>();
			}
			stack.push(new ResultParser(stack, results, attrs));
		} else if (tag.equals(XMLSupport.CONTEXT)) {
			stack.push(new ContextParser(stack, attrs, context));
		} else if (tag.equals(XMLSupport.REACTIVE_CONTROLLER)) {
			stack.push(new ReactiveControllerParser(stack, attrs, controller));
		} else if (tag.equals(XMLSupport.CARDINALITY_CONTROLLER)) {
			stack.push(new CardinalityControllerParser(stack, attrs, controller));
		} else if (tag.equals(XMLSupport.PARAMETER_CONTROLLER)) {
			stack.push(new ParameterControllerParser(stack, attrs, controller));
		} else if (tag.equals(XMLSupport.PREDICATE_CONTROLLER)) {
			stack.push(new PredicateControllerParser(stack, attrs, controller));
		} else {
			throw new SAXParseException("Unexpected tag " + tag, null);
		}
	}
	
	@Override
	void endElement() {
		Event ancestor = null;
		String ancestorId = attrs.getValue(XMLSupport.ANCESTOR);
		if (ancestorId != null) {
			ancestor = ancestors.get(ancestorId);
		}
		
		// Create the event
		StateChangeEvent event = new StateChangeEvent(attrs.getValue(XMLSupport.AGENT),
				attrs.getValue(XMLSupport.NAME),
				StateChangeEvent.SequencingKind.valueOf(attrs.getValue(XMLSupport.SEQUENCING_KIND)),
				StateChangeEvent.State.valueOf(attrs.getValue(XMLSupport.STATE)),
				Long.parseLong(attrs.getValue(XMLSupport.TIMESTAMP)),
				ancestor,
				parameters,
				results,
				context.get(),
				controller.get() );
		// Set the ID to the index in the list
		event.getID();
		// Add the annotations to that event
		if (annotations != null) {
			for (String currentAnnotationKind : annotations.keySet()) {
				String currentAnnotationValue = annotations.get(currentAnnotationKind);
				event.addAnnotation(currentAnnotationKind, currentAnnotationValue);
			} // end for currentAnnotationKind
		}
		ancestors.put(attrs.getValue(XMLSupport.ID), event);
		trace.add(event);
		super.endElement();
	}
	
	static class Cell<T> {
		void set(T v) {
			o = v;
		}
		
		T get() {
			return o;
		}
		
		T o;
	}
	
	private Trace trace;
	private Attributes attrs;
	private Map<String, Event> ancestors;
	
	private Map<String, String> annotations;
    private Map<String, Parameter> parameters;
    private List<String> results;
    private Cell<Context> context = new Cell<Context>();;
    private Cell<Controller> controller = new Cell<Controller>();
}
