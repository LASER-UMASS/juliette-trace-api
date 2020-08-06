package laser.juliette.trace.filter;

import laser.juliette.trace.Event;
import laser.juliette.trace.EventFilter;
import laser.juliette.trace.Event.Annotation;


public class LeafEventFilter implements EventFilter 
{
	public LeafEventFilter() {
		super();
	}
	
	@Override
	public boolean accept(Event event) {
		Annotation isLeafAnnotation = event.getAnnotation(Event.ISLEAF_ANNOTATION_NAME);
		if (isLeafAnnotation == null) {
			return false;
		}
		else {
			Boolean isLeaf = new Boolean(isLeafAnnotation.getValue());
			return isLeaf;
		}
	}

}
